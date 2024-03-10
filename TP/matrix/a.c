#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#define MAX_SIZE 5000

typedef struct
{
    int (*matrixA)[MAX_SIZE];
    int (*matrixB)[MAX_SIZE];
    int (*resultMatrix)[MAX_SIZE];
    int rowsA, colsA, rowsB, colsB;
    int startRow, endRow;
} MatrixMultiplyArgs;

void *matrixMultiplyThread(void *args)
{
    MatrixMultiplyArgs *arg = (MatrixMultiplyArgs *)args;

    for (int i = arg->startRow; i < arg->endRow; i++)
    {
        for (int j = 0; j < arg->colsB; j++)
        {
            arg->resultMatrix[i][j] = 0;
            for (int k = 0; k < arg->colsA; k++)
            {
                arg->resultMatrix[i][j] += arg->matrixA[i][k] * arg->matrixB[k][j];
            }
        }
    }

    return NULL;
}

void readCSV(const char *filename, int matrix[5000][5000], int *rows, int *cols)
{
    FILE *file = fopen(filename, "r");
    if (file == NULL)
    {
        printf("Fehler beim Öffnen der Datei: %s\n", filename);
        exit(1);
    }

    int row = 0;
    int col = 0;
    int value;

    while (row < 5000 && fscanf(file, "%d", &value) != EOF)
    {
        if (col < 5000)
        {
            matrix[row][col] = value;
            col++;
        }

        int nextChar = fgetc(file);
        if (nextChar == '\n')
        {
            if (col > *cols)
            {
                *cols = col;
            }
            col = 0;
            row++;
        }
        else if (nextChar == EOF)
        {
            if (col > *cols)
            {
                *cols = col;
            }
            break; // Dateiende erreicht
        }
    }

    *rows = row;

    fclose(file);
}

void writeMatrixToCSV(const char *filename, int matrix[5000][5000], int rows, int cols)
{
    FILE *file = fopen(filename, "w");
    if (file == NULL)
    {
        printf("Fehler beim Öffnen der Datei: %s\n", filename);
        exit(1);
    }

    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < cols; j++)
        {
            fprintf(file, "%d", matrix[i][j]);
            if (j < cols - 1)
            {
                fprintf(file, ",");
            }
        }
        fprintf(file, "\n");
    }

    fclose(file);
}

int main()
{
    int matrixA[MAX_SIZE][MAX_SIZE];
    int matrixB[MAX_SIZE][MAX_SIZE];
    int rowsA, colsA, rowsB, colsB;

    pthread_attr_t atr;
    size_t stksize;
    pthread_attr_init(&atr);
    pthread_attr_getstacksize(&atr, &stksize);
    printf("Aktuelle Stack-Größe: %lu\n", stksize);

    stksize = 320000034; // Neue Stack-Größe (z.B. 320 MB)
    pthread_attr_setstacksize(&atr, stksize);
    pthread_attr_getstacksize(&atr, &stksize);
    printf("Neue Stack-Größe: %lu\n", stksize);

    readCSV("MatrixA.csv", matrixA, &rowsA, &colsA);
    readCSV("MatrixB.csv", matrixB, &rowsB, &colsB);

    if (colsA != rowsB)
    {
        printf("Die Matrizen können nicht multipliziert werden. Anzahl der Spalten von MatrixA muss der Anzahl der Zeilen von MatrixB entsprechen.\n");
        return 1;
    }

    int resultMatrix[MAX_SIZE][MAX_SIZE];

    // Hier teilen wir die Arbeit auf mehrere Threads auf
    int numThreads = 4; // Sie können die Anzahl der Threads anpassen
    pthread_t threads[numThreads];
    MatrixMultiplyArgs threadArgs[numThreads];

    int rowsPerThread = rowsA / numThreads;
    for (int i = 0; i < numThreads; i++)
    {
        threadArgs[i].matrixA = matrixA;
        threadArgs[i].matrixB = matrixB;
        threadArgs[i].resultMatrix = resultMatrix;
        threadArgs[i].rowsA = rowsA;
        threadArgs[i].colsA = colsA;
        threadArgs[i].rowsB = rowsB;
        threadArgs[i].colsB = colsB;
        threadArgs[i].startRow = i * rowsPerThread;
        threadArgs[i].endRow = (i == numThreads - 1) ? rowsA : (i + 1) * rowsPerThread;

        pthread_create(&threads[i], NULL, matrixMultiplyThread, &threadArgs[i]);
    }

    for (int i = 0; i < numThreads; i++)
    {
        pthread_join(threads[i], NULL);
    }

    printf("MatrixC (Ergebnis der Multiplikation) wird in die Datei MatrixC.csv geschrieben.\n");
    writeMatrixToCSV("MatrixC.csv", resultMatrix, rowsA, colsB);

    return 0;
}