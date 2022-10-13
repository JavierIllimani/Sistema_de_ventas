package com.illimani.sistemadeventas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VentasActivity  extends SQLiteOpenHelper {
    private static final int VERSION=4;
    private static final String N_BD="SistemadeVentas4.db";
    String tabla = "create table Clientes(carnet Integer Primary Key,paterno text, materno text,nombre text)";
    String tabla1 = "create table Vendedores(carnet Integer Primary Key,nombre text)";
    String tabla2 = "create table Productos(cod Integer Primary Key,descripcion text)";
    String tabla3 = "create table Precios(cod int,fecha date,precio double)";
    String tabla4 = "create table Ventas(codCli int,nombreCli text,codVen text,nombreVen text,fecha text,estado text,nroOrden int)";
    String tabla5 = "create table ProductosVen(codPro int,descripcion text,cantidad text, precio double,nroOrden text)";
    VentasActivity(Context context) {
        super(context, N_BD, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tabla);
        db.execSQL(tabla1);
        db.execSQL(tabla2);
        db.execSQL(tabla3);
        db.execSQL(tabla4);
        db.execSQL(tabla5);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLA IF EXISTS "+tabla);
        onCreate(db);
        db.execSQL("DROP TABLA IF EXISTS "+tabla1);
        onCreate(db);
        db.execSQL("DROP TABLA IF EXISTS "+tabla2);
        onCreate(db);
        db.execSQL("DROP TABLA IF EXISTS "+tabla3);
        onCreate(db);
        db.execSQL("DROP TABLA IF EXISTS "+tabla4);
        onCreate(db);
        db.execSQL("DROP TABLA IF EXISTS "+tabla5);
        onCreate(db);
    }
    public String muestraNombreCliente(String cod){
        SQLiteDatabase db = this.getReadableDatabase();
        String resultado2="";
        if(db!=null){
            Cursor d= db.rawQuery("select nombre,paterno,materno from Clientes where carnet = "+cod ,null);
            for (d.moveToFirst();!d.isAfterLast();d.moveToNext()) { //se hace el barrido(recorrido) al cursor desde
                resultado2=resultado2+d.getString(0)+" "+d.getString(1)+" "+d.getString(2)+"  \n";
            }
        }
        return resultado2;

    }
    public String muestraNombreVendedor(String cod){
        SQLiteDatabase db = this.getReadableDatabase();
        String resultado2="";
        if(db!=null){
            Cursor d= db.rawQuery("select nombre from Vendedores where carnet = "+cod ,null);
            for (d.moveToFirst();!d.isAfterLast();d.moveToNext()) { //se hace el barrido(recorrido) al cursor desde
                resultado2=resultado2+d.getString(0)+"  \n";
            }
        }
        return resultado2;
    }
    public String muestradescYventaProducto(String cod){
        SQLiteDatabase db = this.getReadableDatabase();
        String resultado2="";
        if(db!=null){
            Cursor d= db.rawQuery("select p.descripcion,precio,max(fecha) from Productos p,Precios pr where p.cod=pr.cod and p.cod = "+cod ,null);
            for (d.moveToFirst();!d.isAfterLast();d.moveToNext()) { //se hace el barrido(recorrido) al cursor desde
                resultado2=resultado2+d.getString(0)+";"+d.getString(1)+"  \n";
            }
        }
        return resultado2;
    }
    public int nroOrden(){
        SQLiteDatabase db = this.getReadableDatabase();
        int resultado=0;
        if(db!=null){
            Cursor c = db.rawQuery("select max(nroOrden) from Ventas",null);
            for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
                resultado = c.getInt(0);
            }
        }
        return resultado;
    }

    public String verXcliente(){
        SQLiteDatabase db = this.getReadableDatabase();
        String resultado="";
        if(db!=null){
            Cursor c = db.rawQuery("select nombreCli,estado from Ventas ",null);
            for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
                resultado = resultado+"Nombre:"+c.getString(0)+"   Estado:"+c.getString(1)+" \n";
            }
        }
        return resultado;
    }
    public String verXvendedor(){
        SQLiteDatabase db = this.getReadableDatabase();
        String resultado="";
        if(db!=null){
            Cursor c = db.rawQuery("select nombreVen,estado from Ventas ",null);
            for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
                resultado = resultado+"Nombre:"+c.getString(0)+"   Estado:"+c.getString(1)+" \n";
            }
        }
        return resultado;
    }
    public String xNro(String cod){
        SQLiteDatabase db = this.getReadableDatabase();
        String resultado="";
        if(db!=null){
            Cursor c = db.rawQuery("select * from ProductosVen where nroOrden ="+cod,null);
            for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
                resultado = resultado+"Cod:"+c.getString(0)+"   Descripcion:"+c.getString(1)+"   Cantidad:"+c.getString(2)+"   Precio:"+c.getString(3)+" \n";
            }
        }
        return resultado;
    }

    public String listarProdVen(String cod){
        SQLiteDatabase db = this.getReadableDatabase();
        String resultado="";
        if(db!=null) {
            Cursor c = db.rawQuery("select * from ProductosVen where nroOrden="+cod,null);
            for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
                resultado = resultado+c.getString(0)+" | "+c.getString(1)+" | "+c.getString(2)+" | "+c.getString(3)+" \n";
            }
        }
        return resultado;
    }

    public String ListarVen(){
        SQLiteDatabase db = this.getReadableDatabase();
        String resultado="";
        if(db!=null){
            Cursor c= db.rawQuery("select * from Ventas",null);
            for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
                resultado=resultado+c.getString(0)+" "+c.getString(1)+" "+c.getString(2)+" "+c.getString(3)+" "+c.getString(4)+" "
                        +c.getString(5)+" "+c.getString(6)+"  \n";
            }
        }
        return resultado;
    }
    public String Listar(){
        SQLiteDatabase db = this.getReadableDatabase();
        String resultado="";
        if(db!=null){
            Cursor c= db.rawQuery("select * from Clientes",null);
            for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) { //se hace el barrido(recorrido) al cursor desde
                resultado=resultado+c.getString(0)+" "+c.getString(1)+" "+c.getString(2)
                        +" "+c.getString(3)+"  \n";
            }
        }
        return resultado;
    }
    public String Listar2(){
        SQLiteDatabase db = this.getReadableDatabase();
        String resultado="";
        if(db!=null){
            Cursor c= db.rawQuery("select * from Vendedores",null);
            for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) { //se hace el barrido(recorrido) al cursor desde
                resultado=resultado+c.getString(0)+" "+c.getString(1)+"  \n";
            }
        }
        return resultado;
    }
    public String Listar3(){
         SQLiteDatabase db = this.getReadableDatabase();
        String resultado="";
        if(db!=null){
            Cursor c= db.rawQuery("select * from Productos",null);
            for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) { //se hace el barrido(recorrido) al cursor desde
                resultado=resultado+c.getString(0)+" "+c.getString(1)+"  \n";
            }
        }
        return resultado;
    }
    public String Listar4(){
        SQLiteDatabase db = this.getReadableDatabase();
        String resultado="";
        if(db!=null){
            Cursor c= db.rawQuery("select * from Precios",null);
            for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) { //se hace el barrido(recorrido) al cursor desde
                resultado=resultado+c.getString(0)+" "+c.getString(1)+" "+c.getString(2)+"  \n";
            }
        }
        return resultado;
    }

}