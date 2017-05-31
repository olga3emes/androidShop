package com.example.olga.shop.rss;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.olga.shop.MainActivity;
import com.example.olga.shop.R;
import com.example.olga.shop.ShoppingCartActivity;

import java.util.List;

public class RssActivity extends AppCompatActivity {

    List<Report> noticias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);
        TextView fuente = (TextView) findViewById(R.id.textFuente);
        String url = "https://flamencolicos.wordpress.com/feed/";
        fuente.setText("FUENTE: Flamencólicos");
        CargarXmlTask tarea = new CargarXmlTask();
        tarea.execute(url);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button arrowBack = (Button) findViewById(R.id.arrow_left);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView tvViewShoppingCart = (TextView)findViewById(R.id.shopping_cart);
        tvViewShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RssActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }




    //Tarea Asíncrona para cargar un XML en segundo plano
    private class CargarXmlTask extends AsyncTask<String,Integer,Boolean> {
        protected Boolean doInBackground(String... params) {
            //RssParserSax parser = new RssParserSax(params[0]);
            RssParserDom parser = new RssParserDom(params[0]);
            noticias = parser.parse();
            return true;
        }

        protected void onPostExecute(Boolean result) {
            System.out.println(noticias.toString());
            TextView txtResultado = (TextView) findViewById(R.id.textoNoticias);
            Report n;
            for(int i=0; i<noticias.size(); i++)
            {
                n = noticias.get(i);
                txtResultado.setText(
                        txtResultado.getText().toString() +
                                System.getProperty("line.separator") + n.toString() +
                                System.getProperty("line.separator"));
            }
        }
    }
}

