package presentation.vue.adminVue.compteVue;

import metier.admin.IServiceAdminGUI;
import presentation.modele.entitesDeLaBanque.Compte;
import presentation.modele.util.ActionResult;
import presentation.vue.HintTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Objects;

public class CompteCreationPanel extends JPanel{
    private JLabel lbl_numCompte,lbl_idClient,lbl_solde;
    private JLabel err_numCompte, err_idClient, err_solde;
    private HintTextField txt_numCompte,txt_idClient,txt_solde;
    private JButton btn_reset,btn_add;
    private IServiceAdminGUI serviceAdmin;
    private ClassLoader cl=getClass().getClassLoader();

    private void initLabels(){
        lbl_numCompte=setLabel("numero du compte",Color.WHITE,17);
        lbl_idClient=setLabel("Identifiant du client",Color.WHITE,17);
        lbl_solde=setLabel("Solde du compte",Color.WHITE,17);

        err_numCompte=setLabel("",Color.RED,12);
        err_idClient=setLabel("",Color.RED,12);
        err_solde=setLabel("",Color.RED,12);
    }
    private void initTextFields(){
        txt_numCompte=new HintTextField("numero du compte");
        txt_numCompte.setEditable(false);
        txt_numCompte.setText("b-co00"+Compte.getCompteur());
        txt_idClient=new HintTextField("Identifiant du client");
        txt_solde=new HintTextField("Solde");
    }
    private void initButtons(){
        btn_add = new JButton(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/add.png"))));
        btn_add.setFont(new Font("Optima",Font.BOLD,17));
        btn_add.setBackground(new Color(0, 173, 181));

        btn_reset = new JButton(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/cancel.png"))));
        btn_reset.setFont(new Font("Optima",Font.BOLD,17));
        btn_reset.setBackground(new Color(0, 173, 181));
    }
    private void initActions(){
        btn_add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_add.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/addHover.png"))));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_add.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/add.png"))));
            }
        });

        btn_reset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_reset.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/cancelHover.png"))));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_reset.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/cancel.png"))));
            }
        });

        btn_add.addActionListener(e -> {
            err_numCompte.setText("");
            err_idClient.setText("");
            err_solde.setText("");

            ActionResult actionResult=serviceAdmin.nouveauCompteClientExistant(txt_idClient.getText(),txt_solde.getText());
            if(actionResult.isSuccess()){
                btn_reset.doClick();
                JOptionPane.showMessageDialog(this,"Compte Ajoute","Succes",JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                Map<String, String> errs=actionResult.getErrorMessage();
                if(errs.containsKey("compte"))
                    err_numCompte.setText(errs.get("compte"));
                if(errs.containsKey("client"))
                    err_idClient.setText(errs.get("client"));
                if(errs.containsKey("solde"))
                    err_solde.setText(errs.get("solde"));
            }
        });
        btn_reset.addActionListener(e -> {
            txt_idClient.resetField("Identifiant du client");
            txt_solde.resetField("Solde");
            err_solde.setText("");
            err_idClient.setText("");
            err_numCompte.setText("");
        });
    }
    private void initPanels(){
        initTextFields();
        initLabels();
        initButtons();
        initActions();
        setLayout(new BorderLayout());

        JPanel westPanel= new JPanel();
        westPanel.setLayout(new GridLayout(10,1,5,5));
        westPanel.setBorder(new EmptyBorder(10,10,10,0));
        westPanel.setBackground(new Color(34, 40, 49));
        westPanel.add(lbl_numCompte);
        westPanel.add(lbl_idClient);
        westPanel.add(lbl_solde);


        JPanel centerPanel= new JPanel();
        centerPanel.setLayout(new GridLayout(10,1,5,5));
        centerPanel.setBorder(new EmptyBorder(10,10,10,0));
        centerPanel.setBackground(new Color(34, 40, 49));
        centerPanel.add(txt_numCompte);
        centerPanel.add(txt_idClient);
        centerPanel.add(txt_solde);


        JPanel eastPanel= new JPanel();
        eastPanel.setLayout(new GridLayout(10,1,5,5));
        eastPanel.setBorder(new EmptyBorder(10,10,10,0));
        eastPanel.setPreferredSize(new Dimension(470,getHeight()));
        eastPanel.setBackground(new Color(34, 40, 49));
        eastPanel.add(err_numCompte);
        eastPanel.add(err_idClient);
        eastPanel.add(err_solde);

        JPanel southPanel= new JPanel();
        southPanel.setBackground(new Color(34, 40, 49));
        southPanel.setBorder(new EmptyBorder(10,10,10,10));
        southPanel.setLayout(new GridLayout(1,2,20,20));
        southPanel.setPreferredSize(new Dimension(getWidth(),100));
        southPanel.add(btn_add);
        southPanel.add(btn_reset);

        add(westPanel,BorderLayout.WEST);
        add(centerPanel,BorderLayout.CENTER);
        add(eastPanel,BorderLayout.EAST);
        add(southPanel,BorderLayout.SOUTH);
    }
    private JLabel setLabel(String txt,Color color,int size){
        JLabel lbl=new JLabel(txt);
        lbl.setFont(new Font("Optima",Font.BOLD,size));
        lbl.setForeground(color);
        lbl.setHorizontalAlignment(JLabel.CENTER);
        return lbl;
    }

    public CompteCreationPanel(IServiceAdminGUI serviceAdmin,int top, int left, int bottom, int right){
        this.serviceAdmin=serviceAdmin;
        setBorder(new EmptyBorder(top,left,bottom,right));
        initPanels();
        setBackground(new Color(34, 40, 49));
    }


}
