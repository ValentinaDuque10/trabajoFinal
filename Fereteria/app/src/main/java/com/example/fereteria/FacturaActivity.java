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
import java.sql.Statement;

public class FacturaActivity extends AppCompatActivity {

    Button btnFacturar, btnLimpiarFactura, btnBuscarFactura, btnBuscarPedido, btnEliminarFactura;
    EditText txtCodFactura,txtCodPedido, txtFechaFactura,txtValorFactura;

    String varCedula = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        btnFacturar = (Button) findViewById(R.id.btnHacerPedido);
        btnLimpiarFactura = (Button) findViewById(R.id.btnClean);
        btnBuscarFactura = (Button) findViewById(R.id.btnBuscarPedidoOCliente);
        btnBuscarPedido = (Button) findViewById(R.id.btnBuscarPedido);
        btnEliminarFactura = (Button) findViewById(R.id.btnEliminarPedido);

        txtValorFactura = (EditText) findViewById(R.id.txtValorFactura);
        txtCodFactura = (EditText) findViewById(R.id.txtCodFactura);
        txtCodPedido = (EditText) findViewById(R.id.txtCodPedido);
        txtFechaFactura = (EditText) findViewById(R.id.txtDescripcionPedido);

        txtCodFactura.setText("");
        txtCodPedido.setText("");
        txtFechaFactura.setText("");

        btnBuscarFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnFacturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crear();
            }
        });
        btnLimpiarFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCodFactura.setText("");
                txtCodPedido.setText("");
                txtFechaFactura.setText("");
            }
        });
        btnBuscarFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarFactura();
            }
        });
        btnBuscarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarPedido();
            }
        });
        btnEliminarFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
    }

    public void crear() {
        Connection _connection = CONN();
        try {
            if (!txtCodFactura.getText().toString().isEmpty() && !txtCodPedido.getText().toString().isEmpty()){

                if (_connection != null) {
                    PreparedStatement prepareState = _connection.prepareStatement("INSERT INTO factura Values(?,?,?,?)");

                    prepareState.setString(1, txtCodPedido.getText().toString());
                    prepareState.setString(2, txtFechaFactura.getText().toString());
                    prepareState.setString(3, txtValorFactura.getText().toString());
                    prepareState.setString(4, txtCodPedido.getText().toString());

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

    public void delete() {
        Connection _connection = CONN();
        try {
            if (_connection != null) {
                PreparedStatement prepareState = _connection.prepareStatement("DELETE FROM factura Where codigo_factura= '" + txtCodFactura.getText().toString() + "'");
                prepareState.executeUpdate();
                Toast.makeText(getApplicationContext(), "Registro eliminado exitosamente", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void buscarFactura() {
        try {
            Connection _connection = CONN();
            Statement prepareState = _connection.createStatement();
            ResultSet rs = prepareState.executeQuery("SELECT*FROM factura Where codigo_pedido= '" + txtCodFactura.getText().toString() + "'");

            if (rs.next()) {

                txtCodFactura.setText(rs.getString(1));
                txtCodPedido.setText(rs.getString(2));
                txtFechaFactura.setText(rs.getString(3));
                txtValorFactura.setText(rs.getString(4));
            }

            Toast.makeText(getApplicationContext(), "Registros cargados exitosamente", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void buscarPedido() {
        try {
            Connection _connection = CONN();
            Statement prepareState = _connection.createStatement();
            ResultSet rs = prepareState.executeQuery("SELECT*FROM pedido Where codigo_pedido= '" + txtCodPedido.getText().toString() + "'");

            if (rs.next()) {

                txtCodPedido.setText(rs.getString(1));
            }

            Toast.makeText(getApplicationContext(), "Registros cargados exitosamente", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}