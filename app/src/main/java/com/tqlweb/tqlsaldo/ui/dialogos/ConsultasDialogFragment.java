package com.tqlweb.tqlsaldo.ui.dialogos;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tqlweb.tqlsaldo.R;
import com.tqlweb.tqlsaldo.ui.saldo.saldoFragment;

public class ConsultasDialogFragment extends DialogFragment implements View.OnClickListener {

    private ConsultasViewModel mViewModel;
    private View view;
    public static saldoFragment saldox;
    Button btngosaldo;
    ImageView eyes;
    boolean centinelaeye;
    TextView etxtnip;
    static String lleganucliente,llegatarjeta;
    String nipfragment;

    public static ConsultasDialogFragment newInstance(saldoFragment saldoFragment, String nucl, String nutj) {
        saldox=saldoFragment;
        lleganucliente= nucl;
        llegatarjeta=nutj;
        return new ConsultasDialogFragment();
    }

    /*
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.consultas_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ConsultasViewModel.class);

    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        centinelaeye=false;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Consulta saldo");
        builder.setMessage("Introduzca NIP de la tarjeta");

               /* .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {*/

                       /* String titulo = etitulo.getText().toString();
                        String contenido = econtenido.getText().toString();
                        String color = "azul";

                        switch (rgcolor.getCheckedRadioButtonId()){
                            case R.id.radioButtonrojo:
                                color ="rojo";
                                break;
                            case R.id.radioButtonverde:
                                color ="verde";
                                break;
                            case R.id.radioButtonazul:
                                color ="azul";
                                break;
                        }
                        boolean favorita = aSwitch.isChecked();


                        //comunicar a viewmodel el mnuevo dato
                        NuevaNotaDialogViewModel mViewModel = new ViewModelProvider(getActivity()).get(NuevaNotaDialogViewModel.class);
                        mViewModel.insertarNota(new NotaEntity(titulo,contenido,favorita,color));
                        dialog.dismiss();*/
/*


                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });*/
        // Create the AlertDialog object and return it

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.consultas_fragment,null);

/*
        etitulo = view.findViewById(R.id.editexttitulo);
        econtenido= view.findViewById(R.id.editTextcontenido);
        rgcolor = view.findViewById(R.id.Radiogroupcolor);
        aSwitch = view.findViewById(R.id.switchnotafavorita);*/

        etxtnip = view.findViewById(R.id.etxtnipfragment);
        eyes = view.findViewById(R.id.image_eyesfragment);
        btngosaldo = view.findViewById(R.id.btngosaldofragment);

        eyes.setOnClickListener(this);
        btngosaldo.setOnClickListener(this);


        builder.setView(view);


        return builder.create();
    }


    @Override
    public void onClick(View v) {
        int elementoclick = v.getId();

        switch (elementoclick) {
            case R.id.image_eyesfragment:
                changeEye();
                break;
            case R.id.btngosaldofragment:
                //hacer query
                btngosaldo.setEnabled(false);
                mandarPrincipalandClick();
                break;
        }
    }

    private void mandarPrincipalandClick() {


        nipfragment = etxtnip.getText().toString();


        if(nipfragment.trim().isEmpty()){
            etxtnip.setError("Ingresa el NIP");
            btngosaldo.setEnabled(true);
        }else{

            if(nipfragment.trim().length() != 4){
                etxtnip.setError("NIP a 4 digitos");
                btngosaldo.setEnabled(true);
            }else{
                //Toast.makeText(getContext(), "nucl: "+lleganucliente+ " nutj:  "+llegatarjeta+"  nip "+nipfragment, Toast.LENGTH_SHORT).show();
                //validar antes de mandar datos a principal
                etxtnip.clearFocus();
                saldox.obtenerSaldo(nipfragment,lleganucliente,llegatarjeta);
                dismiss();
            }
        }




    }


    private void changeEye() {
        if (centinelaeye == false) {
            centinelaeye = true;
            eyes.setImageResource(R.drawable.ic_visibility_off_);
            //regresa numeros
            etxtnip.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            centinelaeye = false;
            eyes.setImageResource(R.drawable.ic__visibility_on);
            //regresa puntos
            etxtnip.setTransformationMethod(PasswordTransformationMethod.getInstance());


        }
    }













}