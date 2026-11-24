package br.com.controle;

import br.com.dao.ManterModalidade;
import br.com.modelo.Modalidade;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * CONTROLLER da Modalidade.
 */
public class ModalidadeControle {

    private ManterModalidade dao = new ManterModalidade();

    private boolean validar(Modalidade m) {
        if (m.getNome() == null || m.getNome().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o nome da modalidade!");
            return false;
        }
        return true;
    }

    public boolean salvar(Modalidade m) {
        if (!validar(m)) {
            return false;
        }
        try {
            dao.inserir(m);
            JOptionPane.showMessageDialog(null, "Modalidade cadastrada com sucesso!");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar modalidade: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Modalidade m) {
        if (m.getCodigo() <= 0) {
            JOptionPane.showMessageDialog(null, "Pesquise ou selecione uma modalidade na tabela primeiro!");
            return false;
        }
        if (!validar(m)) {
            return false;
        }
        try {
            dao.editar(m);
            JOptionPane.showMessageDialog(null, "Modalidade alterada com sucesso!");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar modalidade: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int codigo) {
        if (codigo <= 0) {
            JOptionPane.showMessageDialog(null, "Pesquise ou selecione uma modalidade na tabela primeiro!");
            return false;
        }
        try {
            Modalidade m = new Modalidade();
            m.setCodigo(codigo);
            dao.deletar(m);
            JOptionPane.showMessageDialog(null, "Modalidade excluída com sucesso!");
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Não é possível excluir: esta modalidade está em alguma matrícula.");
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir modalidade: " + e.getMessage());
            return false;
        }
    }

    public Modalidade pesquisar(int codigo) {
        if (codigo <= 0) {
            JOptionPane.showMessageDialog(null, "Digite um código válido!");
            return null;
        }
        try {
            Modalidade m = new Modalidade();
            m.setCodigo(codigo);
            if (dao.PesquisarRegistro(m)) {
                return m;
            }
            JOptionPane.showMessageDialog(null, "Nenhuma modalidade encontrada com o código " + codigo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar modalidade: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Modalidade> listar() {
        try {
            return dao.PesquisarTudo();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar modalidades: " + e.getMessage());
            return new ArrayList<Modalidade>();
        }
    }
}
