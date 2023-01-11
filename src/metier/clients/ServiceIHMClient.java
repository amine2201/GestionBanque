package metier.clients;

import metier.Verifiable;
import presentation.modele.entitesDeLaBanque.Banque;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.entitesDeLaBanque.Compte;
import presentation.modele.util.ConsoleColors;
import presentation.modele.util.Log;
import presentation.modele.util.TypeLog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static metier.InteractiveConsole.clavier;

public class ServiceIHMClient implements IServiceIHMClient{
    private IServiceClient serviceClient;
    private Banque banque;
    private Client client;
    private static final String RESET = ConsoleColors.RESET.getValeur();
    private static final String RED = ConsoleColors.RED.getValeur();
    private static final String GREEN = ConsoleColors.GREEN.getValeur();

    public ServiceIHMClient(Client client, Banque banque) {
        this.banque = banque;
        serviceClient=new ServiceClient(client,banque);
    }

    @Override
    public int menuGlobal() {
        serviceClient.choisirCompte();
        int choix;
        do{
            System.out.println("------------------------------------------------------");
            System.out.println("| 1. Effectuer un versement");
            System.out.println("| 2. Effectuer un retrait");
            System.out.println("| 3. Effectuer un virement");
            System.out.println("| 4. Modifier votre profil");
            System.out.println("| 5. Afficher info compte");
            System.out.println("| 6. Afficher info client");
            System.out.println("| 7. Changer Compte");
            System.out.println("| 8. Se deconnecter");
            choix=getChoix(1,8);
            switch (choix){
                case 1:serviceClient.versement();break;
                case 2:menuRetrait();break;
                case 3:serviceClient.virement();break;
                case 4:menuModification();break;
                case 5:menuInformations();break;
                case 6:
                    System.out.println(client);break;
                case 7: serviceClient.choisirCompte();break;
            }
            System.out.println("------------------------------------------------------");
            if(choix==8){
                System.out.println("------------------------------------------------------");
                if(client.getSexe().getIndice()==0)
                    System.out.println("| Au revoir Mr "+client.getNomComplet());
                else
                    System.out.println("| Au revoir Mme "+client.getNomComplet());
                System.out.println("------------------------------------------------------");
                break;}
        }while (true);
        return choix;
    }

    @Override
    public int menuModification() {
        int choix;
        do{
            System.out.println("------------------------------------------------------");
            System.out.println("| 1. Modifier votre mot de passe");
            System.out.println("| 2. EModifier votre email");
            System.out.println("| 3. Modifier votre numero de telephone");
            System.out.println("| 4. Retourner au menu principal");
            choix=getChoix(1,4);
            serviceClient.modifierProfile(choix);
            System.out.println("------------------------------------------------------");
            if(choix==4)
                break;
        }while (true);
        return choix;
    }

    @Override
    public int menuRetrait() {
        int choix;
        do{
            System.out.println("------------------------------------------------------");
            System.out.println("| 1. Retrait de 50.0 dh");
            System.out.println("| 2. Retrait de 100.0 dh");
            System.out.println("| 3. Retrait de 200.0 dh");
            System.out.println("| 4. Retrait de 500.0 dh");
            System.out.println("| 5. Retrait d'un autre montant");
            System.out.println("| 6. Retour au menu principal");
            choix = getChoix(1,6);
            if(choix<=4)
                serviceClient.retrait(choix);
            else if(choix==5) serviceClient.retrait();
            System.out.println("------------------------------------------------------");
            if(choix==6) break;
        }while (true);
        return choix;
    }



    @Override
    public int menuInformations() {
        int choix;
        do{
            System.out.println("------------------------------------------------------");
            System.out.println("| 1. Afficher solde");
            System.out.println("| 2. Afficher les 3 dernieres operations");
            System.out.println("| 3. Afficher tous le virements du compte");
            System.out.println("| 4. Afficher tous le retraits du compte");
            System.out.println("| 5. Afficher tous le versements du compte");
            System.out.println("| 6. Retourner au menu principal");
            choix = getChoix(1,6);
            if(choix==6)
                break;
            System.out.println(GREEN);
            switch (choix){
                case 1:
                    System.out.println("| Votre solde : "+serviceClient.afficherSolde()); break;
                case 2: serviceClient.dernièresOpérations(); break;
                case 3: System.out.println(serviceClient.afficherLogDeType(TypeLog.VIREMENT));break;
                case 4: System.out.println(serviceClient.afficherLogDeType(TypeLog.RETRAIT));break;
                case 5: System.out.println(serviceClient.afficherLogDeType(TypeLog.VERSEMENT));break;
            }
            System.out.println(RESET);
            System.out.println("------------------------------------------------------");

        }while (true);
        return choix;
    }

    private int getChoix(int d,int f) {
        int choix;
        System.out.println("------------------------------------------------------");
        do {
            System.out.print("| Votre choix: ");
            String line =clavier.nextLine();
            if(Verifiable.isNumeric(line))
            {
                choix=Integer.parseInt(line);
                if(choix>=d && choix<=f)
                break;
            }
            System.out.println("|"+RED +" Choix invalide"+RESET);
        }while(true);
        System.out.println("------------------------------------------------------");
        return choix;
    }
}
