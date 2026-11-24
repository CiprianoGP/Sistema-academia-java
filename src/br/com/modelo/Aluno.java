package br.com.modelo;

/**
 * MODEL - Aluno da academia.
 * HERDA de Pessoa: já tem codigo, nome, cpf e telefone.
 */
public class Aluno extends Pessoa {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // sobrescrita (@Override) do método da classe mãe -> POLIMORFISMO
    @Override
    public String getTipo() {
        return "Aluno";
    }
}
