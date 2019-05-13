package com.example.waterrootapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class MoistureActivity extends AppCompatActivity {
    public static String moistureLogText;
    public static TextView textOutput;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moisture);
        textOutput=findViewById(R.id.textOutput);
        printMoistureLog(textOutput);
        printMoistureLog(textOutput);
        TimerService.addRecentMoistureListener();

    }


    public void onBack (View v){
        Intent startNewActivity = new Intent(MoistureActivity.this,AdditionalFeatures.class);
        startActivity(startNewActivity);
    }
    public void onRefresh(View v){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("commands").child("moistureOn").setValue(1);
        printMoistureLog(textOutput);
    }
    public static void printMoistureLog(TextView output){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference moistureLog = database.getReference("moistureLog");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
//                waterLogText=dataSnapshot.getValue().toString();
                Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();
                moistureLogText="You may need to click Refresh multiple times for current readings.\n\n";
                moistureLogText+="Recent Moisture: "+dataSnapshot.child("recentMoisture").getValue()+"";

                while (iterator.hasNext()) {
                    DataSnapshot entry = iterator.next();
                    //add the formatted string representing the entry as it's supposed to be
                    if (entry.getChildrenCount()!=1||entry.getKey().toString().equals("recentLog")){
                        //Maybe we'll do something here, probably not.
                    }
                    else{
                        Iterator<DataSnapshot> entryIta=entry.getChildren().iterator();
                        long moistureVal= ((long) entryIta.next().getValue());


                        moistureLogText+="\n"+entry.getKey().toString()+"\n\t \t Moisture: "+
                                moistureVal;


                    }
                }
                moistureLogText+="\nRecent Moisture: "+dataSnapshot.child("recentMoisture").getValue()+"";
                textOutput.setText(moistureLogText);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("firebase", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        moistureLog.addValueEventListener(postListener);
    }
}
