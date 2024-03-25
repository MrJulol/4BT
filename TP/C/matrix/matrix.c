#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#define MaxArraySize 1000
#define NumOfThreads 4

struct dataForThread
{
    int (*matrixA)[MaxArraySize];
    int (*matrixB)[MaxArraySize];
    int (*resultMatrix)[MaxArraySize];
    int rowsA, collumnsA, rowsB, collumnsB;
    int startRow, endRow;
};

void readFromCSV(const char *file, int matrix[MaxArraySize][MaxArraySize], int *rows, int *collumns)
{
    printf("TEST");
    FILE *fp = fopen(file, "r");
    if (fp == NULL)
    {
        perror("Error: Can`t open the CSV-File");
        exit(1);
    }
    int rowNum = 0;
    int collumnNum = 0;
    int readValue;

    while (rowNum < MaxArraySize && fscanf(fp, "%d", &readValue) != EOF)
    {
        if (collumnNum < MaxArraySize)
        {
            matrix[rowNum][collumnNum] = readValue;
            collumnNum++;
        }

        int nextSymbol = fgetc(fp);
        if (nextSymbol == NULL || (int)nextSymbol == EOF) // EOF = -1 (stdio.h) so if cast to int makes comparison possible? for NULL? IDK
        {
            if (collumnNum > *collumns)
            {
                *collumns = collumnNum;
            }
            break; // Reached the End of the File //! EOF
        }
        else if (nextSymbol == "\n")
        {
            if (collumnNum > *collumns)
            {
                *collumns = collumnNum;
            }
            collumnNum++;
            rowNum++;
        }
    }
    *rows = rowNum;

    fclose(fp);
}

void writeToCSVFile(const char *file, int matrix[MaxArraySize][MaxArraySize], int rows, int collumns)
{
    FILE *fp = fopen(file, "w");
    if (fp == NULL)
    {
        perror("Error: Can`t open the CSV-File");
        exit(1);
    }
    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < collumns; j++)
        {
            fprintf(fp, "%d", matrix[i][j]);
            if (j < collumns - 1)
            {
                fprintf(fp, ",");
            }
        }
        fprintf(fp, "\n");
    }
    fclose(fp);
}

void *matrixMultiplyThread(void *args)
{
    struct dataForThread *arg = (struct dataForThread *)args;

    for (int i = arg->startRow; i < arg->endRow; i++)
    {
        for (int j = 0; j < arg->collumnsB; j++)
        {
            arg->resultMatrix[i][j] = 0;
            for (int k = 0; k < arg->collumnsA; k++)
            {
                arg->resultMatrix[i][j] += arg->matrixA[i][k] * arg->matrixB[k][j];
            }
        }
    }

    return NULL;
}

int main()
{
    printf("TEST MAIN 1\n");
    int matrixA[MaxArraySize][MaxArraySize];
    int matrixB[MaxArraySize][MaxArraySize];
    //!!!! THIS SHIT BREAKES EVERYTHING LMAO
    // int matrixC[MaxArraySize][MaxArraySize];
    //!!!! THIS SHIT BREAKES EVERYTHING LMAO
    // int resultMatrix[MaxArraySize][MaxArraySize];
    int rowsMatrixA, collumnsMatrixA, rowsMatrixB, collumnsMatrixB;

    // ? The following shit is just stupid i think
    // pthread_t readMatrixThread1, readMatrixThread2;
    // pthread_create(&readMatrixThread1, NULL, readFromCSV, "MatrixA.csv", matrixA, &rowsMatrixA, collumnsMatrixA, NULL);
    //! Here i need to make a Struct Args for the parameters for the readCSV Function with all its parameters and
    //! Give it as void*. In the func i need to cast it back to the right format and then with -> use the right parameter inside the struct

    readFromCSV("MatrixA.csv", matrixA, &rowsMatrixA, &collumnsMatrixA);
    readFromCSV("MatrixB.csv", matrixB, &rowsMatrixB, &collumnsMatrixB);

    for (int i = 0; i < MaxArraySize; i++)
    {
        for (int j = 0; j < MaxArraySize; j++)
        {
            printf("%d ", matrixA[i][j]);
        }
        printf("\n");
    }

    // TODO: Theoretically i have the CSV-DATA now as 2 Arrays... hopefully

    //! Check n of A & B

    if (collumnsMatrixA != rowsMatrixB)
    {
        perror("The matrix aren`t the right size: ");
        exit(1);
    }
    printf("TEST MAIN 2");

    // pthread_t threads[NumOfThreads];
    // struct dataForThread threadArgs[NumOfThreads];

    // int rowsPerThread = rowsMatrixA / NumOfThreads;

    // for (int i = 0; i < NumOfThreads; i++)
    // {
    //     threadArgs[i].matrixA = matrixA;
    //     threadArgs[i].matrixB = matrixB;
    //     threadArgs[i].resultMatrix = resultMatrix;
    //     threadArgs[i].rowsA = rowsMatrixA;
    //     threadArgs[i].collumnsA = collumnsMatrixA;
    //     threadArgs[i].rowsB = rowsMatrixB;
    //     threadArgs[i].collumnsB = collumnsMatrixB;
    //     threadArgs[i].startRow = i * rowsPerThread;
    //     threadArgs[i].endRow = (i == NumOfThreads - 1) ? rowsMatrixA : (i + 1) * rowsPerThread;

    //     pthread_create(&threads[i], NULL, matrixMultiplyThread, &threadArgs[i]);
    // }

    // for (int i = 0; i < NumOfThreads; i++)
    // {
    //     pthread_join(threads[i], NULL);
    // }

    // writeToCSVFile("Ergebnis.csv", resultMatrix, rowsMatrixA, collumnsMatrixB);

    return EXIT_SUCCESS;
}