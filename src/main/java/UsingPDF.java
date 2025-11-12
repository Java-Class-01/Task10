import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;

public class UsingPDF {
    JFrame Mainframe;
    JPanel holdingPanel;
    JTable TabularDataTable;
    JButton ExportToPDF, LoadData;
    DefaultTableModel tableModel;

    public UsingPDF() {
        this.prepareJFrame();
        this.loadProductsForTheTable(); // Load data from DB
    }

    public JFrame prepareJFrame() {
        Mainframe = new JFrame("JTable and PDF Export");
        Mainframe.setSize(600, 400);
        Mainframe.setLayout(new BorderLayout(10, 10));
        Mainframe.add(this.prepareJPanelholdingPanel(), BorderLayout.CENTER);
        Mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Mainframe.setVisible(true);
        return Mainframe;
    }

    public JPanel prepareJPanelholdingPanel() {
        holdingPanel = new JPanel(new BorderLayout(10, 10));
        holdingPanel.add(this.prepareJTableTabularDataTable(), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(this.prepareLoadDataButton());
        buttonPanel.add(this.prepareExportToPDFButton());

        holdingPanel.add(buttonPanel, BorderLayout.SOUTH);
        return holdingPanel;
    }

    public JScrollPane prepareJTableTabularDataTable() {
        String[] columnNames = {"ID", "Product Name", "Price"};
        tableModel = new DefaultTableModel(columnNames, 0);
        TabularDataTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(TabularDataTable);
        return scrollPane;
    }

    public JButton prepareLoadDataButton() {
        LoadData = new JButton("Load Data");
        LoadData.addActionListener(e -> loadProductsForTheTable());
        return LoadData;
    }

    public JButton prepareExportToPDFButton() {
        ExportToPDF = new JButton("Export to PDF");
        ExportToPDF.addActionListener(e -> exportToPDF());
        return ExportToPDF;
    }

    // Load data from the database into JTable
    private void loadProductsForTheTable() {
        tableModel.setRowCount(0); // Clear existing table rows
        String sql = "SELECT id, product_name, price FROM products";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("product_name");
                double price = rs.getDouble("price");
                tableModel.addRow(new Object[]{id, name, price});
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(Mainframe, "No data found in database.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(Mainframe, "Error loading products: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // PDF Export Logic
    private void exportToPDF() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(Mainframe, "No data to export!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
    }
}
