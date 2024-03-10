#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <unistd.h>
#include <time.h>
#include <string.h>
#include <signal.h>
int loop = 1;
void pauseloop(int sig)
{
    loop = 0;
    printf("asdsadsa\n");
}
int main()
{

    int fd[2];

    if (pipe(fd) < 0) // Error during Pipe creation
    {
        perror("Pipe1: ");
    }

    int pid = fork();
    if (pid < 0)
    {
        perror("Child creation");
    }
    if (pid == 0) // Child
    {
        signal(SIGINT, pauseloop);
        printf("TEST2\n");
        char *buff;
        while (loop)
        {
        }
        // close(fd[1]);
        read(fd[0], &buff, sizeof(char *));
        printf("%s", buff);
    }
    else // Parent
    {
        char *buff = "yes";
        printf("TEST!1");
        // fflush(stdin);
        // close(fd[0]);
        write(fd[1], &buff, sizeof(char *));
        sleep(1);
        kill(pid, SIGINT);
        // wait(NULL);
    }
    printf("HELLO");
    return EXIT_SUCCESS;
}