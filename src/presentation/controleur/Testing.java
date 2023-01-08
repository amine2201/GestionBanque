package presentation.controleur;

import dao.IDao;
import dao.daoFiles.ClientDao;
import metier.clients.ServiceClient;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.util.Sexe;

import java.util.List;

public class Testing {
    public static void main(String[] args) {
        IDao daoClient=new ClientDao();
//        Client client4=new Client("log4","mdp4","nom4","prenom4","mail4","cin4","tel1", Sexe.HOMME);
//        Client client5=new Client("log5","mdp5","nom5","prenom5","mail5","cin5","tel1", Sexe.HOMME);
//        daoClient.save(client4);
//        daoClient.save(client5);
        Client client1=(Client) daoClient.findById(Long.valueOf("1"));
        client1.setPrenom("penom");
        daoClient.deleteById(client1.getId());
        for(Client client : (List<Client>) daoClient.findall()){
            System.out.println(client);
        }
        System.out.println();
    }
}
