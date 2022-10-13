package com.illimani.sistemadeventas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class verXvendedorActivity extends AppCompatActivity {
    VentasActivity ventasActivity = new VentasActivity(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_xvendedor);
        String datos = ventasActivity.verXvendedor();
        TextView mueVen = (TextView)findViewById(R.id.mueVen);
        mueVen.setText(datos);
    }
}
