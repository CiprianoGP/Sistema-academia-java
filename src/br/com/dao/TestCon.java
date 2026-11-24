package br.com.dao;

/**
 * Classe só para testar se a conexão com o banco está funcionando.
 * Botão direito > Executar arquivo (Shift+F6).
 */
public class TestCon {

    public static void main(String[] args) throws Exception {
        DAO cx = new DAO();
        cx.abrirBanco();
        cx.fecharBanco();
    }
}
