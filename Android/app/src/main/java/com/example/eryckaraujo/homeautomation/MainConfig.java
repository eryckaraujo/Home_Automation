package com.example.eryckaraujo.homeautomation;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainConfig extends AppCompatActivity {

    Button btnSalvar;
    EditText editIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_config);

        editIp = (EditText)findViewById(R.id.editIp);
        btnSalvar = (Button)findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ip = editIp.getText().toString();

                if (ip.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Digite o Endereço do Servidor", Toast.LENGTH_LONG).show();
                } else {

                    if (ip.contains("http://")){

                        salvar(ip);
                    }
                    else {

                        ip = "http://" + ip;
                        salvar(ip);
                    }
                }
            }
        });
    }

    public void salvar (String endereco){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("Servidor", endereco);
        editor.commit();
        Toast.makeText(getApplicationContext(), "Configuração Salva com Sucesso", Toast.LENGTH_LONG).show();
        finish();

    }
}
