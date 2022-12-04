package presentation.controleur;

import metier.admin.IServiceAdmin;
import metier.admin.ServiceAdmin;
import metier.authentification.IAuth;
import metier.authentification.IServiceIHM;
import metier.authentification.ServiceAuth;
import metier.clients.IServiceClient;
import metier.clients.ServiceClient;
import presentation.modele.entitesDeLaBanque.Banque;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.entitesDeLaBanque.Compte;
import presentation.modele.util.Sexe;
import presentation.modele.util.TypeLog;

import java.time.LocalDateTime;
import java.util.*;

import static metier.InteractiveConsole.clavier;

public class MaBanque {
        public static IAuth loginService;

        public static void main(String[] args) {

                Banque maBanque = new Banque(   "BP",
                                        "Hassan Rabat",
                                        "212535224433",
                                        "bp@banquePop.ma");

                IServiceAdmin serviceAdmin=new ServiceAdmin(maBanque);
                Client client1=new Client("log1","mdp1","nom1","prenom1","mail1","cin1","tel1", Sexe.HOMME);
                Client client2=new Client("log2","mdp2","nom2","prenom2","mail2","cin2","tel1", Sexe.HOMME);
                Client client3=new Client("log3","mdp3","nom3","prenom3","mail3","cin3","tel1", Sexe.HOMME);
                Client client4=new Client("log4","mdp4","nom4","prenom4","mail4","cin4","tel1", Sexe.HOMME);
                Client client5=new Client("log5","mdp5","nom5","prenom5","mail5","cin5","tel1", Sexe.HOMME);
                List<Client> clients=Arrays.asList(client1,client2,client3,client4,client5);
                Compte compte1=new Compte();
                Compte compte2=new Compte();
                Compte compte3=new Compte();
                Compte compte4=new Compte();
                Compte compte5=new Compte();
                Compte compte6=new Compte();
                Compte compte7=new Compte();
                Compte compte8=new Compte();
                Compte compte9=new Compte();
                Compte compte10=new Compte();
                Compte compte11=new Compte();
                Compte compte12=new Compte();
                Compte compte13=new Compte();
                Compte compte14=new Compte();
                Compte compte15=new Compte();
                List<Compte> comptes= Arrays.asList(compte1,compte2,compte3,compte4,compte5,compte6,compte7,compte8,compte9,compte10,compte11,compte12,compte13,compte14,compte15);
                for(int i=0;i<comptes.size();i++){
                        comptes.get(i).setLog(TypeLog.CREATION,"test");
                        comptes.get(i).setSolde(((i+1)*10.0));
                }
                int c=0;
                for(int i=0;i<clients.size();i++){
                        clients.get(i).getComptesClient().addAll(Arrays.asList(comptes.get(0+c),comptes.get(1+c),comptes.get(2+c)));
                        comptes.get(0+c).setPropriétaire(clients.get(i));
                        comptes.get(1+c).setPropriétaire(clients.get(i));
                        comptes.get(2+c).setPropriétaire(clients.get(i));
                        c+=3;
                }
                maBanque.getClientsDeBanque().addAll(clients);
                loginService = new ServiceAuth(maBanque);
                loginService.seConnecter();

                clavier.close();

        }

}
