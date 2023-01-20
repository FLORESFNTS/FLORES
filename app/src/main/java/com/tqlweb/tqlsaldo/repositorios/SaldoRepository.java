package com.tqlweb.tqlsaldo.repositorios;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.tqlweb.tqlsaldo.common.Constantes;
import com.tqlweb.tqlsaldo.common.MyApp;
import com.tqlweb.tqlsaldo.common.SharedPreferencesManager;
import com.tqlweb.tqlsaldo.retrofit.SaldoClient;
import com.tqlweb.tqlsaldo.retrofit.SaldoService;
import com.tqlweb.tqlsaldo.retrofit.request.RequestCarga;
import com.tqlweb.tqlsaldo.retrofit.request.RequestObtenerSaldo;
import com.tqlweb.tqlsaldo.retrofit.response.Carga;
import com.tqlweb.tqlsaldo.retrofit.response.Estacion;
import com.tqlweb.tqlsaldo.retrofit.response.Saldo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaldoRepository {

    SaldoService saldoService;
    SaldoClient saldoClient;
    MutableLiveData<Saldo> saldoVenom;
    MutableLiveData<List<Estacion>> allEstaciones;
    MutableLiveData<List<Carga>> allcargas;
    String folio="";

    public SaldoRepository() {

        saldoClient = SaldoClient.getInstance();
        saldoService = saldoClient.getService();
        //puedo iniciar un a lista aqui
        saldoVenom = new MutableLiveData<>();
        //allcargas = getLastTwCargas("","");

    }


    public MutableLiveData<Saldo> getSaldoRepository(String nutarjeta, String nucliente, String nip) {


        if (saldoVenom == null) {
            saldoVenom = new MutableLiveData<>();
        }

        if (SharedPreferencesManager.getSomeStringvalue(Constantes.GLOBAL_NUCLIENTE).equals("0") || SharedPreferencesManager.getSomeStringvalue(Constantes.GLOBAL_NUTARJETA).equals("0")
        || nip.equals("0")) {
            Log.println(Log.ERROR,"REPOSITORIO","No busca saldo "+nucliente+nutarjeta+nip);
        } else {


            RequestObtenerSaldo requestObtenerSaldo = new RequestObtenerSaldo(nucliente, nutarjeta, nip, "empty", "empty");
            Call<Saldo> call = saldoService.obtenerSaldo(requestObtenerSaldo);

            call.enqueue(new Callback<Saldo>() {
                @Override
                public void onResponse(Call<Saldo> call, Response<Saldo> response) {
                    if (response.isSuccessful()) {
                        //se pongo el valor de la respuesta al saldo

                            saldoVenom.setValue(response.body());

                         if(response.body().getMensaje().equals("EXITO")) {

                             SharedPreferencesManager.setSomeBooleanValue(Constantes.CONSULTA_SALDO, true);
                             SharedPreferencesManager.setSomeStringValue(Constantes.GLOBAL_NUCLIENTE, response.body().getNucl());
                             SharedPreferencesManager.setSomeStringValue(Constantes.GLOBAL_NUTARJETA, response.body().getNutj());
                             SharedPreferencesManager.setSomeStringValue(Constantes.GLOBAL_MENSAJE_SERVER, "EXITO");
                         }else{
                             SharedPreferencesManager.setSomeStringValue(Constantes.GLOBAL_NUCLIENTE, "0");
                             SharedPreferencesManager.setSomeStringValue(Constantes.GLOBAL_NUTARJETA, "0");
                             SharedPreferencesManager.setSomeBooleanValue(Constantes.CONSULTA_SALDO, false);
                             SharedPreferencesManager.setSomeStringValue(Constantes.GLOBAL_MENSAJE_SERVER, "FAIL");
                         }



                    } else {
                        Toast.makeText(MyApp.getContext(), "Hubo un error al procesar tu solicitud", Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<Saldo> call, Throwable t) {
                    Toast.makeText(MyApp.getContext(), "Revisa tu conexion a internet e intentelo de nuevo", Toast.LENGTH_LONG).show();
                }
            });

        }


        return saldoVenom;
    }

    public MutableLiveData<List<Estacion>> getAllTEstaciones() {
        //final MutableLiveData<List<Tweet>> data = new MutableLiveData<>();
        if (allEstaciones == null) {
            allEstaciones = new MutableLiveData<>();
        }
        //conectra api y recoger tweets
        Call<List<Estacion>> llamada = saldoService.getAllEstaciones();
        llamada.enqueue(new Callback<List<Estacion>>() {
            @Override
            public void onResponse(Call<List<Estacion>> call, Response<List<Estacion>> response) {
                if (response.isSuccessful()) {

                    Log.println(Log.ERROR,"AVG",response.message());
                    allEstaciones.setValue(response.body());
                } else {
                    Log.println(Log.ERROR,"AVG",response.message());
                    Toast.makeText(MyApp.getContext(), "Algo salio mal al recuperar estaciones", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Estacion>> call, Throwable t) {
                Log.println(Log.ERROR,"AVG",t.toString());
                Toast.makeText(MyApp.getContext(), "Por favor , revisa tu conexion a internet e intente de nuevo", Toast.LENGTH_LONG).show();
                //Log.println(Log.ERROR, "AVH", "error" + t);
            }
        });
        return allEstaciones;
    }


    public MutableLiveData<List<Carga>> getLastTwCargas(String nucliente,String nutarjeta) {

        if (allcargas == null) {
            allcargas = new MutableLiveData<>();
        }

        RequestCarga requestCarga = new RequestCarga(nucliente,nutarjeta);
        //Log.println(Log.ERROR,"AVG","entro a lastw cargas");
        Call<List<Carga>> llamada = saldoService.getLastCargas(requestCarga);
        llamada.enqueue(new Callback<List<Carga>>() {
            @Override
            public void onResponse(Call<List<Carga>> call, Response<List<Carga>> response) {
                if (response.isSuccessful()) {
                    //Toast.makeText(MyApp.getContext(), "nucliente!"+nucliente +"nutarjeta :"+nutarjeta, Toast.LENGTH_SHORT).show();

                    allcargas.setValue(response.body());

                } else {
                    Log.println(Log.ERROR,"AVG",response.message());
                    Toast.makeText(MyApp.getContext(), "Algo salio mal al recuperar tus consumos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Carga>> call, Throwable t) {
                Log.println(Log.ERROR,"AVG",t.toString());
                Toast.makeText(MyApp.getContext(), "Por favor , revisa tu conexion a internet e intente de nuevo", Toast.LENGTH_LONG).show();
            }
        });


        return allcargas;
    }


}
