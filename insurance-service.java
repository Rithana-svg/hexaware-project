

package dao;

import entity.Policy;
import java.util.Collection;
import myexceptions.PolicyNotFoundException;

public interface IPolicyService {
    boolean createPolicy(Policy policy);
    Policy getPolicy(int policyId) throws PolicyNotFoundException;
    Collection<Policy> getAllPolicies();
    boolean updatePolicy(Policy policy) throws PolicyNotFoundException;
    boolean deletePolicy(int policyId) throws PolicyNotFoundException;
package dao;

import entity.Policy;
import util.DBConnection;
import myexceptions.PolicyNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class InsuranceServiceImpl implements IPolicyService {
    
    @Override
    public boolean createPolicy(Policy policy) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO Policies (policyId, policyType, coverageAmount, premiumAmount, startDate, endDate) " +
                "VALUES (?, ?, ?, ?, ?, ?)")) {
            
            pstmt.setInt(1, policy.getPolicyId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Policy getPolicy(int policyId) throws PolicyNotFoundException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                "SELECT * FROM Policies WHERE policyId = ?")) {
            
            pstmt.setInt(1, policyId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Policy policy = new Policy();
                
                return policy;
            } else {
                throw new PolicyNotFoundException("Policy not found with ID: " + policyId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PolicyNotFoundException("Error retrieving policy: " + e.getMessage());
        }
    }

    @Override
    public Collection<Policy> getAllPolicies() {
        Collection<Policy> policies = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Policies")) {
            
            while (rs.next()) {
                Policy policy = new Policy();
                // Set policy fields from ResultSet
                policies.add(policy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return policies;
    }

    @Override
    public boolean updatePolicy(Policy policy) throws PolicyNotFoundException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE Policies SET policyType = ?, coverageAmount = ?, premiumAmount = ?, " +
                "startDate = ?, endDate = ? WHERE policyId = ?")) {
            
            
            pstmt.setInt(6, policy.getPolicyId());
            
            if (pstmt.executeUpdate() == 0) {
                throw new PolicyNotFoundException("Policy not found with ID: " + policy.getPolicyId());
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletePolicy(int policyId) throws PolicyNotFoundException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                "DELETE FROM Policies WHERE policyId = ?")) {
            
            pstmt.setInt(1, policyId);
            
            if (pstmt.executeUpdate() == 0) {
                throw new PolicyNotFoundException("Policy not found with ID: " + policyId);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
