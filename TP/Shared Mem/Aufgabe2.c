#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <unistd.h>
#include <time.h>
#include <string.h>
#include <signal.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/shm.h>

int childWait = 1;
int vatherWait = 1;

void sigChildHandler(int sig)
{
    childWait = 0;
}

void sigVatherHandler(int sig)
{
    vatherWait = 0;
}

int main()
{
    key_t key = 809;

    int shmid = shmget(key, sizeof(int), IPC_CREAT | 0666);
    if (shmid == -1)
    {
        printf("Ein Fehler ist beim Erstellen des Shared Memory Segments aufgetreten!");
        exit(1);
    }

    int pid = fork();
    if (pid == 0) // Kinderprozess
    {
        signal(SIGUSR1, sigChildHandler); // Regestriere das Startsignal für den Kindprozess

        int *shmaddr = shmat(shmid, NULL, 0);
        if (shmaddr == (int *)-1)
        {
            printf("Ein Fehler ist beim Anhängen des Shared Memory Segments aufgetreten!");
            exit(1);
        }

        for (int i = 0; i < 100; i++)
        {
            childWait = 1;
            while (childWait)
                ;
            *shmaddr += 1;
            printf("%d\n", *shmaddr);

            kill(getppid(), SIGUSR1); // Sende Startsignal an den Vaterprozess
        }

        if (shmdt(shmaddr) == -1)
        {
            printf("Ein Fehler ist beim Lösen des Shared Memory Segments aufgetreten!");
            exit(1);
        }

        return 0;
    }
    else if (pid > 0) // Vaterprozess
    {
        signal(SIGUSR1, sigVatherHandler); // Regestriere das Startsignal für den Vaterprozess

        int *shmaddr = shmat(shmid, NULL, 0);
        if (shmaddr == (int *)-1)
        {
            printf("Ein Fehler ist beim Anhängen des Shared Memory Segments aufgetreten!");
            exit(1);
        }

        for (int i = 0; i < 100; i++)
        {
            vatherWait = 1;
            *shmaddr += 1;
            printf("%d\n", *shmaddr);
            kill(pid, SIGUSR1); // Sende Startsignal an den Kindprozess
            while (vatherWait)
                ;
        }

        if (shmdt(shmaddr) == -1)
        {
            printf("Ein Fehler ist beim Lösen des Shared Memory Segments aufgetreten!");
            exit(1);
        }

        if (shmctl(shmid, IPC_RMID, NULL) == -1)
        {
            printf("Ein Fehler ist beim Löschen des Shared Memory Segments aufgetreten!");
            exit(1);
        }
        return 0;
    }
    else
    {
        printf("Ein Fehler ist beim Fork aufgetreten!");
    }

    return 0;
}