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
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.databinding.ActivityMainBinding;
import com.bergburg.bergburg.databinding.ActivityPrincipalBinding;
import com.bergburg.bergburg.helpers.UsuarioPreferences;
import com.bergburg.bergburg.helpers.VerificadorDeConexao;
import com.bergburg.bergburg.model.Mesa;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.model.Usuario;
import com.bergburg.bergburg.repositorio.remoto.RetrofitClient;
import com.bergburg.bergburg.viewmodel.MainViewModel;

public class PrincipalActivity extends AppCompatActivity {
    private ActivityPrincipalBinding binding;

    private MainViewModel viewModel;
    private UsuarioPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle(R.string.menu_principal);

        preferences = new UsuarioPreferences(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding.buttonConsultarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrincipalActivity.this,ConsultarPedidoActivity.class));

            }
        });
        binding.buttonFazerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrincipalActivity.this,SelecionarMesaActivity.class));

            }
        });
        observer();
    }



    private void observer() {
        viewModel.resposta.observe(this, new Observer<Resposta>() {
            @Override
            public void onChanged(Resposta resposta) {
                if(resposta.getStatus()){
                   Toast.makeText(PrincipalActivity.this, resposta.getMensagem(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PrincipalActivity.this,MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(PrincipalActivity.this, resposta.getMensagem(), Toast.LENGTH_SHORT).show();

                }
            }
        });
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
        switch (item.getItemId()){
            case R.id.menu_Sair:
                Long idUsuario = preferences.recuperarIDUSuario();
                if(idUsuario != null){
                     viewModel.deslogar(idUsuario);
                }else{
                    Toast.makeText(this, "ID usuário não encontrado", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RetrofitClient.CancelarRequisicoes();
    }
}