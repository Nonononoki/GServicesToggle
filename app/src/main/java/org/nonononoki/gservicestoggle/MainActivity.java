package org.nonononoki.gservicestoggle;

import android.content.ComponentName;
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
        if(enabled) {
            changeIconToFirst();
        } else {
            changeIconToSecond();
        }
        this.finishAffinity();
    }

    //https://stackoverflow.com/a/73219911 CC BY-SA 4.0 (c) Elazar Halperin
    private void changeIconToSecond() {
        // disables the first icon
        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(new ComponentName(MainActivity.this, "org.nonononoki.gservicestoggle.MainActivity"),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        // enables the second icon
        packageManager.setComponentEnabledSetting(new ComponentName(MainActivity.this, "org.nonononoki.gservicestoggle.MainActivityAlias"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }
    private void changeIconToFirst() {
        // disables the second icon
        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(new ComponentName(MainActivity.this, "org.nonononoki.gservicestoggle.MainActivity"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        // enables the first icon
        packageManager.setComponentEnabledSetting(new ComponentName(MainActivity.this, "org.nonononoki.gservicestoggle.MainActivityAlias"),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
