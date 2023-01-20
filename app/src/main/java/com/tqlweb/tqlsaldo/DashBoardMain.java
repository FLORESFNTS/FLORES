package com.tqlweb.tqlsaldo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.tqlweb.tqlsaldo.common.MyApp;
import com.tqlweb.tqlsaldo.ui.consumos.CargaListFragment;
import com.tqlweb.tqlsaldo.ui.dialogos.ConsultasDialogFragment;
import com.tqlweb.tqlsaldo.ui.dialogos.NuevaTarjetaDialogFragment;
import com.tqlweb.tqlsaldo.ui.mapa.MapsFragment;
import com.tqlweb.tqlsaldo.ui.saldo.listatarjetas.storedtarjetaFragment;
import com.tqlweb.tqlsaldo.ui.saldo.saldoFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class DashBoardMain extends AppCompatActivity implements PermissionListener {

    BottomNavigationView navigationView;
    TextView titulopprincipal,txtsintarjetas;
    MyApp myApp;
    MapsFragment mapsFragment;
    CargaListFragment cargaListFragment;
    saldoFragment safra;
    storedtarjetaFragment storedtarjetaFragment;
    ExtendedFloatingActionButton fab;
    DashBoardMain dashBoardMain;
    NuevaTarjetaDialogFragment nuevaTarjetaDialogFragment;
    LottieAnimationView lottieArrowDashboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        myApp = new MyApp();//instanciamos para llmar el contexto donde sea
        dashBoardMain = this;
        setContentView(R.layout.activity_dash_board_main);
        titulopprincipal = findViewById(R.id.textViewToolbar);
        navigationView = findViewById(R.id.nav_view);
        mapsFragment = new MapsFragment();
        cargaListFragment = new CargaListFragment();
        safra = saldoFragment.newInstance(this);
        txtsintarjetas = findViewById(R.id.txtSinTarjetas);
        lottieArrowDashboard = findViewById(R.id.arrowjson1);

        mapsFragment.getLocationPermission(DashBoardMain.this);



        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                FragmentManager frm = dashBoardMain.getSupportFragmentManager();
                NuevaTarjetaDialogFragment gialognuevanota = nuevaTarjetaDialogFragment.newInstance();
                gialognuevanota.show(frm,"IngresaNuevaTarjeta");



            }
        });


        getSupportActionBar().hide();
        titulopprincipal.setText("Consulta saldo disponible");
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentcontainer, safra).commit();
            titulopprincipal.setText("Consulta saldo disponible");
        }



        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment f = null;


                switch (menuItem.getItemId()) {
                    case R.id.navigation_saldo:
                        //safra.revTut();
                        f =safra;

                        titulopprincipal.setText("Consulta saldo disponible");
                        fab.show();
                        break;
                    case R.id.navigation_consumos:
                        //f = TweetListFragment.newInstance(Constantes.TWEET_LIST_ALL);
                        f = cargaListFragment;
                        titulopprincipal.setText("Consumos recientes");
                        OcultarTutorial1();
                        fab.hide();
                        break;
                    case R.id.navigation_mapa:
                        f = mapsFragment;
                        titulopprincipal.setText("Ubica tu estación");
                        OcultarTutorial1();
                        fab.hide();
                        break;
                }


                if (f != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.fragment_close_enter, R.anim.fragment_fade_exit)
                            .replace(R.id.fragmentcontainer, f)
                            .commit();
                }


                return true;//ppara que se active el icono en azul

            }
        });


    }

    @Override
    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
        if (permissionGrantedResponse.getPermissionName().contains("ACCESS_FINE_LOCATION")) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fragment_close_enter, R.anim.fragment_fade_exit)
                    .replace(R.id.fragmentcontainer,new MapsFragment())
                    .commit();



        } else {

        }


    }

    @Override
    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

    }


    public void OcultarTutorial1(){
        txtsintarjetas.setVisibility(View.INVISIBLE);
        lottieArrowDashboard.setVisibility(View.INVISIBLE);
    }

    public void MostrarTutorial1(){
        txtsintarjetas.setVisibility(View.VISIBLE);
        lottieArrowDashboard.setVisibility(View.VISIBLE);
    }






}