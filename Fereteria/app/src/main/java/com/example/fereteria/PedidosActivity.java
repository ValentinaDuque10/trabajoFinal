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

public class PedidosActivity extends AppCompatActivity {
    Button btnBuscarPedidoOCliente, btnBuscarPedido, btnClean, btnHacerPedido, btnEliminarPedido, btnLimpiarProductoPedido;
    EditText txtCodPedido, txtFechaPedido, txtCedulaPedido, txtNombrePedido, txtDireccionPedido, txtTelefonoPedido, txtCodProductoPedido, txtCantproductoPedido, txtValorProductoPedido, txtDescripcionPedido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        txtCodPedido = (EditText) findViewById(R.id.txtCodPedido);
        txtCedulaPedido = (EditText) findViewById(R.id.txtCedulaPedido);
        txtNombrePedido = (EditText) findViewById(R.id.txtNombrePedido);
        txtDireccionPedido = (EditText) findViewById(R.id.txtDireccionPedido);
        txtTelefonoPedido = (EditText) findViewById(R.id.txtTelefonoPedido);
        txtFechaPedido = (EditText) findViewById(R.id.txtFechaPedido);
        txtDescripcionPedido = (EditText) findViewById(R.id.txtDescripcionPedido);

        txtTelefonoPedido = (EditText) findViewById(R.id.txtTelefonoPedido);
        txtCodProductoPedido = (EditText) findViewById(R.id.txtCodPedido);
        txtCantproductoPedido = (EditText) findViewById(R.id.txtCantproductoPedido);
        txtValorProductoPedido = (EditText) findViewById(R.id.txtValorProductoPedido);

        btnBuscarPedidoOCliente = (Button) findViewById(R.id.btnBuscarPedidoOCliente);
        btnBuscarPedido = (Button) findViewById(R.id.btnBuscarPedido);
        btnClean = (Button) findViewById(R.id.btnClean);
        btnHacerPedido = (Button) findViewById(R.id.btnHacerPedido);
        btnEliminarPedido = (Button) findViewById(R.id.btnHacerPedido);
        btnLimpiarProductoPedido = (Button) findViewById(R.id.btnLimpiarProductoPedido);


        btnBuscarPedidoOCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtCodPedido.getText().toString().isEmpty() && txtCedulaPedido.getText().toString().isEmpty()){

                    getByIdPedido();
                }
                if (!txtCedulaPedido.getText().toString().isEmpty() && txtCodPedido.getText().toString().isEmpty()){

                    getByIdPedido();
                }
                else{

                    Toast.makeText(getApplicationContext(), "Solo puedes buscar por código de pedido o por cédula", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnHacerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crear();
            }

        });
        btnLimpiarProductoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCodProductoPedido.setText("");
                txtCantproductoPedido.setText("");
                txtValorProductoPedido.setText("");
            }
        });
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCodPedido.setText("");
                txtCedulaPedido.setText("");
                txtNombrePedido.setText("");
                txtDireccionPedido.setText("");
                txtTelefonoPedido.setText("");
                txtCodProductoPedido.setText("");
                txtCantproductoPedido.setText("");
                txtValorProductoPedido.setText("");
            }
        });

        btnEliminarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

    }

    public void crear() {
        Connection _connection = CONN();
        try {
            if (!txtCedulaPedido.getText().toString().isEmpty()){

                if (_connection != null) {
                    PreparedStatement prepareState = _connection.prepareStatement("INSERT INTO pedido Values(?,?,?,?,?,?,)");

                    prepareState.setString(1, txtCodPedido.getText().toString());
                    prepareState.setString(2, txtDescripcionPedido.getText().toString());
                    prepareState.setString(3, txtFechaPedido.getText().toString());
                    prepareState.setString(4, txtCantproductoPedido.getText().toString());
                    prepareState.setString(5, txtCantproductoPedido.getText().toString());
                    prepareState.setString(6, txtCedulaPedido.getText().toString());

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

    public void update() {
        Connection _connection = CONN();

        try {
            if (_connection != null) {
                PreparedStatement prepareState = _connection.prepareStatement("UPDATE producto set codigo_pedido='" + txtCodPedido.getText().toString() + "',descripcion='" + txtDescripcionPedido.getText().toString() + "',fecha='" + txtFechaPedido.getText().toString() + "',cantidad='" + txtCantproductoPedido.getText().toString() + "',codigo_producto='" + txtCodProductoPedido.getText().toString() + "',cedula='" + txtCedulaPedido.getText().toString() + "'");

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
                PreparedStatement prepareState = _connection.prepareStatement("DELETE FROM producto Where codigo_producto= '" + txtCedulaPedido.getText().toString() + "'");
                prepareState.executeUpdate();
                Toast.makeText(getApplicationContext(), "Registro eliminado exitosamente", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Consultar
    public void getByIdPedido() {
        try {
            Connection _connection = CONN();
            Statement prepareState = _connection.createStatement();
            ResultSet rs = prepareState.executeQuery("SELECT*FROM pedido Where codigo_pedido= '" + txtCodPedido.getText().toString() + "'");

            if (rs.next()) {
                txtCodPedido.setText(rs.getString(1));
                txtDescripcionPedido.setText(rs.getString(2));
                txtFechaPedido.setText(rs.getString(3));
                txtCantproductoPedido.setText(rs.getString(4));
                txtCodProductoPedido.setText(rs.getString(5));
                txtCedulaPedido.setText(rs.getString(6));

            }

            Toast.makeText(getApplicationContext(), "Registro cargado exitosamente", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void getByIdCliente() {
        try {
            Connection _connection = CONN();
            Statement prepareState = _connection.createStatement();
            ResultSet rs = prepareState.executeQuery("SELECT*FROM clientes Where cedula= '" + txtCedulaPedido.getText().toString() + "'");

            if (rs.next()) {

                txtCedulaPedido.setText(rs.getString(1));
                txtNombrePedido.setText(rs.getString(2));
                txtDireccionPedido.setText(rs.getString(3));
                txtTelefonoPedido.setText(rs.getString(4));
            }

            Toast.makeText(getApplicationContext(), "Registros cargados exitosamente", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void getByIdProducto() {
        try {
            Connection _connection = CONN();
            Statement prepareState = _connection.createStatement();
            ResultSet rs = prepareState.executeQuery("SELECT*FROM pedido Where codigo_pedido= '" + txtCodPedido.getText().toString() + "'");

            if (rs.next()) {
                txtCodPedido.setText(rs.getString(1));
                txtDireccionPedido.setText(rs.getString(2));
                txtFechaPedido.setText(rs.getString(3));
                txtCodProductoPedido.setText(rs.getString(4));
                txtCedulaPedido.setText(rs.getString(5));

            }

            Toast.makeText(getApplicationContext(), "Registro cargado exitosamente", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



}



