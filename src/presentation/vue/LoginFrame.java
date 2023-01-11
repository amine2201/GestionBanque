package presentation.vue;

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

    public void initLabels(){
        lbl_title= new JLabel("Login");
        lbl_title.setFont(new Font("Optima",Font.BOLD,30));
        lbl_title.setForeground(Color.WHITE);
        lbl_title.setHorizontalAlignment(JLabel.CENTER);

        lbl_login= new JLabel("Login");
        lbl_login.setFont(new Font("Optima",Font.BOLD,17));
        lbl_login.setForeground(Color.BLUE);
        lbl_login.setHorizontalAlignment(JLabel.CENTER);

        lpl_pass= new JLabel("Password");
        lpl_pass.setFont(new Font("Optima",Font.BOLD,17));
        lpl_pass.setForeground(Color.BLUE);
        lpl_pass.setHorizontalAlignment(JLabel.CENTER);


    }

    public void initTextFields(){
        txt_login= new JTextField("");
        txt_login.setFont(new Font("Optima",Font.BOLD,17));
        txt_login.setForeground(Color.yellow);
        txt_login.setHorizontalAlignment(JTextField.CENTER);

        txt_pass= new JPasswordField("");
        txt_pass.setFont(new Font("Optima",Font.BOLD,17));
        txt_pass.setForeground(Color.yellow);
        txt_pass.setHorizontalAlignment(JPasswordField.CENTER);
    }

    public void initButtons(){
        btn_login= new JButton("Login");
        btn_login.setFont(new Font("Optima",Font.BOLD,17));
        btn_login.setForeground(Color.BLACK);
        btn_login.setHorizontalAlignment(JLabel.CENTER);

        btn_cancel= new JButton("Cancel");
        btn_cancel.setFont(new Font("Optima",Font.BOLD,17));
        btn_cancel.setForeground(Color.BLACK);
        btn_cancel.setHorizontalAlignment(JLabel.CENTER);
    }
    public void initPanels(){
        initTextFields();
        initButtons();
        initLabels();
//        titlePanel
        titlePane=new JPanel();
        titlePane.setBackground(new Color(49,83,168));
        titlePane.setBorder(new EmptyBorder(10,10,10,10));
        titlePane.add(lbl_title);

//        formPanel
        formPane=new JPanel();
        formPane.setBackground(new Color(238,235,235));
        formPane.setBorder(new EmptyBorder(10,10,10,10));
        formPane.setLayout(new BorderLayout());

        JPanel westPane= new JPanel();
        westPane.setBackground(new Color(238,235,235));
        westPane.setLayout(new GridLayout(2,1));
        westPane.setBorder(new EmptyBorder(120,0,120,40));
        westPane.add(lbl_login);
        westPane.add(lpl_pass);

        JPanel centerPane= new JPanel();
        centerPane.setBackground(new Color(238,235,235));
        centerPane.setLayout(new GridLayout(2,1));
        centerPane.setBorder(new EmptyBorder(120,0,120,40));
        centerPane.add(txt_login);
        centerPane.add(txt_pass);

        formPane.add(westPane,BorderLayout.WEST);
        formPane.add(centerPane, BorderLayout.CENTER);

//        buttonsPanel
        buttonsPane=new JPanel();
        buttonsPane.setBackground(new Color(49,83,168));
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
