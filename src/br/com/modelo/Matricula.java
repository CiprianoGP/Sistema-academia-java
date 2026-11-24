package br.com.modelo;

/**
 * MODEL - Matrícula = ROTINA PRINCIPAL do sistema.
 * Uma matrícula RELACIONA objetos de outras classes:
 * Aluno + Plano + Modalidade + Instrutor (ASSOCIAÇÃO entre objetos).
 * No banco isso vira as chaves estrangeiras da tabela matricula.
 */
public class Matricula {

    private int codigo;
    private Aluno aluno;
    private Plano plano;
    private Modalidade modalidade;
    private Instrutor instrutor;
    private String dataInicio;      // formato dd/mm/aaaa
    private String dataVencimento;  // calculada pelo controle
    private double valor;
    private String situacao;        // Ativa, Trancada ou Cancelada

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(Instrutor instrutor) {
        this.instrutor = instrutor;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
