#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>
#include <sys/msg.h>
#include <sys/types.h>
#include <sys/ipc.h>

struct message
{
    long type;
    char text[100];
};

int main()
{
    printf("test\n");
    struct message recieve;
    struct message recieve2;
    struct message write;
    struct message write2;

    key_t key = ftok("niga", 99);
    int msgid = msgget(key, IPC_CREAT | 0666);

    pid_t pid = fork();
    if (pid < 0)
    {
        printf("Err fork");
        return 0;
    }

    // EP
    if (pid != 0)
    {
        printf("test2\n");
        strcpy(write.text, "was soll ich drucken?\n");
        write.type = 1;
        msgsnd(msgid, &write, sizeof(write.text), 0);
        wait(NULL);
        printf("test10\n");
        if (msgrcv(msgid, &recieve2, sizeof(recieve2.text), 2, 0) == -1)
        {
            printf("ERROROROROOROR\n");
        }

        printf("test11\n");
        printf("ok, hier: %s \n", recieve2.text);
        msgctl(msgid, IPC_RMID, NULL);
        return EXIT_SUCCESS;
        // KP
    }
    else
    {
        printf("test3\n");
        msgrcv(msgid, &recieve, sizeof(recieve.text), 1, 0);
        printf("test4\n");
        printf("nachricht erhalten: %s \n", recieve.text);
        write2.type = 2;
        printf("test9\n");
        strcpy(write2.text, "DRUCKE\n\0");
        printf("test9\n");
        msgsnd(msgid, &write2, sizeof(write2.text), 0);
        printf("test9\n");
    }
    return EXIT_SUCCESS;
}