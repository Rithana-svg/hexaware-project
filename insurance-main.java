
package mainmod;

import dao.IPolicyService;
import dao.InsuranceServiceImpl;
import entity.Policy;
import myexceptions.PolicyNotFoundException;
import java.util.Collection;

public class MainModule {
    public static void main(String[] args) {
        IPolicyService policyService = new InsuranceServiceImpl();

        try {
            
            Policy newPolicy = new Policy();
            
            boolean created = policyService.createPolicy(newPolicy);
            System.out.println("Policy created: " + created);

        
            try {
                Policy policy = policyService.getPolicy(1);
                System.out.println("Retrieved policy: " + policy);
            } catch (PolicyNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }

            
            Collection<Policy> policies = policyService.getAllPolicies();
            System.out.println("All policies: " + policies);


            try {
                Policy updatePolicy = new Policy();
                
                boolean updated = policyService.updatePolicy(updatePolicy);
                System.out.println("Policy updated: " + updated);
            } catch (PolicyNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }

        
            try {
                boolean deleted = policyService.deletePolicy(1);
                System.out.println("Policy deleted: " + deleted);
            } catch (PolicyNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}