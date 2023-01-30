package presentation.controleur;

import metier.admin.ServiceAdminGUI;
import metier.authentification.ServiceAuthGUI;
import presentation.modele.entitesDeLaBanque.Banque;
import presentation.vue.LoginFrame;
import presentation.vue.MainFrame;

public class Testing {
    public static void main(String[] args) {
        Banque maBanque= SeedData.seedData();
            new MainFrame("banque",new ServiceAdminGUI(maBanque),null);
    }
}
