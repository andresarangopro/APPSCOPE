package com.example.felipearango.appscope.models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.felipearango.appscope.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Sebastian Luna R on 11/11/2017.
 */

public class SolicitudAEmpresaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Button btnAceptar, btnRechazar;
    private TextView tvNombre;
    private LinearLayout ll;
    private Context context;

    public SolicitudAEmpresaHolder(View itemView) {
        super(itemView);
        btnAceptar = (Button) itemView.findViewById(R.id.btnAceptar);
        btnRechazar = (Button) itemView.findViewById(R.id.btnRechazar);
        tvNombre = (TextView) itemView.findViewById(R.id.tvNombre);
        itemView.setOnClickListener(this);
        btnAceptar.setOnClickListener(this);
        btnRechazar.setOnClickListener(this);
        
    }

    public void bindNotificacion(EmpresaSolicitud empresaSolicitud) {
        tvNombre.setText(empresaSolicitud.getNombre());
        context = EmpresaSolicitud.getCtn();
        ll = EmpresaSolicitud.getLl();
    //    Toast.makeText(context, "Por acá también", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnAceptar: {
                break;
            }
            case R.id.btnRechazar: {
                break;
            }
            default: {
                showPopUp();
            }
        }
    }

    private void showPopUp() {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_empresa, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //////////////////////////////////////////////////////////////
        ////////Inicialización de los dos componentes de el pop up
        //////////////////////////////////////////////////////////////

       // TextView tvEmpresa = ((TextView) popupWindow.getContentView().findViewById(R.id.tvEmpresa));
        //TextView tvDetalles = ((TextView) popupWindow.getContentView().findViewById(R.id.tvDetalles));
      //  ((TextView) popupWindow.getContentView().findViewById(R.id.tvDetalles)).setText("hello there");

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

}
