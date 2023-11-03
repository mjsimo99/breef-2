package Services;

import dto.Mission;
import interfeces.IMission;

import java.util.List;
import java.util.Scanner;

public class SMission {
    public static void missionManagement(Scanner scanner, IMission missionService) {
        while (true) {
            System.out.println("Mission Management Menu:");
            System.out.println("1. Add Mission");
            System.out.println("2. Delete Mission by Code");
            System.out.println("3. Show All Missions");
            System.out.println("4. Back to Main Menu");

            System.out.print("Enter your choice (1-6): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addMission(scanner, missionService);
                case 2 -> deleteMissionByCode(scanner, missionService);
                case 3 -> showAllMissions(missionService);
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }

    private static void addMission(Scanner scanner, IMission missionService) {
        System.out.println("Enter mission details:");
        System.out.print("Code: ");
        String code = scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        Mission mission = new Mission(code, name, description, null);

        missionService.Add(mission);

        System.out.println("Mission added successfully!");
    }

    private static void deleteMissionByCode(Scanner scanner, IMission missionService) {
        System.out.print("Enter Code to delete: ");
        String code = scanner.nextLine();
        boolean deleted = missionService.Delete(code);
        if (deleted) {
            System.out.println("Mission with Code '" + code + "' deleted successfully.");
        } else {
            System.out.println("No mission found with the specified Code. Deletion failed.");
        }
    }

    private static void showAllMissions(IMission missionService) {
        List<Mission> missions = missionService.ShowList();
        if (missions.isEmpty()) {
            System.out.println("No missions found in the database.");
        } else {
            System.out.println("List of all missions:");
            for (Mission mission : missions) {
                System.out.println(mission);
            }
        }
    }


}
