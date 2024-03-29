package presentation.vue.generalVue;

import metier.admin.IServiceAdminGUI;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.vue.SearchPanel;
import presentation.vue.adminVue.clientVue.TableClient;
import presentation.vue.adminVue.compteVue.TableCompte;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TablePanel extends JPanel {
    private TableClient tableClient;
    private TableCompte tableCompte;
    private JTable table;
    private JScrollPane scrollPane;
    private JTableHeader tableHeader;
    private SearchPanel searchPanel;
    private IServiceAdminGUI serviceAdmin;
    private int _switch;
//    DefaultTableCellRenderer centerRenderer;
    private void initTable(int i){
        if(i==1){
        tableClient = new TableClient("ID","NOM","PRENOM","LOGIN","PASS","CIN","EMAIL","TEL","SEXE");
        tableClient.initClientsData(serviceAdmin.getClients());
        table=new JTable(tableClient);}
        if(i==2){
            tableCompte = new TableCompte("Numero","Date de creation","Solde","Proprietaire");

            tableCompte.initComptesData(serviceAdmin.getComptes());
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
        initActions();
    }
    private void initActions(){
        if(_switch==1){
            searchPanel.getCrudPanel().getBtn_info().setVisible(false);
            searchPanel.getCrudPanel().getBtn_delete().addActionListener(e->{
                int row=table.getSelectedRow();
                if(row!=-1){
                    long id           =  (long)tableClient.getValueAt(row, 0);
                    String  nom      =  (String)tableClient.getValueAt(row, 1);
                    String  prenom   =  (String)tableClient.getValueAt(row, 2);
                    String nomComplet = nom + " " + prenom;


                    serviceAdmin.supprimerClient(id);
                    tableClient.initClientsData(serviceAdmin.getClients());

                    JOptionPane.showMessageDialog(this,
                            "Le Client "+nomComplet+ " a été supprimé avec succès",
                            "I N F O",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(this,
                            "Veuillez choisir un client d'abord !!!",
                            "A L E R T",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
            searchPanel.getTxt_search().addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_ENTER){
                        searchPanel.getBtn_search().doClick();
                    }
                }
            });
            searchPanel.getBtn_search().addActionListener(e->{
                String keyword = searchPanel.getTxt_search().getText();
                tableClient.initClientsData(serviceAdmin.chercherClient(keyword));

            });
        }
        else{
            searchPanel.getCrudPanel().getBtn_edit().setVisible(false);
            searchPanel.getCrudPanel().getBtn_delete().addActionListener(e->{
                int row=table.getSelectedRow();
                if(row!=-1){
                    String numCompte           =  (String) tableCompte.getValueAt(row, 0);
                    String nomComplet=(String) tableCompte.getValueAt(row,3);


                    serviceAdmin.supprimerCompte(numCompte);
                    tableCompte.initComptesData(serviceAdmin.getComptes());

                    JOptionPane.showMessageDialog(this,
                            "Le compte "+numCompte+"du client "+nomComplet+ " a été supprimé avec succès",
                            "I N F O",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(this,
                            "Veuillez choisir un compte d'abord !!!",
                            "A L E R T",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
            searchPanel.getTxt_search().addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_ENTER){
                        searchPanel.getBtn_search().doClick();
                    }
                }
            });
            searchPanel.getBtn_search().addActionListener(e->{
                String keyword = searchPanel.getTxt_search().getText();
                tableCompte.initComptesData(serviceAdmin.chercherCompte(keyword));

            });
        }
    }
    public JButton getBtn_add(){
        return searchPanel.getCrudPanel().getBtn_add();
    }
    public JButton getBtn_edit(){
        return searchPanel.getCrudPanel().getBtn_edit();
    }
    public JButton getBtn_info(){
        return searchPanel.getCrudPanel().getBtn_info();
    }
    public Object getSelectedID(){
            int row=table.getSelectedRow();
            if(row!=-1)
                return _switch==1?tableClient.getValueAt(row,0):tableCompte.getValueAt(row,0);
            return -1;

    }
    public TablePanel(int i,IServiceAdminGUI serviceAdmin){
        this.serviceAdmin=serviceAdmin;
        _switch=i;
        initTable(_switch);
        setLayout(new BorderLayout());
        scrollPane.getViewport().setBackground(new Color(34, 40, 49));
        add(scrollPane,BorderLayout.CENTER);
        add(searchPanel,BorderLayout.SOUTH);
    }
}
