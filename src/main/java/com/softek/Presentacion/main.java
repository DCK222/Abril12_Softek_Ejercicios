package com.softek.Presentacion;

import com.softek.modelo.DetalleOrden;
import com.softek.persistencia.Conexion;
import com.softek.persistencia.OrdenCone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;

public class main {
    public static void main(String[] args) throws Exception {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();
        OrdenCone servicio = new OrdenCone(conexion);

        try {

            ResultSet res = servicio.obtenerDetallesOrdenes();
            List<DetalleOrden> detallesFiltrados = servicio.filtrarDetallesPorPrecioUnitario(res, 30.0);
            detallesFiltrados.forEach(d -> System.out.println("Numero : " + d.getIdOrden() + ", ID_Producto : " + d.getIdProducto() + ", Precio : " + d.getPrecioUnitario()));
            System.out.println("------------------------------------------------------------------------------");
            res.close();


            res = servicio.obtenerDetallesOrdenes();
            List<DetalleOrden> detallesOrdenados = servicio.ordenarProductosPorPrecioUnitario(res);
            System.out.println("Productos Ordenados :");
            detallesOrdenados.forEach(d -> System.out.println("ID_Producto: " + d.getIdProducto() + ", Precio: " + d.getPrecioUnitario()));
            System.out.println("------------------------------------------------------------------------------");
            res.close();

            res = servicio.obtenerDetallesOrdenes();
                IntSummaryStatistics estadisticas = servicio.obtenerEstadisticasCantidad(res);
            System.out.println("Cantidad Máxima : " + estadisticas.getMax() + ", Mínimo: " + estadisticas.getMin() + ", Promedio: " + estadisticas.getAverage());
            System.out.println("------------------------------------------------------------------------------");
            res.close();


            res = servicio.obtenerDetallesOrdenes();
            List<String> sumaCantidadesPorProducto = servicio.obtenerSumaCantidadesPorProducto(res);
            sumaCantidadesPorProducto.forEach(System.out::println);
            System.out.println("------------------------------------------------------------------------------");
            res.close();

        } finally {

            if (conexion.getConexion() != null) {
                conexion.getConexion().close();
            }
        }

    }
}
