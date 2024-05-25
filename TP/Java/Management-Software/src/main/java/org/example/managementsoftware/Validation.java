package org.example.managementsoftware;

import java.time.LocalDate;
import java.util.stream.Collectors;

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
        return !Database.getDatabase().getAccountsAsList().stream().map(Account::getName).collect(Collectors.toSet()).contains(name);
        //Check if name is already Given Away
    }

    public static boolean validateAddress(String address) {
        return !isInvalidInput(address);
    }

    public static boolean validatePhone(String phone) {
        return phone.matches("\\d+$");
    }

    public static boolean validateBirthDate(LocalDate birthDate) {
        return !birthDate.isAfter(LocalDate.now());
    }

    public static boolean validatePassword(String PassOnInit, String PassOnConfirm) {
        if(isInvalidInput(PassOnInit)) return false;
        if(isInvalidInput(PassOnConfirm)) return false;
        return PassOnInit.equals(PassOnConfirm);
    }
}
