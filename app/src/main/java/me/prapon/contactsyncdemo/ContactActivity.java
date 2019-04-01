package me.prapon.contactsyncdemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import me.prapon.contactsyncdemo.adapter.ContactAdapter;
import me.prapon.contactsyncdemo.database.Constants;
import me.prapon.contactsyncdemo.database.DatabaseHelper;
import me.prapon.contactsyncdemo.model.Contact;
import me.prapon.contactsyncdemo.sevices.SyncDatabaseService;

public class ContactActivity extends AppCompatActivity {

    ArrayList<Contact> contactlist;
    ArrayList<Contact> newContacts;
    Contact contact;
    ContactAdapter adapter;

    SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    Cursor cursor;

    RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contactlist = new ArrayList<>();

        databaseHelper = new DatabaseHelper(this);
        contactlist = readFromDatabase();
        adapter = new ContactAdapter(getApplicationContext(), contactlist);


        recyclerView = findViewById(R.id.recycler_View);
        swipeRefreshLayout = findViewById(R.id.simpleSwipeRefreshLayout);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // implement Handler to wait for 2 seconds and then update UI means update value of list
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                        Intent intent = new Intent(ContactActivity.this, SyncDatabaseService.class);
                        startService(intent);

                        adapter.notifyDataSetChanged();


                    }
                }, 2000);
            }
        });

    }

    public ArrayList<Contact> readFromDatabase() {

        cursor = databaseHelper.getAllContacts();

        newContacts = new ArrayList<>();

        database = databaseHelper.getWritableDatabase();

        String[] columns = {Constants.COL_ID, Constants.COL_NAME, Constants.COL_PHONE};

        cursor = database.query(Constants.TABLE_NAME, columns, Constants.COL_ID, null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(columns[0]);
            int index2 = cursor.getColumnIndex(columns[1]);
            int index3 = cursor.getColumnIndex(columns[2]);

            int id = cursor.getInt(index);
            String name = cursor.getString(index2);
            String phone = cursor.getString(index3);

            contact  = new Contact(id, name, phone);
            newContacts.add(contact);

        }

        cursor.close();
        database.close();
        return newContacts;
    }

}
