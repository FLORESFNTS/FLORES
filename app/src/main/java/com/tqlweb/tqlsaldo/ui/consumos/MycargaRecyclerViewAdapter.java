package com.tqlweb.tqlsaldo.ui.consumos;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tqlweb.tqlsaldo.R;
import com.tqlweb.tqlsaldo.retrofit.response.Carga;
import com.tqlweb.tqlsaldo.ui.saldo.SaldoViewModel;

import java.util.List;


public class MycargaRecyclerViewAdapter extends RecyclerView.Adapter<MycargaRecyclerViewAdapter.ViewHolder> {

    private  List<Carga> mValues;
    private Context ctx;
    SaldoViewModel saldoViewModel;

    public MycargaRecyclerViewAdapter(Context context,List<Carga> items) {
        mValues = items;
        ctx = context;
        saldoViewModel = new ViewModelProvider((FragmentActivity) ctx).get(SaldoViewModel.class);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_carga, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if(mValues != null){
            holder.mItem = mValues.get(position);

            holder.txtCarganufolio.setText(holder.mItem.getNufolio());
            holder.txtCargaescarga.setText(holder.mItem.getEscarga());
            holder.txtCarganutarjeta.setText(holder.mItem.getNutarjeta());
            holder.txtCargafecha.setText(holder.mItem.getFecha());
            holder.txtCargahora.setText(holder.mItem.getHoracarga());
            holder.txtCargaplacas.setText(holder.mItem.getPlacacarga());
            holder.txtCargaimporte.setText(holder.mItem.getImporte());
        }else{
            Log.println(Log.ERROR,"AVG","mvalus vacio");
        }
    }

    @Override
    public int getItemCount() {
        if (mValues != null){
            return mValues.size();
        }else{
            return 0;
        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView txtCarganufolio;
        public TextView txtCargaescarga;
        public TextView txtCarganutarjeta;
        public TextView txtCargafecha;
        public TextView txtCargahora;
        public TextView txtCargaplacas;
        public TextView txtCargaimporte;
        public Carga mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtCarganufolio = view.findViewById(R.id.txtCargaNufolio);
            txtCarganutarjeta = view.findViewById(R.id.txtCarganutarjeta);
            txtCargaescarga = view.findViewById(R.id.txtCargaescarga);
            txtCargafecha = view.findViewById(R.id.txtCargafecha);
            txtCargahora = view.findViewById(R.id.txtCargahora);
            txtCargaplacas = view.findViewById(R.id.txtCargaplacas);
            txtCargaimporte = view.findViewById(R.id.txtCargaimporte);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txtCarganufolio.getText() + "'";
        }


    }

    public void setData(List<Carga> cargaLista){

        this.mValues = cargaLista;
        //repintar lista
        notifyDataSetChanged();

    }





}