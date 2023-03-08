package org.example.repository;

import org.example.container.ComponentContainer;
import org.example.db.DataBase;
import org.example.dto.Card;
import org.example.dto.Terminal;
import org.example.dto.Transaction;
import org.example.enums.TransactionType;
import org.example.model.Model;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class TransactionRepository {

    public int createTransaction(Transaction transaction) {
        try (Connection connection = DataBase.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "insert into transaction(card_id,terminal_id,amount,type,created_date) " +
                            "values (?,?,?,?,?)");
            statement.setInt(1, transaction.getCardId());

            if (transaction.getTerminalId() != null) {
                statement.setInt(2, transaction.getTerminalId());
            } else {
                statement.setObject(2, null);
            }

            statement.setDouble(3, transaction.getAmount());
            statement.setString(4, transaction.getTransactionType().name());
            statement.setTimestamp(5, Timestamp.valueOf(transaction.getCreatedDate()));

            int resultSet = statement.executeUpdate();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;

    }

    public List<Transaction> transactionList() {
        List<Transaction> transactionList = new LinkedList<>();
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from transaction");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(resultSet.getInt("id"));
                transaction.setCardId(resultSet.getInt("card_id"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setTerminalId(resultSet.getInt("terminal_id"));
                transaction.setTransactionType(TransactionType.valueOf(resultSet.getString("type")));
                transaction.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    public List<Transaction> todayTransactionList() {
        List<Transaction> transactionList = new LinkedList<>();
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from transaction where created_date=?");
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ResultSet resultSet = preparedStatement.executeQuery();
            Transaction transaction = new Transaction();
            while (resultSet.next()) {
                transaction.setId(resultSet.getInt("id"));
                transaction.setCardId(resultSet.getInt("card_id"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setTerminalId(resultSet.getInt("terminal_id"));
                transaction.setTransactionType(TransactionType.valueOf(resultSet.getString("type")));
                transaction.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    public List<Transaction> transactionByDay(String date) {
        List<Transaction> transactionList = new LinkedList<>();
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from transaction where date(created_date) = date(now()) order by id desc");
            ResultSet resultSet = preparedStatement.executeQuery();
            Transaction transaction = new Transaction();
            while (resultSet.next()) {
                transaction.setId(resultSet.getInt("id"));
                transaction.setCardId(resultSet.getInt("card_id"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setTerminalId(resultSet.getInt("terminal_id"));
                transaction.setTransactionType(TransactionType.valueOf(resultSet.getString("type")));
                transaction.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    public List<Transaction> transactionBetweenDays(String fromDate, String toDate) {
        List<Transaction> transactionList = new LinkedList<>();
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from transaction where created_date > ? and created_date < ? order by id desc");
            preparedStatement.setDate(1, Date.valueOf(fromDate));
            preparedStatement.setDate(2, Date.valueOf(toDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            Transaction transaction = new Transaction();
            while (resultSet.next()) {
                transaction.setId(resultSet.getInt("id"));
                transaction.setCardId(resultSet.getInt("card_id"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setTerminalId(resultSet.getInt("terminal_id"));
                transaction.setTransactionType(TransactionType.valueOf(resultSet.getString("type")));
                transaction.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    public List<Transaction> transactionByTerminal(Integer number) {
        List<Transaction> transactionList = new LinkedList<>();
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from transaction where terminal_id =? order by id desc");
            preparedStatement.setInt(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            Transaction transaction = new Transaction();
            while (resultSet.next()) {
                transaction.setId(resultSet.getInt("id"));
                transaction.setCardId(resultSet.getInt("card_id"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setTerminalId(resultSet.getInt("terminal_id"));
                transaction.setTransactionType(TransactionType.valueOf(resultSet.getString("type")));
                transaction.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    public List<Transaction> transactionByCard(Integer id) {
        List<Transaction> transactionList = new LinkedList<>();
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from transaction where card_id =? order by id desc");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Transaction transaction = new Transaction();
            while (resultSet.next()) {
                transaction.setId(resultSet.getInt("id"));
                transaction.setCardId(resultSet.getInt("card_id"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setTerminalId(resultSet.getInt("terminal_id"));
                transaction.setTransactionType(TransactionType.valueOf(resultSet.getString("type")));
                transaction.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    public void makePayment(Card card, Integer terminalId) {
        try {
            Connection connection = DataBase.getConnection();
            ComponentContainer.cardRepository.rechargeBalance(card.getId(), card.getBalance() - 1400.0);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into transaction(card_id,amount,terminal_id,type,created_date) value(?,?,?,?,now())");
            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setDouble(2, 1400.0);
            preparedStatement.setInt(3, terminalId);
            preparedStatement.setString(4, TransactionType.Payment.name());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Model> transactionList(Card card) {
        List<Model> transactionList = new LinkedList<>();
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from transaction where card_id=?");
            preparedStatement.setInt(1, card.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(resultSet.getInt("id"));
                transaction.setCardId(resultSet.getInt("card_id"));
                transaction.setTransactionType(TransactionType.valueOf(resultSet.getString("type")));
                transaction.setAmount(Double.valueOf(resultSet.getString("amount")));
                transaction.setTerminalId(resultSet.getInt("terminal_id"));
                transaction.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                Terminal terminal = ComponentContainer.terminalRepository.getTerminalById(transaction.getTerminalId());
                Model model = new Model(transaction, terminal, card);
                transactionList.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }
}
