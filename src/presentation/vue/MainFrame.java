package presentation.vue;

import metier.admin.IServiceAdminGUI;
import metier.admin.ServiceAdminGUI;
import metier.clients.IServiceClientGUI;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.util.ActionResult;
import presentation.vue.clientVue.ClientCreationPanel;
import presentation.vue.generalVue.FooterPanel;
import presentation.vue.generalVue.IdentityPanel;
import presentation.vue.generalVue.SideMenuPanel;
import presentation.vue.generalVue.TablePanel;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainFrame extends JFrame {
    Container mainContainer;
    private SideMenuPanel sideMenuPanel;
//    private FooterPanel footerPanel;
    private IdentityPanel identityPanel;
    private JPanel centerPanel;
    private final IServiceAdminGUI serviceAdmin;
    private IServiceClientGUI serviceClient;
    private final List<String> adminActions= List.of("DashBoard","Client","Compte");
    private final List<String> clientActions=List.of("Virer","tirer","Chercher","Supprimer");;

    private void initActions(){

    }
    private void initPanels(){
        if(serviceAdmin!=null)
        sideMenuPanel =new SideMenuPanel(adminActions,20,10,400,10);
        else sideMenuPanel =new SideMenuPanel(clientActions,20,10,400,10);
//        footerPanel=new FooterPanel(List.of("Ajouter","Annuler"),10,400,20,20);
        identityPanel= new IdentityPanel(new ArrayList<>(),10,10,20,30);
        centerPanel= new TablePanel(1);
        initActions();
    }

    private void initContainer(){
        initPanels();
        mainContainer = getContentPane();
        mainContainer.setBackground(new Color(34, 40, 49));
        mainContainer.setLayout(new BorderLayout());
        mainContainer.add(sideMenuPanel,BorderLayout.WEST);
//        mainContainer.add(footerPanel,BorderLayout.SOUTH);
        mainContainer.add(identityPanel,BorderLayout.NORTH);
        mainContainer.add(centerPanel,BorderLayout.CENTER);
    }

    public MainFrame(String title, IServiceAdminGUI serviceAdmin, IServiceClientGUI serviceClient){
        this.serviceAdmin=serviceAdmin;
        this.serviceClient=serviceClient;
        initContainer();
        setLocation(0,0);
        setTitle(title);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


}
