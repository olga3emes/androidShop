package com.example.olga.shop;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olga.shop.constant.Constant;
import com.example.olga.shop.models.Cart;
import com.example.olga.shop.models.Product;
import com.example.olga.shop.rss.RssActivity;

import tools.CartHelper;

public class ProductActivity extends AppCompatActivity {

    private static final String TAG = "ProductActivity";

    TextView tvProductName;
    TextView tvProductDesc;
    ImageView ivProductImage;
    TextView tvProductPrice;
    Spinner spQuantity;
    Button bOrder;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tvViewShoppingCart = (TextView)findViewById(R.id.shopping_cart);
        tvViewShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
            }
        });
        final Cart cart = CartHelper.getCart();

        final Button badge = (Button) findViewById(R.id.badge_textView);
        badge.setText(String.valueOf(cart.getTotalQuantity()));

        Button arrowBack = (Button) findViewById(R.id.arrow_left);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle data = getIntent().getExtras();
        product = (Product) data.getSerializable("product");

        Log.d(TAG, "Product hashCode: " + product.hashCode());

        retrieveViews();
        setProductProperties();
        initializeQuantity();
        onOrderProduct();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }




    private void retrieveViews() {
        tvProductName = (TextView) findViewById(R.id.tvProductName);
        tvProductDesc = (TextView) findViewById(R.id.tvProductDesc);
        tvProductPrice = (TextView) findViewById(R.id.tvProductPrice);
        ivProductImage = (ImageView) findViewById(R.id.ivProductImage);
        spQuantity = (Spinner) findViewById(R.id.spQuantity);
        bOrder = (Button) findViewById(R.id.bOrder);
    }

    private void setProductProperties() {
        tvProductName.setText(product.getName());
        tvProductDesc.setText(product.getDescription());
        ivProductImage.setImageResource(this.getResources().getIdentifier(product.getImageName(), "drawable", this.getPackageName()));
        tvProductPrice.setText("€ " + product.getPrice().toString());
    }

    private void initializeQuantity() {
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, Constant.QUANTITY_LIST);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spQuantity.setAdapter(dataAdapter);
    }

    private void onOrderProduct() {
        bOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = CartHelper.getCart();
                Log.d(TAG, "Adding product: " + product.getName());
                cart.add(product, Integer.valueOf(spQuantity.getSelectedItem().toString()));

                Intent intent = new Intent(ProductActivity.this, ShoppingCartActivity.class);

                //Create an instance of notification constructor
                // Title and content we can concat the set methods
                NotificationCompat.Builder notif = new NotificationCompat.Builder(getApplicationContext());
                notif.setContentText("New product added to shopping cart.")
                        .setContentTitle("Added: "+ product.getName())
                        .setSmallIcon(R.drawable.logo_72);

                // Action (optional)
                PendingIntent pendingIntent = PendingIntent.getActivity(ProductActivity.this, 0, intent, 0);
                notif.setContentIntent(pendingIntent);

                //ActionButtons (optional)
                notif.addAction(new NotificationCompat.Action.Builder(R.drawable.ic_shopping_cart_24dp, "See on shopping cart", pendingIntent).build());

                //Send notification to system
                NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                nm.notify(1, notif.build());

                Integer quantity= Integer.valueOf(spQuantity.getSelectedItem().toString());
                String items="";
                if(quantity==1){
                   items= " Item added to cart";
                }else{
                    items=" Items added to cart";
                }

                Toast.makeText(getApplicationContext(), spQuantity.getSelectedItem().toString() + items, Toast.LENGTH_LONG).show();

               // nm.cancel(1);     //Cancel the notification with id 1



            }
        });
    }
}
