package com.chuanglan.freeswitch.dynamic.loader.business.share.utils;

import com.chuanglan.freeswitch.dynamic.loader.core.constants.SystemCharset;
import com.chuanglan.freeswitch.dynamic.loader.core.constants.SystemPunctuation;
import com.chuanglan.freeswitch.dynamic.loader.core.enums.FileExt;
import com.chuanglan.freeswitch.dynamic.loader.core.utils.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * @Description 导入文件解析类
 * @Author Youziliang
 * @Date 2019/3/21
 */
@Slf4j
public class FileParser {

    public static List<Map<Object, String>> readXLS(InputStream is) throws ParseException {
        List<Map<Object, String>> datas = new LinkedList<>();
        Map<Object, String> data;
        try (
                POIFSFileSystem poifsFileSystem = new POIFSFileSystem(is);
                HSSFWorkbook workbook = new HSSFWorkbook(poifsFileSystem)
        ) {
            HSSFSheet sheet = workbook.getSheetAt(0);
            int rowLength = sheet.getLastRowNum() + 1;
            HSSFRow hssfRow = sheet.getRow(0);
            int colLength = hssfRow.getLastCellNum();
            for (int i = 0; i < rowLength; i++) {
                HSSFRow hssfRow1 = sheet.getRow(i);
                data = new LinkedHashMap<>();
                for (int j = 0; j < colLength; j++) {
                    HSSFCell hssfCell1 = hssfRow1.getCell(j);
                    String stringCellValue = SystemPunctuation.EMPTY_STRING;
                    if (hssfCell1 != null) {
                        hssfCell1.setCellType(CellType.STRING);
                        stringCellValue = hssfCell1.getStringCellValue();
                    }
                    data.put(j, stringCellValue);
                }
                datas.add(i, data);
            }
        } catch (IOException e) {
            throw new ParseException("XLS解析异常", datas.size());
        }
        return datas;
    }


    public static List<Map<Object, String>> readXLSX(InputStream is) throws ParseException {
        List<Map<Object, String>> datas = new LinkedList<>();
        Map<Object, String> data;
        try (
                XSSFWorkbook workbook = new XSSFWorkbook(is)
                //Workbook workbook = WorkbookFactory.create(is)
        ) {
            Sheet sheet = workbook.getSheetAt(0);
            int rowLength = sheet.getLastRowNum() + 1;
            Row row = sheet.getRow(0);
            int colLength = row.getLastCellNum();
            for (int i = 0; i < rowLength; i++) {
                row = sheet.getRow(i);
                data = new LinkedHashMap<>();
                for (int j = 0; j < colLength; j++) {
                    Cell cell = row.getCell(j);
                    if (cell != null) {
                        cell.setCellType(CellType.STRING);
                        data.put(j, cell.getStringCellValue());
                    }
                }
                datas.add(i, data);
            }
        } catch (IOException e) {
            throw new ParseException("XLSX解析异常", datas.size());
        }
        return datas;
    }

    public static List<Map<Object, String>> readTXT(InputStream is) throws ParseException {
        List<Map<Object, String>> datas = new LinkedList<>();
        Map<Object, String> data;
        try (
                Scanner sc = new Scanner(is, SystemCharset.UTF8)
        ) {
            while (sc.hasNextLine()) {
                String[] splits1 = StringUtils.split(sc.nextLine(), "\t");
                for (int i = 0; i < splits1.length; i++) {
                    data = new LinkedHashMap<>();
                    if (StringUtils.containsAny(splits1[i], SystemPunctuation.COMMA, SystemPunctuation.COLON)) {
                        String[] splits2 = splits1[i].split("[,:]", -1);//针对不同格式特殊处理
                        for (int j = 0; j < splits2.length; j++) {
                            data.put(j, splits2[j]);
                        }
                    } else {
                        data.put(i, splits1[i]);
                    }
                    datas.add(data);
                }
            }
        } catch (Exception e) {
            throw new ParseException("TXT或CSV解析异常", datas.size());
        }
        return datas;
    }

    public static void writeCSV(List<String[]> datas, File file) throws ParseException {
        try (CSVPrinter printer = new CSVPrinter(new FileWriter(file), CSVFormat.EXCEL)) {
            for (Object[] record : datas) {
                printer.printRecord(record);
            }
        } catch (Exception e) {
            throw new ParseException("CSV输出异常", datas.size());
        }
    }

    public static List<Map<Object, String>> readCSV(InputStream is) throws ParseException {
        List<Map<Object, String>> datas = new LinkedList<>();
        Map<Object, String> data;
        try (
                InputStreamReader isr = new InputStreamReader(is);
                CSVParser parser = new CSVParser(isr, CSVFormat.DEFAULT)
        ) {
            List<CSVRecord> records = parser.getRecords();
            for (CSVRecord record : records) {
                data = new LinkedHashMap<>();
                data.put(0, record.get(0));
                datas.add(data);
            }
        } catch (IOException e) {
            throw new ParseException("CSV解析异常", 0);
        }
        return datas;
    }

    public static List<Map<Object, String>> streamFileToData(String originName, InputStream is) throws ParseException {
        long start = System.currentTimeMillis();
        List<Map<Object, String>> datas = null;
        if (StringUtils.endsWithIgnoreCase(originName, FileExt.XLS.getDesc())) {
            datas = FileParser.readXLS(is);
        } else if (StringUtils.endsWithIgnoreCase(originName, FileExt.XLSX.getDesc())) {
            datas = FileParser.readXLSX(is);
        } else if (StringUtils.endsWithIgnoreCase(originName, FileExt.TXT.getDesc())) {
            datas = FileParser.readTXT(is);
        } else if (StringUtils.endsWithIgnoreCase(originName, FileExt.CSV.getDesc())) {
            datas = FileParser.readTXT(is);
        }
        long end = System.currentTimeMillis();
        log.info("文件 {} 流转数据耗时: {} 毫秒, 此时剩余的物理内存为: {}",
                originName, end - start, SystemUtil.getFreePhysicalMemorySize());
        return datas;
    }

    public static void main(String args[]) throws Exception {
        /*List<String[]> datas = new ArrayList<>();
        String[] header = new String[]{"账号", "姓名", "电话"};
        String[] content1 = new String[]{"1", "测试1", "13333333333"};
        String[] content2 = new String[]{"2", "测试2", "14444444444"};
        String[] content3 = new String[]{"3", "测试3", "15555555555"};
        datas.add(header);
        datas.add(content1);
        datas.add(content2);
        datas.add(content3);
        writeCSV(datas, "D:/downloads/test.csv");*/
        /*List<CSVRecord> records = readCSV("D:/downloads/test.csv");
        for (int i = 0; i < records.size(); i++) {
            System.out.println(records.get(i));
        }*/

        /*InputStream inputStream = new FileInputStream(new File("C:\\Users\\ChuangLan\\Desktop\\normal\\phone2.xlsx"));
        List<Map<Object, String>> maps = FileParser.readXLSX(inputStream);
        System.out.println(maps);*/

        /*List<String[]> datas = new ArrayList<>();
        for (int i = 0; i < 7000000; i++) {
            String[] content1 = new String[]{8613000000000L + i + ""};
            datas.add(content1);
        }
        try {
            writeCSV(datas, new File("D:/downloads/850Wtest.csv"));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        /*String str = "A:333";
        if (StringUtils.containsAny(str, ",", ":")) {
            String[] split = str.split(",|:", -1);
            System.out.println(split[0] + "===" + split[1]);
        }*/
    }
}

