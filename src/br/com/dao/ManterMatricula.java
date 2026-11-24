package br.com.dao;

import br.com.modelo.Aluno;
import br.com.modelo.Instrutor;
import br.com.modelo.Matricula;
import br.com.modelo.Modalidade;
import br.com.modelo.Plano;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Acesso ao banco da tabela MATRICULA (CRUD PRINCIPAL).
 * Aqui ficam a CONSULTA COM INNER JOIN e a consulta do RELATÓRIO.
 */
public class ManterMatricula extends DAO {

    // CONSULTA COM JOIN: junta matricula + aluno + plano + modalidade + instrutor
    // para mostrar os NOMES no lugar dos códigos das chaves estrangeiras
    private String consultaJoin = "SELECT m.codigo, m.valor, m.situacao, "
            + "DATE_FORMAT(m.data_inicio, '%d/%m/%Y') AS inicio, "
            + "DATE_FORMAT(m.data_vencimento, '%d/%m/%Y') AS vencimento, "
            + "a.codigo AS cod_aluno, a.nome AS nome_aluno, "
            + "p.codigo AS cod_plano, p.descricao AS desc_plano, p.duracao_meses, p.valor AS valor_plano, "
            + "mo.codigo AS cod_modalidade, mo.nome AS nome_modalidade, "
            + "i.codigo AS cod_instrutor, i.nome AS nome_instrutor "
            + "FROM matricula m "
            + "INNER JOIN aluno a ON m.cod_aluno = a.codigo "
            + "INNER JOIN plano p ON m.cod_plano = p.codigo "
            + "INNER JOIN modalidade mo ON m.cod_modalidade = mo.codigo "
            + "INNER JOIN instrutor i ON m.cod_instrutor = i.codigo ";

    public void inserir(Matricula m) throws Exception {
        try {
            abrirBanco();
            String query = "INSERT INTO matricula (codigo, cod_aluno, cod_plano, cod_modalidade, cod_instrutor, "
                    + "data_inicio, data_vencimento, valor, situacao) "
                    + "values(null,?,?,?,?, STR_TO_DATE(?, '%d/%m/%Y'), STR_TO_DATE(?, '%d/%m/%Y'), ?, ?)";
            pst = con.prepareStatement(query);
            pst.setInt(1, m.getAluno().getCodigo());       // chave estrangeira
            pst.setInt(2, m.getPlano().getCodigo());       // chave estrangeira
            pst.setInt(3, m.getModalidade().getCodigo());  // chave estrangeira
            pst.setInt(4, m.getInstrutor().getCodigo());   // chave estrangeira
            pst.setString(5, m.getDataInicio());
            pst.setString(6, m.getDataVencimento());
            pst.setDouble(7, m.getValor());
            pst.setString(8, m.getSituacao());
            pst.execute();
        } finally {
            fecharBanco();
        }
    }

    public void editar(Matricula m) throws Exception {
        try {
            abrirBanco();
            String query = "UPDATE matricula set cod_aluno = ?, cod_plano = ?, cod_modalidade = ?, cod_instrutor = ?, "
                    + "data_inicio = STR_TO_DATE(?, '%d/%m/%Y'), data_vencimento = STR_TO_DATE(?, '%d/%m/%Y'), "
                    + "valor = ?, situacao = ? where codigo = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, m.getAluno().getCodigo());
            pst.setInt(2, m.getPlano().getCodigo());
            pst.setInt(3, m.getModalidade().getCodigo());
            pst.setInt(4, m.getInstrutor().getCodigo());
            pst.setString(5, m.getDataInicio());
            pst.setString(6, m.getDataVencimento());
            pst.setDouble(7, m.getValor());
            pst.setString(8, m.getSituacao());
            pst.setInt(9, m.getCodigo());
            pst.executeUpdate();
        } finally {
            fecharBanco();
        }
    }

    public void deletar(Matricula m) throws Exception {
        try {
            abrirBanco();
            String query = "delete from matricula where codigo = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, m.getCodigo());
            pst.execute();
        } finally {
            fecharBanco();
        }
    }

    // pesquisa uma matrícula pelo código (usando o JOIN)
    public boolean PesquisarRegistro(Matricula m) throws Exception {
        try {
            abrirBanco();
            String query = consultaJoin + "WHERE m.codigo = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, m.getCodigo());
            ResultSet tr = pst.executeQuery();
            if (tr.next()) {
                preencherMatricula(m, tr);
                return true;
            }
            return false;
        } finally {
            fecharBanco();
        }
    }

    // lista todas as matrículas (usando o JOIN)
    public ArrayList<Matricula> PesquisarTudo() throws Exception {
        ArrayList<Matricula> matriculas = new ArrayList<Matricula>();
        try {
            abrirBanco();
            String query = consultaJoin + "ORDER BY m.codigo";
            pst = con.prepareStatement(query);
            ResultSet tr = pst.executeQuery();
            while (tr.next()) {
                Matricula m = new Matricula();
                preencherMatricula(m, tr);
                matriculas.add(m);
            }
        } finally {
            fecharBanco();
        }
        return matriculas;
    }

    // RELATÓRIO: matrículas por período (data de início) e situação
    public ArrayList<Matricula> relatorioPorPeriodo(String dataInicial, String dataFinal, String situacao) throws Exception {
        ArrayList<Matricula> matriculas = new ArrayList<Matricula>();
        try {
            abrirBanco();
            String query = consultaJoin
                    + "WHERE m.data_inicio BETWEEN STR_TO_DATE(?, '%d/%m/%Y') AND STR_TO_DATE(?, '%d/%m/%Y') ";
            if (situacao != null) {
                query = query + "AND m.situacao = ? ";
            }
            query = query + "ORDER BY m.data_inicio";
            pst = con.prepareStatement(query);
            pst.setString(1, dataInicial);
            pst.setString(2, dataFinal);
            if (situacao != null) {
                pst.setString(3, situacao);
            }
            ResultSet tr = pst.executeQuery();
            while (tr.next()) {
                Matricula m = new Matricula();
                preencherMatricula(m, tr);
                matriculas.add(m);
            }
        } finally {
            fecharBanco();
        }
        return matriculas;
    }

    // regra: o aluno não pode ter duas matrículas ATIVAS na mesma modalidade
    public boolean existeMatriculaAtiva(int codAluno, int codModalidade, int codigoIgnorar) throws Exception {
        try {
            abrirBanco();
            String query = "select codigo FROM matricula where cod_aluno = ? and cod_modalidade = ? "
                    + "and situacao = 'Ativa' and codigo <> ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, codAluno);
            pst.setInt(2, codModalidade);
            pst.setInt(3, codigoIgnorar);
            ResultSet tr = pst.executeQuery();
            return tr.next();
        } finally {
            fecharBanco();
        }
    }

    // monta o objeto Matricula (e os objetos ligados a ela) a partir do resultado do JOIN
    private void preencherMatricula(Matricula m, ResultSet tr) throws Exception {
        m.setCodigo(tr.getInt("codigo"));
        m.setDataInicio(tr.getString("inicio"));
        m.setDataVencimento(tr.getString("vencimento"));
        m.setValor(tr.getDouble("valor"));
        m.setSituacao(tr.getString("situacao"));

        Aluno a = new Aluno();
        a.setCodigo(tr.getInt("cod_aluno"));
        a.setNome(tr.getString("nome_aluno"));
        m.setAluno(a);

        Plano p = new Plano();
        p.setCodigo(tr.getInt("cod_plano"));
        p.setDescricao(tr.getString("desc_plano"));
        p.setDuracaoMeses(tr.getInt("duracao_meses"));
        p.setValor(tr.getDouble("valor_plano"));
        m.setPlano(p);

        Modalidade mo = new Modalidade();
        mo.setCodigo(tr.getInt("cod_modalidade"));
        mo.setNome(tr.getString("nome_modalidade"));
        m.setModalidade(mo);

        Instrutor i = new Instrutor();
        i.setCodigo(tr.getInt("cod_instrutor"));
        i.setNome(tr.getString("nome_instrutor"));
        m.setInstrutor(i);
    }
}
