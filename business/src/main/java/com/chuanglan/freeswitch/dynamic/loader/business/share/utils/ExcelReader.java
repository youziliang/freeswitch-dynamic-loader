package com.chuanglan.freeswitch.dynamic.loader.business.share.utils;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public abstract class ExcelReader extends DefaultHandler {
    /**
     * 共享字符串表
     */
    private SharedStringsTable sst;

    /**
     * 上一次的内容
     */
    private String lastContents;

    /**
     * 字符串标识
     */
    private boolean nextIsString;

    /**
     * 工作表索引
     */
    private int sheetIndex = -1;

    /**
     * 行集合
     */
    private List<String> rowlist = new ArrayList<>();

    /**
     * 当前行
     */
    private int curRow = 0;

    /**
     * 当前列
     */
    private int curCol = 0;

    /**
     * T元素标识
     */
    private boolean isTElement;

    /**
     * 单元格数据类型，默认为字符串类型
     */
    private CellDataType nextDataType = CellDataType.SSTINDEX;

    private final DataFormatter formatter = new DataFormatter();

    private short formatIndex;

    private String formatString;

    /**
     * 单元格
     */
    private StylesTable stylesTable;

    /**
     * 遍历工作簿中所有的电子表格
     */
    public void process(String filename) throws IOException, OpenXML4JException, SAXException {
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader xssfReader = new XSSFReader(pkg);
        stylesTable = xssfReader.getStylesTable();
        SharedStringsTable sst = xssfReader.getSharedStringsTable();
        XMLReader parser = this.fetchSheetParser(sst);
        Iterator<InputStream> sheets = xssfReader.getSheetsData();
        curRow = 0;
        sheetIndex++;
        InputStream sheet = sheets.next();
        InputSource sheetSource = new InputSource(sheet);
        parser.parse(sheetSource);
        sheet.close();
    }


    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        this.sst = sst;
        parser.setContentHandler(this);
        return parser;
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        // c => 单元格
        if ("c".equals(name)) {
            // 设定单元格类型
            this.setNextDataType(attributes);
        }

        // 当元素为t时
        if ("t".equals(name)) {
            isTElement = true;
        } else {
            isTElement = false;
        }

        // 置空
        lastContents = "";
    }

    /**
     * 单元格中的数据可能的数据类型
     */
    enum CellDataType {
        BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER, DATE, NULL
    }

    /**
     * 处理数据类型
     */
    public void setNextDataType(Attributes attributes) {
        nextDataType = CellDataType.NUMBER;
        formatIndex = -1;
        formatString = null;
        String cellType = attributes.getValue("t");
        String cellStyleStr = attributes.getValue("s");

        if ("b".equals(cellType)) {
            nextDataType = CellDataType.BOOL;
        } else if ("e".equals(cellType)) {
            nextDataType = CellDataType.ERROR;
        } else if ("inlineStr".equals(cellType)) {
            nextDataType = CellDataType.INLINESTR;
        } else if ("s".equals(cellType)) {
            nextDataType = CellDataType.SSTINDEX;
        } else if ("str".equals(cellType)) {
            nextDataType = CellDataType.FORMULA;
        }

        if (cellStyleStr != null) {
            int styleIndex = Integer.parseInt(cellStyleStr);
            XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
            formatIndex = style.getDataFormat();
            formatString = style.getDataFormatString();


            if (formatIndex == 14 || formatIndex == 31 || formatIndex == 57 || formatIndex == 58
                    || (176 <= formatIndex && formatIndex <= 178) || (182 <= formatIndex && formatIndex <= 196)
                    || (210 <= formatIndex && formatIndex <= 213) || (208 == formatIndex)) { // 日期
                nextDataType = CellDataType.DATE;
                ReservedFormat format = ReservedFormat.valueOf(formatIndex);
                if (format != null) {
                    formatString = format.getFormatString();
                } else if (formatString == null) {
                    formatString = "yyyy-MM-dd";
                } else {
                    //NOOP
                }

            } else if (formatIndex == 20 || formatIndex == 32 || formatIndex == 183 || (200 <= formatIndex && formatIndex <= 209)) { // 时间
                nextDataType = CellDataType.DATE;
                ReservedFormat format = ReservedFormat.valueOf(formatIndex);
                if (format != null) {
                    formatString = format.getFormatString();
                } else if (formatString == null) {
                    formatString = "hh:mm:ss";
                } else {
                    //NOOP  不处理
                }
            } else {

            }

            if (formatIndex == 22) {
                nextDataType = CellDataType.DATE;
                formatIndex = 0;
                formatString = "yyyy/M/d h:m";
            }

            if (formatIndex == 14) {
                nextDataType = CellDataType.DATE;
                formatIndex = 0;
                formatString = "yyyy/M/d";
            }


            if (formatString == null) {
                nextDataType = CellDataType.NULL;
                formatString = BuiltinFormats.getBuiltinFormat(formatIndex);
            }
        }
    }

    /**
     * 对解析出来的数据进行类型处理
     *
     * @param value 单元格的值（这时候是一串数字）
     */
    @SuppressWarnings("deprecation")
    public String getDataValue(String value) {
        String thisStr;
        switch (nextDataType) {
            // 这几个的顺序不能随便交换，交换了很可能会导致数据错误
            case BOOL:
                char first = value.charAt(0);
                thisStr = first == '0' ? "FALSE" : "TRUE";
                break;
            case ERROR:
                thisStr = "\"ERROR:" + value + '"';
                break;
            case FORMULA:
                thisStr = '"' + value + '"';
                break;
            case INLINESTR:
                XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());

                thisStr = rtsi.toString();
                rtsi = null;
                break;
            case SSTINDEX:
                String sstIndex = value;
                try {
                    int idx = Integer.parseInt(sstIndex);
                    XSSFRichTextString rtss = new XSSFRichTextString(sst.getEntryAt(idx));
                    thisStr = rtss.toString();
                    rtss = null;
                } catch (NumberFormatException ex) {
                    thisStr = value;
                }
                break;
            case NUMBER:
                if (formatString != null) {
                    thisStr = value;
                } else {
                    thisStr = value;
                }

                thisStr = thisStr.replace("_", "").trim();
                break;
            case DATE:
                thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString);
                break;
            default:
                thisStr = " ";

                break;
        }

        return thisStr;
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        // 根据SST的索引值的到单元格的真正要存储的字符串
        // 这时characters()方法可能会被调用多次
        if (nextIsString) {
            int idx = Integer.parseInt(lastContents);
            lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
        }

        // t元素也包含字符串
        if (isTElement) {
            // 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
            String value = lastContents.trim();
            rowlist.add(curCol, value);
            curCol++;
            isTElement = false;
        } else if ("v".equals(name)) {
            // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
            String value = this.getDataValue(lastContents.trim());

            rowlist.add(curCol, value);
            curCol++;
        } else {
            // 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
            if (name.equals("row")) {
                getRows(sheetIndex, curRow, rowlist);

                rowlist.clear();
                curRow++;
                curCol = 0;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // 得到单元格内容的值
        lastContents += new String(ch, start, length);
    }


    enum ReservedFormat {

        /*reserved17(23,""),
        reserved18(24,""),
        reserved19(25,""),
        reserved1A(26,""),
        reserved1B(27,""),*/
        reserved1C(28, "m月d日"),
        /* reserved1D(29,""),
         reserved1E(30,""),*/
        reserved1F(31, "yyyy年m月d日"),
        reserved20(32, "h时mm分"),
        reserved21(33, "h时mm分ss秒"),
        /*reserved22(34,""),
        reserved23(35,""),
        reserved24(36,""),*/
        reserved3A(58, "h时mm分"),
        reservedB2(178, "AMtw/PMtwh时mm分"),
        reservedB3(179, "AMtw/PMtwh时mm分ss秒"),;

        private int format;

        private String formatString;

        ReservedFormat(int format, String formatString) {
            this.format = format;
            this.formatString = formatString;
        }


        public static ReservedFormat valueOf(int format) {
            for (ReservedFormat reservedFormat : ReservedFormat.values()) {
                if (reservedFormat.getFormat() == format) {
                    return reservedFormat;
                }
            }
            return null;
        }

        public int getFormat() {
            return format;
        }

        public void setFormat(int format) {
            this.format = format;
        }

        public String getFormatString() {
            return formatString;
        }

        public void setFormatString(String formatString) {
            this.formatString = formatString;
        }
    }


    /**
     * 获取行数据回调
     */
    public abstract void getRows(int sheetIndex, int curRow, List<String> rowList);

    public static void main(String[] args) throws OpenXML4JException, SAXException, IOException {
        ExcelReader excelReader = new ExcelReader() {
            @Override
            public void getRows(int sheetIndex, int curRow, List<String> rowList) {

            }
        };
        excelReader.process("");
    }
}

