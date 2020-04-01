package me.prapon.contactsyncdemo.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import me.prapon.contactsyncdemo.model.Contact;

@androidx.room.Database(entities = {Contact.class}, version = 1)
public abstract class Database extends RoomDatabase {

    public abstract ContactDAO contactDAO();

    private static volatile Database instance;

    public static Database getInstance(final Context context) {
        if(instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, "mates")
                            .build();
                }
            }
        }
        return instance;
    }


}
