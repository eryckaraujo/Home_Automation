package com.example.eryckaraujo.homeautomation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnSala, btnCozinha, btnQuarto1, btnQuarto2, btnBanheiro, btnGaragem, btnJardim, btnConfig;
    TextView txtResultado;
    String servidor = "";
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSala = (Button)findViewById(R.id.btnSala);
        btnCozinha = (Button)findViewById(R.id.btnCozinha);
        btnQuarto1 = (Button)findViewById(R.id.btnQuarto1);
        btnQuarto2 = (Button)findViewById(R.id.btnQuarto2);
        btnBanheiro = (Button)findViewById(R.id.btnBanheiro);
        btnGaragem = (Button)findViewById(R.id.btnGaragem);
        btnJardim =  (Button)findViewById(R.id.btnJardim);
        btnConfig = (Button)findViewById(R.id.btnConfig);

        txtResultado = (TextView)findViewById(R.id.txtResultado);

        handler.postDelayed(atualizaStatus, 0);

        btnSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                solicita("LedSala");
            }
        });

        btnCozinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                solicita("LedCozinha");
            }
        });

        btnQuarto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                solicita("LedQuarto1");
            }
        });

        btnQuarto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                solicita("LedQuarto2");
            }
        });

        btnBanheiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                solicita("LedBanheiro");
            }
        });

        btnGaragem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                solicita("LedGaragem");
            }
        });

        btnJardim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                solicita("LedJardim");
            }
        });

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreConfig = new Intent(MainActivity.this, MainConfig.class);
                startActivity(abreConfig);
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Config", Context.MODE_PRIVATE);
        servidor = sharedPref.getString("servidor", "0");

        if (servidor.equals("0")){
            solicitaservidor();
        } else{
            handler.postDelayed(atualizaStatus, 0);
        }
    }

    public void solicita (String comando){

       ConnectivityManager connMgr = (ConnectivityManager)
       getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        String url = servidor + "/" + comando;
        txtResultado.setText("URL: " + url);

        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute("http://192.168.0.5:80");
        } else {
            txtResultado.setText("Nenhum Conexão Encontrada!!");
        }
    }

    private Runnable atualizaStatus = new Runnable() {
        @Override
        public void run() {
            solicita("");
            handler.postDelayed(this, 2000);
        }
    };

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            Conexao conexao = new Conexao();

            return conexao.GetArduino(urls[0]);

        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if (result != null){
                txtResultado.setText(result);

                if (result.contains("LedSala - Ligado")){
                   // btnSala.setText("Sala - ON");
                    btnSala.setBackgroundResource(R.drawable.toggle_1);
                }
                if (result.contains("LedSala - Desligado")){
                   // btnSala.setText("Sala - OFF");
                    btnSala.setBackgroundResource(R.drawable.toggle_2);
                }

                if (result.contains("LedCozinha - Ligado")){
                   // btnSala.setText("Cozinha - ON");
                    btnSala.setBackgroundResource(R.drawable.toggle_1);
                }
                if (result.contains("LedCozinha - Desligado")){
                  //  btnSala.setText("Cozinha - OFF");
                    btnSala.setBackgroundResource(R.drawable.toggle_2);
                }

                if (result.contains("LedQuarto1 - Ligado")){
                   // btnSala.setText("Quarto1 - ON");
                    btnSala.setBackgroundResource(R.drawable.toggle_1);
                }
                if (result.contains("LedQuarto1 - Desligado")){
                  //  btnSala.setText("Quarto1 - OFF");
                    btnSala.setBackgroundResource(R.drawable.toggle_2);
                }

                if (result.contains("LedQuarto2 - Ligado")){
                   // btnSala.setText("Quarto2 - ON");
                    btnSala.setBackgroundResource(R.drawable.toggle_1);
                }
                if (result.contains("LedQuarto2 - Desligado")){
                  //  btnSala.setText("Quarto2 - OFF");
                    btnSala.setBackgroundResource(R.drawable.toggle_2);
                }

                if (result.contains("LedBanheiro - Ligado")){
                   // btnSala.setText("Banheiro - ON");
                    btnSala.setBackgroundResource(R.drawable.toggle_1);
                }
                if (result.contains("LedBanheiro - Desligado")){
                    //btnSala.setText("Banheiro - OFF");
                    btnSala.setBackgroundResource(R.drawable.toggle_2);
                }

                if (result.contains("LedGaragem - Ligado")){
                    //btnSala.setText("Garagem - ON");
                    btnSala.setBackgroundResource(R.drawable.toggle_1);
                }
                if (result.contains("LedGaragem - Desligado")){
                    //btnSala.setText("Garagem - OFF");
                    btnSala.setBackgroundResource(R.drawable.toggle_2);
                }

                if (result.contains("LedJardim - Ligado")){
                   // btnSala.setText("Jardim - ON");
                    btnSala.setBackgroundResource(R.drawable.toggle_1);
                }
                if (result.contains("LedJardim - Desligado")){
                   // btnSala.setText("Jardim - OFF");
                    btnSala.setBackgroundResource(R.drawable.toggle_2);
                }

            }
            else {
                txtResultado.setText("Ocorreu um Erro!!");
            }
        }
    }

    public void solicitaservidor(){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Erro no Servidor");
        builder.setMessage("Nenhum endereço está cadastrado, deseja inserir um novo agora?");

        builder.setPositiveButton("Sim ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent abreConfig = new Intent(MainActivity.this, MainConfig.class);
                        startActivity(abreConfig);
                    }
                });

        builder.setNegativeButton("Sair", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        finish();
                    }
                });
        builder.show();
    }
}
