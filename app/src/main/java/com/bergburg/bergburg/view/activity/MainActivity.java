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
import android.widget.EditText;
import android.widget.Toast;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.databinding.ActivityMainBinding;
import com.bergburg.bergburg.helpers.UsuarioPreferences;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.model.Usuario;
import com.bergburg.bergburg.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private EditText editTextUsuario,editTextSenha;
    private Usuario usuarioAtual = new Usuario();
    private UsuarioPreferences preferences;
    private String statusLogado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("");

        preferences = new UsuarioPreferences(this);
        statusLogado = preferences.recuperarStatus();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.verificarUsuarioLogado(preferences.recuperarIDUSuario());

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progressBarLogin.setVisibility(View.VISIBLE);
               editTextUsuario = binding.editCampoLogin;
               editTextSenha = binding.editCampoSenha;
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
                    Toast.makeText(MainActivity.this, resposta.getMensagem(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_controle_de_acesso,menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
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