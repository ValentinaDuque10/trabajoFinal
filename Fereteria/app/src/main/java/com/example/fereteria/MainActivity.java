package com.example.fereteria;

import static com.example.fereteria.DB.DBHelper.CONN;

import static java.sql.DriverManager.getConnection;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    Button btnActualizar, btnCrear, btnCargar, btnEliminar, btnLimpiar, btnPedidosActivity, btnFacturasActivity, btnProductosActivity;
    EditText txtCedula, txtNombre, txtDireccion, txtTelefono;

    String varCedula = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnCrear = (Button) findViewById(R.id.btnCrear);
        btnCargar = (Button) findViewById(R.id.btnCargar);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnLimpiar = (Button) findViewById(R.id.btnLimpiar);

        btnPedidosActivity = (Button) findViewById(R.id.btnPedidosActivity);
        btnFacturasActivity = (Button) findViewById(R.id.btnFacturasActivity);
        btnProductosActivity = (Button) findViewById(R.id.btnProductosActivity);

        txtCedula = (EditText) findViewById(R.id.txtCedula);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtDireccion = (EditText) findViewById(R.id.txtDireccion);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);


        txtCedula.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCedula.setText("");
                txtNombre.setText("");
                txtDireccion.setText("");
                txtTelefono.setText("");
            }
        });


        btnPedidosActivity.setOnClickListener(view -> {
            try {
                Intent intent = new Intent (view.getContext(), PedidosActivity.class);
                startActivityForResult(intent, 0);
            }
            catch (Exception e){

                Toast.makeText(getApplicationContext(), "Registro guardado exitosamente", Toast.LENGTH_SHORT).show();

            }
//            Intent intent = new Intent (view.getContext(), PedidosActivity.class);
//            startActivityForResult(intent, 0);
        });
        btnFacturasActivity.setOnClickListener(view -> {
            Intent intent = new Intent (view.getContext(), FacturaActivity.class);
            startActivityForResult(intent, 0);
        });

        btnProductosActivity.setOnClickListener(view -> {
            Intent intent = new Intent (view.getContext(), ProductosActivity.class);
            startActivityForResult(intent, 0);
        });

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getById();
            }
        });

    }

    //Crear
    public void create() {
        Connection _connection = CONN();
        try {
            if (!txtCedula.getText().toString().isEmpty()){

                if (_connection != null) {
                    PreparedStatement prepareState = _connection.prepareStatement("INSERT INTO clientes Values(?,?,?,?)");

                    prepareState.setString(1, txtCedula.getText().toString());
                    prepareState.setString(2, txtNombre.getText().toString());
                    prepareState.setString(3, txtDireccion.getText().toString());
                    prepareState.setString(4, txtTelefono.getText().toString());

                    prepareState.executeUpdate();
                    Toast.makeText(getApplicationContext(), "Registro guardado exitosamente", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "El campo cedula no puede estar vacío", Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Actualizar
    public void update() {
        Connection _connection = CONN();

        try {
            if (_connection != null) {
                PreparedStatement prepareState = _connection.prepareStatement("UPDATE clientes set cedula='" + txtCedula.getText().toString() + "',nombres='" + txtNombre.getText().toString() + "',direccion='" + txtDireccion.getText().toString() + "',telefono='" + txtTelefono.getText().toString() + "'Where cedula= '" + txtCedula.getText().toString() + "'");

                prepareState.executeUpdate();
                Toast.makeText(getApplicationContext(), "Registro actualizado exitosamente", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            Log.e("ERROR", e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Borrar
    public void delete() {
        Connection _connection = CONN();
        try {
            if (varCedula != null || varCedula != ""){

                if (_connection != null) {
                    PreparedStatement prepareState = _connection.prepareStatement("DELETE FROM clientes Where cedula= '" + varCedula + "'");
                    prepareState.executeUpdate();
                    Toast.makeText(getApplicationContext(), "Registro eliminado exitosamente", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                if (_connection != null) {
                    PreparedStatement prepareState = _connection.prepareStatement("DELETE FROM clientes Where cedula= '" + txtCedula.getText().toString() + "'");
                    prepareState.executeUpdate();
                    Toast.makeText(getApplicationContext(), "Registro eliminado exitosamente", Toast.LENGTH_SHORT).show();
                }
            }

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Consultar por id
    public void getById() {
        try {
            Connection _connection = CONN();
            Statement prepareState = _connection.createStatement();
            ResultSet rs = prepareState.executeQuery("SELECT*FROM clientes Where cedula= '" + txtCedula.getText().toString() + "'");

            if (rs.next()) {
                varCedula = (rs.getString(1));
                txtCedula.setText(rs.getString(1));
                txtNombre.setText(rs.getString(2));
                txtDireccion.setText(rs.getString(3));
                txtTelefono.setText(rs.getString(4));
            }

            Toast.makeText(getApplicationContext(), "Registros cargados exitosamente", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}