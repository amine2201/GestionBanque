package presentation.controleur;

import metier.authentification.ServiceAuthGUI;
import presentation.modele.entitesDeLaBanque.Banque;
import presentation.vue.LoginFrame;

public class Testing {
    public static void main(String[] args) {
        Banque maBanque= SeedData.seedData();
        LoginFrame loginFrame=new LoginFrame("Login",maBanque);
    }
}
