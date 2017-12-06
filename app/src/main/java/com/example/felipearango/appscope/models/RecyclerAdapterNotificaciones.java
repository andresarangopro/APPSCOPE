package com.example.felipearango.appscope.models;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.Util.ManejoUsers;
import com.example.felipearango.appscope.activities.Activity_Notificaciones_S;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.example.felipearango.appscope.Util.Util.notificacion_oferta_aceptada;
import static com.example.felipearango.appscope.Util.Util.usuario_corriente;
import static com.example.felipearango.appscope.Util.Util.usuario_empresa;
import static com.example.felipearango.appscope.activities.Activity_Login.TIPO_USUARIO;

/**
 * Created by Sebastian Luna R on 10/29/2017.
 */

public class RecyclerAdapterNotificaciones extends RecyclerView.Adapter<RecyclerAdapterNotificaciones.NotificacionHolder> {

    private ArrayList<Notificacion> mNotificacion;
    private Context mContext;
    private static boolean userTrabajos = false;
    private LinearLayout ll;
    private ManejoUsers mn = new ManejoUsers();
    private int calificacion = 0;
    private FirebaseUser user;
    public RecyclerAdapterNotificaciones(Context context, ArrayList<Notificacion> mNotificacion, boolean userWT, LinearLayout ll, FirebaseUser user) {
        this.mNotificacion = mNotificacion;
        mContext = context;
        this.ll = ll;
        userTrabajos = userWT;
        mn.inicializatedFireBase();
        this.user = user;
    }

    public static class NotificacionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView tvTitulo, tvEmpresa, tvEstado;
        private View thisView;
        public NotificacionHolder(View itemView) {
            super(itemView);
            tvTitulo = (TextView)itemView.findViewById(R.id.tvEmpresa);
            tvEmpresa = (TextView)itemView.findViewById(R.id.tvTrabajo);
            tvEstado = (TextView)itemView.findViewById(R.id.tvEstado);
            if(TIPO_USUARIO == usuario_empresa || userTrabajos){
                itemView.setOnClickListener(this);
            }
            thisView = itemView;
        }


        //////////////////////
        /////Acá iría para donde lo mandaría cuando de click
        /////////////////////////
        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent=new Intent(new Intent(context,Activity_Notificaciones_S.class));
            Bundle bundle=new Bundle();
            bundle.putString("idJob", tvEmpresa.getText().toString());
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }

    @Override
    public NotificacionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_notificacion, parent, false);
        return new NotificacionHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(NotificacionHolder holder, int position) {
        final Notificacion notificacion = mNotificacion.get(position);
        if(TIPO_USUARIO == usuario_empresa){
            holder.tvTitulo.setText(notificacion.getNombreTrabajo());
            holder.tvEmpresa.setText(notificacion.getIdEmpresa());
            holder.tvEstado.setText("Aspirantes: "+notificacion.getEstado());
        }else if(TIPO_USUARIO == usuario_corriente){
            holder.tvTitulo.setText(notificacion.getNombreTrabajo());
            holder.tvTitulo.setTextColor((Color.parseColor("#017580")));
            holder.tvEmpresa.setText(notificacion.getNombreEmpresa());
            holder.tvEmpresa.setTextColor((Color.parseColor("#000000")));
            Log.e("NOT",notificacion.getEstadoCalif()+"");
            if(notificacion.getEstadoCalif() == 0){
                holder.thisView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopUp(notificacion);
                    }
                });
            }
            if(!userTrabajos) {
                holder.tvEstado.setText("Resultado : " + (notificacion.getEstado() == notificacion_oferta_aceptada ? "ACEPTADA" : "RECHAZADA"));
            }else{
                holder.tvTitulo.setText(notificacion.getNombreTrabajo());
                holder.tvEmpresa.setText(notificacion.getIdEmpresa());
                holder.tvEstado.setText("Aspirantes: "+notificacion.getEstado());
            }
        }

    }

    public void showPopUp(final Notificacion notifier)  {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_califacion, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        Button btnC1 =  (Button) popupWindow.getContentView().findViewById(R.id.btnC1);
        Button btnC2 =  (Button) popupWindow.getContentView().findViewById(R.id.btnC2);
        Button btnC3 =  (Button) popupWindow.getContentView().findViewById(R.id.btnC3);
        Button btnC4 =  (Button) popupWindow.getContentView().findViewById(R.id.btnC4);
        Button btnC5 =  (Button) popupWindow.getContentView().findViewById(R.id.btnC5);


        btnC1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calificacion = 1;
                try {
                    notifier.setEstadoCalif(calificacion);
                    mn.updateEstadoC(notifier.getIdJob(), user.getUid(),notifier.getIdEmpresa(),calificacion);
                    popupWindow.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnC2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifier.setEstadoCalif(calificacion);
                calificacion = 2;
                mn.updateEstadoC(notifier.getIdJob(), user.getUid(),notifier.getIdEmpresa(),calificacion);
                popupWindow.dismiss();
            }
        });
        btnC3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifier.setEstadoCalif(calificacion);
                calificacion = 3;
                mn.updateEstadoC(notifier.getIdJob(), user.getUid(),notifier.getIdEmpresa(),calificacion);
                popupWindow.dismiss();
            }
        });
        btnC4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifier.setEstadoCalif(calificacion);
                calificacion = 4;
                mn.updateEstadoC(notifier.getIdJob(), user.getUid(),notifier.getIdEmpresa(),calificacion);
                popupWindow.dismiss();
            }
        });
        btnC5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifier.setEstadoCalif(calificacion);
                calificacion = 5;
                mn.updateEstadoC(notifier.getIdJob(), user.getUid(),notifier.getIdEmpresa(),calificacion);
                popupWindow.dismiss();
            }
        });
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


    @Override
    public int getItemCount() {
        return mNotificacion.size();
    }
}