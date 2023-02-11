package presentation.vue;

import metier.admin.IServiceAdminGUI;
import metier.clients.IServiceClientGUI;
import metier.clients.ServiceClientGUI;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.vue.adminVue.clientVue.ClientCreationPanel;

import presentation.vue.adminVue.clientVue.ClientModificationPanel;
import presentation.vue.clientVue.RetraitPanel;
import presentation.vue.clientVue.VersementPanel;
import presentation.vue.clientVue.VirementPanel;
import presentation.vue.generalVue.IdentityPanel;
import presentation.vue.generalVue.SideMenuPanel;
import presentation.vue.generalVue.StatistiquesPanel;
import presentation.vue.generalVue.TablePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainFrame extends JFrame {
    Container mainContainer;
    private SideMenuPanel sideMenuPanel;
//    private FooterPanel footerPanel;
    private IdentityPanel identityPanel;
    private JPanel centerPanel;
    private final IServiceAdminGUI serviceAdmin;
    private IServiceClientGUI serviceClient;
    private final List<String> adminActions= List.of("DashBoard","Client","Compte");
    private final List<String> clientActions=List.of("Virer","Retirer","Verser","Supprimer");;
    private int _switch;
    private void initAdminActions(){
        if(centerPanel instanceof TablePanel tablePanel){
            tablePanel.getBtn_add().addActionListener(e->{
                if(_switch==1)
                    redirect(new ClientCreationPanel(serviceAdmin,10,10,10,10,Client.getCompteur()));

            });
            tablePanel.getBtn_edit().addActionListener(e->{
                Object id=tablePanel.getSelectedID();
                if(id instanceof Integer i && i==-1){
                    String message=_switch==1?"Veuillez choisir un client d'abord !!!":"Veuillez choisir un compte d'abord !!!";
                    JOptionPane.showMessageDialog(this,
                            message,
                            "A L E R T",
                            JOptionPane.ERROR_MESSAGE);
                }
                else{
                    if(_switch==1){
                        Long idClient=(long)id;
                        Client client=serviceAdmin.chercherClientParId(idClient);
                        if(client!=null)
                            redirect(new ClientModificationPanel(serviceAdmin,10,10,10,10,client));
                    }
                    else{
                        String numClient=String.valueOf(id);
                        System.out.println(numClient);
                    }
                }
            });
        }
        Map<String,JButton> buttonMap=sideMenuPanel.getButtons();
            for(String label : buttonMap.keySet()){
                if(label.equals("DashBoard"))
                    buttonMap.get(label).addActionListener(e->{
                        JPanel panel=new StatistiquesPanel(serviceAdmin.calculerEtAfficherStatistiques(),10,10,10,10);
                        redirect(panel);
                    });
                if(label.equals("Client"))
                    buttonMap.get(label).addActionListener(e->{
                        JPanel panel=new TablePanel(1,serviceAdmin);
                        redirect(panel);
                    });
                if(label.equals("Compte"))
                    buttonMap.get(label).addActionListener(e->{
                        JPanel panel=new TablePanel(2,serviceAdmin);
                        redirect(panel);
                    });
            }
    }
    private void initClientActions(){
        Map<String,JButton> buttonMap=sideMenuPanel.getButtons();
        for(String label : buttonMap.keySet()){
            if(label.equals("Virer"))
                buttonMap.get(label).addActionListener(e->{
                    JPanel panel=new VirementPanel(serviceClient,10,10,10,10);
                    redirect(panel);
                });
            if(label.equals("Retirer"))
                buttonMap.get(label).addActionListener(e->{
                    JPanel panel=new RetraitPanel(serviceClient,10,10,10,10);
                    redirect(panel);
                });
            if(label.equals("Verser"))
                buttonMap.get(label).addActionListener(e->{
                    JPanel panel=new VersementPanel(serviceClient,10,10,10,10);
                    redirect(panel);
                });
        }
    }
    private void initAdminPanel(){
        sideMenuPanel =new SideMenuPanel(adminActions,20,10,400,10);
//        footerPanel=new FooterPanel(List.of("Ajouter","Annuler"),10,400,20,20);
        identityPanel= new IdentityPanel(new ArrayList<>(),10,10,20,30);
        centerPanel= new StatistiquesPanel(serviceAdmin.calculerEtAfficherStatistiques(),10,10,10,10);
        initAdminActions();
    }
    private void initClientPanel(){
        sideMenuPanel =new SideMenuPanel(clientActions,20,10,400,10);
        identityPanel= new IdentityPanel(new ArrayList<>(),10,10,20,30);
        centerPanel=new RetraitPanel(serviceClient,10,10,10,10);
        initClientActions();
    }
    private void initPanels(){
        if(serviceAdmin!=null)
            initAdminPanel();
        else initClientPanel();
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
    private void redirect(JPanel panel){
        mainContainer.remove(centerPanel);
        centerPanel=panel;
        mainContainer.add(centerPanel,BorderLayout.CENTER);
        mainContainer.revalidate();
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
