package interfeces;

import dto.Client;

import java.util.List;
import java.util.Optional;

public interface IClient extends IPersonne {
    Optional<List<Client>> SearchByCode(String code);
    boolean Delete(String code);
    Optional<List<Client>> Showlist() ;
    Optional<List<Client>> SearchByLastName(String prenom);
    Optional<Client> Update(Client client);
}
