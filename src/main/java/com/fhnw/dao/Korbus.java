package com.fhnw.dao;

import java.util.ArrayList;
import java.util.List;

public class Korbus {

    private List<Email> mails = new ArrayList<>();
    private EmailType type;

    public Korbus(EmailType type) {
        this.type = type;
    }

    public List<Email> getMails() {
        return mails;
    }

    public EmailType getType() {
        return type;
    }

    public void setType(EmailType type) {
        this.type = type;
    }

    public void setMails(List<Email> mails) {
        this.mails = mails;
    }
}
