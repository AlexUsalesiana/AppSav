package com.roddwy.appsav.entityDao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.roddwy.appsav.entity.AVeterinario;

import java.util.List;

@Dao
public interface AVeterinarioDao {

    @Query("SELECT COUNT(*) FROM "+ AVeterinario.TABLE_NAME)
    int count();

    @Query("SELECT * FROM "+ AVeterinario.TABLE_NAME)
    List<AVeterinario> getAllAVeterinario();

    @Insert
    void insertAll(AVeterinario ... aVeterinarios);

    @Query("DELETE FROM "+ AVeterinario.TABLE_NAME + " WHERE " + AVeterinario.COLUMN_ID + " = :id")
    int deleteById(long id);

    @Update
    int updateAVeterianrio(AVeterinario obj);

    @Insert
    long insert(AVeterinario aVeterinarios);
}
