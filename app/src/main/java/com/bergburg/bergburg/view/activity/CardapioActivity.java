package com.bergburg.bergburg.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.databinding.ActivityCardapioBinding;

public class CardapioActivity extends AppCompatActivity {
    private ActivityCardapioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardapioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}