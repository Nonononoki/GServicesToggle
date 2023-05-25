package org.nonononoki.gservicestoggle;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

public class MainActivity extends ComponentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationInfo ai =null;
        try {
            ai = this.getPackageManager().getApplicationInfo("com.google.android.gms",0);
        } catch (PackageManager.NameNotFoundException e) {
            this.finishAffinity();
        }
        boolean enabled = ai.enabled;
        try {
            String command = enabled ? "su -c pm disable com.google.android.gms" : "su -c pm enable com.google.android.gms";
            String toast = enabled ? "Play Services Disabled" : "Play Services Enabled";
            Runtime.getRuntime().exec(command);
            Toast.makeText(this,toast, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        }
        this.finishAffinity();
    }
}
