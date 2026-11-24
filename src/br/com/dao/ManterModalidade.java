package br.com.dao;

import br.com.modelo.Modalidade;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Acesso ao banco da tabela MODALIDADE (Create, Read, Update, Delete).
 * HERDA de DAO para usar a conexão.
 */
public class ManterModalidade extends DAO {

    public void inserir(Modalidade m) throws Exception {
        try {
            abrirBanco();
            String query = "INSERT INTO modalidade (codigo, nome, descricao) values(null,?,?)";
            pst = con.prepareStatement(query);
            pst.setString(1, m.getNome());
            pst.setString(2, m.getDescricao());
            pst.execute();
        } finally {
            fecharBanco();
        }
    }

    public void editar(Modalidade m) throws Exception {
        try {
            abrirBanco();
            String query = "UPDATE modalidade set nome = ?, descricao = ? where codigo = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, m.getNome());
            pst.setString(2, m.getDescricao());
            pst.setInt(3, m.getCodigo());
            pst.executeUpdate();
        } finally {
            fecharBanco();
        }
    }

    public void deletar(Modalidade m) throws Exception {
        try {
            abrirBanco();
            String query = "delete from modalidade where codigo = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, m.getCodigo());
            pst.execute();
        } finally {
            fecharBanco();
        }
    }

    public boolean PesquisarRegistro(Modalidade m) throws Exception {
        try {
            abrirBanco();
            String query = "select codigo, nome, descricao FROM modalidade where codigo = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, m.getCodigo());
            ResultSet tr = pst.executeQuery();
            if (tr.next()) {
                m.setCodigo(tr.getInt("codigo"));
                m.setNome(tr.getString("nome"));
                m.setDescricao(tr.getString("descricao"));
                return true;
            }
            return false;
        } finally {
            fecharBanco();
        }
    }

    public ArrayList<Modalidade> PesquisarTudo() throws Exception {
        ArrayList<Modalidade> modalidades = new ArrayList<Modalidade>();
        try {
            abrirBanco();
            String query = "select * FROM modalidade order by codigo";
            pst = con.prepareStatement(query);
            ResultSet tr = pst.executeQuery();
            Modalidade m;
            while (tr.next()) {
                m = new Modalidade();
                m.setCodigo(tr.getInt("codigo"));
                m.setNome(tr.getString("nome"));
                m.setDescricao(tr.getString("descricao"));
                modalidades.add(m);
            }
        } finally {
            fecharBanco();
        }
        return modalidades;
    }
}
