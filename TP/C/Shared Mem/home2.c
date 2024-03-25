/*
3 Prozesse, 2 Child, der Elternprozess gibt immer aus
1Child liest was in sharedmem und if zahl ungerade erssetzen mit nechste zahl
2Child liest was in sharedmem und if zahl gerade erssetzen mit nechste zahl
*/
#include <stdio.h>
#include <sys/shm.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <signal.h>
#include <unistd.h>

int *mem;
pid_t pid1, pid2; //! pls help me

void parentHandler()
{
    printf("Parent Handler\n");
    printf("%d\n", *mem);
    kill(pid1, SIGUSR2);
    kill(pid2, SIGUSR2);
}

void child1Handler()
{
    printf("%d", *mem);
    printf("Child Handler 1\n");
    if (*mem % 2 != 0)
    {
        *mem = ++*mem;
        kill(getppid(), SIGUSR1);
    }
}

void child2Handler()
{
    printf("%d", *mem);
    printf("Child Handler 2\n");
    if (*mem % 2 == 0)
    {
        *mem += 1;
        kill(getppid(), SIGUSR1);
    }
}

int main()
{

    int shmID;
    key_t shmKey = 123;

    shmID = shmget(shmKey, sizeof(int), IPC_CREAT | 0600);
    if (shmID == -1)
    {
        perror("shmget");
        return 1;
    }

    mem = shmat(shmID, NULL, 0);
    if (mem == (void *)-1)
    {
        perror("shmat");
        return 1;
    }

    *mem = 0;

    pid1 = fork();
    if (pid1 == -1)
    {
        perror("frok");
        return 1;
    }

    if (pid1) //? dad
    {
        pid2 = fork();
        if (pid2 == -1)
        {
            perror("fork2");
            return 1;
        }

        if (pid2) //? dad
        {

            signal(SIGUSR1, parentHandler);
            kill(getpid(), SIGUSR1);

            while (*mem < 100)
            {
            }

            wait(NULL);
            shmctl(shmID, IPC_RMID, 0);
        }
        else
        { //? kid1

            signal(SIGUSR2, child1Handler);
            while (*mem < 100)
            {
            } //! idk what im doing

            if (shmdt(mem) == -1)
            {
                perror("shmdt");
                return 1;
            }
        }
    }
    else //? kid2
    {

        signal(SIGUSR2, child2Handler);
        while (*mem < 100)
        {
        }

        if (shmdt(mem) == -1)
        {
            perror("shmdt");
            return 1;
        }
    }
}