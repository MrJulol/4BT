// printf is stupid verwenet stderr un fprintf
//  fflush(stdin) nach jedem printf
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <time.h>

int fd[2];
int fdback[2];
pid_t isChild;

void handler(int signal)
{
    if (signal == SIGUSR1)
    {
        char message[100] = {0};
        read(fd[0], message, 100);
        int num;
        sscanf(message, "%d;%[^\n]", &num, message);
        char timestamp[10] = {0};
        printf("%s: %s\n", timestamp, message);
        fflush(stdin);
        sprintf(message, "%d;Erledigt für Sekunde: %d", num, num);
        write(fdback[1], message, 100);
        kill(getppid(), SIGUSR2);
    }
    else if (signal == SIGUSR2)
    {
        char message[100] = {0};
        read(fdback[0], message, 100);
        int num;
        sscanf(message, "%d;%[^\n]", &num, message);
        printf("%s\n", message);
        fflush(stdin);

        if (num == 10)
        {
            kill(isChild, SIGKILL);
        }
    }
}
int main()
{

    pipe(fd);
    pipe(fdback);
    signal(SIGUSR1, &handler);
    signal(SIGUSR2, &handler);

    isChild = fork();

    if (isChild == 0)
    {
        close(fd[1]);     // close writing
        close(fdback[0]); // close reading
        while (1)
        {
        }
    }
    else
    {
        close(fd[0]);     // close reading
        close(fdback[1]); // close writing
        for (int i = 1; i <= 10; i++)
        {
            char message[100] = {0};
            // Write the message here
            char someString[11] = {0};
            for (int j = 0; j < i; j++)
            {
                someString[j] = '.';
            }
            char timestamp[20] = {0};
            sprintf(message, "%d;Zeitstempel: %s; %d Sekunde: %s\n", i, timestamp, i, someString);
            write(fd[1], message, 100);
            sleep(1);
            kill(isChild, SIGUSR1);
        }
        wait(NULL);
    }

    return EXIT_SUCCESS;
}