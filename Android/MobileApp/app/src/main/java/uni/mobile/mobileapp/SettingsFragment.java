package uni.mobile.mobileapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class SettingsFragment extends Fragment {

    private RadioGroup radioTheme;
    private RadioButton radioDay, radioNight, radioAuto, radioBattery;

    private SharedPreferences preferenceManager;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        preferenceManager = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = preferenceManager.edit();

        radioTheme = view.findViewById(R.id.radioTheme);
        radioDay = view.findViewById(R.id.radioDay);
        radioNight = view.findViewById(R.id.radioNight);
        radioAuto = view.findViewById(R.id.radioAuto);
        radioBattery = view.findViewById(R.id.radioBattery);

        radioTheme.check(preferenceManager.getInt("radioThemeId", R.id.radioDay));

        radioTheme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup arg0, int id) {
                switch (id) {
                    case R.id.radioDay:
                        editor.putInt("Theme", AppCompatDelegate.MODE_NIGHT_NO);
                        editor.commit();
                        editor.putInt("radioThemeId", R.id.radioDay);
                        editor.commit();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        restartApp();
                        break;
                    case R.id.radioNight:
                        editor.putInt("Theme", AppCompatDelegate.MODE_NIGHT_YES);
                        editor.commit();
                        editor.putInt("radioThemeId", R.id.radioNight);
                        editor.commit();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        restartApp();
                        break;
                    case R.id.radioAuto:
                        editor.putInt("Theme", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                        editor.commit();
                        editor.putInt("radioThemeId", R.id.radioAuto);
                        editor.commit();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                        restartApp();
                        break;
                    case R.id.radioBattery:
                        editor.putInt("Theme", AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                        editor.commit();
                        editor.putInt("radioThemeId", R.id.radioBattery);
                        editor.commit();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                        restartApp();
                        break;
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) radioBattery.setVisibility(View.GONE);
        else radioBattery.setVisibility(View.GONE);
    }

    private void restartApp() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        this.startActivity(intent);
        getActivity().finish();
    }

}
