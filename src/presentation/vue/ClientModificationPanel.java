package presentation.vue;

import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.util.Sexe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ClientModificationPanel extends JPanel {
    //    id,nom,prenom,login,password,cin,tel,email,sex
    private JLabel lbl_id, lbl_nom, lbl_prenom, lbl_login, lbl_mdp,lbl_mdp_confirmation, lbl_cin, lbl_tel, lbl_email,lbl_sexe;
    private JTextField txt_id, txt_nom, txt_prenom, txt_login, txt_cin, txt_tel, txt_email;
    private JPasswordField txt_mdp,txt_mdp_confirmation;
    private JComboBox<Sexe> txt_sexe;
    private List<Integer> fields;
    private Client client;
    void initLabels(){
        lbl_id=new JLabel("Identifiant");
        lbl_id.setFont(new Font("Optima",Font.BOLD,17));
        lbl_id.setForeground(Color.WHITE);
        lbl_id.setHorizontalAlignment(JLabel.CENTER);

        lbl_nom=new JLabel("Nom");
        lbl_nom.setFont(new Font("Optima",Font.BOLD,17));
        lbl_nom.setForeground(Color.WHITE);
        lbl_nom.setHorizontalAlignment(JLabel.CENTER);

        lbl_prenom=new JLabel("Prenom");
        lbl_prenom.setFont(new Font("Optima",Font.BOLD,17));
        lbl_prenom.setForeground(Color.WHITE);
        lbl_prenom.setHorizontalAlignment(JLabel.CENTER);

        lbl_login=new JLabel("Login");
        lbl_login.setFont(new Font("Optima",Font.BOLD,17));
        lbl_login.setForeground(Color.WHITE);
        lbl_login.setHorizontalAlignment(JLabel.CENTER);

        lbl_mdp=new JLabel("Mot de passe");
        lbl_mdp.setFont(new Font("Optima",Font.BOLD,17));
        lbl_mdp.setForeground(Color.WHITE);
        lbl_mdp.setHorizontalAlignment(JLabel.CENTER);

        lbl_mdp_confirmation=new JLabel("Confirmation Mot de passe");
        lbl_mdp_confirmation.setFont(new Font("Optima",Font.BOLD,17));
        lbl_mdp_confirmation.setForeground(Color.WHITE);
        lbl_mdp_confirmation.setHorizontalAlignment(JLabel.CENTER);

        lbl_cin=new JLabel("CIN");
        lbl_cin.setFont(new Font("Optima",Font.BOLD,17));
        lbl_cin.setForeground(Color.WHITE);
        lbl_cin.setHorizontalAlignment(JLabel.CENTER);

        lbl_tel=new JLabel("Telephone");
        lbl_tel.setFont(new Font("Optima",Font.BOLD,17));
        lbl_tel.setForeground(Color.WHITE);
        lbl_tel.setHorizontalAlignment(JLabel.CENTER);

        lbl_email=new JLabel("Email");
        lbl_email.setFont(new Font("Optima",Font.BOLD,17));
        lbl_email.setForeground(Color.WHITE);
        lbl_email.setHorizontalAlignment(JLabel.CENTER);

        lbl_sexe=new JLabel("Sexe");
        lbl_sexe.setFont(new Font("Optima",Font.BOLD,17));
        lbl_sexe.setForeground(Color.WHITE);
        lbl_sexe.setHorizontalAlignment(JLabel.CENTER);
    }

    void initTextFields(){
        txt_id=new JTextField(client.getId().toString());
        txt_id.setFont(new Font("Optima",Font.BOLD,17));
        txt_id.setForeground(Color.BLACK);
        txt_id.setHorizontalAlignment(JTextField.LEFT);
        if(fields.contains(1))txt_id.setEditable(false);

        txt_nom=new JTextField(client.getNom());
        txt_nom.setFont(new Font("Optima",Font.BOLD,17));
        txt_nom.setForeground(Color.BLACK);
        txt_nom.setHorizontalAlignment(JTextField.LEFT);
        if(fields.contains(2))txt_nom.setEditable(false);

        txt_prenom=new JTextField(client.getPrenom());
        txt_prenom.setFont(new Font("Optima",Font.BOLD,17));
        txt_prenom.setForeground(Color.BLACK);
        txt_prenom.setHorizontalAlignment(JTextField.LEFT);
        if(fields.contains(3))txt_prenom.setEditable(false);

        txt_login=new JTextField(client.getLogin());
        txt_login.setFont(new Font("Optima",Font.BOLD,17));
        txt_login.setForeground(Color.BLACK);
        txt_login.setHorizontalAlignment(JTextField.LEFT);
        if(fields.contains(4))txt_login.setEditable(false);

        txt_cin=new JTextField(client.getCin());
        txt_cin.setFont(new Font("Optima",Font.BOLD,17));
        txt_cin.setForeground(Color.BLACK);
        txt_cin.setHorizontalAlignment(JTextField.LEFT);
        if(fields.contains(5))txt_cin.setEditable(false);

        txt_tel=new JTextField(client.getTel());
        txt_tel.setFont(new Font("Optima",Font.BOLD,17));
        txt_tel.setForeground(Color.BLACK);
        txt_tel.setHorizontalAlignment(JTextField.LEFT);
        if(fields.contains(6))txt_tel.setEditable(false);

        txt_email=new JTextField(client.getEmail());
        txt_email.setFont(new Font("Optima",Font.BOLD,17));
        txt_email.setForeground(Color.BLACK);
        txt_email.setHorizontalAlignment(JTextField.LEFT);
        if(fields.contains(7))txt_email.setEditable(false);

        txt_mdp=new JPasswordField(client.getMotDePasse());
        txt_mdp.setFont(new Font("Optima",Font.BOLD,17));
        txt_mdp.setForeground(Color.BLACK);
        txt_mdp.setHorizontalAlignment(JTextField.LEFT);
        if(fields.contains(8))txt_mdp.setEditable(false);

        txt_mdp_confirmation=new JPasswordField(client.getMotDePasse());
        txt_mdp_confirmation.setFont(new Font("Optima",Font.BOLD,17));
        txt_mdp_confirmation.setForeground(Color.BLACK);
        txt_mdp_confirmation.setHorizontalAlignment(JTextField.LEFT);
        if(fields.contains(9))txt_mdp_confirmation.setEditable(false);

        txt_sexe=new JComboBox<>();
        txt_sexe.addItem(Sexe.FEMME);
        txt_sexe.addItem(Sexe.HOMME);
        txt_sexe.setSelectedItem(client.getSexe());
        txt_sexe.setFont(new Font("Optima",Font.BOLD,17));
        txt_sexe.setForeground(Color.BLACK);
        if(fields.contains(10))txt_sexe.setEditable(false);

    }

    void initPanels(int top,int left,int bottom,int right){
        initTextFields();
        initLabels();

        setBorder(new EmptyBorder(top,left,bottom,right));
        setLayout(new BorderLayout());

        JPanel westPanel= new JPanel();
        westPanel.setLayout(new GridLayout(10,1,5,5));
        westPanel.setBorder(new EmptyBorder(10,10,10,50));
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
        centerPanel.setBorder(new EmptyBorder(10,10,10,100));
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


        add(westPanel,BorderLayout.WEST);
        add(centerPanel,BorderLayout.CENTER);
    }
    ClientModificationPanel(int top, int left, int bottom, int right, Client client, List<Integer> fields){
        this.client=client;
        this.fields=fields;
        initPanels(top,left,bottom,right);
        setBackground(new Color(34, 40, 49));


    }
}
