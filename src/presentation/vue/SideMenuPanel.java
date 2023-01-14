package presentation.vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SideMenuPanel extends JPanel {
    private Map<String,JButton> buttons;

    public Map<String, JButton> getButtons() {
        return buttons;
    }

    private void initButtons(List<String> buttonsNames){
        buttons=new HashMap<>();
        buttonsNames.forEach(buttonsName ->{
            JButton btn = new JButton(buttonsName);
            btn.setFont(new Font("Optima",Font.BOLD,17));
            btn.setBackground(new Color(0, 173, 181));
            btn.setForeground(new Color(238, 238, 238));
            btn.setHorizontalAlignment(JButton.CENTER);
            buttons.put(buttonsName,btn);
        });
    }
    public SideMenuPanel(List<String> buttonsNames,int top,int left,int bottom,int right ){
        initButtons(buttonsNames);
        setBorder(new EmptyBorder(top,left,bottom,right));
        setLayout(new GridLayout(buttonsNames.size(),1,10,10));
        setBackground(new Color(55, 65, 79));
        buttons.forEach((name,button)->{this.add(button);});
    }

}
