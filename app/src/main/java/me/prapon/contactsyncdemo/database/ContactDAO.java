package me.prapon.contactsyncdemo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import me.prapon.contactsyncdemo.model.Contact;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ContactDAO {

    @Insert(entity = Contact.class, onConflict = REPLACE)
    void insert(Contact contact);

    @Update(entity = Contact.class)
    void update(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("SELECT * FROM contacts ORDER BY name")
    LiveData<List<Contact>> load();
}
