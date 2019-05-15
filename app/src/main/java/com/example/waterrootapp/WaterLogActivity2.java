/**@author: Daniel Abadjiev
 * @date: 5/15/19
 * @description: This is the second version of the activity to display the information fom the WaterLog in firebase. This information includes the date and time of water, the duration, and whether the plant was watered automatically or manually.
 */
package com.example.waterrootapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class WaterLogActivity2 extends AppCompatActivity {
    public static String waterLogText;
    public static TextView textOutput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_log2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textOutput=findViewById(R.id.textOutput);
        printWaterLog();
    }

    /**
     * Takes the user to the AdditionalFeatures page from the WaterLogActivity2 page
     * @param v is the View object
     */
    public void onBack (View v){
        Intent startNewActivity = new Intent(WaterLogActivity2.this,AdditionalFeatures.class);
        startActivity(startNewActivity);
    }

    /**
     * Displays updated WaterLog entries from firebase
     * @param v is the View object
     */
    public void onRefresh(View v){
        printWaterLog();
    }

    /**
     * Displays Water Log entries from firebase in a text view
     */
    public void printWaterLog(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference waterLog = database.getReference("waterLog");
//        output.setText(waterLog.toString());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
//                waterLogText=dataSnapshot.getValue().toString();
                Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();
                waterLogText="";

                while (iterator.hasNext()) {
                    DataSnapshot entry = iterator.next();
                    //add the formatted string representing the entry as it's supposed to be
                    if (entry.getChildrenCount()!=4){
                        waterLogText+="\n"+entry.getKey().toString()+": Badly Formatted Entry!!!!!";
                    }
                    else{
                        Iterator<DataSnapshot> entryIta=entry.getChildren().iterator();
                        String durationVal=entryIta.next().getValue().toString();
                        if (durationVal.equals("0"))
                            durationVal="Not Available";
                        String manAutoVal=entryIta.next().getValue().toString();
                        if (manAutoVal.equals("0"))
                            manAutoVal="Not Available";
                        String moistureVal= ""+((long) entryIta.next().getValue());
                        if (moistureVal.equals("0"))
                            moistureVal="Not Available";
                        boolean waterVal=((boolean) entryIta.next().getValue());
                        String timeTag=entry.getKey().toString();
                        if (timeTag.indexOf("Current ")>-1){
                            timeTag=timeTag.substring(8);
                        }
                        waterLogText+="\n"+timeTag;
                        waterLogText+="\n\t \t Duration: "+durationVal+
                                "\n\t \t Manual or Automatic: "+manAutoVal+"\n\t \t Moisture: "+
                                moistureVal+"\n\t \t Watered: "+waterVal;
                    }
                }
                textOutput.setText(waterLogText);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("firebase", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        waterLog.addValueEventListener(postListener);
    }
}
