package br.com.modelo;

/**
 * MODEL - Classe mãe (superclasse).
 * Guarda os atributos que Aluno e Instrutor têm em comum.
 * Aluno e Instrutor herdam dela usando "extends" (HERANÇA).
 */
public class Pessoa {

    // atributos privados = ENCAPSULAMENTO (só acessa por get/set)
    private int codigo;
    private String nome;
    private String cpf;
    private String telefone;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // método que as classes filhas vão SOBRESCREVER (POLIMORFISMO)
    public String getTipo() {
        return "Pessoa";
    }

    // sobrescrita do toString(): é o texto que aparece no JComboBox
    @Override
    public String toString() {
        return codigo + " - " + nome;
    }
}
