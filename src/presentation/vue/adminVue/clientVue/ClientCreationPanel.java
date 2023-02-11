package presentation.vue.adminVue.clientVue;

import metier.admin.IServiceAdminGUI;
import presentation.modele.util.ActionResult;
import presentation.modele.util.Sexe;
import presentation.vue.HintTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class ClientCreationPanel extends JPanel {
//    id,nom,prenom,login,password,cin,tel,email,sex
    public static final String CHAMP_PRENOM = "prenom",CHAMP_NOM = "nom",CHAMP_EMAIL = "email", CHAMP_PASS = "pass",CHAMP_CIN = "cin",CHAMP_TEL = "tel",CHAMP_SEXE = "sexe";
    private JLabel lbl_id, lbl_nom, lbl_prenom, lbl_login, lbl_mdp,lbl_mdp_confirmation, lbl_cin, lbl_tel, lbl_email,lbl_sexe;
    private JLabel err_id, err_nom, err_prenom, err_login, err_mdp, err_mdp_confirmation, err_cin, err_tel, err_email,err_sexe;
    private HintTextField txt_id, txt_nom, txt_prenom, txt_login, txt_cin, txt_tel, txt_email;
    private JPasswordField txt_mdp,txt_mdp_confirmation;
    private JComboBox<String> txt_sexe;
    private JButton btn_add,btn_cancel;
    private IServiceAdminGUI serviceAdmin;
    private long client_id;
    private ClassLoader cl=getClass().getClassLoader();
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
        txt_id=new HintTextField("Client ID");
        txt_id.setEditable(false);
        txt_id.setText(client_id+"");
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
        txt_sexe.addItem(Sexe.HOMME.getLibelle());
        txt_sexe.addItem(Sexe.FEMME.getLibelle());
        txt_sexe.setFont(new Font("Optima",Font.BOLD,17));
        ((JLabel)txt_sexe.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        txt_sexe.setForeground(Color.BLACK);

    }
    private void initButtons(){
        btn_add = new JButton(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/add.png"))));
        btn_add.setFont(new Font("Optima",Font.BOLD,17));
        btn_add.setBackground(new Color(0, 173, 181));

        btn_cancel = new JButton(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/cancel.png"))));
        btn_cancel.setFont(new Font("Optima",Font.BOLD,17));
        btn_cancel.setBackground(new Color(0, 173, 181));
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

        btn_cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_cancel.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/cancelHover.png"))));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_cancel.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/cancel.png"))));
            }
        });

        btn_cancel.addActionListener(e -> {
            resetFields(client_id,"Prenom","Nom","Login","","","CIN","TEL","Email","");
        });
        btn_add.addActionListener(e -> {
                List<String> values=getValues();
                ActionResult actionResult= serviceAdmin.nouveauClient(values.get(0),values.get(1),values.get(2),values.get(3),values.get(4),values.get(5),values.get(6),values.get(7));
                setResult(actionResult.getErrorMessage());

        });
    }
    void initPanels(int top,int left,int bottom,int right){
        initTextFields();
        initLabels();
        initButtons();
        initActions();
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

        JPanel southPanel= new JPanel();
        southPanel.setBackground(new Color(34, 40, 49));
        southPanel.setBorder(new EmptyBorder(10,10,10,10));
        southPanel.setLayout(new GridLayout(1,2,20,20));
        southPanel.setPreferredSize(new Dimension(getWidth(),100));
        southPanel.add(btn_add);
        southPanel.add(btn_cancel);

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
    private List<String> getValues(){
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
    private void setResult(Map<String,String> err){
        if(err==null){
                JOptionPane.showMessageDialog(this,"Client Ajoute","Succes",JOptionPane.INFORMATION_MESSAGE);
                resetFields(client_id,"Prenom","Nom","Login","","","CIN","TEL","Email","");
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

    private void resetFields(long id,String prenom,String nom,String login,String mdp,String mdpC,String cin,String tel, String email, String sexe ){
        txt_id.setText(id+"");
        txt_prenom.resetField(prenom);
        txt_nom.resetField(nom);
        txt_login.resetField(login);
        txt_mdp.setText(mdp);
        txt_mdp_confirmation.setText(mdpC);
        txt_cin.resetField(cin);
        txt_tel.resetField(tel);
        txt_email.resetField(email);
        txt_sexe.setSelectedItem(sexe);

        err_id.setText("");
        err_nom.setText("");
        err_prenom.setText("");
        err_login.setText("");
        err_mdp.setText("");
        err_mdp_confirmation.setText("");
        err_cin.setText("");
        err_tel.setText("");
        err_email.setText("");
        err_sexe.setText("");

    }
    public ClientCreationPanel(IServiceAdminGUI serviceAdmin,int top, int left, int bottom, int right,long id){
        this.serviceAdmin=serviceAdmin;
        client_id=id;
        initPanels(top,left,bottom,right);
        setBackground(new Color(34, 40, 49));


    }


}
