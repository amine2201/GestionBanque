package presentation.controleur;

import dao.daoFiles.ClientDao;
import metier.admin.ServiceAdminGUI;
import metier.authentification.ServiceAuthGUI;
import metier.clients.ServiceClientGUI;
import presentation.modele.entitesDeLaBanque.Banque;
import presentation.modele.entitesDeLaBanque.Compte;
import presentation.vue.LoginFrame;
import presentation.vue.MainFrame;

public class Testing {
    public static void main(String[] args) {
        Banque maBanque= SeedData.seedData();
//        new LoginFrame("Login",maBanque);
            new MainFrame("banque",null,new ServiceClientGUI(new ClientDao(),maBanque.getClientsDeBanque().get(0)));
//        new MainFrame("banque",new ServiceAdminGUI(maBanque),null);

    }
}
