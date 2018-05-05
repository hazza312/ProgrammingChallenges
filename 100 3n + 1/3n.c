/*

Exercise 100 (P.15), The 3n+1 Problem
hk, JULY 2017

gcc 3n.c -lm -lcrypt -O2 -pipe -ansi -DONLINE_JUDGE

*/

#include <stdlib.h>
#include <stdio.h>


int main() {

	int pstart, pend, start, end, i, k, count, max;

	while (scanf("%d %d", &pstart, &pend) != EOF) {
		if (pstart > pend) {end=pstart; start = pend;}
		else {start = pstart; end=pend;}

		for (i=start, max=0; i<=end; i++) {
			for (k=i, count=1; k!=1; count++) {
				k = (k % 2 == 0) ? k/2 : 3*k + 1;
			}
			max = (count > max) ? count : max;
		}
		printf("%d %d %d\n", pstart, pend, max);
	}

	exit(0);
}

