package com.chuanglan.freeswitch.dynamic.loader.share.utils;

import com.chuanglan.freeswitch.dynamic.loader.core.constants.SystemPunctuation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Arrays;
import java.util.TreeSet;

@Slf4j
public class NioUtil {

    /**
     * @param source
     * @param path
     * @param fileName
     * @return
     * @Description NIO写出（适合小文件）
     */
    public static Boolean writeToDiskNio(MultipartFile source, String path, String fileName) {
        File file = new File(path + SystemPunctuation.ANTI_SLASH + fileName);
        try (
                FileOutputStream outputStream = new FileOutputStream(file, false);
                FileChannel channel = outputStream.getChannel()
        ) {
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 10);
            buffer.put(source.getBytes());
            buffer.flip();//此处必须要调用buffer的flip方法
            long write = channel.write(buffer);
            buffer.clear();
            if (checkOver(path, fileName, file, write)) return true;
        } catch (IOException | InterruptedException e) {
            log.error("Write File To Disk Exception: {}", e.getMessage());
            return false;
        }
        return false;
    }

    /**
     * @param source
     * @param path
     * @param fileName
     * @return
     * @Description 流写出（适合大文件）
     */
    public static Boolean writeToDisk(MultipartFile source, String path, String fileName) {
        File file = new File(path + SystemPunctuation.ANTI_SLASH + fileName);
        try (
                InputStream in = source.getInputStream();
                OutputStream out = new FileOutputStream(file, false)
        ) {
            if (!file.exists())
                file.createNewFile();
            int i;
            while ((i = in.read()) != -1)
                out.write(i);
            return true;
        } catch (Exception e) {
            log.error("Write File To Disk Exception: {}", e.getMessage());
            return false;
        }
    }

    public static void readFromDiskNio(String fullPath) {
        try {
            File file = new File(fullPath);
            FileChannel channel = new RandomAccessFile(file, "r").getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 10);
            CharsetDecoder decoder = Charset.defaultCharset().newDecoder();
            while ((channel.read(buffer)) != -1) {
                buffer.flip();
                CharBuffer decode = decoder.decode(buffer);
                decode.toString();
                buffer.clear();
            }
        } catch (IOException e) {
            log.error("Read File From Disk Exception: {}", e.getMessage());
        }
    }

    public static void mergeFiles(String fileDir, String fileName) {
        File dir = new File(fileDir);
        File combination = new File(fileDir + SystemPunctuation.ANTI_SLASH + fileName);
        File[] files = dir.listFiles();
        if (null == files || files.length < 1)
            return;
        TreeSet<File> fileSet = new TreeSet<>(Arrays.asList(files));
        for (File file : fileSet) {
            try (
                    FileChannel outChannel = new FileOutputStream(combination, true).getChannel();
                    FileChannel inChannel = new FileInputStream(file).getChannel()
            ) {
                outChannel.transferFrom(inChannel, outChannel.size(), inChannel.size());
            } catch (IOException e) {
                log.error("File Merge Exception: {}", e.getMessage());
            }
        }
    }

    public static void spiltFile(String source, long singleSize, String targetPath) {//singleSize 单位MB
        File file = new File(source);
        singleSize = singleSize * 1024 * 1024;
        int count = (int) (file.length() / singleSize);
        for (int i = 0; i <= count; i++) {
            String tempName = targetPath + SystemPunctuation.ANTI_SLASH + i;
            try (
                    FileChannel inChannel = new FileInputStream(file).getChannel();
                    FileChannel outChannel = new FileOutputStream(new File(tempName)).getChannel()
            ) {
                if (i != count)//从inChannel的m*i处，读取固定长度的数据，写入outChannel
                    inChannel.transferTo(singleSize * i, singleSize, outChannel);
                else//最后一个文件，大小不固定，所以需要重新计算长度
                    inChannel.transferTo(singleSize * i, file.length() - singleSize * count, outChannel);
            } catch (IOException e) {
                log.error("Split File Exception: {}", e.getMessage());
            }
        }
    }

    public static void recurDel(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] innerFiles = file.listFiles();
                if (innerFiles == null)
                    return;
                for (File innerfile : innerFiles)
                    recurDel(innerfile);
                file.delete();
            }
        }
    }

    private long sizeOfDir(File file) {//TODO 待测试
        if (file.isFile())
            return file.length();
        File[] children = file.listFiles();
        long total = 0;
        if (children != null)
            for (File child : children)
                total += sizeOfDir(child);
        return total;
    }

    private static boolean checkOver(String path, String fileName, File file, long write) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            log.info("文件 {} 实际大小与已写入磁盘大小比较 {}:{}",
                    path + SystemPunctuation.ANTI_SLASH + fileName, write, file.length());
            if (write == file.length()) {
                return true;
            } else {
                Thread.sleep(100);
            }
        }
        return false;
    }
}
