package com.shaked.crowns;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class LoginFrag extends Fragment {

    private DatabaseHelper myDb;
    Button btnWar, btnBack;
    EditText etP1Name, etP2Name;

    boolean clickTwice;

    public LoginFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        clickTwice = false;
        myDb = new DatabaseHelper(getContext());

        etP1Name = view.findViewById(R.id.etP1Name);
        etP2Name = view.findViewById(R.id.etP2Name);
        btnWar = view.findViewById(R.id.btnWar);
        btnWar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                boolean isInserted=false;

                Intent intent = new Intent(getContext(), GameActivity.class);

                if(etP1Name.getText().toString().isEmpty() || etP2Name.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please Enter Names", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        if(!clickTwice){
                            Toast.makeText(getContext(), "War like this Exists, if its you click again", Toast.LENGTH_SHORT).show();
                            clickTwice = true;}
                        else {
                            intent.putExtra("P1", etP1Name.getText().toString());
                            intent.putExtra("P2", etP2Name.getText().toString());
                            startActivity(intent);
                        }
                    }
                }
            }
        });

        btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}