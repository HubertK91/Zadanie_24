import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

class TransactionsApp {
    private static final TransactionDao TRANSACTION_DAO = new TransactionDao();
    private static Connection connection;

    public static void main(String[] args) {
        boolean optionExit = true;
        while (optionExit) {
            System.out.println("Wybierz opcję:");
            System.out.println("1 - dodawanie transakcji");
            System.out.println("2 - modyfikacja transakcji");
            System.out.println("3 - usuwanie transakcji");
            System.out.println("4 - wyświetlanie wszystkich przychodów");
            System.out.println("5 - wyświetlanie wszystkich wydatków");
            System.out.println("6 - wyjście z programu");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    TRANSACTION_DAO.add(new Transaction(Type.INCOME, "pożyczka z banku",
                            20_000, LocalDate.of(2021, 5, 12)));
                    break;
                case "2":
                    Transaction komputer = new Transaction(1, Type.EXPENSE, "kupno komputera",
                            7_000, LocalDate.of(2020, 5, 18));
                    TRANSACTION_DAO.update(komputer);
                    break;
                case "3":
                    TRANSACTION_DAO.delete(1);
                    break;
                case "4":
                    displayIncomes();
                    break;
                case "5":
                    displayExpense();
                    break;
                case "6":
                    optionExit = false;
                    TRANSACTION_DAO.close();
                    break;
                default:
                    System.out.println("Nieznana opcja!");
            }
        }
    }

    private static void displayIncomes() {
        fetchAndDisplay(Type.INCOME);
    }

    private static void displayExpense() {
        fetchAndDisplay(Type.EXPENSE);
    }

    private static void fetchAndDisplay(Type type) {
        String sql = "SELECT * FROM transaction WHERE type = ?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, String.valueOf(type));
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("description");
                double amount = resultSet.getDouble("amount");
                Date date = resultSet.getDate("date");

                System.out.println("id transakcji: " + id + ", opis transakcji: " + description + ", kwota transakcji: "
                        + amount + "zł, data transakcji: " + date);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
