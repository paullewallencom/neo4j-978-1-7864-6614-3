public class Account {
    private double amount;
    private long accountNumber;

    public Account(double amount, long accountNumber) {
        this.amount = amount;
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    private void addAmount(double amount) {
        /* To be continued in next video */
    }
}
