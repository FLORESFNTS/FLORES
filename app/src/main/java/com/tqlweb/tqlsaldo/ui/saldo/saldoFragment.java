package com.tqlweb.tqlsaldo.ui.saldo;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tqlweb.tqlsaldo.DashBoardMain;
import com.tqlweb.tqlsaldo.MycustomIntroActivity;
import com.tqlweb.tqlsaldo.R;
import com.tqlweb.tqlsaldo.common.Constantes;
import com.tqlweb.tqlsaldo.common.SharedPreferencesManager;
import com.tqlweb.tqlsaldo.retrofit.response.Saldo;
import com.tqlweb.tqlsaldo.ui.saldo.listatarjetas.storedtarjetaFragment;


public class saldoFragment extends Fragment implements View.OnClickListener, View.OnKeyListener {

    private SaldoViewModel mViewModel;
    //public EditText etxtidenti, etxtregion, etxtnucliente, etxtnutarjeta, etxtnip;
    TextView txtsaldo;
    boolean centinelaeye = false;
    ImageView ImagenTutorial;
    //ImageView eyes;
    String saldoObtenido;
    String mensajeServer;
    storedtarjetaFragment storedtarjetaFragment;
    public static DashBoardMain dash;


    public static saldoFragment newInstance(DashBoardMain dashBoardMain) {
        dash = dashBoardMain;
        return new saldoFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        saldoObtenido = "0";
        mensajeServer = "";
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(SaldoViewModel.class);
        View v = inflater.inflate(R.layout.saldo_fragment, container, false);


        /////creamos lista de tarjetas
        storedtarjetaFragment = storedtarjetaFragment.newInstance(1, dash,this);

        getActivity().
                getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentotarjetas, storedtarjetaFragment).commit();
        /////fin lista de tarjetas

        txtsaldo = v.findViewById(R.id.txtsaldo);
        ImagenTutorial = v.findViewById(R.id.ImagenTutorial);

        ImagenTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ituto = new Intent(getActivity(), MycustomIntroActivity.class);
                startActivity(ituto);
            }
        });

        if (SharedPreferencesManager.getSomeStringvalue(Constantes.GLOBAL_MENSAJE_SERVER).equals("EXITO")) {

            txtsaldo.setText(SharedPreferencesManager.getSomeStringvalue(Constantes.GLOBAL_SALDOCONSULTADO));
            txtsaldo.setBackgroundResource(R.color.verdesemaforo);
        } else {

        }


        mViewModel.respuestaSaldoLiveData.observe(getActivity(), new Observer<Saldo>() {
            @Override
            public void onChanged(Saldo saldo) {
                //btngosaldo.setEnabled(true);
                //Toast.makeText(getContext(), "Saldo: "+saldo.getSaldo(), Toast.LENGTH_LONG).show();

                mensajeServer = saldo.getMensaje();
                //Toast.makeText(getActivity(), mensajeServer, Toast.LENGTH_LONG).show();

                //ERROR_NIP
                //model_invalid (nodebe de salir)
                //EXITO


                if (saldo.getNucl().equals("0") || saldo.getNutj().equals("0")) {

                } else {

                    saldoObtenido = saldo.getSaldo();

                    if (saldoObtenido.equals("999999.0000 Litros") || saldoObtenido.equals("999999.0000 Pesos")) {
                        saldoObtenido = "Libre";
                    }

                    txtsaldo.setText(saldoObtenido);
                    SharedPreferencesManager.setSomeStringValue(Constantes.GLOBAL_SALDOCONSULTADO, saldoObtenido);

                    if (saldo.getMensaje().equals("EXITO")) {
                        txtsaldo.setBackgroundResource(R.color.verdesemaforo);
                        //etxtnip.setText("");
                    } else {
                        txtsaldo.setText(saldo.getMensaje());
                        txtsaldo.setBackgroundResource(R.color.colorPrimary);
                    }
                }


            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SaldoViewModel.class);
    }


    @Override
    public void onClick(View v) {

       /* int elementoclick = v.getId();

        switch (elementoclick) {
            case R.id.image_eyes:
                changeEye();
                break;
            case R.id.btngosaldo:
                //hacer query
                btngosaldo.setEnabled(false);
                obtenerSaldo();
                break;
        }*/

    }

    public void obtenerSaldo() {

        //TODO: validamos que no hayan vacios
        /*String nutarjeta = etxtnutarjeta.getText().toString();
        String nucliente = etxtnucliente.getText().toString();
        String nip = etxtnip.getText().toString();*/
/*
        //utiliza el mismo modelview para que el observer tenga efecto
        SharedPreferencesManager.setSomeStringValue(Constantes.GLOBAL_NUCLIENTE, nucliente);
        SharedPreferencesManager.setSomeStringValue(Constantes.GLOBAL_NUTARJETA, nutarjeta);
        mViewModel.getSaldo(nutarjeta, nucliente, nip);*/

    }


    public void obtenerSaldo(String nip,String nucliente,String nutarjeta) {

        //utiliza el mismo modelview para que el observer tenga efecto
        SharedPreferencesManager.setSomeStringValue(Constantes.GLOBAL_NUCLIENTE, nucliente);
        SharedPreferencesManager.setSomeStringValue(Constantes.GLOBAL_NUTARJETA, nutarjeta);
        mViewModel.getSaldo(nutarjeta, nucliente, nip);

    }






    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        /*int id = v.getId();

        switch (id) {
            case R.id.etxtnucliente:

                if (etxtnucliente.getText().length() == 5) {
                    etxtnutarjeta.requestFocus();
                    etxtnutarjeta.selectAll();
                } else {

                }
                break;
            case R.id.etxtnutarjeta:
                if (etxtnutarjeta.getText().length() == 5) {
                    etxtnip.requestFocus();
                    etxtnip.selectAll();
                } else {

                }
                break;


        }*/

        return false;
    }





}