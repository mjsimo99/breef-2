package Services;

import dto.*;
import implementation.CompteEpargneImpl;
import interfeces.ICompte;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class SCOmpteE {
    public static void compteEpargneManagement(Scanner scanner, ICompte compteEpargneService) {

        while (true) {
            System.out.println("Compte Epargne Management Menu:");
            System.out.println("1. Add Compte Epargne");
            System.out.println("2. Search Compte Epargne by Client Code");
            System.out.println("3. Delete Compte Epargne by Numero");
            System.out.println("4. Update Compte Epargne Status by Numero");
            System.out.println("5. Show All Comptes Epargne");
            System.out.println("6. Search Compte Epargne by Operation Type");
            System.out.println("7. Filter Comptes Epargne by Status");
            System.out.println("8. Filter Comptes Epargne by Date of Creation");
            System.out.println("9. Back to Main Menu");

            System.out.print("Enter your choice (1-9): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addCompteEpargne(scanner, compteEpargneService);
                case 2 -> searchCompteEpargneByClientCode(scanner, compteEpargneService);
                case 3 -> deleteCompteEpargneByNumero(scanner, compteEpargneService);
                case 4 -> updateCompteEpargneStatusByNumero(scanner, compteEpargneService);
                case 5 -> showAllComptesEpargne(compteEpargneService);
                case 6 -> searchCompteEpargneByOperation(scanner, compteEpargneService);
                case 7 -> filterComptesEpargneByStatus(scanner, compteEpargneService);
                case 8 -> filterComptesEpargneByDateOfCreation(scanner, compteEpargneService);
                case 9 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 9.");
            }
        }

    }

    private static void addCompteEpargne(Scanner scanner, ICompte compteEpargneService) {
        System.out.println("Enter Compte Epargne details:");

        System.out.print("Numero: ");
        String numero = scanner.nextLine();

        System.out.print("Sold: ");
        double sold = scanner.nextDouble();
        scanner.nextLine();

        LocalDate currentDate = LocalDate.now();

        System.out.print("Etat: ");
        String etatStr = scanner.nextLine();
        EtatCompte etat = EtatCompte.valueOf(etatStr);

        System.out.print("Client Code: ");
        String clientCode = scanner.nextLine();
        Client client = new Client(clientCode, null, null, null, null, null, null);

        System.out.print("Employe Matricule: ");
        String employeMatricule = scanner.nextLine();
        Employe employe = new Employe(null, null, null, null, null, employeMatricule, null, null, null, null, null);

        System.out.print("Taux Interet: ");
        double tauxInteret = scanner.nextDouble();
        scanner.nextLine();

        List<Operation> operations = new ArrayList<>();


        Compte compte = new CompteEpargne(numero, sold, currentDate, etat, client, employe, operations, tauxInteret);

        Optional<Compte> addedCompte = compteEpargneService.Add(compte);

        if (addedCompte.isPresent()) {
            System.out.println("Compte Epargne added successfully!");
        } else {
            System.out.println("Failed to add Compte Epargne.");
        }
    }

    private static void searchCompteEpargneByClientCode(Scanner scanner, ICompte compteEpargneService) {
        System.out.print("Enter Client Code to search Comptes Epargne: ");
        String clientCode = scanner.nextLine();

        Client client = new Client(clientCode, null, null, null, null, null, null);

        List<Compte> comptes = compteEpargneService.SearchByClient(client);

        if (comptes.isEmpty()) {
            System.out.println("No Comptes Epargne found for the specified Client Code.");
        } else {
            System.out.println("Comptes Epargne for Client Code '" + clientCode + "':");
            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        }
    }

    private static void deleteCompteEpargneByNumero(Scanner scanner, ICompte compteEpargneService) {
        System.out.print("Enter Compte Epargne Numero to delete: ");
        String numero = scanner.nextLine();

        boolean deleted = compteEpargneService.Delete(numero);

        if (deleted) {
            System.out.println("Compte Epargne with Numero '" + numero + "' deleted successfully.");
        } else {
            System.out.println("No Compte Epargne found with the specified Numero. Deletion failed.");
        }
    }

    private static void updateCompteEpargneStatusByNumero(Scanner scanner, ICompte compteEpargneService) {
        System.out.print("Enter Compte Epargne Numero to update status: ");
        String numero = scanner.nextLine();

        Compte existingCompte = CompteEpargneImpl.GetByNumero(numero);

        if (existingCompte == null) {
            System.out.println("No Compte Epargne found with the specified Numero.");
            return;
        }

        System.out.print("Enter new status (ACTIVE or INACTIVE): ");
        String newStatusStr = scanner.nextLine();
        EtatCompte newStatus = EtatCompte.valueOf(newStatusStr);

        existingCompte.setEtat(newStatus);

        Optional<Compte> updatedCompte = compteEpargneService.UpdateStatus(existingCompte);

        if (updatedCompte.isPresent()) {
            System.out.println("Compte Epargne status updated successfully.");
        } else {
            System.out.println("Failed to update Compte Epargne status.");
        }
    }

    private static void showAllComptesEpargne(ICompte compteEpargneService) {
        List<Compte> comptes = compteEpargneService.ShowList();
        if (comptes.isEmpty()) {
            System.out.println("No Compte Epargne found in the database.");
        } else {
            System.out.println("List of all Comptes Epargne:");
            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        }
    }

    private static void searchCompteEpargneByOperation(Scanner scanner, ICompte compteEpargneService) {
        System.out.print("Enter Operation Type (versement or retrait): ");
        String operationType = scanner.nextLine();

        TypeOperation typeOperation;
        try {
            typeOperation = TypeOperation.valueOf(operationType);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid Operation Type. Please enter 'versement' or 'retrait'.");
            return;
        }

        Operation operation = new Operation(null, null, null, typeOperation, null, null);
        List<Compte> comptes = compteEpargneService.SearchByOperation(operation);

        if (comptes.isEmpty()) {
            System.out.println("No Comptes Epargne found for the specified Operation Type.");
        } else {
            System.out.println("Comptes Epargne for Operation Type '" + operationType + "':");
            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        }
    }

    private static void filterComptesEpargneByStatus(Scanner scanner, ICompte compteEpargneService) {
        System.out.print("Enter Status (ACTIVE or INACTIVE): ");
        String status = scanner.nextLine();

        EtatCompte etat = EtatCompte.valueOf(status);
        List<Compte> comptes = compteEpargneService.FilterByStatus(etat);

        if (comptes.isEmpty()) {
            System.out.println("No Comptes Epargne found with the specified status.");
        } else {
            System.out.println("Comptes Epargne with status '" + status + "':");
            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        }
    }

    private static void filterComptesEpargneByDateOfCreation(Scanner scanner, ICompte compteEpargneService) {
        System.out.print("Enter Date of Creation (yyyy-MM-dd): ");
        String dateCreationStr = scanner.nextLine();

        try {
            LocalDate dateCreation = LocalDate.parse(dateCreationStr);
            List<Compte> comptes = compteEpargneService.FilterByDCreation(dateCreation);

            if (comptes.isEmpty()) {
                System.out.println("No Comptes Epargne found with the specified Date of Creation.");
            } else {
                System.out.println("Comptes Epargne with Date of Creation '" + dateCreationStr + "':");
                for (Compte compte : comptes) {
                    System.out.println(compte);
                }
            }
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format. Please enter a date in yyyy-MM-dd format.");
        }
    }
}
