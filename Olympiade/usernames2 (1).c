// NOTE: it is recommended to use this even if you don't understand the following code.

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAXS 30
#define MAXN 100000

char S[MAXS], T[MAXS];
int N, i;
char U[MAXN][MAXS];

int main()
{
    // uncomment the two following lines if you want to read/write from files
    freopen("input0.txt", "r", stdin);
    freopen("output.txt", "w", stdout);

    assert(1 == scanf("%s", S));

    assert(1 == scanf("%d", &N));

    for (i = 0; i < N; ++i)
        assert(1 == scanf("%s", U[i]));

    T[0] = 0;

    // INSERT YOUR CODE HERE
    printf("%s", S);
    int checkeuqal = 0;

    for (int i = 0; i < N; ++i)
    {
        int equal = strcmp(S, U[i]);
        if (!equal)
        {
            checkeuqal++;
        }
    }
    if (checkeuqal == N)
    {
        printf("%s", S);
    }

    for (int i = 0; i < N; i++)
    {
        }

    printf("%s\n", T);

    return 0;
}
