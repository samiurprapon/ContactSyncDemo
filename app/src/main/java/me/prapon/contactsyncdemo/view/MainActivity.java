package me.prapon.contactsyncdemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import me.prapon.contactsyncdemo.R;
import me.prapon.contactsyncdemo.viewModel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
    }

    public void nextActivity(View view) {
        Intent intent = new Intent(MainActivity.this, ContactActivity.class);
        startActivity(intent);
    }


}
