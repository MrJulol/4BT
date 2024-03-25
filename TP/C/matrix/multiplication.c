#pragma GCC optimize("Ofast,inline")
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

int **matrixA, **matrixB, **resultMatrix;

struct args
{
    int *len;
    FILE *fp;
};
void *getSizeOfMatrix(void *arg);
void matrix_multiplication(int l, int m, int n);
void printCSV(int l, int n);

int main()
{
    // Name of the files
    char *fileA = "MatrixB.csv";
    char *fileB = "MatrixC.csv";

    // Structs to give threads args with void*
    struct args *lenOfA = malloc(sizeof(struct args));
    struct args *lenOfB = malloc(sizeof(struct args));
    lenOfA->fp = fopen(fileA, "r");
    lenOfB->fp = fopen(fileB, "r");
    if (lenOfA->fp == NULL || lenOfB->fp == NULL) // Handle Opening Error
    {
        free(lenOfA);
        free(lenOfB);
        perror("Opening Files");
        exit(1);
    }

    pthread_t threads;
    pthread_create(&threads, NULL, getSizeOfMatrix, lenOfA);
    getSizeOfMatrix(lenOfB);
    pthread_join(threads, NULL);

    printf("Len1: %d\n", lenOfA->len[0]);
    printf("Len2: %d\n", lenOfB->len[1]);

    if (lenOfA->len[0] != lenOfB->len[1])
    {
        free(lenOfA->len);
        free(lenOfA);
        free(lenOfB->len);
        free(lenOfB);
        perror("Matrix not compatible");
        exit(1);
    }

    printf("Matrix compatible!: \n\n");

    FILE *fp1 = fopen(fileA, "r");
    FILE *fp2 = fopen(fileB, "r");

    int l = lenOfA->len[1];
    int m = lenOfA->len[0];
    int n = lenOfB->len[0];

    // TODO: The next Lines of code could be put in a separate func as they do the same, just different values
    // TODO: Could also be possible to put into separate thread to potentially optimize

    matrixA = malloc(lenOfA->len[1] * sizeof(int *));
    for (int i = 0; i < lenOfA->len[1]; ++i)
    {
        matrixA[i] = malloc(lenOfA->len[0] * sizeof(int *));
        for (int j = 0; j < lenOfA->len[0]; ++j)
        {
            fscanf(fp1, "%d;", &matrixA[i][j]);
        }
        fscanf(fp1, "\n");
    }

    matrixB = malloc(lenOfB->len[1] * sizeof(int *));
    for (int i = 0; i < lenOfB->len[1]; ++i)
    {
        matrixB[i] = malloc(lenOfB->len[0] * sizeof(int *));
        for (int j = 0; j < lenOfB->len[0]; ++j)
        {
            fscanf(fp2, "%d;", &matrixB[i][j]);
        }
        fscanf(fp2, "\n");
    }
    matrix_multiplication(l, m, n);

    printCSV(l, n);

    return EXIT_SUCCESS;
}

void *getSizeOfMatrix(void *arg)
{
    struct args *myArgs = (struct args *)arg;
    myArgs->len = malloc(2 * sizeof(int));

    myArgs->len[0] = 1;
    myArgs->len[1] = 0;

    char lastReadCharFromFile;
    int EOL = 0;
    while ((lastReadCharFromFile = fgetc(myArgs->fp)) != EOF)
    {
        if (!EOL && lastReadCharFromFile == ';')
        {
            myArgs->len[0]++;
        }
        else if (lastReadCharFromFile == '\n')
        {
            EOL = 1;
            myArgs->len[1]++;
        }
    }
    fclose(myArgs->fp);
    return NULL;
}
void matrix_multiplication(int l, int m, int n)
{
    resultMatrix = malloc(l * sizeof(int *));
    for (int i = 0; i < l; i++)
    {
        resultMatrix[i] = malloc(n * sizeof(int));
        for (int j = 0; j < n; j++)
        {
            resultMatrix[i][j] = 0;
            for (int k = 0; k < m; k++)
            {
                resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
            }
        }
    }
}

void printCSV(int l, int n)
{
    FILE *fp = fopen("result.csv", "w");
    for (int i = 0; i < l; i++)
    {
        for (int j = 0; j < n; j++)
        {
            fprintf(fp, "%d", resultMatrix[i][j]);
            if (j != n - 1)
            {
                fprintf(fp, ";");
            }
        }
        fprintf(fp, "\n");
    }
    fclose(fp);
}
