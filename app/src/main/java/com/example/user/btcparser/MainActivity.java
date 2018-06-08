package com.example.user.btcparser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class MainActivity extends AppCompatActivity {

    private Button getBtn;
    private TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    result = (TextView) findViewById(R.id.result);
 


    getBtn = (Button) findViewById(R.id.getBtn);
    getBtn.setOnClickListener(new View.OnClickListener() {



        @Override
        public void onClick(View view) {
            getWebsite();
        }
    });
}

    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect("https://www.investing.com/crypto/bitcoin/btc-usd-historical-data").get();
                    String title = doc.title();
                    Elements links = doc.select("a[href]");

                    builder.append(title).append("\n");

                    for (Element link : links) {
                        builder.append("\n").append("Link : ").append(link.attr("href"))
                                .append("\n").append("Text : ").append(link.text());
                    }
                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {


                    @Override
                    public void run() {
                        result.setText(builder.toString());





                    }
                });
            }
        }).start();
    }
}



