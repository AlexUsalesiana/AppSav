package com.roddwy.appsav.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.roddwy.appsav.R;
import com.roddwy.appsav.interfaces.DrawerController;
import com.roddwy.appsav.views.fragments.FormVetAssistedFragment;
import com.roddwy.appsav.views.fragments.InicioFragment;
import com.roddwy.appsav.views.fragments.ListVetAssistedFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerController {

    NavigationView nvView;
    Toolbar tb;
    DrawerLayout dl;

    FragmentTransaction transaction;
    Fragment inicioFragment, listVetAssistedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nvView = findViewById(R.id.navView);
        nvView.setNavigationItemSelectedListener(this);

        tb = findViewById(R.id.toolbar);
        toolbar();

        dl = findViewById(R.id.drawerLayout);

        inicioFragment = new InicioFragment();
        listVetAssistedFragment = new ListVetAssistedFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.containerFragment,inicioFragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        transaction = getSupportFragmentManager().beginTransaction();
        switch (id){
            case R.id.home:
                Toast.makeText(MainActivity.this,"INICIO",Toast.LENGTH_SHORT).show();
                transaction.replace(R.id.containerFragment,inicioFragment);
                transaction.addToBackStack(null);
                break;
            case R.id.registerCattleSysExp:
                Toast.makeText(MainActivity.this,"Registro por sistema Experto",Toast.LENGTH_SHORT).show();
                break;
            case R.id.registerCattleVet:
                Toast.makeText(MainActivity.this,"Registro por Veterinario",Toast.LENGTH_SHORT).show();
                transaction.replace(R.id.containerFragment,listVetAssistedFragment);
                transaction.addToBackStack(null);
                break;
        }
        transaction.commit();
        return false;
    }

    public void toolbar(){
        if(tb != null){
            setSupportActionBar(tb);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setTitle("SAV");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idItem  = item.getItemId();
        switch (idItem){
            case android.R.id.home:
                dl.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setDrawer_Locked() {
        dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void setDrawer_UnLocked() {
        dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
}