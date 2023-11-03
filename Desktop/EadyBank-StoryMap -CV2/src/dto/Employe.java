package dto;

import java.time.LocalDate;
import java.util.List;

public class Employe extends Personne {
    private String matricule;
    private LocalDate dateRecrutement;
    private String emailAdresse;
    List<Compte> comptes;
    List<Affectation> affectations;

    List<Operation> operations;


    public Employe(String nom, String prenom, LocalDate dateN, String tel, String adress, String matricule, LocalDate dateRecrutement, String emailAdresse, List<Compte> comptes, List<Affectation> affectations, List<Operation> operations) {
        super(nom, prenom, dateN, tel, adress);
        setMatricule(matricule);
        setDateRecrutement(dateRecrutement);
        setEmailAdresse(emailAdresse);
        setComptes(comptes);
        setAffectations(affectations);
        setOperations(operations);
    }



    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public List<Affectation> getAffectations() {
        return affectations;
    }

    public void setAffectations(List<Affectation> affectations) {
        this.affectations = affectations;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public LocalDate getDateRecrutement() {
        return dateRecrutement;
    }

    public void setDateRecrutement(LocalDate dateRecrutement) {
        this.dateRecrutement = dateRecrutement;
    }

    public String getEmailAdresse() {
        return emailAdresse;
    }

    public void setEmailAdresse(String emailAdresse) {
        this.emailAdresse = emailAdresse;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "matricule='" + getMatricule() + '\'' +
                ", dateRecrutement=" + getDateRecrutement() +
                ", emailAdresse='" + getEmailAdresse() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", dateN=" + getDateN() +
                ", tel='" + getTel() + '\'' +
                ", adress='" + getAdress() + '\'' +
                '}';
    }
}
