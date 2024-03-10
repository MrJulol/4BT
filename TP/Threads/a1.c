#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>

sem_t semaphore1, semaphore2, semaphore3;

void *thread1func(void *arg)
{
    for (int i = 0; i < 10; i++)
    {
        sem_wait(&semaphore1);
        printf("A");
        sem_post(&semaphore2);
    }
    pthread_exit(NULL);
}
void *thread2func(void *arg)
{
    for (int i = 0; i < 10; i++)
    {
        sem_wait(&semaphore2);
        printf("B");
        sem_post(&semaphore3);
    }
    pthread_exit(NULL);
}
void *thread3func(void *arg)
{
    for (int i = 0; i < 10; i++)
    {
        sem_wait(&semaphore3);
        printf("C-->");
        sem_post(&semaphore1);
    }
    pthread_exit(NULL);
}

int main()
{
    pthread_t thread1, thread2, thread3;
    sem_init(&semaphore1, 0, 1);
    sem_init(&semaphore2, 0, 0);
    sem_init(&semaphore3, 0, 0);
    pthread_create(&thread1, NULL, thread1func, NULL);
    pthread_create(&thread2, NULL, thread2func, NULL);
    pthread_create(&thread3, NULL, thread3func, NULL);
    pthread_join(thread1, NULL);
    pthread_join(thread2, NULL);
    pthread_join(thread3, NULL);
    printf("\n");
    return EXIT_SUCCESS;
}