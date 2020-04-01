package me.prapon.contactsyncdemo.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class SplashActivityViewModel extends AndroidViewModel {

    public SplashActivityViewModel(@NonNull Application application) {
        super(application);
    }
    public void spendTime() {
        int progress;


        for (progress = 10; progress <= 100; progress += 10) {
            try {
                Thread.sleep(160);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
