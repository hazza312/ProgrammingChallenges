import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static void main(String args[] ) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        // num substrings = (n*n + n) /2
        Scanner in = new Scanner(System.in);
        
        String original = in.next();
        int n = original.length();
        long moda = 1000000007;
        long ones = 1;
        long sum=0; 
        for (int s=original.length()-1; s>=0; s--) {
            sum = (sum + (s+1) * (original.charAt(s) - '0') * ones) %moda;                
            ones = (ones * 10 + 1) % moda;
            
        }
        
        System.out.println(sum);
        
    }
}