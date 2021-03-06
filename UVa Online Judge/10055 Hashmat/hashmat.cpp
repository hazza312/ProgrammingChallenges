/*

Exercise 10055 (P.66), Hashmat the Brave Warrior
hk, JULY 2017

g++ hashmat.cpp -lm -lcrypt -O2 -std=c++11 -pipe -DONLINE_JUDGE

*/

#include <iostream>

using namespace std;

int main() {
	long a, b; 
	while (cin >> a >> b) cout << ((a-b < 0) ? b-a : a-b) << "\n";	

	exit(0);
}
