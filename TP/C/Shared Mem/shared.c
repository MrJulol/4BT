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

int main()
{
    int *shared_data = malloc(100 * sizeof(int));
    key_t key = 123;
    int memID = shmget(key, sizeof(shared_data), IPC_CREAT | 0666);
    if (memID == -1)
    {
        perror("shmget");
        exit(1);
    }
    int *mem = malloc(100 * sizeof(int));
    mem = shmat(memID, NULL, 0);
    if (mem == (void *)-1)
    {
        perror("shmat");
        exit(1);
    }
    *mem = 1;

    printf("%d\n", *mem);

    if (shmdt(mem) == -1)
    {
        perror("shmdt");
        exit(1);
    }
}