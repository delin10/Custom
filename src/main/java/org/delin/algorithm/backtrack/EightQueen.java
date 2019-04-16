package org.delin.algorithm.backtrack;

import org.delin.util.ArrayUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 八皇后问题
 * S1、获取单个解
 * S2、获取所有解的列表
 */
public class EightQueen {
    private static boolean[][] chessboard = new boolean[8][8];
    private static int num = 8;
    private static List<boolean[][]> all = new LinkedList<>();

    /**
     * 判断当前给定位置放置皇后是否合法
     * @param row
     * @param col
     * @return
     */
    private static boolean isValid(int row, int col) {
        return isColumnValid(row, col) && isLeftTopValid(row, col) && isRightTopValid(row, col);
    }

    /**
     * 同一列是否存在皇后
     * @param row
     * @param col
     * @return
     */
    private static boolean isColumnValid(int row, int col) {
        for (int i = row; i >= 0; --i) {
            if (chessboard[i][col]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 左上对角线判断是否存在皇后
     * @param row
     * @param col
     * @return
     */
    private static boolean isLeftTopValid(int row, int col) {
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; --i, --j) {
            if (chessboard[i][j]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 在右上对角线判断是否存在皇后
     * @param row
     * @param col
     * @return
     */
    private static boolean isRightTopValid(int row, int col) {
        for (int i = row - 1, j = col + 1; i >= 0 && j < 8; --i, ++j) {
            if (chessboard[i][j]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否已经放置八个皇后
     * @return
     */
    private static boolean isEnd() {
        return num == 0;
    }

    /**
     * 回溯
     * @param row
     * @param col
     */
    private static void backTrace(int row, int col) {
        chessboard[row][col] = false;
        ++num;
    }

    /**
     * 放置皇后
     * @param row
     * @param col
     */
    private static void put(int row, int col) {
        chessboard[row][col] = true;
    }

    /**
     * 获得一个解
     * @param row
     * @param lastCol
     */
    public static void queen(int row, int lastCol) {
        for (int i = 0; i < 8 && !isEnd(); ++i) {
            if (isValid(row, i)) {
                put(row, i);
                num--;
                printQueen(chessboard);
                queen(row + 1, i);
            }

        }
        if (!isEnd()) {
            backTrace(row - 1, lastCol);
        }
    }

    /**
     * 获得92种解法
     * @param row
     * @param lastCol
     */
    public static void queenAll(int row, int lastCol) {
        for (int i = 0; i < 8; ++i) {
            if (isValid(row, i)) {
                put(row, i);
                num--;
                if (row + 1 < 8) {
                    queenAll(row + 1, i);
                }
                if (isEnd()) {
                    all.add(ArrayUtils.copy(chessboard));
                    backTrace(row, i);
                }
            }

        }
        if (row > 0) {
            backTrace(row - 1, lastCol);
        }
    }

    /**
     * 按照一定格式打印棋盘
     * @param chessboard
     */
    public static void printQueen(boolean[][] chessboard) {
        printHorizentalBorder(49);
        for (int i = 0; i < 8; ++i) {
            System.out.print("|");
            for (int j = 0; j < 8; ++j) {
                if (chessboard[i][j]) {
                    System.out.print("  1  ");
                } else {
                    System.out.print("  0  ");
                }
                System.out.print("|");
            }
            System.out.println();
            printHorizentalBorder(49);
        }
    }

    /**
     * 打印横向边界线，类似 “-----”
     * @param n
     */
    public static void printHorizentalBorder(int n) {
        for (int i = 0; i < n; ++i) {
            System.out.print("-");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        queenAll(0, -1);
        System.out.println("共有:" + all.size() + "种结果");
        all.forEach(EightQueen::printQueen);
    }
}
