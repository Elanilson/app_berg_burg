package com.bergburg.bergburg.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.databinding.ActivityEmitirPedidoBinding;
import com.bergburg.bergburg.helpers.Permissoes;
import com.bergburg.bergburg.repositorio.remoto.RetrofitClient;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class EmitirPedidoActivity extends AppCompatActivity {
    private ActivityEmitirPedidoBinding binding;
    private TextView textView;
    private String pedidoInfo;
    private String[] permissoes = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE
    };
    private int pageWidth = 40;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmitirPedidoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        textView = binding.textViewDetalhesPedido;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //validar Permissao
        Permissoes.validarPermissoes(permissoes, this, 1);


        pedidoInfo += "BERGS BURG \n \n";
        pedidoInfo += "Mesa 9 \n";
        pedidoInfo += "Total  R$ 25,99  \n";
        pedidoInfo += "Itens ----------- \n";
        pedidoInfo += "1 x Produto 1        R$25,90 \n";
        pedidoInfo += "1 x Produto 1        R$25,90 \n";
        pedidoInfo += "1 x Produto 1        R$25,90 \n";
        pedidoInfo += "1 x Produto 1        R$25,90 \n";
        pedidoInfo += "1 x Produto 1        R$25,90 \n";



        textView.setText(pedidoInfo);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarPDF();
            }
        });




    }

    private  void criarPDF(){
        if (ContextCompat.checkSelfPermission(EmitirPedidoActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


            PdfDocument mypdfDocument = new PdfDocument();
            Paint paint = new Paint();
            PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(pageWidth, 80, 1).create();
            PdfDocument.Page page = mypdfDocument.startPage(myPageInfo);

            Canvas canvas = page.getCanvas();
           /* canvas.drawText(pedidoInfo, 300, 50, paint);
            mypdfDocument.finishPage(page);*/

            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            paint.setTextSize(3f);
            canvas.drawText("BERGS BURG",pageWidth/2 ,10,paint);

            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(2f);
            canvas.drawText("Mesa 9",pageWidth/2 ,15,paint);

            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize(2f);
            canvas.drawText("Data: 12/11/2022",3 ,20,paint);

            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize(2f);
            canvas.drawText("N° 526",3 ,23,paint);


            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
            canvas.drawRect(20,780,pageWidth-5 ,860 , paint);




            mypdfDocument.finishPage(page);
            File file = new File(Environment.getExternalStorageDirectory(), "/Pedido001.pdf");

            try {
                mypdfDocument.writeTo(new FileOutputStream(file));
                System.out.println("Sucesso");
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
            } finally {
                 mypdfDocument.close();
            }

        }else{
            askPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int permissaoResultado : grantResults) {
            if (permissaoResultado == PackageManager.PERMISSION_DENIED) {
                // snackbar("Permissão de localização não aceita!");
                criarPDF();
                System.out.println("Sem Permissao");

            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void askPermission() {

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

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