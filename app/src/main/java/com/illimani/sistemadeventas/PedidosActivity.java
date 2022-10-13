package com.illimani.sistemadeventas;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PedidosActivity extends AppCompatActivity {
    VentasActivity ventasActivity = new VentasActivity(this);
    boolean sw=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        final EditText codProd = (EditText) findViewById(R.id.codProd);
        codProd.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    String datos=ventasActivity.muestradescYventaProducto(codProd.getText().toString());
                    TextView muesDescPro = (TextView) findViewById(R.id.mueDescPro);
                    int fin = datos.indexOf(";");
                    muesDescPro.setText(datos.substring(0,fin));
                    TextView muePre = (TextView) findViewById(R.id.muePre);
                    muePre.setText(datos.substring(fin+1));
                }
            }
            public void afterTextChanged(Editable s) {
            }
        });
    }
    public void finalizar(View vista){
        Intent intent = new Intent(PedidosActivity.this, MainActivity.class);
        intent.putExtra("ingCarCli",getIntent().getStringExtra("ingCarCli").toString());
        intent.putExtra("mueNomCli",getIntent().getStringExtra("mueNomCli").toString());
        intent.putExtra("ingCarVen",getIntent().getStringExtra("ingCarVen").toString());
        intent.putExtra("mueNomVen",getIntent().getStringExtra("mueNomVen").toString());
        intent.putExtra("ingFecha",getIntent().getStringExtra("ingFecha").toString());
        int nroPed = ventasActivity.nroOrden();
        TextView mosVenAgre = (TextView)findViewById(R.id.mosVenAgre);
        nroPed++;
        if(sw==true){
            intent.putExtra("NROPED",nroPed);
        }else{
            intent.putExtra("NROPED",0);
        }
        startActivity(intent);
    }
    public void agregar(View vista){
        SQLiteDatabase db = ventasActivity.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        EditText codProd = (EditText) findViewById(R.id.codProd);
        contentValues.put("codPro",codProd.getText().toString());
        TextView muesDescPro = (TextView) findViewById(R.id.mueDescPro);
        contentValues.put("descripcion",muesDescPro.getText().toString());
        EditText ingCantidad = (EditText) findViewById(R.id.ingCantidad);
        contentValues.put("cantidad",ingCantidad.getText().toString());
        TextView muePre = (TextView) findViewById(R.id.muePre);
        contentValues.put("precio",muePre.getText().toString());
        int nroPed = ventasActivity.nroOrden();
        nroPed++;
        contentValues.put("nroOrden",nroPed);
        TextView mosVenAgre = (TextView)findViewById(R.id.mosVenAgre);

        if ((muesDescPro.getText().toString().trim().length() > 0) && (ingCantidad.getText().toString().trim().length() > 0)&&(muesDescPro.getText().toString().charAt(0)!='n')) {
            db.beginTransaction();
            db.insert("ProductosVen",null,contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();
            sw=true;
        }
        else{
            Toast.makeText(this,"Deben estar llenos todos los campos para poder agregar",Toast.LENGTH_LONG).show();
        }
        String datos;
        datos = ventasActivity.listarProdVen(String.valueOf(nroPed));
        mosVenAgre.setText(datos);
    }
}
