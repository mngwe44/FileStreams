import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RandProductSearch extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;

    public RandProductSearch() {
        setTitle("Product Search");
        setLayout(new BorderLayout());
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        searchField = new JTextField();
        resultArea = new JTextArea();
        JButton searchBtn = new JButton("Search");

        add(searchField, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
        add(searchBtn, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> {
            String keyword = searchField.getText().toLowerCase();
            try (RandomAccessFile raf = new RandomAccessFile("products.dat", "r")) {
                resultArea.setText("");
                long numRecords = raf.length() / Product.RECORD_SIZE;
                for (int i = 0; i < numRecords; i++) {
                    raf.seek(i * Product.RECORD_SIZE);

                    String name = readFixedString(raf, Product.NAME_LENGTH);
                    String desc = readFixedString(raf, Product.DESC_LENGTH);
                    String id = readFixedString(raf, Product.ID_LENGTH);
                    double cost = raf.readDouble();

                    if (name.toLowerCase().contains(keyword)) {
                        resultArea.append("Name: " + name.trim() + "\n");
                        resultArea.append("Desc: " + desc.trim() + "\n");
                        resultArea.append("ID: " + id.trim() + "\n");
                        resultArea.append("Cost: " + cost + "\n\n");
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        setVisible(true);
    }

    private String readFixedString(RandomAccessFile raf, int size) throws IOException {
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        new RandProductSearch();
    }
}
