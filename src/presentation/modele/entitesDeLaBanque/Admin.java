package presentation.modele.entitesDeLaBanque;

public class Admin  extends Utilisateur {

    private static  Admin ADMIN = new Admin();

    private Admin(){

        login       = "admin";
        motDePasse  = "12345";
        role        = "Admin";
        nom         =  "Jaoui";
        prenom      = "Amine";

    }


    public static Admin getInstance(){

        return ADMIN;
    }

}
