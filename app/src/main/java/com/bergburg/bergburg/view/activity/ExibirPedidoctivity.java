package com.bergburg.bergburg.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.constantes.Constantes;
import com.bergburg.bergburg.databinding.ActivityExibirPedidoctivityBinding;
import com.bergburg.bergburg.helpers.Permissoes;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.ItemDePedido;
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
import java.util.Locale;

public class ExibirPedidoctivity extends AppCompatActivity {
    private ActivityExibirPedidoctivityBinding binding;
    private ExibirPedidoViewModel viewModel;
    private ItemPedidoAdapter adapter = new ItemPedidoAdapter();
    private ItemPedidoInfoAdapter itemPedidoInfoAdapter = new ItemPedidoInfoAdapter();
    private Runnable runnable;
    private Handler handler = new Handler();
    private Boolean ticker = false;
    private Long idPedido;

    private Pedido pedidoLocal = new Pedido();
    private List<ItemDePedido> itensPedidoLocal = new ArrayList<>();

    private String[] permissoes = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExibirPedidoctivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //validar Permissao
        Permissoes.validarPermissoes(permissoes, this, 1);


        viewModel = new ViewModelProvider(this).get(ExibirPedidoViewModel.class);
        recuperar();
        configurarrRecyclerView();
        adapteListener();
        configuracaoToolbar();
        observe();
       // ImpressaoUtils.preparaDados(pedidoTeste,itensTeste);


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
       // binding.recyclerviewItensPedido.setNestedScrollingEnabled(false);
      //  binding.recyclerviewItensPedido.suppressLayout(false);
        binding.recyclerviewItensPedido.setAdapter(itemPedidoInfoAdapter);

        // swipe();
    }

    private  void observe(){
        viewModel.pedido.observe(this, new Observer<Pedido>() {
            @Override
            public void onChanged(Pedido pedido) {
                if(pedido != null){
                    System.out.println("xxxxxxx "+pedido.toString());
                    pedidoLocal = pedido;
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
                    itensPedidoLocal = itemDePedidos;
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
        if (ContextCompat.checkSelfPermission(ExibirPedidoctivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(ExibirPedidoctivity.this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


            System.out.println(pedidoLocal.toString());
            System.out.println("Total de itens: " + itensPedidoLocal.size());
            LinearLayout layoutPedido = binding.linearLayoutPedido;
            Bitmap bitmap, bitmapLogo;
            int pageWidth = 210;
            int espacamentoDefault = 175;
            int espacamento_unitarioDefault = 185;
            int espaco = 20;
            int altura_da_pagina = 297;
            int totalItens = 0;
            int tamanhoObservacaoString = 224;
            float totalPedido = 0f;
            String observacaoFormatada = "";
            int caracteresPorLinha = 52;
            //calculo
            if (itensPedidoLocal != null) {
                totalItens = itensPedidoLocal.size();
                altura_da_pagina = (espacamento_unitarioDefault + (totalItens * espaco)) + 80;  // lagura da pagina sem observações 60 largura maxima para cada observacao
                // lagura da pagina com observações
                for (ItemDePedido item : itensPedidoLocal) {
                    if (!item.getObservacao().isEmpty()) {

                        observacaoFormatada += "" + System.lineSeparator() +
                                "☞ " + item.getTitulo() + System.lineSeparator() +
                                "" +
                                item.getObservacao().toLowerCase(Locale.ROOT).replaceAll("\n", " ") + System.lineSeparator() +
                                "";

                        if (item.getObservacao().length() > 200) {
                            altura_da_pagina += 60;
                        } else if (item.getObservacao().length() > 170 && item.getObservacao().length() <= 200) {
                            altura_da_pagina += 60;
                        } else if (item.getObservacao().length() > 120 && item.getObservacao().length() <= 170) {
                            altura_da_pagina += 60;
                        } else if (item.getObservacao().length() > 90 && item.getObservacao().length() <= 120) {
                            altura_da_pagina += 35;
                        } else if (item.getObservacao().length() > 60 && item.getObservacao().length() <= 90) {
                            altura_da_pagina += 30;
                        } else if (item.getObservacao().length() > 30 && item.getObservacao().length() <= 60) {
                            altura_da_pagina += 30;
                        } else if (item.getObservacao().length() > 10 && item.getObservacao().length() <= 30) {
                            altura_da_pagina += 30;
                        } else if (item.getObservacao().length() <= 10) {
                            altura_da_pagina += 15;
                        }

                    }


                }

                System.out.println("Tamanho: " + altura_da_pagina);
            }

            try {

        /*    Bitmap bitmap = Bitmap.createBitmap(layoutPedido.getWidth(),layoutPedido.getHeight(),Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            layoutPedido.draw(canvas);*/


                //  bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logoburgs);
                // bitmapLogo = Bitmap.createScaledBitmap(bitmap,100,100,false);

                PdfDocument mypdfDocument = new PdfDocument();
                Paint paint = new Paint(); //297
                PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(pageWidth, altura_da_pagina, 1).create();
                PdfDocument.Page page = mypdfDocument.startPage(myPageInfo);

                Canvas canvas = page.getCanvas();

                //  canvasPDF.drawBitmap(bitmap,0,0,paint);

                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                //     paint.setTextSize(3f);
                canvas.drawText("BERGS BURG", pageWidth / 2, 20, paint);

                // canvas.drawBitmap(bitmapLogo,0,10,paint);

                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                paint.setTextSize(8f);
                canvas.drawText("Rua Principal do Panorama XXI,", pageWidth / 2, 35, paint);

                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                paint.setTextSize(8f);
                canvas.drawText(" Q. Dois, 01  Mangueirão, Belém - PA", pageWidth / 2, 45, paint);

                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(8f);
                canvas.drawText("(91) 98235-9645", pageWidth / 2, 57, paint);

                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(8f);
                canvas.drawText("Data", pageWidth / 2, 70, paint);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(8f);
                canvas.drawText("" + pedidoLocal.getData_create(), pageWidth / 2, 80, paint);

                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(8f);
                canvas.drawText("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ", pageWidth / 2, 90, paint);

                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(8f);
                canvas.drawText("***** NÃO É DOCUMENTO FISCAL *****", pageWidth / 2, 100, paint);

                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTextSize(8f);
                canvas.drawText("Pedido N° ", 10, 120, paint);
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTextSize(8f);
                canvas.drawText("" + pedidoLocal.getId(), 60, 120, paint);


                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTextSize(8f);
                canvas.drawText("Mesa", 10, 130, paint);
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTextSize(8f);
                canvas.drawText("" + pedidoLocal.getIdMesa(), 40, 130, paint);


                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(8f);
                canvas.drawText("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ", pageWidth / 2, 150, paint);


                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTextSize(8f);
                canvas.drawText("#", 10, 160, paint);
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTextSize(8f);
                canvas.drawText("Item Qtd.", 20, 160, paint);
                paint.setTextAlign(Paint.Align.RIGHT);
                paint.setTextSize(8f);
                canvas.drawText("Total", 200, 160, paint);


                for (ItemDePedido item : itensPedidoLocal) {
                    int index = (itensPedidoLocal.indexOf(item) + 1);

                    if (index <= 10) {

                        Float total = item.getQuantidade() * item.getPreco();
                        totalPedido += total;
                        // item
                        paint.setTextAlign(Paint.Align.LEFT);
                        paint.setTextSize(8f);
                        canvas.drawText("" + index, 10, espacamentoDefault, paint);
                        paint.setTextAlign(Paint.Align.LEFT);
                        paint.setTextSize(8f);
                        canvas.drawText(item.getTitulo(), 20, espacamentoDefault, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        paint.setTextSize(8f);
                        canvas.drawText("R$ " + String.format("%.2f", total), 200, espacamentoDefault, paint);
                        // valor unitario
                        paint.setTextAlign(Paint.Align.LEFT);
                        paint.setTextSize(8f);
                        canvas.drawText(item.getQuantidade() + " x $" + String.format("%.2f", item.getPreco()), 20, espacamento_unitarioDefault, paint);
                        espacamentoDefault += espaco;
                        espacamento_unitarioDefault += espaco;

                    } else if (index > 10 && index <= 99) {

                        Float total = item.getQuantidade() * item.getPreco();
                        totalPedido += total;
                        // item
                        paint.setTextAlign(Paint.Align.LEFT);
                        paint.setTextSize(8f);
                        canvas.drawText("" + index, 10, espacamentoDefault, paint);
                        paint.setTextAlign(Paint.Align.LEFT);
                        paint.setTextSize(8f);
                        canvas.drawText(item.getTitulo(), 25, espacamentoDefault, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        paint.setTextSize(8f);
                        canvas.drawText("R$ " + String.format("%.2f", total), 200, espacamentoDefault, paint);
                        // valor unitario
                        paint.setTextAlign(Paint.Align.LEFT);
                        paint.setTextSize(8f);
                        canvas.drawText(item.getQuantidade() + " x $" + String.format("%.2f", item.getPreco()), 20, espacamento_unitarioDefault, paint);
                        espacamentoDefault += espaco;
                        espacamento_unitarioDefault += espaco;

                    } else if (index > 99) {

                        Float total = item.getQuantidade() * item.getPreco();
                        totalPedido += total;
                        // item
                        paint.setTextAlign(Paint.Align.LEFT);
                        paint.setTextSize(8f);
                        canvas.drawText("" + index, 10, espacamentoDefault, paint);
                        paint.setTextAlign(Paint.Align.LEFT);
                        paint.setTextSize(8f);
                        canvas.drawText(item.getTitulo(), 30, espacamentoDefault, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        paint.setTextSize(8f);
                        canvas.drawText("R$ " + String.format("%.2f", total), 200, espacamentoDefault, paint);
                        // valor unitario
                        paint.setTextAlign(Paint.Align.LEFT);
                        paint.setTextSize(8f);
                        canvas.drawText(item.getQuantidade() + " x $" + String.format("%.2f", item.getPreco()), 20, espacamento_unitarioDefault, paint);
                        espacamentoDefault += espaco;
                        espacamento_unitarioDefault += espaco;

                    }


                }


                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(9f);
                canvas.drawText("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ", pageWidth / 2, espacamento_unitarioDefault - 5, paint);

                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTextSize(9f);
                canvas.drawText("Total", 10, (espacamento_unitarioDefault + 10), paint);

                paint.setTextAlign(Paint.Align.RIGHT);
                paint.setTextSize(9f);
                canvas.drawText("R$ " + String.format("%.2f", pedidoLocal.getTotal()), 200, (espacamento_unitarioDefault + 10), paint);


                TextPaint textPaint = new TextPaint();
                textPaint.setTextSize(8);
                StaticLayout staticLayout;
                Boolean tem_observacao = false;


                for (ItemDePedido item : itensPedidoLocal) {
                    if (!item.getObservacao().isEmpty()) {
                        if (tem_observacao == false) {
                            paint.setTextAlign(Paint.Align.LEFT);
                            paint.setTextSize(8f);
                            canvas.drawText("Observações:", 10, (espacamento_unitarioDefault + 40), paint);
                            tem_observacao = true;
                        }
                    }
                }


                staticLayout = StaticLayout.Builder
                        .obtain(observacaoFormatada, 0, observacaoFormatada.length(), textPaint, canvas.getWidth() - 10)
                        .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                        .build();
                canvas.translate(10, espacamento_unitarioDefault + 40);
                staticLayout.draw(canvas);


                File direct = new File(Environment.getExternalStorageDirectory()+"/Pedidos");

                if(!direct.exists()) {
                    if(direct.mkdir()); //se não existir o diretorio e criado
                }




                //Compartilhar o pdf

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                mypdfDocument.finishPage(page);
                File file = new File(direct, "/Pedido_" + pedidoLocal.getId() + ".pdf");
                System.out.println("Caminho: " + file.getAbsolutePath());
                System.out.println("Caminho: " + file.getPath());

                mypdfDocument.writeTo(new FileOutputStream(file));
                System.out.println("Sucesso");

                final Uri arquivo = Uri.fromFile(file);
                final Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, arquivo);
                intent.putExtra(Intent.EXTRA_TITLE, "Imprimir pedido");

                intent.setType("application/pdf");

                startActivity(Intent.createChooser(intent, "Selecionar impressora"));

                mypdfDocument.close();
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
            }
        }else{
            askPermission();
        }
    }



    private void askPermission() {

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int permissaoResultado : grantResults) {
            if (permissaoResultado == PackageManager.PERMISSION_DENIED) {
                // snackbar("Permissão de localização não aceita!");
              //  criarPDF();
                askPermission();
                System.out.println("Sem Permissao");

            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pedido,menu);
        return true;
        // return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.impremir_pedido:
                criarPDF();
                break;
        }
        return super.onOptionsItemSelected(item);
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