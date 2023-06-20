package com.unipar.cadastronotasalunos;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.unipar.cadastronotasalunos.Models.Alunos;
import com.unipar.cadastronotasalunos.Utilities.Listagem_notas;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Relacao_media_alunos extends AppCompatActivity {

    private Spinner sp_disciplina;

    private ListView lv_medias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relacao_medias);
        sp_disciplina = findViewById(R.id.spn_disciplina_layout_relacao_medias);
        lv_medias = findViewById(R.id.lv_disciplina_relacao_medias);

            String[] vetorDisciplina = new String[]{
                    "Matemática",
                    "PortuguêsPortuguês",
                    "Ciências",
                    "História",
            };

            ArrayAdapter<String> adapterDisciplina = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vetorDisciplina);
        sp_disciplina.setAdapter(adapterDisciplina);

        sp_disciplina.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                atualizaLista(Listagem_notas.listarNotas, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        private void atualizaLista(ArrayList<Alunos> lista, int disciplinaSelecionadaPosicao) {
            String nomeDisciplinaSelecionada = vetorDisciplina[disciplinaSelecionadaPosicao];

            ArrayList<Alunos> listaFiltrada = new ArrayList<>();

            for (Alunos aluno : lista) {
                if (aluno.getBimestre().getNome().equals(nomeDisciplinaSelecionada)) {
                    listaFiltrada.add(aluno);
                }
            }

            mediaAdapter adapter = new mediaAdapter(this, listaFiltrada);
            lv_medias.setAdapter(adapter);
        }


    }
