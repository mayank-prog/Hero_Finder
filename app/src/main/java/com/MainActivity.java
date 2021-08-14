package com;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.hero.finder.herofinder.R;
import com.hero.finder.herofinder.RegisterActivity;
import com.hero.finder.herofinder.ui.gallery.GalleryViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.razorpay.PaymentResultListener;

public class MainActivity extends AppCompatActivity implements PaymentResultListener ,NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration ;
    private GalleryViewModel galleryViewModel;

    FirebaseAuth mAuth;
    FirebaseUser CurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent)); //color for meow bar holder data

        mAuth =FirebaseAuth.getInstance();
        CurrentUser = mAuth.getCurrentUser();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        BottomNavigationView navBottomView = findViewById(R.id.bottom_navigation_view); //add this for bottom nav
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navigationView.setNavigationItemSelectedListener(this);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(navBottomView, navController); //ad this for bottom nav_bar
        updateNavHeader();
        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        galleryViewModel.setOnSuccess(false);
        galleryViewModel.setOnError(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void updateNavHeader() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View Headerview = navigationView.getHeaderView(0);
        TextView navBarUserName  = Headerview.findViewById(R.id.Name_nav);
        TextView navBarUserEmail  = Headerview.findViewById(R.id.Email_nav);
        // nav_Data user
        navBarUserName.setText("Hi Hero");
        navBarUserEmail.setText(CurrentUser.getEmail());

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int Id = item.getItemId();
        item.setChecked(true);
        if(Id == R.id.nav_logout){
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPaymentSuccess(String s) {
        PaymentSuccess paymentSuccess = new PaymentSuccess(s);

        galleryViewModel.setPaymentSuccess(paymentSuccess);
        galleryViewModel.setOnSuccess(true);
    }

    @Override
    public void onPaymentError(int i, String s) {
        PaymentError paymentError = new PaymentError(i,s);

        galleryViewModel.setPaymentError(paymentError);
        galleryViewModel.setOnError(true);
    }
}