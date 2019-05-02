package org.delin.meituan;

import java.util.Scanner;

public class Main0 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m, n;
        n = scanner.nextInt();
        m = scanner.nextInt();
        int[][] matrix = new int[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (!isBW(matrix,i,j)){

                }
            }
        }
    }

    public static boolean isBW(int[][] matrix, int i, int j) {
        boolean res = true;
        int m = matrix[0].length;
        int n = matrix.length;
        int cur = matrix[i][j];
        if (i == 0 && j == 0) {
            res = res && matrix[i][j + 1] == matrix[i + 1][j];
            res = res && matrix[i][j + 1] != cur;
            return res;
        }

        if (i == 0 && j == m - 1) {
            res=res && matrix[i + 1][j] == matrix[i][j - 1];
            res = res && matrix[i + 1][j] != cur;
            return res;
        }

        if (i == n - 1 && j == m - 1) {
            res=res && matrix[i - 1][j] == matrix[i][j - 1];
            res = res && matrix[i - 1][j] != cur;
            return res;
        }

        if (i == n - 1 && j == 0) {
            res=res && matrix[i - 1][j] == matrix[i][j + 1];
            res = res && matrix[i - 1][j] != cur;
            return res;
        }

        if (i==0){
            res = res && matrix[i][j + 1] == matrix[i + 1][j] && matrix[i][j-1]==matrix[i + 1][j];
            res = res && matrix[i][j + 1] != cur;
            return res;
        }

        if (j==0){
            res = res && matrix[i][j + 1] == matrix[i + 1][j] && matrix[i-1][j]==matrix[i + 1][j];
            res = res && matrix[i][j + 1] != cur;
            return res;
        }

        if (j==m-1){
            res = res && matrix[i][j - 1] == matrix[i + 1][j] && matrix[i-1][j]==matrix[i + 1][j];
            res = res && matrix[i][j - 1]  != cur;
            return res;
        }

        if (i==n-1){
            res = res && matrix[i][j - 1] == matrix[i][j+1] && matrix[i-1][j]==matrix[i][j-1];
            res = res && matrix[i][j - 1] != cur;
            return res;
        }

        res=res&& matrix[i][j - 1] == matrix[i-1][j]&&matrix[i-1][j]==matrix[i][j+1]&&matrix[i][j+1]==matrix[i+1][j];
        return res = res && matrix[i][j - 1] != cur;
    }
    //i+1,j
    //i-1,j
    //i,j+1
    //i,j-1
}
