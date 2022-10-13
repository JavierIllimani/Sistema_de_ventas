package com.illimani.sistemadeventas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class verXclienteActivity extends AppCompatActivity {
    VentasActivity ventasActivity = new VentasActivity(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_xcliente);
        String datos = ventasActivity.verXcliente();
        TextView mueCli = (TextView)findViewById(R.id.muCli);
        mueCli.setText(datos);
    }
}
