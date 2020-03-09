package com.example.testapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testapplication.BoundaryClass.getCoordinateUI;
import com.example.testapplication.ControlClass.getCoordinateController;

public class MainActivity extends AppCompatActivity {
    public static TextView result;
    public static int postalAdd;
    public static EditText postalcode;
    double xCoordinate;
    double yCoordinate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView result = findViewById(R.id.result);

        // Retrieve all parks
        Database db = new Database();
        db.loadAllParks().whenComplete((parks, throwable) -> {
            if (throwable == null){
                // Continue Here
            } else {
                Toast.makeText(MainActivity.this, "Please try again.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        postalcode = findViewById(R.id.postalAddress);

       Button submit = findViewById(R.id.postalBtn);
        Button SearchButton = findViewById(R.id.SearchButton);
        //test button can be deleted sooonz
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postalAdd = Integer.parseInt(postalcode.getText().toString());
                result.setText("Postcode: "+postalAdd);

                getCoordinateController process = new getCoordinateController();
                process.execute();

                Intent startIntent = new Intent(getApplicationContext(), getCoordinateUI.class);
                //passing info to another activity
                startIntent.putExtra("com.example.testapplication.SOMETHING",postalAdd);
                startActivity(startIntent);
            }
        });
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postalAdd = Integer.parseInt(postalcode.getText().toString());
                result.setText("Postcode: "+postalAdd);
                //call background controller
                getCoordinateController process = new getCoordinateController();
                process.execute();

                Intent intent = new Intent(getApplicationContext(), FilterActivity.class);
                //pass x- and y-coordinates to FilterActivity
                intent.putExtra("com.example.testapplication.SOMETHING",postalAdd);
                startActivity(intent);
            }
        });

        ImageButton favouritesButton = findViewById(R.id.favouritesButton);
        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavouritesActivity.class);
                startActivity(intent);
            }
        });

    }
}
