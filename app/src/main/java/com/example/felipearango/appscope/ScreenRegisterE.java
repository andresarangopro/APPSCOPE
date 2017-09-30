package com.example.felipearango.appscope;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ScreenRegisterE extends AppCompatActivity implements View.OnClickListener {

    private EditText txtnameE,txtRazonSoc, txtNIT, txtUrl, txtSocial;
    private Button btnRegisterE, btnAgregarNIT, btnAgregarRedSocial;
    private LinearLayout llRedesSociales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_register_e);
        instanceXml();


    }

    private void instanceXml(){
        txtnameE = (EditText) findViewById(R.id.txtNameE);
        txtRazonSoc = (EditText) findViewById(R.id.txtRazonSoc);
        txtNIT = (EditText) findViewById(R.id.txtNIT);
        txtUrl = (EditText) findViewById(R.id.txtUrl);
        txtSocial = (EditText) findViewById(R.id.txtSocial);
        llRedesSociales = (LinearLayout) findViewById(R.id.llRedesSociales);

        btnRegisterE = (Button) findViewById(R.id.btnRegisterE);
        btnRegisterE.setOnClickListener(this);

        btnAgregarRedSocial = (Button) findViewById(R.id.btnAgregarRedesSociales);
        btnAgregarRedSocial.setOnClickListener(this);

        btnAgregarNIT = (Button) findViewById(R.id.btnAgregarNit);
        btnAgregarNIT.setOnClickListener(this);
    }

    private void addToSocialNetwork(){

        LinearLayout llrow = new LinearLayout(this);
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                0, 1);
        llrow.setWeightSum(5f);
        llrow.setLayoutParams(llParams);

        EditText nET = new EditText(this);
        nET.setLayoutParams(new TableLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT, 4f));
        llrow.addView(nET);

        Button btn = new Button(this);
        btn.setOnClickListener(this);
        btn.setText("+");
        btn.setTextSize(40);
        btn.setBackgroundResource(R.drawable.mybutton);

        llrow.addView(btn);
        llRedesSociales.setWeightSum(llRedesSociales.getWeightSum()+1);
        llRedesSociales.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,
                llRedesSociales.getWeightSum()+1));
        llRedesSociales.addView(llrow);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnAgregarNit:{
                break;
            }
            case R.id.btnAgregarRedesSociales:{
                addToSocialNetwork();
                break;
            }
            case R.id.btnRegisterE: {
                break;
            }
            default:{

            }
        }
    }
}
