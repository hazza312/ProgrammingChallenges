/*

Exercise 706 (P.18), LCD Display
hk, JULY 2017

gcc lcd.c -lm -lcrypt -O2 -pipe -ansi -DONLINE_JUDGE

*/

#include <stdlib.h>
#include <stdio.h>

#define HORIZONTAL 	'-'
#define VERTICAL 	'|'
#define BLANK 		' '

struct Digit {
	unsigned char a, b, c, d, e, f, g;
};

const struct Digit digits[10] = { 
	{1,1,1,1,1,1,0}, 
	{0,1,1,0,0,0,0}, 
	{1,1,0,1,1,0,1}, 
	{1,1,1,1,0,0,1}, 
	{0,1,1,0,0,1,1}, 
	{1,0,1,1,0,1,1}, 
	{1,0,1,1,1,1,1}, 
	{1,1,1,0,0,0,0}, 
	{1,1,1,1,1,1,1}, 
	{1,1,1,1,0,1,1}  
};

int main() {
	
	int s, width, height, mid, row, i;
	char charArray[10] = "";

	while (scanf("%d", &s) != EOF) {
		if (s==0) 	exit(0);
		scanf("%s", charArray);
		width = s + 2;
		height = 2*s + 3;
		mid = (height + 1) /2;
	
		for (row=1; row<=height; row++) {
			for (i=0; charArray[i]!=0; i++) {
				struct Digit current = digits[charArray[i] - '0'];

				if (row==1) 			horizontal(current.a, width);	
				else if (row<mid) 		walled(current.f, current.b, width);				
				else if (row==mid) 		horizontal(current.g, width);
				else if (row<height) 	walled(current.e, current.c, width);
				else if (row==height)	horizontal(current.d, width);
	
				if (charArray[i+1]!=0) 	putc(' ', stdout);
			}
			putc('\n', stdout);
		}
		putc('\n', stdout);
	}

	exit(0);
}

void repeatPrint(char character, int repetitions) {
	int i;
	for (i=0; i<repetitions; i++) putc(character, stdout);
}

void horizontal(int solid, int width) {
	putc(BLANK, stdout);
	repeatPrint(solid ? HORIZONTAL : BLANK, width-2);
	putc(BLANK, stdout);
};

void walled(int left, int right, int width) {
	putc(left ? VERTICAL : BLANK, stdout);
	repeatPrint(BLANK, width - 2);
	putc(right ? VERTICAL : BLANK, stdout);
}
