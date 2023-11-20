package DAO;

import Model.Message;
import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SocialMediaDao {
    public Account registerAccounts(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(),
                        account.getPassword());
            }
        } catch (

        SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Account> getAllAccounts() {
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));
                accounts.add(account);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    public Message createMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by,message_text,time_posted_epoch) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int getMessage_id = (int) pkeyResultSet.getInt(1);
                return new Message(getMessage_id, message.getPosted_by(),
                        message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (

        SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (

        SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageById(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            // Write SQL logic here
            String sql = "SELECT * FROM message WHERE message_id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, message_id);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return message;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }
    public List<Message> getAllMessageByUser(int posted_by) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, posted_by);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                 messages.add(message);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return messages;
  

    }

    public Message updateMessage(int message_id, Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2, message_id);
            preparedStatement.executeUpdate();
            return message;
        } catch (

        SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

  
    }
    public Message deleteMessage(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        Message deletedMessage = getMessageById(message_id);
        if(deletedMessage!=null)
        try {
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            preparedStatement.executeUpdate();
            return deletedMessage;
        } catch (
        SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account userLogin(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql ="SELECT * FROM account WHERE username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Account retrievedAccount = new Account();
                retrievedAccount.setUsername(resultSet.getString("username"));
                retrievedAccount.setPassword(resultSet.getString("password"));
               retrievedAccount.setAccount_id(resultSet.getInt("account_id"));
                return retrievedAccount;
            } else {
                System.out.println("User not found");
            }
        }
        catch(    SQLException e){      System.out.println(e.getMessage());}
        return null;
    }
}
