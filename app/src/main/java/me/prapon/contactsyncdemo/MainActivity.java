package me.prapon.contactsyncdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.prapon.contactsyncdemo.sevices.SyncDatabaseService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService();
    }

    public void nextActivity(View view) {
        Intent intent = new Intent(MainActivity.this, ContactActivity.class);
        startActivity(intent);
    }

    public void startService() {

        Intent intent = new Intent(this, SyncDatabaseService.class);
        startService(intent);

    }

}
