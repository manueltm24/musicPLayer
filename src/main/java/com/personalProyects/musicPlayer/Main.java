package com.personalProyects.musicPlayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

/**
 * Created by mt on 19/05/17.
 */
public class Main extends   Application {
    public static void main(String[] args) throws SQLException {

        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Vistas/PLayer.fxml"));

            //Propiedades de la ventana principal
            primaryStage.setTitle("Ventana Principal"); //Titutlo de la Ventana
            primaryStage.setMaximized(false);
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("ERROR CARGAR VENTANA");
            e.printStackTrace();
            return;
        }
    }



}
