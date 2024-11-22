package mainmod;

import dao.IPolicyService;
import dao.InsuranceServiceImpl;
import entity.*;
import myexceptions.PolicyNotFoundException;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainModule {
    private static IPolicyService policyService = new InsuranceServiceImpl();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try {
                displayMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        createNewPolicy();
                        break;
                    case 2:
                        viewPolicy();
                        break;
                    case 3:
                        viewAllPolicies();
                        break;
                    case 4:
                        updateExistingPolicy();
                        break;
                    case 5:
                        deleteExistingPolicy();
                        break;
                    case 6:
                        System.out.println("Exiting the application. Goodbye!");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Insurance Management System ===");
        System.out.println("1. Create New Policy");
        System.out.println("2. View Policy");
        System.out.println("3. View All Policies");
        System.out.println("4. Update Policy");
        System.out.println("5. Delete Policy");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void createNewPolicy() {
        try {
            System.out.println("\n=== Create New Policy ===");
            
            
            Policy newPolicy = getPolicyDetailsFromUser();
            
            
            boolean created = policyService.createPolicy(newPolicy);
            
            if (created) {
                System.out.println("Policy created successfully!");
            } else {
                System.out.println("Failed to create policy.");
            }
        } catch (Exception e) {
            System.out.println("Error creating policy: " + e.getMessage());
        }
    }

    private static void viewPolicy() {
        try {
            System.out.println("\n=== View Policy ===");
            System.out.print("Enter Policy ID: ");
            int policyId = scanner.nextInt();
            scanner.nextLine(); 

            try {
                Policy policy = policyService.getPolicy(policyId);
                displayPolicyDetails(policy);
            } catch (PolicyNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error retrieving policy: " + e.getMessage());
        }
    }

    private static void viewAllPolicies() {
        try {
            System.out.println("\n=== All Policies ===");
            Collection<Policy> policies = policyService.getAllPolicies();
            
            if (policies.isEmpty()) {
                System.out.println("No policies found.");
                return;
            }

            for (Policy policy : policies) {
                displayPolicyDetails(policy);
                System.out.println("------------------------");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving policies: " + e.getMessage());
        }
    }

    private static void updateExistingPolicy() {
        try {
            System.out.println("\n=== Update Policy ===");
            System.out.print("Enter Policy ID to update: ");
            int policyId = scanner.nextInt();
            scanner.nextLine(); 

            
            try {
                Policy existingPolicy = policyService.getPolicy(policyId);
                System.out.println("Current Policy Details:");
                displayPolicyDetails(existingPolicy);

                
                Policy updatedPolicy = getPolicyDetailsFromUser();
                updatedPolicy.setPolicyId(policyId);

                boolean updated = policyService.updatePolicy(updatedPolicy);
                if (updated) {
                    System.out.println("Policy updated successfully!");
                } else {
                    System.out.println("Failed to update policy.");
                }
            } catch (PolicyNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error updating policy: " + e.getMessage());
        }
    }

    private static void deleteExistingPolicy() {
        try {
            System.out.println("\n=== Delete Policy ===");
            System.out.print("Enter Policy ID to delete: ");
            int policyId = scanner.nextInt();
            scanner.nextLine();

            try {
                boolean deleted = policyService.deletePolicy(policyId);
                if (deleted) {
                    System.out.println("Policy deleted successfully!");
                } else {
                    System.out.println("Failed to delete policy.");
                }
            } catch (PolicyNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error deleting policy: " + e.getMessage());
        }
    }

    private static Policy getPolicyDetailsFromUser() {
        Policy policy = new Policy();

        System.out.print("Enter Policy Type: ");
        policy.setPolicyType(scanner.nextLine());

        System.out.print("Enter Coverage Amount: ");
        policy.setCoverageAmount(scanner.nextDouble());
        scanner.nextLine(); 

        System.out.print("Enter Premium Amount: ");
        policy.setPremiumAmount(scanner.nextDouble());
        scanner.nextLine(); 

        
        policy.setStartDate(new Date());

        Date endDate = new Date();
        endDate.setYear(endDate.getYear() + 1);
        policy.setEndDate(endDate);

        return policy;
    }

    private static void displayPolicyDetails(Policy policy) {
        System.out.println("Policy ID: " + policy.getPolicyId());
        System.out.println("Type: " + policy.getPolicyType());
        System.out.println("Coverage Amount: $" + policy.getCoverageAmount());
        System.out.println("Premium Amount: $" + policy.getPremiumAmount());
        System.out.println("Start Date: " + policy.getStartDate());
        System.out.println("End Date: " + policy.getEndDate());
    }
}