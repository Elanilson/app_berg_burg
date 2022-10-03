package com.bergburg.bergburg.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.databinding.ActivityMesaBinding;

public class MesaActivity extends AppCompatActivity {
    private ActivityMesaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMesaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // getSupportActionBar().setTitle("Mesa 25");
        Toolbar toolbar = binding.toolbarMesa.toolbarCentralizado;
        binding.toolbarMesa.textViewToolbarTitulo.setText("Mesa 25");
        binding.toolbarMesa.imageButtonMesaVoltar.setOnClickListener(v -> voltarTela());
        setSupportActionBar(toolbar);

        binding.buttonAdicionarProdutos.setOnClickListener(v -> abriCardapio());
    }

    private void abriCardapio(){
        startActivity(new Intent(this,CardapioActivity.class));
    }

    private void voltarTela() {
        onBackPressed();
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
}