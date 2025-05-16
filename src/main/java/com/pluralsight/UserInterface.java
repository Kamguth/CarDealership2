package com.pluralsight;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private static Scanner scanner = new Scanner(System.in);
    private static Dealership dealership;

    public void display() {
        init(); // load dealership from file

        int choice = 0;
        do {
            displayMenu();

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> processGetByPriceRequest();
                case 2 -> processGetByMakeModelRequest();
                case 3 -> processGetByYearRequest();
                case 4 -> processGetByColorRequest();
                case 5 -> processGetByMileageRequest();
                case 6 -> processGetByTypeRequest();
                case 7 -> processGetAllVehiclesRequest();
                case 8 -> addVehicleProcess();
                case 9 -> removeVehicleProcess();
                case 10 -> processSaleOrLease();
                case 99 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice! Please select a valid option.");
            }


        } while (choice != 99);
    }

    // Loads dealership from file
    private void init() {
        DealershipFileManager fileManager = new DealershipFileManager();
        dealership = fileManager.getDealership("src/main/resources/inventory.csv");
    }

    // Menu helper
    private static void displayMenu() {
        System.out.println("\n===== Dealership Menu =====");
        System.out.println("1 - Find vehicles within a price range");
        System.out.println("2 - Find vehicles by make / model");
        System.out.println("3 - Find vehicles by year range");
        System.out.println("4 - Find vehicles by color");
        System.out.println("5 - Find vehicles by mileage range");
        System.out.println("6 - Find vehicles by type");
        System.out.println("7 - List ALL vehicles");
        System.out.println("8 - Add a vehicle");
        System.out.println("9 - Remove a vehicle");
        System.out.println("10 - Buy or Lease a vehicle");
        System.out.println("99 - Quit");
        System.out.print("Enter your choice: ");
    }

    // Option 1: Search by price
    private static void processGetByPriceRequest() {
        System.out.println("Option 1: Find vehicles within a price range");

        try {
            System.out.print("Enter minimum price: ");
            double minPrice = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter maximum price: ");
            double maxPrice = Double.parseDouble(scanner.nextLine());

            List<Vehicle> vehiclesInRange = dealership.getVehiclesByPrice(minPrice, maxPrice);
            displayVehicles(vehiclesInRange);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid number for price.");
        }
    }

    //Option 2: Find by make/model
    private static void processGetByMakeModelRequest() {
        System.out.println("Option 2: Find vehicles by make and model");

        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();

        List<Vehicle> results = dealership.getVehiclesByMakeModel(make, model);
        displayVehicles(results);
    }

    //Option 3: find by year range
    private static void processGetByYearRequest() {
        System.out.println("Option 3: Find vehicles by year range");

        try {
            System.out.print("Enter start year: ");
            int min = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter end year: ");
            int max = Integer.parseInt(scanner.nextLine());

            List<Vehicle> results = dealership.getVehiclesByYear(min, max);
            displayVehicles(results);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid years.");
        }
    }

    //Option 4: find by color
    private static void processGetByColorRequest() {
        System.out.println("Option 4: Find vehicles by color");

        System.out.print("Enter color: ");
        String color = scanner.nextLine();

        List<Vehicle> results = dealership.getVehiclesByColor(color);
        displayVehicles(results);
    }

    //Option 5: Find by mileage range
    private static void processGetByMileageRequest() {
        System.out.println("Option 5: Find vehicles by mileage range");

        try {
            System.out.print("Enter minimum mileage: ");
            int min = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter maximum mileage: ");
            int max = Integer.parseInt(scanner.nextLine());

            List<Vehicle> results = dealership.getVehiclesByMileage(min, max);
            displayVehicles(results);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid mileage numbers.");
        }
    }

    //Option 6: find by type
    private static void processGetByTypeRequest() {
        System.out.println("Option 6: Find vehicles by type");

        System.out.print("Enter vehicle type (car, truck, SUV, van): ");
        String type = scanner.nextLine();

        List<Vehicle> results = dealership.getVehiclesByType(type);
        displayVehicles(results);
    }


    // Option 7: Display all vehicles
    private static void processGetAllVehiclesRequest() {
        List<Vehicle> allVehicles = dealership.getAllVehicles();
        displayVehicles(allVehicles);
    }

    // Display list of vehicles
    private static void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
        } else {
            for (Vehicle vehicle : vehicles) {
                System.out.println(vehicle);
            }
        }
    }

    // Option 8: Add vehicle
    private static void addVehicleProcess() {
        try {
            System.out.print("Enter VIN number: ");
            int vin = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter year: ");
            int year = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter make: ");
            String make = scanner.nextLine();

            System.out.print("Enter model: ");
            String model = scanner.nextLine();

            System.out.print("Enter type of car: ");
            String type = scanner.nextLine();

            System.out.print("Enter color: ");
            String color = scanner.nextLine();

            System.out.print("Enter mileage: ");
            int odometer = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter price: ");
            double price = Double.parseDouble(scanner.nextLine());

            Vehicle newVehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);
            dealership.addVehicle(newVehicle);

            DealershipFileManager fileManager = new DealershipFileManager();
            fileManager.saveDealership(dealership, "src/main/resources/inventory.csv");

            System.out.println("\n-----Vehicle added to inventory!-----");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Operation cancelled.");
        }
    }

    // remove vehicle
    private static void removeVehicleProcess() {
        try {
            System.out.print("Enter VIN of vehicle to remove: ");
            int vin = Integer.parseInt(scanner.nextLine());

            Vehicle vehicleToRemove = null;
            for (Vehicle v : dealership.getAllVehicles()) {
                if (v.getVin() == vin) {
                    vehicleToRemove = v;
                    break;
                }
            }

            if (vehicleToRemove != null) {
                dealership.removeVehicle(vehicleToRemove);
                DealershipFileManager fileManager = new DealershipFileManager();
                fileManager.saveDealership(dealership, "src/main/resources/inventory.csv");
                System.out.println("Vehicle removed successfully.");
            } else {
                System.out.println("Vehicle with VIN " + vin + " not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid VIN input.");
        }
    }

    // sellorlease method
    private void processSaleOrLease() {
        ContractFileManager contractFileManager = new ContractFileManager();

        System.out.print("Enter VIN of vehicle to sell or lease: ");
        int vin = scanner.nextInt();
        scanner.nextLine();

        Vehicle selectedVehicle = null;
        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVin() == vin) {
                selectedVehicle = v;
                break;
            }
        }

        if (selectedVehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        System.out.print("Enter contract date (YYYYMMDD): ");
        String date = scanner.nextLine();

        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter customer email: ");
        String customerEmail = scanner.nextLine();

        System.out.print("Is this a Sale or Lease? (Enter SALE or LEASE): ");
        String type = scanner.nextLine().toUpperCase();

        Contract contract = null;

        if (type.equals("SALE")) {
            System.out.println("Is it financing the purchase? (yes/no): ");
            String userFinanceChoice = scanner.nextLine().trim().toLowerCase();

            boolean finance = userFinanceChoice.equals("yes");

            contract = new SalesContract(date, customerName, customerEmail, selectedVehicle, finance);

        } else if (type.equals("LEASE")) {
            contract = new LeaseContract(date, customerName, customerEmail, selectedVehicle);
        }

        contractFileManager.saveContract(contract);
        dealership.removeVehicle(selectedVehicle);
        DealershipFileManager fileManager = new DealershipFileManager();
        fileManager.saveDealership(dealership, "src/main/resources/inventory.csv");


        System.out.println("Contract created and vehicle removed from inventory.");
    }
}