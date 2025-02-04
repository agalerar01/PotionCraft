package org.example.Controladores;

import org.example.Main.Main;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ControlFichAle {

    private static ControlFichAle instance;
    private final String RUTA = "src/main/resources/reputacion.dat";

    private ControlFichAle(){

    }

    public static ControlFichAle getInstance(){
        if(instance == null){
            instance = new ControlFichAle();
        }

        return instance;
    }

    public void guardarPartida(){
        try{
            RandomAccessFile raf;
            Path ruta = Paths.get(RUTA);
            if(Files.exists(ruta)) {
                borrarArchivo();
                raf = new RandomAccessFile(RUTA, "rw");
            }else{
                Files.createFile(ruta);
                raf = new RandomAccessFile(RUTA, "rw");
            }

            raf.writeInt(ControladorJuego.getRep());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void cargarPartida(){
        try{
            Path ruta = Paths.get(RUTA);
            if(Files.exists(ruta)) {
                RandomAccessFile raf = new RandomAccessFile(RUTA, "r");

                while(raf.getFilePointer() < raf.length()){
                    int rep = raf.readInt();
                    ControladorJuego.setRep(rep);
                }
            }else{
                System.out.println("El archivo no existe");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void borrarArchivo(){
        Path ruta = Paths.get(RUTA);
        try {
            if(Files.exists(ruta)) {
                Files.delete(ruta);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public boolean existeFichero(){
        Path ruta = Paths.get(RUTA);
        return Files.exists(ruta);
    }
}
