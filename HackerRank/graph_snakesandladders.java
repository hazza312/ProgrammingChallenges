import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
		Scanner in = new Scanner(System.in);
		
		int testCases = in.nextInt();

		for (int testCase=0; testCase < testCases; testCase++) {
			HashMap<Integer, Integer> snakesLadders = new HashMap<Integer, Integer>();
			// handle input
			int nLadders = in.nextInt();
			for (int i=0; i<nLadders; i++) 	snakesLadders.put(in.nextInt(), in.nextInt());			

			int nSnakes = in.nextInt();

			for (int i=0; i<nSnakes; i++)	snakesLadders.put(in.nextInt(), in.nextInt());
		
			// start solving
			int[] rolls = new int[101];
			ArrayDeque<Integer> queue = new ArrayDeque<Integer>();

			queue.addFirst(1);
			rolls[1] = 0;

			while (!queue.isEmpty()) {
				int current = queue.removeFirst();

				for (int i=current +1; i<=current+6 && i <=100; i++) {
					int consider = (snakesLadders.containsKey(i)) ? snakesLadders.get(i) : i;
					
					if (rolls[consider] == 0) {
						rolls[consider] = rolls[current] + 1;
						queue.add(consider);
					} 
					
								
				}
			}

			System.out.println((rolls[100] == 0) ? -1 : rolls[100]);			
		}
		
 
    }
}