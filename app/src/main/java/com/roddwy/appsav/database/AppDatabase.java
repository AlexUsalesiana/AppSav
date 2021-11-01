package com.roddwy.appsav.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.roddwy.appsav.entity.AVeterinario;
import com.roddwy.appsav.entity.User;
import com.roddwy.appsav.entityDao.AVeterinarioDao;
import com.roddwy.appsav.entityDao.UserDao;

@Database(entities = {User.class, AVeterinario.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    @SuppressWarnings("WeakerAccess")
    public abstract UserDao userDao();
    public abstract AVeterinarioDao aVeterinarioDao();

    /**
     *  The only instance
     */
    public static AppDatabase sInstance;
}
