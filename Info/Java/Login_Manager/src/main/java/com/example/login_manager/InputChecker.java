package com.example.login_manager;

/**
 * Check a string to certain patterns
 */
public class InputChecker {
    /**
     * Checks if the String is either empty, blank or does not match the Regex: "[a-zA-Z0-9]+"
     * @param input String
     * @return null if valid ||| String with error
     */
    public static String checkIfValid(String input){
        if(input.isBlank() || input.isEmpty()){
            return "Input cannot be empty!";
        }
        if(input.contains(" ")){
            return "Input cannot contain whitespaces!";
        }
        if(!input.matches("[a-zA-Z0-9]+")){
            return "Input can only contain letters and numbers!";
        }
        System.out.println("String matches!");
        return null;
    }
}
