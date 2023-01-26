package presentation.vue.clientVue;

import presentation.modele.util.Sexe;
import presentation.vue.HintTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ClientCreationPanel extends JPanel {
//    id,nom,prenom,login,password,cin,tel,email,sex
    public static final String CHAMP_PRENOM = "prenom",CHAMP_NOM = "nom",CHAMP_EMAIL = "email", CHAMP_PASS = "pass",CHAMP_CIN = "cin",CHAMP_TEL = "tel",CHAMP_SEXE = "sexe";
    private JLabel lbl_id, lbl_nom, lbl_prenom, lbl_login, lbl_mdp,lbl_mdp_confirmation, lbl_cin, lbl_tel, lbl_email,lbl_sexe;
    private JLabel err_id, err_nom, err_prenom, err_login, err_mdp, err_mdp_confirmation, err_cin, err_tel, err_email,err_sexe;
    private HintTextField  txt_nom, txt_prenom, txt_login, txt_cin, txt_tel, txt_email;
    private JTextField txt_id;
    private JPasswordField txt_mdp,txt_mdp_confirmation;
    private JComboBox<String> txt_sexe;
    private long client_id;
    void initLabels(){
        lbl_id=setLabel("Identifiant",Color.WHITE,17);
        lbl_nom=setLabel("Nom",Color.WHITE,17);
        lbl_prenom=setLabel("Prenom",Color.WHITE,17);
        lbl_login=setLabel("Login",Color.WHITE,17);
        lbl_mdp=setLabel("Mot de passe",Color.WHITE,17);
        lbl_mdp_confirmation=setLabel("Confirmation Mot de passe",Color.WHITE,17);
        lbl_cin=setLabel("CIN",Color.WHITE,17);
        lbl_tel=setLabel("Telephone",Color.WHITE,17);
        lbl_email=setLabel("Email",Color.WHITE,17);
        lbl_sexe=setLabel("Sexe",Color.WHITE,17);

        err_id=setLabel("",Color.RED,12);
        err_nom=setLabel("",Color.RED,12);
        err_prenom=setLabel("",Color.RED,12);
        err_login=setLabel("",Color.RED,12);
        err_mdp=setLabel("",Color.RED,12);
        err_mdp_confirmation=setLabel("",Color.RED,12);
        err_cin=setLabel("",Color.RED,12);
        err_tel=setLabel("",Color.RED,12);
        err_email=setLabel("",Color.RED,12);
        err_sexe=setLabel("",Color.RED,12);
    }

    void initTextFields(){
        txt_id=new JTextField(client_id+"");
        txt_id.setFont(new Font("Optima",Font.BOLD,17));
        txt_id.setHorizontalAlignment(JTextField.CENTER);
        txt_id.setEditable(false);
        txt_nom=new HintTextField("Nom");
        txt_prenom=new HintTextField("Prenom");
        txt_login=new HintTextField("Login");
        txt_cin=new HintTextField("CIN");
        txt_tel=new HintTextField("TEL");
        txt_email=new HintTextField("Email");

        txt_mdp=new JPasswordField("");
        txt_mdp.setFont(new Font("Optima",Font.BOLD,17));
        txt_mdp.setForeground(Color.BLACK);
        txt_mdp.setHorizontalAlignment(JTextField.LEFT);

        txt_mdp_confirmation=new JPasswordField("");
        txt_mdp_confirmation.setFont(new Font("Optima",Font.BOLD,17));
        txt_mdp_confirmation.setForeground(Color.BLACK);
        txt_mdp_confirmation.setHorizontalAlignment(JTextField.LEFT);

        txt_sexe=new JComboBox<>();
        txt_sexe.addItem(Sexe.FEMME.getLibelle());
        txt_sexe.addItem(Sexe.HOMME.getLibelle());
        txt_sexe.setFont(new Font("Optima",Font.BOLD,17));
        ((JLabel)txt_sexe.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        txt_sexe.setForeground(Color.BLACK);


    }

    void initPanels(int top,int left,int bottom,int right){
        initTextFields();
        initLabels();

        setBorder(new EmptyBorder(top,left,bottom,right));
        setLayout(new BorderLayout());

        JPanel westPanel= new JPanel();
        westPanel.setLayout(new GridLayout(10,1,5,5));
        westPanel.setBorder(new EmptyBorder(10,10,10,0));
        westPanel.setBackground(new Color(34, 40, 49));
        westPanel.add(lbl_id);
        westPanel.add(lbl_nom);
        westPanel.add(lbl_prenom);
        westPanel.add(lbl_login);
        westPanel.add(lbl_mdp);
        westPanel.add(lbl_mdp_confirmation);
        westPanel.add(lbl_cin);
        westPanel.add(lbl_tel);
        westPanel.add(lbl_email);
        westPanel.add(lbl_sexe);

        JPanel centerPanel= new JPanel();
        centerPanel.setLayout(new GridLayout(10,1,5,5));
        centerPanel.setBorder(new EmptyBorder(10,10,10,0));
        centerPanel.setBackground(new Color(34, 40, 49));
        centerPanel.add(txt_id);
        centerPanel.add(txt_nom);
        centerPanel.add(txt_prenom);
        centerPanel.add(txt_login);
        centerPanel.add(txt_mdp);
        centerPanel.add(txt_mdp_confirmation);
        centerPanel.add(txt_cin);
        centerPanel.add(txt_tel);
        centerPanel.add(txt_email);
        centerPanel.add(txt_sexe);

        JPanel eastPanel= new JPanel();
        eastPanel.setLayout(new GridLayout(10,1,5,5));
        eastPanel.setBorder(new EmptyBorder(10,10,10,0));
        eastPanel.setPreferredSize(new Dimension(470,getHeight()));
        eastPanel.setBackground(new Color(34, 40, 49));
        eastPanel.add(err_id);
        eastPanel.add(err_nom);
        eastPanel.add(err_prenom);
        eastPanel.add(err_login);
        eastPanel.add(err_mdp);
        eastPanel.add(err_mdp_confirmation);
        eastPanel.add(err_cin);
        eastPanel.add(err_tel);
        eastPanel.add(err_email);
        eastPanel.add(err_sexe);


        add(westPanel,BorderLayout.WEST);
        add(centerPanel,BorderLayout.CENTER);
        add(eastPanel,BorderLayout.EAST);

    }
    private JLabel setLabel(String txt,Color color,int size){
        JLabel lbl=new JLabel(txt);
        lbl.setFont(new Font("Optima",Font.BOLD,size));
        lbl.setForeground(color);
        lbl.setHorizontalAlignment(JLabel.CENTER);
        return lbl;
    }
    public List<String> getValues(){
        List<String> values= new ArrayList<>();
        values.add(txt_prenom.getText());
        values.add(txt_nom.getText());
        values.add(txt_email.getText());
        values.add(String.valueOf(txt_mdp.getPassword()));
        values.add(String.valueOf(txt_mdp_confirmation.getPassword()));
        values.add(txt_cin.getText());
        values.add(txt_tel.getText());
        values.add((String) txt_sexe.getSelectedItem());
        return values;
    }
    public void setResult(Map<String,String> err){
        if(err==null){
                JOptionPane.showMessageDialog(this,"Client Ajoute","Succes",JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            if(err.containsKey(CHAMP_PRENOM))
                err_prenom.setText(err.get(CHAMP_PRENOM));

            if(err.containsKey(CHAMP_NOM))
                err_nom.setText(err.get(CHAMP_NOM));

            if(err.containsKey(CHAMP_EMAIL))
                err_email.setText(err.get(CHAMP_EMAIL));

            if(err.containsKey(CHAMP_PASS))
                err_mdp.setText(err.get(CHAMP_PASS));

            if(err.containsKey(CHAMP_CIN))
                err_cin.setText(err.get(CHAMP_CIN));

            if(err.containsKey(CHAMP_TEL))
                err_tel.setText(err.get(CHAMP_TEL));

            if(err.containsKey(CHAMP_SEXE))
                err_sexe.setText(err.get(CHAMP_SEXE));


        }
    }
    public ClientCreationPanel(int top, int left, int bottom, int right,long id){
        client_id=id;
        initPanels(top,left,bottom,right);
        setBackground(new Color(34, 40, 49));


    }


}
