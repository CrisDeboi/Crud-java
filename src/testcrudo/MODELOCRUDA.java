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

public class MODELOCRUDA {

    private final String sURL = "jdbc:mariadb://localhost:3306/Jose"; // Especifica la base de datos aqui
    private final String user = "root";
    private final String password = "";

    public void createDatabaseAndTables() {
        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/", user, password); Statement stmt = conn.createStatement()) {

            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS Jose";
            stmt.executeUpdate(createDatabaseSQL);

            try (Connection connTienda = DriverManager.getConnection(sURL, user, password); Statement stmtTienda = connTienda.createStatement()) {

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

    public void insertCustomer(String customerName) {
        String sql = "INSERT INTO customers (customer_name) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customerName);
            pstmt.executeUpdate();

            System.out.println("Customer inserted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(int customerId) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();

            System.out.println("Customer deleted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> consultarOrdersByCustomerId(int customerId) {
        List<String[]> results = new ArrayList<>();
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

            pstmt.setInt(1, customerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String[] row = new String[6];
                    row[0] = rs.getString("customer_name");
                    row[1] = String.valueOf(rs.getInt("order_id"));
                    row[2] = rs.getDate("order_date").toString();
                    row[3] = rs.getString("shipment");
                    row[4] = rs.getString("shipment_id") != null ? String.valueOf(rs.getInt("shipment_id")) : "N/A";
                    row[5] = rs.getDate("shipment_date") != null ? rs.getDate("shipment_date").toString() : "N/A";
                    results.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public List<String[]> consultarOrdersByOrderId(int orderId) {
        List<String[]> results = new ArrayList<>();
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

            pstmt.setInt(1, orderId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String[] row = new String[6];
                    row[0] = rs.getString("customer_name");
                    row[1] = String.valueOf(rs.getInt("order_id"));
                    row[2] = rs.getDate("order_date").toString();
                    row[3] = rs.getString("shipment");
                    row[4] = rs.getString("shipment_id") != null ? String.valueOf(rs.getInt("shipment_id")) : "N/A";
                    row[5] = rs.getDate("shipment_date") != null ? rs.getDate("shipment_date").toString() : "N/A";
                    results.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public List<String[]> getCustomers() {
        List<String[]> customers = new ArrayList<>();
        String sql = "SELECT customer_id, customer_name FROM customers";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String[] customer = new String[2];
                customer[0] = String.valueOf(rs.getInt("customer_id"));
                customer[1] = rs.getString("customer_name");
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public void updateCustomer(int oldCustomerId, int newCustomerId, String newCustomerName) {
        String sql = "UPDATE customers SET customer_id = ?, customer_name = ? WHERE customer_id = ?";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newCustomerId);
            pstmt.setString(2, newCustomerName);
            pstmt.setInt(3, oldCustomerId);
            pstmt.executeUpdate();

            System.out.println("Customer updated successfully.");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("El ID del cliente ya existe. Por favor, pruebe con otro ID.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//-------------------------------------------------------------
    public List<String[]> getAllOrders() {
        List<String[]> orders = new ArrayList<>();
        String sql = "SELECT order_id, order_date, customer_id FROM orders";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String[] order = new String[3];
                order[0] = String.valueOf(rs.getInt("order_id"));
                order[1] = rs.getDate("order_date").toString();
                order[2] = String.valueOf(rs.getInt("customer_id"));
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public List<String> getCustomerNames() {
        List<String> customerNames = new ArrayList<>();
        String sql = "SELECT customer_name FROM customers";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                customerNames.add(rs.getString("customer_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerNames;
    }

    public Map<Integer, String> getCustomerData() {
        Map<Integer, String> customerData = new HashMap<>();
        String sql = "SELECT customer_id, customer_name FROM customers";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                customerData.put(rs.getInt("customer_id"), rs.getString("customer_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerData;
    }

    public void insertOrder(String orderDate, int customerId) {
        String sql = "INSERT INTO orders (order_date, customer_id) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, java.sql.Date.valueOf(orderDate));
            pstmt.setInt(2, customerId);
            pstmt.executeUpdate();

            System.out.println("Order inserted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE order_id = ?";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            pstmt.executeUpdate();

            System.out.println("Order deleted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOrder(int oldOrderId, int newOrderId, String newOrderDate, int newCustomerId) {
        String sqlUpdateOrder = "UPDATE orders SET order_id = ?, order_date = ?, customer_id = ? WHERE order_id = ?";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sqlUpdateOrder)) {
            pstmt.setInt(1, newOrderId);
            pstmt.setDate(2, java.sql.Date.valueOf(newOrderDate));
            pstmt.setInt(3, newCustomerId);
            pstmt.setInt(4, oldOrderId);
            pstmt.executeUpdate();

            System.out.println("Order updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertOrder(String orderDate, int customerId, int orderId) {
        String sql = "INSERT INTO orders (order_id, order_date, customer_id) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            pstmt.setDate(2, java.sql.Date.valueOf(orderDate));
            pstmt.setInt(3, customerId);
            pstmt.executeUpdate();

            System.out.println("Order inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//--------------------------------------------------------
    public List<String[]> getShipments() {
        List<String[]> shipments = new ArrayList<>();
        String sql = "SELECT shipment_id, shipment_date, order_id FROM shipments";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String[] shipment = new String[3];
                shipment[0] = String.valueOf(rs.getInt("shipment_id"));
                shipment[1] = rs.getDate("shipment_date").toString();
                shipment[2] = String.valueOf(rs.getInt("order_id"));
                shipments.add(shipment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return shipments;
    }

    public List<Integer> getOrderIds() {
        List<Integer> orderIds = new ArrayList<>();
        String sql = "SELECT order_id FROM orders";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                orderIds.add(rs.getInt("order_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderIds;
    }

    public void insertShipment(String shipmentDate, int orderId) {
        String sql = "INSERT INTO shipments (shipment_date, order_id) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, java.sql.Date.valueOf(shipmentDate));
            pstmt.setInt(2, orderId);
            pstmt.executeUpdate();

            System.out.println("Shipment inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteShipment(int shipmentId) {
        String sql = "DELETE FROM shipments WHERE shipment_id = ?";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, shipmentId);
            pstmt.executeUpdate();

            System.out.println("Shipment deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateShipment(int oldShipmentId, int newShipmentId, String newShipmentDate, int newOrderId) {
        if (oldShipmentId != newShipmentId) {
            // Eliminar el registro antiguo
            deleteShipment(oldShipmentId);
            // Insertar el nuevo registro
            insertShipmentWithId(newShipmentDate, newOrderId, newShipmentId);
        } else {
            String sql = "UPDATE shipments SET shipment_date = ?, order_id = ? WHERE shipment_id = ?";

            try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setDate(1, java.sql.Date.valueOf(newShipmentDate));
                pstmt.setInt(2, newOrderId);
                pstmt.setInt(3, oldShipmentId);
                pstmt.executeUpdate();

                System.out.println("Shipment updated successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertShipmentWithId(String shipmentDate, int orderId, int shipmentId) {
        String sql = "INSERT INTO shipments (shipment_id, shipment_date, order_id) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(sURL, user, password); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, shipmentId);
            pstmt.setDate(2, java.sql.Date.valueOf(shipmentDate));
            pstmt.setInt(3, orderId);
            pstmt.executeUpdate();

            System.out.println("Shipment inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//--------------------------------------------------------
}
