package presentation.vue.generalVue;

import presentation.modele.util.TableauDeBord;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatistiquesPanel extends JPanel {
    private JLabel lbl_nbrClient,lbl_nbrCompte,lbl_maxSolde,lbl_minSolde,lbl_nomClient,lbl_totalHomme,lbl_totalFemme;
    private JTextField txt_nbrClient,txt_nbrCompte,txt_maxSolde,txt_minSolde,txt_nomClient,txt_totalHomme,txt_totalFemme;
    private TableauDeBord tableauDeBord;
    private void initLabels(){
        lbl_nbrClient = setLabel("Nombre de clients",Color.BLACK,17);
        lbl_nbrCompte= setLabel("Nombre de comptes",Color.BLACK,17);
        lbl_maxSolde= setLabel("Solde maximale des comptes",Color.BLACK,17);
        lbl_minSolde= setLabel("Solde minimale des comptes",Color.BLACK,17);
        lbl_nomClient= setLabel("Client le plus riche",Color.BLACK,17);
        lbl_totalHomme= setLabel("Nombre de client hommes",Color.BLACK,17);
        lbl_totalFemme= setLabel("Nombre de client femmes",Color.BLACK,17);
    }
    private void initTextFields(){
        txt_nbrClient=new JTextField(""+tableauDeBord.getNombreTotaleClient());
        txt_nbrCompte=new JTextField(""+tableauDeBord.getNombreTotaleCompte());
        txt_maxSolde=new JTextField(""+tableauDeBord.getMaxSolde());
        txt_minSolde=new JTextField(""+tableauDeBord.getMinSolde());
        txt_nomClient=new JTextField(tableauDeBord.getNomClientLePlusRiche());
        txt_totalHomme=new JTextField(""+tableauDeBord.getTotaleClientsHomme());
        txt_totalFemme=new JTextField(""+tableauDeBord.getTotalClientsFemme());

        txt_nbrClient.setEditable(false);
        txt_nbrCompte.setEditable(false);
        txt_maxSolde.setEditable(false);
        txt_minSolde.setEditable(false);
        txt_nomClient.setEditable(false);
        txt_totalHomme.setEditable(false);
        txt_totalFemme.setEditable(false);


    }
    private void initPanel(){
        initLabels();
        initTextFields();
        setLayout(new BorderLayout());
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new GridLayout(7,1,10,10));
        westPanel.setBackground(new Color(34, 40, 49));
        westPanel.add(lbl_nbrClient);
        westPanel.add(lbl_nbrCompte);
        westPanel.add(lbl_maxSolde);
        westPanel.add(lbl_minSolde);
        westPanel.add(lbl_nomClient);
        westPanel.add(lbl_totalHomme);
        westPanel.add(lbl_totalFemme);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(7,1,10,10));
        centerPanel.setBackground(new Color(34, 40, 49));
        centerPanel.add(txt_nbrClient);
        centerPanel.add(txt_nbrCompte);
        centerPanel.add(txt_maxSolde);
        centerPanel.add(txt_minSolde);
        centerPanel.add(txt_nomClient);
        centerPanel.add(txt_totalHomme);
        centerPanel.add(txt_totalFemme);

        add(westPanel,BorderLayout.WEST);
        add(centerPanel,BorderLayout.CENTER);

    }
    private JLabel setLabel(String txt, Color color, int size){
        JLabel lbl=new JLabel(txt);
        lbl.setFont(new Font("Optima",Font.BOLD,size));
        lbl.setForeground(color);
        lbl.setHorizontalAlignment(JLabel.CENTER);
        return lbl;
    }
    public StatistiquesPanel(TableauDeBord tableauDeBord,int top,int left,int bottom, int right){
        this.tableauDeBord=tableauDeBord;
        setBorder(new EmptyBorder(top,left,bottom,right));
        initPanel();
    }
}
