package com.example.felipearango.appscope;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Ofertas extends MainActivity {

    private TextView tvTrabajo, tvEmpresa, tvTitulo, tvOfertas;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_ofertas, null, false);
        mDrawer.addView(contentView, 0);
        ll = (LinearLayout) findViewById(R.id.llLayout);
        ll.setOnTouchListener(new OnSwipeTouchListener(Ofertas.this) {
            public void onSwipeTop() {
                Toast.makeText(Ofertas.this, "top", Toast.LENGTH_SHORT).show();
                Toast.makeText(Ofertas.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(Ofertas.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(Ofertas.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(Ofertas.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
    }


    private void iniciarComponentes(){



    }


}
