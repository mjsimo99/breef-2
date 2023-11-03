package implementation;

import dto.*;
import helper.DatabaseConnection;
import interfeces.IOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OperationImpl implements IOperation {
    private static final String ADD_OPERATION = "INSERT INTO operations (numero, datecreation, montant, type, employe_matricule, compte_numero) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SEARCH_BY_NUMBER = "SELECT * FROM operations WHERE numero=?";
    private static final String DELETE_OPERATION = "DELETE FROM operations WHERE numero=?";
    private static final String GET_EMPLOYE_BY_MATRICULE = "SELECT * FROM employes WHERE matricule=?";
    private static final String GET_COMPTE_EPARGNE = "SELECT * FROM ComptesEpargnes WHERE numeroCompte = ?";
    private static final String GET_COMPTE_COURANT = "SELECT * FROM ComptesCourants WHERE numeroCompte = ?";

    public Compte getCompteByNumero(String numero) {
        Connection connection = DatabaseConnection.getConn();

        try {
            PreparedStatement preparedStatementCourant = connection.prepareStatement(GET_COMPTE_COURANT);
            preparedStatementCourant.setString(1, numero);
            ResultSet resultSetCourant = preparedStatementCourant.executeQuery();

            if (resultSetCourant.next()) {
                return new CompteCourant(
                        resultSetCourant.getString("numeroCompte"),
                        0.0,
                        null,
                        null,
                        null,
                        null,
                        null,
                        resultSetCourant.getDouble("decouvert")
                );
            }

            PreparedStatement preparedStatementEpargne = connection.prepareStatement(GET_COMPTE_EPARGNE);
            preparedStatementEpargne.setString(1, numero);
            ResultSet resultSetEpargne = preparedStatementEpargne.executeQuery();

            if (resultSetEpargne.next()) {
                return new CompteEpargne(
                        resultSetEpargne.getString("numeroCompte"),
                        0.0,
                        null,
                        null,
                        null,
                        null,
                        null,
                        resultSetEpargne.getDouble("tauxInteret")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while retrieving account by numero: " + e.getMessage(), e);
        }
        return null;
    }

    public Employe getEmployeByMatricule(String matricule) {
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_EMPLOYE_BY_MATRICULE)) {
            preparedStatement.setString(1, matricule);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Employe(
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getDate("dateN").toLocalDate(),
                        resultSet.getString("tel"),
                        resultSet.getString("adress"),
                        resultSet.getString("matricule"),
                        resultSet.getDate("dateRecrutement").toLocalDate(),
                        resultSet.getString("emailAdresse"),
                        null,
                        null,
                        null
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Optional<Operation> Add(Operation operation) {
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_OPERATION)) {
            preparedStatement.setString(1, operation.getNumero());
            preparedStatement.setObject(2, operation.getDateCreation());
            preparedStatement.setDouble(3, operation.getMontant());
            preparedStatement.setString(4, operation.getType().toString());
            preparedStatement.setString(5, operation.getEmploye().getMatricule());
            preparedStatement.setString(6, operation.getCompte().getNumero());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(operation);
    }

    @Override
    public Optional<List<Operation>> SearchByNumber(String numero) {
        List<Operation> resultList = new ArrayList<>();
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_NUMBER)) {
            preparedStatement.setString(1, numero);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Operation operation = new Operation(
                        resultSet.getString("numero"),
                        resultSet.getObject("datecreation", LocalDate.class),
                        resultSet.getDouble("montant"),
                        TypeOperation.valueOf(resultSet.getString("type")),
                        null,
                        null
                );
                resultList.add(operation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(resultList);
    }

    @Override
    public boolean Delete(String numero) {
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_OPERATION)) {
            preparedStatement.setString(1, numero);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}