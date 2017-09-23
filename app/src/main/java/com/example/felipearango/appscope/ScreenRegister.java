package com.example.felipearango.appscope;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ScreenRegister extends AppCompatActivity {

    ///////////////////////////////
    //Variables
    //////////////////////////////

    private EditText txtName,txtSNombre, txtPrimerA, txtSegundoA,txtUniversidad, txtCorreo,
            txtPass, txtPassC, txtPhone;
    private TextView lblDateInf,lblDisponibilidad,lblDateBorn;
    private ImageButton iBtnSelectDateB;
    private RadioGroup rGDisponibilidadViaje;
    private Spinner spOcupation;
    private Button btnRegister;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private int day,monthS,yearS;


    ////////////////////////////////
    //Oncreate
    ////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_register);
        instanceXml();
        onClicklistener();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
    }

    ////////////////////////////////
    //Metodos
    ////////////////////////////////

    /**
     *
     */
    private void instanceXml(){
        txtName = (EditText) findViewById(R.id.txtName);
        txtSNombre = (EditText) findViewById(R.id.txtSNombre);
        txtPrimerA = (EditText) findViewById(R.id.txtPrimerA);
        txtSegundoA = (EditText) findViewById(R.id.txtSegundoA);
        txtUniversidad = (EditText) findViewById(R.id.txtUniversidad);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtPass = (EditText) findViewById(R.id.txtPass);
        txtPassC = (EditText) findViewById(R.id.txtPassC);
        txtPhone = (EditText) findViewById(R.id.txtPhone);

        lblDateInf = (TextView) findViewById(R.id.lblDateInf);
        lblDisponibilidad = (TextView) findViewById(R.id.lblDisponibilidad);
        lblDateBorn = (TextView) findViewById(R.id.lblDateBorn);

        iBtnSelectDateB = (ImageButton) findViewById(R.id.IBtnSelectDateB);

        rGDisponibilidadViaje = (RadioGroup) findViewById(R.id.rGDisponibilidadViaje);

        spOcupation = (Spinner) findViewById(R.id.spOcupacion);

        btnRegister = (Button) findViewById(R.id.btnRegistrar);
    }

    /**
     *
     */
    private void onClicklistener(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        iBtnSelectDateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDialog(0);
            }
        });
    }

    /**
     *
     * @param id
     * @return
     */
    protected Dialog onCreateDialog(int id){
        return new DatePickerDialog(this, datePickerLister, yearS, monthS, day);
    }

    /**
     *
     */
    private DatePickerDialog.OnDateSetListener datePickerLister = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            day = dayOfMonth;
            monthS = month;
            yearS = year;
            lblDateBorn.setText(day+"/"+(monthS+1)+"/"+yearS);
        }
    };

    /**
     *
     * @param id
     * @param txtName
     * @param sNombre
     * @param txtPrimerA
     * @param txtSegundoA
     * @param ocupacion
     * @param dateBorn
     * @param txtUniversidad
     * @param txtPhone
     * @param txtCorreo
     * @param txtFoto
     * @param txtFrase
     * @param hobbies
     * @param conocimientosInf
     * @param disponibilidadViaje
     * @param anexos
     * @param idiomas
     * @param expProfesionales
     * @param refEmpleo
     * @param formacion
     */
    private void tomarDatos(String id, String txtName, String sNombre, String txtPrimerA, String txtSegundoA
                            , String ocupacion, String dateBorn , String txtUniversidad, String txtPhone,
                            String txtCorreo, String txtFoto, String txtFrase, String hobbies,
                            String conocimientosInf, int disponibilidadViaje, ArrayList<String> anexos,
                            ArrayList<String> idiomas, ArrayList<String> expProfesionales,
                            ArrayList<String> refEmpleo,ArrayList<String> formacion){

        if(!campEmpty(id) && !campEmpty(txtName) && !campEmpty(txtPrimerA) && !campEmpty(txtUniversidad)
               && !campEmpty(txtCorreo) && !campEmpty(dateBorn) && !campEmpty(disponibilidadViaje+"")
                && !campEmpty(ocupacion)){
                String nombre = txtName+" "+sNombre;
                String apellido = txtPrimerA+" "+txtSegundoA;
                UsuarioCorriente uC = new UsuarioCorriente(id,nombre,apellido,ocupacion,dateBorn,
                                                            txtUniversidad,txtPhone,txtCorreo,txtFoto,
                                                            txtFrase, hobbies, conocimientosInf,
                                                             anexos,idiomas,expProfesionales,
                                                                refEmpleo,formacion);
        }


    }

    /**
     * Metodo register metodo que registra un usuario inexistente
     * @param mail Correo del usuario nuevo a registrar
     * @param pass Contraseña del usuario que se esta registrando
     */
    private void registerUser(String mail, String pass) {
        firebaseAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(ScreenRegister.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(ScreenRegister.this, "REGISTER SUCCESFULLY", Toast.LENGTH_SHORT).show();
                           // finish();
                            //startActivity(new Intent(ScreenRegister.this, MainActivity.class));
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(ScreenRegister.this, "COULD NOT REGISTER. PLEASE TRY AGAIN", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private boolean campEmpty(String campo){
        return TextUtils.isEmpty(campo);
    }

    private String getTxtEdit(EditText txt){
        return txt.getText().toString();
    }
}
