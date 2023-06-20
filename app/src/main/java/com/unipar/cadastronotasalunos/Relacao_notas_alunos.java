package com.unipar.cadastronotasalunos;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.unipar.cadastronotasalunos.MainActivity;
import com.unipar.cadastronotasalunos.Models.Alunos;
import com.unipar.cadastronotasalunos.Utilities.Listagem_notas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Relacao_notas_alunos extends AppCompatActivity {

    private ListView listadeNotas;
    private Spinner spn_aluno_layout_relacao_notas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relacaonotas);

        listadeNotas = findViewById(R.id.lv_lista_alunos_relacao_notas);
        spn_aluno_layout_relacao_notas = findViewById(R.id.spn_aluno_layout_relacao_notas);
        ArrayList<String> arrayAlunos = new ArrayList<>();

        for (int i = 0; i < Listagem_notas.listarNotas.size(); i++) {
            String nome = Listagem_notas.listarNotas.get(i).getNome();
            if (!arrayAlunos.contains(nome)) {
                arrayAlunos.add(nome);
            }
        }

        ArrayAdapter adapterAlunos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayAlunos);

        spn_aluno_layout_relacao_notas.setAdapter(adapterAlunos);

        spn_aluno_layout_relacao_notas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                atualizaLista(Listagem_notas.listarNotas, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void atualizaLista(ArrayList<Alunos> lista, int alunoSelecionadoPosicao) {

        ArrayList<Alunos> listaFiltrada = new ArrayList<>();

        for (int i = 0; i < lista.size(); i++) {
            String nomeAlunoSelecionado = spn_aluno_layout_relacao_notas.getItemAtPosition(alunoSelecionadoPosicao).toString();
            if (lista.get(i).getNome().equals(nomeAlunoSelecionado)) {
                listaFiltrada.add(lista.get(i));
            }
        }

        notaAdapter adapter = new notaAdapter(this, listaFiltrada);
        adapter.notifyDataSetChanged();
        listadeNotas.setAdapter(adapter);
    }
    }


