package metier.authentification;

import metier.admin.IServiceIHMAdmin;
import presentation.modele.entitesDeLaBanque.Banque;

import static metier.InteractiveConsole.clavier;

public class ServiceAuth implements IAuth, IServiceIHM {
    private Banque banque;
    public ServiceAuth(Banque banque) {
        this.banque=banque;
    }

    @Override
    public void seConnecter() {
        int choix=menuGlobal();
        if(choix==1){

        }
    }

    @Override
    public void SeDéconnecter() {

    }

    @Override
    public int menuGlobal() {
        int choix=clavier.nextInt();
        return 0;
    }
}
