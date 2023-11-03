package implementation;

import dto.Client;
import dto.Compte;
import dto.CompteEpargne;
import dto.EtatCompte;
import dto.Operation;
import helper.DatabaseConnection;
import interfeces.ICompte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompteEpargneImpl implements ICompte {
    private static final String INSERT_COMPTE_EPARGNE = "INSERT INTO Comptes (numero, sold, dateCreation, etat, client_code, employe_matricule) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String INSERT_COMPTE_EPARGNE_TABLE = "INSERT INTO ComptesEpargnes (numeroCompte, tauxInteret) VALUES (?, ?)";
    private static final String DELETE_COMPTE = "DELETE FROM Comptes WHERE numero = ?";
    private static final String UPDATE_STATUS_COMPTE = "UPDATE Comptes SET etat = ? WHERE numero = ?";
    private static final String LIST_COMPTE = "SELECT c.numero, c.sold, c.dateCreation, c.etat, ce.tauxInteret " +
            "FROM Comptes c " +
            "LEFT JOIN ComptesEpargnes ce ON c.numero = ce.numeroCompte";
    private static final String UPDATE_COMPTE = "UPDATE Comptes " +
            "SET sold = ?, dateCreation = ?, etat = ?, client_code = ?, employe_matricule = ? " +
            "WHERE numero = ?";
    private static final String GET_COMPTE_BY_NUMBER = "SELECT * FROM Comptes WHERE numero = ?";
    private static final String SEARCH_BY_CLIENT = "SELECT * FROM Comptes WHERE client_code = ?";
    private static final String SEARCH_BY_CLIENT_TAUX = "SELECT tauxInteret FROM ComptesEpargnes WHERE numeroCompte = ?";
    private static final String SEARCH_BY_OPERATION = "SELECT c.numero, c.sold, c.dateCreation, c.etat, ce.tauxInteret " +
            "FROM Comptes c " +
            "LEFT JOIN ComptesEpargnes ce ON c.numero = ce.numeroCompte " +
            "INNER JOIN Operations ao ON c.numero = ao.compte_numero " +
            "WHERE ao.type = ?";
    private static final String FILTER_BY_STATUS = "SELECT c.numero, c.sold, c.dateCreation, c.etat, ce.tauxInteret " +
            "FROM Comptes c " +
            "LEFT JOIN ComptesEpargnes ce ON c.numero = ce.numeroCompte " +
            "WHERE c.etat = ? " +
            "ORDER BY c.etat DESC";
    private static final String FILTER_BY_DATE_CREATION = "SELECT c.numero, c.sold, c.dateCreation, c.etat, ce.tauxInteret " +
            "FROM Comptes c " +
            "LEFT JOIN ComptesEpargnes ce ON c.numero = ce.numeroCompte " +
            "WHERE c.dateCreation = ?";

    public static Compte GetByNumero(String numero) {
        Connection connection = DatabaseConnection.getConn();
        Compte compte = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_COMPTE_BY_NUMBER)) {
            preparedStatement.setString(1, numero);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String fetchedNumero = resultSet.getString("numero");
                double sold = resultSet.getDouble("sold");
                LocalDate dateCreation = resultSet.getDate("dateCreation").toLocalDate();
                String etatStr = resultSet.getString("etat");

                compte = new CompteEpargne(fetchedNumero, sold, dateCreation, EtatCompte.valueOf(etatStr), null, null, null, 0.0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return compte;
    }

    @Override
    public Optional<Compte> Add(Compte compte) {
        if (compte instanceof CompteEpargne compteEpargne) {
            Connection connection = DatabaseConnection.getConn();

            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMPTE_EPARGNE)) {
                preparedStatement.setString(1, compteEpargne.getNumero());
                preparedStatement.setDouble(2, compteEpargne.getSold());
                preparedStatement.setObject(3, compteEpargne.getDateCreation());
                preparedStatement.setString(4, compteEpargne.getEtat().name());
                preparedStatement.setString(5, compteEpargne.getClient().getCode());
                preparedStatement.setString(6, compteEpargne.getEmploye().getMatricule());

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    try (PreparedStatement compteEpargneStatement = connection.prepareStatement(INSERT_COMPTE_EPARGNE_TABLE)) {
                        compteEpargneStatement.setString(1, compteEpargne.getNumero());
                        compteEpargneStatement.setDouble(2, compteEpargne.getTauxInteret());
                        compteEpargneStatement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    return Optional.of(compteEpargne);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Compte> SearchByClient(Client client) {
        List<Compte> compteList = new ArrayList<>();
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_CLIENT)) {
            preparedStatement.setString(1, client.getCode());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String numero = resultSet.getString("numero");
                double sold = resultSet.getDouble("sold");
                LocalDate dateCreation = resultSet.getDate("dateCreation").toLocalDate();
                String etatStr = resultSet.getString("etat");
                EtatCompte etat = EtatCompte.valueOf(etatStr);

                try (PreparedStatement tauxStatement = connection.prepareStatement(SEARCH_BY_CLIENT_TAUX)) {
                    tauxStatement.setString(1, numero);
                    ResultSet tauxResultSet = tauxStatement.executeQuery();
                    double tauxInteret = 0.0;

                    if (tauxResultSet.next()) {
                        tauxInteret = tauxResultSet.getDouble("tauxInteret");
                    }

                    CompteEpargne compteEpargne = new CompteEpargne(numero, sold, dateCreation, etat, client, null, null, tauxInteret);
                    compteList.add(compteEpargne);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return compteList;
    }

    @Override
    public boolean Delete(String numero) {
        Connection connection = DatabaseConnection.getConn();
        try (PreparedStatement deleteComptesStatement = connection.prepareStatement(DELETE_COMPTE)) {
            deleteComptesStatement.setString(1, numero);
            int rowsDeleted = deleteComptesStatement.executeUpdate();

            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Compte> UpdateStatus(Compte compte) {
        if (compte instanceof CompteEpargne compteEpargne) {
            Connection connection = DatabaseConnection.getConn();
            try (PreparedStatement updateCompteStatusStatement = connection.prepareStatement(UPDATE_STATUS_COMPTE)) {
                updateCompteStatusStatement.setString(1, compteEpargne.getEtat().name());
                updateCompteStatusStatement.setString(2, compteEpargne.getNumero());

                int rowsUpdated = updateCompteStatusStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    return Optional.of(compteEpargne);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Compte> FilterByStatus(EtatCompte etat) {
        List<Compte> compteList = new ArrayList<>();

        Connection connection = DatabaseConnection.getConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FILTER_BY_STATUS)) {
            preparedStatement.setString(1, etat.name());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String numero = resultSet.getString("numero");
                double sold = resultSet.getDouble("sold");
                LocalDate dateCreation = resultSet.getDate("dateCreation").toLocalDate();
                String etatStr = resultSet.getString("etat");
                double tauxInteret = resultSet.getDouble("tauxInteret");

                Compte compte = new CompteEpargne(numero, sold, dateCreation, EtatCompte.valueOf(etatStr), null, null, null, tauxInteret);
                compteList.add(compte);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return compteList;
    }

    @Override
    public List<Compte> ShowList() {
        List<Compte> compteList = new ArrayList<>();
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(LIST_COMPTE)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String numero = resultSet.getString("numero");
                double sold = resultSet.getDouble("sold");
                LocalDate dateCreation = resultSet.getDate("dateCreation").toLocalDate();
                String etatStr = resultSet.getString("etat");
                double tauxInteret = resultSet.getDouble("tauxInteret");

                Compte compte = new CompteEpargne(numero, sold, dateCreation, EtatCompte.valueOf(etatStr), null, null, null, tauxInteret);
                compteList.add(compte);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return compteList;
    }

    @Override
    public List<Compte> FilterByDCreation(LocalDate dateCreation) {
        List<Compte> compteList = new ArrayList<>();
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(FILTER_BY_DATE_CREATION)) {
            preparedStatement.setObject(1, dateCreation);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String numero = resultSet.getString("numero");
                double sold = resultSet.getDouble("sold");
                LocalDate creationDate = resultSet.getDate("dateCreation").toLocalDate();
                String etatStr = resultSet.getString("etat");
                double tauxInteret = resultSet.getDouble("tauxInteret");

                Compte compte = new CompteEpargne(numero, sold, creationDate, EtatCompte.valueOf(etatStr), null, null, null, tauxInteret);
                compteList.add(compte);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return compteList;
    }

    @Override
    public Optional<Compte> Update(Compte compte) {
        if (compte instanceof CompteEpargne compteEpargne) {
            Connection connection = DatabaseConnection.getConn();

            try (PreparedStatement updateCompteStatement = connection.prepareStatement(UPDATE_COMPTE)) {
                updateCompteStatement.setDouble(1, compteEpargne.getSold());
                updateCompteStatement.setObject(2, compteEpargne.getDateCreation());
                updateCompteStatement.setString(3, compteEpargne.getEtat().name());
                updateCompteStatement.setString(4, compteEpargne.getClient().getCode());
                updateCompteStatement.setString(5, compteEpargne.getEmploye().getMatricule());
                updateCompteStatement.setString(6, compteEpargne.getNumero());

                int rowsUpdated = updateCompteStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    return Optional.of(compteEpargne);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Compte> SearchByOperation(Operation operation) {
        List<Compte> compteList = new ArrayList<>();
        Connection connection = DatabaseConnection.getConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_OPERATION)) {
            preparedStatement.setString(1, operation.getType().name());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String numero = resultSet.getString("numero");
                double sold = resultSet.getDouble("sold");
                LocalDate dateCreation = resultSet.getDate("dateCreation").toLocalDate();
                String etatStr = resultSet.getString("etat");
                double tauxInteret = resultSet.getDouble("tauxInteret");

                Compte compte = new CompteEpargne(numero, sold, dateCreation, EtatCompte.valueOf(etatStr), null, null, null, tauxInteret);
                compteList.add(compte);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return compteList;
    }
}