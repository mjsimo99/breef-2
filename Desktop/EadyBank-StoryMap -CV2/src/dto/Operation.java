package dto;

import java.time.LocalDate;

public class Operation {
    private String numero;
    private LocalDate dateCreation;
    private Double montant;
    private TypeOperation type;
    private Employe employe;
    private Compte compte;

    public Operation(String numero, LocalDate dateCreation, Double montant, TypeOperation type,Employe employe,Compte compte) {

        setNumero(numero);
        setDateCreation(dateCreation);
        setMontant(montant);
        setType(type);
        setEmploye(employe);
        setCompte(compte);
    }



    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public TypeOperation getType() {
        return type;
    }

    public void setType(TypeOperation type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "numero='" + numero + '\'' +
                ", dateCreation=" + dateCreation +
                ", montant=" + montant +
                ", type=" + type +
                ", employe=" + employe +
                ", compte=" + compte +
                '}';
    }
}
