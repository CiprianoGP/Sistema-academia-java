package br.com.controle;

import br.com.dao.ManterInstrutor;
import br.com.modelo.Instrutor;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * CONTROLLER do Instrutor.
 * HERDA de PessoaControle para reaproveitar a validação de nome e CPF.
 */
public class InstrutorControle extends PessoaControle {

    private ManterInstrutor dao = new ManterInstrutor();

    public boolean salvar(Instrutor i) {
        if (!validarPessoa(i)) {
            return false;
        }
        try {
            if (dao.existeCpf(i.getCpf(), 0)) {
                JOptionPane.showMessageDialog(null, "Já existe um instrutor com esse CPF!");
                return false;
            }
            dao.inserir(i);
            JOptionPane.showMessageDialog(null, "Instrutor cadastrado com sucesso!");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar instrutor: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Instrutor i) {
        if (i.getCodigo() <= 0) {
            JOptionPane.showMessageDialog(null, "Pesquise ou selecione um instrutor na tabela primeiro!");
            return false;
        }
        if (!validarPessoa(i)) {
            return false;
        }
        try {
            if (dao.existeCpf(i.getCpf(), i.getCodigo())) {
                JOptionPane.showMessageDialog(null, "Já existe outro instrutor com esse CPF!");
                return false;
            }
            dao.editar(i);
            JOptionPane.showMessageDialog(null, "Instrutor alterado com sucesso!");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar instrutor: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int codigo) {
        if (codigo <= 0) {
            JOptionPane.showMessageDialog(null, "Pesquise ou selecione um instrutor na tabela primeiro!");
            return false;
        }
        try {
            Instrutor i = new Instrutor();
            i.setCodigo(codigo);
            dao.deletar(i);
            JOptionPane.showMessageDialog(null, "Instrutor excluído com sucesso!");
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Não é possível excluir: este instrutor está em alguma matrícula.");
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir instrutor: " + e.getMessage());
            return false;
        }
    }

    public Instrutor pesquisar(int codigo) {
        if (codigo <= 0) {
            JOptionPane.showMessageDialog(null, "Digite um código válido!");
            return null;
        }
        try {
            Instrutor i = new Instrutor();
            i.setCodigo(codigo);
            if (dao.PesquisarRegistro(i)) {
                return i;
            }
            JOptionPane.showMessageDialog(null, "Nenhum instrutor encontrado com o código " + codigo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar instrutor: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Instrutor> listar() {
        try {
            return dao.PesquisarTudo();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar instrutores: " + e.getMessage());
            return new ArrayList<Instrutor>();
        }
    }
}
