// NOTE: it is recommended to use this even if you don't understand the following code.

import java.util.*;
import java.io.*;
import java.lang.*;

public class usernames2 {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Locale.setDefault(Locale.US);
        InputStream fin = System.in;
        OutputStream fout = System.out;
        // uncomment the two following lines if you want to read/write from files
        // fin = new FileInputStream("input.txt");
        // fout = new FileOutputStream("output.txt");

        Scanner scn = new Scanner(fin);
        PrintStream prnt = new PrintStream(fout);

        String S = scn.next();
        
        int N = scn.nextInt();
        
        String[] U = new String[N];
        for (int i = 0; i < N; ++i)
            U[i] = scn.next();
        
        String T = "";
        
        
        // INSERT YOUR CODE HERE
        
        
        prnt.format("%s\n", T);

        fout.flush();
    }
}
