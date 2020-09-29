package dao;

import dto.Account;
import java.util.ArrayList;

public interface IAccountDao {
    public Account getUserById(int user_id);
    public Account getUserByServiceId(int serviceId);
    public int checkUserByEmailPassword(Account a);
    public int updatePassword(String username, String oldPassword, String newPassword);
    public int createNewAccount(Account a);
    public boolean isEmailUnique(String email);
    public int getUserStatus(int user_id);
    public ArrayList<Account> getAllusers();
    public int updateUserStatus(int user_id, int new_status);
    public int updateUser(Account user);
}
