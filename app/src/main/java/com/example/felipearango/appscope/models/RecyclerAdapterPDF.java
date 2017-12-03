package com.example.felipearango.appscope.models;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.felipearango.appscope.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Felipe Arango on 2/12/2017.
 */

public class RecyclerAdapterPDF extends RecyclerView.Adapter<RecyclerAdapterPDF.RecyclerAdapterPDFHolder>{
    private ArrayList<String[]> listPDf;
    private Context mContext;
    private ProgressDialog progressDialog;

    public static class RecyclerAdapterPDFHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        // public ImageView imagen;
        public TextView nombre;
        public ImageView btn1;
        //public TextView visitas;

        public RecyclerAdapterPDFHolder(View v) {
            super(v);
            btn1 = (ImageView) itemView.findViewById(R.id.btn1);
            nombre = (TextView) itemView.findViewById(R.id.tvH);
        }
    }

    public RecyclerAdapterPDF(Context context,ArrayList<String[]> students){
        this.listPDf = students;
        mContext = context;
        progressDialog = new ProgressDialog(mContext);
    }

    @Override
    public RecyclerAdapterPDFHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_pdf, parent, false);
        return new RecyclerAdapterPDFHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterPDFHolder holder, final int position) {
        holder.nombre.setText(listPDf.get(position)[0]);
        holder.btn1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!listPDf.get(position)[1].equals("")){
                progressDialog.setMessage("Descargando, por favor espera");
                progressDialog.show();
                downloadFile(listPDf.get(position)[1]);
            }else{
                Toast.makeText(mContext,"No hay documento para descargar",Toast.LENGTH_SHORT).show();
            }

        }
    });
    }

    @Override
    public int getItemCount() {
        return listPDf.size();
    }

    private void downloadFile(String es) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl(es);
           // StorageReference  islandRef = storageRef.child("file.txt");
            File rootPath = new File(Environment.getExternalStorageDirectory(), "Download");
            if(!rootPath.exists()) {
                rootPath.mkdirs();
            }

            final String name = System.currentTimeMillis() + ".pdf";
            final File localFile = new File(rootPath,name);

            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.e("firebase ",";local tem file created  created " +localFile.toString());
                    //  updateDb(timestamp,localFile.toString(),position);
                    progressDialog.setMessage("Abriendo..., por favor espera");
                    openFile(name,mContext);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("firebase ",";local tem file not created  created " +exception.toString());
                }
            });
    }

    private void openFile(String filename, Context context){
        File file = new File(Environment.getExternalStorageDirectory() +"/"+"Download/"+ filename);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file),"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, "Open File");
        progressDialog.dismiss();
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
            Toast.makeText(mContext,"Debe instalar un lector de pdf",Toast.LENGTH_SHORT).show();
        }
    }




}
