package com.example.fereteria;

import static com.example.fereteria.DB.DBHelper.CONN;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductosActivity extends AppCompatActivity {

    Button btnCrearProducto, btnBuscarProducto, btnActualizarProducto, btnLimpiarProducto, btnEliminarProducto;
    EditText txtCodProducto, txtValorProducto, txtDescripcionProducto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        txtCodProducto = (EditText) findViewById(R.id.txtCodProducto);
        txtValorProducto = (EditText) findViewById(R.id.txtValorProducto);
        txtDescripcionProducto = (EditText) findViewById(R.id.txtDescripcionProducto);

        btnCrearProducto = (Button) findViewById(R.id.btnCrearProducto);
        btnBuscarProducto = (Button) findViewById(R.id.btnBuscarProducto);
        btnActualizarProducto = (Button) findViewById(R.id.btnActualizarProducto);
        btnLimpiarProducto = (Button) findViewById(R.id.btnLimpiarProducto);
        btnEliminarProducto = (Button) findViewById(R.id.btnEliminarProducto);



        btnCrearProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create();
            }
        });
        btnBuscarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getById();
            }
        });
        btnActualizarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }

        });
        btnLimpiarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtCodProducto.setText("");
                txtValorProducto .setText("");
                txtDescripcionProducto.setText("");


            }
        });
        btnEliminarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();

            }
        });


    }

    public void create() {
        Connection _connection = CONN();
        try {
            if (!txtCodProducto.getText().toString().isEmpty()){

                if (_connection != null) {
                    PreparedStatement prepareState = _connection.prepareStatement("INSERT INTO producto Values(?,?,?,?,?,?,?,?)");

                    prepareState.setString(1, txtCodProducto.getText().toString());
                    prepareState.setString(2, txtDescripcionProducto.getText().toString());
                    prepareState.setString(3, txtValorProducto.getText().toString());

                    prepareState.executeUpdate();
                    Toast.makeText(getApplicationContext(), "Registro guardado exitosamente", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "No pueden existir campos vacíos", Toast.LENGTH_SHORT).show();
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
                PreparedStatement prepareState = _connection.prepareStatement("UPDATE producto set codigo_producto='" + txtCodProducto.getText().toString() + "',descripcion='" + txtDescripcionProducto.getText().toString() + "',valor='" + txtValorProducto.getText().toString() + "'");

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
            if (_connection != null) {
                PreparedStatement prepareState = _connection.prepareStatement("DELETE FROM producto Where codigo_producto= '" + txtCodProducto.getText().toString() + "'");
                prepareState.executeUpdate();
                Toast.makeText(getApplicationContext(), "Registro eliminado exitosamente", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Consultar
    public void getById() {
        try {
            Connection _connection = CONN();
            Statement prepareState = _connection.createStatement();
            ResultSet rs = prepareState.executeQuery("SELECT*FROM clientes Where codigo_producto= '" + txtCodProducto.getText().toString() + "'");

            if (rs.next()) {
                txtCodProducto.setText(rs.getString(1));
                txtDescripcionProducto.setText(rs.getString(1));
                txtValorProducto.setText(rs.getString(2));

            }

            Toast.makeText(getApplicationContext(), "Registro cargado exitosamente", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}