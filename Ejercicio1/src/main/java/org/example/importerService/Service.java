package org.example.importerService;


import org.example.dataProvider.HibernateUtil;
import org.example.model.Producto;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Service {

    // ============================
    //   IMPORTAR CSV
    // ============================
    public List<Producto> readFromCSV(String filename) {
        List<Producto> productos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String linea = br.readLine(); // saltar cabecera

            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(";");

                String nombre = campos[0];
                BigDecimal precio = new BigDecimal(campos[1].replace(",", "."));
                int stock = Integer.parseInt(campos[2]);
                String categoria = campos[3];

                Producto p = new Producto(nombre, precio, stock, categoria);
                productos.add(p);
            }

            guardarProductosSinDuplicados(productos);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return productos;
    }

    //   GUARDAR SIN DUPLICADOS
    private void guardarProductosSinDuplicados(List<Producto> productos) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            for (Producto producto : productos) {

                // Comprobar si ya existe un producto con ese nombre
                Producto existente = session.createQuery(
                                "FROM Producto WHERE nombre = :nombre", Producto.class)
                        .setParameter("nombre", producto.getNombre())
                        .uniqueResult();

                if (existente == null) {
                    session.persist(producto);
                }
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    //   CONSULTAS
    public List<Producto> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Producto> lista = session.createQuery("FROM Producto", Producto.class).list();
        session.close();
        return lista;
    }

    public List<Producto> findLowStock(Integer min) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Producto> lista = session.createQuery(
                        "FROM Producto WHERE stock = :stock", Producto.class)
                .setParameter("stock", findLowStock(5))
                .list();
        session.close();
        return lista;
    }

    public List<Producto> findByPriceIn(Double min, Double max) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Producto> lista = session.createQuery(
                        "FROM Producto WHERE precio < :maxPrecio", Producto.class)
                .setParameter("maxPrecio", max)
                .list();
        session.close();
        return lista;
    }



    //   Listar Productos
    public void createProducto(List<Producto> productos) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

    }

    // Insertar datos del CSV en la base de datos
    public void guardarProducto(Producto producto) {
        Session session = HibernateUtil.getSessionFactory().openSession();
    }



}

