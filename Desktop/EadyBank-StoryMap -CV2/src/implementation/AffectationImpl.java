package implementation;
import java.time.LocalDate;

import dto.Affectation;
import dto.Employe;
import dto.Mission;
import helper.DatabaseConnection;
import interfeces.IAffectation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AffectationImpl implements IAffectation {
    private static final String INSERT_AFFECTATION = "INSERT INTO Affectations (employe_matricule, mission_code, datedebut, datefin) VALUES (?, ?, ?, ?)";
    private static final String DELETE_AFFECTATION = "DELETE FROM Affectations WHERE employe_matricule = ? AND mission_code = ?";
    private static final String SELECT_AFFECTATIONS_BY_MATRICULE = "SELECT * FROM Affectations WHERE employe_matricule = ?";
    private static final String TOTAL_AFFECTATIONS = "SELECT COUNT(*) AS TotalAffectations FROM Affectations";
    private static final String TOTAL_EMPLOYES = "SELECT COUNT(DISTINCT employe_matricule) AS TotalEmployees FROM Affectations";
    private static final String TOTAL_MISSIONS = "SELECT COUNT(DISTINCT mission_code) AS TotalMissions FROM Affectations";

    @Override
    public Optional<Affectation> createNewAffectation(Affectation affectation) {
        Connection connection = DatabaseConnection.getConn();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AFFECTATION)) {
            preparedStatement.setString(1, affectation.getEmploye().getMatricule());
            preparedStatement.setString(2, affectation.getMission().getCode());
            preparedStatement.setObject(3, affectation.getDatedebut());
            preparedStatement.setObject(4, affectation.getDatefin());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(affectation);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean DeleteAffectation(Affectation affectation) {
        Connection connection = DatabaseConnection.getConn();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AFFECTATION)) {
            preparedStatement.setString(1, affectation.getEmploye().getMatricule());
            preparedStatement.setString(2, affectation.getMission().getCode());
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Affectation> getAssignmentHistoryByMatricule(String matricule) {
        List<Affectation> assignmentHistory = new ArrayList<>();
        Connection connection = DatabaseConnection.getConn();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AFFECTATIONS_BY_MATRICULE)) {
            preparedStatement.setString(1, matricule);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String missionCode = resultSet.getString("mission_code");
                LocalDate datedebut = resultSet.getObject("datedebut", LocalDate.class);
                LocalDate datefin = resultSet.getObject("datefin", LocalDate.class);

                Employe employe = new EmployeImpl().getEmployeById(matricule);

                Mission mission = new MissionImpl().getCodeMission(missionCode).orElse(null);

                Affectation affectation = new Affectation(employe, mission, datedebut, datefin);
                assignmentHistory.add(affectation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return assignmentHistory;
    }

    @Override
    public HashMap<String, Integer> getAffectationStatistics() {
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatementTotalAffectations = connection.prepareStatement(TOTAL_AFFECTATIONS);
             PreparedStatement preparedStatementTotalEmployees = connection.prepareStatement(TOTAL_EMPLOYES);
             PreparedStatement preparedStatementTotalMissions = connection.prepareStatement(TOTAL_MISSIONS)) {

            ResultSet resultSetTotalAffectations = preparedStatementTotalAffectations.executeQuery();
            ResultSet resultSetTotalEmployees = preparedStatementTotalEmployees.executeQuery();
            ResultSet resultSetTotalMissions = preparedStatementTotalMissions.executeQuery();

            HashMap<String, Integer> statistics = new HashMap<>();

            statistics.put("TotalAffectations", resultSetTotalAffectations.next() ? resultSetTotalAffectations.getInt("TotalAffectations") : 0);
            statistics.put("TotalEmployees", resultSetTotalEmployees.next() ? resultSetTotalEmployees.getInt("TotalEmployees") : 0);
            statistics.put("TotalMissions", resultSetTotalMissions.next() ? resultSetTotalMissions.getInt("TotalMissions") : 0);

            return statistics;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}


