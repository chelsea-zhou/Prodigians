package com.example.prodigians.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ActRecordEntity.class}, version=1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract RecordDao recordDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            //context.getApplicationContext()
            System.out.println("created database");
            instance = Room.databaseBuilder(context,
                    AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    // can add existing records
}
