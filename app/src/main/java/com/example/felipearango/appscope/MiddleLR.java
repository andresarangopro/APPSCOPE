package com.example.felipearango.appscope;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.felipearango.appscope.activities.Activity_ScreenRegisterE;
import com.example.felipearango.appscope.activities.Activity_ScreenRegisterUC;

public class MiddleLR extends AppCompatActivity {

    private Button iBtnCompany, iBtnUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_lr);
        instanceXml();
        setListenerBtn();
    }

    private void instanceXml(){
        iBtnCompany = (Button) findViewById(R.id.iBtnCompany);
        iBtnUser = (Button) findViewById(R.id.iBtnUser);
    }

    private void setListenerBtn(){
        this.iBtnCompany.setOnClickListener(this.buttonClickListener);
        this.iBtnUser.setOnClickListener(this.buttonClickListener);

    }

   public View.OnClickListener buttonClickListener = new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           if(v == iBtnCompany){
               finish();
               startActivity(new Intent(MiddleLR.this,Activity_ScreenRegisterE.class));
           }
           if(v == iBtnUser){
               finish();
               startActivity(new Intent(MiddleLR.this,Activity_ScreenRegisterUC.class));
           }
       }
   };
}
