package presentation.vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdentityPanel extends JPanel {
    private Map<String,JLabel> labels;

    public Map<String, JLabel> getLabels() {
        return labels;
    }

    private void initLabels(List<String> labelsNames){
        labels=new HashMap<>();
        labelsNames.forEach(labelName ->{
            JLabel lbl = new JLabel(labelName);
            lbl.setFont(new Font("Optima",Font.BOLD,17));
            lbl.setBackground(new Color(57, 62, 70));
            lbl.setForeground(new Color(238, 238, 238));
            lbl.setHorizontalAlignment(JButton.RIGHT);
            labels.put(labelName,lbl);
        });
    }
    public IdentityPanel(List<String> buttonsNames,int top,int left,int bottom,int right ){
        initLabels(buttonsNames);
        setBorder(new EmptyBorder(top,left,bottom,right));
        setLayout(new GridLayout(1,buttonsNames.size(),5,5));
        setBackground(new Color(0, 173, 181));
        labels.forEach((name,label)->{this.add(label);});
    }
}
