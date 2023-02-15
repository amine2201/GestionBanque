package presentation.vue.generalVue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class IdentityPanel extends JPanel {
    private JLabel name;
    private JButton btn_logout;
    private ClassLoader cl = getClass().getClassLoader();
    private void initButtons(){
        btn_logout = new JButton(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/logout.png"))));
        btn_logout.setBorderPainted(false);
        btn_logout.setBackground(new Color(0, 173, 181));
    }
    private void initActions(){
        btn_logout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_logout.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/logoutHover.png"))));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_logout.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/logout.png"))));
            }
        });
    }
    private void initLabels(String val){
        name=new JLabel(new ImageIcon(cl.getResource("images/icons/profile.png")));
        name.setText(val);
        name.setHorizontalTextPosition(JLabel.RIGHT);
        name.setFont(new Font("Optima",Font.BOLD,20));
        name.setBackground(new Color(57, 62, 70));
        name.setForeground(new Color(238, 238, 238));
        name.setHorizontalAlignment(JButton.RIGHT);


    }

    public JButton getBtn_logout() {
        return btn_logout;
    }

    public IdentityPanel(String label, int top, int left, int bottom, int right ){
        initLabels(label);
        initButtons();
        initActions();
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        setBorder(new EmptyBorder(top, left,bottom,right));
        setBackground(new Color(0, 173, 181));
        add(name);
        add(btn_logout);
    }
}
