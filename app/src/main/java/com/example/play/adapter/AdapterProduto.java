package com.example.play.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.play.R;
import com.example.play.model.Produto;

import java.util.List;

public class AdapterProduto extends RecyclerView.Adapter<AdapterProduto.MyViewHolder> {

    private List<Produto> produtoList;
    private OnClick onClick;

    public AdapterProduto(List<Produto> produtoList, OnClick onClick) {
        this.produtoList = produtoList;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Lugar onde é passado o layout que vai ser utilizado no RecyclerView
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Configura as informações da linha do recycler view
        Produto produto = produtoList.get(position);

        holder.textProduto.setText(produto.getNome());
        holder.textEstoque.setText(String.valueOf(produto.getEstoque()));
        holder.textValor.setText(String.valueOf(produto.getValor()));

        holder.itemView.setOnClickListener( view -> onClick.onClickListener(produto));
    }

    @Override
    public int getItemCount() {
        //Retorna a quantidade de produto da nossa lista
        return produtoList.size();
    }

    public interface OnClick{
        void onClickListener(Produto produto);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        //Configura quais elementos vão ter na nossa listagem

        TextView textProduto, textEstoque, textValor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textProduto = itemView.findViewById(R.id.text_produto);
            textEstoque = itemView.findViewById(R.id.text_estoque);
            textValor = itemView.findViewById(R.id.text_valor);
        }
    }

}
