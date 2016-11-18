package com.example.eryckaraujo.homeautomation;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Eryck Araujo on 12/11/2016.
 */

public class Conexao {

    private String dados = null;
    public String GetArduino(String urlString){
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            if(httpURLConnection.getResponseCode() == 200){

                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                StringBuilder stringBuilder = new StringBuilder();

                String linha;

                while((linha = bufferedReader.readLine()) != null){
                    stringBuilder.append(linha);
                }
                dados = stringBuilder.toString();
                httpURLConnection.disconnect();
            }
        } catch (IOException erro){

            return null;
                    }
        return dados;
    }
}
