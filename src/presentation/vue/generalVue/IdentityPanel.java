package presentation.vue.generalVue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdentityPanel extends JPanel {
    private JLabel name;
    private ClassLoader cl = getClass().getClassLoader();
    private void initLabels(String val){
        name=new JLabel(new ImageIcon(cl.getResource("images/icons/profile.png")));
        name.setText(val);
        name.setHorizontalTextPosition(JLabel.LEFT);
        name.setFont(new Font("Optima",Font.BOLD,20));
        name.setBackground(new Color(57, 62, 70));
        name.setForeground(new Color(238, 238, 238));
        name.setHorizontalAlignment(JButton.RIGHT);


    }
    public IdentityPanel(String label,int top,int left,int bottom,int right ){
        initLabels(label);
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        setBorder(new EmptyBorder(top, left,bottom,right));
        setBackground(new Color(0, 173, 181));
        add(name);
    }
}
