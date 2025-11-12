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
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save PDF File");
        int userSelection = fileChooser.showSaveDialog(Mainframe);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }

            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();

                // Title
                Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
                Paragraph title = new Paragraph("FACULTY OF SCIENCE AND TECHNOLOGY\nADVANCED OBJECT-ORIENTED PROGRAMMING\nCLASS ACTIVITY\n\n", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                // Subtitle
                Font subTitleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
                Paragraph subTitle = new Paragraph("Advanced Object Oriented Programming\n\n", subTitleFont);
                subTitle.setAlignment(Element.ALIGN_CENTER);
                document.add(subTitle);

                // Timestamp
                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                Paragraph dateParagraph = new Paragraph("Report generated on: " + currentDate + "\n\n");
                dateParagraph.setAlignment(Element.ALIGN_RIGHT);
                document.add(dateParagraph);

                // Table setup
                PdfPTable pdfTable = new PdfPTable(tableModel.getColumnCount());
                pdfTable.setWidthPercentage(100);
                pdfTable.setSpacingBefore(10f);
                pdfTable.setSpacingAfter(10f);

                // Add table headers
                Font headFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                for (int i = 0; i < tableModel.getColumnCount(); i++) {
                    PdfPCell headerCell = new PdfPCell(new Phrase(tableModel.getColumnName(i), headFont));
                    headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    headerCell.setBorderWidth(1);
                    pdfTable.addCell(headerCell);
                }

                // Add table rows
                Font cellFont = new Font(Font.FontFamily.HELVETICA, 11);
                for (int row = 0; row < tableModel.getRowCount(); row++) {
                    for (int col = 0; col < tableModel.getColumnCount(); col++) {
                        PdfPCell cell = new PdfPCell(new Phrase(tableModel.getValueAt(row, col).toString(), cellFont));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBorderWidth(1);
                        pdfTable.addCell(cell);
                    }
                }
                document.add(pdfTable);
                document.close();

                JOptionPane.showMessageDialog(Mainframe, "PDF exported successfully!\nSaved at: " + filePath);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(Mainframe, "Error exporting to PDF: " + ex.getMessage(),
                        "Export Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
