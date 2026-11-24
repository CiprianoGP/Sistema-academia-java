package br.com.dao;

import br.com.modelo.Aluno;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Acesso ao banco da tabela ALUNO (Create, Read, Update, Delete).
 * HERDA de DAO para usar a conexão.
 */
public class ManterAluno extends DAO {

    // C - Create
    public void inserir(Aluno a) throws Exception {
        try {
            abrirBanco();
            String query = "INSERT INTO aluno (codigo, nome, cpf, telefone, email) values(null,?,?,?,?)";
            pst = con.prepareStatement(query);
            pst.setString(1, a.getNome());
            pst.setString(2, a.getCpf());
            pst.setString(3, a.getTelefone());
            pst.setString(4, a.getEmail());
            pst.execute();
        } finally {
            fecharBanco();
        }
    }

    // U - Update
    public void editar(Aluno a) throws Exception {
        try {
            abrirBanco();
            String query = "UPDATE aluno set nome = ?, cpf = ?, telefone = ?, email = ? where codigo = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, a.getNome()); 
            pst.setString(2, a.getCpf());
            pst.setString(3, a.getTelefone());
            pst.setString(4, a.getEmail());
            pst.setInt(5, a.getCodigo());
            pst.executeUpdate();
        } finally {
            fecharBanco();
        }
    }

    // D - Delete
    public void deletar(Aluno a) throws Exception {
        try {
            abrirBanco();
            String query = "delete from aluno where codigo = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, a.getCodigo());
            pst.execute();
        } finally {
            fecharBanco();
        }
    }

    // R - Read (um registro pelo código). Retorna true se encontrou.
    public boolean PesquisarRegistro(Aluno a) throws Exception {
        try {
            abrirBanco();
            String query = "select codigo, nome, cpf, telefone, email FROM aluno where codigo = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, a.getCodigo());
            ResultSet tr = pst.executeQuery();
            if (tr.next()) {
                a.setCodigo(tr.getInt("codigo"));
                a.setNome(tr.getString("nome"));
                a.setCpf(tr.getString("cpf"));
                a.setTelefone(tr.getString("telefone"));
                a.setEmail(tr.getString("email"));
                return true;
            }
            return false;
        } finally {
            fecharBanco();
        }
    }

    // R - Read (todos os registros)
    public ArrayList<Aluno> PesquisarTudo() throws Exception {
        ArrayList<Aluno> alunos = new ArrayList<Aluno>();
        try {
            abrirBanco();
            String query = "select * FROM aluno order by codigo";
            pst = con.prepareStatement(query);
            ResultSet tr = pst.executeQuery();
            Aluno a;
            while (tr.next()) {
                a = new Aluno();
                a.setCodigo(tr.getInt("codigo"));
                a.setNome(tr.getString("nome"));
                a.setCpf(tr.getString("cpf"));
                a.setTelefone(tr.getString("telefone"));
                a.setEmail(tr.getString("email"));
                alunos.add(a);
            }
        } finally {
            fecharBanco();
        }
        return alunos;
    }

    // verifica se já existe OUTRO aluno com o mesmo CPF
    public boolean existeCpf(String cpf, int codigoIgnorar) throws Exception {
        try {
            abrirBanco();
            String query = "select codigo FROM aluno where cpf = ? and codigo <> ?";
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
