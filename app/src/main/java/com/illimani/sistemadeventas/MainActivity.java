package com.illimani.sistemadeventas;

import android.content.ContentValues;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
public class MainActivity extends AppCompatActivity {
    //
    VentasActivity ventasActivity = new VentasActivity(this);
    boolean sw=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            llenaClientes();
            llenaVendedores();
            llenaProductos();
            llenaPrecios();
            final EditText carCli = (EditText) findViewById(R.id.ingCarCli);
             carCli.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                public void afterTextChanged(Editable s) {
                    if(s.length()>0){
                        String nombre=ventasActivity.muestraNombreCliente(carCli.getText().toString());
                        TextView mostrar = (TextView) findViewById(R.id.mueNomClie);
                        mostrar.setText(nombre);
                    }
                }
            });
            final EditText carVen = (EditText) findViewById(R.id.ingCarVen);
             carVen.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length() > 0) {
                        String nombre = ventasActivity.muestraNombreVendedor(carVen.getText().toString());
                        TextView mostrar = (TextView) findViewById(R.id.mueNomVen);
                        mostrar.setText(nombre);
                    }
                }
            });
            preProceso();
    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        String nameValue = "hola";
        outState.putString("carnetCl", nameValue);
      }
    public void productos(View vista) {
        Intent intent = new Intent(MainActivity.this, PedidosActivity.class);
        EditText ingCarCli = (EditText) findViewById(R.id.ingCarCli);
        TextView mueNomClie = (TextView) findViewById(R.id.mueNomClie);
        EditText ingCarVen = (EditText) findViewById(R.id.ingCarVen);
        TextView mueNomVen = (TextView) findViewById(R.id.mueNomVen);
        EditText ingFecha = (EditText) findViewById(R.id.ingFecha);
        if ((mueNomClie.getText().toString().trim().length() > 0) && (mueNomVen.getText().toString().trim().length() > 0) && (ingFecha.getText().toString().trim().length() > 0)) {
                intent.putExtra("ingCarCli", ingCarCli.getText().toString());
                intent.putExtra("mueNomCli", mueNomClie.getText().toString());
                intent.putExtra("ingCarVen", ingCarVen.getText().toString());
                intent.putExtra("mueNomVen", mueNomVen.getText().toString());
                intent.putExtra("ingFecha", ingFecha.getText().toString());
                startActivity(intent);
        } else {
                Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
        }
    }
    public void procesar(View vista){
        SQLiteDatabase db = ventasActivity.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("codCli",getIntent().getStringExtra("ingCarCli"));
        contentValues.put("nombreCli",getIntent().getStringExtra("mueNomCli"));
        contentValues.put("codVen",getIntent().getStringExtra("ingCarVen"));
        contentValues.put("nombreVen",getIntent().getStringExtra("mueNomVen"));
        contentValues.put("fecha",getIntent().getStringExtra("ingFecha"));
        contentValues.put("estado","PEDIDO");
        contentValues.put("nroOrden",getIntent().getIntExtra("NROPED",0));

        int date = getIntent().getIntExtra("NROPED",0);
        if((date>0)&&(sw==true)){
            db.beginTransaction();
            db.insert("Ventas",null,contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();
            Toast.makeText(this,"Se proceso con exito ",Toast.LENGTH_LONG).show();
            sw=false;
        }else{Toast.makeText(this,"No se pude procesar porque todavia no realizo pedidos",Toast.LENGTH_LONG).show();}
    }
    public void nuevo(View vista){
        EditText ingCarCli =(EditText) findViewById(R.id.ingCarCli);
        ingCarCli.setText("");
        TextView mueNomClie =(TextView) findViewById(R.id.mueNomClie);
        mueNomClie.setText("");
        EditText ingCarVen =(EditText) findViewById(R.id.ingCarVen);
        ingCarVen.setText("");
        TextView mueNomVen =(TextView) findViewById(R.id.mueNomVen);
        mueNomVen.setText("");
        EditText ingFecha =(EditText) findViewById(R.id.ingFecha);
        ingFecha.setText("");
        TextView mueNumOrd = (TextView)findViewById(R.id.devNumOr);
        mueNumOrd.setText("");
    }
    public void verXcliente(View vista){
        Intent intent = new Intent(MainActivity.this,verXclienteActivity.class);
        startActivity(intent);
    }
    public void verXvendedor(View vista){
        Intent intent = new Intent(MainActivity.this,verXvendedorActivity.class);
        startActivity(intent);
    }
    public void xNro(View vista){
        Intent intent = new Intent(MainActivity.this,xNroActivity.class);
        startActivity(intent);
    }
    public void preProceso(){
        String dato;
        EditText ingCarCli =(EditText) findViewById(R.id.ingCarCli);
        dato = getIntent().getStringExtra("ingCarCli");
        ingCarCli.setText(dato);
        TextView mueNomClie =(TextView) findViewById(R.id.mueNomClie);
        dato = getIntent().getStringExtra("mueNomCli");
        mueNomClie.setText(dato);
        EditText ingCarVen =(EditText) findViewById(R.id.ingCarVen);
        dato = getIntent().getStringExtra("ingCarVen");
        ingCarVen.setText(dato);
        TextView mueNomVen =(TextView) findViewById(R.id.mueNomVen);
        dato = getIntent().getStringExtra("mueNomVen");
        mueNomVen.setText(dato);
        EditText ingFecha =(EditText) findViewById(R.id.ingFecha);
        dato = getIntent().getStringExtra("ingFecha");
        ingFecha.setText(dato);
        TextView mueNumOrd = (TextView)findViewById(R.id.devNumOr);
        int date = getIntent().getIntExtra("NROPED",0);
        if(date!=0){
            mueNumOrd.setText(String.valueOf(date));
        }
    }
    public void llenaClientes () {
            String[] texto = leeCLientes();
            SQLiteDatabase db = ventasActivity.getWritableDatabase();
            db.beginTransaction();
            for (int i = 1; i < texto.length; i++) {
                String[] linea = texto[i].split(";");
                ContentValues contentValues = new ContentValues();
                contentValues.put("carnet", linea[0]);
                contentValues.put("paterno", linea[1]);
                contentValues.put("materno", linea[2]);
                contentValues.put("nombre", linea[3]);
                db.insert("Clientes", null, contentValues);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
    }
    private String[] leeCLientes(){
        InputStream inputStream = getResources().openRawResource(R.raw.clientes);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int i = inputStream.read();
            while(i !=-1){
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return  byteArrayOutputStream.toString().split("\n");
    }
    public void llenaVendedores () {
            String[] texto = leeVendedores();
            SQLiteDatabase db = ventasActivity.getWritableDatabase();
            db.beginTransaction();
            for (int i = 1; i < texto.length; i++) {
                String[] linea = texto[i].split(";");
                ContentValues contentValues = new ContentValues();
                contentValues.put("carnet", linea[0]);
                contentValues.put("nombre", linea[1]);
                db.insert("Vendedores", null, contentValues);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
    }
    private String[] leeVendedores(){
        InputStream inputStream = getResources().openRawResource(R.raw.vendedores);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int i = inputStream.read();
            while(i !=-1){
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return  byteArrayOutputStream.toString().split("\n");
    }
    public void llenaProductos () {
        String[] texto = leeProductos();
        SQLiteDatabase db = ventasActivity.getWritableDatabase();
        db.beginTransaction();
        for (int i = 1; i < texto.length; i++) {
            String[] linea = texto[i].split(";");
            ContentValues contentValues = new ContentValues();
            contentValues.put("cod", linea[0]);
            contentValues.put("descripcion", linea[1]);
            db.insert("Productos", null, contentValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    private String[] leeProductos(){
        InputStream inputStream = getResources().openRawResource(R.raw.productos);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int i = inputStream.read();
            while(i !=-1){
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return  byteArrayOutputStream.toString().split("\n");
    }
    public void llenaPrecios () {
        String[] texto = leePrecios();
        SQLiteDatabase db = ventasActivity.getWritableDatabase();
        db.beginTransaction();
        for (int i = 1; i < texto.length; i++) {
            String[] linea = texto[i].split(";");
            ContentValues contentValues = new ContentValues();
            contentValues.put("cod", linea[0]);
            contentValues.put("fecha", linea[1]);
            contentValues.put("precio", linea[2]);
            db.insert("Precios", null, contentValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    private String[] leePrecios(){
        InputStream inputStream = getResources().openRawResource(R.raw.precios);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int i = inputStream.read();
            while(i !=-1){
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return  byteArrayOutputStream.toString().split("\n");
    }
}