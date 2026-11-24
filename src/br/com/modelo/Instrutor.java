package br.com.modelo;

/**
 * MODEL - Instrutor (professor de educação física) da academia.
 * HERDA de Pessoa: já tem codigo, nome, cpf e telefone.
 */
public class Instrutor extends Pessoa {

    private String cref; // registro do profissional de educação física

    public String getCref() {
        return cref;
    }

    public void setCref(String cref) {
        this.cref = cref;
    }

    // sobrescrita (@Override) do método da classe mãe -> POLIMORFISMO
    @Override
    public String getTipo() {
        return "Instrutor";
    }
}
