package org.example.managementsoftware;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Account {
    private final String name;
    private final String address;
    private final String telNumber;
    private final LocalDate birtDate;
    private final String pass;
    private Membership membership;
    private int checkinStat = 0;
    private final List<String> checkinStatDates;

    public Account(String name, String address, String telNumber, LocalDate birtDate, String pass, MembershipType membershipType) {
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
        this.birtDate = birtDate;
        this.pass = pass;
        switch (membershipType) {
            case MONTHLY ->
                    this.membership = new Membership(ZonedDateTime.now().plusMonths(1).toLocalDate(), MembershipType.MONTHLY);
            case YEARLY ->
                    this.membership = new Membership(ZonedDateTime.now().plusYears(1).toLocalDate(), MembershipType.YEARLY);
            case QUARTERLY ->
                    this.membership = new Membership(ZonedDateTime.now().plusMonths(3).toLocalDate(), MembershipType.QUARTERLY);
            default -> throw new IllegalStateException("Unexpected value: " + membershipType);
        }
        this.checkinStatDates = new ArrayList<>();
    }
    public Account(String name, String address, String telNumber, LocalDate birtDate, String pass, MembershipType membershipType, String expirationDate) {
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
        this.birtDate = birtDate;
        this.pass = pass;
        switch (membershipType) {
            case MONTHLY ->
                    this.membership = new Membership(ZonedDateTime.now().plusMonths(1).toLocalDate(), MembershipType.MONTHLY);
            case YEARLY ->
                    this.membership = new Membership(ZonedDateTime.now().plusYears(1).toLocalDate(), MembershipType.YEARLY);
            case QUARTERLY ->
                    this.membership = new Membership(ZonedDateTime.now().plusMonths(3).toLocalDate(), MembershipType.QUARTERLY);
            default -> throw new IllegalStateException("Unexpected value: " + membershipType);
        }
        this.checkinStatDates = new ArrayList<>();
        this.membership.setExpirationDate(LocalDate.parse(expirationDate));
    }



    public void changeMembership(MembershipType membershipType) {
        switch (membershipType) {
            case MONTHLY -> {
                this.membership = new Membership(ZonedDateTime.now().plusMonths(1).toLocalDate(), MembershipType.MONTHLY);
                try {
                    Database.getDatabase().updateMembership(this.getName(), MembershipType.MONTHLY);
                } catch (
                        SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            case YEARLY -> {
                this.membership = new Membership(ZonedDateTime.now().plusYears(1).toLocalDate(), MembershipType.YEARLY);
                try {
                    Database.getDatabase().updateMembership(this.getName(), MembershipType.YEARLY);
                } catch (
                        SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            case QUARTERLY -> {
                this.membership = new Membership(ZonedDateTime.now().plusMonths(3).toLocalDate(), MembershipType.QUARTERLY);
                try {
                    Database.getDatabase().updateMembership(this.getName(), MembershipType.QUARTERLY);
                } catch (
                        SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + membershipType);
        }
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public int getCheckinStat() {
        return checkinStat;
    }

    public String getAddress() {
        return address;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public LocalDate getBirtDate() {
        return birtDate;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setCheckinStat(int checkinStat) {
        this.checkinStat = checkinStat;
        this.checkinStatDates.add(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    }

    public List<String> getCheckinStatDates() {
        return checkinStatDates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(name, account.name) && Objects.equals(address, account.address) && Objects.equals(telNumber, account.telNumber) && Objects.equals(birtDate, account.birtDate) && Objects.equals(pass, account.pass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, telNumber, birtDate, pass);
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", checkinStat=" + checkinStat +
                '}';
    }
}
