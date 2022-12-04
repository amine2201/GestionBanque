package metier.clients;

import presentation.modele.entitesDeLaBanque.Compte;
import presentation.modele.util.Log;
import presentation.modele.util.TypeLog;

import java.util.List;

public interface IServiceClient {

        boolean versement();
        boolean retrait  ();

        boolean retrait  (int choixRetrait);
        boolean virement ();
        boolean modifierProfile(int choixModification);
        void dernièresOpérations();
        Double afficherSolde();
        Compte choisirCompte();
        List<Log> afficherLogDeType(TypeLog typeLog);
        void afficherTicket();


}
