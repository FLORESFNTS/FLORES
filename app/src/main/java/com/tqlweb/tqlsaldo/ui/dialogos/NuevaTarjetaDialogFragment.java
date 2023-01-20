package com.tqlweb.tqlsaldo.ui.dialogos;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tqlweb.tqlsaldo.R;
import com.tqlweb.tqlsaldo.db.entity.TarjetaEntity;

public class NuevaTarjetaDialogFragment extends DialogFragment {

    private NuevaTarjetaDialogViewModel mViewModel;
    private View view;
    EditText txtnucliente,txtnutarjeta,txtnametarjeta,etxtidenti,etxtregion;
    String nucliente,nutarjeta,leyendauser;
    boolean vacios,nocom;
    Button btnNewCardSave,btnNewCardCancel;
    ImageView scanertarjeta;
    public static  final int SCAN_RESULT=100;

    public static NuevaTarjetaDialogFragment newInstance() {
        return new NuevaTarjetaDialogFragment();
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.nueva_tarjeta_dialog_fragment, null);

        txtnucliente = view.findViewById(R.id.etxtnuclientefrag);
        txtnutarjeta = view.findViewById(R.id.etxtnutarjetafrag);
        txtnametarjeta= view.findViewById(R.id.txtnametarjetafrag);
        btnNewCardSave= view.findViewById(R.id.btnNewCardSave);
        btnNewCardCancel= view.findViewById(R.id.btnNewCardCancel);
        etxtidenti = view.findViewById(R.id.etxtidentificador);
        etxtregion = view.findViewById(R.id.etxtregion);
        //scanertarjeta = view.findViewById(R.id.imgscanner);

        etxtidenti.setEnabled(false);
        etxtregion.setEnabled(false);




        vacios=false;nocom=false;


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Nueva tarjeta");
        builder.setMessage("Completa tu numero de tarjeta");

        builder.setView(view);

        btnNewCardSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vacios=false;nocom=false;
                nucliente = txtnucliente.getText().toString();
                nutarjeta = txtnutarjeta.getText().toString();
                leyendauser= txtnametarjeta.getText().toString();


                if(nucliente.trim().isEmpty()){
                    vacios=true;
                    txtnucliente.setError("No puede ir vacio");
                }
                if(nutarjeta.trim().isEmpty()){
                    vacios=true;
                    txtnutarjeta.setError("No puede ir vacio");
                }
                if(leyendauser.trim().isEmpty()){
                    vacios=true;
                    txtnametarjeta.setError("No puede ir vacio");
                }

                if(nucliente.trim().length()<5){
                    nocom=true;
                    txtnucliente.setError("Completa el campo a 5 digitos");
                }
                if(nutarjeta.trim().length()<5){
                    nocom=true;
                    txtnutarjeta.setError("Completa el campo a 5 digitos");
                }


                if(vacios==true || nocom == true){

                }else{
                    //comunicar a viewmodel el mnuevo dato

                    NuevaTarjetaDialogViewModel mViewModel = new ViewModelProvider(getActivity()).get(NuevaTarjetaDialogViewModel.class);
                    mViewModel.insertarTarjeta(new TarjetaEntity(nucliente,nutarjeta,leyendauser));
                    getDialog().dismiss();

                    Toast.makeText(getActivity(), "Tarjeta 3-99999-"+nucliente+"-"+nutarjeta+" Registrada exitosamente", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnNewCardCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });



        AlertDialog dialogx = builder.create();





        return dialogx;
    }





}

