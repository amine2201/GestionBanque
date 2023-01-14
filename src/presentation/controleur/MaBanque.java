package presentation.controleur;

import metier.InteractiveConsole;
import metier.admin.IServiceAdmin;
import metier.admin.ServiceAdmin;
import metier.authentification.IAuth;
import metier.authentification.IServiceIHM;
import metier.authentification.ServiceAuth;
import metier.authentification.ServiceIHM;
import presentation.modele.entitesDeLaBanque.Admin;
import presentation.modele.entitesDeLaBanque.Banque;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.entitesDeLaBanque.Compte;
import presentation.modele.util.Sexe;
import presentation.modele.util.TypeLog;

import java.util.*;


public class MaBanque {
        public static IServiceIHM loginService;

        public static void main(String[] args) {
                Banque maBanque= SeedData.seedData();
                loginService = new ServiceIHM(maBanque);
                while(loginService.menuGlobal()!=2);
                InteractiveConsole.fermerClavier();
        }

}
