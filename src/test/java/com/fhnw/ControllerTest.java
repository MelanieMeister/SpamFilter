package com.fhnw;

import com.fhnw.dao.Wort;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerTest {

    @Test
    public void setSpamwahrscheinlichkeitByWort() {
        Controller c = new Controller(false);

        Wort wort = new Wort("wort", 22,1,1.0,10);
        int amount = 60;
        c.setBayesSpamwahrscheinlichkeitByDatabase(wort, amount);

        double anzahlhams = wort.getAnzahlHamMails();
        double anzahlSpams = wort.getAnzahlSpamMails();

        double anzahlMail = amount;

        double zaehler = anzahlhams / anzahlMail;
        double nenner = (anzahlhams + anzahlSpams) / anzahlMail;


        System.out.println(wort.getSpamWahrscheinlichekeit());
        Assert.assertEquals(0.9, wort.getSpamWahrscheinlichekeit(), 0.2);
    }
}