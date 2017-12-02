package com.example.felipearango.appscope.models;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.activities.Activity_Notificaciones_S;
import com.example.felipearango.appscope.activities.Activity_ScreenRegisterE;
import com.example.felipearango.appscope.activities.Activity_ScreenRegisterUC;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.example.felipearango.appscope.Util.Util.notificacion_oferta_aceptada;
import static com.example.felipearango.appscope.Util.Util.notificacion_oferta_rechazada;
import static com.example.felipearango.appscope.activities.Activity_Login.TIPO_USUARIO;
import static com.example.felipearango.appscope.activities.Activity_Login.calledAlready;

/**
 * Created by Sebastian Luna R on 11/11/2017.
 */

public class RecyclerAdapterEmpresa extends RecyclerView.Adapter<RecyclerAdapterEmpresa.SolicitudAEmpresaHolder> {

    private ArrayList<UsuariosSolicitudEnEM> mUsuariosSolicitudEnEM;
    private Context mContext;
    private LinearLayout ll;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    public RecyclerAdapterEmpresa(Context context, LinearLayout linearLayout,ArrayList<UsuariosSolicitudEnEM> mUsuariosSolicitudEnEM) {
        this.mUsuariosSolicitudEnEM = mUsuariosSolicitudEnEM;
        mContext = context;
        ll = linearLayout;
    }

    public static class SolicitudAEmpresaHolder extends RecyclerView.ViewHolder {
        private Button btnAceptar, btnRechazar;
        private TextView tvNombre;
        private TextView tvApellido;
        private TextView tvCedula;
        private View thisView;

        public SolicitudAEmpresaHolder(View itemView) {
            super(itemView);

            btnAceptar = (Button) itemView.findViewById(R.id.btnAceptar);
            btnRechazar = (Button) itemView.findViewById(R.id.btnRechazar);
            tvNombre = (TextView) itemView.findViewById(R.id.tvNombre);
            tvApellido = (TextView) itemView.findViewById(R.id.tvApellido);
            tvCedula = (TextView) itemView.findViewById(R.id.tvCedula);
            thisView = itemView;
        }
    }

    @Override
    public SolicitudAEmpresaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.botones_rv, parent, false);
        return new SolicitudAEmpresaHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(SolicitudAEmpresaHolder holder, final int position) {
        final UsuariosSolicitudEnEM usuariosSolicitudEnEM = mUsuariosSolicitudEnEM.get(position);
        holder.tvNombre.setText("NOMBRE: "+usuariosSolicitudEnEM.getNombre());
        holder.tvApellido.setText("APELLIDO: "+usuariosSolicitudEnEM.getApellido());
        holder.tvCedula.setText("CELULAR: "+usuariosSolicitudEnEM.getCedula());
        holder.btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inicializatedFireBase();
               insertarUs(notificacion_oferta_aceptada,mUsuariosSolicitudEnEM.get(position).getIdJob()
                       ,mUsuariosSolicitudEnEM.get(position).getId() );
                mUsuariosSolicitudEnEM.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mUsuariosSolicitudEnEM.size());
            }
        });

        final UsuariosSolicitudEnEM usuariosSolicitudEnEM = mUsuariosSolicitudEnEM.get(position);
        holder.tvNombre.setText("NOMBRE: "+usuariosSolicitudEnEM.getNombre());
        holder.tvApellido.setText("APELLIDO: "+usuariosSolicitudEnEM.getApellido());
        holder.tvCedula.setText("CELULAR: "+usuariosSolicitudEnEM.getCedula());
        holder.btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inicializatedFireBase();
               insertarUs(notificacion_oferta_aceptada,mUsuariosSolicitudEnEM.get(position).getIdJob()
                       ,mUsuariosSolicitudEnEM.get(position).getId() );
                mUsuariosSolicitudEnEM.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mUsuariosSolicitudEnEM.size());
            }
        });

        holder.btnRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarUs(notificacion_oferta_rechazada,mUsuariosSolicitudEnEM.get(position).getIdJob()
                        ,mUsuariosSolicitudEnEM.get(position).getId() );
                mUsuariosSolicitudEnEM.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mUsuariosSolicitudEnEM.size());
                inicializatedFireBase();

            }
        });

        holder.thisView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              showPopUp(mUsuariosSolicitudEnEM.get(position));
            }
        });
    }


    public void showPopUp( UsuariosSolicitudEnEM usuariosSolicitudEnEM) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_empresa, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        ImageView imageView = (ImageView)popupWindow.getContentView().findViewById(R.id.imVPerfil);
        TextView tvNombre = (TextView)popupWindow.getContentView().findViewById(R.id.tVNameP);
        TextView tvOcupacion = (TextView)popupWindow.getContentView().findViewById(R.id.tVOcupacionP);
        TextView tvFrase = (TextView)popupWindow.getContentView().findViewById(R.id.tVFrase);

        
        tvNombre.setText(usuariosSolicitudEnEM.getNombre());
        tvOcupacion.setText(usuariosSolicitudEnEM.getApellido());
        tvFrase.setText(usuariosSolicitudEnEM.getCedula());

        //////////////////////////////////////////////////////////////
        ////Esto muestra el pop Up window
        ////////////////////////////////////////////////////////////

        popupWindow.showAtLocation(ll, Gravity.CENTER, 0, 0);
        //////////////////////////////////////////////////
        ////////Listener que oculta el pop Up
        ////////////////////////////////////////////////
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }


    private void insertarUs(int estado,String idTrabajo,String idWorker){
        databaseReference.child("Jobs").child(idTrabajo).child("Ofertas").child(idWorker).child("Estado").setValue(estado);

    }

    protected void inicializatedFireBase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        if (!calledAlready) {
            firebaseDatabase.setPersistenceEnabled(true);
            calledAlready = true;
        }
        databaseReference = firebaseDatabase.getReference();
    }
    @Override
    public int getItemCount() {
        return mUsuariosSolicitudEnEM.size();
    }

}
