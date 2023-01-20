package com.tqlweb.tqlsaldo.ui.saldo.listatarjetas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.tqlweb.tqlsaldo.DashBoardMain;
import com.tqlweb.tqlsaldo.R;
import com.tqlweb.tqlsaldo.common.MyApp;
import com.tqlweb.tqlsaldo.db.entity.TarjetaEntity;
import com.tqlweb.tqlsaldo.ui.dialogos.NuevaTarjetaDialogViewModel;
import com.tqlweb.tqlsaldo.ui.saldo.saldoFragment;

import java.util.ArrayList;
import java.util.List;


public class storedtarjetaFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    MystoredtarjetaRecyclerViewAdapter adaptador;
    List<TarjetaEntity> listaStarjetas;
    public static DashBoardMain DA;
    public static saldoFragment saldi;
    RecyclerView recyclervv;
    Drawable drawable;

    private NuevaTarjetaDialogViewModel nuevaTarjetaDialogViewModel;

    public storedtarjetaFragment() {
    }


    public static storedtarjetaFragment newInstance(int columnCount, DashBoardMain dashBoardMain, saldoFragment sal) {
        storedtarjetaFragment fragment = new storedtarjetaFragment();
        Bundle args = new Bundle();
        DA = dashBoardMain;
        saldi = sal;

        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_starjeta_list, container, false);

        //   Log.println(Log.ERROR,"focusable",v.getId()+"");

        drawable = getResources().getDrawable(R.drawable.ic_baseline_delete,null);






        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                //recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }


            recyclervv = view.findViewById(R.id.list);


            Log.println(Log.ERROR, "Camino", "oncreateview storedtarjetas");
            listaStarjetas = new ArrayList<>();


            adaptador = new MystoredtarjetaRecyclerViewAdapter(listaStarjetas, DA, saldi);
            recyclerView.setAdapter(adaptador);

            LanzarViewModel();


            SimpleCallback simpleCallback = new SimpleCallback(0, ItemTouchHelper.UP) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                    int indexseleccionado=viewHolder.getAdapterPosition();
                    //elimino primero de la base
                    adaptador.seekAndRemove(indexseleccionado,nuevaTarjetaDialogViewModel);

                    //elimino de la vista
                    adaptador.getmValues().remove(indexseleccionado);

                    //notificamos que ya no esta
                    //adaptador.notifyDataSetChanged();
                    adaptador.notifyItemRemoved(indexseleccionado);
                    Toast.makeText(getActivity(), "Tarjeta eliminada exitosamente", Toast.LENGTH_SHORT).show();
                }

                //metodo del icono  que no salio
                   /*@Override
                    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);


                        c.clipRect(0f, ((float) viewHolder.itemView.getTop()), dX, ((float) viewHolder.itemView.getBottom()));
                        if (dX < c.getWidth() / 3) {
                            c.drawColor(Color.GRAY);
                        } else {
                            c.drawColor(Color.RED);
                        }

                        int textmargin = ((int) getResources().getDimension(R.dimen.text_margin));


                        drawable.setBounds(new Rect(textmargin,viewHolder.itemView.getTop()+textmargin,
                                textmargin+drawable.getIntrinsicWidth(),viewHolder.itemView.getTop()+drawable.getIntrinsicHeight()+textmargin));

                        drawable.draw(c);

                    }*/
            };

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(recyclervv);



        }
        //view.setScrollX(100000);
        return view;
    }


    private void LanzarViewModel() {
        nuevaTarjetaDialogViewModel = new ViewModelProvider(getActivity()).get(NuevaTarjetaDialogViewModel.class);

        /*
        nuevaTarjetaDialogViewModel.getAllTarjetas().observe(getActivity(), (notaEntities) -> {

            //avisa que hay nueva
            adaptador.SetNuevasTarjetas(notaEntities);

        });*/

        nuevaTarjetaDialogViewModel.getAllTarjetas().observe(getActivity(), new Observer<List<TarjetaEntity>>() {
            @Override
            public void onChanged(List<TarjetaEntity> tarjetaEntities) {

                if (tarjetaEntities == null) {
                    Toast.makeText(getActivity(), "tarjas estan vacias", Toast.LENGTH_LONG).show();
                    Log.println(Log.ERROR, "Camino", "lista vacia!");
                } else {

                    int totalitems = 0;
                    totalitems = tarjetaEntities.size();

                    if (totalitems <= 0) {
                        DA.MostrarTutorial1();

                    } else {
                        DA.OcultarTutorial1();

                    }

                }

                //le aviso al adapter que cambiaron
                adaptador.SetNuevasTarjetas(tarjetaEntities, recyclervv);

            }
        });
    }

    public RecyclerView getRecyclerViewTarjetas() {
        return recyclervv;
    }


}