package com.example.waterrootapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.sql.Time;
import java.util.Timer;

import static android.provider.Contacts.SettingsColumns.KEY;
import static android.support.constraint.Constraints.TAG;
import static com.example.waterrootapp.SplashScreenActivity.firstTime;
import static com.example.waterrootapp.SplashScreenActivity.sharedPreferences;
import static com.example.waterrootapp.SplashScreenActivity.switchon;


public class SettingsActivity2 extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
   public  SharedPreferences myPrefs;
    public  SharedPreferences.Editor editor2;
    public static final String LAST_TEXT = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);



        sharedPreferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);
          editor2 = myPrefs.edit();





        Switch timerSwitch = (Switch) findViewById(R.id.switch1);

        SharedPreferences settings2 = getSharedPreferences(PREFS_NAME, 0);
        boolean silent2 = settings2.getBoolean("timerkey", false);
        timerSwitch.setChecked(silent2);
        timerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {



                    Intent intent = new Intent(SettingsActivity2.this,TimerService.class);


                    startService(intent);


         }
                else{
                    Intent intent = new Intent(SettingsActivity2.this,TimerService.class);

                    stopService(intent);
                    // Log.d(TAG, "service off ");

                }
                SharedPreferences settings2 = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings2.edit();
                editor.putBoolean("timerkey", isChecked);
                editor.commit();


            }


        });



        Switch instructionsSwitch = (Switch) findViewById(R.id.switch2);
if(instructionsSwitch.isChecked()){
    switchon = true;
}
else {
    switchon = false;
}
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean silent = settings.getBoolean("switchkey", false);
        instructionsSwitch.setChecked(silent);



        instructionsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {


                    editor2.putBoolean("switchon", true);
                    editor2.apply();
                    Log.d(TAG, Boolean.toString(myPrefs.getBoolean("switchon", true)));



//                    switchon = true;
//
//                    //switchon = true;
//                    Log.d(TAG, Boolean.toString(getSwitchOn(mainView)));







                }
                else{

                    editor2.putBoolean("switchon", false);
                    editor2.apply();
                    Log.d(TAG, Boolean.toString(myPrefs.getBoolean("switchon", true)));




                }
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("switchkey", isChecked);
                editor.commit();




            }


        });
        final EditText timeOfWater =  findViewById(R.id.editText);


        SharedPreferences settings4 = getSharedPreferences(PREFS_NAME, 0);
        String silent4 = settings.getString("timeofwaterkey", null);
        timeOfWater.setText(silent4);

        timeOfWater.addTextChangedListener(new TextChangedListener<EditText>(timeOfWater) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                String time = timeOfWater.getText().toString();
                Log.d(TAG, time);
                Intent timeIntent = new Intent(SettingsActivity2.this, TimerService.class);

                timeIntent.putExtra("timeofday", time);

                SharedPreferences settings4 = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor4 = settings4.edit();
                editor4.putString("timeofwaterkey", time);
                editor4.commit();

            }



        });




        final EditText dateOfWater =  findViewById(R.id.editText2);


        SharedPreferences settings5 = getSharedPreferences(PREFS_NAME, 0);
        String silent5 = settings.getString("dateofwaterkey", null);
        dateOfWater.setText(silent5);

        dateOfWater.addTextChangedListener(new TextChangedListener<EditText>(dateOfWater) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                String date = dateOfWater.getText().toString();
                Log.d(TAG, date);
                Intent timeIntent = new Intent(SettingsActivity2.this, TimerService.class);

                timeIntent.putExtra("dateofwater", date);

                SharedPreferences settings5 = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor5 = settings5.edit();
                editor5.putString("dateofwaterkey", date);
                editor5.commit();

            }



        });

        final EditText duration =  findViewById(R.id.editText3);


        SharedPreferences settings6 = getSharedPreferences(PREFS_NAME, 0);
        String silent6 = settings6.getString("durationkey", null);
        duration.setText(silent6);

        duration.addTextChangedListener(new TextChangedListener<EditText>(duration) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                String durationstring = duration.getText().toString();
                Log.d(TAG, durationstring);
                Intent timeIntent = new Intent(SettingsActivity2.this, TimerService.class);

                timeIntent.putExtra("durationintentkey", durationstring);

                SharedPreferences settings6 = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor6 = settings6.edit();
                editor6.putString("durationkey", durationstring);
                editor6.commit();

            }



        });

    }

    public void onReturn (View v){
        Intent startNewActivity = new Intent(SettingsActivity2.this,MainActivity.class);
        startActivity(startNewActivity);


    }


    public abstract class TextChangedListener<T> implements TextWatcher {
        private T target;

        public TextChangedListener(T target) {
            this.target = target;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {

            this.onTextChanged(target, s);

        }

        public abstract void onTextChanged(T target, Editable s);
    }







}
