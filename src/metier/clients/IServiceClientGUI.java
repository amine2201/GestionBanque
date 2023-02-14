package metier.clients;

import presentation.modele.entitesDeLaBanque.Compte;
import presentation.modele.util.ActionResult;
import presentation.modele.util.Log;
import presentation.modele.util.TypeLog;

import java.util.List;

public interface IServiceClientGUI {
    ActionResult versement(String compte,String solde);
    ActionResult retrait  (String compte,String solde);
    ActionResult virement (String compte,String ben,String solde);
    ActionResult modifierProfile(String email,String mdp,String mdpc,String tel);
    List<Log> getLogs(String compte);
    List<Compte> comptes();

}
