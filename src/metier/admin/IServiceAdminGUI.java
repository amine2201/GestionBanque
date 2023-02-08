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
    ActionResult nouveauCompteClientExistant(String idClient,String solde);
    List<Client> getClients();
    List<Client> chercherClient(String mot);
    Client          chercherClientParId(Long id);
    List<Compte> getComptes();
    List<Compte> chercherCompte(String mot);
    Compte          chercherCompteParNum(String numCompte);
    ActionResult modifierClient(String prenom, String nom, String email, String pass, String passConfirmation, String cin, String tel, String sexe,Client client);
    ActionResult supprimerClient(Long id);
    ActionResult supprimerCompte(String numCompte);
    TableauDeBord calculerEtAfficherStatistiques();


}
