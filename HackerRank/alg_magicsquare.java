import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    static final int[][][] magicSquares = new int[][][] {   
        {{8,1,6},{3,5,7},{4,9,2}},
        {{4,3,8},{9,5,1},{2,7,6}},
        {{2,9,4},{7,5,3},{6,1,8}},
        {{6,7,2},{1,5,9},{8,3,4}},
        {{6,1,8},{7,5,3},{2,9,4}},
        {{8,3,4},{1,5,9},{6,7,2}},
        {{4,9,2},{3,5,7},{8,1,6}},
        {{2,7,6},{9,5,1},{4,3,8}}        
    };
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[][] s = new int[3][3];
        for(int s_i=0; s_i < 3; s_i++){
            for(int s_j=0; s_j < 3; s_j++){
                s[s_i][s_j] = in.nextInt();
            }
        }
        
        int minimumCost = 9999;
        int thisCost;
        for (int p=0; p<8; p++) {
            thisCost = 0;
            for (int i=0; i<3; i++) {
                for (int j=0; j<3; j++) {
                    thisCost += Math.abs(magicSquares[p][i][j] - s[i][j]);
                }
            }
            minimumCost = Math.min(minimumCost, thisCost);
        }
        //  Print the minimum cost of converting 's' into a magic square
        System.out.println(minimumCost);
    }
}