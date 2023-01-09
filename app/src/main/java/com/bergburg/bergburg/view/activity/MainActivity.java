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
import com.bergburg.bergburg.helpers.UsuarioPreferences;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.model.Usuario;
import com.bergburg.bergburg.repositorio.remoto.RetrofitClient;
import com.bergburg.bergburg.viewmodel.MainViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private TextInputEditText editTextUsuario,editTextSenha;
    private Usuario usuarioAtual = new Usuario();
    private UsuarioPreferences preferences;
    private String statusLogado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /*getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("");*/

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        preferences = new UsuarioPreferences(this);
        statusLogado = preferences.recuperarStatus();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.verificarUsuarioLogado(preferences.recuperarIDUSuario());

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progressBarLogin.setVisibility(View.VISIBLE);
               editTextUsuario = binding.textInputLogin;
               editTextSenha = binding.textInputSenha;
               String usuario  = editTextUsuario.getText().toString().trim();
               String senha  = editTextSenha.getText().toString().trim();
                viewModel.login(usuario,senha);
            }
        });

        observer();
    }

    private void observer() {
        viewModel.usuario.observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                usuarioAtual = usuario;
                if(usuario != null){
                    if(usuario.getStatus().equalsIgnoreCase(Constantes.USUARIO_LOGADO)){
                        startActivity(new Intent(MainActivity.this,PrincipalActivity.class));
                        finish();
                    }else{
                     binding.progressBarLogin.setVisibility(View.GONE);

                    }
                }else{
                    binding.progressBarLogin.setVisibility(View.GONE);
                }
            }
        });
        viewModel.resposta.observe(this, new Observer<Resposta>() {
            @Override
            public void onChanged(Resposta resposta) {
                if(resposta.getStatus()){

                }else{
                    binding.textInputLayoutLogin.setError("Dados incorretos");
                    binding.textInputLayoutSenha.setError("Dados incorretos");
                    binding.textInputLayoutLogin.setErrorEnabled(true);
                    binding.textInputLayoutSenha.setErrorEnabled(true);

                  //  binding.textInputLayoutLogin.setcolo( getResources().getColor(R.color.laranja));
                    binding.progressBarLogin.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, resposta.getMensagem(), Toast.LENGTH_SHORT).show();

                }
            }
        });
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