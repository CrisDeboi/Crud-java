package testcrudo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

public class Modelo {

   public String sURL = "jdbc:mariadb://localhost:3306/Tienda"; // Especifica la base de datos aqui
    public String user = "root";
    public String password = "";

    public Modelo() {
       try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/", "root", ""); Statement stmt = conn.createStatement()) {

            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS Tienda";
            stmt.executeUpdate(createDatabaseSQL);

            try (Connection connTienda = DriverManager.getConnection("jdbc:mariadb://localhost:3306/Tienda", "root", ""); Statement stmtTienda = connTienda.createStatement()) {

                String createCustomersTableSQL = "CREATE TABLE IF NOT EXISTS customers ("
                        + "customer_id INT NOT NULL AUTO_INCREMENT, "
                        + "customer_name VARCHAR(50) NOT NULL, "
                        + "PRIMARY KEY (customer_id)"
                        + ")";
                stmtTienda.executeUpdate(createCustomersTableSQL);

                String createOrdersTableSQL = "CREATE TABLE IF NOT EXISTS orders ("
                        + "order_id INT NOT NULL AUTO_INCREMENT, "
                        + "order_date DATE NOT NULL, "
                        + "customer_id INT NOT NULL, "
                        + "PRIMARY KEY (order_id), "
                        + "FOREIGN KEY (customer_id) REFERENCES customers(customer_id) "
                        + "ON DELETE CASCADE ON UPDATE CASCADE"
                        + ")";
                stmtTienda.executeUpdate(createOrdersTableSQL);

                String createShipmentsTableSQL = "CREATE TABLE IF NOT EXISTS shipments ("
                        + "shipment_id INT NOT NULL AUTO_INCREMENT, "
                        + "shipment_date DATE NOT NULL, "
                        + "order_id INT NOT NULL, "
                        + "PRIMARY KEY (shipment_id), "
                        + "FOREIGN KEY (order_id) REFERENCES orders(order_id) "
                        + "ON DELETE CASCADE ON UPDATE CASCADE"
                        + ")";
                stmtTienda.executeUpdate(createShipmentsTableSQL);

                System.out.println("Base de datos y tablas creadas exitosamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }
     

   

    public void insertarCliente(String nombre) {
        String sql = "INSERT INTO customers (customer_name) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.executeUpdate();

            System.out.println("Cliente insertado con exito");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void borrarCliente(int id) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            System.out.println("Cliente eliminado con exito");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> consultarPedidosIDCliente(int id) {
        List<String[]> pedidos = new ArrayList<>();
        String sql = "SELECT "
                + "c.customer_name, "
                + "o.order_id, "
                + "o.order_date, "
                + "CASE WHEN s.shipment_id IS NOT NULL THEN 'true' ELSE 'false' END AS shipment, "
                + "s.shipment_id, "
                + "s.shipment_date "
                + "FROM orders o "
                + "JOIN customers c ON o.customer_id = c.customer_id "
                + "LEFT JOIN shipments s ON o.order_id = s.order_id "
                + "WHERE c.customer_id = ?";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String[] row = new String[6];
                    row[0] = rs.getString("customer_name");
                    row[1] = String.valueOf(rs.getInt("order_id"));
                    row[2] = rs.getDate("order_date").toString();
                    row[3] = rs.getString("shipment");
                    row[4] = rs.getString("shipment_id") != null ? String.valueOf(rs.getInt("shipment_id")) : "N/A";
                    row[5] = rs.getDate("shipment_date") != null ? rs.getDate("shipment_date").toString() : "N/A";
                    pedidos.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }

    public List<String[]> consultarPedidosIDPedido(int id) {
        List<String[]> pedidos = new ArrayList<>();
        String sql = "SELECT "
                + "c.customer_name, "
                + "o.order_id, "
                + "o.order_date, "
                + "CASE WHEN s.shipment_id IS NOT NULL THEN 'true' ELSE 'false' END AS shipment, "
                + "s.shipment_id, "
                + "s.shipment_date "
                + "FROM orders o "
                + "JOIN customers c ON o.customer_id = c.customer_id "
                + "LEFT JOIN shipments s ON o.order_id = s.order_id "
                + "WHERE o.order_id = ?";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String[] row = new String[6];
                    row[0] = rs.getString("customer_name");
                    row[1] = String.valueOf(rs.getInt("order_id"));
                    row[2] = rs.getDate("order_date").toString();
                    row[3] = rs.getString("shipment");
                    row[4] = rs.getString("shipment_id") != null ? String.valueOf(rs.getInt("shipment_id")) : "N/A";
                    row[5] = rs.getDate("shipment_date") != null ? rs.getDate("shipment_date").toString() : "N/A";
                    pedidos.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }

    public List<String[]> obtenerClientes() {
        List<String[]> clientes = new ArrayList<>();
        String sql = "SELECT customer_id, customer_name FROM customers";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String[] cliente = new String[2];
                cliente[0] = String.valueOf(rs.getInt("customer_id"));
                cliente[1] = rs.getString("customer_name");
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    public void actualizarCliente(int idViejo, int idNuevo, String nombre) {
        String sql = "UPDATE customers SET customer_id = ?, customer_name = ? WHERE customer_id = ?";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idNuevo);
            pstmt.setString(2, nombre);
            pstmt.setInt(3, idViejo);
            pstmt.executeUpdate();

            System.out.println("Cliente actualizado con exito");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("El ID del cliente ya existe");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//-------------------------------------------------------------
    public List<String[]> obtenerPedidos() {
        List<String[]> pedidos = new ArrayList<>();
        String sql = "SELECT order_id, order_date, customer_id FROM orders";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String[] pedido = new String[3];
                pedido[0] = String.valueOf(rs.getInt("order_id"));
                pedido[1] = rs.getDate("order_date").toString();
                pedido[2] = String.valueOf(rs.getInt("customer_id"));
                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }

    public List<String> obtenerNombreClientes() {
        List<String> nombres = new ArrayList<>();
        String sql = "SELECT customer_name FROM customers";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                nombres.add(rs.getString("customer_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombres;
    }

    public Map<Integer, String> obtenerInfoCliente() {
        Map<Integer, String> info = new HashMap<>();
        String sql = "SELECT customer_id, customer_name FROM customers";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                info.put(rs.getInt("customer_id"), rs.getString("customer_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return info;
    }

    public void insertarPedido(String fecha, int id) {
        String sql = "INSERT INTO orders (order_date, customer_id) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, java.sql.Date.valueOf(fecha));
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

            System.out.println("Pedido insertado con exito");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPedido(int id) {
        String sql = "DELETE FROM orders WHERE order_id = ?";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            System.out.println("Pedido eliminado cone exito");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarPedido(int idViejo, int idNuevo, String nuevaFecha, int idCliente) {
        String sqlUpdateOrder = "UPDATE orders SET order_id = ?, order_date = ?, customer_id = ? WHERE order_id = ?";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sqlUpdateOrder)) {
            pstmt.setInt(1, idNuevo);
            pstmt.setDate(2, java.sql.Date.valueOf(nuevaFecha));
            pstmt.setInt(3, idCliente);
            pstmt.setInt(4, idViejo);
            pstmt.executeUpdate();

            System.out.println("Pedido actualizado con exito");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertarPedido(String fecha, int idCliente, int idPedido) {
        String sql = "INSERT INTO orders (order_id, order_date, customer_id) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idPedido);
            pstmt.setDate(2, java.sql.Date.valueOf(fecha));
            pstmt.setInt(3, idCliente);
            pstmt.executeUpdate();

            System.out.println("Pedido insertado con exito");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//--------------------------------------------------------
    public List<String[]> obtenerEnvios() {
        List<String[]> envios = new ArrayList<>();
        String sql = "SELECT shipment_id, shipment_date, order_id FROM shipments";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String[] envio = new String[3];
                envio[0] = String.valueOf(rs.getInt("shipment_id"));
                envio[1] = rs.getDate("shipment_date").toString();
                envio[2] = String.valueOf(rs.getInt("order_id"));
                envios.add(envio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return envios;
    }

    public List<Integer> obtenerIDPedidos() {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT order_id FROM orders";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ids.add(rs.getInt("order_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ids;
    }

    public void insertarEnvio(String fecha, int id) {
        String sql = "INSERT INTO shipments (shipment_date, order_id) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, java.sql.Date.valueOf(fecha));
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

            System.out.println("Envio insertado con exito");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarEnvio(int id) {
        String sql = "DELETE FROM shipments WHERE shipment_id = ?";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            System.out.println("Envio borrado con exito");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarEnvio(int idViejo, int idNuevo, String fecha, int idPedido) {
        if (idViejo != idNuevo) {
            // Eliminar el registro antiguo
            eliminarEnvio(idViejo);
            // Insertar el nuevo registro
            insertarEnvioID(fecha, idPedido, idNuevo);
        } else {
            String sql = "UPDATE shipments SET shipment_date = ?, order_id = ? WHERE shipment_id = ?";

            try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setDate(1, java.sql.Date.valueOf(fecha));
                pstmt.setInt(2, idPedido);
                pstmt.setInt(3, idViejo);
                pstmt.executeUpdate();

                System.out.println("Envio acutalizado con exito");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertarEnvioID(String fecha, int idPedido, int idEnvio) {
        String sql = "INSERT INTO shipments (shipment_id, shipment_date, order_id) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idEnvio);
            pstmt.setDate(2, java.sql.Date.valueOf(fecha));
            pstmt.setInt(3, idPedido);
            pstmt.executeUpdate();

            System.out.println("Envio insertado con exito");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//--------------------------------------------------------
}
