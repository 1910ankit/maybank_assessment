package ui;
import db.CustomerDAO;
import db.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerFrame extends JFrame {
    private CustomerDAO customerDAO = new CustomerDAO();
    private DefaultListModel<String> customerListModel;
    private JList<String> customerList;

    public CustomerFrame() {
        setTitle("Customer Management");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        customerListModel = new DefaultListModel<>();
        customerList = new JList<>(customerListModel);
        loadCustomers();

        JButton addButton = new JButton("Add Customer");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddressDialog dialog = new AddressDialog(CustomerFrame.this, null);
                dialog.setVisible(true);
                loadCustomers();
            }
        });

        JButton modifyButton = new JButton("Modify Customer");
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCustomer = customerList.getSelectedValue();
                if (selectedCustomer != null) {
                    int id = getCustomerId(selectedCustomer);
                    AddressDialog dialog = new AddressDialog(CustomerFrame.this, id);
                    dialog.setVisible(true);
                    loadCustomers();
                } else {
                    JOptionPane.showMessageDialog(CustomerFrame.this, "Select a customer to modify.");
                }
            }
        });

        JButton viewButton = new JButton("View Address");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCustomer = customerList.getSelectedValue();
                if (selectedCustomer != null) {
                    int id = getCustomerId(selectedCustomer);
                    displayCustomerAddress(id);
                } else {
                    JOptionPane.showMessageDialog(CustomerFrame.this, "Select a customer to view.");
                }
            }
        });

        JButton deleteButton = new JButton("Delete Customer");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCustomer = customerList.getSelectedValue();
                if (selectedCustomer != null) {
                    int id = getCustomerId(selectedCustomer);
                    customerDAO.deleteCustomer(id);
                    loadCustomers();
                } else {
                    JOptionPane.showMessageDialog(CustomerFrame.this, "Select a customer to delete.");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(deleteButton);

        setLayout(new BorderLayout());
        add(new JScrollPane(customerList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private int getCustomerId(String shortName) {
        String[] parts = shortName.split(": ");
        return Integer.parseInt(parts[0]);
    }

    private void loadCustomers() {
        customerListModel.clear();
        for (String customer : customerDAO.getCustomers()) {
            customerListModel.addElement(customer);
        }
    }

    private void displayCustomerAddress(int customerId) {
        ResultSet rs = customerDAO.getCustomerById(customerId);
        try {
            if (rs != null && rs.next()) {
                String addressDetails = String.format("Address 1: %s\nAddress 2: %s\nAddress 3: %s\nCity: %s\nPostal Code: %s",
                        rs.getString("Address1"),
                        rs.getString("Address2"),
                        rs.getString("Address3"),
                        rs.getString("City"),
                        rs.getString("PostalCode"));
                JOptionPane.showMessageDialog(this, addressDetails, "Customer Address", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }
