package interfeces;

import dto.Mission;

import java.util.List;
import java.util.Optional;

public interface IMission {
    Optional<Mission> Add(Mission mission);
    boolean Delete(String code);
    List<Mission> ShowList();

    Optional<Mission> getCodeMission(String code);


}
