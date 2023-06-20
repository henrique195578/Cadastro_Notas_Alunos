package com.unipar.cadastronotasalunos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.text.TextUtils;

import com.unipar.cadastronotasalunos.Models.Alunos;
import com.unipar.cadastronotasalunos.Models.Bimestre;
import com.unipar.cadastronotasalunos.Utilities.Listagem_notas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    private EditText et_ra_aluno;
    private EditText et_nome_aluno;
    private EditText et_insere_nota;
    private Spinner spn_disciplina;
    private Spinner spn_bimestre;
    private Spinner spn_aluno_layout_relacao_notas;
    private Spinner spn_disciplina_layout_relacao_medias;
    private Button btn_adicionar;
    private Button btn_ver_notas;
    private Button btn_ver_medias;
    private Bimestre bmtSel;

    private String bimeste_selecionado;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_notas_alunos);
        spn_aluno_layout_relacao_notas = findViewById(R.id.spn_aluno_layout_relacao_notas);
        et_ra_aluno = findViewById(R.id.et_ra_aluno);
        et_nome_aluno = findViewById(R.id.et_nome_aluno);
        et_insere_nota = findViewById(R.id.et_insere_nota);
        spn_disciplina = findViewById(R.id.spn_disciplina);
        spn_bimestre = findViewById(R.id.spn_bimestre);
        btn_adicionar = findViewById(R.id.btn_adicionar);
        btn_ver_notas = findViewById(R.id.btn_ver_notas);
        btn_ver_medias = findViewById(R.id.btn_ver_medias);

        // Definir um listener de clique para o botão "Adicionar"
        btn_adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarNota();
            }
        });

        String[] vetorBimestre = new String[]{"", "(1º)Primeiro", "(2º)Segundo", "(3º)Terceiro", "(4º)Quarto"};
        Bimestre materia1 = new Bimestre();
        materia1.setId(1);
        materia1.setNome("Matemática");
        materia1.setPrimeiro_bimestre(0);
        materia1.setSegundo_bimestre(0);
        materia1.setTerceiro_bimestre(0);
        materia1.setQuarto_bimestre(0);

        Bimestre materia2 = new Bimestre();
        materia2.setId(2);
        materia2.setNome("Português");
        materia2.setPrimeiro_bimestre(0);
        materia2.setSegundo_bimestre(0);
        materia2.setTerceiro_bimestre(0);
        materia2.setQuarto_bimestre(0);

        Bimestre materia3 = new Bimestre();
        materia3.setId(3);
        materia3.setNome("Ciências");
        materia3.setPrimeiro_bimestre(0);
        materia3.setSegundo_bimestre(0);
        materia3.setTerceiro_bimestre(0);
        materia3.setQuarto_bimestre(0);

        Bimestre materia4 = new Bimestre();
        materia4.setId(4);
        materia4.setNome("História");
        materia4.setPrimeiro_bimestre(0);
        materia4.setSegundo_bimestre(0);
        materia4.setTerceiro_bimestre(0);
        materia4.setQuarto_bimestre(0);

        Bimestre[] vetorAnoletivo = new Bimestre[]{materia1, materia2, materia3, materia4};

        ArrayAdapter adapterbm = new ArrayAdapter(this, android.R.layout.simple_list_item_1, vetorBimestre);

        List<String> listaNomesDisciplinas = new ArrayList<>();

        Arrays.stream(vetorAnoletivo)
                .map(Bimestre::getNome)
                .forEach(listaNomesDisciplinas::add);

        ArrayAdapter<String> adapterMateria = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaNomesDisciplinas);

        spn_disciplina.setAdapter(adapterMateria);
        spn_bimestre.setAdapter(adapterbm);

        spn_disciplina.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nomeDisciplina = (String) spn_disciplina.getItemAtPosition(position);

                Bimestre disciplinaSelecionada = null;
                for (Bimestre disciplina : vetorAnoletivo) {
                    if (disciplina.getNome().equals(nomeDisciplina)) {
                        disciplinaSelecionada = disciplina;
                        Bimestre bmtSel = disciplinaSelecionada;
                        break;
                    }
                }

                // Realize ação com a disciplina selecionada
                if (disciplinaSelecionada != null) {
                    // Exemplo: Exiba o nome da disciplina selecionada
                    Toast.makeText(getApplicationContext(), disciplinaSelecionada.getNome(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_bimestre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                bimeste_selecionado = (String) spn_bimestre.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_ver_medias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirRelacao_media();
            }
        });

        btn_ver_notas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirRelacao_nota();
            }
        });

    }

    private void adicionarNota () {
        // Obter os valores inseridos pelo usuário

        String ra = et_ra_aluno.getText().toString();
        String nome = et_nome_aluno.getText().toString();
        String nota = et_insere_nota.getText().toString();
        String disciplina = spn_disciplina.getSelectedItem().toString();
        String bimestre = spn_bimestre.getSelectedItem().toString();

        // Lista para armazenar os campos pendentes
        List<String> camposPendentes = new ArrayList<>();

        // Verificar se algum campo está nulo e adicionar à lista de campos pendentes
        if (ra.isEmpty()) {
            camposPendentes.add("R.A");
        }
        if (nome.isEmpty()) {
            camposPendentes.add("NOME");
        }
        if (nota.isEmpty()) {
            camposPendentes.add("NOTA");
        }
        if (disciplina.isEmpty()) {
            camposPendentes.add("DISCIPLINA");
        }
        if (bimestre.isEmpty()) {
            camposPendentes.add("BIMESTRE");
        }

        // Verificar se há campos pendentes e exibir mensagem
        if (!camposPendentes.isEmpty()) {
            String camposPendentesStr = TextUtils.join(", ", camposPendentes);
            String mensagem = "Preencher os campos vazios: " + camposPendentesStr;

            // Mostrar o alerta com o botão "OK"
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(mensagem)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Definir o Drawable de fundo vermelho
                            Drawable campoPendenteBackground = getResources().getDrawable(R.drawable.campo_pendente_background);

                            // Aplicar o estilo de fundo aos campos pendentes
                            et_ra_aluno.setBackground(camposPendentes.contains("R.A") ? campoPendenteBackground : null);
                            et_nome_aluno.setBackground(camposPendentes.contains("NOME") ? campoPendenteBackground : null);
                            et_insere_nota.setBackground(camposPendentes.contains("NOTA") ? campoPendenteBackground : null);
                            spn_disciplina.setBackground(camposPendentes.contains("DISCIPLINA") ? campoPendenteBackground : null);
                            spn_bimestre.setBackground(camposPendentes.contains("BIMESTRE") ? campoPendenteBackground : null);

                        }
                    })
                    .create()
                    .show();
        } else {
            Toast.makeText(this, "Nota adicionada: " + nota, Toast.LENGTH_SHORT).show();
        }
        try {
            Alunos alunos = new Alunos();
            int posicaoAlunos = -1;

            for (int i = 0; i < Listagem_notas.listarNotas.size(); i++){
                if (String.valueOf(Listagem_notas.listarNotas.get(i).getRa()).equals(et_ra_aluno.getText().toString())
                        && Listagem_notas.listarNotas.get(i).getNome().equals(et_nome_aluno.getText().toString())
                        && Listagem_notas.listarNotas.get(i).getBimestre().getNome().equals(bmtSel.getNome())) {
                    posicaoAlunos = i;
                }
            }

            if (posicaoAlunos == -1) {
                alunos.setNome(et_nome_aluno.getText().toString());
                alunos.setRa(Integer.parseInt(et_ra_aluno.getText().toString()));
                alunos.setBimestre(bmtSel);

                switch (bimeste_selecionado){
                    case "(1º)Primeiro":
                        alunos.getBimestre().setPrimeiro_bimestre(Integer.parseInt(et_insere_nota.getText().toString()));
                        break;
                    case "(2º)Primeiro":
                        alunos.getBimestre().setSegundo_bimestre(Integer.parseInt(et_insere_nota.getText().toString()));
                        break;
                    case "(3º)Primeiro":
                        alunos.getBimestre().setTerceiro_bimestre(Integer.parseInt(et_insere_nota.getText().toString()));
                        break;
                    case "(4º)Primeiro":
                        alunos.getBimestre().setQuarto_bimestre(Integer.parseInt(et_insere_nota.getText().toString()));
                        break;
                }
                Listagem_notas.listarNotas.add(alunos);
                posicaoAlunos = -1;
            }

            if (posicaoAlunos != -1) {
                Listagem_notas.listarNotas.get(posicaoAlunos).setNome(et_nome_aluno.getText().toString());
                Listagem_notas.listarNotas.get(posicaoAlunos).setRa(Integer.parseInt(et_ra_aluno.getText().toString()));
                Listagem_notas.listarNotas.get(posicaoAlunos).setBimestre(bmtSel);

                switch (bimeste_selecionado) {
                    case "(1º)Primeiro":
                        Listagem_notas.listarNotas.get(posicaoAlunos).getBimestre().setPrimeiro_bimestre(Integer.parseInt(et_insere_nota.getText().toString()));
                    case "(2º)Segundo":
                        Listagem_notas.listarNotas.get(posicaoAlunos).getBimestre().setSegundo_bimestre(Integer.parseInt(et_insere_nota.getText().toString()));
                    case "(3º)Terceiro":
                        Listagem_notas.listarNotas.get(posicaoAlunos).getBimestre().setTerceiro_bimestre(Integer.parseInt(et_insere_nota.getText().toString()));
                    case "(4º)Quarto":
                        Listagem_notas.listarNotas.get(posicaoAlunos).getBimestre().setQuarto_bimestre(Integer.parseInt(et_insere_nota.getText().toString()));
                }
                posicaoAlunos = -1;
            }

            Toast.makeText(this, "Aluno inserido com sucesso!", Toast.LENGTH_SHORT).show();
        }catch (Exception exception){
            Log.e("Error ao Salvar Aluno", exception.getMessage());
        }
    }

    private void abrirRelacao_nota() {
        try {
            Intent intent = new Intent(this, Relacao_notas_alunos.class);
            startActivity(intent);
        }catch (Exception exception){
            exception.printStackTrace();
            Toast.makeText(this, "Error:" + exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirRelacao_media() {
        try {
            Intent intent = new Intent(this, Relacao_media_alunos.class);
            startActivity(intent);
        } catch (Exception exception) {
            Toast.makeText(this, "Não há alunos cadastrado, por gentileza registrar!", Toast.LENGTH_SHORT).show();
        }
    }
}