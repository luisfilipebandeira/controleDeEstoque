package com.example.play;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.play.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private final SQLiteDatabase write;
    private final SQLiteDatabase read;

    public ProdutoDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        this.write = dbHelper.getWritableDatabase();
        this.read = dbHelper.getReadableDatabase();
    }

    public void salvarProduto(Produto produto) {
        ContentValues cv = new ContentValues();
        cv.put("nome", produto.getNome());
        cv.put("estoque", produto.getEstoque());
        cv.put("valor", produto.getValor());

        try {
            write.insert(DBHelper.TB_PRODUTO, null, cv);
            //write.close();
        } catch (Exception e) {
            Log.i("ERROR", "Erro ao salvar produto: " + e.getMessage());
        }
    }

    @SuppressLint("Range")
    public List<Produto> getListProdutos() {
        List<Produto> produtoList = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.TB_PRODUTO + ";";
        Cursor c = read.rawQuery(sql, null);

        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("id"));
            String nome = c.getString(c.getColumnIndex("nome"));
            int estoque = c.getInt(c.getColumnIndex("estoque"));
            double valor = c.getDouble(c.getColumnIndex("valor"));

            Produto produto = new Produto();
            produto.setId(id);
            produto.setNome(nome);
            produto.setEstoque(estoque);
            produto.setValor(valor);

            produtoList.add(produto);
        }

        return produtoList;
    }

    public void atualizaProduto(Produto produto) {
        ContentValues cv = new ContentValues();
        cv.put("nome", produto.getNome());
        cv.put("estoque", produto.getEstoque());
        cv.put("valor", produto.getValor());

        String where = "id=?";
        String[] args = {String.valueOf(produto.getId())};

        try {
            write.update(DBHelper.TB_PRODUTO, cv, where, args);
            write.close();
        } catch (Exception e) {
            Log.i("Error", "Erro ao atualizar produto 0 " + e.getMessage());
        }
    }

    public void deletaProduto(Produto produto){
        try{
            String[] args = {String.valueOf(produto.getId())};
            write.delete(DBHelper.TB_PRODUTO, "id=?", args);
        } catch (Exception e) {
            Log.i("ERROR", "Erro ao deletar produto: " + e.getMessage());
        }
    }
}



