package com.personalProyects.musicPlayer.Controladoras;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mt on 19/05/17.
 */
public class playerControladora {
    //Variables Generales
    String ubicacionCarpeta = "/home/mt/IdeaProjects/musicPLayer/src/main/resources/Musica/";
    private MediaPlayer mp;
    public Media me;
    Random numeroRandom = new Random();
    int max=leerArchivosMP3Carpeta(ubicacionCarpeta).size();
    int min=1;


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

    @FXML
    void play(ActionEvent event) {
        System.out.println(max);
        mp3(null).play();
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

    public MediaPlayer mp3(String cancionSeleccionada){
        int proxCancion = numeroRandom.nextInt(max)+ min;
        String nombreCancion = leerArchivosMP3Carpeta(ubicacionCarpeta).get(proxCancion).getName();
        String archivo;
        if(cancionSeleccionada!=null){ //GENERA LA CANCION A PARTIR DE LA SELECCIONADA EN EL LISTVIEW
            archivo = new File(ubicacionCarpeta + cancionSeleccionada).getAbsolutePath();
        }
        else{ //NO HAY CANCIONES SELECCIONADAS, GENERA UNA CANCION RANDOM
            archivo = new File(ubicacionCarpeta + nombreCancion ).getAbsolutePath();
            System.out.println(archivo.isEmpty());
        }



        try{
//ERROR!!!!!!!!!!!!!!!!!!!!!!!!!
            me = new Media(new File(archivo).toURI().toString());
            mp = new MediaPlayer(me);


        }catch(Exception e){
            System.out.println(e);
        }

        return mp;
    }





}
