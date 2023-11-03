package interfeces;

import dto.Employe;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IEmploye extends IPersonne {
    Optional<List<Employe>> SearchByMatricule(String matricule);
    boolean Delete(String marticule);
    Optional<List<Employe>> ShowList();
    Optional<List<Employe>> SearchByDateR(LocalDate dateRecrutement);
    Optional<Employe> Update(Employe employe);

}
