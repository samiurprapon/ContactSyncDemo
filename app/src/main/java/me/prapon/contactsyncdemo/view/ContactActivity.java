package me.prapon.contactsyncdemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import me.prapon.contactsyncdemo.R;
import me.prapon.contactsyncdemo.adapter.ContactAdapter;
import me.prapon.contactsyncdemo.model.Contact;
import me.prapon.contactsyncdemo.viewModel.ContactActivityViewModel;

public class ContactActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactAdapter adapter;

    private ContactActivityViewModel viewModel;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        swipeRefreshLayout = findViewById(R.id.simpleSwipeRefreshLayout);
        recyclerView = findViewById(R.id.recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        viewModel = new ViewModelProvider(this).get(ContactActivityViewModel.class);

        adapter = new ContactAdapter(this);
        recyclerView.setAdapter(adapter);

        viewModel.getContactList().observe(this, new Observer<List<Contact>>() {

            @Override
            public void onChanged(List<Contact> contacts) {
                adapter.setContacts(contacts);
                Log.i("View Model", ""+contacts.size());
                adapter.notifyDataSetChanged();
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // implement Handler to wait for 2 seconds and then update UI means update value of list
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        viewModel.sync();
                        Log.i("Size : ", String.valueOf(adapter.getItemCount()));

                    }
                }, 2000);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
