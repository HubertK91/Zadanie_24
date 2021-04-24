import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        final String sql = "INSERT INTO transaction(type,description,amount,date) VALUES (?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, String.valueOf(transaction.getType()));
            statement.setString(2, transaction.getDescription());
            statement.setDouble(3, transaction.getAmount());
            statement.setDate(4, Date.valueOf(transaction.getDate()));
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                transaction.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean delete(int id) {
        final String sql = "DELETE FROM transaction WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int updatedRows = statement.executeUpdate();
            return updatedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean update(Transaction transaction) {
        final String sql = """
                UPDATE
                 transaction 
                SET type = ?,description = ?,amount = ?,date = ? 
                WHERE id = ?""";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, String.valueOf(transaction.getType()));
            statement.setString(2, transaction.getDescription());
            statement.setDouble(3, transaction.getAmount());
            statement.setDate(4, Date.valueOf(transaction.getDate()));
            statement.setInt(5, transaction.getId());
            int updatedRows = statement.executeUpdate();
            return updatedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    List<Transaction> fetchAndDisplay(Type type) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transaction WHERE type = ?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, String.valueOf(type));
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("description");
                double amount = resultSet.getDouble("amount");
                Date date = resultSet.getDate("date");
                transactions.add(new Transaction(id, type, description, amount, date.toLocalDate()));
            }
            return transactions;
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
