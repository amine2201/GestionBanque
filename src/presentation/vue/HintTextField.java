package presentation.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class HintTextField extends JTextField {


    Font gainFont = new Font("Optima", Font.BOLD, 18);
    Font lostFont = new Font("Candara", Font.ITALIC, 17);
    Color lostColor =new Color(60, 42, 33);
    Color gainColor=new Color(0,0,0);
    public void resetField(String hint){
        setText(hint);
        setFont(lostFont);
        setForeground(lostColor);
        setHorizontalAlignment(JTextField.CENTER);
    }

    public HintTextField(final String hint) {

        setText(hint);
        setFont(lostFont);
        setForeground(lostColor);
        setHorizontalAlignment(JTextField.CENTER);


        this.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(hint)) {
                    setText("");
                    setFont(gainFont);
                    setForeground(gainColor);
                } else {
                    setText(getText());
                    setFont(gainFont);
                    setForeground(gainColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(isEditable())
                    if (getText().equals(hint)|| getText().length()==0) {
                        setText(hint);
                        setFont(lostFont);
                        //setForeground(Color.GRAY);
                        setForeground(lostColor);
                    } else {
                        setText(getText());
                        setFont(gainFont);
                        setForeground(lostColor);
                    }
            }
        });

    }

    public HintTextField(final String hint, Color gainColor, Color lostColor) {

        this(hint);
        this.gainColor=gainColor;
        this.lostColor=lostColor;

    }

    @Override
    public void setText(String t) {
        super.setText(t);
        setFont(gainFont);
        setForeground(gainColor);
    }

    public HintTextField(final String hint, Font f, Color textColor, Color backColor, boolean opaque) {

        setText(hint);
        setFont(f);
        setForeground(textColor);
        setBackground(backColor);
        setOpaque(opaque);

        this.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(hint)) {
                    setText("");
                    setFont(f);
                } else {
                    setText(getText());
                    setFont(f);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().equals(hint)|| getText().length()==0) {
                    setText(hint);
                    setFont(lostFont);
                    //setForeground(Color.GRAY);
                    setForeground(textColor);
                } else {
                    setText(getText());
                    setFont(f);
                    setForeground(Color.blue);
                }
            }
        });

    }

}
