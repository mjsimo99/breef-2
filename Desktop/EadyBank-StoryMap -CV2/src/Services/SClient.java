package Services;

import dto.Client;
import interfeces.IClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SClient {
    public static void clientManagement(Scanner scanner, IClient clientService) {
        while (true) {
            System.out.println("Client Management Menu:");
            System.out.println("1. Add Client");
            System.out.println("2. Search Client by Code");
            System.out.println("3. Delete Client by Code");
            System.out.println("4. Show All Clients");
            System.out.println("5. Search Clients by Last Name");
            System.out.println("6. Update Client");
            System.out.println("7. Back to Main Menu");

            System.out.print("Enter your choice (1-7): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addClient(scanner, clientService);
                case 2 -> searchClientByCode(scanner, clientService);
                case 3 -> deleteClientByCode(scanner, clientService);
                case 4 -> showAllClients(clientService);
                case 5 -> searchClientsByLastName(scanner, clientService);
                case 6 -> updateClient(scanner, clientService);
                case 7 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }
    }

    private static void addClient(Scanner scanner, IClient clientService) {
        System.out.println("Enter client details:");
        System.out.print("Code: ");
        String code = scanner.nextLine();

        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        LocalDate dateOfBirth = null;
        boolean validDateOfBirth = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (!validDateOfBirth) {
            System.out.print("Date of Birth (yyyy-MM-dd): ");
            String dateOfBirthStr = scanner.nextLine();

            try {
                dateOfBirth = LocalDate.parse(dateOfBirthStr, formatter);
                validDateOfBirth = true;
            } catch (Exception e) {
                System.err.println("Invalid date format. Please enter a date in yyyy-MM-dd format.");
            }
        }

        System.out.print("Phone Number: ");
        String phone = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        Client client = new Client(code, lastName, firstName, dateOfBirth, phone, address, null);

        clientService.Add(client);

        System.out.println("Client added successfully!");
    }


    private static void searchClientByCode(Scanner scanner, IClient clientService) {
        System.out.print("Enter Code to search: ");
        String code = scanner.nextLine();
        Optional<List<Client>> optionalClients = clientService.SearchByCode(code);

        if (optionalClients.isPresent()) {
            List<Client> clients = optionalClients.get();
            if (clients.isEmpty()) {
                System.out.println("No clients found with the specified Code.");
            } else {
                System.out.println("Clients with Code '" + code + "':");
                for (Client client : clients) {
                    System.out.println(client);
                }
            }
        } else {
            System.out.println("No clients found with the specified Code.");
        }
    }


    private static void deleteClientByCode(Scanner scanner, IClient clientService) {
        System.out.print("Enter Code to delete: ");
        String code = scanner.nextLine();
        boolean deleted = clientService.Delete(code);
        if (deleted) {
            System.out.println("Client with Code '" + code + "' deleted successfully.");
        } else {
            System.out.println("No client found with the specified Code. Deletion failed.");
        }
    }

    private static void showAllClients(IClient clientService) {
        Optional<List<Client>> clientsOptional = clientService.Showlist();
        if (clientsOptional.isPresent()) {
            List<Client> clients = clientsOptional.get();
            if (clients.isEmpty()) {
                System.out.println("No clients found in the database.");
            } else {
                System.out.println("List of all clients:");
                for (Client client : clients) {
                    System.out.println(client);
                }
            }
        } else {
            System.out.println("No clients found in the database.");
        }
    }


    private static void searchClientsByLastName(Scanner scanner, IClient clientService) {
        System.out.print("Enter Last Name to search: ");
        String lastName = scanner.nextLine();
        Optional<List<Client>> clientsOptional = clientService.SearchByLastName(lastName);
        if (clientsOptional.isPresent()) {
            List<Client> clients = clientsOptional.get();
            if (clients.isEmpty()) {
                System.out.println("No clients found with the specified Last Name.");
            } else {
                System.out.println("Clients with Last Name '" + lastName + "':");
                for (Client client : clients) {
                    System.out.println(client);
                }
            }
        } else {
            System.out.println("No clients found with the specified Last Name.");
        }
    }


    private static void updateClient(Scanner scanner, IClient clientService) {
        System.out.print("Enter Code to update: ");
        String code = scanner.nextLine();
        Optional<List<Client>> clientsOptional = clientService.SearchByCode(code);
        if (clientsOptional.isPresent()) {
            List<Client> clients = clientsOptional.get();
            if (clients.isEmpty()) {
                System.out.println("No clients found with the specified Code.");
            } else {
                Client clientToUpdate = clients.get(0);

                System.out.println("Update client details:");
                System.out.print("First Name: ");
                String firstName = scanner.nextLine();

                System.out.print("Last Name: ");
                String lastName = scanner.nextLine();

                LocalDate dateOfBirth = null; // Change Date to LocalDate
                boolean validDateOfBirth = false;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                while (!validDateOfBirth) {
                    System.out.print("Date of Birth (yyyy-MM-dd): ");
                    String dateOfBirthStr = scanner.nextLine();
                    try {
                        dateOfBirth = LocalDate.parse(dateOfBirthStr, formatter);
                        validDateOfBirth = true;
                    } catch (Exception e) {
                        System.err.println("Invalid date format. Please enter a date in yyyy-MM-dd format.");
                    }
                }

                System.out.print("Phone Number: ");
                String phone = scanner.nextLine();

                System.out.print("Address: ");
                String address = scanner.nextLine();

                clientToUpdate.setNom(firstName);
                clientToUpdate.setPrenom(lastName);
                clientToUpdate.setDateN(dateOfBirth);
                clientToUpdate.setTel(phone);
                clientToUpdate.setAdress(address);

                Optional<Client> updatedClientOptional = clientService.Update(clientToUpdate);

                if (updatedClientOptional.isPresent()) {
                    System.out.println("Client with Code '" + code + "' updated successfully.");
                } else {
                    System.out.println("Failed to update client with Code '" + code + "'.");
                }
            }
        } else {
            System.out.println("No clients found with the specified Code.");
        }
    }

}
