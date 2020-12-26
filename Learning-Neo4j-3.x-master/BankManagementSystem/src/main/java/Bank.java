import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    DatabaseHelper dbHelper;
    private int accountNumber;

    public Bank() {
        dbHelper = new DatabaseHelper();

        dbHelper.createEmployee(new Employee("admin", "admin123"));
        accountNumber = 3400;
        dbHelper.addConstraints();
    }

    public int generateAccountNumber() {
        return accountNumber++;
    }

    public boolean validateEmployee(String username, String password) {
        Employee employee = dbHelper.validateEmployee(username, password);
        if(employee.getUsername().equals(username) && employee.getPassword().equals(password))
            return true;
        return false;
    }

    public void createAccount(int id, String fullName, String dateOfBirth, String phoneNumber, double initialAmount) {
        int accNumber = generateAccountNumber();
        Account account = new Account(initialAmount, accNumber);
        dbHelper.createCustomer(new Customer(id, fullName, dateOfBirth, phoneNumber, account));

        System.out.println("Your account is successfully created under " + accNumber + "!");
    }

    public void deleteAccount(int id, int accNumber) {
        int flag = 0;
        if(dbHelper.deleteCustomerAccount(id, accNumber)) {
            flag = 1;
        }

        if(flag == 0)
            System.out.println("Customer Account not Found...");
        else
            System.out.println("Customer Account successfully deleted from Bank...");
    }

    public boolean searchAccount(int customerId, int accNumber) {
        if(dbHelper.validateCustomer(accNumber, customerId)) {
            return true;
        }
        return false;
    }

    public void updateAccountDetails(String phoneNumber, int id) {
        if(dbHelper.updateCustomerAccount(phoneNumber, id))
        System.out.println("Account details Successfully updated!");
    }

    public void printCustomerAccountDetails(int id, int accNum) {
        dbHelper.printCustomerDetails(id, accNum);
    }

    public void depositAmount(double amount, int accNum, int id) {
        dbHelper.depositAmount(accNum, id, amount);
    }

    public void withdrawAmount(double amount, int accNum, int id) {
        dbHelper.withdrawAmount(accNum, id, amount);
    }
}
