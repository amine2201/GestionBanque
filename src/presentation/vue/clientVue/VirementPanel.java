package presentation.vue.clientVue;

import metier.admin.IServiceAdminGUI;
import metier.clients.IServiceClientGUI;
import presentation.modele.util.ActionResult;
import presentation.vue.HintTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class VirementPanel extends JPanel {
    private JLabel lbl_titre,lbl_compte,lbl_mnt,lbl_ben,err_compte,err_mnt,err_ben;
    private HintTextField txt_mnt,txt_ben;
    private JComboBox<String> txt_compte;
    private JButton btn_reset,btn_add;
    private IServiceClientGUI serviceClient;
    private ClassLoader cl=getClass().getClassLoader();
    private void initLabels(){
        lbl_compte=setLabel("Num de compte: ",Color.BLACK,17);
        lbl_mnt=setLabel("Montant: ",Color.BLACK,17);
        lbl_ben=setLabel("Bénéficiaire",Color.BLACK,17);

        err_compte=setLabel("",Color.RED,12);
        err_mnt=setLabel("",Color.RED,12);
        err_mnt=setLabel("",Color.RED,12);

        lbl_titre=setLabel("Retrait",Color.BLACK,25);
    }
    private void initTextFields(){
        txt_compte=new JComboBox<>();
        txt_compte.addItem("sss");
        txt_compte.addItem("ddd");
        txt_mnt=new HintTextField("Montant");
        txt_ben=new HintTextField("Bénéficiaire");
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

        btn_reset.addActionListener(e -> {
            txt_mnt.resetField("Montant");
            txt_ben.resetField("Bénéficiaire");
            err_mnt.setText("");
            err_compte.setText("");
        });
        btn_add.addActionListener(e -> {
            ActionResult actionResult= serviceClient.virement((String) txt_compte.getSelectedItem(),(String) txt_ben.getText(),txt_mnt.getText());
            if(actionResult.isSuccess()){
                JOptionPane.showMessageDialog(this,"Virement termine","Succes",JOptionPane.INFORMATION_MESSAGE);
                txt_mnt.resetField("Montant");
                txt_ben.resetField("Bénéficiaire");
                err_mnt.setText("");
                err_compte.setText("");
            }
            else {
                Map<String,String> errs=actionResult.getErrorMessage();
                for(String key : errs.keySet()){
                    if(key.equals("compte"))
                        err_compte.setText(errs.get(key));
                    if(key.equals("ben"))
                        err_compte.setText(errs.get(key));
                    if(key.equals("solde"))
                        err_mnt.setText(errs.get(key));
                }
            }
        });
    }
    private void initPanels(){
        initLabels();
        initTextFields();
        initButtons();
        initActions();
        setLayout(new BorderLayout());

        JPanel westPanel=new JPanel();
        westPanel.setBackground(new Color(34, 40, 49));
        westPanel.setLayout(new GridLayout(3,1,5,5));
        westPanel.setBorder(new EmptyBorder(10,10,400,10));
        westPanel.add(lbl_compte);
        westPanel.add(lbl_mnt);
        westPanel.add(lbl_ben);


        JPanel centerPanel=new JPanel();
        centerPanel.setBackground(new Color(34, 40, 49));
        centerPanel.setLayout(new GridLayout(2,1,5,5));
        centerPanel.setBorder(new EmptyBorder(10,10,400,10));
        centerPanel.add(txt_compte);
        centerPanel.add(txt_mnt);
        centerPanel.add(txt_ben);

        JPanel eastPanel=new JPanel();
        eastPanel.setBackground(new Color(34, 40, 49));
        eastPanel.setLayout(new GridLayout(2,1,5,5));
        eastPanel.setPreferredSize(new Dimension(450,getHeight()));
        eastPanel.setBorder(new EmptyBorder(10,10,400,10));
        eastPanel.add(err_compte);
        eastPanel.add(err_mnt);
        eastPanel.add(err_ben);

        JPanel southPanel=new JPanel();
        southPanel.setBackground(new Color(34, 40, 49));
        southPanel.setLayout(new GridLayout(1,2,5,5));
        southPanel.add(btn_add);
        southPanel.add(btn_reset);

        add(lbl_titre,BorderLayout.NORTH);
        add(westPanel,BorderLayout.WEST);
        add(centerPanel,BorderLayout.CENTER);
        add(eastPanel,BorderLayout.EAST);
        add(southPanel,BorderLayout.SOUTH);


    }
    private JLabel setLabel(String txt, Color color, int size){
        JLabel lbl=new JLabel(txt);
        lbl.setFont(new Font("Optima",Font.BOLD,size));
        lbl.setForeground(color);
        lbl.setHorizontalAlignment(JLabel.CENTER);
        return lbl;
    }
    public VirementPanel(IServiceClientGUI serviceClient, int top, int left, int bottom, int right){
        this.serviceClient=serviceClient;
        setBackground(new Color(34, 40, 49));
        setBorder(new EmptyBorder(top,left,bottom,right));
        initPanels();
    }
}
