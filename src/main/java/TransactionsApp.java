import java.time.LocalDate;

class TransactionsApp {
    private static final TransactionDao TRANSACTION_DAO = new TransactionDao();

    public static void main(String[] args) {
        TRANSACTION_DAO.add(new Transaction(Type.INCOME, "pożyczka z banku",
                20_000, LocalDate.of(2021, 5, 12)));
        TRANSACTION_DAO.delete(1);
        Transaction komputer = new Transaction(1, Type.EXPENSE, "kupno komputera",
                7_000, LocalDate.of(2020, 5, 18));
        TRANSACTION_DAO.update(komputer);
        TRANSACTION_DAO.displayIncomes();
        TRANSACTION_DAO.displayExpense();
        TRANSACTION_DAO.close();
    }
}
