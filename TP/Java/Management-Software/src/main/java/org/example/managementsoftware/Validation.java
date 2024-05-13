package org.example.managementsoftware;

import java.time.LocalDate;

public class Validation {
    /**
     * Checks that the provided String is neither blank empty, only repeating whitespaces nor less than 3 Characters
     *
     * @param string
     * @return true if Invalid, false if Valid
     */
    public static boolean isInvalidInput(String string) {
        return string.matches("\\s+") || string.isBlank() || string.isEmpty();
    }

    public static boolean validateName(String name) {
        if(isInvalidInput(name)) return false;
        //Check if name is already Given Away
        return true;
    }

    public static boolean validateAddress(String address) {
        if(isInvalidInput(address)) return false;
        return true;
    }

    public static boolean validatePhone(String phone) {
        if(isInvalidInput(phone)) return false;
        return true;
    }

    public static boolean validateBirthDate(LocalDate birthDate) {
        if(birthDate.isAfter(LocalDate.now())) return false;
        return true;
    }

    public static boolean validatePassword(String PassOnInit, String PassOnConfirm) {
        if(isInvalidInput(PassOnInit)) return false;
        if(isInvalidInput(PassOnConfirm)) return false;
        if(!PassOnInit.equals(PassOnConfirm)) return false;
        return true;
    }
}
