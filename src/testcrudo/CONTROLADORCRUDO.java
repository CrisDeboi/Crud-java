package testcrudo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CONTROLADORCRUDO {

    private MODELOCRUDA modelo;
    private VISTACRUDA vista;

    public CONTROLADORCRUDO(MODELOCRUDA modelo, VISTACRUDA vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void cargarClientes() {
        List<String[]> customers = modelo.getCustomers();
        vista.actualizarTablaClientes(customers);
    }

    public void insertarCliente(String customerName) {
        modelo.insertCustomer(customerName);
        cargarClientes();
        cargarNombresClientes(); // Actualizar el jComboBox1
        cargarOrders(); // Actualizar la tabla de órdenes
    }

    public void eliminarCliente(int customerId) {
        modelo.deleteCustomer(customerId);
        cargarClientes();
        cargarNombresClientes(); // Actualizar el jComboBox1
        cargarOrders(); // Actualizar la tabla de órdenes
        cargarShipments();
    }

    public void actualizarCliente(int oldCustomerId, int newCustomerId, String newCustomerName) {
        modelo.updateCustomer(oldCustomerId, newCustomerId, newCustomerName);
        cargarClientes();
        cargarNombresClientes(); // Actualizar el jComboBox1
        cargarOrders(); // Actualizar la tabla de órdenes
    }

    public void consultarOrdersByCustomerId(int customerId) {
        List<String[]> orders = modelo.consultarOrdersByCustomerId(customerId);
        vista.actualizarTablaOrders(orders);
    }

    //---------------------------------------------------------
    public void cargarOrders() {
        List<String[]> orders = modelo.getAllOrders();
        vista.actualizarTablaOrdersAll(orders);
    }

    public void cargarNombresClientes() {
        Map<Integer, String> customerData = modelo.getCustomerData();
        vista.actualizarComboBoxClientes(customerData);
    }

    public void insertarOrder(String orderDate, int customerId) {
        modelo.insertOrder(orderDate, customerId);
        cargarOrders(); // Actualizar la tabla de órdenes si es necesario
    }

    public void eliminarOrder(int orderId) {
        modelo.deleteOrder(orderId);
        cargarOrders();
        cargarShipments(); // Actualizar la tabla de envíos
    }

    public void actualizarOrder(int oldOrderId, int newOrderId, String newOrderDate, int newCustomerId) {
        modelo.updateOrder(oldOrderId, newOrderId, newOrderDate, newCustomerId);
        cargarOrders(); // Actualizar la tabla de órdenes
        cargarShipments(); // Actualizar la tabla de envíos si es necesario
    }

    public void consultarOrdersByOrderId(int orderId) {
        List<String[]> orders = modelo.consultarOrdersByOrderId(orderId);
        vista.actualizarTablaOrders(orders);
    }

    //---------------------------------------------------------
    public void cargarShipments() {
        List<String[]> shipments = modelo.getShipments();
        vista.actualizarTablaShipments(shipments);
    }

    public void cargarOrderIds() {
        List<Integer> orderIds = modelo.getOrderIds();
        vista.actualizarComboBoxOrders(orderIds);
    }

    public void insertarShipment(String shipmentDate, int orderId) {
        modelo.insertShipment(shipmentDate, orderId);
        cargarShipments(); // Actualizar la tabla de envíos
    }

    public void eliminarShipment(int shipmentId) {
        modelo.deleteShipment(shipmentId);
        cargarShipments(); // Actualizar la tabla de envíos
    }

    public void actualizarShipment(int oldShipmentId, int newShipmentId, String newShipmentDate, int newOrderId) {
        modelo.updateShipment(oldShipmentId, newShipmentId, newShipmentDate, newOrderId);
        cargarShipments(); // Actualizar la tabla de envíos
    }
    
    

}
