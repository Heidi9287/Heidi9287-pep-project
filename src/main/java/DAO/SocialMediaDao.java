package DAO;

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
        List<Account> accounts=new ArrayList<>();
        try{
            String sql="SELECT * FROM account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getInt("account+id"), rs.getString("username"),
                        rs.getString("password"));
                accounts.add(account);
            }
           
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }
}
