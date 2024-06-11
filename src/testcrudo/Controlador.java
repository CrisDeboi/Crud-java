package testcrudo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controlador {

    private Modelo modelo;
    private Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void cargarClientes() {
        List<String[]> customers = modelo.obtenerClientes();
        vista.actualizarTablaClientes(customers);
    }

    public void insertarCliente(String customerName) {
        modelo.insertarCliente(customerName);
        cargarClientes();
        cargarNombresClientes(); // Actualizar el jComboBox1
        cargarOrders(); // Actualizar la tabla de órdenes
    }

    public void eliminarCliente(int customerId) {
        modelo.borrarCliente(customerId);
        cargarClientes();
        cargarNombresClientes(); // Actualizar el jComboBox1
        cargarOrders(); // Actualizar la tabla de órdenes
        cargarShipments();
    }

    public void actualizarCliente(int oldCustomerId, int newCustomerId, String newCustomerName) {
        modelo.actualizarCliente(oldCustomerId, newCustomerId, newCustomerName);
        cargarClientes();
        cargarNombresClientes(); // Actualizar el jComboBox1
        cargarOrders(); // Actualizar la tabla de órdenes
    }

    public void consultarOrdersByCustomerId(int customerId) {
        List<String[]> orders = modelo.consultarPedidosIDCliente(customerId);
        vista.actualizarTablaOrders(orders);
    }

    //---------------------------------------------------------
    public void cargarOrders() {
        List<String[]> orders = modelo.obtenerPedidos();
        vista.actualizarTablaOrdersAll(orders);
    }

    public void cargarNombresClientes() {
        Map<Integer, String> customerData = modelo.obtenerInfoCliente();
        vista.actualizarComboBoxClientes(customerData);
    }

    public void insertarOrder(String orderDate, int customerId) {
        modelo.insertarPedido(orderDate, customerId);
        cargarOrders(); // Actualizar la tabla de órdenes si es necesario
    }

    public void eliminarOrder(int orderId) {
        modelo.eliminarPedido(orderId);
        cargarOrders();
        cargarShipments(); // Actualizar la tabla de envíos
    }

    public void actualizarOrder(int oldOrderId, int newOrderId, String newOrderDate, int newCustomerId) {
        modelo.actualizarPedido(oldOrderId, newOrderId, newOrderDate, newCustomerId);
        cargarOrders(); // Actualizar la tabla de órdenes
        cargarShipments(); // Actualizar la tabla de envíos si es necesario
    }

    public void consultarOrdersByOrderId(int orderId) {
        List<String[]> orders = modelo.consultarPedidosIDPedido(orderId);
        vista.actualizarTablaOrders(orders);
    }

    //---------------------------------------------------------
    public void cargarShipments() {
        List<String[]> shipments = modelo.obtenerEnvios();
        vista.actualizarTablaShipments(shipments);
    }

    public void cargarOrderIds() {
        List<Integer> orderIds = modelo.obtenerIDPedidos();
        vista.actualizarComboBoxOrders(orderIds);
    }

    public void insertarShipment(String shipmentDate, int orderId) {
        modelo.insertarEnvio(shipmentDate, orderId);
        cargarShipments(); // Actualizar la tabla de envíos
    }

    public void eliminarShipment(int shipmentId) {
        modelo.eliminarEnvio(shipmentId);
        cargarShipments(); // Actualizar la tabla de envíos
    }

    public void actualizarShipment(int oldShipmentId, int newShipmentId, String newShipmentDate, int newOrderId) {
        modelo.actualizarEnvio(oldShipmentId, newShipmentId, newShipmentDate, newOrderId);
        cargarShipments(); // Actualizar la tabla de envíos
    }
    
    

}
