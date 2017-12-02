package com.example.felipearango.appscope.models;

import android.content.Context;
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

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Sebastian Luna R on 11/11/2017.
 */

public class RecyclerAdapterEmpresa extends RecyclerView.Adapter<RecyclerAdapterEmpresa.SolicitudAEmpresaHolder> {

    private ArrayList<UsuarioCorriente> usuario = new ArrayList<>();
    private ArrayList<Empresa> empresa = new ArrayList<>();
    private Context mContext;
    private LinearLayout ll;

    public RecyclerAdapterEmpresa(Context context, LinearLayout linearLayout,ArrayList<Object> mEmpresaSolicitud) {

        clonarListaEmpresa(mEmpresaSolicitud);

        mContext = context;
        ll = linearLayout;
    }

    private void clonarListaEmpresa(ArrayList<Object> mEmpresa){

        for (Object obj : mEmpresa) {
            if(obj instanceof UsuarioCorriente){
                usuario.add((UsuarioCorriente)obj);
            }else if(obj instanceof Empresa){
                empresa.add((Empresa)obj);
            }
        }
    }

    public static class SolicitudAEmpresaHolder extends RecyclerView.ViewHolder {

        private Button btnAceptar, btnRechazar;
        private TextView tvNombre;
        private View thisView;

        public SolicitudAEmpresaHolder(View itemView) {
            super(itemView);
            btnAceptar = (Button) itemView.findViewById(R.id.btnAceptar);
            btnRechazar = (Button) itemView.findViewById(R.id.btnRechazar);
            tvNombre = (TextView) itemView.findViewById(R.id.tvNombre);
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

        if(empresa.isEmpty()){
            final UsuarioCorriente empresaSolicitud = usuario.get(position);
            holder.tvNombre.setText(empresaSolicitud.getNombre());
            holder.btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*
                    Acá pasa lo de aceptar
                     */
                }
            });

            holder.btnRechazar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    usuario.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, usuario.size());
                }
            });

            holder.thisView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopUp(usuario.get(position));
                }
            });
        }else{
            final Empresa empresaSolicitud = empresa.get(position);
            holder.tvNombre.setText(empresaSolicitud.getNombre());
            holder.btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*
                    Acá pasa lo de aceptar
                     */
                }
            });

            holder.btnRechazar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    empresa.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, empresa.size());
                }
            });

            holder.thisView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopUp(empresa.get(position));
                }
            });
        }
    }

    private void showPopUp(Object user) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_empresa, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        ImageView imPerfil = (ImageView)popupWindow.getContentView().findViewById(R.id.imVPerfil);
        TextView tvNombre = ((TextView) popupWindow.getContentView().findViewById(R.id.tVNameP));
        TextView tvOcupacion = ((TextView) popupWindow.getContentView().findViewById(R.id.tVOcupacionP));
        TextView tvFrase = ((TextView) popupWindow.getContentView().findViewById(R.id.tVFrase));



        if(user instanceof UsuarioCorriente){
            UsuarioCorriente usuarioCorriente = (UsuarioCorriente) user;
            tvNombre.setText(usuarioCorriente.getNombre());
            tvOcupacion.setText(usuarioCorriente.getOcupacion());
            tvFrase.setText(usuarioCorriente.getFrase());
        }else{
            Empresa empresa = (Empresa) user;
            tvNombre.setText(empresa.getNombre());
            tvOcupacion.setText(empresa.getMail());
            tvFrase.setText(empresa.getEstadoCuenta());
        }







        //Falta la foto!!

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
        if(!usuario.isEmpty()){
            return usuario.size();
        }else{
            return empresa.size();
        }
    }
}
