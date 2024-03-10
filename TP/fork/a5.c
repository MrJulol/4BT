#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <unistd.h>
#include <time.h>
#include <string.h>
#include <signal.h>

int fd1[2];
int fd2[2];
int pid;

void handler(int signal)
{
    if (signal == SIGUSR1)
    {
        char mssg[100] = {0};
        char retmssg[100] = {0};

        close(fd1[1]);
        read(fd1[0], mssg, 100);

        int num;
        sscanf(mssg, "%d;%[^\n]", &num, mssg);
        printf("%s\n", mssg);

        time_t mytime;
        mytime = time(NULL);
        char *time_str;
        time_str = ctime(&mytime);
        time_str[strlen(time_str) - 1] = '\0';

        sprintf(retmssg, "%d;%s: Erledigt für Sekunde: %d\n", num, time_str, num);
        close(fd2[0]);
        write(fd2[1], retmssg, 100);
        kill(getppid(), SIGUSR2);
    }
    else if (signal == SIGUSR2)
    {
        char mssg[100] = {0};
        close(fd2[1]);
        read(fd2[0], mssg, 100);

        int num;
        sscanf(mssg, "%d;%[^\n]", &num, mssg);
        printf("%s\n\n", mssg);

        if (num == 10)
        {
            kill(pid, SIGKILL);
        }
    }
}

int main()
{
    signal(SIGUSR1, &handler);
    signal(SIGUSR2, &handler);

    if (pipe(fd1) < 0) // Error during Pipe creation
    {
        perror("Pipe1: ");
        raise(SIGTERM);
    }
    if (pipe(fd2) < 0) // Error during Pipe creation
    {
        perror("Pipe1: ");
        raise(SIGTERM);
    }

    pid = fork();
    if (pid < 0)
    {
        perror("Child creation");
        raise(SIGTERM);
    }
    if (!pid)
    {
        while (1)
        {
        }
    }
    else
    {
        time_t mytime;
        for (int num = 1; num <= 10; num++)
        {
            mytime = time(NULL);
            char *time_str;
            time_str = ctime(&mytime);
            time_str[strlen(time_str) - 1] = '\0';

            char mssg[100] = {0};
            char Dots[11] = {0};
            for (int j = 0; j < num; j++)
            {
                Dots[j] = '.';
            }

            sprintf(mssg, "%d;%s; %d Sekunde: %s\n", num, time_str, num, Dots);
            close(fd1[0]);
            write(fd1[1], mssg, 100);
            sleep(1);
            kill(pid, SIGUSR1);
            sleep(1);
        }
        wait(NULL);
    }

    return EXIT_SUCCESS;
}