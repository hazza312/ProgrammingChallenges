#include <math.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <assert.h>
#include <limits.h>
#include <stdbool.h>

int main(){
    int t; 
    scanf("%d",&t);
    for(int a0 = 0; a0 < t; a0++){
        int n; 
        scanf("%d",&n);
        long int limit = n - 1;
        long int sum_threes = 3 * (((limit/3) * ((limit/3) + 1)) / 2);
        long int sum_fives = 5 * (((limit/5) * ((limit/5) + 1)) / 2);
        long int sum_fifteens = 15 * (((limit/15) * ((limit/15) + 1)) / 2);
        printf("%ld\n", sum_threes + sum_fives - sum_fifteens);
    }
    return 0;
}