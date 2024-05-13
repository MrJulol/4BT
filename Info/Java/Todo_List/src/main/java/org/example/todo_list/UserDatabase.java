package org.example.todo_list;

import java.util.HashSet;

public class UserDatabase {
    private static UserDatabase instance;
    private static HashSet<String> users;
    public static UserDatabase getInstance() {
        if (instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }
    private UserDatabase() {
        users = new HashSet<>();
    }
    public boolean isEmpty(){
        return users.isEmpty();
    }
    public void addUser(String pass){
        users.add(pass);
    }
    public boolean canLogin(String pass){
        return users.contains(pass);
    }
}
