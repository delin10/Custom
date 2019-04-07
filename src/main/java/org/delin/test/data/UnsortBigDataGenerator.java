package org.delin.test.data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;

/**
 * 生成未排序的大文件
 */
public class UnsortBigDataGenerator {
    public static void generate(long count, String path) {
        Random random = new Random();
        //Buffer快老多

        try (DataOutputStream ds = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(Paths.get(path + "/test.dat"), StandardOpenOption.TRUNCATE_EXISTING,StandardOpenOption.CREATE)))) {
            for (long i = 0; i < count; ++i) {
//                ds.writeInt(random.nextInt(Integer.MAX_VALUE));
                ds.writeInt((int)i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void getData(String path, String from, String to) {
        try (DataInputStream di = new DataInputStream(new BufferedInputStream(Files.newInputStream(Paths.get(path, from)))); BufferedWriter bfw = Files.newBufferedWriter(Paths.get(path, to), StandardOpenOption.TRUNCATE_EXISTING,StandardOpenOption.CREATE)) {
            int nxt = 0;
            while (true) {
                nxt = di.readInt();
                bfw.write(nxt + "\n");
            }
        } catch (IOException e) {

        }
    }

    public static void main(String[] args) {
        //500~mb
        //generate(100000000L, "E:/");
        //String path = "E:/", from = "test.dat", to = "testSS.dat";
        String path = "E:/myTemp", from = "rs.dat", to = "rsss.dat";
        getData(path, from, to);
    }
}
