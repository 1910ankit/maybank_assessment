package ui;

import db.CustomerDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AddressDialog extends JDialog {
    private JTextField shortNameField;
    private JTextField fullNameField;
    private JTextField address1Field;
    private JTextField address2Field;
    private JTextField address3Field;
    private JTextField cityField;
    private JTextField postalCodeField;
    private CustomerDAO customerDAO;
    private Integer customerId;

    public AddressDialog(Frame parent, Integer customerId) {
        super(parent, customerId == null ? "Add Customer" : "Modify Customer", true);
        this.customerDAO = new CustomerDAO();
        this.customerId = customerId;

        setLayout(new GridLayout(8, 2));

        shortNameField = new JTextField();
        fullNameField = new JTextField();
        address1Field = new JTextField();
        address2Field = new JTextField();
        address3Field = new JTextField();
        cityField = new JTextField();
        postalCodeField = new JTextField();

        if (customerId != null) {
            loadCustomerData();
        }

        add(new JLabel("Short Name:"));
        add(shortNameField);
        add(new JLabel("Full Name:"));
        add(fullNameField);
        add(new JLabel("Address 1:"));
        add(address1Field);
        add(new JLabel("Address 2:"));
        add(address2Field);
        add(new JLabel("Address 3:"));
        add(address3Field);
        add(new JLabel("City:"));
        add(cityField);
        add(new JLabel("Postal Code:"));
        add(postalCodeField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validatePostalCode(postalCodeField.getText())) {
                    saveCustomer();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(AddressDialog.this,
                            "Invalid postal code. It should be only digits.",
                            "Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(saveButton);
        pack();
        setLocationRelativeTo(parent);
    }

    private void loadCustomerData() {
        ResultSet rs = customerDAO.getCustomerById(customerId);
        try {
            if (rs != null && rs.next()) {
                shortNameField.setText(rs.getString("ShortName"));
                fullNameField.setText(rs.getString("FullName"));
                address1Field.setText(rs.getString("Address1"));
                address2Field.setText(rs.getString("Address2"));
                address3Field.setText(rs.getString("Address3"));
                cityField.setText(rs.getString("City"));
                postalCodeField.setText(rs.getString("PostalCode"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveCustomer() {
        String shortName = shortNameField.getText();
        String fullName = fullNameField.getText();
        String address1 = address1Field.getText();
        String address2 = address2Field.getText();
        String address3 = address3Field.getText();
        String city = cityField.getText();
        String postalCode = postalCodeField.getText();

        if (customerId == null) {
            customerDAO.addCustomer(shortName, fullName, address1, address2, address3, city, postalCode);
        } else {
            customerDAO.updateCustomer(customerId, shortName, fullName, address1, address2, address3, city, postalCode);
        }
    }

    private boolean validatePostalCode(String postalCode) {
        // Simple validation for 5-10 only digits
        return Pattern.matches("\\d{5,10}", postalCode);
    }
}

