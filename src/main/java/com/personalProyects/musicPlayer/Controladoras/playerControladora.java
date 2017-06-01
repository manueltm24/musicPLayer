package com.personalProyects.musicPlayer.Controladoras;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;



import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mt on 19/05/17.
 */
public class playerControladora {
    //Variables Generales
    URL urlArchivo = playerControladora.class.getClassLoader().getResource("Musica/");
    String ubicacionCarpeta = urlArchivo.getPath();

    private MediaPlayer mp;
    public Media me;
    Random numeroRandom = new Random();
    int max=leerArchivosMP3Carpeta(ubicacionCarpeta).size();
    int min=1;

    @FXML
    public void initialize() {
        //REPRODUCE CANCION AL INICIAR PROGRAMA
        mp3(null).play();
    }







    //Botones y Atrivutos
    @FXML
    private ListView<String> listviewAlbums;
    @FXML
    private ListView<String> listviewCanciones;

    @FXML
    void llenarListViewAlbums(ActionEvent event) {
        ArrayList<File> archivosLeidos = leerArchivosMP3Carpeta(ubicacionCarpeta);
        for(int i=0;i<archivosLeidos.size();i++){
            listviewAlbums.setItems(FXCollections.observableArrayList(leerAlbumsCanciones(archivosLeidos)));
        }
        if(archivosLeidos.isEmpty()){
            System.out.println("Carpeta Vacia");
        }
    }

    @FXML
    void llenarListViewCanciones(ActionEvent event) {
        ArrayList<String> listaCanciones = leerNombresCanciones(leerArchivosMP3Carpeta(ubicacionCarpeta));
        ObservableList<String> nombreCanciones = FXCollections.observableArrayList(listaCanciones);
        listviewCanciones.setItems(nombreCanciones);
    }

    @FXML
    void play(ActionEvent event) {
        //CHEQUIAR FUNCION OJO!!
        String cancionSeleccionada = listviewCanciones.getSelectionModel().getSelectedItems().toString();
        String nuevaCadena = leerNombrePath(leerArchivosMP3Carpeta(ubicacionCarpeta), EliminaCaracteres(cancionSeleccionada,"[]"));

        if(nuevaCadena!=null){
            //SI SELECCIONAS CANCION LA REPRODUCE
            mp.stop();
            mp3(nuevaCadena).play();
        }
        else{
            //SI NO HAY CANCION SELECCIONADA
            System.out.println("SELECCION UNA CANCION");
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

    public ArrayList<String> leerAlbumsCanciones(ArrayList<File> listaCanciones){
        //File cancion = songs.get(1);
        ArrayList<String> listaNombresAlbums=new ArrayList<String>();
        String albumAnterior = " ";
        try {
            for(int i=0; i<listaCanciones.size(); i++ ){
                FileInputStream file = new FileInputStream(listaCanciones.get(i));
                int size = (int)listaCanciones.get(i).length();
                file.skip(size - 128);
                byte[] last128 = new byte[128];
                file.read(last128);
                String id3 = new String(last128);
                String tag = id3.substring(0, 3);
                if (tag.equals("TAG")) {
                    if(!listaNombresAlbums.contains(id3.substring(33,62))){
                        listaNombresAlbums.add(id3.substring(33,62));
                    }
                    //System.out.println("Title: " + id3.substring(3, 32));
                    //System.out.println("Artist: " + id3.substring(33, 62));
                    //System.out.println("Album: " + id3.substring(63, 91));
                    //System.out.println("Year: " + id3.substring(93, 97));
                } else{
                    System.out.println("ERROR EN LOS ATRIVUTOS DE LAS CANCION!");
                }
                file.close();
            }
        } catch (Exception e) {
            System.out.println("Error - " + e.toString());
        }
        return listaNombresAlbums;

    }

    public ArrayList<String> leerNombresCanciones(ArrayList<File> listaCanciones){

        String Album = listviewAlbums.getSelectionModel().getSelectedItem().toString();
        ArrayList<String> listaNombresCanciones=new ArrayList<String>();


        try {
            for(int i=0; i<listaCanciones.size(); i++ ){
                FileInputStream file = new FileInputStream(listaCanciones.get(i));
                int size = (int)listaCanciones.get(i).length();
                file.skip(size - 128);
                byte[] last128 = new byte[128];
                file.read(last128);
                String id3 = new String(last128);
                String tag = id3.substring(0, 3);

                if (tag.equals("TAG")) {

                    if(Album.equals(id3.substring(33,62))){
                        listaNombresCanciones.add(id3.substring(3,32));
                    }
                }
                else{
                    System.out.println("ERROR EN LOS ATRIVUTOS!");
                }
                file.close();
            }
        } catch (Exception e) {
            System.out.println("Error - " + e.toString());
        }
        return listaNombresCanciones;

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
        }



        try{
            me = new Media(new File(archivo).toURI().toString());
            mp = new MediaPlayer(me);


        }catch(Exception e){
            System.out.println(e);
        }

        return mp;
    }

    public String EliminaCaracteres(String cancionSeleccionada, String s_caracteres) {
        String stringValido = "";
        Character caracter = null;
        boolean valido = true;

        for (int i=0; i<cancionSeleccionada.length(); i++) {
            valido = true;
            for (int j=0; j<s_caracteres.length(); j++) {
                caracter = s_caracteres.charAt(j);

                if (cancionSeleccionada.charAt(i) == caracter) {
                    valido = false;
                    break;
                }
            }
            if (valido)
                stringValido += cancionSeleccionada.charAt(i);
        }

        return stringValido;
    }

    public String leerNombrePath(ArrayList<File> listaCanciones, String Cancion) {
        try {
            for (int i = 0; i < listaCanciones.size(); i++) {
                FileInputStream file = new FileInputStream(listaCanciones.get(i));
                int size = (int) listaCanciones.get(i).length();
                file.skip(size - 128);
                byte[] last128 = new byte[128];
                file.read(last128);
                String id3 = new String(last128);
                String tag = id3.substring(0, 3);
                if (tag.equals("TAG")) {
                    if (id3.substring(3, 32).equals(listviewCanciones.getSelectionModel().getSelectedItem().toString())) {
                        return listaCanciones.get(i).getName();
                    }

                } else {
                    System.out.println("ERROR EN LOS ATRIVUTOS!");
                }
                file.close();
            }
        } catch (Exception e) {
            System.out.println("Error - " + e.toString());
        }
        return null;
    }






}
