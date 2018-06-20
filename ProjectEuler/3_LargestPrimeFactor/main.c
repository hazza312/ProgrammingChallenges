#include <math.h>
#include <stdlib.h>
#include <stdio.h>
#include <limits.h>

#define MAX_PRIMES 78498

int primes[MAX_PRIMES];
int* last_prime = primes;



void gen_primes(int last) {
    for (int i=2; i <= last; i++) {
        for (int j=2; j<= (floor(sqrt(i))); j++) {

            // goto not best practice, but needing speed here.
            // ('check prime' should be a function by itself..
            if (i % j == 0 )     goto consider_next;
        }

        *(last_prime++) = i;      

consider_next: ;
    }
}

long largest_pfactor(long n) {
    int* prime = primes;
    ldiv_t result;
    int largest_possible = ceil(sqrt(n));

    while (n != 1) {
        result = ldiv(n, *prime);

        if (result.rem == 0)                {n = result.quot;}        
        else                                {prime++;}
        if (*(prime) > largest_possible || *prime == 0)  return n;
    }
    return *prime;
}


int main(){
    long int test_cases[10];
    long int max = LONG_MIN;

    int num_test_cases;
    scanf("%d",&num_test_cases);

    for(int a0 = 0; a0 < num_test_cases; a0++){ 
        scanf("%ld", &test_cases[a0]);
        if (test_cases[a0] > max)   max = test_cases[a0];
    }

    gen_primes(floor(sqrt(max)));

    for (int i=0; i < num_test_cases; i++) {
        printf("%ld\n", largest_pfactor(test_cases[i]));
    }

    return 0;
}