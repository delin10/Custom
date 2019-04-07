package org.delin.algorithm;

import org.delin.test.array.ArrayPrinter;
import org.delin.test.data.UnsortBigDataGenerator;

import java.io.*;
import java.nio.file.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
/**
 * 分治排序
 * bug 会多出一行
 * 尝试使用位图索引
 */
public class MergeSort {
    private static ForkJoinPool fp = new ForkJoinPool();

    public static void merge(int[] arr, int l, int m, int h) {
        int li = l, mi = m + 1, hi = mi, cur = 0;
        int[] tempArr = new int[h - l + 1];
        while (li < mi && hi <= h) {
            while (hi <= h && arr[hi] <= arr[li]) {
                tempArr[cur++] = arr[hi++];
            }
            if (hi > h) {
                break;
            }
            while (li < mi && arr[li] <= arr[hi]) {
                tempArr[cur++] = arr[li++];
            }
        }

        if (li == mi) {
            while (hi <= h) {
                tempArr[cur++] = arr[hi++];
            }
        }

        if (hi > h) {
            while (li < mi) {
                tempArr[cur++] = arr[li++];
            }
        }
        System.arraycopy(tempArr, 0, arr, l, tempArr.length);
    }

    public static void mergeSort(int[] arr, int l, int h) {
        if (l >= h) {
            return;
        }
        int mid = (l + h) / 2;
        mergeSort(arr, l, mid);
        mergeSort(arr, mid + 1, h);
        merge(arr, l, mid, h);
    }

    public static void mergeSortForkJoin(int[] arr, int l, int h) {
        if (l >= h) {
            return;
        }
        int mid = (l + h) / 2;
        fp.submit(() -> {
            mergeSort(arr, l, mid);
        });
        fp.submit(() -> {
            mergeSort(arr, mid + 1, h);
        });
        try {
            fp.awaitTermination(2, TimeUnit.SECONDS
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        merge(arr, l, mid, h);
    }

    static LinkedList<Path> paths = new LinkedList<>();

    public static void loadAndSort(String path, String temp, int baseSize) {
        int[] arr = new int[baseSize];
        int count = 0;
        try (DataInputStream di = new DataInputStream(new BufferedInputStream(Files.newInputStream(Paths.get(path + "/test.dat"))))) {
            Files.createDirectories(Paths.get(temp));
            //不会递归创建上级目录
            Path dirPth = Files.createTempDirectory(Paths.get(temp), "temp");
            while (true) {
                try {
                    arr[count++] = di.readInt();
                } catch (IOException ie) {
                    break;
                }
                //到达划分文件阈值
                if (count == baseSize) {
                    Path pth = Files.createTempFile(dirPth, "temp", ".dat");
                    paths.add(pth);
                    Arrays.sort(arr);
                    try (DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(pth, StandardOpenOption.TRUNCATE_EXISTING)))) {
                        for (int num : arr) {
                            dout.writeInt(num);
                        }
                    } catch (IOException ie) {
                        ie.printStackTrace();
                    }
                    count = 0;
                }
            }
            //未满文件阈值
            if (count != 0) {
                Path pth = Files.createTempFile(dirPth, "temp", ".dat");
                paths.add(pth);
                Arrays.sort(arr);
                try (DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(pth, StandardOpenOption.TRUNCATE_EXISTING)))) {
                    for (int i = 0; i < count; ++i) {
                        dout.writeInt(arr[i]);
                    }
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Path mergeFile(Path pa, Path pb, int bufferSize, String temp, String name) {
        int[] buffer = new int[bufferSize];
        Path pth = null;
        try {
            pth = Files.createTempFile(Paths.get(temp), name + "-merge", ".dat");
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean forwa = false, forwb = false;
        DataInputStream dia = null, dib = null;
        DataOutputStream dout = null;
        int cur = 0, nxtb = 0, nxta = 0;
        try {
            dia = new DataInputStream(new BufferedInputStream(Files.newInputStream(pa)));
            dib = new DataInputStream(new BufferedInputStream(Files.newInputStream(pb)));
            dout = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(pth, StandardOpenOption.TRUNCATE_EXISTING)));
            //代表a或b是无值的
            nxta = dia.readInt();
            nxtb = dib.readInt();
            while (true) {
                while (nxta <= nxtb) {
                    buffer[cur++] = nxta;
                    forwa = true;
                    nxta = dia.readInt();
                    if (cur == bufferSize) {
                        writeToFile(dout, buffer, 0, bufferSize);
                        cur = 0;
                    }
                }
                forwa = false;
                while (nxtb <= nxta) {
                    buffer[cur++] = nxtb;
                    forwb = true;
                    nxtb = dib.readInt();
                    if (cur == bufferSize) {
                        writeToFile(dout, buffer, 0, bufferSize);
                        cur = 0;
                    }
                }
                forwb = false;
            }


        } catch (IOException e) {
            //处理缓冲区中的数据
            if (cur != 0) {
                writeToFile(dout, buffer, 0, cur);
            }
            //b的数据未被处理
            if (forwa) {
                try {
                    dout.writeInt(nxtb);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                fromto(dout, dib);
            }
            //a的数据未被处理
            if (forwb) {
                try {
                    dout.writeInt(nxta);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                fromto(dout, dia);
            }
        } finally {
            try {
                dia.close();
                dib.close();
                dout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pth;
    }


    public static void writeToFile(DataOutputStream dout, int[] arr, int from, int len) {
        try {
            int end = from + len;
            for (int i = from; i < end; ++i) {
                dout.writeInt(arr[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void fromto(DataOutputStream dout, DataInputStream din) {
        try {
            while (true) {
                dout.writeInt(din.readInt());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String temp = "E:/myTemp";
        int bufferSize = 10000000;
        loadAndSort("E:/", temp, bufferSize);
        while (paths.size() != 1) {
            Path pa = paths.removeFirst();
            Path pb = paths.removeFirst();
            paths.addLast(mergeFile(pa, pb, bufferSize, temp, "merge"));
            try {
                Files.deleteIfExists(pa);
                Files.deleteIfExists(pb);
            } catch (IOException ie) {

            }
        }

        Path rs = paths.removeFirst();
        try {
            Files.createDirectories(Paths.get(temp));
            Path rsPath = Paths.get(temp, "rs.dat");
            Files.createFile(rsPath);
            Files.copy(rs, rsPath, StandardCopyOption.REPLACE_EXISTING);
            Files.deleteIfExists(rs);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String path = "E:/myTemp", from = "rs.dat", to = "rsss.dat";
        UnsortBigDataGenerator.getData(path, from, to);
    }

}
