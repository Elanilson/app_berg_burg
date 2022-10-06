package com.bergburg.bergburg.view.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bergburg.bergburg.R;
import com.bergburg.bergburg.listeners.OnListenerAcao;
import com.bergburg.bergburg.model.Mesa;
import com.bergburg.bergburg.view.viewholder.SelecionarMesaViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SelecionarMesaGridAdapter extends BaseAdapter {

    private List<Mesa> mesas = new ArrayList<>();
    //private List<Integer> mesas = new ArrayList<>();
    //private OnListenerAcao<Integer> onListenerAcao;
    private OnListenerAcao<Mesa> onListenerAcao;
    private Context context;

    public SelecionarMesaGridAdapter() {
    }

    public SelecionarMesaGridAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return mesas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =layoutInflater.inflate(R.layout.layout_mesa,null);

           LinearLayout layout = view.findViewById(R.id.layoutMesa);
        if(mesas.get(position).getLivre() == 1){
           layout.setBackgroundResource(R.color.purple_500);
        }else{
           layout.setBackgroundResource(R.color.laranja);

        }

        TextView textViewNumeroMesa = view.findViewById(R.id.textViewNumeroMesa);
        CardView cardViewMesa = view.findViewById(R.id.cardMesa);
        textViewNumeroMesa.setText(""+mesas.get(position).getNumero());
        cardViewMesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListenerAcao.onClick(mesas.get(position));
            }
        });
        return view;
    }

    public void attackMesas(List<Mesa> mesas){
        this.mesas = mesas;
        notifyDataSetChanged();
    }
    public void limparMesas(){
        this.mesas.clear();
        notifyDataSetChanged();
    }
    public void attackListener(OnListenerAcao<Mesa> onListenerAcao){
        this.onListenerAcao = onListenerAcao;
    }
}
