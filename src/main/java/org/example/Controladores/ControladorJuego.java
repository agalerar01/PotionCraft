package org.example.Controladores;

import org.example.Jugador.*;
import org.example.Main.Main;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ControladorJuego {

    private static ControladorJuego instance;
    private ControlDB db;
    private static int reputacion;

    private ControladorJuego(){
        db = ControlDB.getInstance();
    }

    public static ControladorJuego getInstance(){
        if(instance == null){
            instance = new ControladorJuego();
        }

        return instance;
    }

    public void crearPocion(){
        List<Pocion> lPociones = db.recuperarPocion();
        List<Pocion> lPocionesDisponibles = new ArrayList<>();
        List<InventarioPocion> lInPociones = db.recuperarInventarioPocion();
        List<InventarioIngrediente> lInIngrediente = db.recuperarInventarioIngredientes();
        List<Ingrediente> misIngredientes = new ArrayList<>();
        int opc, contPositivo = 0, contNegativo = 0, numeroPocion = 1;
        boolean noIngredientes, existe;

        for(int i = 0 ; i <lInIngrediente.size() ; i++){
            misIngredientes.add(lInIngrediente.get(i).getIngrediente());
        }

        System.out.println("=== Pociones disponibles para fabricar ===");
        for(int i = 0; i < lPociones.size() ; i++){
            List<Ingrediente> lIngredientePocion = lPociones.get(i).getlIngredientes();
            noIngredientes = false;
            for(int j = 0 ; j < lIngredientePocion.size() ; j++){
                if (!misIngredientes.contains(lIngredientePocion.get(j))) {
                    noIngredientes = true;
                }
            }

            if (!noIngredientes){
                System.out.println(numeroPocion+". "+lPociones.get(i).getNombre());
                lPocionesDisponibles.add(lPociones.get(i));
                System.out.println(lPociones.get(i).mostrarIngredientesCrear());
                numeroPocion++;
            }
        }
        System.out.println("0. Cancelar");
        System.out.println("Cual quieres elaborar: ");
        opc = pedirInt();

        if(opc > lPocionesDisponibles.size()){
            do {
                System.out.println("Esa pocion no se encuenta en la lista actual. Introduce otra: ");
                opc = pedirInt();
            }while(opc > lPocionesDisponibles.size());
        }

        if (opc != 0) {
            System.out.println("Has elegido elaborar: "+lPocionesDisponibles.get(opc-1).getNombre());
            for(int i = 0 ; i < lPocionesDisponibles.get(opc-1).getlIngredientes().size() ; i++){
                if(lPocionesDisponibles.get(opc-1).getlIngredientes().get(i).getEfectoPositivo() != null){
                    contPositivo++;
                }else if(lPocionesDisponibles.get(opc-1).getlIngredientes().get(i).getEfectoNegativo() != null){
                    contNegativo++;
                }
            }

            if(contPositivo > contNegativo){
                aumentarRep();
            }else if(contPositivo < contNegativo){
                disminuirRep();
            }

            System.out.println("Reputacion actualizada: "+reputacion+" (Positivos: "+contPositivo+", Negativos: "+contNegativo+")");
            System.out.println("Pocion fabricada con exito: "+lPocionesDisponibles.get(opc-1).getNombre());

            existe = false;
            InventarioPocion p = new InventarioPocion(1, lPocionesDisponibles.get(opc-1));
            for (int j = 0; j < lInPociones.size(); j++) {
                if (lInPociones.get(j).getPocion() == lPocionesDisponibles.get(opc - 1)) {
                    existe = true;
                    lInPociones.get(j).setCantidad(lInPociones.get(j).getCantidad()+1);
                    p = lInPociones.get(j);
                }
            }
            if(existe){
                db.sumarPocionInvent(p);
            }else{
                db.aniadirPocionInvent(p);
            }

            for(int i = 0; i < lInIngrediente.size() ; i++){
                lInIngrediente.get(i).setCantidad(lInIngrediente.get(i).getCantidad()-1);
                db.crearPocion(lInIngrediente.get(i));
            }
        }else{
            System.out.println("Volviendo al menu...");
        }

        System.out.println("");
    }

    public void venderPocion(){
        List<InventarioPocion> lInPocion = db.recuperarInventarioPocion();
        double total = 0;

        System.out.println("=== Vendiendo todas las pociones disponibles ===");

        for(int i = 0; i < lInPocion.size() ; i++){
            int cant = lInPocion.get(i).getCantidad();
            double precio = 0,dinero = 0;
            List<Ingrediente> lPocionIngrediente = lInPocion.get(i).getPocion().getlIngredientes();

            for(int j = 0 ; j <lPocionIngrediente.size() ; j++){
                precio += lPocionIngrediente.get(j).getPrecioCompra();
            }
            dinero = (precio+(precio*0.1))*cant;
            total += dinero;
            System.out.println("Has vendido "+lInPocion.get(i).getCantidad()+" unidades de "+lInPocion.get(i).getPocion().getNombre()+" por "+String.format("%.2f",dinero)+" monedas de oro.");
        }
        System.out.println("Total de ganancias: "+String.format("%.2f",total)+" monedas de oro.");
        System.out.println("");
        db.venderPocion(lInPocion);
    }

    public void comprarIngredientes(){
        List<Comerciante> lComerciante = db.recuperarComerciantes();
        int comercianteRandom = (int)(Math.random()* lComerciante.size()), opc = 0, cant = 0;

        List<Ingrediente> lIngrediente = lComerciante.get(comercianteRandom).getlIngredientes();
        List<Ingrediente> lIngredienteUsado = new ArrayList<>();

        System.out.println("Visita: "+lComerciante.get(comercianteRandom).getNombre()+" ("+lComerciante.get(comercianteRandom).getTipo().toString()+")");

        List<Integer> lNumUsados = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            int ingredienteRandom = (int)(Math.random()*lIngrediente.size());

            if (!lNumUsados.contains(ingredienteRandom)) {
                lIngredienteUsado.add(lIngrediente.get(ingredienteRandom));
            }else{
                i--;
            }

            lNumUsados.add(ingredienteRandom);
        }

        do {
            boolean existe = false;
            System.out.println("=== Menu de Compra ===");
            for(int i = 0; i < lIngredienteUsado.size(); i++){
                System.out.println((i + 1) + ". " + lIngredienteUsado.get(i).mostrarIngrediente());
            }

            lNumUsados.clear();
            System.out.println("0. Salir");
            System.out.println("Seleccione el numero del ingrediente que desea comprar: ");
            opc = pedirInt();

            if(opc > lIngrediente.size()){
                do {
                    System.out.println("Ese Ingrediente no se encuenta en la lista actual. Introduce otra: ");
                    opc = pedirInt();
                }while(opc > lIngrediente.size());
            }

            if(opc != 0) {
                System.out.println("Has seleccionado: " + lIngrediente.get(opc - 1).mostrarIngrediente());
                System.out.println("¿Cuantas unidades desea comprar?");
                cant = pedirInt();

                System.out.println("Has comprado " + cant + " unidades de " + lIngrediente.get(opc - 1).getNombre() + " por " + String.format("%.2f", lIngrediente.get(opc - 1).getPrecioCompra() * cant) + " monedas de oro");
                System.out.println("");
                InventarioIngrediente i = new InventarioIngrediente(cant, lIngrediente.get(opc - 1));

                List<InventarioIngrediente> lInIngrediente = db.recuperarInventarioIngredientes();
                for (int j = 0; j < lInIngrediente.size(); j++) {
                    if (lInIngrediente.get(j).getIngrediente() == lIngrediente.get(opc - 1)) {
                        existe = true;
                        lInIngrediente.get(j).setCantidad(lInIngrediente.get(j).getCantidad()+cant);
                        i = lInIngrediente.get(j);
                    }
                }

                if (existe) {
                    db.sumarIngredienteInvent(i);
                } else {
                    db.aniadirIngredienteInvent(i);
                }
            }
        }while(opc != 0);
        System.out.println("Volviendo al menu");
        System.out.println("");
    }

    public void mostrarEstadisticas(){
        List<InventarioPocion> lInPocion = db.recuperarInventarioPocion();
        List<InventarioIngrediente> lInIngrediente = db.recuperarInventarioIngredientes();

        System.out.println("REPUTACION: "+reputacion);
        System.out.println("");

        System.out.println("INVENTARIO INGREDIENTES: ");
        if(!lInIngrediente.isEmpty()) {
            for (int i = 0; i < lInIngrediente.size(); i++) {
                System.out.println(lInIngrediente.get(i).mostrarIngrediente());
            }
        }
        System.out.println("");

        System.out.println("INVENTARIO Pocion: ");
        if(!lInPocion.isEmpty()) {
            for (int i = 0; i < lInPocion.size(); i++) {
                System.out.println(lInPocion.get(i).mostrarPocion());
            }
        }
        System.out.println("");
    }

    public void mostrarComerciantes(){
        List<Comerciante> lComerciante = db.recuperarComerciantes();

        System.out.println("=== Posibles comerciantes ===");
        for(int i = 0 ; i < lComerciante.size(); i++){
            System.out.println(lComerciante.get(i).mostrarComerciante());
        }
        System.out.println("");
    }

    public void mostrarIngrePociones(){
        List<Pocion> lPociones = db.recuperarPocion();

        System.out.println("=== Pociones disponibles ===");
        for(Pocion p : lPociones){
            p.mostrarIngredientes();
        }
    }

    public int pedirInt() {
        Scanner es = new Scanner(System.in);
        int num = 0;
        boolean error;

        do{
            try{
                num = es.nextInt();
                error = false;
            }catch (InputMismatchException e){
                error = true;
                System.out.println("Error Introduce de nuevo el valor");
                es.nextLine();
            }
        }while(error == true);

        return num;
    }

    public String pedirString() {
        Scanner es = new Scanner(System.in);
        String string = es.nextLine();

        return string;
    }

    public void nuevaPartida(){
        db.nuevaPartida();
        setRep(0);
    }

    public void cerrarConexion(){
        db.cerrarConexion();
    }


    public static void aumentarRep(){
        reputacion++;
    }

    public static void disminuirRep(){
        reputacion--;
    }

    public static int getRep(){
        return reputacion;
    }

    public static void setRep(int rep){
        reputacion = rep;
    }
}
