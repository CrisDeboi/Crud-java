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

public class Vista extends javax.swing.JFrame {

    private Controlador controlador;
    private DefaultTableModel tableModel;
    private DefaultTableModel tableModelOrders;
    private DefaultTableModel tableModelOrdersAll;
    private DefaultTableModel tableModelShipments;
    private Map<Integer, String> customerDataMap = new HashMap<>();

    public Vista() {
        initComponents();
        initTables();
        getContentPane().setBackground(new Color(214, 217, 223));
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    private void initTables() {
        tableModel = new DefaultTableModel(new Object[]{"ID Cliente", "Nombre Cliente"}, 0) {
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

        tableModelOrdersAll = new DefaultTableModel(new Object[]{"ID Pedido", "Fecha Pedido", "ID Cliente"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita la edición directa de las celdas
            }
        };
        jTable3.setModel(tableModelOrdersAll);

        tableModelShipments = new DefaultTableModel(new Object[]{"ID Envio", "Fecha Envio", "ID Pedido"}, 0) {
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
        jLabel7 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        jButton1.setText("Añadir");
        jButton1.setMaximumSize(new java.awt.Dimension(81, 23));
        jButton1.setMinimumSize(new java.awt.Dimension(81, 23));
        jButton1.setPreferredSize(new java.awt.Dimension(81, 23));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Cliente:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

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

        jButton2.setText("Eliminar");
        jButton2.setMaximumSize(new java.awt.Dimension(81, 23));
        jButton2.setMinimumSize(new java.awt.Dimension(81, 23));
        jButton2.setPreferredSize(new java.awt.Dimension(81, 23));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Modificar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("CLIENTE");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("PEDIDOS");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("ENVIOS");

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

        jLabel5.setText("Cliente:");

        jLabel6.setText("Fecha Pedido:");

        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton6.setText("Añadir");
        jButton6.setMaximumSize(new java.awt.Dimension(81, 23));
        jButton6.setMinimumSize(new java.awt.Dimension(81, 23));
        jButton6.setPreferredSize(new java.awt.Dimension(81, 23));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Eliminar");
        jButton7.setMaximumSize(new java.awt.Dimension(81, 23));
        jButton7.setMinimumSize(new java.awt.Dimension(81, 23));
        jButton7.setPreferredSize(new java.awt.Dimension(81, 23));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton5.setText("Modificar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel7.setText("Pedido:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setText("Fecha Envio:");

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });

        jButton9.setText("Añadir");
        jButton9.setMaximumSize(new java.awt.Dimension(81, 23));
        jButton9.setMinimumSize(new java.awt.Dimension(81, 23));
        jButton9.setPreferredSize(new java.awt.Dimension(81, 23));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("Eliminar");
        jButton10.setMaximumSize(new java.awt.Dimension(81, 23));
        jButton10.setMinimumSize(new java.awt.Dimension(81, 23));
        jButton10.setPreferredSize(new java.awt.Dimension(81, 23));
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

        jLabel9.setForeground(new java.awt.Color(255, 51, 51));

        jLabel10.setForeground(new java.awt.Color(255, 0, 51));

        jLabel11.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addComponent(jLabel2)
                .addGap(372, 372, 372)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(190, 190, 190))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addGap(100, 100, 100)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(18, 18, 18)
                                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel11))
                            .addGap(37, 37, 37)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton11)
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton11)
                            .addComponent(jButton5)
                            .addComponent(jButton3)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(44, 44, 44)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
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
            jLabel11.setText(null);
            jLabel10.setText(null);
            jLabel9.setText(null);
        } else {
            System.out.println("No hay ningún cliente seleccionado.");
            jLabel9.setText("Selecciona un cliente");
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
            jLabel11.setText(null);
            jLabel10.setText(null);
            jLabel9.setText(null);
        } else {
            System.out.println("No hay ningún cliente seleccionado.");
            jLabel9.setText("Selecciona un cliente");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String customerName = jTextField1.getText();
        if (!customerName.isEmpty() && customerName.trim().length() < 15 && customerName.trim().length() > 0) {
            controlador.insertarCliente(customerName);
            jTextField1.setText(null);
            jLabel11.setText(null);
            jLabel10.setText(null);
            jLabel9.setText(null);
        } else {
            jLabel9.setText("Nombre no valido");
            jTextField1.setText(null);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String nombre = (String) jComboBox1.getSelectedItem();
        String fecha = jTextField3.getText();
        int customerId = getCustomerIdFromName(nombre);
        if (nombre != null && !fecha.isEmpty() && customerId != -1 && fecha.trim().length() >= 10) {
            controlador.insertarOrder(fecha, customerId);
            jLabel11.setText(null);
            jLabel10.setText(null);
            jLabel9.setText(null);
        } else {
            System.out.println("Seleccione un cliente y escriba una fecha valida aaaa-mm-dd");
        }
        if (fecha.trim().length() < 10) {
            jLabel10.setText("Inserte fecha valida");
        }

        controlador.cargarOrderIds();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        int orderId = getSelectedOrderId();
        if (orderId != -1) {
            controlador.eliminarOrder(orderId);
            jLabel11.setText(null);
            jLabel10.setText(null);
            jLabel9.setText(null);
        } else {
            System.out.println("No hay ningún pedido seleccionado.");
            jLabel10.setText("Seleccione un pedido");
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        String idPedido = (String) jComboBox2.getSelectedItem();
        String fecha = jTextField2.getText();
        if (idPedido != null && !fecha.isEmpty() && fecha.trim().length() >= 10) {
            int orderId = Integer.parseInt(idPedido);
            controlador.insertarShipment(fecha, orderId);
            jLabel11.setText(null);
            jLabel10.setText(null);
            jLabel9.setText(null);
        } else {
            System.out.println("Selecciona un ID y escribe una fecha valida");
        }
        if (fecha.trim().length() < 10) {
            jLabel11.setText("Inserte fecha valida");
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
                jLabel11.setText(null);
                jLabel10.setText(null);
                jLabel9.setText(null);

            }
        } else {
            System.out.println("No hay ningún pedido seleccionado.");
            jLabel10.setText("Selecciona un pedido");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        int shipmentId = getSelectedShipmentId();
        if (shipmentId != -1) {
            controlador.eliminarShipment(shipmentId);
            jLabel11.setText(null);
            jLabel10.setText(null);
            jLabel9.setText(null);
        } else {
            System.out.println("No hay ningún envío seleccionado.");
            jLabel11.setText("Selecciona un envio");
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
                jLabel11.setText(null);
                jLabel10.setText(null);
                jLabel9.setText(null);
            }
        } else {
            System.out.println("No hay ningún envío seleccionado.");
            jLabel11.setText("Seleccione un envio");
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (Character.isDigit(c)) {
            evt.consume(); // Evita que el carácter se escriba en el JTextField
        }
        if (jTextField1.getText().length() > 15) {
            evt.consume(); // Evita que se escriban más caracteres
        }
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyTyped
        // TODO add your handling code here:
        try {
            char c = evt.getKeyChar();
            String text = jTextField3.getText();

            // Permitir solo dígitos y el carácter '-'
            if (!Character.isDigit(c) && c != '-') {
                evt.consume();
                return;
            }

            // Evitar más de 10 caracteres
            if (text.length() >= 10) {
                evt.consume();
                return;
            }

            // Evitar más de 4 dígitos para el año, 2 para el mes y 2 para el día
            int hyphenCount = text.length() - text.replace("-", "").length();
            // Si el carácter es un dígito, controlar la longitud en función de los guiones
            if (Character.isDigit(c)) {
                if ((hyphenCount == 0 && text.length() >= 4)
                        || (hyphenCount == 1 && text.length() >= 7)
                        || (hyphenCount == 2 && text.length() >= 10)) {
                    evt.consume();
                }
            }

            // Si el carácter es un guión, controlar la posición de inserción
            if (c == '-') {
                if (text.length() != 4 && text.length() != 7) {
                    evt.consume();
                }
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Error en la entrada: " + e.getMessage());
            evt.consume();
        }
    }//GEN-LAST:event_jTextField3KeyTyped

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        // TODO add your handling code here:
        try {
            char c = evt.getKeyChar();
            String text = jTextField2.getText();

            // Permitir solo dígitos y el carácter '-'
            if (!Character.isDigit(c) && c != '-') {
                evt.consume();
                return;
            }

            // Evitar más de 10 caracteres
            if (text.length() >= 10) {
                evt.consume();
                return;
            }

            // Evitar más de 4 dígitos para el año, 2 para el mes y 2 para el día
            int hyphenCount = text.length() - text.replace("-", "").length();
            // Si el carácter es un dígito, controlar la longitud en función de los guiones
            if (Character.isDigit(c)) {
                if ((hyphenCount == 0 && text.length() >= 4)
                        || (hyphenCount == 1 && text.length() >= 7)
                        || (hyphenCount == 2 && text.length() >= 10)) {
                    evt.consume();
                }
            }

            // Si el carácter es un guión, controlar la posición de inserción
            if (c == '-') {
                if (text.length() != 4 && text.length() != 7) {
                    evt.consume();
                }
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Error en la entrada: " + e.getMessage());
            evt.consume();
        }
    }//GEN-LAST:event_jTextField2KeyTyped
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
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Modelo modelo = new Modelo();
                //modelo.createDatabaseAndTables();
                Vista vista = new Vista();
                Controlador controlador = new Controlador(modelo, vista);
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
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
