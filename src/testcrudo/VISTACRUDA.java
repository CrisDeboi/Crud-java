package testcrudo;

import java.awt.Color;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class VISTACRUDA extends javax.swing.JFrame {

    private CONTROLADORCRUDO controlador;
    private DefaultTableModel tableModel;
    private DefaultTableModel tableModelOrders;
    private DefaultTableModel tableModelOrdersAll;
    private DefaultTableModel tableModelShipments;
    private Map<Integer, String> customerDataMap = new HashMap<>();

    public VISTACRUDA() {
        initComponents();
        initTables();
              getContentPane().setBackground(new Color(214, 217, 223));
    }

    public void setControlador(CONTROLADORCRUDO controlador) {
        this.controlador = controlador;
    }

    private void initTables() {
        tableModel = new DefaultTableModel(new Object[]{"Customer ID", "Customer Name"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita la edición directa de las celdas
            }
        };
        jTable1.setModel(tableModel);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); // Permite seleccionar solo una fila

        tableModelOrders = new DefaultTableModel(new Object[]{"Customer Name", "Order ID", "Order Date", "Shipment", "Shipment ID", "Shipment Date"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita la edición directa de las celdas
            }
        };
        jTable2.setModel(tableModelOrders);

        tableModelOrdersAll = new DefaultTableModel(new Object[]{"Order ID", "Order Date", "Customer ID"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita la edición directa de las celdas
            }
        };
        jTable3.setModel(tableModelOrdersAll);

        tableModelShipments = new DefaultTableModel(new Object[]{"Shipment ID", "Shipment Date", "Order ID"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita la edición directa de las celdas
            }
        };
        jTable4.setModel(tableModelShipments);
    }

    public void actualizarTablaShipments(List<String[]> shipments) {
        tableModelShipments.setRowCount(0); // Limpiar la tabla
        for (String[] shipment : shipments) {
            tableModelShipments.addRow(shipment);
        }
    }

    public void actualizarTablaClientes(List<String[]> customers) {
        tableModel.setRowCount(0); // Limpiar la tabla
        for (String[] customer : customers) {
            tableModel.addRow(customer);
        }
    }

    public void actualizarTablaOrders(List<String[]> orders) {
        tableModelOrders.setRowCount(0); // Limpiar la tabla
        for (String[] order : orders) {
            tableModelOrders.addRow(order);
        }
    }

    public void actualizarTablaOrdersAll(List<String[]> orders) {
        tableModelOrdersAll.setRowCount(0); // Limpiar la tabla
        for (String[] order : orders) {
            tableModelOrdersAll.addRow(order);
        }
    }

    public int getSelectedCustomerId() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            return Integer.parseInt((String) tableModel.getValueAt(selectedRow, 0));
        }
        return -1; // Indica que no hay fila seleccionada
    }

    public String getSelectedCustomerName() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            return (String) tableModel.getValueAt(selectedRow, 1);
        }
        return null; // Indica que no hay fila seleccionada
    }

    public String[] mostrarPopUpParaActualizarCliente(int customerId, String customerName) {
        JTextField idField = new JTextField(String.valueOf(customerId));
        JTextField nameField = new JTextField(customerName);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Customer ID:"));
        panel.add(idField);
        panel.add(new JLabel("Customer Name:"));
        panel.add(nameField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Actualizar Cliente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            return new String[]{idField.getText(), nameField.getText()};
        } else {
            return null;
        }
    }

    public String[] mostrarPopUpParaActualizarOrder(int orderId, String orderDate, int customerId) {
        JTextField idField = new JTextField(String.valueOf(orderId));
        JTextField dateField = new JTextField(orderDate);
        JTextField customerIdField = new JTextField(String.valueOf(customerId));

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Order ID:"));
        panel.add(idField);
        panel.add(new JLabel("Order Date:"));
        panel.add(dateField);
        panel.add(new JLabel("Customer ID:"));
        panel.add(customerIdField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Actualizar Pedido", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            return new String[]{idField.getText(), dateField.getText(), customerIdField.getText()};
        } else {
            return null;
        }
    }

    public String[] mostrarPopUpParaActualizarShipment(int shipmentId, String shipmentDate, int orderId) {
        JTextField idField = new JTextField(String.valueOf(shipmentId));
        JTextField dateField = new JTextField(shipmentDate);
        JTextField orderIdField = new JTextField(String.valueOf(orderId));

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Shipment ID:"));
        panel.add(idField);
        panel.add(new JLabel("Shipment Date:"));
        panel.add(dateField);
        panel.add(new JLabel("Order ID:"));
        panel.add(orderIdField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Actualizar Envío", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            return new String[]{idField.getText(), dateField.getText(), orderIdField.getText()};
        } else {
            return null;
        }
    }

    public String getSelectedShipmentDate() {
        int selectedRow = jTable4.getSelectedRow();
        if (selectedRow != -1) {
            return (String) tableModelShipments.getValueAt(selectedRow, 1);
        }
        return null; // Indica que no hay fila seleccionada
    }

    public int getSelectedOrderIdForShipment() {
        int selectedRow = jTable4.getSelectedRow();
        if (selectedRow != -1) {
            return Integer.parseInt((String) tableModelShipments.getValueAt(selectedRow, 2));
        }
        return -1; // Indica que no hay fila seleccionada
    }

    public void actualizarComboBoxClientes(Map<Integer, String> customerData) {
        customerDataMap = customerData;
        jComboBox1.removeAllItems();
        for (String name : customerData.values()) {
            jComboBox1.addItem(name);
        }
    }

    public int getCustomerIdFromName(String customerName) {
        for (Map.Entry<Integer, String> entry : customerDataMap.entrySet()) {
            if (entry.getValue().equals(customerName)) {
                return entry.getKey();
            }
        }
        return -1; // Cliente no encontrado
    }

    public void actualizarComboBoxOrders(List<Integer> orderIds) {
        jComboBox2.removeAllItems();
        for (Integer id : orderIds) {
            jComboBox2.addItem(id.toString());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        jButton1.setText("añadir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("customer:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane2.setViewportView(jTable1);

        jButton2.setText("eliminar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("modificar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("consultar");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable2);

        jLabel2.setText("CUSTOMER");

        jLabel3.setText("ORDERS");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("SHIPMENTS");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable4);

        jLabel5.setText("Customer :");

        jLabel6.setText("order date:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton6.setText("añadir");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Eliminar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton5.setText("modificar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton8.setText("consultar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Order: ");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("ship date: ");

        jButton9.setText("añadir");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("Eliminar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Modificar");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setText("Consultar");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1402, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton11))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(111, 111, 111)
                                .addComponent(jLabel2)
                                .addGap(372, 372, 372)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)))
                        .addGap(32, 32, 32)
                        .addComponent(jButton12)
                        .addGap(175, 175, 175))))
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jButton2)
                            .addGap(28, 28, 28)
                            .addComponent(jButton3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(100, 100, 100)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField3)
                                    .addComponent(jComboBox1, 0, 118, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(201, 201, 201)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField2)))
                                .addGap(48, 48, 48)
                                .addComponent(jButton9))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton7)
                                .addGap(18, 18, 18)
                                .addComponent(jButton5)
                                .addGap(18, 18, 18)
                                .addComponent(jButton8)
                                .addGap(233, 233, 233)
                                .addComponent(jButton10)))
                        .addGap(0, 168, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(35, 35, 35)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton1))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel6)
                                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(34, 34, 34)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel8)
                                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton9)
                                .addGap(36, 36, 36)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton3)
                            .addComponent(jButton4)
                            .addComponent(jButton7)
                            .addComponent(jButton5)
                            .addComponent(jButton8)
                            .addComponent(jButton10)
                            .addComponent(jButton11)
                            .addComponent(jButton12))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)
                        .addGap(79, 79, 79)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int customerId = getSelectedCustomerId();
        if (customerId != -1) {
            controlador.eliminarCliente(customerId);
        } else {
            System.out.println("No hay ningún cliente seleccionado.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int oldCustomerId = getSelectedCustomerId();
        String customerName = getSelectedCustomerName();
        if (oldCustomerId != -1 && customerName != null) {
            String[] updatedData = mostrarPopUpParaActualizarCliente(oldCustomerId, customerName);
            if (updatedData != null) {
                int newCustomerId = Integer.parseInt(updatedData[0]);
                String newCustomerName = updatedData[1];
                controlador.actualizarCliente(oldCustomerId, newCustomerId, newCustomerName);
            }
        } else {
            System.out.println("No hay ningún cliente seleccionado.");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String customerName = jTextField1.getText();
        if (!customerName.isEmpty()) {
            controlador.insertarCliente(customerName);
            jTextField1.setText(""); // Limpiar el campo de texto
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int customerId = getSelectedCustomerId();
        if (customerId != -1) {
            controlador.consultarOrdersByCustomerId(customerId);
        } else {
            System.out.println("No hay ningún cliente seleccionado.");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String customerName = (String) jComboBox1.getSelectedItem();
        String orderDate = jTextField3.getText();
        int customerId = getCustomerIdFromName(customerName);
        if (customerName != null && !orderDate.isEmpty() && customerId != -1) {
            controlador.insertarOrder(orderDate, customerId);
        } else {
            System.out.println("Por favor, seleccione un cliente y escriba una fecha de pedido.");
        }
        controlador.cargarOrderIds();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        int orderId = getSelectedOrderId();
        if (orderId != -1) {
            controlador.eliminarOrder(orderId);
        } else {
            System.out.println("No hay ningún pedido seleccionado.");
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        String orderIdStr = (String) jComboBox2.getSelectedItem();
        String shipmentDate = jTextField2.getText();
        if (orderIdStr != null && !shipmentDate.isEmpty()) {
            int orderId = Integer.parseInt(orderIdStr);
            controlador.insertarShipment(shipmentDate, orderId);
        } else {
            System.out.println("Por favor, seleccione un Order ID y escriba una fecha de envío.");
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int oldOrderId = getSelectedOrderId();
        String orderDate = getSelectedOrderDate();
        int customerId = getSelectedCustomerIdForOrder();
        if (oldOrderId != -1 && orderDate != null && customerId != -1) {
            String[] updatedData = mostrarPopUpParaActualizarOrder(oldOrderId, orderDate, customerId);
            if (updatedData != null) {
                int newOrderId = Integer.parseInt(updatedData[0]);
                String newOrderDate = updatedData[1];
                int newCustomerId = Integer.parseInt(updatedData[2]);
                controlador.actualizarOrder(oldOrderId, newOrderId, newOrderDate, newCustomerId);
            }
        } else {
            System.out.println("No hay ningún pedido seleccionado.");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        int orderId = getSelectedOrderId();
        if (orderId != -1) {
            controlador.consultarOrdersByOrderId(orderId);
        } else {
            System.out.println("No hay ningún pedido seleccionado.");
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        int shipmentId = getSelectedShipmentId();
        if (shipmentId != -1) {
            controlador.eliminarShipment(shipmentId);
        } else {
            System.out.println("No hay ningún envío seleccionado.");
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        int oldShipmentId = getSelectedShipmentId();
        String shipmentDate = getSelectedShipmentDate();
        int orderId = getSelectedOrderIdForShipment();
        if (oldShipmentId != -1 && shipmentDate != null && orderId != -1) {
            String[] updatedData = mostrarPopUpParaActualizarShipment(oldShipmentId, shipmentDate, orderId);
            if (updatedData != null) {
                int newShipmentId = Integer.parseInt(updatedData[0]);
                String newShipmentDate = updatedData[1];
                int newOrderId = Integer.parseInt(updatedData[2]);
                controlador.actualizarShipment(oldShipmentId, newShipmentId, newShipmentDate, newOrderId);
            }
        } else {
            System.out.println("No hay ningún envío seleccionado.");
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed
    public int getSelectedShipmentId() {
        int selectedRow = jTable4.getSelectedRow();
        if (selectedRow != -1) {
            return Integer.parseInt((String) tableModelShipments.getValueAt(selectedRow, 0));
        }
        return -1; // Indica que no hay fila seleccionada
    }

    public int getSelectedOrderId() {
        int selectedRow = jTable3.getSelectedRow();
        if (selectedRow != -1) {
            return Integer.parseInt((String) tableModelOrdersAll.getValueAt(selectedRow, 0));
        }
        return -1; // Indica que no hay fila seleccionada
    }

    public String getSelectedOrderDate() {
        int selectedRow = jTable3.getSelectedRow();
        if (selectedRow != -1) {
            return (String) tableModelOrdersAll.getValueAt(selectedRow, 1);
        }
        return null; // Indica que no hay fila seleccionada
    }

    public int getSelectedCustomerIdForOrder() {
        int selectedRow = jTable3.getSelectedRow();
        if (selectedRow != -1) {
            return Integer.parseInt((String) tableModelOrdersAll.getValueAt(selectedRow, 2));
        }
        return -1; // Indica que no hay fila seleccionada
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VISTACRUDA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VISTACRUDA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VISTACRUDA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VISTACRUDA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MODELOCRUDA modelo = new MODELOCRUDA();
                modelo.createDatabaseAndTables();
                VISTACRUDA vista = new VISTACRUDA();
                CONTROLADORCRUDO controlador = new CONTROLADORCRUDO(modelo, vista);
                vista.setControlador(controlador);
                vista.setVisible(true);
                controlador.cargarClientes();
                controlador.cargarOrders(); // Cargar los datos de Orders al iniciar
                controlador.cargarNombresClientes(); // Cargar los nombres de los clientes en el combo box
                controlador.cargarShipments(); // Cargar los datos de Shipments al iniciar
                controlador.cargarOrderIds(); // Cargar los order_id en el combo box
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
