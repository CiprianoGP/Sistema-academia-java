package br.com.controle;

import br.com.dao.ManterPlano;
import br.com.modelo.Plano;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * CONTROLLER do Plano.
 */
public class PlanoControle {

    private ManterPlano dao = new ManterPlano();

    private boolean validar(Plano p) {
        if (p.getDescricao() == null || p.getDescricao().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe a descrição do plano!");
            return false;
        }
        if (p.getDuracaoMeses() <= 0) {
            JOptionPane.showMessageDialog(null, "Informe a duração em meses (número maior que zero)!");
            return false;
        }
        if (p.getValor() <= 0) {
            JOptionPane.showMessageDialog(null, "Informe o valor do plano (maior que zero)!");
            return false;
        }
        return true;
    }

    public boolean salvar(Plano p) {
        if (!validar(p)) {
            return false;
        }
        try {
            dao.inserir(p);
            JOptionPane.showMessageDialog(null, "Plano cadastrado com sucesso!");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar plano: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Plano p) {
        if (p.getCodigo() <= 0) {
            JOptionPane.showMessageDialog(null, "Pesquise ou selecione um plano na tabela primeiro!");
            return false;
        }
        if (!validar(p)) {
            return false;
        }
        try {
            dao.editar(p);
            JOptionPane.showMessageDialog(null, "Plano alterado com sucesso!");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar plano: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int codigo) {
        if (codigo <= 0) {
            JOptionPane.showMessageDialog(null, "Pesquise ou selecione um plano na tabela primeiro!");
            return false;
        }
        try {
            Plano p = new Plano();
            p.setCodigo(codigo);
            dao.deletar(p);
            JOptionPane.showMessageDialog(null, "Plano excluído com sucesso!");
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Não é possível excluir: este plano está em alguma matrícula.");
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir plano: " + e.getMessage());
            return false;
        }
    }

    public Plano pesquisar(int codigo) {
        if (codigo <= 0) {
            JOptionPane.showMessageDialog(null, "Digite um código válido!");
            return null;
        }
        try {
            Plano p = new Plano();
            p.setCodigo(codigo);
            if (dao.PesquisarRegistro(p)) {
                return p;
            }
            JOptionPane.showMessageDialog(null, "Nenhum plano encontrado com o código " + codigo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar plano: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Plano> listar() {
        try {
            return dao.PesquisarTudo();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar planos: " + e.getMessage());
            return new ArrayList<Plano>();
        }
    }
}
