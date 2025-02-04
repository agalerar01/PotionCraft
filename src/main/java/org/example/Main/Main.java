package org.example.Main;

import org.example.Controladores.ControlFichAle;
import org.example.Controladores.ControladorJuego;

import java.util.Scanner;

public class Main {

    private static ControladorJuego cJuego;
    private static ControlFichAle cAle;

    public static void main(String[] args) {
        cJuego = ControladorJuego.getInstance();
        cAle = ControlFichAle.getInstance();
        int opc, opcMenuIni;

        if(cAle.existeFichero()){
            opcMenuIni = menuInicio();

            switch (opcMenuIni){
                case 1:
                    cAle.borrarArchivo();
                    cJuego.nuevaPartida();
                    System.out.println("");
                    break;
                case 2:
                    cAle.cargarPartida();
                    System.out.println("");
                    break;
            }
        }

        do{
            opc = menu();
            switch (opc){
                case 1:
                    cJuego.crearPocion();
                    break;

                case 2:
                    cJuego.venderPocion();
                    break;

                case 3:
                    cJuego.comprarIngredientes();
                    break;

                case 4:
                    cJuego.mostrarEstadisticas();
                    break;

                case 5:
                    cJuego.mostrarComerciantes();
                    break;

                case 6:
                    cJuego.mostrarIngrePociones();
                    break;

                case 7:
                    System.out.println("Saliendo...");
                    cJuego.cerrarConexion();
                    cAle.guardarPartida();
                    break;
            }

        }while(opc != 7);
    }

    public static int menuInicio(){
        Scanner es = new Scanner(System.in);
        int opc;

        System.out.println("1. Nueva Partida");
        System.out.println("2. Cargar Partida");
        opc = es.nextInt();

        return opc;
    }

    public static int menu(){
        Scanner es = new Scanner(System.in);
        int opc;

        System.out.println("1. Crear Pocion");
        System.out.println("2. Vender Pociones");
        System.out.println("3. Comprar Ingredientes");
        System.out.println("4. Mostrar estadisticas del juego");
        System.out.println("5. Mostrar Comerciantes");
        System.out.println("6. Mostrar Pociones");
        System.out.println("7. Salir");
        opc = es.nextInt();

        return opc;
    }
}