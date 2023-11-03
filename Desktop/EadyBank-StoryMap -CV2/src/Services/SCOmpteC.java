package Services;

import dto.*;
import implementation.CompteCourantImpl;
import interfeces.ICompte;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class SCOmpteC {
    public static void compteCourantManagement(Scanner scanner, ICompte compteCourantService) {
        while (true) {
            System.out.println("Compte Management Menu:");
            System.out.println("1. Add Compte");
            System.out.println("2. Search Compte by Client Code");
            System.out.println("3. Delete Compte by Numero");
            System.out.println("4. Update Compte Status by Numero");
            System.out.println("5. Show All Comptes");
            System.out.println("6. Search Compte by Operation Type");
            System.out.println("7. Filter Comptes by Status");
            System.out.println("8. Filter Comptes by Date of Creation");
            System.out.println("9. Back to Main Menu");

            System.out.print("Enter your choice (1-9): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addCompteCourant(scanner, compteCourantService);
                case 2 -> searchCompteCourantByClientCode(scanner, compteCourantService);
                case 3 -> deleteCompteByNumero(scanner, compteCourantService);
                case 4 -> updateCompteCourantStatusByNumero(scanner, compteCourantService);
                case 5 -> showAllComptesCourant(compteCourantService);
                case 6 -> searchCompteCourantByOperation(scanner, compteCourantService);
                case 7 -> filterComptesCourantByStatus(scanner, compteCourantService);
                case 8 -> filterComptesCourantByDateOfCreation(scanner, compteCourantService);
                case 9 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }


    private static void addCompteCourant(Scanner scanner, ICompte compteCourantService) {
        System.out.println("Enter Compte Courant details:");

        System.out.print("Numero: ");
        String numero = scanner.nextLine();

        System.out.print("Sold: ");
        double sold = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Etat: ");
        String etatStr = scanner.nextLine();
        EtatCompte etat = EtatCompte.valueOf(etatStr);

        System.out.print("Client Code: ");
        String clientCode = scanner.nextLine();
        Client client = new Client(clientCode, null, null, null, null, null, null);

        System.out.print("Employe Matricule: ");
        String employeMatricule = scanner.nextLine();
        Employe employe = new Employe(null, null, null, null, null, employeMatricule, null, null, null, null, null);

        System.out.print("Decouvert: ");
        double decouvert = scanner.nextDouble();
        scanner.nextLine();

        LocalDate currentDate = LocalDate.now();

        List<Operation> operations = new ArrayList<>();

        Compte compte = new CompteCourant(numero, sold, currentDate, etat, client, employe, operations, decouvert);

        Optional<Compte> addedCompte = compteCourantService.Add(compte);

        if (addedCompte.isPresent()) {
            System.out.println("Compte added successfully!");
        } else {
            System.out.println("Failed to add Compte.");
        }
    }

    private static void searchCompteCourantByClientCode(Scanner scanner, ICompte compteCourantService) {
        System.out.print("Enter Client Code to search Comptes: ");
        String clientCode = scanner.nextLine();

        Client client = new Client(clientCode, null, null, null, null, null, null);

        List<Compte> comptes = compteCourantService.SearchByClient(client);

        if (comptes.isEmpty()) {
            System.out.println("No Comptes found for the specified Client Code.");
        } else {
            System.out.println("Comptes for Client Code '" + clientCode + "':");
            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        }
    }

    private static void deleteCompteByNumero(Scanner scanner, ICompte compteService) {
        System.out.print("Enter Compte Numero to delete: ");
        String numero = scanner.nextLine();

        boolean deleted = compteService.Delete(numero);

        if (deleted) {
            System.out.println("Compte with Numero '" + numero + "' deleted successfully.");
        } else {
            System.out.println("No Compte found with the specified Numero. Deletion failed.");
        }
    }

    private static void updateCompteCourantStatusByNumero(Scanner scanner, ICompte compteCourantService) {
        System.out.print("Enter Compte Numero to update status: ");
        String numero = scanner.nextLine();

        Compte existingCompte = CompteCourantImpl.GetByNumero(numero);

        if (existingCompte == null) {
            System.out.println("No Compte found with the specified Numero.");
            return;
        }

        System.out.print("Enter new status (ACTIVE or INACTIVE): ");
        String newStatusStr = scanner.nextLine();
        EtatCompte newStatus = EtatCompte.valueOf(newStatusStr);

        existingCompte.setEtat(newStatus);

        Optional<Compte> updatedCompte = compteCourantService.UpdateStatus(existingCompte);

        if (updatedCompte.isPresent()) {
            System.out.println("Compte status updated successfully.");
        } else {
            System.out.println("Failed to update Compte status.");
        }
    }
    private static void showAllComptesCourant(ICompte compteCourantService) {
        List<Compte> comptes = compteCourantService.ShowList();
        if (comptes.isEmpty()) {
            System.out.println("No compte found in the database.");
        } else {
            System.out.println("List of all comptes:");
            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        }
    }
    private static void searchCompteCourantByOperation(Scanner scanner, ICompte compteCourantService) {
        System.out.print("Enter Operation Type (versement or retrait): ");
        String operationType = scanner.nextLine();

        TypeOperation typeOperation;
        try {
            typeOperation = TypeOperation.valueOf(operationType);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid Operation Type. Please enter 'versement' or 'retrait'.");
            return;
        }

        Operation operation = new Operation(null,null,null,typeOperation,null,null);
        List<Compte> comptes = compteCourantService.SearchByOperation(operation);

        if (comptes.isEmpty()) {
            System.out.println("No Comptes found for the specified Operation Type.");
        } else {
            System.out.println("Comptes for Operation Type '" + operationType + "':");
            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        }
    }

    private static void filterComptesCourantByStatus(Scanner scanner, ICompte compteCourantService) {
        System.out.print("Enter Status (ACTIVE or INACTIVE): ");
        String status = scanner.nextLine();

        EtatCompte etat = EtatCompte.valueOf(status);
        List<Compte> comptes = compteCourantService.FilterByStatus(etat);

        if (comptes.isEmpty()) {
            System.out.println("No Comptes found with the specified status.");
        } else {
            System.out.println("Comptes with status '" + status + "':");
            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        }
    }


    private static void filterComptesCourantByDateOfCreation(Scanner scanner, ICompte compteCourantService) {
        System.out.print("Enter Date of Creation (yyyy-MM-dd): ");
        String dateCreationStr = scanner.nextLine();

        try {
            LocalDate dateCreation = LocalDate.parse(dateCreationStr);
            List<Compte> comptes = compteCourantService.FilterByDCreation(dateCreation);

            if (comptes.isEmpty()) {
                System.out.println("No Comptes found with the specified Date of Creation.");
            } else {
                System.out.println("Comptes with Date of Creation '" + dateCreationStr + "':");
                for (Compte compte : comptes) {
                    System.out.println(compte);
                }
            }
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format. Please enter a date in yyyy-MM-dd format.");
        }
    }

}
