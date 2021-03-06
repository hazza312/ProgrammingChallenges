/*

Exercise 10127 (P.122), Ones
hk, JULY 2017

gcc ones.c -lm -lcrypt -O2 -pipe -ansi -DONLINE_JUDGE

*/

#include <stdlib.h>
#include <stdio.h>

int main() {
	int n, c;
	unsigned long long t;

	while (scanf("%d", &n) != EOF) {
		for (t=1, c=1; c<=20 ; t=t*10+1, c++) {
			if (t % n == 0) break;
		}
		printf("%d\n", c);
	}
	exit(0);
}
