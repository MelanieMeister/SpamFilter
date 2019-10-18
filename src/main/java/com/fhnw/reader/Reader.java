package com.fhnw.reader;


import com.fhnw.dao.Email;
import com.fhnw.dao.EmailType;
import com.fhnw.dao.Wort;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;



//email generiert
//und woerter in email befüllt
public class Reader {
    //Todo: change path
    private static final String PATH = "C:\\Users\\Meister\\Documents\\FHNW\\dist\\Programmieraufgabe1\\";

    private static final String PATHNAME_HAM =  "ham-anlern.zip";
    private static final String PATHNAME_SPAM = "spam-anlern.zip";

    private static final String PATHNAME_Kalib_HAM =  "ham-kallibrierung.zip";
    private static final String PATHNAME_Kalib_SPAM = "ham-kallibrierung.zip";

    private static final String PATHNAME_TEST_HAM =  "spam-test.zip";
    private static final String PATHNAME_TEST_SPAM = "ham-test.zip";

    private List<Email> hamMails = new ArrayList<>();
    private List<Email> spamMails = new ArrayList<>();
    private List<Email> hamTestMails = new ArrayList<>();
    private List<Email> spamTestMails = new ArrayList<>();
    private List<Email> hamKallibTestMails = new ArrayList<>();
    private List<Email> spamKallibTestMails = new ArrayList<>();
    private InputStream input;


    //import anlern-zip
    public void importMailsByType(EmailType type ) {
        if(type == EmailType.HAM){
            importMailsByType(type, PATH+PATHNAME_HAM, hamMails);
        }else{
            importMailsByType(type, PATH+PATHNAME_SPAM, spamMails);
        }
    }

    //import kallibrieren-zip
    public void importKallibrierMailsByType(EmailType type ) {
        if(type == EmailType.HAM){
            importMailsByType(type,PATH+ PATHNAME_Kalib_HAM, hamKallibTestMails);
        }else{
            importMailsByType(type, PATH+PATHNAME_Kalib_SPAM, spamKallibTestMails);
        }
    }

    //import test-zip
    public void importTestMailsByType(EmailType type ) {
        if(type == EmailType.HAM){
            importMailsByType(type,PATH+ PATHNAME_TEST_HAM, hamTestMails);
        }else{
            importMailsByType(type,PATH+ PATHNAME_TEST_SPAM, spamTestMails);
        }
    }
    /**
     * Dateien werden vom zip-file eingelesen und als Mails konvertiert.
     */
    public void importMailsByType(EmailType type, String path, List<Email> list) {
       try{
           ZipFile file = new ZipFile(path);
           Enumeration<? extends ZipEntry> zipEntries = file.entries();

           while (zipEntries.hasMoreElements()) {
               ZipEntry entry = zipEntries.nextElement();
               InputStream stream = file.getInputStream(entry);

               StringBuilder stringBuilder = new StringBuilder();

               List<Wort> woerterListe = new ArrayList();
               Email email = new Email(type,woerterListe);

               String line = getTextFromStream(stream);
               stringBuilder.append(line);
               if(!(stringBuilder.toString().equals("") || stringBuilder.toString()==null)){
                   this.wortListeInEmailBefuellen(stringBuilder.toString(), email);
               }

               list.add(email);
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    // get the inputStream of the file and convert it to a text
    public String getTextFromStream(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader =  new BufferedReader(
                new InputStreamReader(inputStream));
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line + " ");
        }
        return stringBuilder.toString();
    }

    //set words of the mails in a wordList and assure that if the word appears twice in a mail, it will just count for one.
    private void wortListeInEmailBefuellen(String linie, Email email) {
        String[] wortBezeichnungen = linie.split(" ");

        for (String wortInLinie : wortBezeichnungen) {
            if(!compare(email.getWoerter(), wortInLinie)){
                Wort neuesWort = new Wort(wortInLinie);
                email.addWort(neuesWort);
            }
        }


    }

    //show if the word exist already in the wordList
    public boolean compare(List<Wort> woerter, String word){
        for (Wort wort : woerter) {
            if (wort.getBezeichnug().equals(word)) {
              return true;
            }
        }
        return false;
    }


    //limited the mailsSize for tet cases
    public List<Email> getLists(List<Email> mails2){
//        List<Email> mail = new ArrayList<>();
//        for(int i=0; i<15; i++){
//            mail.add(mails2.get(i));
//        }
//
//        return mail;
        return  mails2;
    }
    public List<Email> getHamMails() {
       return getLists( hamMails);
    }



    public List<Email> getSpamMails() {
        return getLists(  hamMails);
    }

    public List<Email> getHamTestMails() {
        return getLists(  hamTestMails);
    }

    public List<Email> getHamKallibTestMails() {
        return getLists(  hamKallibTestMails);
    }

    public List<Email> getSpamKallibTestMails() {
        return getLists(  spamKallibTestMails);
    }

    public List<Email> getSpamTestMails() {
        return getLists(  spamTestMails);
    }
}
