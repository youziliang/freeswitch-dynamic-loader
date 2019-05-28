package com.chuanglan.freeswitch.dynamic.loader.business.share.utils;

import com.alibaba.druid.util.Base64;
import com.chuanglan.freeswitch.dynamic.loader.core.constants.SystemPunctuation;
import org.apache.commons.lang3.RandomUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Random;

/**
 * @Description 图形验证码生成工具
 * @Author Youziliang
 * @Date 2019/3/28
 */
public class ValidCodeUtil {
    private static Validate validate = null; //验证码类，用于最后返回此对象，包含验证码图片base64和真值
    private static Random random = new Random(); //随机类，用于生成随机参数
    private static String randString = "0123456789abcdefghijkmnpqrtyABCDEFGHIJLMNQRTY"; //随机生成字符串的取值范围

    private static int width = 80;     //图片宽度
    private static int height = 34;    //图片高度
    private static int StringNum = 4;  //字符的数量
    private static int lineSize = 40;  //干扰线数量


    //将构造函数私有化 禁止new创建
    private ValidCodeUtil() {
        super();
    }

    /**
     * 获取随机字符,并返回字符的String格式
     */
    private static String getRandomChar(int index) {
        //获取指定位置index的字符，并转换成字符串表示形式
        return String.valueOf(randString.charAt(index));
    }

    /**
     * 获取随机指定区间的随机数
     */
    private static int getRandomNum(int min, int max) {
        return RandomUtils.nextInt(min, max);
    }

    /**
     * 获得字体
     */
    private static Font getFont() {
        return new Font("Fixedsys", Font.CENTER_BASELINE, 25);  //名称、样式、磅值
    }

    /**
     * 获得颜色
     */
    private static Color getRandColor(int frontColor, int backColor) {
        if (frontColor > 255)
            frontColor = 255;
        if (backColor > 255)
            backColor = 255;

        int red = frontColor + random.nextInt(backColor - frontColor - 16);
        int green = frontColor + random.nextInt(backColor - frontColor - 14);
        int blue = frontColor + random.nextInt(backColor - frontColor - 18);
        return new Color(red, green, blue);
    }

    /**
     * 绘制字符串,返回绘制的字符串
     */
    private static String drawString(Graphics graphics, String randomString, int i) {
        Graphics2D g2d = (Graphics2D) graphics;

        g2d.setFont(getFont());//设置字体
        g2d.setColor(new Color(random.nextFloat(), random.nextFloat(), random.nextFloat()));//设置颜色
        String randChar = String.valueOf(getRandomChar(random.nextInt(randString.length())));
        randomString += randChar;//组装
        int rot = getRandomNum(5, 10);
        g2d.translate(random.nextInt(3), random.nextInt(3));
        g2d.rotate(rot * Math.PI / 180);
        g2d.drawString(randChar, 13 * i, 20);
        g2d.rotate(-rot * Math.PI / 180);
        return randomString;
    }

    /**
     * 绘制干扰线
     */
    private static void drawLine(Graphics g) {
        //起点(x,y)，偏移量x1、y1
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.setColor(new Color(random.nextFloat(), random.nextFloat(), random.nextFloat()));
        g.drawLine(x, y, x + xl, y + yl);
    }

    /**
     * 生成Base64图片验证码
     */
    public static Validate getRandomCode() {
        validate = validate == null ? new Validate() : validate;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        // 获得BufferedImage对象的Graphics对象
        Graphics graphics = image.getGraphics();
        //填充矩形
        graphics.fillRect(0, 0, width, height);
        graphics.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));//设置字体
        graphics.setColor(getRandColor(110, 133));//设置颜色
        //绘制干扰线
        for (int i = 0; i <= lineSize; i++) {
            drawLine(graphics);
        }
        //绘制字符
        String randomString = SystemPunctuation.EMPTY_STRING;
        for (int i = 1; i <= StringNum; i++) {
            randomString = drawString(graphics, randomString, i);
            validate.setValue(randomString);
        }
        graphics.dispose();//释放绘图资源
        try (
                ByteArrayOutputStream bs = new ByteArrayOutputStream()
        ) {
            ImageIO.write(image, "png", bs);//将绘制得图片输出到流
            String imgsrc = Base64.byteArrayToBase64(bs.toByteArray());
            validate.setBase64Str(imgsrc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return validate;
    }

    /**
     * 验证码类
     */
    public static class Validate implements Serializable {
        private static final long serialVersionUID = 1L;
        private String Base64Str;//Base64 值
        private String value;//验证码值

        public String getBase64Str() {
            return Base64Str;
        }

        public void setBase64Str(String base64Str) {
            Base64Str = base64Str;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
