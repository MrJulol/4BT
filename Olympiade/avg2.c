// NOTE: it is recommended to use this even if you don't understand the following code.

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

#define MAXN 200000

int N, i;
long long C, K;
long long P[MAXN];

int main()
{
    // uncomment the two following lines if you want to read/write from files
    freopen("input1.txt", "r", stdin);
    // freopen("output.txt", "w", stdout);

    assert(2 == scanf("%d%lld", &N, &K));

    for (i = 0; i < N; ++i)
        assert(1 == scanf("%lld", &P[i]));

    C = 0;

    for (int i = 0; i < N; i++)
    {
        printf("%lld ", P[i]);
    }

    printf("%lld\n", C);

    return 0;
}
