package me.prapon.contactsyncdemo.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "phone")
    private String phoneNumber;

    public Contact(@NonNull String name, @NonNull String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
