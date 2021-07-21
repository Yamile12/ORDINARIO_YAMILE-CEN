package com.example.ordinario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText nombre,precio,cantidad,imagen;
    Button guardare,leerb,eliminar,update;
    TextView contenidob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre= (EditText) findViewById(R.id.nombre);
        precio= (EditText) findViewById(R.id.precio);
        cantidad= (EditText) findViewById(R.id.cantidad);
        imagen= (EditText) findViewById(R.id.imagen);
        guardare= (Button) findViewById(R.id.guardar);
        eliminar= (Button) findViewById(R.id.);
        update= (Button) findViewById(R.id.update);

        guardare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nombre.getText().toString().equals("")&&!precio.getText().toString().equals("")&&!cantidad.getText().toString().equals("")&&!imagen.getText().toString().equals("")) {
                    GuardarRegistro(nombre.getText().toString(), precio.getText().toString(),cantidad.getText().toString(),imagen.getText().toString());
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Debes capturar todos los campos",Toast.LENGTH_SHORT).show();
                }
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarRegistro(nombre.getText().toString());
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarRegistro(precio.getText().toString(),cantidad.getText().toString());
            }
        });
    }

    private void actualizarRegistro( String precios,String cantidades) {
        AdminSQLITEHelper admin = new AdminSQLITEHelper(getApplicationContext(),"OrdinarioBD", null, 1);
        SQLiteDatabase basedatos = admin.getReadableDatabase();
        ContentValues registros =new ContentValues();
        registros.put("precio",precios);
        registros.put("cantidad",cantidades);
        basedatos.update("alumnos",registros,"precio='"+precios+"'",null);
        basedatos.close();
        nombre.setText("");
        precio.setText("");
        cantidad.setText("");
        imagen.setText("");
    }

    private void eliminarRegistro(String nombres) {
        AdminSQLITEHelper admin = new AdminSQLITEHelper(getApplicationContext(),"OrdinarioBD", null, 1);
        SQLiteDatabase basedatos = admin.getReadableDatabase();
        basedatos.delete("productos","nombre='"+nombres+"'",null);
        basedatos.close();
        Toast.makeText(getApplicationContext(),"Registro eliminado con exito",Toast.LENGTH_SHORT).show();
        nombre.setText("");
        precio.setText("");
        cantidad.setText("");
        imagen.setText("");
    }

    private void LeerRegistro() {
        AdminSQLITEHelper admin = new AdminSQLITEHelper(getApplicationContext(),"UniversidadBD", null, 1);
        SQLiteDatabase basedatos = admin.getReadableDatabase();
        try{
            Cursor cursor = basedatos.rawQuery("SELECT * FROM productos",null);
            String cont="";
            while (cursor.moveToNext())
            {
                cont+="Nombre:"+cursor.getString(1)+"        Materia:"+cursor.getString(2)+"\n";
            }
            cursor.close();
            contenidob.setText(cont);
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"ERROR"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void GuardarRegistro(String nombres, String precios,String cantidades,String imagenes) {
        AdminSQLITEHelper admin = new AdminSQLITEHelper(getApplicationContext(),"OrdinarioBD", null, 1);
        SQLiteDatabase basedatos = admin.getReadableDatabase();
        ContentValues registro =new ContentValues();
        registro.put("nombre",nombres);
        registro.put("precio",precios);
        registro.put("cantidad",cantidades);
        registro.put("imagen",imagenes);
        basedatos.insert("productos",null,registro);
        basedatos.close();
        Toast.makeText(getApplicationContext(),"Registro insertado con exito",Toast.LENGTH_SHORT).show();
        nombre.setText("");
        precio.setText("");
        cantidad.setText("");
        imagen.setText("");
    }
}