#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <unistd.h>
#include <time.h>
#include <string.h>
#include <signal.h>

int loop2 = 1;
int secs = 10;

void pauseloop()
{
    loop2 = 0;
    printf("YESSSSS");
}

int main()
{

    int fd[2];
    int fd2[2];
    if (pipe(fd) < 0) // Error during Pipe creation
    {
        perror("Pipe1: ");
    }
    if (pipe(fd2) < 0) // Error during Pipe creation
    {
        perror("Pipe2: ");
    }

    int pid = fork();
    if (pid < 0)
    {
        perror("Child creation");
    }
    if (!pid)
    {
        signal(SIGUSR2, pauseloop);

        time_t mytime;

        while (1)
        {
            while (loop2)
            {
            }
            char *buff = malloc(100 * sizeof(char));
            read(fd[0], &buff, sizeof(char *));
            printf("%s\n", buff);
            loop2 = 1;
        }
    }
    else
    {
        time_t mytime;

        char seknumber[2];
        for (int i = 0; i < secs; i++)
        {
            mytime = time(NULL);

            seknumber[0] = (char)i;
            seknumber[1] = '\0';
            char *time_str = malloc(100 * sizeof(char));
            time_str = ctime(&mytime);
            time_str[strlen(time_str) - 1] = '\0';

            strcat(time_str, ":____\0");
            strcat(time_str, seknumber);
            strcat(time_str, " Sekunde: \0");

            for (int j = 0; j <= i; j++)
            {
                strcat(time_str, ".");
            }

            strcat(time_str, "\n");

            write(fd[1], time_str, sizeof(time_str) + 1);
            kill(pid, SIGUSR2);

            sleep(1);
        }
        kill(pid, SIGQUIT);
    }

    return EXIT_SUCCESS;
}