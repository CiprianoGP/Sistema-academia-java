package br.com.modelo;

/**
 * MODEL - Modalidade de treino (Musculação, Pilates, Dança...).
 */
public class Modalidade {

    private int codigo;
    private String nome;
    private String descricao;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // texto que aparece no JComboBox da tela de Matrícula
    @Override
    public String toString() {
        return codigo + " - " + nome;
    }
}
