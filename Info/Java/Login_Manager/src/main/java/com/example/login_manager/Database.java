package com.example.login_manager;


import java.util.HashSet;

public class Database {

    private static Database database;
    private final HashSet<Account> accounts;

    private Database() {
        accounts = new HashSet<>();
    }
    public static Database getDatabase(){
        if(database == null){
            database = new Database();
        }
        return database;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public boolean checkAccount(Account account) {
        return this.accounts.contains(account);
    }

    public boolean checkDuplicateUsername(String username){
        return this.accounts.stream().anyMatch(account -> account.getUsername().equals(Encrypt.sha256(username)));
    }



}
