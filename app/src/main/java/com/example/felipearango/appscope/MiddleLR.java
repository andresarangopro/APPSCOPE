package com.example.felipearango.appscope;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MiddleLR extends AppCompatActivity implements View.OnClickListener{

    private Button iBtnCompany, iBtnUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_lr);
        instanceXml();

    }

    private void instanceXml(){
        iBtnCompany = (Button) findViewById(R.id.iBtnCompany);
        iBtnCompany.setOnClickListener(this);
        iBtnUser = (Button) findViewById(R.id.iBtnUser);
        iBtnUser.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view == iBtnCompany){
            finish();
            startActivity(new Intent(MiddleLR.this,ScreenRegisterE.class));
        }
        if(view == iBtnUser){
            finish();
            startActivity(new Intent(MiddleLR.this,ScreenRegisterUC.class));
        }
    }
}
