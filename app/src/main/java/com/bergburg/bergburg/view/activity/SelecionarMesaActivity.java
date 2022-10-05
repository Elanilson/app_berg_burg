package com.bergburg.bergburg.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.databinding.ActivitySelecionarMesaBinding;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.view.adapter.SelecionarMesaGridAdapter;
import com.bergburg.bergburg.viewmodel.SelecionarMesaViewModel;

import java.util.List;

public class SelecionarMesaActivity extends AppCompatActivity {
    private ActivitySelecionarMesaBinding binding;

   // private SelecionarMesaAdapter adapter = new SelecionarMesaAdapter();
    private SelecionarMesaViewModel viewModel ;
    private OnListenerAcao<Integer> onListenerAcao;
    private SelecionarMesaGridAdapter mesasAdapte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelecionarMesaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle(R.string.selecionar_mesa);
        viewModel = new ViewModelProvider(this).get(SelecionarMesaViewModel.class);
        mesasAdapte = new SelecionarMesaGridAdapter(getApplicationContext());
        viewModel.listaDeMesas();

        onListenerAcao = new OnListenerAcao<Integer>() {
            @Override
            public void onClick(Integer numeroMesa) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constantes.NUMERO_MESA,numeroMesa);
                Intent intent = new Intent(SelecionarMesaActivity.this,MesaActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

               // startActivity(new Intent(SelecionarMesaActivity.this,MesaActivity.class));
            }

            @Override
            public void onLongClick(Integer obj) {

            }
        };
        mesasAdapte.attackListener(onListenerAcao);


        observe();
        configurarRecyclerview();



    }

    private void observe() {
        viewModel.mesas.observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                mesasAdapte.limparMesas();
                mesasAdapte.attackMesas(integers);
            }
        });
    }

    private void configurarRecyclerview(){
       // GridLayoutManager manager = new GridLayoutManager(this,3);
     //   manager.setOrientation(RecyclerView.HORIZONTAL);
      //  binding.recyclerViewSelecionarMesa.setLayoutManager(manager);
       // binding.recyclerViewSelecionarMesa.setAdapter(adapter);
        //binding.gridviewteste.setAdapter(adapter);
        //binding.gridLayout.setad
        binding.gridViewMesas.setAdapter(mesasAdapte);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal,menu);
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


    }
}