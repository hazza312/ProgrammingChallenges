/*

Exercise 10038 (P.42), Jolly Jumpers
hk, JULY 2017

javac Main.java

*/

import java.util.*;

class Main {

	Scanner in, lineParser;
	Stack<Integer> inputNumbers;

	public static void main (String args[]) {
        Main myWork = new Main();
        myWork.Begin();
    }

	boolean contains(int[] array, int value) {
		for (int i=0; i<array.length; i++) {
			
			if (array[i] == value){ return true;}
		}
		return false;
	}

	boolean allDifferences (int[] differences) {
		for (int i=1; i<= differences.length; i++) {	
			if (!contains(differences, i)) {return false;}
		}
		return true;
	}

    void Begin() {
		in = new Scanner(System.in);
		while (in.hasNextLine()) {
			lineParser = new Scanner(in.nextLine());
			inputNumbers = new Stack<Integer>();
			lineParser.nextInt();

			while (lineParser.hasNextInt()) {
				inputNumbers.push(lineParser.nextInt());
			}
	
			int numItems = inputNumbers.size();
			int[] differences = new int[numItems - 1];	
			int current;
			int previous=inputNumbers.pop();

			for (int i=0; !inputNumbers.empty(); i++) {
				current = inputNumbers.pop();
				differences[i] =  current - previous;
				if (differences[i]<0) {differences[i] *= -1;}
				previous = current;
			}

			if (allDifferences(differences)) 	System.out.println("Jolly");
			else  								System.out.println("Not jolly");

		}		
        
    }

}
