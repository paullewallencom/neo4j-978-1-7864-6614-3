import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.util.Pair;

import java.util.List;
import java.util.Scanner;

import static org.neo4j.driver.v1.Values.parameters;

public class DatabaseHelper {
    Scanner input = new Scanner(System.in);

    private Driver driver;
    private Session session;

    private String uri;
    private String username;
    private String password;

    public DatabaseHelper() {
        username = askUsername();
        password = askPassword();
        uri = "bolt://localhost:7687";
        try {
            driver = GraphDatabase.driver( uri, AuthTokens.basic( username, password) );
        }
        catch (Exception excp) {}
        createBank();
    }

    public int getMaxAccountNUmber() {
        int accountNumber = 0;

        try {
            session = driver.session();
            StatementResult result = session.run( "MATCH (a: Account) " +
                            "RETURN MAX(a.acc_num)");

            Record res = result.next();
            List<org.neo4j.driver.v1.util.Pair<String, Value>> values = res.fields();
            for (Pair<String, Value> nameValue: values) {
                if ("MAX(a.acc_num)".equals(nameValue.key())) {
                    Value value = nameValue.value();

                    System.out.print("v: " + value.get("acc_num").asInt());
                    accountNumber = value.get("acc_num").asInt();
                }
            }
        }
        catch(Exception exception) {}
        finally {
            session.close();
        }
        return accountNumber;
    }

    public void addConstraints() {
        try {
            session = driver.session();
            session.run("CREATE CONSTRAINT ON (a:Customer) ASSERT a.id IS UNIQUE ");
            session.run("CREATE CONSTRAINT ON (a:Account) ASSERT a.acc_num IS UNIQUE ");
        }
        catch(Exception exception) {}
        finally {
            session.close();
        }

    }

    private String askUsername() {
        System.out.print("Enter username: ");
        return (input.nextLine());
    }

    private String askPassword() {
        System.out.print("Enter password: ");
        return (input.nextLine());
    }

    private void createBank() {
        final String name = "Bank";

        try {
            session = driver.session();
            session.run("MERGE(:Bank {name: $name}) ",
                    parameters("name", name));
        }
        catch(Exception exception) {}
        finally {
            session.close();
        }
    }

    /************************************************************
     *               EMPLOYEE RELATED MODULES
     ************************************************************/
    public void createEmployee(Employee employee) {
        final String username = employee.getUsername();
        final String password = employee.getPassword();

        try {
            session = driver.session();

            session.run("MERGE (:Employee {username: $username, password: $password})",
                    parameters("username", username, "password", password));


            session.run("MATCH (a:Bank), (b:Employee) " +
                            "WHERE a.name = 'Bank' AND b.username = $username "+
                            "MERGE (b)-[r:Employee_Of]->(a)",
                    parameters("username", username));
        }
        catch(Exception exception) {}
        finally {
            session.close();
        }
    }

    public Employee validateEmployee(final String username, final String password) {
        Employee employee = new Employee();

        try {
            session = driver.session();
            StatementResult result = session.run( "MATCH (n: Employee) " +
                                                    "WHERE n.username = $username AND n.password = $password " +
                                                    "RETURN n",
                    parameters("username", username, "password", password));

            Record res = result.next();
            List<org.neo4j.driver.v1.util.Pair<String, Value>> values = res.fields();
            for (Pair<String, Value> nameValue: values) {
                if ("n".equals(nameValue.key())) {
                    Value value = nameValue.value();

                    employee.setUsername(value.get("username").asString());
                    employee.setPassword(value.get("password").asString());
                }
            }
        }
        catch(Exception exception) {}
        finally {
            session.close();
        }
        return employee;
    }

    public boolean deleteCustomerAccount(int customerId, int accNum) {
        int flag = 0;
        try {
            session = driver.session();

            session.run("MATCH (:Customer {id: $id})-[r:Customer_Of]-(:Bank) DELETE r",
                    parameters("id", customerId));

            session.run("MATCH (:Account {acc_num: $accNum})-[r:Account_of]-(:Customer {id: $id}) DELETE r",
                    parameters("id", customerId, "accNum", accNum));

            session.run("MATCH (a:Customer {id: $id}), (b:Account {acc_num: $accNum}) DELETE a, b",
                    parameters("id", customerId, "accNum", accNum));
            flag = 1;
        }
        catch(Exception exception) {}
        finally {
            session.close();
        }
        if(flag == 1)
            return true;
        return false;
    }

    public boolean updateCustomerAccount(String ph, int id) {
        int flag = 0;
        try {
            session = driver.session();

            session.run("MATCH (a:Customer {id: $id}) SET a.ph_num = $ph",
                    parameters("id", id, "ph", ph));
            flag = 1;
        }
        catch(Exception exception) {}
        finally {
            session.close();
        }
        if(flag == 1)
            return true;
        return false;
    }

    /************************************************************
    *               CUSTOMER RELATED MODULES
    ************************************************************/
    public void createCustomer(Customer customer) {
        final int id = customer.getCustomerId();
        final String name = customer.getFullName();
        final String dob = customer.getDateOfBirth();
        final String ph = customer.getPhoneNumber();

        final int accNumber = customer.getAccount().getAccountNumber();
        final double accBalance = customer.getAccount().getAmount();

        try {
            session = driver.session();

            session.run("CREATE (:Customer {id: $id, name: $name, date_of_birth: $dob, ph_num: $ph})",
                    parameters("id", id, "name", name, "dob", dob, "ph", ph));

            session.run("MATCH (a:Bank), (b:Customer) " +
                    "WHERE a.name = 'Bank' AND b.id = $customerId "+
                    "CREATE (b)-[r:Customer_Of]->(a)",
                    parameters("customerId", id));

            session.run("CREATE (:Account {acc_num: $acc_number, amount: $amount}) ",
                    parameters("acc_number", accNumber, "amount", accBalance));


            session.run("MATCH (a: Customer), (b: Account) " +
                            "WHERE a.id = $id AND b.acc_num = $acc_number "+
                            "CREATE (b)-[r:Account_of]->(a)",
                    parameters("id", id, "acc_number", accNumber));
        }
        catch(Exception exception) {}
        finally {
            session.close();
        }
    }


    public boolean validateCustomer(final int accNumber, final int customerId) {
        Account account = new Account();
        Customer customer = new Customer();

        try {
            session = driver.session();
            StatementResult result = session.run( "MATCH (a:Account)-[:Account_of]->(b:Customer) " +
                                                    "WHERE a.acc_num = $accNumber AND b.id = $customerId " +
                                                    "RETURN a, b",
                    parameters("accNumber", accNumber, "customerId", customerId));

            Record res = result.next();
            List<org.neo4j.driver.v1.util.Pair<String, Value>> values = res.fields();
            for (Pair<String, Value> nameValue: values) {
                if("a".equals(nameValue.key())) {
                    Value v = nameValue.value();
                    account.setAccountNumber(v.get("acc_num").asInt());
                    System.out.println("Account Number: " + v.get("acc_num").asInt());
                }

                if ("b".equals(nameValue.key())) {
                    Value value = nameValue.value();
                    customer.setCustomerId(value.get("id").asInt());
                    System.out.println("Customer ID: " + value.get("id").asInt());
                }
            }
        }
        catch(Exception exception) {}
        finally {
            session.close();
        }

        if(customer.getCustomerId() == customerId && account.getAccountNumber() == accNumber) {
            return true;
        }
        return false;
    }

    public void depositAmount(int accNum, int id, double amount) {
        try {
            session = driver.session();
            session.run( "MATCH (a:Account)-[:Account_of]->(b:Customer) " +
                            "WHERE a.acc_num = $accNumber AND b.id = $customerId " +
                            "SET a.amount = (a.amount + $amount)",
                    parameters("accNumber", accNum, "customerId", id, "amount", amount));
        }
        catch(Exception exception) {}
        finally {
            session.close();
            printCustomerDetails(id, accNum);
        }
    }

    public void withdrawAmount(int accNum, int id, double amount) {
        try {
            session = driver.session();
            session.run( "MATCH (a:Account)-[:Account_of]->(b:Customer) " +
                            "WHERE a.acc_num = $accNumber AND b.id = $customerId " +
                            "SET a.amount = (a.amount - $amount)",
                    parameters("accNumber", accNum, "customerId", id, "amount", amount));
        }
        catch(Exception exception) {}
        finally {
            session.close();
            printCustomerDetails(id, accNum);
        }
    }

    public void printCustomerDetails(int customerId, int accNumber) {
        try {
            session = driver.session();
            StatementResult result = session.run( "MATCH (a:Account)-[:Account_of]->(b:Customer) " +
                            "WHERE a.acc_num = $accNumber AND b.id = $customerId " +
                            "RETURN a, b",
                    parameters("accNumber", accNumber, "customerId", customerId));

            Record res = result.next();
            List<org.neo4j.driver.v1.util.Pair<String, Value>> values = res.fields();

            System.out.println("******************************************");
            System.out.println("*         CUSTOMER ACCOUNT DETAILS       *");
            System.out.println("******************************************");
            for (Pair<String, Value> nameValue: values) {
                int accNum = 0;
                double amount = 0.0;

                if ("b".equals(nameValue.key())) {
                    Value value = nameValue.value();
                    System.out.println("Customer ID: " + value.get("id").asInt());
                    System.out.println("Customer Name: " + value.get("name").asString());
                    System.out.println("Customer DOB: " + value.get("date_of_birth").asString());
                    System.out.println("Customer Ph# " + value.get("ph_num").asString());
                }
                if("a".equals(nameValue.key())) {
                    Value v = nameValue.value();

                    System.out.println("Customer Account Number: " + v.get("acc_num").asInt());
                    System.out.println("Customer's New Balance: " + v.get("amount").asDouble());
                }
                System.out.println("------------------------------------------");

            }
        }
        catch(Exception exception) {}
        finally {
            session.close();
        }
    }
}
