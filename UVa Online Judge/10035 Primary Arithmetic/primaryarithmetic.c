/*

Exercise 10035 (P.119), Primary Arithmetic
hk, JULY 2017

gcc primaryarithmetic.c -lm -lcrypt -O2 -pipe -ansi -DONLINE_JUDGE

*/

#include <stdlib.h>
#include <stdio.h>


int main() {
	unsigned int intA, intB;
	unsigned char numA[10], numB[10];

	while (scanf("%u %u", &intA, &intB) != EOF) {
		if (intA==0 && intB==0)		exit(0);
		intToCharArray(intA, numA);
		intToCharArray(intB, numB);	

		int p, carry=0, numCarries=0;

		for (p=0; p<10; p++) {
			if (numA[p] + numB[p] + carry > 9) {
				carry = 1; 
				numCarries++;
			}
			else carry = 0;
		}

		switch (numCarries) {
			case 0: printf("No carry operation.\n"); break;
			case 1: printf("1 carry operation.\n"); break;
			default: printf("%d carry operations.\n", numCarries); break;
		}
			
	}
	exit(0);
}

void intToCharArray(int input, char *output) {
	int p;
	for (p=0; p<10; p++) {
		output[p] = input % 10;
		input /= 10;
	}
}
