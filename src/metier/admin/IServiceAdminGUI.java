package metier.admin;

import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.entitesDeLaBanque.Compte;
import presentation.modele.util.ActionResult;
import presentation.modele.util.Sexe;
import presentation.modele.util.TableauDeBord;

import java.util.List;
import java.util.Map;

public interface IServiceAdminGUI {
    ActionResult nouveauClient(String prenom, String nom, String email, String pass, String passConfirmation, String cin, String tel, String sexe);
    ActionResult nouveauCompteClientExistant(Client client,double solde);
    List<Client> chercherClient(String mot);
    List<Compte> chercherCompte(String mot);
    ActionResult modifierClient(String prenom, String nom, String email, String pass, String passConfirmation, String cin, String tel, String sexe,Client client);
    ActionResult supprimerClient(Long id);
    TableauDeBord calculerEtAfficherStatistiques();

}
