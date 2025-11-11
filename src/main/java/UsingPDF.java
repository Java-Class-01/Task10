import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
public class UsingPDF {
    JFrame Mainframe;
    JPanel holdingPanel;
    JTable TabularDataTable;
    JButton ExportToPDF;
    DefaultTableModel tableModel;
    public UsingPDF(){
        this.prepareJFrame();
        //incase you have not created the database comment this method to just see empty table
        this.loadProductsForTheTable();
    }
    public JFrame prepareJFrame(){
        Mainframe=new JFrame("JTable and PDF");
        Mainframe.setSize(500, 400);
        Mainframe.setLayout(new BorderLayout(10, 10));
        Mainframe.add(this.prepareJPanelholdingPanel(), BorderLayout.CENTER);
        Mainframe.setVisible(true);

        return Mainframe;
    }
    public JPanel prepareJPanelholdingPanel(){
        holdingPanel=new JPanel(new BorderLayout(10, 10));
        holdingPanel.add(this.prepareJTableTabularDataTable(), BorderLayout.CENTER);
        holdingPanel.add(this.prepareExportToPDFButton(), BorderLayout.SOUTH);
        return holdingPanel;
    }
}
