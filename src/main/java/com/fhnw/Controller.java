package com.fhnw;


import com.fhnw.dao.Email;
import com.fhnw.dao.EmailType;
import com.fhnw.dao.Korbus;
import com.fhnw.dao.Wort;
import com.fhnw.reader.Reader;

import java.util.ArrayList;
import java.util.List;


public class Controller {

    private int mailSize=0;

    private final double SCHWELLENWERT = 4.721223412218478E-252;
    Korbus hamKorbus = new Korbus(EmailType.HAM);
    Korbus spamKorpus = new Korbus(EmailType.SPAM);
    private Reader reader = new Reader();
    private List<Wort> woerter = new ArrayList<>();



    //Hier werden alle Mails durch iteriert
    // in der iteration wird die wortliste der Mail rausgezogen und mit der woesterListe verglichen
    //wenn ein Wort noch nicht in woersterListe existiert, dieses adden und den zugehörigen Counter raufzählen
    //wenn schon existiert nur counter raufzählen
    public Controller(boolean i) {
    }

    public Controller() {
        anlernen();
        kallibrieren();
        testen();
    }

    public void anlernen() {
        reader.importMailsByType(EmailType.SPAM);
        reader.importMailsByType(EmailType.HAM);

        mailSize = reader.getHamMails().size() + reader.getSpamMails().size() + mailSize;

        this.hamMailWoerterVergleichMitWortListe(reader.getHamMails());
        this.spamMailWoerterVergleichMitWortListe(reader.getSpamMails());

        bayesWahrscheinlichkeit(woerter, mailSize);

    }
    //import kallibrier-zip
    public void kallibrieren() {
        reader.importKallibrierMailsByType(EmailType.SPAM);
        reader.importKallibrierMailsByType(EmailType.HAM);

        mailSize = reader.getHamKallibTestMails().size()
                + reader.getSpamKallibTestMails().size() + mailSize;

        this.hamMailWoerterVergleichMitWortListe(reader.getHamKallibTestMails());
        this.spamMailWoerterVergleichMitWortListe(reader.getSpamKallibTestMails());

        int size = reader.getSpamKallibTestMails().size() + reader.getHamKallibTestMails().size()
                +reader.getHamMails().size() + reader.getSpamMails().size();

        bayesWahrscheinlichkeitByDatabase(woerter, mailSize);
    }

    //import test-zip
    public void testen() {
        reader.importTestMailsByType(EmailType.SPAM);
        reader.importTestMailsByType(EmailType.HAM);

        updateMails(reader.getHamTestMails());
        updateMails(reader.getSpamTestMails());

        spamWahrscheinlichkeitMail(reader.getHamTestMails());
        spamWahrscheinlichkeitMail(reader.getSpamTestMails());

        setToKorpus(reader.getHamTestMails());
        setToKorpus(reader.getSpamTestMails());
    }

    //setzen das Mail in den Ham- / oder Spamkorbus, je nachdem ob die Spamwahrscheinlichkeit des Mails den Schwellenwert übersteigt.
    private void setToKorpus(List<Email> mails) {
        for (Email mail : mails) {
            if (mail.getSpamWahrscheinlichkeit() > (SCHWELLENWERT) ){
                hamKorbus.getMails().add(mail);
            } else {
                spamKorpus.getMails().add(mail);
            }
        }
    }

    private void updateMails(List<Email> mails) {
        for (Email email : mails) {
            for (Wort word : email.getWoerter()) {
                Wort newWord = getWordByDescription(word);
                if (newWord != null) {
                    word.setSpamWahrscheinlichekeit(newWord.getSpamWahrscheinlichekeit());
                }
            }
        }
    }

    private Wort getWordByDescription(Wort inputWord) {
        for (Wort word : woerter) {
            if (word.getBezeichnug().equals(inputWord.getBezeichnug())) {
                return word;
            }
        }
        return null;
    }

    public void hamMailWoerterVergleichMitWortListe(List<Email> mails) {
        for (Email email : mails) {
            for (int i = 0; i < email.getWoerter().size(); i++) {
                int wortPosition = compare(woerter, email.getWoerter().get(i).getBezeichnug());
                if (wortPosition != -1) {
                    int count = woerter.get(i).getAnzahlHamMails();
                    woerter.get(wortPosition).setAnzahlHamMails(count + 1);
                } else {
                    woerter.add(email.getWoerter().get(i));
                }
            }
        }
    }

    public int compare(List<Wort> woerter, String word) {
        for (int i = 0; i < woerter.size(); i++) {
            if (woerter.get(i).getBezeichnug().equals(word)) {
                return i;
            }
        }
        return -1;
    }

    public void spamMailWoerterVergleichMitWortListe(List<Email> mails) {
        for (Email email : mails) {
            for (int i = 0; i < email.getWoerter().size(); i++) {
                int wortPosition = compare(woerter, email.getWoerter().get(i).getBezeichnug());
                if (wortPosition != -1) {
                    woerter.get(wortPosition).setAnzahlSpamMails(woerter.get(i).getAnzahlSpamMails());
                } else {
                    woerter.add(email.getWoerter().get(i));
                }
            }
        }
    }


    public void bayesWahrscheinlichkeit(List<Wort> wortList, int anzahlMail) {
        for (Wort wort : wortList) {
            setBayesSpamwahrscheinlichkeit(wort, anzahlMail);
        }
    }

    public void bayesWahrscheinlichkeitByDatabase(List<Wort> wortList, int anzahlMail) {
        for (Wort wort : wortList) {
            setBayesSpamwahrscheinlichkeitByDatabase(wort, anzahlMail);
        }
    }

    /**
     * SpamWahrscheinlichkeit eines Mails setzen (wörter-wahrschinelichkeiten werden multipiziert).
     * @param emailList
     */
    public void spamWahrscheinlichkeitMail(List<Email> emailList) {
        for (Email email : emailList) {
            double spamWahrscheinlichkeit = 1.0;
            for (Wort wort : email.getWoerter()) {
                spamWahrscheinlichkeit = spamWahrscheinlichkeit * (wort.getSpamWahrscheinlichekeit()) ;
            }
            email.setSpamWahrscheinlichkeit(spamWahrscheinlichkeit);

        }
    }


    public void setBayesSpamwahrscheinlichkeit(Wort wort, double anzahlMail) {
        double P_SpamWord = (wort.getAnzahlSpamMails() - 1) / anzahlMail;
        double P_HamWord = (wort.getAnzahlHamMails() - 1) / anzahlMail;

        if (wort.getAnzahlHamMails() == 1) {
            P_HamWord = 1 / anzahlMail;
        }

        if (wort.getAnzahlSpamMails() == 1) {
            P_SpamWord = 1 / anzahlMail;
        }
        double zaehler = P_HamWord / anzahlMail;
        double nenner = (P_HamWord + P_SpamWord) / anzahlMail;

        wort.setSpamWahrscheinlichekeit(zaehler / nenner);
    }


    // Bayes zum kallibrieren
    public void setBayesSpamwahrscheinlichkeitByDatabase(Wort wort, double anzahlMail) {
        double P_SpamWord = (wort.getAnzahlSpamMails() - 1) / anzahlMail;
        double P_Spam = 0.5;
        double P_HamWord = (wort.getAnzahlHamMails() - 1) / anzahlMail;
        double P_Ham = 0.5;

        double wahrscheinlichkeit = P_SpamWord * P_Spam / (P_SpamWord * P_Spam + P_HamWord * P_Ham);
        wort.setSpamWahrscheinlichekeit(wahrscheinlichkeit);

        if (wort.getAnzahlSpamMails() == 1) {
            wort.setSpamWahrscheinlichekeit(0.01);
        }

        if (wort.getHamWahrscheinlichkeit() == 1) {
            wort.setSpamWahrscheinlichekeit(0.99);
        }

    }

    public double getSCHWELLENWERT() {
        return SCHWELLENWERT;
    }

    public Reader getReader() {
        return reader;
    }

    public List<Wort> getWoerter() {
        return woerter;
    }

    public Korbus getHamKorbus() {
        return hamKorbus;
    }

    public Korbus getSpamKorpus() {
        return spamKorpus;
    }
}
