package com.example.submission4.ui.Alarm;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

import com.example.submission4.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment {


    private View view;
    private DailyAlarmReceiver dailyAlarmReceiver = new DailyAlarmReceiver();
    private DailyTodayAlarmReceiver dailyTodayAlarmReceiver = new DailyTodayAlarmReceiver();
    Switch switchDaily;
    Switch switchToday;

//    private Context context = null;
    public AlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_alarm, container, false);

        switchDaily = view.findViewById(R.id.switchDaily);
        switchToday = view.findViewById(R.id.switchToday);

        SharedPreferences sharedPreferencesDaily = this.getActivity().getSharedPreferences("isDaily",Context.MODE_PRIVATE);
        switchDaily.setChecked(sharedPreferencesDaily.getBoolean("checked_Daily",false));

        SharedPreferences sharedPreferencestoday = this.getActivity().getSharedPreferences("isToday",Context.MODE_PRIVATE);
        switchToday.setChecked(sharedPreferencestoday.getBoolean("checked_Today",false));

        dailyChecked();
        todayChecked();
        return view;

    }


    public void dailyChecked(){
        switchDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchDaily.isChecked()){
                    sharedPreferencesDaily(true);
                    switchDaily.setChecked(true);
                    dailyAlarmReceiver.setDailyRepeatingAlarm(getActivity().getApplicationContext());
                }else {
                    sharedPreferencesDaily(false);
                    switchDaily.setChecked(false);
                    dailyAlarmReceiver.cancelDailyRepeating(getActivity().getApplicationContext());
                }
            }
        });
    }

    public void todayChecked(){
        switchToday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchToday.isChecked()){
                    sharedPreferencesToday(true);
                    switchToday.setChecked(true);
                    dailyTodayAlarmReceiver.setDailyTodayAlarm(getActivity().getApplicationContext());
                }else {
                    sharedPreferencesToday(false);
                    switchToday.setChecked(false);
                    dailyTodayAlarmReceiver.cancelTodayRepeating(getActivity().getApplicationContext());
                }
            }
        });
    }

    public void sharedPreferencesDaily (Boolean value){
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("isDaily",Context.MODE_PRIVATE).edit();
        editor.putBoolean("checked_Daily",value);
        editor.apply();

    }

    public void sharedPreferencesToday (Boolean value){
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("isToday",Context.MODE_PRIVATE).edit();
        editor.putBoolean("checked_Today",value);
        editor.apply();
    }




}
