package me.prapon.contactsyncdemo.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import me.prapon.contactsyncdemo.R;
import me.prapon.contactsyncdemo.viewModel.SplashActivityViewModel;


public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBar;
    private SplashActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewModel = new ViewModelProvider(this).get(SplashActivityViewModel.class);

        progressBar = findViewById(R.id.progressBarSPLSH);
        setContentView(R.layout.activity_splash);

        // Threading
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                viewModel.spendTime();
                requestPermission(SplashActivity.this);
            }
        });

        thread.start();
    }
    /**
     * check permission (Contact)
     */
    public void requestPermission(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            int REQUEST_READ_CONTACTS = 3;
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS);
        } else {
            nextActivity();
        }
    }


    private void nextActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 3) {
            nextActivity();
        }
    }
}
