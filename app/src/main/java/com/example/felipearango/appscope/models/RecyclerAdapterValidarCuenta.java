package com.example.felipearango.appscope.models;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.Util.CircleTransform;
import com.example.felipearango.appscope.Util.ManejoUsers;


import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.example.felipearango.appscope.Util.Util.cuenta_certificada;
import static com.example.felipearango.appscope.Util.Util.cuenta_no_certificada;

/**
 * Created by Sebastian Luna R on 10/31/2017.
 */

public class RecyclerAdapterValidarCuenta extends RecyclerView.Adapter<RecyclerAdapterValidarCuenta.ValidarCuentaHolder> {

    private ArrayList<Empresa> listEmpresaValidar;
    private ManejoUsers mn = new ManejoUsers();
    RecyclerView recycler;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager lManager;
    private Context mContext;
    private LinearLayout ll;

    public static class ValidarCuentaHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        private TextView tvFecha, tvEmpresa;
        private Button aprobar, noAprobar;
        private int idView;
        private View thisView;
        public ValidarCuentaHolder(View v) {
            super(v);
            idView = itemView.getId();
            tvEmpresa = (TextView)itemView.findViewById(R.id.tvEmpresa);
            tvFecha = (TextView)itemView.findViewById(R.id.tvFecha);
            aprobar = (Button)itemView.findViewById(R.id.btnAprobar);
            noAprobar = (Button)itemView.findViewById(R.id.btnNoAprobar);
            thisView = itemView;
        }
    }

    public RecyclerAdapterValidarCuenta(LinearLayout linearl,Context context,ArrayList<Empresa> mValidarCuenta) {
        this.listEmpresaValidar = mValidarCuenta;
        mContext = context;

        ll = linearl;
    }

    @Override
    public RecyclerAdapterValidarCuenta.ValidarCuentaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_validar, parent, false);
        return new ValidarCuentaHolder(inflatedView);
    }


    @Override
    public void onBindViewHolder(ValidarCuentaHolder holder, final int position) {
        Empresa validarCuenta = listEmpresaValidar.get(position);
        bindNotificacion(holder,validarCuenta);
        holder.aprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("apr","APROBO");
                mn.inicializatedFireBase();
                mn.updateEstadoEmpresa(listEmpresaValidar.get(position).getId(),"certificacion",cuenta_certificada);
            }
        });

        holder.noAprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("apr","NO APROBO");
                mn.inicializatedFireBase();
                mn.updateEstadoEmpresa(listEmpresaValidar.get(position).getId(),"certificacion",cuenta_no_certificada);
            }
        });

        holder.thisView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPopUp(listEmpresaValidar.get(position));
            }
        });
    }

    public void bindNotificacion(ValidarCuentaHolder holder, Empresa vCuenta){
        holder.tvEmpresa.setText(vCuenta.getNombre());
        holder.tvFecha.setText("");
    }


    @Override
    public int getItemCount() {
        return listEmpresaValidar.size();
    }

    public void showPopUp(Empresa empresa) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_empresa, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        ArrayList<String[]> txtAndId = new ArrayList();

        if(empresa.getNit().equals("")){
            txtAndId.add(new String[] {"No hay nit anexado ",""});
            Toast.makeText(mContext,"No hay documento para descargar",Toast.LENGTH_SHORT).show();
        }else {
            txtAndId.add(new String[]  {"Hoja de vida anexada",empresa.getNit()});
        }

        // Obtener el Recycler
        recycler = (RecyclerView) popupView.findViewById(R.id.rv_Info);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(mContext);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new RecyclerAdapterPDF(mContext,txtAndId);
        recycler.setAdapter(adapter);



        ImageView imageView = (ImageView)popupWindow.getContentView().findViewById(R.id.imVPerfil);
        TextView tvNombre = (TextView)popupWindow.getContentView().findViewById(R.id.tVNameP);
        TextView tvOcupacion = (TextView)popupWindow.getContentView().findViewById(R.id.tVOcupacionP);
        TextView tvFrase = (TextView)popupWindow.getContentView().findViewById(R.id.tVFrase);
        TextView tVCertificada = (TextView)popupWindow.getContentView().findViewById(R.id.tVCertificada);
        tVCertificada.setVisibility(View.INVISIBLE);
        tvNombre.setText(empresa.getNombre());
        tvOcupacion.setText(empresa.getMail());
        //tvFrase.setText(usuariosSolicitudEnEM.getCedula());
        if(!(empresa.getFoto().equals(""))){
            Glide.with(mContext)
                    .load(empresa.getFoto())
                    .apply(RequestOptions
                            .circleCropTransform()).into(imageView);
            /*Picasso.with(mContext)
                    .load(empresa.getFoto())
                    .transform(new CircleTransform())
                    .into(imageView);*/
        }



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
