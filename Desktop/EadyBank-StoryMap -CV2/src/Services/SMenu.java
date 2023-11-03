package Services;

import implementation.*;
import interfeces.*;

import java.util.Scanner;


public class SMenu {
    private static final IEmploye employeService = new EmployeImpl();

    private static final IClient clientService = new ClientImpl();
    private static final IMission missionService = new MissionImpl();
    private static final IAffectation affectationService = new AffectationImpl();
    private static final IOperation operationService = new OperationImpl();
    private static final ICompte compteCourantService = new CompteCourantImpl();
    private static final ICompte compteEpargneService = new CompteEpargneImpl();

    private static final Scanner scanner = new Scanner(System.in);

    public static void startMenu() {
        while (true) {
            System.out.println("EasyBank Management System Menu:");
            System.out.println("1. Employee Management");
            System.out.println("2. Client Management");
            System.out.println("3. Mission Management");
            System.out.println("4. Affectation Management");
            System.out.println("5. compte Courant Management");
            System.out.println("6. compte Epargne Management");
            System.out.println("7. Operation Management");
            System.out.println("8. Exit");

            System.out.print("Enter your choice : ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    SEmploye.employeeManagement(scanner, employeService);
                    break;
                case 2:
                    SClient.clientManagement(scanner, clientService);
                    break;
                case 3:
                    SMission.missionManagement(scanner, missionService);
                    break;
                case 4:
                    SAffectation.affectationManagement(scanner, affectationService, employeService, missionService);
                    break;
                case 5:
                    SCOmpteC.compteCourantManagement(scanner, compteCourantService);
                    break;
                case 6:
                    SCOmpteE.compteEpargneManagement(scanner, compteEpargneService);
                    break;
                case 7:
                    SOperation.operationManagement(scanner, operationService);
                    break;
                case 8:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
            }
        }
    }
}