package com.bergburg.bergburg.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.databinding.ActivityExibirPedidoctivityBinding;
import com.bergburg.bergburg.helpers.ImpressaoUtils;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.ItemDePedido;
import com.bergburg.bergburg.model.ItensComanda;
import com.bergburg.bergburg.model.Mesa;
import com.bergburg.bergburg.model.Pedido;
import com.bergburg.bergburg.model.Resposta;
import com.bergburg.bergburg.view.adapter.ItemPedidoAdapter;
import com.bergburg.bergburg.view.adapter.ItemPedidoInfoAdapter;
import com.bergburg.bergburg.viewmodel.ExibirPedidoViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExibirPedidoctivity extends AppCompatActivity {
    private ActivityExibirPedidoctivityBinding binding;
    private ExibirPedidoViewModel viewModel;
    private ItemPedidoAdapter adapter = new ItemPedidoAdapter();
    private ItemPedidoInfoAdapter itemPedidoInfoAdapter = new ItemPedidoInfoAdapter();
    private Runnable runnable;
    private Handler handler = new Handler();
    private Boolean ticker = false;
    private Long idPedido;

    private Pedido pedidoTeste = new Pedido();
    private List<ItemDePedido> itensTeste = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExibirPedidoctivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ExibirPedidoViewModel.class);
        recuperar();
        configurarrRecyclerView();
        adapteListener();
        configuracaoToolbar();
        observe();
        ImpressaoUtils.preparaDados(pedidoTeste,itensTeste);

        binding.buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarPDF();
            }
        });
    }

    private void configuracaoToolbar(){
        Toolbar toolbar = binding.toolbar.toolbarPersonalizado;
        binding.toolbar.textViewLabelToolbar.setText("Voltar");

        binding.toolbar.imageButtonVoltar.setOnClickListener(v -> finish());
        setSupportActionBar(toolbar);
    }
    private void recuperar(){
        Bundle bundle = getIntent().getExtras();
        if(bundle!= null){
           idPedido = bundle.getLong(Constantes.ID_PEDIDO);

        }
    }
    private void adapteListener() {
        OnListenerAcao<ItemDePedido> onListenerAcao = new OnListenerAcao<ItemDePedido>() {
            @Override
            public void onClick(ItemDePedido item) {

            }

            @Override
            public void onLongClick(ItemDePedido item) {

            }
        };
        adapter.attackOnListener(onListenerAcao);
    }
    private void configurarrRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerviewItensPedido.setLayoutManager(manager);
        binding.recyclerviewItensPedido.setNestedScrollingEnabled(false);
        binding.recyclerviewItensPedido.setAdapter(itemPedidoInfoAdapter);
        // swipe();
    }

    private  void observe(){
        viewModel.pedido.observe(this, new Observer<Pedido>() {
            @Override
            public void onChanged(Pedido pedido) {
                if(pedido != null){
                    System.out.println("xxxxxxx "+pedido.toString());
                    pedidoTeste = pedido;
                    binding.progressBarPedido.setVisibility(View.GONE);
                    binding.textViewDataPedido.setText(pedido.getData_create());
                    binding.textViewIdPedido.setText(""+pedido.getId());
                    binding.textViewIdMesa.setText(""+pedido.getIdMesa());
                    binding.textViewPedido.setText(pedido.getAberturaPedido());
                    binding.textViewStatus.setText(pedido.getStatus());
                    binding.textViewTotal.setText("R$ "+String.format("%.2f", pedido.getTotal()));
                }else{
                    binding.progressBarPedido.setVisibility(View.VISIBLE);
                }
            }
        });
        viewModel.itensDoPedido.observe(this, new Observer<List<ItemDePedido>>() {
            @Override
            public void onChanged(List<ItemDePedido> itemDePedidos) {

                if(itemDePedidos != null){
                    itensTeste = itemDePedidos;
                    binding.progressBarPedidoItem.setVisibility(View.GONE);
                   // adapter.attackProdutos(itemDePedidos);
                    itemPedidoInfoAdapter.attackProdutos(itemDePedidos);
                }else{
                    binding.progressBarPedidoItem.setVisibility(View.VISIBLE);

                }
            }
        });

        viewModel.resposta.observe(this, new Observer<Resposta>() {
            @Override
            public void onChanged(Resposta resposta) {
                if(resposta.getStatus()){
                    Toast.makeText(ExibirPedidoctivity.this, resposta.getMensagem(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ExibirPedidoctivity.this, resposta.getMensagem(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void criarPDF(){
        LinearLayout layoutPedido = binding.linearLayoutPedido;
        int pageWidth = 40;

        try {

        /*    Bitmap bitmap = Bitmap.createBitmap(layoutPedido.getWidth(),layoutPedido.getHeight(),Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            layoutPedido.draw(canvas);*/






            PdfDocument mypdfDocument = new PdfDocument();
            Paint paint = new Paint();
            PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(pageWidth,80, 1).create();
            PdfDocument.Page page = mypdfDocument.startPage(myPageInfo);

            Canvas canvas = page.getCanvas();

          //  canvasPDF.drawBitmap(bitmap,0,0,paint);

            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            paint.setTextSize(3f);
            canvas.drawText("BERGS BURG",pageWidth/2 ,10,paint);

            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(2f);
            canvas.drawText("Rua Principal do Panorama XXI, \n Q. Dois, 01 \n Mangueirão, Belém - PA",pageWidth/2 ,15,paint);

            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(2f);
            canvas.drawText("(91) 98235-9645",pageWidth/2 ,16,paint);




            mypdfDocument.finishPage(page);
            File file = new File(Environment.getExternalStorageDirectory(), "/Pedido0002.pdf");

            mypdfDocument.writeTo(new FileOutputStream(file));
            System.out.println("Sucesso");

            mypdfDocument.close();
        }catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }
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
                    System.out.println("Exibir_Pedido -Milisegundos: "+System.currentTimeMillis());

                    if(idPedido != null){
                        viewModel.getPedido(idPedido);
                        viewModel.getItensPedido(idPedido);
                    }

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
    protected void onResume() {
        super.onResume();
        ticker = true;
        startClock();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ticker = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        ticker = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ticker = false;
    }

}