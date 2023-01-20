package com.tqlweb.tqlsaldo.ui.saldo.listatarjetas;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.tqlweb.tqlsaldo.DashBoardMain;
import com.tqlweb.tqlsaldo.R;
import com.tqlweb.tqlsaldo.common.MyApp;
import com.tqlweb.tqlsaldo.db.entity.TarjetaEntity;
import com.tqlweb.tqlsaldo.ui.dialogos.ConsultasDialogFragment;
import com.tqlweb.tqlsaldo.ui.dialogos.NuevaTarjetaDialogViewModel;
import com.tqlweb.tqlsaldo.ui.saldo.saldoFragment;

import java.util.List;

public class MystoredtarjetaRecyclerViewAdapter extends RecyclerView.Adapter<MystoredtarjetaRecyclerViewAdapter.ViewHolder> {

    private  List<TarjetaEntity> mValues;
    DashBoardMain ctx;
    saldoFragment saldox;
    NuevaTarjetaDialogViewModel nuevaTarjetaDialogViewModel;

    public MystoredtarjetaRecyclerViewAdapter(List<TarjetaEntity> items, DashBoardMain context,saldoFragment saldo) {
        Log.println(Log.ERROR,"AVG","constructor 1");
        mValues = items;
        ctx = context;
        saldox = saldo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.println(Log.ERROR,"AVG","OCVH stroedadapter");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_starjeta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mValues != null){
            holder.mItem = mValues.get(position);
            holder.mContentView.setText(holder.mItem.getLeyenda());
            holder.txttarjetanucliente.setText(holder.mItem.getNucliente());
            holder.txttarjetanutarjeta.setText(holder.mItem.getNutarjeta());


            holder.imageTarjetabig.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Toast.makeText(MyApp.getContext(), "indice: "+position+" tarjeta"+holder.mItem.getNutarjeta(), Toast.LENGTH_SHORT).show();

                    FragmentManager frm = ctx.getSupportFragmentManager();
                    ConsultasDialogFragment gialognuevanota = ConsultasDialogFragment.newInstance(saldox,holder.mItem.getNucliente(),holder.mItem.getNutarjeta());
                    gialognuevanota.show(frm,"IngresaNipTarjeta");
                }
            });



        }else{
            Log.println(Log.ERROR,"AVG","mvalus vacio");
        }

    }

public List<TarjetaEntity> getmValues(){
        return mValues;
}


    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public void seekAndRemove(int adapterPosition,NuevaTarjetaDialogViewModel viewmodelstanciado) {
        nuevaTarjetaDialogViewModel=viewmodelstanciado;

        nuevaTarjetaDialogViewModel.removefromDb(mValues.get(adapterPosition).getIdtarjeta());

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public TextView txttarjetanucliente;
        public TextView txttarjetanutarjeta;
        public TextView txttarjetaverif;
        public ImageView imageTarjetabig;
        public TarjetaEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageTarjetabig  = view.findViewById(R.id.imgtarjetabig);
            mContentView = (TextView) view.findViewById(R.id.txtleyenda);
            txttarjetanucliente = view.findViewById(R.id.txttarjetanucliente);
            txttarjetanutarjeta = view.findViewById(R.id.txttarjetanutarjeta);
            txttarjetaverif = view.findViewById(R.id.txttarjetaverif);

            Typeface typeface = Typeface.createFromAsset(MyApp.getContext().getAssets(),"creditcardfont.ttf");
            Typeface typefacenormal = Typeface.createFromAsset(MyApp.getContext().getAssets(),"ptmono.ttf");
            mContentView.setTypeface(typefacenormal);
            txttarjetaverif.setTypeface(typeface);
            txttarjetanucliente.setTypeface(typeface);
            txttarjetanutarjeta.setTypeface(typeface);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }


    public void SetNuevasTarjetas(List<TarjetaEntity> nuevasNotaEntities,RecyclerView recyclerView){
        this.mValues =nuevasNotaEntities;
        notifyDataSetChanged();


        //mueve posicion al ultimo
        recyclerView.scrollToPosition(this.mValues.size()-1);
        //notifyItemInserted( 2);
    }






}