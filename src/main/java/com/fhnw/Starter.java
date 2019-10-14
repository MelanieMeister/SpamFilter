package com.fhnw;

import com.fhnw.control.Printer;
import com.fhnw.reader.Reader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Starter  extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /**
         * main programm.
         */
        Controller c = new Controller();
        /**
         * print values to the console
         */
        Printer p = new Printer(c);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
