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

void dostuff(int number)
{
    int numbers[MAXS];
    strcpy(T, S);
    int len = 0;
    while (T[len] != '\0')
    {
        len++;
    }
    int comparethis = number % 10;
    int numcouter = 0;
    while (comparethis >= 10)
    {
        numcouter++;
        comparethis %= 10;
    }

    printf("%d", numcouter);

    T[len] = (char)number + 48;

    int checkeuqal = 0;

    for (int i = 0; i < N; ++i)
    {
        int equal = strcmp(T, U[i]);
        if (equal)
        {
            checkeuqal++;
        }
        else if (!equal)
        {
            dostuff(++number);
        }
    }
    if (checkeuqal == N)
    {
        printf("%s", T);
    }
}

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

    int checkeuqal = 0;
    int secondequal = 0;

    for (int i = 0; i < N; ++i)
    {
        int equal = strcmp(S, U[i]);
        if (equal)
        {
            checkeuqal++;
        }
        else if (!equal)
        {
            dostuff(1);
        }
    }
    if (checkeuqal == N)
    {
        printf("%s", S);
    }

    //! CASE DONE ALL NAMES NOT EQUAL NAME

    return 0;
}
