package Services;

import dto.Employe;
import dto.Personne;
import interfeces.IEmploye;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class SEmploye {

    public static void employeeManagement(Scanner scanner, IEmploye employeService) {
        while (true) {
            System.out.println("Employee Management Menu:");
            System.out.println("1. Add Employee");
            System.out.println("2. Search Employee by Matricule");
            System.out.println("3. Delete Employee by Matricule");
            System.out.println("4. Show All Employees");
            System.out.println("5. Search Employees by Recruitment Date");
            System.out.println("6. Update Employee");
            System.out.println("7. Back to Main Menu");

            System.out.print("Enter your choice (1-7): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addEmployee(scanner, employeService);
                case 2 -> searchEmployeeByMatricule(scanner, employeService);
                case 3 -> deleteEmployeeByMatricule(scanner, employeService);
                case 4 -> showAllEmployees(employeService);
                case 5 -> searchEmployeesByRecruitmentDate(scanner, employeService);
                case 6 -> updateEmployee(scanner, employeService);
                case 7 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }
    }

    private static void addEmployee(Scanner scanner, IEmploye employeService) {
        System.out.println("Enter employee details:");
        System.out.print("Matricule: ");
        String matricule = scanner.nextLine();

        LocalDate recruitmentDate = null;
        boolean validRecruitmentDate = false;
        while (!validRecruitmentDate) {
            System.out.print("Recruitment Date (yyyy-MM-dd): ");
            String recruitmentDateStr = scanner.nextLine();
            try {
                recruitmentDate = LocalDate.parse(recruitmentDateStr);
                validRecruitmentDate = true;
            } catch (DateTimeParseException e) {
                System.err.println("Invalid date format. Please enter a date in yyyy-MM-dd format.");
            }
        }

        System.out.print("Email Address: ");
        String email = scanner.nextLine();

        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        LocalDate dateOfBirth = null;
        boolean validDateOfBirth = false;
        while (!validDateOfBirth) {
            System.out.print("Date of Birth (yyyy-MM-dd): ");
            String dateOfBirthStr = scanner.nextLine();
            try {
                dateOfBirth = LocalDate.parse(dateOfBirthStr);
                validDateOfBirth = true;
            } catch (DateTimeParseException e) {
                System.err.println("Invalid date format. Please enter a date in yyyy-MM-dd format.");
            }
        }

        System.out.print("Phone Number: ");
        String phone = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        Employe employe = new Employe(firstName, lastName, dateOfBirth, phone, address, matricule, recruitmentDate, email, null, null, null);

        Optional<Personne> result = employeService.Add(employe);

        if (result.isPresent()) {
            System.out.println("Employee added successfully!");
        } else {
            System.out.println("Failed to add the employee.");
        }
    }


    private static void searchEmployeeByMatricule(Scanner scanner, IEmploye employeService) {
        System.out.print("Enter Matricule to search: ");
        String matricule = scanner.nextLine();
        Optional<List<Employe>> optionalEmployees = employeService.SearchByMatricule(matricule);

        if (optionalEmployees.isPresent()) {
            List<Employe> employees = optionalEmployees.get();
            System.out.println("Employees with Matricule '" + matricule + "':");
            employees.forEach(System.out::println);
        } else {
            System.out.println("No employees found with the specified Matricule.");
        }
    }



    private static void deleteEmployeeByMatricule(Scanner scanner, IEmploye employeService) {
        System.out.print("Enter Matricule to delete: ");
        String matricule = scanner.nextLine();
        boolean deleted = employeService.Delete(matricule);
        if (deleted) {
            System.out.println("Employee with Matricule '" + matricule + "' deleted successfully.");
        } else {
            System.out.println("No employee found with the specified Matricule. Deletion failed.");
        }
    }

    private static void showAllEmployees(IEmploye employeService) {
        Optional<List<Employe>> employees = employeService.ShowList();
        if (employees.isPresent()) {
            List<Employe> employeeList = employees.get();
            System.out.println("List of all employees:");
            for (Employe employee : employeeList) {
                System.out.println(employee);
            }
        } else {
            System.out.println("No employees found in the database.");
        }
    }


    private static void searchEmployeesByRecruitmentDate(Scanner scanner, IEmploye employeService) {
        System.out.print("Enter Recruitment Date (yyyy-MM-dd) to search: ");
        String dateRecruitmentStr = scanner.nextLine();
        try {
            LocalDate dateRecruitment = LocalDate.parse(dateRecruitmentStr);
            Optional<List<Employe>> optionalEmployees = employeService.SearchByDateR(dateRecruitment);

            if (optionalEmployees.isPresent()) {
                List<Employe> employees = optionalEmployees.get();
                if (employees.isEmpty()) {
                    System.out.println("No employees found with the specified Recruitment Date.");
                } else {
                    System.out.println("Employees recruited on '" + dateRecruitmentStr + "':");
                    for (Employe employee : employees) {
                        System.out.println(employee);
                    }
                }
            } else {
                System.out.println("No employees found with the specified Recruitment Date.");
            }
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format. Please enter a date in yyyy-MM-dd format.");
        }
    }


    private static void updateEmployee(Scanner scanner, IEmploye employeService) {
        System.out.print("Enter Matricule to update: ");
        String matricule = scanner.nextLine();
        Optional<List<Employe>> employeesOptional = employeService.SearchByMatricule(matricule);

        if (employeesOptional.isPresent()) {
            List<Employe> employees = employeesOptional.get();

            if (employees.isEmpty()) {
                System.out.println("No employees found with the specified Matricule.");
            } else {
                Employe employeeToUpdate = employees.get(0);

                System.out.println("Update employee details:");
                System.out.print("Email Address: ");
                String email = scanner.nextLine();

                System.out.print("First Name: ");
                String firstName = scanner.nextLine();

                System.out.print("Last Name: ");
                String lastName = scanner.nextLine();

                LocalDate dateOfBirth = null;
                boolean validDateOfBirth = false;
                while (!validDateOfBirth) {
                    System.out.print("Date of Birth (yyyy-MM-dd): ");
                    String dateOfBirthStr = scanner.nextLine();
                    try {
                        dateOfBirth = LocalDate.parse(dateOfBirthStr);
                        validDateOfBirth = true;
                    } catch (DateTimeParseException e) {
                        System.err.println("Invalid date format. Please enter a date in yyyy-MM-dd format.");
                    }
                }

                System.out.print("Phone Number: ");
                String phone = scanner.nextLine();

                System.out.print("Address: ");
                String address = scanner.nextLine();

                employeeToUpdate.setEmailAdresse(email);
                employeeToUpdate.setNom(firstName);
                employeeToUpdate.setPrenom(lastName);
                employeeToUpdate.setDateN(dateOfBirth);
                employeeToUpdate.setTel(phone);
                employeeToUpdate.setAdress(address);

                Optional<Employe> updatedEmployee = employeService.Update(employeeToUpdate);

                if (updatedEmployee.isPresent()) {
                    System.out.println("Employee with Matricule '" + matricule + "' updated successfully.");
                } else {
                    System.out.println("Failed to update employee with Matricule '" + matricule + "'.");
                }
            }
        } else {
            System.out.println("No employees found with the specified Matricule.");
        }
    }


}
