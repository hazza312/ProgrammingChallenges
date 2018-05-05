/*

Exercise 272, TEX Quotes
hk, JULY 2017

g++ tex.cpp -lm -lcrypt -O2 -std=c++11 -pipe -DONLINE_JUDGE

*/

#include <iostream>

using namespace std;

int main() {
	bool startQuote = true;
	char c;

	while (cin >> noskipws >> c) {
		if (c == '"') 	{
			startQuote ? cout << "``" : cout << "''"; 
			startQuote = !startQuote;
		}

		else	{		cout << c;}
	}
	

	exit(0);
}
