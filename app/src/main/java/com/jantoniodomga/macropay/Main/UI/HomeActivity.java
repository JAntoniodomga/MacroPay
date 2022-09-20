package com.jantoniodomga.macropay.Main.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.jantoniodomga.macropay.Entitys.Session;
import com.jantoniodomga.macropay.MainActivity;
import com.jantoniodomga.macropay.Private.PrivateModuls;
import com.jantoniodomga.macropay.R;
import com.jantoniodomga.macropay.Utils.Constants;
import com.jantoniodomga.macropay.Utils.SharedPreferenceGenerator;
import com.jantoniodomga.macropay.databinding.HomeActivityBinding;

public class HomeActivity extends PrivateModuls {

    private AppBarConfiguration mAppBarConfiguration;
    private HomeActivityBinding binding;
    private Bundle extras;
    private Session session;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extras=getIntent().getExtras();
        SharedPreferences preferences= SharedPreferenceGenerator.getInstance(getApplicationContext());
        session=new Session().fromJWT(preferences.getString(Constants.KEY_SESSION,"null"));

        binding = HomeActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);

        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Solicitando un prestamo.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        if(session!=null)
        {
            ((TextView)navigationView.getHeaderView(0).findViewById(R.id.lblUsernameMail)).setText(session.getEmail());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_settings)
        {
            SharedPreferences preferences=SharedPreferenceGenerator.getInstance(getApplicationContext());
            preferences.edit().clear().commit();
            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            getApplicationContext().startActivity(intent);
            HomeActivity.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}