package br.com.dao;

import br.com.modelo.Plano;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Acesso ao banco da tabela PLANO (Create, Read, Update, Delete).
 * HERDA de DAO para usar a conexão.
 */
public class ManterPlano extends DAO {

    public void inserir(Plano p) throws Exception {
        try {
            abrirBanco();
            String query = "INSERT INTO plano (codigo, descricao, duracao_meses, valor) values(null,?,?,?)";
            pst = con.prepareStatement(query);
            pst.setString(1, p.getDescricao());
            pst.setInt(2, p.getDuracaoMeses());
            pst.setDouble(3, p.getValor());
            pst.execute();
        } finally {
            fecharBanco();
        }
    }

    public void editar(Plano p) throws Exception {
        try {
            abrirBanco();
            String query = "UPDATE plano set descricao = ?, duracao_meses = ?, valor = ? where codigo = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, p.getDescricao());
            pst.setInt(2, p.getDuracaoMeses());
            pst.setDouble(3, p.getValor());
            pst.setInt(4, p.getCodigo());
            pst.executeUpdate();
        } finally {
            fecharBanco();
        }
    }

    public void deletar(Plano p) throws Exception {
        try {
            abrirBanco();
            String query = "delete from plano where codigo = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, p.getCodigo());
            pst.execute();
        } finally {
            fecharBanco();
        }
    }

    public boolean PesquisarRegistro(Plano p) throws Exception {
        try {
            abrirBanco();
            String query = "select codigo, descricao, duracao_meses, valor FROM plano where codigo = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, p.getCodigo());
            ResultSet tr = pst.executeQuery();
            if (tr.next()) {
                p.setCodigo(tr.getInt("codigo"));
                p.setDescricao(tr.getString("descricao"));
                p.setDuracaoMeses(tr.getInt("duracao_meses"));
                p.setValor(tr.getDouble("valor"));
                return true;
            }
            return false;
        } finally {
            fecharBanco();
        }
    }

    public ArrayList<Plano> PesquisarTudo() throws Exception {
        ArrayList<Plano> planos = new ArrayList<Plano>();
        try {
            abrirBanco();
            String query = "select * FROM plano order by codigo";
            pst = con.prepareStatement(query);
            ResultSet tr = pst.executeQuery();
            Plano p;
            while (tr.next()) {
                p = new Plano();
                p.setCodigo(tr.getInt("codigo"));
                p.setDescricao(tr.getString("descricao"));
                p.setDuracaoMeses(tr.getInt("duracao_meses"));
                p.setValor(tr.getDouble("valor"));
                planos.add(p);
            }
        } finally {
            fecharBanco();
        }
        return planos;
    }
}
