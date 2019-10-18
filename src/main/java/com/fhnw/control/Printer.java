package com.fhnw.control;

import com.fhnw.Controller;
import com.fhnw.dao.Email;
import com.fhnw.dao.Korbus;

import java.util.List;

public class Printer {
    private Controller controller;
    public Printer(){}
    public Printer(Controller controller){
        this.controller = controller;

        printSchwellwert();
        printOverviewPercentage();
        printPercentage();
    }
    /*
    * Bestimmen Sie geeignete Werte fur den Schwellenwert, wann eine Mail als Spam
    * klassifiziert werden soll und fur obiges , so dass ihr Spamfilter gut arbeitet. Nutzen
    * Sie dazu die Mails in ham-kalibrierung.zip und spam-kalibrierung.zip.*/
    public void printSchwellwert(){
        System.out.println("Schwellwert: " + controller.getSCHWELLENWERT());
    }

    /*Geben Sie an, wie viel Prozent der Mails in ham-test.zip bzw. spam-test.zip korrekt
    klassifiziert wurden.*/
    public void printOverviewPercentage(){
        System.out.print("Hams eingelesen: " +  controller.getReader().getHamTestMails().size());
        System.out.println("    ->  Hams gefiltert (im Korbus): " + controller.getHamKorbus().getMails().size());

        System.out.print("Spams eingelesen: " + controller.getReader().getSpamTestMails().size());
        System.out.println("    ->  Spams gefiltert (im Korbus): " + controller.getSpamKorpus().getMails().size());
    }

    private void printPercentage(){
        double correctHams = getPercentageOfCorrect(controller.getReader().getHamTestMails().size(), controller.getHamKorbus());
        double correctSpams = getPercentageOfCorrect(controller.getReader().getSpamTestMails().size(), controller.getSpamKorpus());

        System.out.println(correctHams +"% der Ham-Emails wurden korrekt gefiltert.");
        System.out.println(correctSpams +"% der Spam-Emails wurden korrekt gefiltert.");
    }

    public double getPercentageOfCorrect(int size, Korbus korbus){
        double amountOfCorrect = 0;
        for(Email mail: korbus.getMails()){
            if(mail.getType() == korbus.getType()){
                amountOfCorrect = amountOfCorrect + 1.0;
            }
        }
        return 100.0 /size * amountOfCorrect;
    }
}
