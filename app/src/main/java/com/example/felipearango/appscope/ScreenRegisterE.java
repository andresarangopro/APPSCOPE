package com.example.felipearango.appscope;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ScreenRegisterE extends AppCompatActivity {

    private EditText txtnameE,txtMailE, txtPassE, txtConfirmPassE;
    private Button btnRegisterE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_register_e);
        instanceXml();
        putClickBtn();
    }

    private void instanceXml(){
        txtnameE = (EditText) findViewById(R.id.txtNameE);
        txtMailE = (EditText) findViewById(R.id.txtMailE);
        txtPassE = (EditText) findViewById(R.id.txtPassE);
        txtConfirmPassE = (EditText) findViewById(R.id.txtPassEC);

        btnRegisterE = (Button) findViewById(R.id.btnRegisterE);
    }

    private void putClickBtn(){
        this.btnRegisterE.setOnClickListener(this.getButtonListener);
    }

    public View.OnClickListener getButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if(v == btnRegisterE){

          }
        }
    };
}
