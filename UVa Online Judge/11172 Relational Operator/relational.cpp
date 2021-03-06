/*

Exercise 11172, Relational Operator
hk, JULY 2017

g++ relational.cpp -lm -lcrypt -O2 -std=c++11 -pipe -DONLINE_JUDGE

*/

#include <iostream>

using namespace std;

int main() {

	long n, a, b;
	cin >> n;

	for (int i=0; i< n; i++) {
		cin >> b >> a;
		if (a>b) {cout << "<\n"; continue;}
		else if (a<b) {cout << ">\n"; continue;}
		else {cout << "=\n";}
	}		

	exit(0);
}
