package presentation.vue.clientVue;

import metier.admin.IServiceAdminGUI;
import metier.clients.IServiceClientGUI;
import metier.clients.ServiceClientGUI;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.util.ActionResult;
import presentation.vue.HintTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Objects;

public class ModificationPanel extends JPanel {
    public static final String CHAMP_PRENOM = "prenom",CHAMP_NOM = "nom",CHAMP_EMAIL = "email", CHAMP_PASS = "pass",CHAMP_CIN = "cin",CHAMP_TEL = "tel",CHAMP_SEXE = "sexe";
    private JLabel lbl_id, lbl_nom, lbl_prenom, lbl_login, lbl_mdp,lbl_mdp_confirmation, lbl_cin, lbl_tel, lbl_email,lbl_sexe;
    private JLabel err_id, err_nom, err_prenom, err_login, err_mdp, err_mdp_confirmation, err_cin, err_tel, err_email,err_sexe;
    private HintTextField  txt_tel, txt_email;
    private JTextField txt_id,txt_nom, txt_prenom, txt_login, txt_cin,txt_sexe;
    private JPasswordField txt_mdp,txt_mdp_confirmation;
    private JButton btn_edit,btn_cancel;
    private IServiceClientGUI serviceCLient;
    private Client client;
    private ClassLoader cl=getClass().getClassLoader();;
    void initLabels(){
        lbl_id=setLabel("Identifiant", Color.WHITE,17);
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
        txt_id=setNonEditableTextFields(client.getId()+"",17);

        txt_nom=setNonEditableTextFields(client.getNom(),17);
        txt_prenom=setNonEditableTextFields(client.getPrenom(),17);
        txt_login=setNonEditableTextFields(client.getLogin(),17);
        txt_cin=setNonEditableTextFields(client.getCin(),17);
        txt_tel=new HintTextField(client.getTel());
        txt_email=new HintTextField(client.getEmail());
        txt_sexe=setNonEditableTextFields(client.getSexe().getLibelle(),17);

        txt_mdp=new JPasswordField(client.getMotDePasse());
        txt_mdp.setFont(new Font("Optima",Font.BOLD,17));
        txt_mdp.setForeground(Color.BLACK);
        txt_mdp.setHorizontalAlignment(JTextField.LEFT);

        txt_mdp_confirmation=new JPasswordField(client.getMotDePasse());
        txt_mdp_confirmation.setFont(new Font("Optima",Font.BOLD,17));
        txt_mdp_confirmation.setForeground(Color.BLACK);
        txt_mdp_confirmation.setHorizontalAlignment(JTextField.LEFT);


    }
    private void initButtons(){
        btn_edit = new JButton(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/add.png"))));
        btn_edit.setFont(new Font("Optima",Font.BOLD,17));
        btn_edit.setBackground(new Color(0, 173, 181));

        btn_cancel = new JButton(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/cancel.png"))));
        btn_cancel.setFont(new Font("Optima",Font.BOLD,17));
        btn_cancel.setBackground(new Color(0, 173, 181));
    }
    private void initActions(){
        btn_edit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_edit.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/editHover.png"))));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_edit.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/edit.png"))));
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
            resetFields(client.getTel(),client.getEmail());
        });
        btn_edit.addActionListener(e -> {
            ActionResult actionResult= serviceCLient.modifierProfile(txt_email.getText(),String.valueOf(txt_mdp.getPassword()),String.valueOf(txt_mdp_confirmation.getPassword()),txt_tel.getText());
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
        southPanel.add(btn_edit);
        southPanel.add(btn_cancel);

        add(westPanel,BorderLayout.WEST);
        add(centerPanel,BorderLayout.CENTER);
        add(eastPanel,BorderLayout.EAST);
        add(southPanel,BorderLayout.SOUTH);
    }
    private JTextField setNonEditableTextFields(String ph,int size){
        JTextField txt=new JTextField(ph);
        txt.setFont(new Font("Optima",Font.BOLD,size));
        txt.setHorizontalAlignment(JTextField.CENTER);
        txt.setEditable(false);
        return txt;
    }
    private JLabel setLabel(String txt,Color color,int size){
        JLabel lbl=new JLabel(txt);
        lbl.setFont(new Font("Optima",Font.BOLD,size));
        lbl.setForeground(color);
        lbl.setHorizontalAlignment(JLabel.CENTER);
        return lbl;
    }
    private void setResult(Map<String,String> err){
        if(err==null){
            JOptionPane.showMessageDialog(this,"Client Modifie","Succes",JOptionPane.INFORMATION_MESSAGE);
            resetFields(txt_tel.getText(),txt_email.getText());
        }
        else {

            if(err.containsKey(CHAMP_EMAIL))
                err_email.setText(err.get(CHAMP_EMAIL));

            if(err.containsKey(CHAMP_PASS))
                err_mdp.setText(err.get(CHAMP_PASS));


            if(err.containsKey(CHAMP_TEL))
                err_tel.setText(err.get(CHAMP_TEL));



        }
    }

    private void resetFields(String tel, String email ){
        txt_mdp.setText("");
        txt_mdp_confirmation.setText("");
        txt_tel.resetField(tel);
        txt_email.resetField(email);
    }
    public ModificationPanel(IServiceClientGUI serviceClient, int top, int left, int bottom, int right){
        this.serviceCLient =serviceClient;
        this.client=((ServiceClientGUI)serviceClient).getClient();
        initPanels(top,left,bottom,right);
        setBackground(new Color(34, 40, 49));


    }
}
