package presentation.controleur;

import metier.InteractiveConsole;
import metier.authentification.IServiceIHM;
import metier.authentification.ServiceIHMConsole;
import presentation.modele.entitesDeLaBanque.Banque;


public class MaBanque {
        public static IServiceIHM loginService;

        public static void main(String[] args) {
                Banque maBanque= SeedData.seedData();
                loginService = new ServiceIHMConsole(maBanque);
                while(loginService.menuGlobal()!=2);
                InteractiveConsole.fermerClavier();
        }

}
