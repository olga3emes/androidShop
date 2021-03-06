package com.example.olga.shop;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olga.shop.adapters.ProductAdapter;
import com.example.olga.shop.auth.LoginActivity;
import com.example.olga.shop.constant.Constant;
import com.example.olga.shop.models.Cart;
import com.example.olga.shop.models.Product;
import com.example.olga.shop.rss.RssActivity;
import com.example.olga.shop.sqlite.AuthSQLite;

import butterknife.Bind;
import tools.CartHelper;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private AuthSQLite auth = new AuthSQLite(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView tvViewShoppingCart = (TextView)findViewById(R.id.shopping_cart);
        tvViewShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
            }
        });


        ListView lvProducts = (ListView) findViewById(R.id.lvProducts);
        //lvProducts.addHeaderView(getLayoutInflater().inflate(R.layout.product_list_header, lvProducts, false));

        ProductAdapter productAdapter = new ProductAdapter(this);
        productAdapter.updateProducts(Constant.PRODUCT_LIST);

        lvProducts.setAdapter(productAdapter);

        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Product product = Constant.PRODUCT_LIST.get(position);
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", product);
                Log.d("MainActivity", "View product: " + product.getName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        final Cart cart = CartHelper.getCart();

        final Button badge = (Button) findViewById(R.id.badge_textView);
        badge.setText(String.valueOf(cart.getTotalQuantity()));


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private static final int REQUEST_VIDEO = 1;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            else
                intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("video/*");
            startActivityForResult(intent, REQUEST_VIDEO);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_rss) {

            Intent intent = new Intent(MainActivity.this, RssActivity.class);
            startActivity(intent);


        }else if (id == R.id.nav_contact) {

            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }






    private static int RESULT_LOAD_IMG = 2;

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_VIDEO){
            if (requestCode == REQUEST_VIDEO && resultCode == Activity.RESULT_OK) {
                Intent intent = new Intent(this, VideoActivity.class);
                intent.putExtra("video", data.getData());
                startActivity(intent);
            }else{
                Toast.makeText(this, "Any Video picked", Toast.LENGTH_LONG).show();
            }
        }
        if(requestCode== RESULT_LOAD_IMG) {

            super.onActivityResult(requestCode, resultCode, data);
            String imgDecodableString;
            try {
                // When an Image is picked
                if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                        && null != data) {
                    // Get the Image from data
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    isStoragePermissionGranted();
                    // Get the cursor
                        Cursor cursor =
                                getContentResolver()
                                        .query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    ImageView imgView = (ImageView) findViewById(R.id.profileImage);
                    // Set the Image in ImageView after decoding the String and remove drawable of background
                    Bitmap image = BitmapFactory
                            .decodeFile(imgDecodableString);

                    imgView.setBackgroundResource(0);
                    Bitmap scaledImage = scaleDown(image, 240, true);

                    imgView.setImageBitmap(scaledImage);

                } else {
                    Toast.makeText(this, "Any Image picked",
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.i("P","Permission is granted");
                return true;
            } else {

                Log.v("PR","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("PG","Permission is granted");
            return true;
        }
    }

}