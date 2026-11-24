package br.com.dao;

import br.com.modelo.Instrutor;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Acesso ao banco da tabela INSTRUTOR (Create, Read, Update, Delete).
 * HERDA de DAO para usar a conexão.
 */
public class ManterInstrutor extends DAO {

    public void inserir(Instrutor i) throws Exception {
        try {
            abrirBanco();
            String query = "INSERT INTO instrutor (codigo, nome, cpf, telefone, cref) values(null,?,?,?,?)";
            pst = con.prepareStatement(query);
            pst.setString(1, i.getNome());
            pst.setString(2, i.getCpf());
            pst.setString(3, i.getTelefone());
            pst.setString(4, i.getCref());
            pst.execute();
        } finally {
            fecharBanco();
        }
    }

    public void editar(Instrutor i) throws Exception {
        try {
            abrirBanco();
            String query = "UPDATE instrutor set nome = ?, cpf = ?, telefone = ?, cref = ? where codigo = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, i.getNome());
            pst.setString(2, i.getCpf());
            pst.setString(3, i.getTelefone());
            pst.setString(4, i.getCref());
            pst.setInt(5, i.getCodigo());
            pst.executeUpdate();
        } finally {
            fecharBanco();
        }
    }

    public void deletar(Instrutor i) throws Exception {
        try {
            abrirBanco();
            String query = "delete from instrutor where codigo = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, i.getCodigo());
            pst.execute();
        } finally {
            fecharBanco();
        }
    }

    public boolean PesquisarRegistro(Instrutor i) throws Exception {
        try {
            abrirBanco();
            String query = "select codigo, nome, cpf, telefone, cref FROM instrutor where codigo = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, i.getCodigo());
            ResultSet tr = pst.executeQuery();
            if (tr.next()) {
                i.setCodigo(tr.getInt("codigo"));
                i.setNome(tr.getString("nome"));
                i.setCpf(tr.getString("cpf"));
                i.setTelefone(tr.getString("telefone"));
                i.setCref(tr.getString("cref"));
                return true;
            }
            return false;
        } finally {
            fecharBanco();
        }
    }

    public ArrayList<Instrutor> PesquisarTudo() throws Exception {
        ArrayList<Instrutor> instrutores = new ArrayList<Instrutor>();
        try {
            abrirBanco();
            String query = "select * FROM instrutor order by codigo";
            pst = con.prepareStatement(query);
            ResultSet tr = pst.executeQuery();
            Instrutor i;
            while (tr.next()) {
                i = new Instrutor();
                i.setCodigo(tr.getInt("codigo"));
                i.setNome(tr.getString("nome"));
                i.setCpf(tr.getString("cpf"));
                i.setTelefone(tr.getString("telefone"));
                i.setCref(tr.getString("cref"));
                instrutores.add(i);
            }
        } finally {
            fecharBanco();
        }
        return instrutores;
    }

    // verifica se já existe OUTRO instrutor com o mesmo CPF
    public boolean existeCpf(String cpf, int codigoIgnorar) throws Exception {
        try {
            abrirBanco();
            String query = "select codigo FROM instrutor where cpf = ? and codigo <> ?";
            pst = con.prepareStatement(query);
            pst.setString(1, cpf);
            pst.setInt(2, codigoIgnorar);
            ResultSet tr = pst.executeQuery();
            return tr.next();
        } finally {
            fecharBanco();
        }
    }
}
