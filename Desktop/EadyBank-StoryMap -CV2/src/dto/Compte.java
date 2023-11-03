package dto;

import java.time.LocalDate;
import java.util.List;

public abstract class Compte {

    protected String numero;
    protected double sold;
    protected LocalDate dateCreation;
    protected EtatCompte etat;
    protected Client client;
    protected Employe employe;
    List<Operation> operations;


    public Compte(String numero, double sold, LocalDate dateCreation, EtatCompte etat, Client client,Employe employe,List<Operation> operations) {
        setNumero(numero);
        setSold(sold);
        setDateCreation(dateCreation);
        setEtat(etat);
        setClient(client);
        setEmploye(employe);
        setOperations(operations);
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getSold() {
        return sold;
    }

    public void setSold(double sold) {
        this.sold = sold;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public EtatCompte getEtat() {
        return etat;
    }

    public void setEtat(EtatCompte etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "numero='" + numero + '\'' +
                ", sold=" + sold +
                ", dateCreation=" + dateCreation +
                ", etat=" + etat +
                ", client=" + client +
                ", employe=" + employe +
                ", operations=" + operations +
                '}';
    }
}
