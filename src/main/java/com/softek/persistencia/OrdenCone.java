package com.softek.persistencia;

import com.softek.modelo.DetalleOrden;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class OrdenCone {
    private Conexion conexion;

    public OrdenCone(Conexion conexion) {
        this.conexion = conexion;
    }

    public ResultSet obtenerDetallesOrdenes() throws Exception {
        System.out.println("Detalles de Ordenes: ");
        String consulta = "SELECT * FROM order_details";
        PreparedStatement sentencia = conexion.getConexion().prepareStatement(consulta);
        return sentencia.executeQuery();
    }


    private List<DetalleOrden> extraerDetalles(ResultSet resultSet) throws SQLException {
        List<DetalleOrden> detalles = new ArrayList<>();
        while (resultSet.next()) {
            DetalleOrden detalle = new DetalleOrden(
                    resultSet.getInt("order_id"),
                    resultSet.getInt("product_id"),
                    resultSet.getDouble("unit_price"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("discount")
            );
            detalles.add(detalle);
        }
        return detalles;
    }

    public List<DetalleOrden> filtrarDetallesPorPrecioUnitario(ResultSet resultSet, double umbralPrecio) throws SQLException {
        List<DetalleOrden> detalles = extraerDetalles(resultSet);
        return detalles.stream()
                .filter(detalle -> detalle.getPrecioUnitario() > umbralPrecio)
                .collect(Collectors.toList());
    }

    public List<DetalleOrden> ordenarProductosPorPrecioUnitario(ResultSet resultSet) throws SQLException {
        List<DetalleOrden> detalles = extraerDetalles(resultSet);

        detalles.sort(Comparator.comparing(DetalleOrden::getPrecioUnitario).reversed());
        return detalles;
    }

    public IntSummaryStatistics obtenerEstadisticasCantidad(ResultSet resultSet) throws SQLException {
        List<DetalleOrden> detalles = extraerDetalles(resultSet);
        return detalles.stream()

                .mapToInt(DetalleOrden::getCantidad)
                .summaryStatistics();
    }

    public List<String> obtenerSumaCantidadesPorProducto(ResultSet resultSet) throws SQLException {
        List<DetalleOrden> detalles = extraerDetalles(resultSet);
        return detalles.stream()
                .collect(Collectors.groupingBy(DetalleOrden::getIdProducto, Collectors.summingInt(DetalleOrden::getCantidad)))
                .entrySet().stream()
                .map(e -> "Producto ID: " + e.getKey() + " - Suma : " + e.getValue())
                .collect(Collectors.toList());
    }



}
