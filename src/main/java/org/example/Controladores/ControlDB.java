package org.example.Controladores;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.example.Jugador.*;

import java.util.List;

public class ControlDB {
    public static ControlDB instance;
    private static EntityManager manager;

    private ControlDB() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia-potioncraft");
        manager = emf.createEntityManager();
    }

    public static ControlDB getInstance() {
        if (instance == null) {
            instance = new ControlDB();
        }

        return instance;
    }

    public void crearPocion(InventarioIngrediente i){
        try {
            if(i.getCantidad() != 0) {
                manager.getTransaction().begin();
                manager.merge(i);
                manager.getTransaction().commit();
            }else{
                manager.getTransaction().begin();
                manager.remove(i);
                manager.getTransaction().commit();
            }
        }catch (Exception e){
            System.out.println(e);
            manager.getTransaction().rollback();
        }
    }

    public void aniadirPocionInvent(InventarioPocion p){
        try {
            manager.getTransaction().begin();
            manager.persist(p);
            manager.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e);
            manager.getTransaction().rollback();
        }
    }

    public void sumarPocionInvent(InventarioPocion p){
        try {
            manager.getTransaction().begin();
            manager.merge(p);
            manager.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e);
            manager.getTransaction().rollback();
        }
    }

    public void aniadirIngredienteInvent(InventarioIngrediente i){
        try {
            manager.getTransaction().begin();
            manager.persist(i);
            manager.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e);
            manager.getTransaction().rollback();
        }
    }

    public void sumarIngredienteInvent(InventarioIngrediente i){
        try {
            manager.getTransaction().begin();
            manager.merge(i);
            manager.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e);
            manager.getTransaction().rollback();
        }
    }

    public void venderPocion(List<InventarioPocion> lInPocion){
        for(int i = 0; i < lInPocion.size() ; i++) {
            try {
                manager.getTransaction().begin();
                manager.remove(lInPocion.get(i));
                manager.getTransaction().commit();
            } catch (Exception e) {
                System.out.println(e);
                manager.getTransaction().rollback();
            }
        }
    }

    public void nuevaPartida(){
        List<InventarioIngrediente> lInIngrediente = recuperarInventarioIngredientes();
        List<InventarioPocion> lInPocion = recuperarInventarioPocion();

        if(!lInIngrediente.isEmpty()) {
            for (InventarioIngrediente i : lInIngrediente) {
                manager.getTransaction().begin();
                manager.remove(i);
                manager.getTransaction().commit();
            }
        }

        if(!lInPocion.isEmpty()) {
            for (InventarioPocion p : lInPocion) {
                manager.getTransaction().begin();
                manager.remove(p);
                manager.getTransaction().commit();
            }
        }
    }

    public List<Comerciante> recuperarComerciantes(){
        Query query = manager.createQuery("Select c from Comerciante c", Comerciante.class);

        List<Comerciante> lComerciante = query.getResultList();

        return lComerciante;
    }

    public List<Ingrediente> recuperarIngredientes(){
        Query query = manager.createQuery("Select i from Ingrediente i", Ingrediente.class);

        List<Ingrediente> lIngredientes = query.getResultList();

        return lIngredientes;
    }

    public List<InventarioIngrediente> recuperarInventarioIngredientes(){
        Query query = manager.createQuery("Select i from InventarioIngrediente i", InventarioIngrediente.class);

        List<InventarioIngrediente> lInventarioIngredientes = query.getResultList();

        return lInventarioIngredientes;
    }

    public List<InventarioPocion> recuperarInventarioPocion(){
        Query query = manager.createQuery("Select i from InventarioPocion i", InventarioPocion.class);

        List<InventarioPocion> lInventarioPocion = query.getResultList();

        return lInventarioPocion;
    }

    public List<Pocion> recuperarPocion(){
        Query query = manager.createQuery("Select p from Pocion p", Pocion.class);

        List<Pocion> lPocion = query.getResultList();

        return lPocion;
    }

    public void cerrarConexion(){
        manager.close();
    }
}
