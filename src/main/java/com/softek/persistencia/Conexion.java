package com.softek.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private Connection miConexion;

    public void abrirConexion() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        miConexion = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/northwind",
                "postgres", "Gabriela2050$"
        );
        if (miConexion != null) {
            System.out.println("Conectado con éxito");
        } else {
            System.out.println("No se pudo establecer conexión");
        }
    }

    public Connection getConexion() {
        return miConexion;
    }


}
