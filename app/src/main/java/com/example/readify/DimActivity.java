package com.example.readify;

import static com.example.readify.HomeActivity.DIM_MODE_KEY;
import static com.example.readify.HomeActivity.PREFS_NAME;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DimActivity extends AppCompatActivity {
    private View dimOverlay;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter filter = new IntentFilter("ACTION_UPDATE_DIM_MODE");
        registerReceiver(dimModeReceiver, filter, Context.RECEIVER_EXPORTED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dimModeReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDimMode();
    }

    private final BroadcastReceiver dimModeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateDimMode();
        }
    };

    private void updateDimMode() {
        if (dimOverlay == null) {
            dimOverlay = findViewById(R.id.dim_overlay);
        }

        if (dimOverlay != null) {
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            boolean isDimMode = preferences.getBoolean(DIM_MODE_KEY, false);

            if (isDimMode) {
                dimOverlay.setVisibility(View.VISIBLE);
            } else {
                dimOverlay.setVisibility(View.GONE);
            }
        }
    }
}
