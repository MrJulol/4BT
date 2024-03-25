if (collumnsMatrixA != rowsMatrixB)
    // {
    //     perror("The matrix aren`t the right size: ");
    //     exit(1);
    // }

    // int resultMatrix[MaxArraySize][MaxArraySize] = {0};

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

    // writeToCSVFile