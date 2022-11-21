package com.bergburg.bergburg.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.databinding.ActivitySelecionarMesaBinding;
import com.bergburg.bergburg.helpers.VerificadorDeConexao;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.Mesa;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.view.adapter.SelecionarMesaGridAdapter;
import com.bergburg.bergburg.viewmodel.MesaViewModel;
import com.bergburg.bergburg.viewmodel.SelecionarMesaViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SelecionarMesaActivity extends AppCompatActivity {
    private ActivitySelecionarMesaBinding binding;

    private SelecionarMesaViewModel viewModel ;
    private MesaViewModel mesaViewModel;
    private OnListenerAcao<Mesa> onListenerAcao;
    private SelecionarMesaGridAdapter mesasAdapte;
    private int totalDeMesas = 0;
    private Runnable runnable;
    private Handler handler = new Handler();
    private Boolean ticker = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelecionarMesaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle(R.string.selecionar_mesa);

        viewModel = new ViewModelProvider(this).get(SelecionarMesaViewModel.class);
        mesaViewModel = new ViewModelProvider(this).get(MesaViewModel.class);

        mesasAdapte = new SelecionarMesaGridAdapter(getApplicationContext());

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        onListenerAcao = new OnListenerAcao<Mesa>() {
            @Override
            public void onClick(Mesa mesa) {
                Intent intent = new Intent(SelecionarMesaActivity.this,MesaActivity.class);
                intent.putExtra(Constantes.MESA,mesa);
                startActivity(intent);
            }
            @Override
            public void onLongClick(Mesa obj) {

            }
        };


        mesasAdapte.attackListener(onListenerAcao);
        observe();
        configurarRecyclerview();
    }

    private void observe() {
        mesaViewModel.mesas.observe(this, new Observer<List<Mesa>>() {
            @Override
            public void onChanged(List<Mesa> mesas) {
                if(mesas != null){
                    if(mesas.size() > 0){
                        binding.progressBarGimesasd.setVisibility(View.GONE);
                        mesasAdapte.limparMesas();
                        mesasAdapte.attackMesas(mesas);
                    }else{
                        binding.progressBarGimesasd.setVisibility(View.VISIBLE);

                    }
                }else{
                        binding.progressBarGimesasd.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel.resposta.observe(this, new Observer<Resposta>() {
            @Override
            public void onChanged(Resposta resposta) {
                if(resposta.getStatus()){
                    //Toast.makeText(SelecionarMesaActivity.this, resposta.getMensagem(), Toast.LENGTH_SHORT).show();
                    binding.progressBarGimesasd.setVisibility(View.GONE);
                }else{
                    Toast.makeText(SelecionarMesaActivity.this, resposta.getMensagem(), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void configurarRecyclerview(){
        binding.gridViewMesas.setAdapter(mesasAdapte);
    }

    private void startClock(){

        final Calendar calendar = Calendar.getInstance();
        this.runnable = new Runnable() {
            @Override
            public void run() {
                if(!ticker){
                    return;
                }

                try {
                            calendar.setTimeInMillis(System.currentTimeMillis());
                            System.out.println("SelecionarMesa -Milisegundos: "+System.currentTimeMillis());

                             mesaViewModel.carregarMesas();
                             System.out.println("Selecionar mesa ==");

                            Long now = SystemClock.uptimeMillis();
                            Long next = now + (1000 - (now % 1000));
                            handler.postAtTime(runnable,next);

                }catch (Exception e){
                    System.out.println("Error "+e.getMessage());
                }


            }
        };
        this.runnable.run();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      //  MenuInflater inflater = getMenuInflater();
     //   inflater.inflate(R.menu.menu_principal,menu);
        return true;
       // return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onResume() {
        super.onResume();

        ticker = true;
        startClock();
        System.out.println("onResume");
        System.out.println("TEM INTERNET ? "+VerificadorDeConexao.isConnectionAvailable(SelecionarMesaActivity.this));


        mesasAdapte.limparMesas();

    }

    @Override
    protected void onPause() {
        super.onPause();
        ticker = false;
        System.out.println("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        ticker = false;
        System.out.println("onStop");
       // finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ticker = false;
        System.out.println("onDestroy");
    }
}