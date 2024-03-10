#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <unistd.h>

pthread_mutex_t forks[5];
int didThePhilosopherEat[] = {0, 0, 0, 0, 0};
int howManyAteThatRound = 0;

void takeFork(int philo)
{
    pthread_mutex_lock(&forks[philo]);
    pthread_mutex_lock(&forks[philo + 1]);
}
void eat(int philo)
{
}
void releaseFork(int philo)
{
    pthread_mutex_unlock(&forks[philo]);
    pthread_mutex_unlock(&forks[philo + 1]);
}

void *philofunc(void *num)
{
    int *philosopher = (int *)num;
    while (1)
    {
        if (howManyAteThatRound >= 1)
        {
            printf("ALL ATE\n");
            break;
        }
        printf("Philosopher %d is not eating!\n", *philosopher);
        takeFork(*philosopher);
        printf("Philosopher %d took the forks!\n", *philosopher);
        eat(*philosopher);
        printf("Philosopher %d ate!\n", *philosopher);
        howManyAteThatRound++;
        didThePhilosopherEat[*philosopher]++;
        releaseFork(*philosopher);
        printf("Philosopher %d released the forks!\n", *philosopher);
    }
    return NULL;
}

int main()
{
    int philosopherNumbers[] = {0, 1, 2, 3, 4};
    pthread_t philosophers[5];

    for (int i = 0; i < 5; i++)
    {
        pthread_mutex_init(&forks[i], NULL);
    }
    for (int i = 0; i < 5; i++)
    {
        pthread_create(&philosophers[i], NULL, philofunc, (void *)&philosopherNumbers[i]);
    }
    for (int i = 0; i < 5; i++)
    {
        pthread_join(philosophers[i], NULL);
        pthread_mutex_destroy(&forks[i]);
    }

    return EXIT_SUCCESS;
}
