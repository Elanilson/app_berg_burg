package com.bergburg.bergburg.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.databinding.ActivityCardapioBinding;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.Categoria;
import com.bergburg.bergburg.view.adapter.CardapioAdapter;
import com.bergburg.bergburg.viewmodel.CardapioViewModel;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class CardapioActivity extends AppCompatActivity {
    private ActivityCardapioBinding binding;
    private CardapioAdapter adapter = new CardapioAdapter();
    private CardapioViewModel viewModel;
    private int id_mesa = 0;
    private List<Categoria> categoriaList = new ArrayList<>();
    private int posicao = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardapioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(CardapioViewModel.class);
        configuracaoToolbar();
        recuperar();
        configurarrRecyclerView();
        adapteListener();
        observe();
    }

    private void swipe(){
        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags =ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlags = ItemTouchHelper.END ;
                return makeMovementFlags(dragFlags,swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // System.out.println("Direção: "+direction);
                posicao = viewHolder.getLayoutPosition();
                switch (direction){
                    case ItemTouchHelper.END:
                        if(categoriaList.get(posicao) != null){
                            Bundle bundle = new Bundle();
                            bundle.putLong(Constantes.ID_CATEGORIA,categoriaList.get(posicao).getId());
                            bundle.putString(Constantes.TITULO_CATEGORIA,categoriaList.get(posicao).getTitulo());
                            bundle.putInt(Constantes.ID_MESA,id_mesa);
                            Intent intent = new Intent(CardapioActivity.this,ItemCardapioActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        adapter.notifyDataSetChanged();

                        break;

                }

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(CardapioActivity.this, R.color.black))
                        .addSwipeRightActionIcon(R.drawable.ic_baseline_list_alt_24)
                        .addSwipeRightLabel(getString(R.string.exibir))
                        .setSwipeRightLabelColor(ContextCompat.getColor(CardapioActivity.this, R.color.white))
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        new ItemTouchHelper(itemTouch).attachToRecyclerView(binding.recyclerViewCardPio);
    }

    private void configuracaoToolbar(){
        // getSupportActionBar().setTitle("Mesa 25");
        Toolbar toolbar = binding.toolbar.toolbarPersonalizado;

        binding.toolbar.textViewLabelToolbar.setText(getString(R.string.cardapio));

        binding.toolbar.imageButtonVoltar.setOnClickListener(v -> voltarTela());
        setSupportActionBar(toolbar);
    }
    private void voltarTela() {
        onBackPressed();
    }

    private void recuperar(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            this.id_mesa = bundle.getInt(Constantes.ID_MESA);
        }
    }

    private void adapteListener(){
        OnListenerAcao<Categoria> onListenerAcao = new OnListenerAcao<Categoria>() {
            @Override
            public void onClick(Categoria obj) {
                if(obj != null){
                    Bundle bundle = new Bundle();
                    bundle.putLong(Constantes.ID_CATEGORIA,obj.getId());
                    bundle.putString(Constantes.TITULO_CATEGORIA,obj.getTitulo());
                    bundle.putInt(Constantes.ID_MESA,id_mesa);
                    Intent intent = new Intent(CardapioActivity.this,ItemCardapioActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }

            @Override
            public void onLongClick(Categoria obj) {

            }
        };
        adapter.attackOnListener(onListenerAcao);
    }

    private void observe() {
        viewModel.categorias.observe(this, new Observer<List<Categoria>>() {
            @Override
            public void onChanged(List<Categoria> categorias) {
                categoriaList.addAll(categorias);
                adapter.attackCategorias(categorias);
            }
        });
    }

    private void configurarrRecyclerView(){
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerViewCardPio.setLayoutManager(manager);
        binding.recyclerViewCardPio.setAdapter(adapter);
        //swipe();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getCategorias();
       // viewModel.getCategoriasOnline();
    }
}