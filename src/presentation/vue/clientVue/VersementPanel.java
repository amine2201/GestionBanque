package presentation.vue.clientVue;

import metier.clients.IServiceClientGUI;
import presentation.modele.entitesDeLaBanque.Compte;
import presentation.modele.util.ActionResult;
import presentation.vue.HintTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class VersementPanel extends JPanel {
        private JLabel lbl_titre,lbl_compte,lbl_mnt,err_compte,err_mnt;
        private HintTextField txt_mnt;
        private JComboBox<String> txt_compte;
        private JButton btn_reset,btn_add;
        private IServiceClientGUI serviceClient;
        private ClassLoader cl=getClass().getClassLoader();
        private void initLabels(){
            lbl_compte=setLabel("Num de compte: ", Color.BLACK,17);
            lbl_mnt=setLabel("Montant: ",Color.BLACK,17);

            err_compte=setLabel("",Color.RED,12);
            err_mnt=setLabel("",Color.RED,12);

            lbl_titre=setLabel("Versement",Color.BLACK,25);
        }
        private void initTextFields(){
            txt_compte=new JComboBox<>(serviceClient.comptes().stream().map(Compte::getNumeroCompte).toArray(String[]::new));
            txt_mnt=new HintTextField("Montant");
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
                err_mnt.setText("");
                err_compte.setText("");
            });
            btn_add.addActionListener(e ->
            {
                ActionResult actionResult= serviceClient.versement((String) txt_compte.getSelectedItem(),txt_mnt.getText());
                if(actionResult.isSuccess()) {
                    JOptionPane.showMessageDialog(this, "Versement termine", "Succes", JOptionPane.INFORMATION_MESSAGE);
                    txt_mnt.resetField("Montant");
                    err_mnt.setText("");
                    err_compte.setText("");
                }});
        }
        private void initPanels(){
            initLabels();
            initTextFields();
            initButtons();
            initActions();
            setLayout(new BorderLayout());

            JPanel westPanel=new JPanel();
            westPanel.setBackground(new Color(34, 40, 49));
            westPanel.setLayout(new GridLayout(2,1,5,5));
            westPanel.setBorder(new EmptyBorder(10,10,400,10));
            westPanel.add(lbl_compte);
            westPanel.add(lbl_mnt);


            JPanel centerPanel=new JPanel();
            centerPanel.setBackground(new Color(34, 40, 49));
            centerPanel.setLayout(new GridLayout(2,1,5,5));
            centerPanel.setBorder(new EmptyBorder(10,10,400,10));
            centerPanel.add(txt_compte);
            centerPanel.add(txt_mnt);

            JPanel eastPanel=new JPanel();
            eastPanel.setBackground(new Color(34, 40, 49));
            eastPanel.setLayout(new GridLayout(2,1,5,5));
            eastPanel.setPreferredSize(new Dimension(450,getHeight()));
            eastPanel.setBorder(new EmptyBorder(10,10,400,10));
            eastPanel.add(err_compte);
            eastPanel.add(err_mnt);

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
        public VersementPanel(IServiceClientGUI serviceClient, int top, int left, int bottom, int right){
            this.serviceClient=serviceClient;
            setBackground(new Color(34, 40, 49));
            setBorder(new EmptyBorder(top,left,bottom,right));
            initPanels();
        }
    }

