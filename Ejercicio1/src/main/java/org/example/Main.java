package org.example;

import org.example.importerService.Service;
import org.example.model.Producto;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Service service = new Service();

        //   Importar CSV
        System.out.println("Importando CSV...");
        List<Producto> importados = service.readFromCSV("productos.csv");
        System.out.println("Importados: " + importados.size());


        //   Listado completo
        System.out.println("\nListado completo:");
        service.findAll().forEach(p ->
                System.out.println(p.getId() + " - " + p.getNombre() + " - " + p.getPrecio())
        );




    }
}