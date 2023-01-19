package presentation.vue.clientVue;

import presentation.modele.util.Sexe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

public class ClientCreationPanel extends JPanel {
//    id,nom,prenom,login,password,cin,tel,email,sex
    private JLabel lbl_id, lbl_nom, lbl_prenom, lbl_login, lbl_mdp,lbl_mdp_confirmation, lbl_cin, lbl_tel, lbl_email,lbl_sexe;
    private JTextField txt_id, txt_nom, txt_prenom, txt_login, txt_cin, txt_tel, txt_email;
    private JPasswordField txt_mdp,txt_mdp_confirmation;
    private JComboBox<Sexe> txt_sexe;

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
        txt_id=new JTextField("1");
        txt_id.setFont(new Font("Optima",Font.BOLD,17));
        txt_id.setForeground(Color.BLACK);
        txt_id.setHorizontalAlignment(JTextField.LEFT);
        txt_id.setEditable(false);

        txt_nom=new JTextField("");
        txt_nom.setFont(new Font("Optima",Font.BOLD,17));
        txt_nom.setForeground(Color.BLACK);
        txt_nom.setHorizontalAlignment(JTextField.LEFT);

        txt_prenom=new JTextField("");
        txt_prenom.setFont(new Font("Optima",Font.BOLD,17));
        txt_prenom.setForeground(Color.BLACK);
        txt_prenom.setHorizontalAlignment(JTextField.LEFT);

        txt_login=new JTextField("");
        txt_login.setFont(new Font("Optima",Font.BOLD,17));
        txt_login.setForeground(Color.BLACK);
        txt_login.setHorizontalAlignment(JTextField.LEFT);

        txt_cin=new JTextField("");
        txt_cin.setFont(new Font("Optima",Font.BOLD,17));
        txt_cin.setForeground(Color.BLACK);
        txt_cin.setHorizontalAlignment(JTextField.LEFT);

        txt_tel=new JTextField("");
        txt_tel.setFont(new Font("Optima",Font.BOLD,17));
        txt_tel.setForeground(Color.BLACK);
        txt_tel.setHorizontalAlignment(JTextField.LEFT);

        txt_email=new JTextField("");
        txt_email.setFont(new Font("Optima",Font.BOLD,17));
        txt_email.setForeground(Color.BLACK);
        txt_email.setHorizontalAlignment(JTextField.LEFT);

        txt_mdp=new JPasswordField("");
        txt_mdp.setFont(new Font("Optima",Font.BOLD,17));
        txt_mdp.setForeground(Color.BLACK);
        txt_mdp.setHorizontalAlignment(JTextField.LEFT);

        txt_mdp_confirmation=new JPasswordField("");
        txt_mdp_confirmation.setFont(new Font("Optima",Font.BOLD,17));
        txt_mdp_confirmation.setForeground(Color.BLACK);
        txt_mdp_confirmation.setHorizontalAlignment(JTextField.LEFT);

        txt_sexe=new JComboBox<>();
        txt_sexe.addItem(Sexe.FEMME);
        txt_sexe.addItem(Sexe.HOMME);
        txt_sexe.setFont(new Font("Optima",Font.BOLD,17));
        txt_sexe.setForeground(Color.BLACK);


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
    public ClientCreationPanel(int top,int left,int bottom,int right){
        initPanels(top,left,bottom,right);
        setBackground(new Color(34, 40, 49));


    }
}
