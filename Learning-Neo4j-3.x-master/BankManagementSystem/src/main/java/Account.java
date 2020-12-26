public class Account {
    private double amount;
    private int accountNumber;

    public Account(double amount, int accountNumber) {
        this.amount = amount;
        this.accountNumber = accountNumber;
    }

    public Account() {
        this.amount = -1;
        this.accountNumber = -1;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
}
