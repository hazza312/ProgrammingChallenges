/*

hk, Exercise 10082 (P.66), WERTYU
JULY 2017

g++ wertyu.cpp-lm -lcrypt -O2 -std=c++11 -pipe -DONLINE_JUDGE

*/

#include <iostream>

using namespace std;

const unsigned char LUT[128] = {
	// ascii dec 00-0F
	0, 0, 0, 0, 0, 0, 0, 0, 0, 0,'\n', 0, 0, 0, 0, 0,
	// ascii dec 10-1F
	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
	// ascii dec 20-2F
	' ', 0, 0, 0, 0, 0, 0,';', 0, 0, 0, 0, 'M','0', ',', '.',
	// ascii dec 30-3F
	'9','`','1','2','3','4','5','6','7','8','9', 'L', 0, '-', 0, 0,
	// ascii dec 40-4F
	0, 0,'V','X','S','W','D','F','G','U','H','J','K','N','B','I',
	// ascii dec 50-5F
	'O','\t','E','A','R','Y','C','Q','Z','T', 0,'P',']','[', 0, 0,
	// ascii dec 60-6F
	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
	// ascii dec 70-7F
	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
			
};

int main() {
	unsigned char c;
	while (cin >> noskipws >> c) cout << LUT[c];	
	exit(0);
}
