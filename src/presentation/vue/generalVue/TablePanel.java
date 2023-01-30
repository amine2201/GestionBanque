package presentation.vue.generalVue;

import dao.daoFiles.ClientDao;
import dao.daoFiles.CompteDao;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.entitesDeLaBanque.Compte;
import presentation.vue.SearchPanel;
import presentation.vue.clientVue.TableClient;
import presentation.vue.compteVue.TableCompte;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TablePanel extends JPanel {
    private TableClient tableClient;
    private TableCompte tableCompte;
    private JTable table;
    private JScrollPane scrollPane;
    private JTableHeader tableHeader;
    private SearchPanel searchPanel;
//    DefaultTableCellRenderer centerRenderer;
    private void initTable(int i){
        if(i==1){
        tableClient = new TableClient("ID","NOM","PRENOM","LOGIN","PASS","CIN","EMAIL","TEL","SEXE");
        tableClient.initClientsData(new ClientDao().findAll());
        table=new JTable(tableClient);}
        if(i==2){
            tableCompte = new TableCompte("Numero","Date de creation","Solde","Proprietaire");
            List<Compte> comptes=new ArrayList<>();
            for(Client client: new ClientDao().findAll()){
                comptes.addAll(new CompteDao(client).findAll());
            }
            tableCompte.initComptesData(comptes);
            table=new JTable(tableCompte);
        }
        table.setFont(new Font("Optima",Font.BOLD,15));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(35);
        table.setAutoCreateRowSorter(true);
        JTableUtilities.setCellsAlignment(table,SwingConstants.CENTER);

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
        searchPanel=new SearchPanel(new Color(34, 40, 49));
    }
    public TablePanel(int i){
        initTable(i);

        setLayout(new BorderLayout());
        scrollPane.getViewport().setBackground(new Color(34, 40, 49));
        add(scrollPane,BorderLayout.CENTER);
        add(searchPanel,BorderLayout.SOUTH);
    }
}
