import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;
    private Employee employee;
    private int accountNumber;

    public Bank() {
        accountNumber = 342400;
        employee = new Employee("admin", "admin123");
        customers = new ArrayList<Customer>();
    }

    public boolean validateEmployee(String username, String password) {
        if(employee.getUsername().equals(username) && employee.getPassword().equals(password))
            return true;
        return false;
    }

    public void createAccount() {
        /* To be continued in next video */
    }

    public void deleteAccount() {
        /* To be continued in next video */
    }
}
