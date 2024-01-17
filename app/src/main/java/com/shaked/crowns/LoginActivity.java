package com.shaked.crowns;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private DatabaseHelper myDb;
    Button btnWar,btnBack;
    EditText etP1Name, etP2Name;

    boolean warExists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        warExists = false;
        myDb = new DatabaseHelper(this);

        etP1Name = findViewById(R.id.etP1Name);
        etP2Name = findViewById(R.id.etP2Name);
        btnWar = findViewById(R.id.btnWar);
        btnWar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                boolean isInserted=false;

                Intent intent = new Intent(LoginActivity.this, GameActivity.class);

                if(etP1Name.getText().toString().isEmpty() || etP2Name.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please Enter Names", Toast.LENGTH_SHORT).show();
                }
                else {
                    War war = new War(etP1Name.getText().toString(), etP2Name.getText().toString());
                    if(!myDb.dataExists(war.getP1Name(),war.getP2Name())&&!myDb.dataExists(war.getP2Name(),war.getP1Name())){
                    isInserted = myDb.insertData(new War(war.getP1Name(),war.getP2Name()));

                    if(isInserted){
                    intent.putExtra("P1", war.getP1Name());
                    intent.putExtra("P2", war.getP2Name());
                    startActivity(intent);}

                    else {
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                    }
                    else{
                        if(!warExists){
                        Toast.makeText(LoginActivity.this, "War like this Exists, if its you click again", Toast.LENGTH_SHORT).show();
                        warExists = true;}
                        else {
                            intent.putExtra("P1", etP1Name.getText().toString());
                            intent.putExtra("P2", etP2Name.getText().toString());
                            startActivity(intent);
                        }
                    }
                }
            };
        });

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}