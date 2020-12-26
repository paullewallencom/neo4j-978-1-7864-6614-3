import java.util.Scanner;

class Neo4jDriver {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int choice = -1;
        Bank bank = new Bank();

        do {
            System.out.println("**********************************************");
            System.out.println("**          BANK MANAGEMENT SYSTEM          **");
            System.out.println("**********************************************");
            System.out.println("1. Login as Bank Employee");
            System.out.println("2. Login as Bank Customer");
            System.out.println("0. Exit from the System");
            System.out.println("----------------------------------------------");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(input.nextLine());

            switch (choice) {
                case 1:
                {
                    String username, password;
                    do {
                        System.out.println("***********************************");
                        System.out.println("**     EMPLOYEE VERIFICATION     **");
                        System.out.println("***********************************");
                        System.out.print("Enter the username: ");
                        username = input.nextLine();
                        System.out.print("Enter the password: ");
                        password = input.nextLine();
                        System.out.println("-----------------------------------");
                    }while(!bank.validateEmployee(username, password));
                    System.out.println("Successfully Logged in...");
                    break;
                }
                case 2:
                {
                    break;
                }
                case 0:
                {
                    break;
                }
                default:
                {
                    System.out.println("Please make a valid selection!");
                }
            }
        } while(choice != 0);
    }
}