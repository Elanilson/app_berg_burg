package com.bergburg.bergburg.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.databinding.ActivityTesteImpremirBinding;


import java.util.ArrayList;

public class TesteImpremirActivity extends AppCompatActivity {
    private ActivityTesteImpremirBinding binding;

  //  Printing printing;
  //  Button btn_unpair, btn_print,btn_print_imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTesteImpremirBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

      //  initView();
    }
/*
    private void initView() {
        btn_print = binding.buttonPrint;
        btn_print_imagem = binding.buttonPrintImagem;
        btn_unpair = binding.button4UniPar;

        if(printing != null){
            printing.setPrintingCallback(this);

            btn_unpair.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(Printooth.INSTANCE.hasPairedPrinter()){
                        Printooth.INSTANCE.removeCurrentPrinter();
                    }else{
                        startActivityForResult(new Intent(TesteImpremirActivity.this, ScanningActivity.class),ScanningActivity.SCANNING_FOR_PRINTER);
                        changePairAndUpair();
                    }

                }
            });

            btn_print_imagem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!Printooth.INSTANCE.hasPairedPrinter()){
                        startActivityForResult(new Intent(TesteImpremirActivity.this, ScanningActivity.class),ScanningActivity.SCANNING_FOR_PRINTER);
                    }else{
                        printImagem();
                    }
                }
            });

            btn_print_imagem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!Printooth.INSTANCE.hasPairedPrinter()){
                        startActivityForResult(new Intent(TesteImpremirActivity.this, ScanningActivity.class),ScanningActivity.SCANNING_FOR_PRINTER);
                    }else{
                        printText();
                    }
                }
            });

            changePairAndUpair();
        }
    }

    private void printText(){
        ArrayList<Printable> printables = new ArrayList<>();
        printables.add(new RawPrintable.Builder(new byte[]{27,100}).build());

        //add text
        printables.add(new TextPrintable.Builder()
                .setText("Olá mundo")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());
        //custom text
        printables.add(new TextPrintable.Builder()
                .setText("Ola mundoo")
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_60())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
                .setUnderlined(DefaultPrinter.Companion.getUNDERLINED_MODE_ON())
                .setNewLinesAfter(1)
                .build());

        printing.print(printables);

    }
    private void printImagem(){
        ArrayList<Printable> printables = new ArrayList<>();

        Picasso.get().load("https://upload.wikimedia.org/wikipedia/commons/thumb/e/e0/Android_robot_%282014-2019%29.svg/1200px-Android_robot_%282014-2019%29.svg.png")
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        printables.add(new ImagePrintable.Builder(bitmap).build());
                        printing.print(printables);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

    }
    private void  changePairAndUpair(){
        if(Printooth.INSTANCE.hasPairedPrinter()){
            btn_unpair.setText(new StringBuilder("Unpair")
                    .append(Printooth.INSTANCE.getPairedPrinter()
                            .getName().toString()));

        }else{
            btn_print.setText("Pair witch  printer");
        }
    }

    @Override
    public void connectingWithPrinter() {

        Toast.makeText(this, "Conectando à impressora", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void connectionFailed(@NonNull String s) {
        Toast.makeText(this, "Falhou "+s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(@NonNull String s) {
        Toast.makeText(this, "Error "+s, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMessage(@NonNull String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void printingOrderSentSuccessfully() {
        Toast.makeText(this, "Pedido enviado para impressora", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ScanningActivity.SCANNING_FOR_PRINTER &&
         resultCode == Activity.RESULT_OK){

            iniciarImpressao();
            changePairAndUpair();

        }
    }

    private void iniciarImpressao() {
        if(!Printooth.INSTANCE.hasPairedPrinter()){
            printing = Printooth.INSTANCE.printer();
            if(printing != null){
                printing.setPrintingCallback(this);

            }

        }
    }*/
}