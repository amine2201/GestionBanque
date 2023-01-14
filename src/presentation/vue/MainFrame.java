package presentation.vue;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    Container mainContainer;
    private SideMenuPanel sideMenuPanel;
    private FooterPanel footerPanel;
    private IdentityPanel identityPanel;
    private JPanel centerPanel;

    private void initActions(){
        sideMenuPanel.getButtons().get("Ajouter").addActionListener(e -> {
            System.out.println("Ajouter");
        });
    }
    private void initPanels(){
        sideMenuPanel =new SideMenuPanel(List.of("Ajouter","Modifier","Chercher","Supprimer"),20,10,400,10);
        footerPanel=new FooterPanel(List.of("Ajouter","Modifier","Chercher","Supprimer"),10,400,20,20);
        identityPanel= new IdentityPanel(List.of("Supprimer"),10,10,20,30);
        initActions();
    }

    private void initContainer(){
        initPanels();
        mainContainer = getContentPane();
        mainContainer.setBackground(new Color(34, 40, 49));
        mainContainer.setLayout(new BorderLayout());
        mainContainer.add(sideMenuPanel,BorderLayout.WEST);
        mainContainer.add(footerPanel,BorderLayout.SOUTH);
        mainContainer.add(identityPanel,BorderLayout.NORTH);
        mainContainer.add(centerPanel,BorderLayout.CENTER);
    }
    public MainFrame(String title){
        initContainer();
        setLocation(0,0);
        setTitle(title);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame("bankManager");
    }
}
