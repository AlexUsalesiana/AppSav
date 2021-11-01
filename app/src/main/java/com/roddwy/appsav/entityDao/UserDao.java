package com.roddwy.appsav.entityDao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.roddwy.appsav.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT COUNT(*) FROM "+ User.TABLE_NAME)
    int count();

    @Query("SELECT * FROM "+User.TABLE_NAME)
    List<User> getAllUsers();

    @Insert
    void insertAll(User ... users);

    @Query("DELETE FROM "+ User.TABLE_NAME + " WHERE " + User.COLUMN_ID + " = :id")
    int deleteById(long id);

    @Update
    int updateUser(User obj);

    @Insert
    long insert(User users);

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    int getUserLogin(String username,String password);

    @Query("SELECT * FROM user WHERE ci = :ci")
    int getUserByCi(String ci);

}
