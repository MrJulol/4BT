#pragma GCC optimize("O3")
#pragma GCC target("avx2")

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>
#include <pthread.h>

#define FILENAME "listHashing.txt"
#define NUM_THREADS 10

typedef struct
{
   char **names;
   int names_count;
   int m_start;
   int m_end;
} ThreadData;

int hash(const char *s, int m, int a)
{
   int h = 0;
   while (*s)
   {
      h = (a * h + (unsigned char)(*s)) % m;
      s++;
   }
   return h;
}

char **readNamesFromFile(const char *filename, int *count)
{
   FILE *file = fopen(filename, "r");
   if (!file)
   {
      perror("Error opening file");
      exit(EXIT_FAILURE);
   }

   char **names = NULL;
   char line[256];
   int n = 0;
   while (fgets(line, sizeof(line), file))
   {
      line[strcspn(line, "\n")] = 0;
      if (strlen(line) > 0)
      {
         names = realloc(names, (n + 1) * sizeof(char *));
         names[n] = strdup(line);
         n++;
      }
   }
   fclose(file);
   *count = n;
   return names;
}

int countCollisions(char **names, int names_count, int m, int a)
{
   int *hashTable = calloc(m, sizeof(int));
   int collisions = 0;
   for (int i = 0; i < names_count; i++)
   {
      int hashValue = hash(names[i], m, a);
      if (hashTable[hashValue] != 0)
      {
         collisions++;
      }
      else
      {
         hashTable[hashValue] = 1;
      }
   }
   free(hashTable);
   return collisions;
}

void *findBestParameters(void *arg)
{
   ThreadData *data = (ThreadData *)arg;
   int bestA = 0, bestM = 0, minCollisions = INT_MAX;

   for (int m = data->m_start; m <= data->m_end; m++)
   {
      for (int a = 1; a < m; a++)
      {
         int collisions = countCollisions(data->names, data->names_count, m, a);
         if (collisions < minCollisions)
         {
            minCollisions = collisions;
            bestA = a;
            bestM = m;
         }
      }
   }

   printf("Best parameters found: a = %d, m = %d with %d collisions in Area: %d-%d\n",
          bestA, bestM, minCollisions, data->m_start, data->m_end);

   return NULL;
}

int main()
{
   int names_count;
   char **names = readNamesFromFile(FILENAME, &names_count);
   printf("Number of entries: %d\n", names_count);

   int mRangeStart = 3327, mRangeEnd = 4096;
   int step = (mRangeEnd - mRangeStart + 1) / NUM_THREADS;

   pthread_t threads[NUM_THREADS];
   ThreadData threadData[NUM_THREADS];

   for (int i = 0; i < NUM_THREADS; i++)
   {
      threadData[i].names = names;
      threadData[i].names_count = names_count;
      threadData[i].m_start = mRangeStart + i * step;
      threadData[i].m_end = (i == NUM_THREADS - 1) ? mRangeEnd : threadData[i].m_start + step - 1;

      pthread_create(&threads[i], NULL, findBestParameters, &threadData[i]);
   }

   for (int i = 0; i < NUM_THREADS; i++)
   {
      pthread_join(threads[i], NULL);
   }

   for (int i = 0; i < names_count; i++)
   {
      free(names[i]);
   }
   free(names);

   return 0;
}
