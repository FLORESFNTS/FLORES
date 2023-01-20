package com.tqlweb.tqlsaldo.ui.consumos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tqlweb.tqlsaldo.DashBoardMain;
import com.tqlweb.tqlsaldo.R;
import com.tqlweb.tqlsaldo.common.Constantes;
import com.tqlweb.tqlsaldo.common.SharedPreferencesManager;
import com.tqlweb.tqlsaldo.retrofit.response.Carga;
import com.tqlweb.tqlsaldo.ui.saldo.SaldoViewModel;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CargaListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    MycargaRecyclerViewAdapter adapter ;
    List<Carga> cargaList;
    SaldoViewModel saldoViewModel;

    public CargaListFragment() {
    }


    public static CargaListFragment newInstance(int columnCount) {
        CargaListFragment fragment = new CargaListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saldoViewModel = new ViewModelProvider(getActivity()).get(SaldoViewModel.class);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carga_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            adapter = new MycargaRecyclerViewAdapter(getActivity(),cargaList);
            recyclerView.setAdapter(adapter);
            //LoadCargaAdapter("93298","00001");//cargamos

            if(SharedPreferencesManager.getSomeBooleanvalue(Constantes.CONSULTA_SALDO) == true)//solo entra si consultar saldo es exitoso
            {
                //Toast.makeText(context, "es true la consulta", Toast.LENGTH_SHORT).show();
                LoadCargaAdapter(SharedPreferencesManager.getSomeStringvalue(Constantes.GLOBAL_NUCLIENTE),SharedPreferencesManager.getSomeStringvalue(Constantes.GLOBAL_NUTARJETA));
                //ponemos como que no se haya hecho una consulta
                SharedPreferencesManager.setSomeBooleanValue(Constantes.CONSULTA_SALDO,false);
                /*
                if(cargaList ==null){
                    Log.println(Log.ERROR,"LFRAG","es nulo , busca cargas");
                    LoadCargaAdapter(SharedPreferencesManager.getSomeStringvalue(Constantes.GLOBAL_NUCLIENTE),SharedPreferencesManager.getSomeStringvalue(Constantes.GLOBAL_NUTARJETA));
                    //ponemos como que no se haya hecho una consulta
                    SharedPreferencesManager.setSomeBooleanValue(Constantes.CONSULTA_SALDO,false);
                }else{
                    Log.println(Log.ERROR,"LFRAG","ya tenia cargas");
                }*/



            }else{
                if(cargaList ==null){
                    Toast.makeText(context, "Para visualizar los consumos , primero consulta el saldo de tu tarjeta Tanque lleno", Toast.LENGTH_LONG).show();
                }else{

                    if(SharedPreferencesManager.getSomeStringvalue(Constantes.GLOBAL_MENSAJE_SERVER).equals("EXITO")){

                    }else{
                        ListaNula();
                    }
                   //

                }

            }


        }

        return view;
    }

    private void ListaNula() {
        cargaList = new List<Carga>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(@Nullable Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<Carga> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] a) {
                return null;
            }

            @Override
            public boolean add(Carga carga) {
                return false;
            }

            @Override
            public boolean remove(@Nullable Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends Carga> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, @NonNull Collection<? extends Carga> c) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Carga get(int index) {
                return null;
            }

            @Override
            public Carga set(int index, Carga element) {
                return null;
            }

            @Override
            public void add(int index, Carga element) {

            }

            @Override
            public Carga remove(int index) {
                return null;
            }

            @Override
            public int indexOf(@Nullable Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(@Nullable Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<Carga> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<Carga> listIterator(int index) {
                return null;
            }

            @NonNull
            @Override
            public List<Carga> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        adapter.setData(cargaList);
    }


    private void LoadCargaAdapter(String nucliente,String nutarjeta) {

        //observar cuando recibimos una lista de datos
        saldoViewModel.getCargas(nucliente,nutarjeta).observe(getActivity(), new Observer<List<Carga>>() {

            @Override
            public void onChanged(List<Carga> cargas) {
                cargaList = cargas;
                adapter.setData(cargaList);

            }
        });

    }




    private void LoadNewCargaData(String nucliente,String nutarjeta) {

        //observar cuando recibimos una lista de datos
        saldoViewModel.getNewCargas(nucliente,nutarjeta).observe(getActivity(), new Observer<List<Carga>>() {

            @Override
            public void onChanged(List<Carga> cargas) {
                cargaList = cargas;
                //swipeRefreshLayout.setRefreshing(false);
                adapter.setData(cargaList);
                //desactivar observer

                saldoViewModel.getNewCargas(nucliente,nutarjeta).removeObserver(this);
            }
        });

    }











}