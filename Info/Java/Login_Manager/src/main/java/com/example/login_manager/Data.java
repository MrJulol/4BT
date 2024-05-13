package com.example.login_manager;

public class Data {
    private static Account loggedOnAccount;

    public static Account getLoggedOnAccount() {
        return loggedOnAccount;
    }

    public static void setLoggedOnAccount(Account loggedOnAccount) {
        Data.loggedOnAccount = loggedOnAccount;
    }
}
