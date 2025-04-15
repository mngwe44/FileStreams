import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RandProductMaker extends JFrame {
    private JTextField nameField, descField, idField, costField, countField;
    private int recordCount = 0;
    private RandomAccessFile raf;

    public RandProductMaker() {
        setTitle("Product Entry");
        setLayout(new GridLayout(6, 2));
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Input Fields
        nameField = new JTextField();
        descField = new JTextField();
        idField = new JTextField();
        costField = new JTextField();
        countField = new JTextField("0");
        countField.setEditable(false);

        JButton addBtn = new JButton("Add Product");

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Description:"));
        add(descField);
        add(new JLabel("ID:"));
        add(idField);
        add(new JLabel("Cost:"));
        add(costField);
        add(new JLabel("Record Count:"));
        add(countField);
        add(addBtn);

        try {
            raf = new RandomAccessFile("products.dat", "rw");
        } catch (IOException e) {
            e.printStackTrace();
        }

        addBtn.addActionListener(e -> {
            try {
                String name = Product.pad(nameField.getText(), Product.NAME_LENGTH);
                String desc = Product.pad(descField.getText(), Product.DESC_LENGTH);
                String id = Product.pad(idField.getText(), Product.ID_LENGTH);
                double cost = Double.parseDouble(costField.getText());

                Product p = new Product(name, desc, id, cost);

                raf.seek(recordCount * Product.RECORD_SIZE);
                raf.writeChars(p.getFormattedName());
                raf.writeChars(p.getFormattedDescription());
                raf.writeChars(p.getFormattedId());
                raf.writeDouble(p.getCost());

                recordCount++;
                countField.setText(String.valueOf(recordCount));
                clearFields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input!");
            }
        });

        setVisible(true);
    }

    private void clearFields() {
        nameField.setText("");
        descField.setText("");
        idField.setText("");
        costField.setText("");
    }

    public static void main(String[] args) {
        new RandProductMaker();
    }
}

