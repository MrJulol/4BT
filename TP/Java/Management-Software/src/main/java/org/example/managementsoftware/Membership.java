package org.example.managementsoftware;

import java.time.LocalDate;
import java.util.Date;

public class Membership {
    private LocalDate expirationDate;
    private MembershipType type;
    public Membership(LocalDate expirationDate, MembershipType type) {
        this.expirationDate = expirationDate;
        this.type = type;
    }

    public String name() {
        return type.name();
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
