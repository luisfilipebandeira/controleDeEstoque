package com.example.play.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.play.model.Produto;
import com.example.play.ProdutoDAO;
import com.example.play.R;

public class FormProdutoActivity extends AppCompatActivity {

    private EditText edit_produto;
    private EditText edit_quantidade;
    private EditText edit_valor;

    private ProdutoDAO produtoDAO;

    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_produto);

        produtoDAO = new ProdutoDAO(this);

        edit_produto = findViewById(R.id.edit_produto);
        edit_quantidade = findViewById(R.id.edit_quantidade);
        edit_valor = findViewById(R.id.edit_valor);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            produto = (Produto) bundle.getSerializable("produto");

            editProduto();
        }
    }

    private void editProduto() {
        edit_produto.setText(produto.getNome());
        edit_quantidade.setText(String.valueOf(produto.getEstoque()));
        edit_valor.setText(String.valueOf(produto.getValor()));
    }

    public void salvarProduto(View view) {
        String nome = edit_produto.getText().toString();
        String quantidade = edit_quantidade.getText().toString();
        String valor = edit_valor.getText().toString();

        if (!nome.isEmpty()) {
         if(!quantidade.isEmpty() && Integer.parseInt(quantidade) >= 1) {
            if(!valor.isEmpty() && Double.parseDouble(valor) >= 1) {

                if(produto == null) produto = new Produto();
                produto.setNome(nome);
                produto.setEstoque(Integer.parseInt(quantidade));
                produto.setValor(Double.parseDouble(valor));

                if (produto.getId() != 0) {
                    produtoDAO.atualizaProduto(produto);
                } else {
                    produtoDAO.salvarProduto(produto);
                }

                finish();

            } else {
                edit_valor.requestFocus();
                edit_valor.setError("Informe o valor do produto");
            }
         } else {
             edit_quantidade.requestFocus();
             edit_quantidade.setError("Informe um valor válido");
         }
        } else {
            edit_produto.requestFocus();
            edit_produto.setError("Informe o nome do produto");
        }
    }
}