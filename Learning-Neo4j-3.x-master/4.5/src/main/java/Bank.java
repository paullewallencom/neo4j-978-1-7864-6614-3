import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;
    private Employee employee;
    private int accountNumber;

    public Bank() {
        accountNumber = 3400;
        employee = new Employee("admin", "admin123");
        customers = new ArrayList<Customer>();
    }

    public int generateAccountNumber() {
        return accountNumber++;
    }

    public boolean validateEmployee(String username, String password) {
        if(employee.getUsername().equals(username) && employee.getPassword().equals(password))
            return true;
        return false;
    }

    public void createAccount(String fullName, String dateOfBirth, String phoneNumber, double initialAmount) {
        int accNumber = generateAccountNumber();
        Account account = new Account(initialAmount, accNumber);
        customers.add(new Customer(fullName, dateOfBirth, phoneNumber, account));

        System.out.println("Your account is successfully created under " + accNumber + "!");
    }

    public void deleteAccount(int accNumber) {
        int flag = 0;
        if(accNumber > 0) {
            for (int i = 0; i < customers.size(); i++) {
                if(customers.get(i).getAccount().getAccountNumber() == accNumber) {
                    customers.remove(i);
                    flag = 1;
                }
            }
        }
        if(flag == 0) {
           System.out.println("Customer Account not Found...");
        }
        else {
            System.out.println("Customer Account successfully deleted from Bank...");
        }
    }

    public boolean searchAccount(int accNumber) {
        System.out.println("Size: " + customers.size() + "!");
        for (Customer customer : customers) {
            if(customer.getAccount().getAccountNumber() == accNumber) {
                return true;
            }
        }
        return false;
    }

    public void updateAccountDetails(String phoneNumber, int accNumber) {
        for (Customer customer : customers) {
            if(customer.getAccount().getAccountNumber() == accNumber) {
                customer.setPhoneNumber(phoneNumber);
                System.out.println("Account details Successfully updated!");
                return;
            }
        }
    }

    public void printCustomerAccountDetails(int accNum) {
        for (Customer customer : customers) {
            if(customer.getAccount().getAccountNumber() == accNum) {
                System.out.println("******************************************");
                System.out.println("*         CUSTOMER ACCOUNT DETAILS       *");
                System.out.println("******************************************");
                System.out.println("Customer Account Number: " + customer.getAccount().getAccountNumber());
                System.out.println("Customer Name: " + customer.getFullName());
                System.out.println("Customer DOB: " + customer.getDateOfBirth());
                System.out.println("Customer Ph# " + customer.getPhoneNumber());
                System.out.println("Customer's Balance: " + customer.getAccount().getAmount());
                System.out.println("------------------------------------------");
                return;
            }
        }
        System.out.println("Customer not found...!");
    }

    public void depositAmount(double amount, int accNum) {
        for (Customer customer : customers) {
            customer.getAccount().setAmount((customer.getAccount().getAmount() + amount));
            if(customer.getAccount().getAccountNumber() == accNum) {
                System.out.println("******************************************");
                System.out.println("*         CUSTOMER ACCOUNT DETAILS       *");
                System.out.println("******************************************");
                System.out.println("Customer Account Number: " + customer.getAccount().getAccountNumber());
                System.out.println("Customer Name: " + customer.getFullName());
                System.out.println("Customer DOB: " + customer.getDateOfBirth());
                System.out.println("Customer Ph# " + customer.getPhoneNumber());
                System.out.println("Customer's New Balance: " + customer.getAccount().getAmount());
                System.out.println("------------------------------------------");
                return;
            }
        }
        System.out.println("Customer not found...!");
    }

    public void withdrawAmount(double amount, int accNum) {
        for (Customer customer : customers) {
            customer.getAccount().setAmount((customer.getAccount().getAmount() - amount));
            if(customer.getAccount().getAccountNumber() == accNum) {
                System.out.println("******************************************");
                System.out.println("*         CUSTOMER ACCOUNT DETAILS       *");
                System.out.println("******************************************");
                System.out.println("Customer Account Number: " + customer.getAccount().getAccountNumber());
                System.out.println("Customer Name: " + customer.getFullName());
                System.out.println("Customer DOB: " + customer.getDateOfBirth());
                System.out.println("Customer Ph# " + customer.getPhoneNumber());
                System.out.println("Customer's New Balance: " + customer.getAccount().getAmount());
                System.out.println("------------------------------------------");
                return;
            }
        }
    }
}
