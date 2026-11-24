package br.com.controle;

import br.com.dao.ManterMatricula;
import br.com.modelo.Matricula;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * CONTROLLER da Matrícula (ROTINA PRINCIPAL).
 * Aqui ficam as REGRAS DE NEGÓCIO da academia:
 *  - a data de vencimento é calculada pela duração do plano;
 *  - o valor da matrícula é o valor do plano escolhido;
 *  - o aluno não pode ter 2 matrículas ATIVAS na mesma modalidade.
 */
public class MatriculaControle {

    private ManterMatricula dao = new ManterMatricula();
    private DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // valida os campos e calcula vencimento e valor
    private boolean validarECalcular(Matricula m) {
        if (m.getAluno() == null || m.getPlano() == null
                || m.getModalidade() == null || m.getInstrutor() == null) {
            JOptionPane.showMessageDialog(null, "Selecione o aluno, o plano, a modalidade e o instrutor!");
            return false;
        }
        LocalDate inicio;
        try {
            inicio = LocalDate.parse(m.getDataInicio(), formato);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Data de início inválida! Use o formato dd/mm/aaaa.");
            return false;
        }
        // REGRA: vencimento = data de início + duração do plano (em meses)
        LocalDate vencimento = inicio.plusMonths(m.getPlano().getDuracaoMeses());
        m.setDataInicio(inicio.format(formato));
        m.setDataVencimento(vencimento.format(formato));
        // REGRA: o valor da matrícula é o valor do plano
        m.setValor(m.getPlano().getValor());
        return true;
    }

    public boolean salvar(Matricula m) {
        if (!validarECalcular(m)) {
            return false;
        }
        try {
            if (m.getSituacao().equals("Ativa")
                    && dao.existeMatriculaAtiva(m.getAluno().getCodigo(), m.getModalidade().getCodigo(), 0)) {
                JOptionPane.showMessageDialog(null, "Este aluno já possui uma matrícula ATIVA nessa modalidade!");
                return false;
            }
            dao.inserir(m);
            JOptionPane.showMessageDialog(null, "Matrícula realizada com sucesso!\n"
                    + "Vencimento: " + m.getDataVencimento()
                    + "\nValor: R$ " + String.format("%.2f", m.getValor()));
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar matrícula: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Matricula m) {
        if (m.getCodigo() <= 0) {
            JOptionPane.showMessageDialog(null, "Pesquise ou selecione uma matrícula na tabela primeiro!");
            return false;
        }
        if (!validarECalcular(m)) {
            return false;
        }
        try {
            if (m.getSituacao().equals("Ativa")
                    && dao.existeMatriculaAtiva(m.getAluno().getCodigo(), m.getModalidade().getCodigo(), m.getCodigo())) {
                JOptionPane.showMessageDialog(null, "Este aluno já possui outra matrícula ATIVA nessa modalidade!");
                return false;
            }
            dao.editar(m);
            JOptionPane.showMessageDialog(null, "Matrícula alterada com sucesso!");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar matrícula: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int codigo) {
        if (codigo <= 0) {
            JOptionPane.showMessageDialog(null, "Pesquise ou selecione uma matrícula na tabela primeiro!");
            return false;
        }
        try {
            Matricula m = new Matricula();
            m.setCodigo(codigo);
            dao.deletar(m);
            JOptionPane.showMessageDialog(null, "Matrícula excluída com sucesso!");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir matrícula: " + e.getMessage());
            return false;
        }
    }

    public Matricula pesquisar(int codigo) {
        if (codigo <= 0) {
            JOptionPane.showMessageDialog(null, "Digite um código válido!");
            return null;
        }
        try {
            Matricula m = new Matricula();
            m.setCodigo(codigo);
            if (dao.PesquisarRegistro(m)) {
                return m;
            }
            JOptionPane.showMessageDialog(null, "Nenhuma matrícula encontrada com o código " + codigo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar matrícula: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Matricula> listar() {
        try {
            return dao.PesquisarTudo();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar matrículas: " + e.getMessage());
            return new ArrayList<Matricula>();
        }
    }

    // RELATÓRIO: situacao = "Todas" traz todas as situações
    public ArrayList<Matricula> relatorio(String dataInicial, String dataFinal, String situacao) {
        try {
            LocalDate ini = LocalDate.parse(dataInicial, formato);
            LocalDate fim = LocalDate.parse(dataFinal, formato);
            if (ini.isAfter(fim)) {
                JOptionPane.showMessageDialog(null, "A data inicial não pode ser maior que a data final!");
                return new ArrayList<Matricula>();
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Datas inválidas! Use o formato dd/mm/aaaa.");
            return new ArrayList<Matricula>();
        }
        try {
            if (situacao.equals("Todas")) {
                situacao = null;
            }
            return dao.relatorioPorPeriodo(dataInicial, dataFinal, situacao);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório: " + e.getMessage());
            return new ArrayList<Matricula>();
        }
    }

    // soma o valor de todas as matrículas da lista (usado no relatório)
    public double calcularTotal(ArrayList<Matricula> lista) {
        double total = 0;
        for (Matricula m : lista) {
            total = total + m.getValor();
        }
        return total;
    }
}
