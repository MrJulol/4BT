#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <unistd.h>
#include <time.h>
#include <string.h>
#include <signal.h>

int loop = 1;
int secs = 10;
int parentloop = 0;

void endloop()
{
    loop = 0;
}
void parentresponse()
{
    parentloop = 1;
}

int main()
{

    int fd[2];
    if (pipe(fd) < 0) // Error during Pipe creation
    {
        perror("Pipe: ");
    }

    int pid = fork();
    if (pid < 0)
    {
        perror("Child creation");
    }
    if (!pid)
    {
        signal(SIGUSR1, endloop);

        char *buff;
        while (loop)
        {
        }
        for (int i = 0; i < secs; i++)
        {
            close(fd[1]);
            read(fd[0], &buff, sizeof(char *));
            printf("%s\n", buff);
        }
        static char *mssg = "DONE";
        for (int i = 0; i < secs; i++)
        {
            close(fd[0]);
            write(fd[1], &mssg, sizeof(char *));
            sleep(1);
        }
        kill(getppid(), SIGUSR2);
        pause();
    }
    else
    {
        signal(SIGUSR2, parentresponse);

        time_t mytime = time(NULL);
        for (int i = 0; i < secs; i++)
        {
            char seknumber[2];
            seknumber[0] = (char)i;
            char *time_str = ctime(&mytime);
            time_str[strlen(time_str) - 1] = '\0';
            strcat(time_str, ":____\0");
            // strcat(time_str, seknumber[0]);
            strcat(time_str, " Sekunde: ");
            for (int j = 0; j <= i; j++)
            {
                strcat(time_str, ".");
            }
            close(fd[0]);
            write(fd[1], &time_str, sizeof(char *));
            sleep(1);
        }
        kill(pid, SIGUSR1);
        while (parentloop != 1)
        {
        }
        char *buff;
        close(fd[1]);
        read(fd[0], &buff, sizeof(char *));
        printf("%s\n", buff);
        kill(pid, SIGQUIT);
        raise(SIGQUIT);
    }

    return EXIT_SUCCESS;
}