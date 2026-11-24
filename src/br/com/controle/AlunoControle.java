package br.com.controle;

import br.com.dao.ManterAluno;
import br.com.modelo.Aluno;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * CONTROLLER do Aluno.
 * Faz a ponte entre a tela (View) e o banco (ManterAluno):
 * valida os dados, chama o DAO e mostra as mensagens.
 */
public class AlunoControle extends PessoaControle {

    private ManterAluno dao = new ManterAluno();

    public boolean salvar(Aluno a) {
        if (!validarPessoa(a)) {
            return false;
        }
        try {
            if (dao.existeCpf(a.getCpf(), 0)) {
                JOptionPane.showMessageDialog(null, "Já existe um aluno com esse CPF!");
                return false;
            }
            dao.inserir(a);
            JOptionPane.showMessageDialog(null, "Aluno cadastrado com sucesso!");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar aluno: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Aluno a) {
        if (a.getCodigo() <= 0) {
            JOptionPane.showMessageDialog(null, "Pesquise ou selecione um aluno na tabela primeiro!");
            return false;
        }
        if (!validarPessoa(a)) {
            return false;
        }
        try {
            if (dao.existeCpf(a.getCpf(), a.getCodigo())) {
                JOptionPane.showMessageDialog(null, "Já existe outro aluno com esse CPF!");
                return false;
            }
            dao.editar(a);
            JOptionPane.showMessageDialog(null, "Aluno alterado com sucesso!");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar aluno: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int codigo) {
        if (codigo <= 0) {
            JOptionPane.showMessageDialog(null, "Pesquise ou selecione um aluno na tabela primeiro!");
            return false;
        }
        try {
            Aluno a = new Aluno();
            a.setCodigo(codigo);
            dao.deletar(a);
            JOptionPane.showMessageDialog(null, "Aluno excluído com sucesso!");
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            // erro de chave estrangeira: o aluno está em alguma matrícula
            JOptionPane.showMessageDialog(null, "Não é possível excluir: este aluno possui matrícula cadastrada.");
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir aluno: " + e.getMessage());
            return false;
        }
    }

    public Aluno pesquisar(int codigo) {
        if (codigo <= 0) {
            JOptionPane.showMessageDialog(null, "Digite um código válido!");
            return null;
        }
        try {
            Aluno a = new Aluno();
            a.setCodigo(codigo);
            if (dao.PesquisarRegistro(a)) {
                return a;
            }
            JOptionPane.showMessageDialog(null, "Nenhum aluno encontrado com o código " + codigo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar aluno: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Aluno> listar() {
        try {
            return dao.PesquisarTudo();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar alunos: " + e.getMessage());
            return new ArrayList<Aluno>();
        }
    }
}
