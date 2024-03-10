#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

pthread_mutex_t mutex, mutex2;
pthread_cond_t cond1, cond2;
int thing1 = 5;
int thing2 = 3;

void *threadfunc(void *args)
{
    int my1, my2;
    pthread_mutex_trylock(&mutex2);
    my1 = thing2;
    pthread_mutex_trylock(&mutex);
    my2 = thing1;
    thing1 = my1 + my2;
    printf("%d\n", thing1);
    pthread_mutex_unlock(&mutex);
    pthread_mutex_unlock(&mutex2);

    return NULL;
}
void *threadfunc2(void *args)
{
    int my1, my2;
    pthread_mutex_trylock(&mutex);
    my1 = thing1;
    pthread_mutex_trylock(&mutex2);
    my2 = thing2;
    thing2 = my1 + my2;
    printf("%d\n", thing2);
    pthread_mutex_unlock(&mutex);
    pthread_mutex_unlock(&mutex2);

    return NULL;
}

int main()
{

    pthread_mutex_init(&mutex, NULL);
    pthread_mutex_init(&mutex2, NULL);
    pthread_cond_init(&cond1, NULL);
    pthread_cond_init(&cond2, NULL);

    pthread_t T1, T2;
    pthread_create(&T1, NULL, threadfunc, NULL);
    pthread_create(&T2, NULL, threadfunc2, NULL);

    pthread_join(T1, NULL);
    pthread_join(T2, NULL);

    return EXIT_SUCCESS;
}