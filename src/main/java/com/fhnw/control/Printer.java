package com.fhnw.control;

import com.fhnw.Controller;
import com.fhnw.dao.Email;
import com.fhnw.dao.Korbus;

import java.util.List;

public class Printer {
    private final Controller controller;

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
        int hamSizeInKorpus = controller.getHamKorbus().getMails().size();
        int spamSizeInKorpus = controller.getHamKorbus().getMails().size();

        int spamSizeInTestFile = controller.getReader().getSpamTestMails().size();
        int hamizeInTestFile = controller.getReader().getHamTestMails().size();
        System.out.print("Hams eingelesen: " + hamizeInTestFile);
        System.out.println("    ->  Hams gefiltert: " + hamSizeInKorpus);

        System.out.print("Spams eingelesen: " + spamSizeInTestFile);
        System.out.println("    ->  Spams gefiltert: " + spamSizeInKorpus);
    }

    private void printPercentage(){
        double correctHams = getPercentageOfCorrect(controller.getReader().getHamTestMails(), controller.getHamKorbus());
        double correctSpams = getPercentageOfCorrect(controller.getReader().getSpamTestMails(), controller.getSpamKorpus());

        System.out.println(correctHams +"% der Ham-Emails wurden korrekt gefiltert.");
        System.out.println(correctSpams +"% der Spam-Emails wurden korrekt gefiltert.");
    }

    private double getPercentageOfCorrect(List<Email> emailList, Korbus korbus){
        double amountOfCorrect = 0,  amountOfIncorrect = 0;
        for(Email mail: emailList){
            if(mail.getType() == korbus.getType()){
                amountOfCorrect = amountOfCorrect + 1.0;
            }else{
                amountOfIncorrect = amountOfIncorrect + 1.0;
            }
        }


        return 100 / emailList.size() * amountOfCorrect;
    }
}
