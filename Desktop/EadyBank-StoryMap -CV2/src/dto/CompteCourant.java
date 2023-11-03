package dto;

import java.time.LocalDate;
import java.util.List;


public class CompteCourant extends Compte{
    private double decouvert;

    public CompteCourant(String numero, double sold, LocalDate dateCreation, EtatCompte etat, Client client, Employe employe, List<Operation> operations, double decouvert) {
        super(numero, sold, dateCreation, etat, client, employe, operations);
        setDecouvert(decouvert);
    }

    public double getDecouvert() {
        return decouvert;
    }

    public void setDecouvert(double decouvert) {
        this.decouvert = decouvert;
    }

    @Override
    public String toString() {
        return "CompteCourant{" +
                "decouvert=" + decouvert +
                ", numero='" + numero + '\'' +
                ", sold=" + sold +
                ", dateCreation=" + dateCreation +
                ", etat=" + etat +
                ", client=" + client +
                ", employe=" + employe +
                ", operations=" + operations +
                '}';
    }
}
