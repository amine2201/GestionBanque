package presentation.vue.clientVue;

import metier.admin.IServiceAdminGUI;
import metier.clients.IServiceClientGUI;
import presentation.vue.SearchPanel;
import presentation.vue.adminVue.compteVue.TableCompte;
import presentation.vue.generalVue.JTableUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class ComptesPanel extends JPanel {
    private TableCompte tableCompte;
    private JTable table;
    private JScrollPane scrollPane;
    private JTableHeader tableHeader;
    private IServiceClientGUI serviceClient;
    ClassLoader cl = getClass().getClassLoader();
    private JButton btn_vir, btn_ver, btn_ret,btn_info;

    public JButton getBtn_vir() {
        return btn_vir;
    }

    public JButton getBtn_ver() {
        return btn_ver;
    }

    public JButton getBtn_ret() {
        return btn_ret;
    }

    public JButton getBtn_info() {
        return btn_info;
    }

    private void initButtons(){
        btn_vir = new JButton(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/transfer.png"))));
        btn_vir.setBorderPainted(false);
//        btn_vir.setBackground(new Color(0, 173, 181));

        btn_ver = new JButton(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/deposit.png"))));
        btn_ver.setBorderPainted(false);
//        btn_ver.setBackground(new Color(0, 173, 181));

        btn_ret = new JButton(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/withdraw.png"))));
        btn_ret.setBorderPainted(false);
//        btn_ret.setBackground(new Color(0, 173, 181));

        btn_info = new JButton(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/information.png"))));
        btn_info.setBorderPainted(false);

    }
    private void initActions(){
        btn_vir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_vir.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/transferHover.png"))));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_vir.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/transfer.png"))));
            }
        });

        btn_ver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_ver.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/depositHover.png"))));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_ver.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/deposit.png"))));
            }
        });
        btn_ret.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_ret.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/withdrawHover.png"))));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_ret.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/withdraw.png"))));
            }
        });

        btn_info.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_info.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/informationHover.png"))));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_info.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/information.png"))));
            }
        });
    }
    private void initPanels(){
        initButtons();
        initActions();
        setLayout(new BorderLayout());
        scrollPane.getViewport().setBackground(new Color(34, 40, 49));
        add(scrollPane,BorderLayout.CENTER);

        JPanel southPanel=new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER,50,10));
        southPanel.setBackground(new Color(34, 40, 49));
        southPanel.add(btn_ret);
        southPanel.add(btn_ver);
        southPanel.add(btn_vir);
        southPanel.add(btn_info);

        add(southPanel,BorderLayout.SOUTH);
    }
    private void initTable(){
            tableCompte = new TableCompte("Numero","Date de creation","Solde","Proprietaire");
            tableCompte.initComptesData(serviceClient.comptes());
            table=new JTable(tableCompte);
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
            initPanels();
    }
    public Object getSelectedCompte(){
        int row=table.getSelectedRow();
        if(row!=-1)
            return tableCompte.getValueAt(row,0);
        return -1;

    }
    public ComptesPanel(IServiceClientGUI serviceClient,int top,int left,int bottom,int right){
        this.serviceClient=serviceClient;
        setBorder(new EmptyBorder(top,left,bottom,right));
        setBackground(new Color(34, 40, 49));
        initTable();

    }
}
