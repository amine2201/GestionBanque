package presentation.vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Frame extends JFrame {
    private Container mainContainer;
    private JPanel titlePane, buttonsPane, formPane;
    private List<JLabel> titleLabels=new ArrayList<>();
    private List<JLabel> westLabels=new ArrayList<>();
    private List<JTextField> centerTextField=new ArrayList<>();
    private List<JButton> centerButtons=new ArrayList<>();
    private List<JButton> footerButtons=new ArrayList<>();
    private Map<String,List<String>> texts;
    private int heightUpDown;
    public void initLabels(){
        initTitleLabels();
        initWestLabels();
    }

    private void initWestLabels() {
        if(texts.containsKey("westLabels"))
            for (String title : texts.get("westLabels")){
            JLabel label=new JLabel(title);
            label.setFont(new Font("Optima",Font.BOLD,17));
            label.setForeground(Color.BLUE);
            label.setHorizontalAlignment(JLabel.CENTER);
            westLabels.add(label);
        }
    }

    private void initTitleLabels() {
        if(texts.containsKey("titleLabels")){
            JLabel label= new JLabel(texts.get("titleLabels").get(0));
            label.setFont(new Font("Optima",Font.BOLD,30));
            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(JLabel.CENTER);
            titleLabels.add(label);
        }
    }

    public void initCenterTextFields(){
        if(texts.containsKey("centerTextFields"))
            for(String title : texts.get("centerTextFields")){
            if(title.toLowerCase().contains("password")){
                JPasswordField passwordField=new JPasswordField("");
                passwordField.setFont(new Font("Optima",Font.BOLD,17));
                passwordField.setForeground(Color.yellow);
                passwordField.setHorizontalAlignment(JPasswordField.CENTER);
                centerTextField.add(passwordField);
            }
            else{
                JTextField textField= new JTextField("");
                textField.setFont(new Font("Optima",Font.BOLD,17));
                textField.setForeground(Color.yellow);
                textField.setHorizontalAlignment(JTextField.CENTER);
                centerTextField.add(textField);
            }
        }
    }
    public void initButtons(){
        initCenterButtons();
        initFooterButtons();
    }

    private void initFooterButtons() {
        if(texts.containsKey("footerButtons"))
            for(String title : texts.get("footerButtons")){
            JButton button= new JButton(title);
            button.setFont(new Font("Optima",Font.BOLD,17));
            button.setForeground(Color.BLACK);
            button.setHorizontalAlignment(JLabel.CENTER);
            footerButtons.add(button);
        }
    }

    private void initCenterButtons() {
        if(texts.containsKey("centerButtons"))
            for(String title : texts.get("centerButtons")){
            JButton button= new JButton(title);
            button.setFont(new Font("Optima",Font.BOLD,17));
            button.setForeground(Color.BLACK);
            button.setHorizontalAlignment(JLabel.CENTER);
            centerButtons.add(button);
        }
    }

    public void initPanels(){
        initCenterTextFields();
        initButtons();
        initLabels();
//        ______________________________________________________________________________________________________________
        titlePane=new JPanel();
        titlePane.setBackground(new Color(49,83,168));
        titlePane.setBorder(new EmptyBorder(0,10,0,10));
        for(JLabel label : titleLabels){
            titlePane.add(label);
        }
//        ______________________________________________________________________________________________________________
        formPane=new JPanel();
        formPane.setBackground(new Color(238,235,235));
        formPane.setBorder(new EmptyBorder(10,20,10,10));
        formPane.setLayout(new BorderLayout());
//        ______________________________________________________________________________________________________________
        JPanel westPane= new JPanel();
        westPane.setBackground(new Color(238,235,235));
        westPane.setLayout(new GridLayout(westLabels.size(),1));
        westPane.setBorder(new EmptyBorder(heightUpDown,30,heightUpDown,10));
        for(JLabel label : westLabels){
            westPane.add(label);
        }
//        ______________________________________________________________________________________________________________
        JPanel centerPane= new JPanel();
        centerPane.setBackground(new Color(238,235,235));
        centerPane.setLayout(new GridLayout(westLabels.size(),1));
        centerPane.setBorder(new EmptyBorder(heightUpDown,10,heightUpDown,10));
        for(JTextField textField : centerTextField){
            centerPane.add(textField);
        }
        for(JButton button : centerButtons){
            centerPane.add(button);
        }
        formPane.add(westPane,BorderLayout.WEST);
        formPane.add(centerPane, BorderLayout.CENTER);
//        ______________________________________________________________________________________________________________
        buttonsPane =new JPanel();
        buttonsPane.setBackground(new Color(49,83,168));
        buttonsPane.setBorder(new EmptyBorder(10,20,10,10));
        buttonsPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        for(JButton button : footerButtons){
            buttonsPane.add(button);
        }
    }
    public void initContainer(){
        initPanels();
        mainContainer=this.getContentPane();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.add(titlePane,BorderLayout.NORTH);
        mainContainer.add(formPane,BorderLayout.CENTER);
        mainContainer.add(buttonsPane,BorderLayout.SOUTH);
    }

    public Frame(String title,int width,int height,Map<String,List<String>> texts) throws HeadlessException {
        this.texts=texts;
        heightUpDown=(height-151-texts.get("westLabels").size()*54)/2;
        System.out.println(heightUpDown);
        initContainer();
        setTitle(title);
        setSize(width,height);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setVisible(true);
        System.out.println(titlePane.getHeight());
        System.out.println(formPane.getHeight());
        System.out.println(buttonsPane.getHeight());
        System.out.println(mainContainer.getHeight());
    }

}
