// NOTE: it is recommended to use this even if you don't understand the following code.

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

#define MAXN 100000

int N, i;
int S[MAXN];
long long A;

int main()
{
    // uncomment the two following lines if you want to read/write from files
    freopen("input0.txt", "r", stdin);
    freopen("output.txt", "w", stdout);

    assert(1 == scanf("%d", &N));

    for (i = 0; i < N; ++i)
        assert(1 == scanf("%d", &S[i]));

    A = 0;

    int arr[MAXN][1];

    printf("%lld\n", A);

    return 0;
}
