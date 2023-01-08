package dao.daoFiles;

import dao.IDao;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.util.Sexe;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ClientDao implements IDao<Client,Long> {
    public  static  final Path Clients_Tab = Paths.get("FileBase/clients.txt");
    public static final Path IDS = Paths.get("FileBase/compteurs.txt");
    public Long id;
    public ClientDao(){
        id=getId();
        Client.setCompteur(id+1);
    }

    @Override
    public List<Client> findall() {
        List<Client> clientList=new ArrayList<Client>();
        try {
            List<String> clients=Files.readAllLines(Clients_Tab, StandardCharsets.UTF_8);
            clients.remove(0);
//            structure in the txt file: id,nom,prenom,login,password,cin,tel,email,sexe
//            constructor : Long id,String login, String pass, String n, String p, String mail, String cin, String tel, Sexe sexe
            if(clients.size()!=0){
                for(String line : clients ){
                    String[] fields=line.split(",");
                    Client client = new Client(Long.parseLong(fields[0]),fields[3],fields[4],fields[1],fields[2],fields[7],fields[5],fields[6],Sexe.valueOf(fields[8]));
                    clientList.add(client);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return clientList;
    }

    @Override
    public Client findById(Long id) {
        Client client = null;
        List<Client> clients=findall();
        for(Client client1: clients){
            if(client1.getId()==id){
                client=client1;
                break;
            }
        }

        return client;
    }

    @Override
    public Client save(Client client) {
        try {
            String clientStr=clientString(client)+"\n";
            if(client.getId()>id){
                id=client.getId();
                setId(id);
            }
            Files.writeString(Clients_Tab,clientStr, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return client;
    }

    @Override
    public Client update(Client client) {
        List<String> lines;
        try {
            lines=Files.readAllLines(Clients_Tab,StandardCharsets.UTF_8);
            for(int i=1; i<lines.size();i++){
                Long id=Long.valueOf(lines.get(i).substring(0,lines.get(i).indexOf(',')));
                if(client.getId().equals(id)) {
                    lines.set(i, clientString(client));
                    break;
                }
            }
            Files.write(Clients_Tab,lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return client;
    }

    @Override
    public Boolean deleteById(Long id) {
        List<String> lines;
        Boolean deleted=false;
        try {
            lines=Files.readAllLines(Clients_Tab,StandardCharsets.UTF_8);
            for(int i=1; i<lines.size();i++){
                Long idClient=Long.valueOf(lines.get(i).substring(0,lines.get(i).indexOf(',')));
                if(idClient.equals(id)) {
                    lines.remove(i);
                    deleted=true;
                    break;
                }
            }
            Files.write(Clients_Tab,lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return deleted;
    }

    @Override
    public Boolean delete(Client client) {
        List<String> lines;
        Boolean deleted=false;
        try {
            lines=Files.readAllLines(Clients_Tab,StandardCharsets.UTF_8);
            String head=lines.remove(0);
            for(int i=0; i<lines.size()-1;i++){
                Long idClient=Long.valueOf(lines.get(i).substring(0,lines.get(i).indexOf(',')));
                if(idClient.equals(client.getId())) {
                    lines.remove(i);
                    deleted=true;
                    break;
                }
            }
            lines.add(0,head);
            Files.write(Clients_Tab,lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return deleted;
    }
    String clientString(Client client){
        return  client.getId()
                +","+client.getNom()
                +","+client.getPrenom()
                +","+client.getLogin()
                +","+ client.getMotDePasse()
                +","+client.getCin()
                +","+((client.getTel()!=null&&client.getTel().trim().length()!=0)?client.getTel():"null")
                +","+((client.getEmail()!=null&&client.getEmail().trim().length()!=0)?client.getEmail():"null")
                +","+(client.getSexe()!=null?client.getSexe():"null");
    }

    @Override
    public  Long getId() {
        try {
            List<String> lines=Files.readAllLines(IDS,StandardCharsets.UTF_8);
            String[] ids=lines.get(1).split(",");
            if(ids.length<2)
                return Long.valueOf(1);
            Long id= Long.valueOf(ids[1]);
                return id;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public  void setId(Long id) {
        try {
            List<String> lines=Files.readAllLines(IDS,StandardCharsets.UTF_8);
            String[] ids=lines.get(1).split(",");
            Long oldId= Long.valueOf(ids[1]);
            lines.set(1,ids[0]+","+id+","+ids[2]);
            Files.write(IDS,lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
