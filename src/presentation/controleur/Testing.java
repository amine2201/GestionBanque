package presentation.controleur;

import metier.authentification.ServiceAuthGUI;
import presentation.modele.entitesDeLaBanque.Banque;

public class Testing {
    public static void main(String[] args) {
        Banque maBanque= SeedData.seedData();
        ServiceAuthGUI serviceAuthGUI=new ServiceAuthGUI(maBanque);
        serviceAuthGUI.seConnecter();
    }
}
