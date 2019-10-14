package com.fhnw.dao;

import java.math.BigDecimal;

public class Wort {
    private String bezeichnug;
    private int anzahlHamMails;
    private int anzahlSpamMails;
    private double spamWahrscheinlichekeit;
    private double hamWahrscheinlichkeit;

    public Wort(String bezeichnug, int anzahlHamMails, int anzahlSpamMails, double spamWahrscheinlichekeit,
                double hamWahrscheinlichkeit) {
        this.bezeichnug = bezeichnug;
        this.anzahlHamMails = anzahlHamMails;
        this.anzahlSpamMails = anzahlSpamMails;
        this.spamWahrscheinlichekeit = spamWahrscheinlichekeit;
        this.hamWahrscheinlichkeit = hamWahrscheinlichkeit;
    }

    public Wort(String bezeichnug) {
        this.bezeichnug = bezeichnug;
        this.anzahlHamMails = 1;
        this.anzahlSpamMails = 1;
        this.spamWahrscheinlichekeit = 1.0;
        this.hamWahrscheinlichkeit = 1.0;
    }

    public String getBezeichnug() {
        return bezeichnug;
    }

    public void setBezeichnug(String bezeichnug) {
        this.bezeichnug = bezeichnug;
    }

    public int getAnzahlHamMails() {
        return anzahlHamMails;
    }

    public void setAnzahlHamMails(int anzahlHamMails) {
        this.anzahlHamMails = anzahlHamMails;
    }

    public int getAnzahlSpamMails() {
        return anzahlSpamMails;
    }

    public void setAnzahlSpamMails(int anzahlSpamMails) {
        this.anzahlSpamMails = anzahlSpamMails;
    }

    public BigDecimal getSpamWahrscheinlichekeitDecimal() {
        BigDecimal bd = new BigDecimal(getSpamWahrscheinlichekeit());
        return bd;
    }

    public double getSpamWahrscheinlichekeit() {
        if (anzahlHamMails == 0) {
            return 0.001;
        }
        return spamWahrscheinlichekeit;
    }

    public void setSpamWahrscheinlichekeit(double spamWahrscheinlichekeit) {
        this.spamWahrscheinlichekeit = spamWahrscheinlichekeit;
    }

    public double getHamWahrscheinlichkeit() {
        if (anzahlSpamMails == 0) {
            return 0.001;
        }
        return 1 - spamWahrscheinlichekeit;
    }

    public void setHamWahrscheinlichkeit(double hamWahrscheinlichkeit) {
        this.hamWahrscheinlichkeit = hamWahrscheinlichkeit;
    }
}
