package presentation.vue;

import metier.forms.LoginFormValidator;
import presentation.modele.entitesDeLaBanque.Banque;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

public class LoginFrame extends JFrame {
    private Container mainContainer;
    private JPanel titlePane, buttonsPane, formPane;
    private JLabel lbl_title, lbl_login, lpl_pass;
    private JTextField txt_login;
    private JPasswordField txt_pass;
    private JButton btn_login, btn_cancel;

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
    }

    public void initContainer(){
        initPanels();
        mainContainer=this.getContentPane();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.add(titlePane,BorderLayout.NORTH);
        mainContainer.add(formPane,BorderLayout.CENTER);
        mainContainer.add(buttonsPane,BorderLayout.SOUTH);
    }

    public JButton getBtn_login() {
        return btn_login;
    }

    public JTextField getTxt_login() {
        return txt_login;
    }

    public JPasswordField getTxt_pass() {
        return txt_pass;
    }

    public void setTxt_login(JTextField txt_login) {
        this.txt_login = txt_login;
    }

    public void setTxt_pass(JPasswordField txt_pass) {
        this.txt_pass = txt_pass;
    }

    public LoginFrame(String title){
        initContainer();
        setTitle(title);
        setSize(400,500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFrame("Login");
    }

}
