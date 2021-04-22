import java.sql.*;

class TransactionDao {
    private final Connection connection;


    public TransactionDao() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/transakcje?serverTimezone=UTC",
                    "root", "admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void add(Transaction transaction) {
        final String sql = String.format("INSERT INTO transaction(type,description,amount,date) VALUES ('%s', '%s', %f, '%s')",
                transaction.getType(), transaction.getDescription(), transaction.getAmount(), Date.valueOf(transaction.getDate()));
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                transaction.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean delete(int id) {
        final String sql = "DELETE FROM transaction WHERE id = " + id;
        try (Statement statement = connection.createStatement()) {
            int updatedRows = statement.executeUpdate(sql);
            return updatedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean update(Transaction transaction) {
        final String sql = String.format("""
                        UPDATE
                         transaction 
                        SET type = '%s',description = '%s',amount = %f,date = '%s' 
                        WHERE id = %d""",
                transaction.getType(), transaction.getDescription(), transaction.getAmount(),
                transaction.getDate(), transaction.getId());
        try (Statement statement = connection.createStatement()) {
            int updatedRows = statement.executeUpdate(sql);
            return updatedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void displayIncomes() {
        fetchAndDisplay(Type.INCOME);
    }

    void displayExpense() {
        fetchAndDisplay(Type.EXPENSE);
    }

    private void fetchAndDisplay(Type type) {
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

    void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
