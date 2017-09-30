package com.example.felipearango.appscope;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class ScreenRegisterUC extends AppCompatActivity {

    ///////////////////////////////
    //Variables
    //////////////////////////////

    private EditText txtName,txtSNombre, txtPrimerA, txtSegundoA,txtUniversidad,
            txtPass, txtPassC, txtPhone;
    private TextView lblDisponibilidad,lblDateBorn;
    private DatabaseReference databaseReference;
    private ImageButton iBtnSelectDateB;
    private RadioGroup rGDisponibilidadViaje;
    private Spinner spOcupation;
    private Button btnRegister;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private int day,monthS,yearS;
    private static final String ESTADO_ACTIVA = "ACTIVA";

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
        initializedDR();
        chooseDate();
    }



    ////////////////////////////////
    //Metodos
    ////////////////////////////////

    private void initializedDR() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    /**
     *
     */
    private void instanceXml(){
        txtName = (EditText) findViewById(R.id.txtName);
        txtSNombre = (EditText) findViewById(R.id.txtSNombre);
        txtPrimerA = (EditText) findViewById(R.id.txtPrimerA);
        txtSegundoA = (EditText) findViewById(R.id.txtSegundoA);
        txtUniversidad = (EditText) findViewById(R.id.txtUniversidad);
        txtPhone = (EditText) findViewById(R.id.txtPhone);

        lblDisponibilidad = (TextView) findViewById(R.id.lblDisponibilidad);
        lblDateBorn = (TextView) findViewById(R.id.lblDateBorn);


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
                String pass = getTxtEdit(txtPass);
                String passC = getTxtEdit(txtPassC);

                if(!campEmpty(pass) && !campEmpty(passC) && comprobarCampos()){
                    if(comprobarPass(pass,passC)){
                        updateDatesUser();
                       // registerUser(mail,pass);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Campos vacios",Toast.LENGTH_LONG).show();
                }


            }
        });
    }


    private void chooseDate(){

        final Calendar calendar = Calendar.getInstance();
        final int yy = calendar.get(Calendar.YEAR);
        final int mm = calendar.get(Calendar.MONTH);
        final int dd = calendar.get(Calendar.DAY_OF_MONTH);
        lblDateBorn.setText(dd+"/"+mm+"/"+yy);
        lblDateBorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDate=Calendar.getInstance();
                int mYear=mcurrentDate.get(Calendar.YEAR);
                int mMonth=mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(ScreenRegisterUC.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                        actualizarFecha(selectedday+"/"+selectedmonth+"/"+selectedyear);

                    }
                },mYear, mMonth, mDay);

                mDatePicker.setTitle("Seleccione su fecha de nacimiento");
                mDatePicker.show();
            }
        });
    }

    private void actualizarFecha(String fecha){
        lblDateBorn.setText(fecha);
    }

    private boolean comprobarCampos(){
        String name =getTxtEdit(txtName);
        String apellido = getTxtEdit(txtPrimerA);
        String dateBorn = lblDateBorn.getText().toString();
        String universidad = getTxtEdit(txtUniversidad);
        String celular = getTxtEdit(txtPhone);

        return !campEmpty(name) && !campEmpty(apellido) && !campEmpty(dateBorn) && !campEmpty(universidad)
                    && !campEmpty(celular);
    }

    public void updateDatesUser(){
        String id = "";
        String name =getTxtEdit(txtName);
        String sName = getTxtEdit(txtSNombre);
        String apellido = getTxtEdit(txtPrimerA);
        String sApellido = getTxtEdit(txtSegundoA);
        String ocupacion = "";
        String dateBorn = lblDateBorn.getText().toString();
        String universidad = getTxtEdit(txtUniversidad);
        String celular = getTxtEdit(txtPhone);
        String foto = "";
        String frase = "";
        String hobbies = "";
        String conocimientosInf = "";
        int disponibilidadV = 0;
        String anexos = "";
        String idiomas = "";
        String expProfesionaless ="";
        String refEmpleo ="";
        String formacion = "";
        String mail = " ";
        FirebaseUser user = firebaseAuth.getCurrentUser();
        id = user.getUid();
        mail = user.getEmail();
        UsuarioCorriente uC = new UsuarioCorriente(id,name+" "+sName,apellido+" "+sApellido,ocupacion,
                dateBorn,universidad,celular,mail,foto,frase,hobbies,conocimientosInf,ESTADO_ACTIVA,
                anexos,idiomas,expProfesionaless,refEmpleo,formacion);

        if(uC != null){
            insertarUsCFireBase(uC,user);
        }
    }

    /**
     *
     * @param uC
     */
    private void insertarUsCFireBase(UsuarioCorriente uC,FirebaseUser user){
        databaseReference.child("CorrientsUsers").child(user.getUid()).setValue(uC);
        finish();
       startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    /**
     *
     * @param camp
     * @param camp2
     * @return
     */
    private boolean comprobarPass(String camp, String camp2){
        return camp.equals(camp2);
    }

    /**
     *
     * @param campo
     * @return
     */
    private boolean campEmpty(String campo){
        return TextUtils.isEmpty(campo);
    }

    /**
     *
     * @param txt
     * @return
     */
    private String getTxtEdit(EditText txt){
        return txt.getText().toString();
    }


}
