package com.example.rohan.fromo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menu extends AppCompatActivity {

    Button rcmode,facefollower,pathfollower,animation,objecttracking,trainingmode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        rcmode=(Button)findViewById(R.id.rcmode);
        facefollower=(Button)findViewById(R.id.facefollower);
        pathfollower=(Button)findViewById(R.id.pathfollower);
        animation=(Button)findViewById(R.id.animation);
      //  objecttracking=(Button)findViewById(R.id.objecttracking);
        trainingmode=(Button)findViewById(R.id.trainingmode);
    }

    public void rcmode(View view){
        Intent i =new Intent(this,btrc.class);
        startActivity(i);
    }

    public void facefollower(View view){
        Intent i =new Intent(this,facefollower.class);
        startActivity(i);
    }

    public void pathfollower(View view){
        Intent i =new Intent(this,pathfollower.class);
        startActivity(i);
    }

    public void animation(View view){
        Intent i =new Intent(this,animation.class);
        startActivity(i);
    }

    public void objecttracking(View view){
        Intent i =new Intent(this,objecttracking.class);
        startActivity(i);
    }

    public void trainingmode(View view){
        Intent i =new Intent(this,surveillance.class);
        startActivity(i);
    }
}
