/*

Exercise 10189 (P.16), Minesweeper
hk, JULY 2017

gcc minesweeper.c -lm -lcrypt -O2 -pipe -ansi -DONLINE_JUDGE

*/

#include <stdlib.h>
#include <stdio.h>

#define MINE '*'
#define BLANK '.'

int main() {
	int h, w, neighbours, fieldCount=1;
	char *field;

	while (scanf("%d %d", &h, &w) != EOF) {
		if (h==0) 		exit(0);	
		h+=2; w+=2;		
		field = (char *) calloc(h * w, sizeof(char));

		int k;

		for (k=w+1; k <= w*(h-1); k++) {
			scanf(" %c", &field[k]);
			if ((k+2) % w == 0) k+=2;
		}

		if (fieldCount > 1) {printf("\n");}
 		printf("Field #%d:\n", fieldCount);

		for (k=w+1; k <= w*(h-1); k++) {
			if (field[k] == MINE) 	putc(MINE, stdout);
			
			else {
				neighbours = 0;
				neighbours += (field[k-w-1]	== MINE) ? 1:0;
				neighbours += (field[k-w]	== MINE) ? 1:0;
				neighbours += (field[k-w+1]	== MINE) ? 1:0;
				neighbours += (field[k-1]	== MINE) ? 1:0;
				neighbours += (field[k+1]	== MINE) ? 1:0;
				neighbours += (field[k+w-1]	== MINE) ? 1:0;
				neighbours += (field[k+w]	== MINE) ? 1:0;
				neighbours += (field[k+w+1]	== MINE) ? 1:0;

				printf("%d", neighbours);
			}
			if ((k+2) % w == 0) {k+=2; printf("\n");}
		}

		free(field);
		fieldCount++;
	
	}

	exit(0);
}
