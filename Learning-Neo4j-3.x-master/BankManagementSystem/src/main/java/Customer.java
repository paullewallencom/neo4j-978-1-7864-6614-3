public class Customer {
    private int customerId;
    private String fullName;
    private String dateOfBirth;
    private String phoneNumber;
    Account account;

    public Customer() {
        this.customerId = -1;
        this.fullName = "";
        this.dateOfBirth = "";
        this.phoneNumber = "";
        this.account = null;
    }

    public Customer(int customerId, String fullName, String dateOfBirth, String phoneNumber, Account account) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.account = account;
    }


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
