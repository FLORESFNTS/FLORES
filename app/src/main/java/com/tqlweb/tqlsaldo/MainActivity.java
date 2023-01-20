package com.tqlweb.tqlsaldo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tqlweb.tqlsaldo.common.Constantes;
import com.tqlweb.tqlsaldo.common.SharedPreferencesManager;

public class MainActivity extends AppCompatActivity {
    Button gosaldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gosaldo = findViewById(R.id.btnirconsultar);

        SharedPreferencesManager.cleanPreferences();

        iniciarPreferences();

        getSupportActionBar().hide();




    }

    private void iniciarPreferences() {

        SharedPreferencesManager.setSomeBooleanValue(Constantes.CONSULTA_SALDO, false);
        SharedPreferencesManager.setSomeStringValue(Constantes.GLOBAL_NUCLIENTE, "0");
        SharedPreferencesManager.setSomeStringValue(Constantes.GLOBAL_NUTARJETA, "0");
        SharedPreferencesManager.setSomeStringValue(Constantes.GLOBAL_SALDOCONSULTADO, "");
        SharedPreferencesManager.setSomeStringValue(Constantes.GLOBAL_MENSAJE_SERVER, "");
    }


    public void linkPoliticaclick(View view) {


        String url = "https://clientescentro.tanquelleno.com/Mapa/Politica_privacidad";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);


    }


    public void iraDashboard(View view) {
        gosaldo.setEnabled(false);




        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this
        );
        builder.setTitle(R.string.local_acces);
        builder.setMessage(R.string.warning_local)
                .setPositiveButton("CERRAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Intent a = new Intent(MainActivity.this, DashBoardMain.class);
                        startActivity(a);
                        finish();

                    }
                }).setCancelable(false);

        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        dialog.show();


    }


    public void apretetqlsaldo(View view) {


    }


}