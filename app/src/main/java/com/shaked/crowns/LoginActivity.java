package com.shaked.crowns;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button btnWar;
    EditText etP1Name, etP2Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etP1Name = findViewById(R.id.etP1Name);
        etP2Name = findViewById(R.id.etP2Name);
        btnWar = findViewById(R.id.btnWar);
        btnWar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, GameActivity.class);
                if(etP1Name.getText().toString().isEmpty() || etP2Name.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please Enter Names", Toast.LENGTH_SHORT).show();
                }
                else {
                intent.putExtra("P1", etP1Name.getText().toString());
                intent.putExtra("P2", etP2Name.getText().toString());
                startActivity(intent);}
            }
        });
    }
}