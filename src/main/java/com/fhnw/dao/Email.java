package com.fhnw.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Email {
    private EmailType type;
    private List<Wort> woerter;
    private double spamWahrscheinlichkeit;


    public Email(EmailType type, List<Wort> woerter) {
        this.type = type;
        this.woerter = woerter;
    }

    public double getSpamWahrscheinlichkeit() {
        return spamWahrscheinlichkeit;
    }

    public void setSpamWahrscheinlichkeit(double spamWahrscheinlichkeit) {
        this.spamWahrscheinlichkeit = spamWahrscheinlichkeit;
    }

    public EmailType getType() {
        return type;
    }

    public List<Wort> getWoerter() {
        return woerter;
    }

    public void addWort(Wort neuesWort) {
        this.woerter.add(neuesWort);
    }
}
