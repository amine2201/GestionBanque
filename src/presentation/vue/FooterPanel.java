package presentation.vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FooterPanel extends JPanel {
    private Map<String,JButton> buttons;
    public Map<String, JButton> getButtons() {
        return buttons;
    }

    private void initButtons(List<String> buttonsNames){
        buttons=new HashMap<>();
        buttonsNames.forEach(buttonsName ->{
            JButton btn = new JButton(buttonsName);
            btn.setFont(new Font("Optima",Font.BOLD,17));
            btn.setBackground(new Color(55, 65, 79));
            btn.setForeground(new Color(238, 238, 238));
            btn.setHorizontalAlignment(JButton.CENTER);
            buttons.put(buttonsName,btn);
        });
    }
    public FooterPanel(List<String> buttonsNames,int top,int left,int bottom,int right ){
        initButtons(buttonsNames);
        setBorder(new EmptyBorder(top,left,bottom,right));
        setLayout(new GridLayout(1,buttonsNames.size(),20,20));
        setBackground(new Color(0, 173, 181));
        buttons.forEach((name,button)->{this.add(button);});
    }
}
