package presentation.vue;

import dao.daoFiles.ClientDao;
import presentation.vue.clientVue.TableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class TablePanel extends JPanel {
    private TableModel tableModel;
    JTable table;
    JScrollPane scrollPane;

    JTableHeader tableHeader;
//    DefaultTableCellRenderer centerRenderer;
    private void initTable(){
        tableModel= new TableModel("ID","NOM","PRENOM","LOGIN","PASS","CIN","EMAIL","TEL","SEXE");
        tableModel.initClientsData(new ClientDao().findall());
        table=new JTable(tableModel);
        table.setFont(new Font("Optima",Font.BOLD,15));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(35);

        tableHeader=table.getTableHeader();
        tableHeader.setFont(new Font("Optima",Font.BOLD,23));



//        centerRenderer=new DefaultTableCellRenderer();
//        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        ((DefaultTableCellRenderer)tableHeader.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        tableHeader.setBackground(new Color(34, 40, 49));
        table.setBackground(new Color(34, 40, 49));
        tableHeader.setForeground(new Color(238, 238, 238));
        table.setForeground(new Color(238, 238, 238));
        scrollPane=new JScrollPane(table);

    }
    public TablePanel(){
        initTable();
        setLayout(new GridLayout(1,1));
        scrollPane.getViewport().setBackground(new Color(34, 40, 49));
        add(scrollPane);
    }
}
