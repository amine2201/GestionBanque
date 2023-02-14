package dao.daoFiles;

import dao.IDao;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.entitesDeLaBanque.Compte;
import presentation.modele.util.Log;
import presentation.modele.util.TypeLog;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompteDao implements IDao<Compte,String> {
    public static final Path Comptes_Tab = Paths.get("FileBase/comptes.txt");
    public static final Path IDS = Paths.get("FileBase/compteurs.txt");
    public Long id;
    public final Client client;
    public CompteDao(Client client){
        this.client=client;
        id=getId();
        if(Compte.getCompteur()<=id)
            Compte.setCompteur(id+1);
    }

    @Override
    public List<Compte> findAll() {
        List<Compte> comptes=new ArrayList<Compte>();
        try {
            List<String> lines= Files.readAllLines(Comptes_Tab, StandardCharsets.UTF_8);
            lines.remove(0);
            List<String> linesOfClient=lines.stream()
                    .filter(line -> client.getId()
                            .equals(Long.parseLong(line.substring(line.lastIndexOf(",")+1))))
                    .toList();
            for(String line : linesOfClient){
//                id,dateCreation,solde,idClient
//                String numeroCompte, Double solde, LocalDateTime dateCreation, Client propriétaire, List< Log > logs
                String[] fields=line.split(",");
                Compte compte=new Compte(fields[0],Double.valueOf(fields[2]),LocalDateTime.parse(fields[1]),client,getCompteLogs(fields[0]));
                comptes.add(compte);
            }
            return comptes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Compte findById(String numCompte) {
        try {
            Compte compte=null;
            List<String> lines= Files.readAllLines(Comptes_Tab, StandardCharsets.UTF_8);
            lines.remove(0);
            String compteString=lines.stream()
                    .filter(line -> numCompte.equals(line.substring(0,line.indexOf(","))))
                    .findFirst().orElse(null);
            if (compteString != null) {
                String[] fields = compteString.split(",");
                compte=new Compte(fields[0],Double.valueOf(fields[2]),LocalDateTime.parse(fields[1]),client,getCompteLogs(fields[0]));
            }
            return compte;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Compte save(Compte compte) {
        //                id,dateCreation,solde,idClient
        //                String numeroCompte, Double solde, LocalDateTime dateCreation, Client propriétaire, List< Log > logs
        try {
            Long idCompte=Long.valueOf(compte.getNumeroCompte().substring(5));
                if(idCompte>id){
                    id=idCompte;
                    setId(idCompte);
                }
            Files.writeString(Comptes_Tab,compteString(compte)+"\n",StandardOpenOption.APPEND);
            List<Log> logs=getCompteLogs(compte.getNumeroCompte());
            if(!logs.equals(compte.getLogs())){
                setCompteLogs(compte.getNumeroCompte(),compte.getLogs().toArray(new Log[0]));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return compte;
    }

    @Override
    public Compte update(Compte compte) {
        try {
            List<String> lines=Files.readAllLines(Comptes_Tab,StandardCharsets.UTF_8);
            for(int i=1; i<lines.size();i++){
                String numCompte=lines.get(i).substring(0,lines.get(i).indexOf(','));
                if(compte.getNumeroCompte().equals(numCompte)){
                    lines.set(i,compteString(compte));
                    setCompteLogs(numCompte,compte.getLogs().toArray(new Log[0]));
                    break;
                }
            }
            Files.write(Comptes_Tab,lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return compte;
    }

    @Override
    public Boolean deleteById(String numCompte) {
        boolean deleted=false;
        try {
            List<String> lines=Files.readAllLines(Comptes_Tab,StandardCharsets.UTF_8);
            for(int i=1; i<lines.size();i++){
                String num_Compte=lines.get(i).substring(0,lines.get(i).indexOf(','));
                if(numCompte.equals(num_Compte)){
                    lines.remove(i);
                    Files.delete(Paths.get("FileBase/archives/logs_"+numCompte));
                    deleted=true;
                    break;
                }
            }
            Files.write(Comptes_Tab,lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return deleted;
    }

    @Override
    public Boolean delete(Compte compte) {
        boolean deleted=false;
        try {
            List<String> lines=Files.readAllLines(Comptes_Tab,StandardCharsets.UTF_8);
            for(int i=1; i<lines.size();i++){
                String numCompte=lines.get(i).substring(0,lines.get(i).indexOf(','));
                if(compte.getNumeroCompte().equals(numCompte)){
                    lines.remove(i);
                    Files.delete(Paths.get("FileBase/archives/logs_"+numCompte));
                    deleted=true;
                    break;
                }
            }
            Files.write(Comptes_Tab,lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return deleted;
    }

    @Override
    public Long getId() {
        try {
            List<String> lines=Files.readAllLines(IDS,StandardCharsets.UTF_8);
            String[] ids=lines.get(1).split(",");
            if(ids.length<3)
                return Long.valueOf("1");
            Long id= Long.valueOf(ids[2]);
            return id;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setId(Long id) {
        try {
            List<String> lines=Files.readAllLines(IDS,StandardCharsets.UTF_8);
            String[] ids=lines.get(1).split(",");
            lines.set(1,ids[0]+","+ids[1]+","+id);
            Files.write(IDS,lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Log> getCompteLogs(String numCompte){
        List<Log> logs=new ArrayList<Log>();
        try {
            Path path = Paths.get("FileBase/archives/logs_"+numCompte);
            if(!Files.exists(path))
                return logs;
            List<String> lines=Files.readAllLines(path,StandardCharsets.UTF_8);
            lines.remove(0);
            for(String line : lines){
//                LocalDate date, LocalTime time, TypeLog type, String msg
                  String[] fields = line.split(",");
                  Log log=new Log(LocalDate.parse(fields[0]), fields[1].equals("null")?null:LocalTime.parse(fields[1]),fields[2].equals("null")?null:TypeLog.valueOf(fields[2]),fields[3]);
                  logs.add(log);
            }
            return logs;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setCompteLogs(String numCompte,Log... log){
        try {
            Path path = Paths.get("FileBase/archives/logs_"+numCompte);
            if(log.length>1)
                Files.delete(path);
            if(!Files.exists(path)){
                Files.writeString(path,"date,time,type,msg\n");
            }
//                LocalDate date, LocalTime time, TypeLog type, String msg
            for(int i=0;i<log.length;i++) {
                String logString = log[i].getDate() + "," + log[i].getTime() + "," + log[i].getType() + "," + log[i].getMessage() + "\n";
                Files.writeString(path, logString, StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Compte> findByKeyWord(String keyword) {
        List<Compte> comptes=findAll();
        return  comptes.stream().filter(
                c-> c.getNumeroCompte().equals(keyword) ||
                        c.getDateCreation().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).contains(keyword) ||
                        c.getSolde().toString().equals(keyword) ||
                        c.getPropriétaire().getId().toString().equals(keyword)
                        || c.getPropriétaire().getNomComplet().contains(keyword)

        ).collect(Collectors.toList());
    }

    private String compteString(Compte compte){
//                id,dateCreation,solde,idClient
        return compte.getNumeroCompte()+","+compte.getDateCreation()+","+compte.getSolde()+","+client.getId();
    }

}
