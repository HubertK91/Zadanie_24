import java.time.LocalDate;
import java.util.Date;

class Transaction {
    private Integer id;
    private Type type;
    private String description;
    private double amount;
    private LocalDate date;

    public Transaction(Type type, String description, double amount, LocalDate date) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public Transaction(Integer id, Type type, String description, double amount, LocalDate date) {
        this(type, description, amount, date);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "id transakcji: " + id + ", opis transakcji: " + description + ", kwota transakcji: "
                + amount + "zł, data transakcji: " + date;
    }
}
