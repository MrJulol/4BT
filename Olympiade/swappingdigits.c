// NOTE: it is recommended to use this even if you don't understand the following code.

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

#define MAXS 100001

int T, ans, test;
char N[MAXS];

int main()
{
    // uncomment the two following lines if you want to read/write from files
    freopen("input.txt", "r", stdin);
    // freopen("output.txt", "w", stdout);

    assert(1 == scanf("%d", &T));
    for (test = 1; test <= T; ++test)
    {
        assert(1 == scanf("%s", N));

        ans = 0;

        
        

        printf("%d\n", ans);
    }

    return 0;
}
