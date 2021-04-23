import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

class TransactionsApp {
    private static final TransactionDao TRANSACTION_DAO = new TransactionDao();
    private static Scanner scanner;

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
            scanner = new Scanner(System.in);
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    TRANSACTION_DAO.add(new Transaction(Type.INCOME, "pożyczka z banku",
                            20_000.00, LocalDate.of(2021, 5, 12)));
                    break;
                case "2":
                    Transaction computer = new Transaction(2, Type.EXPENSE, "kupno komputera",
                            7_000.00, LocalDate.of(2020, 5, 18));
                    boolean update = TRANSACTION_DAO.update(computer);
                    if (update) {
                        System.out.println("Pomyślnie zmodyfikowano transakcje");
                    } else {
                        System.out.println("Nie zmodyfikowano transakcji");
                    }
                    break;
                case "3":
                    delete();
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


    private static void delete() {
        System.out.println("Podaj id transakcji do usunięcia");
        int id = scanner.nextInt();
        boolean delete = TRANSACTION_DAO.delete(id);
        if (delete) {
            System.out.println("Pomyślnie usunięto transakcje");
        } else {
            System.out.println("Nie ma transakcji o podanym id");
        }
    }

    private static void displayIncomes() {
        List<Transaction> incomesTransactions = TRANSACTION_DAO.fetchAndDisplay(Type.INCOME);
        for (Transaction transaction : incomesTransactions) {
            System.out.println(transaction);
        }
    }

    private static void displayExpense() {
        List<Transaction> expenseTransactions = TRANSACTION_DAO.fetchAndDisplay(Type.EXPENSE);
        for (Transaction transaction : expenseTransactions) {
            System.out.println(transaction);
        }
    }
}


