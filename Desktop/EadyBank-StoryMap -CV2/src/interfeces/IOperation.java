package interfeces;

import dto.Operation;

import java.util.List;
import java.util.Optional;

public interface IOperation {
    Optional<Operation> Add(Operation operation);
    Optional<List<Operation>> SearchByNumber(String numero);
    boolean Delete(String numero);
}
