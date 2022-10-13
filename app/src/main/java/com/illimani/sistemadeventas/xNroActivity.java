package com.illimani.sistemadeventas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class xNroActivity extends AppCompatActivity {
    VentasActivity ventasActivity = new VentasActivity(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_nro);
        final EditText ingNro = (EditText)findViewById(R.id.ingNro);
        ingNro.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    String datos=ventasActivity.xNro(ingNro.getText().toString());
                    TextView mueNro = (TextView)findViewById(R.id.mueNro);
                    mueNro.setText(datos);
                }
            }
        });
    }
}
