package presentation.vue;

import dao.daoFiles.ClientDao;
import metier.admin.ServiceAdminGUI;
import metier.authentification.IAuthGUI;
import metier.authentification.ServiceAuthGUI;
import metier.clients.ServiceClientGUI;
import presentation.modele.entitesDeLaBanque.Admin;
import presentation.modele.entitesDeLaBanque.Banque;
import presentation.modele.util.AuthResult;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class LoginFrame extends JFrame {
    private Container mainContainer;
    private JPanel titlePane, buttonsPane, formPane;
    private JLabel lbl_title, lbl_login, lpl_pass;
    private JTextField txt_login;
    private JPasswordField txt_pass;
    private JButton btn_login, btn_cancel;
    private IAuthGUI auth;
    private Banque banque;

    public void initLabels(){

        lbl_title= new JLabel("Login");
        lbl_title.setFont(new Font("Optima",Font.BOLD,30));
        lbl_title.setForeground(new Color(238, 238, 238));
        lbl_title.setHorizontalAlignment(JLabel.CENTER);

        lbl_login= new JLabel("Login");
        lbl_login.setFont(new Font("Optima",Font.BOLD,17));
        lbl_login.setForeground(new Color(238, 238, 238));
        lbl_login.setHorizontalAlignment(JLabel.CENTER);

        lpl_pass= new JLabel("Password");
        lpl_pass.setFont(new Font("Optima",Font.BOLD,17));
        lpl_pass.setForeground(new Color(238, 238, 238));
        lpl_pass.setHorizontalAlignment(JLabel.CENTER);


    }

    public void initTextFields(){
        txt_login= new JTextField("");
        txt_login.setFont(new Font("Optima",Font.BOLD,17));
        txt_login.setForeground(Color.BLACK);
        txt_login.setHorizontalAlignment(JTextField.CENTER);

        txt_pass= new JPasswordField("");
        txt_pass.setFont(new Font("Optima",Font.BOLD,17));
        txt_pass.setForeground(Color.BLACK);
        txt_pass.setHorizontalAlignment(JPasswordField.CENTER);
    }

    public void initButtons(){
        btn_login= new JButton("Login");
        btn_login.setFont(new Font("Optima",Font.BOLD,17));
        btn_login.setForeground(new Color(238, 238, 238));
        btn_login.setBackground(new Color(34, 40, 49));
        btn_login.setHorizontalAlignment(JLabel.CENTER);

        btn_cancel= new JButton("Cancel");
        btn_cancel.setFont(new Font("Optima",Font.BOLD,17));
        btn_cancel.setForeground(new Color(238, 238, 238));
        btn_cancel.setBackground(new Color(34, 40, 49));
        btn_cancel.setHorizontalAlignment(JLabel.CENTER);
    }
    public void initPanels(){
        initTextFields();
        initButtons();
        initLabels();
//        titlePanel
        titlePane=new JPanel();
        titlePane.setBackground(new Color(0, 173, 181));
        titlePane.setBorder(new EmptyBorder(10,10,10,10));
        titlePane.add(lbl_title);

//        formPanel
        formPane=new JPanel();
        formPane.setBackground(new Color(55, 65, 79));
        formPane.setBorder(new EmptyBorder(10,10,10,10));
        formPane.setLayout(new BorderLayout());

        JPanel westPane= new JPanel();
        westPane.setBackground(new Color(55, 65, 79));
        westPane.setLayout(new GridLayout(2,1));
        westPane.setBorder(new EmptyBorder(120,0,120,40));
        westPane.add(lbl_login);
        westPane.add(lpl_pass);

        JPanel centerPane= new JPanel();
        centerPane.setBackground(new Color(55, 65, 79));
        centerPane.setLayout(new GridLayout(2,1,10,10));
        centerPane.setBorder(new EmptyBorder(120,20,120,20));
        centerPane.add(txt_login);
        centerPane.add(txt_pass);
        formPane.add(westPane,BorderLayout.WEST);
        formPane.add(centerPane, BorderLayout.CENTER);

//        buttonsPanel
        buttonsPane=new JPanel();
        buttonsPane.setBackground(new Color(0, 173, 181));
        buttonsPane.setBorder(new EmptyBorder(10,10,10,10));
        buttonsPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPane.add(btn_cancel);
        buttonsPane.add(btn_login);
        initActions();
    }

    public void initContainer(){
        initPanels();
        mainContainer=this.getContentPane();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.add(titlePane,BorderLayout.NORTH);
        mainContainer.add(formPane,BorderLayout.CENTER);
        mainContainer.add(buttonsPane,BorderLayout.SOUTH);
    }

    public void initActions(){
        btn_login.addActionListener( l -> {
                AuthResult authResult =auth.seConnecter(txt_login.getText(),new String(txt_pass.getPassword()));
                if(!authResult.isSuccess()) JOptionPane.showMessageDialog(this,authResult.getErrorMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                else {
                    if(authResult.getUtilisateur() instanceof Admin)
                        new MainFrame("Banque",new ServiceAdminGUI(banque),null);
                    else new MainFrame("Banque",null,new ServiceClientGUI(new ClientDao(),banque.getClientsDeBanque().get(0)));
                    dispose();}
            });
        btn_cancel.addActionListener(l -> dispose());
    }

    public LoginFrame(String title,Banque banque){
        initContainer();
        setTitle(title);
        setSize(400,500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setVisible(true);
        this.banque=banque;
        auth=new ServiceAuthGUI(banque);
    }
}
