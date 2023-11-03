package dto;

import java.time.LocalDate;

public class Affectation {
    private Employe employe;
    private Mission mission;
    private LocalDate datedebut;
    private LocalDate datefin;

    public Affectation(Employe employe, Mission mission, LocalDate datedebut, LocalDate datefin) {
        setEmploye(employe);
        setMission(mission);
        setDatedebut(datedebut);
        setDatefin(datefin);
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public LocalDate getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(LocalDate datedebut) {
        this.datedebut = datedebut;
    }

    public LocalDate getDatefin() {
        return datefin;
    }

    public void setDatefin(LocalDate datefin) {
        this.datefin = datefin;
    }

    @Override
    public String toString() {
        return "Affectation{" +
                "employe=" + employe +
                ", mission=" + mission +
                ", datedebut=" + datedebut +
                ", datefin=" + datefin +
                '}';
    }
}
