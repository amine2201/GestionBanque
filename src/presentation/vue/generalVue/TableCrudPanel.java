package presentation.vue.generalVue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class TableCrudPanel extends JPanel {

    private ClassLoader cl = getClass().getClassLoader();
    private JButton btn_add, btn_edit, btn_delete,btn_info;



    public JButton getBtn_add() {
        return btn_add;
    }

    public JButton getBtn_edit() {
        return btn_edit;
    }

    public JButton getBtn_delete() {
        return btn_delete;
    }

    public JButton getBtn_info() {
        return btn_info;
    }

    private void initButtons(){

        btn_add = new JButton(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/add.png"))));
        btn_add.setBorderPainted(false);
        btn_add.setBackground(new Color(34, 40, 49));

        btn_edit = new JButton(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/edit.png"))));
        btn_edit.setBorderPainted(false);
        btn_edit.setBackground(new Color(34, 40, 49));


        btn_delete = new JButton(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/delete.png"))));
        btn_delete.setBorderPainted(false);
        btn_delete.setBackground(new Color(34, 40, 49));

        btn_info = new JButton(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/information.png"))));
        btn_info.setBorderPainted(false);
        btn_info.setBackground(new Color(34, 40, 49));
    }
    public void initActions(){
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

        btn_edit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_edit.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/editHover.png"))));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_edit.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/edit.png"))));
            }
        });
        btn_delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_delete.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/deleteHover.png"))));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_delete.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/delete.png"))));
            }
        });
        btn_info.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_info.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/informationHover.png"))));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_info.setIcon(new ImageIcon(Objects.requireNonNull(cl.getResource("images/icons/information.png"))));
            }
        });
    }

    public TableCrudPanel(){

        initButtons();
        initActions();
        setLayout(new FlowLayout());
        setBackground(new Color(34, 40, 49));
        add(btn_add);
        add(btn_edit);
        add(btn_delete);
        add(btn_info);
    }


}
