#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>

int resource = 0;

sem_t sem1;

void *threadfunc(void *arg)
{
    sem_wait(&sem1);
    printf("%d", resource++);
    sem_post(&sem1);
    pthread_exit(NULL);
}

int main()
{
    pthread_t threads[25];

    sem_init(&sem1, 0, 2);

    for (int i = 0; i < 5; i++)
    {
        pthread_create(threads[i], NULL, threadfunc, NULL);
    }
    for (int i = 0; i < 5; i++)
    {
        pthread_join(threads[i], NULL);
    }
    printf("\n");
    return EXIT_SUCCESS;
}