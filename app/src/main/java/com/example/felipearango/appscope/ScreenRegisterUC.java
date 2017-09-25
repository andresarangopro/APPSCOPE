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

    private EditText txtName,txtSNombre, txtPrimerA, txtSegundoA,txtUniversidad, txtCorreo,
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
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtPass = (EditText) findViewById(R.id.txtPass);
        txtPassC = (EditText) findViewById(R.id.txtPassC);
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
                String mail = getTxtEdit(txtCorreo);
                String pass = getTxtEdit(txtPass);
                String passC = getTxtEdit(txtPassC);

                if(!campEmpty(pass) && !campEmpty(passC) && !campEmpty(mail) && comprobarCampos()){
                    if(comprobarPass(pass,passC)){
                        registerUser(mail,pass);
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

    /**
     *
     *
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
    private UsuarioCorriente tomarDatos(String id,String txtName, String sNombre, String txtPrimerA, String txtSegundoA
                            , String ocupacion, String dateBorn , String txtUniversidad, String txtPhone,
                            String txtCorreo, String txtFoto, String txtFrase, String hobbies,
                            String conocimientosInf, int disponibilidadViaje, ArrayList<String> anexos,
                            ArrayList<String> idiomas, ArrayList<String> expProfesionales,
                            ArrayList<String> refEmpleo,ArrayList<String> formacion){

        if(!campEmpty(txtName) && !campEmpty(txtPrimerA) && !campEmpty(txtUniversidad)
               && !campEmpty(txtCorreo) && !campEmpty(dateBorn)){
                String nombre = txtName+" "+sNombre;
                String apellido = txtPrimerA+" "+txtSegundoA;
                UsuarioCorriente uC = new UsuarioCorriente(id,nombre,apellido,ocupacion,dateBorn,
                                                            txtUniversidad,txtPhone,txtCorreo,txtFoto,
                                                            txtFrase, hobbies, conocimientosInf,
                                                             anexos,idiomas,expProfesionales,
                                                                refEmpleo,formacion);
            return uC;
        }

        return null;
    }

    /**
     * Metodo register metodo que registra un usuario inexistente
     * @param mail Correo del usuario nuevo a registrar
     * @param pass Contraseña del usuario que se esta registrando
     */
    private void registerUser(String mail, String pass) {
        final String mail1 = mail;
        final String pass1 = pass;
        firebaseAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(ScreenRegisterUC.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ScreenRegisterUC.this, "REGISTER SUCCESFULLY", Toast.LENGTH_SHORT).show();
                           // finish();
                            //startActivity(new Intent(ScreenRegisterUC.this, MainActivity.class));
                            loginUser(mail1,pass1);

                        } else {
                            Toast.makeText(ScreenRegisterUC.this, "COULD NOT REGISTER. PLEASE TRY AGAIN", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    /**
     *
     * @param mail
     * @param pass
     */
    private void loginUser(String mail, String pass){
       // progressDialog.setMessage("Login user, please wait...");
        //progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(mail,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       // progressDialog.dismiss();
                        if(task.isSuccessful()){
                            creaUsuario();
                        }else{
                            Toast.makeText(ScreenRegisterUC.this,"Datos errados",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    public void creaUsuario (){
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
        ArrayList<String> anexos = new ArrayList<String>();
        ArrayList<String> idiomas = new ArrayList<String>();
        ArrayList<String> expProfesionaless = new ArrayList<String>();
        ArrayList<String> refEmpleo = new ArrayList<String>();
        ArrayList<String> formacion = new ArrayList<String>();
        String mail = getTxtEdit(txtCorreo);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        id = user.getUid();
        // Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
        UsuarioCorriente uC =  tomarDatos(id,name,sName,apellido,sApellido,ocupacion,dateBorn,universidad
                ,celular,mail,foto,frase,hobbies,conocimientosInf,disponibilidadV
                ,anexos,idiomas,expProfesionaless,refEmpleo,formacion);

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
