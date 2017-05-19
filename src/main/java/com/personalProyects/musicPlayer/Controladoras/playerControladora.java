package com.personalProyects.musicPlayer.Controladoras;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by mt on 19/05/17.
 */
public class playerControladora {
    //Variables Generales
    String ubicacionCarpeta = "/home/mt/IdeaProjects/musicPLayer/src/main/resources/Musica/";


    //Botones
    @FXML
    void llenarListViewAlbums(ActionEvent event) {
        ArrayList<File> archivosLeidos = leerArchivosMP3Carpeta(ubicacionCarpeta);
        for(int i=0;i<archivosLeidos.size();i++){
            System.out.println(archivosLeidos.get(i).getName());
        }
        if(archivosLeidos.isEmpty()){
            System.out.println("Carpeta Vacia");
        }
    }

    //Funciones
    public static ArrayList<File> leerArchivosMP3Carpeta(String ubicacionCarpeta){
        File carpeta = new File(ubicacionCarpeta);
        File[] listaArchivos = carpeta.listFiles();
        ArrayList<File> listaCanciones=new ArrayList<File>();

        for(File file: listaArchivos){
            if(file.isFile()){
                if(!file.getName().contains(".jpg")){
                    listaCanciones.add(file);
                }
            }
        }
        return listaCanciones;
    }
}
