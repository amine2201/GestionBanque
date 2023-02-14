package presentation.vue.generalVue;

import metier.admin.ServiceAdminGUI;
import metier.clients.IServiceClientGUI;
import metier.clients.ServiceClientGUI;
import presentation.modele.util.Log;
import presentation.vue.SearchPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class LogPanel extends JPanel{
    private TableLog tableLog;
    private JTable table;
    private JScrollPane scrollPane;
    private JTableHeader tableHeader;
    private List<Log> logs;
    private void initTable(){
        tableLog=new TableLog("Date","Heure","Message");
        tableLog.initLogsData(logs);
        table=new JTable(tableLog);
        table.setFont(new Font("Optima",Font.BOLD,15));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(35);
        table.setAutoCreateRowSorter(true);
        JTableUtilities.setCellsAlignment(table,SwingConstants.CENTER);

        tableHeader=table.getTableHeader();
        tableHeader.setFont(new Font("Optima",Font.BOLD,23));
        ((DefaultTableCellRenderer)tableHeader.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        tableHeader.setBackground(new Color(34, 40, 49));
        table.setBackground(new Color(34, 40, 49));
        tableHeader.setForeground(new Color(238, 238, 238));
        table.setForeground(new Color(238, 238, 238));
        scrollPane=new JScrollPane(table);
    }
    public LogPanel(List<Log> logs){
        this.logs=logs;
        initTable();
        setLayout(new BorderLayout());
        scrollPane.getViewport().setBackground(new Color(34, 40, 49));
        add(scrollPane,BorderLayout.CENTER);
    }
}
