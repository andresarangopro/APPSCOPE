package com.example.felipearango.appscope;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class ScreenRegister extends AppCompatActivity {

    ///////////////////////////////
    //Variables
    //////////////////////////////

    private EditText txtName,txtSNombre, txtPrimerA, txtSegundoA,txtUniversidad, txtCorreo,
            txtPass, txtPassC;
    private TextView lblDateInf,lblDisponibilidad,lblDateBorn;
    private ImageButton iBtnSelectDateB;
    private RadioGroup rGDisponibilidadViaje;
    private Spinner spOcupation;
    private Button btnRegister;

    ////////////////////////////////
    //Oncreate
    ////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_register);
        instanceXml();
    }

    ////////////////////////////////
    //Metodos
    ////////////////////////////////

    private void instanceXml(){
        txtName = (EditText) findViewById(R.id.txtName);
        txtSNombre = (EditText) findViewById(R.id.txtSNombre);
        txtPrimerA = (EditText) findViewById(R.id.txtPrimerA);
        txtSegundoA = (EditText) findViewById(R.id.txtSegundoA);
        txtUniversidad = (EditText) findViewById(R.id.txtUniversidad);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtPass = (EditText) findViewById(R.id.txtPass);
        txtPassC = (EditText) findViewById(R.id.txtPassC);

        lblDateInf = (TextView) findViewById(R.id.lblDateInf);
        lblDisponibilidad = (TextView) findViewById(R.id.lblDisponibilidad);
        lblDateBorn = (TextView) findViewById(R.id.lblDateBorn);

        iBtnSelectDateB = (ImageButton) findViewById(R.id.IBtnSelectDateB);

        rGDisponibilidadViaje = (RadioGroup) findViewById(R.id.rGDisponibilidadViaje);

        spOcupation = (Spinner) findViewById(R.id.spOcupacion);

        btnRegister = (Button) findViewById(R.id.btnRegistrar);
    }
}
