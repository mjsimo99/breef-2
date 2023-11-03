package interfeces;

import dto.Client;
import dto.Compte;
import dto.EtatCompte;
import dto.Operation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ICompte {
    Optional<Compte> Add(Compte compte);
    List<Compte> SearchByClient(Client client);
    boolean Delete(String numero);
    Optional<Compte> UpdateStatus(Compte compte);
    List<Compte> FilterByStatus(EtatCompte etat);
    List<Compte> ShowList();
    List<Compte> FilterByDCreation(LocalDate dateCreation);
    Optional<Compte> Update(Compte compte);
    List<Compte> SearchByOperation(Operation operation);





}
//gerniriques