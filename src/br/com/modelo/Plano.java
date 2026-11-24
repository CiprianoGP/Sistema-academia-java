package br.com.modelo;

/**
 * MODEL - Plano da academia (Mensal, Trimestral, Semestral, Anual...).
 */
public class Plano {

    private int codigo;
    private String descricao;
    private int duracaoMeses;
    private double valor;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getDuracaoMeses() {
        return duracaoMeses;
    }

    public void setDuracaoMeses(int duracaoMeses) {
        this.duracaoMeses = duracaoMeses;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    // texto que aparece no JComboBox da tela de Matrícula
    @Override
    public String toString() {
        return codigo + " - " + descricao + " (R$ " + String.format("%.2f", valor) + ")";
    }
}
