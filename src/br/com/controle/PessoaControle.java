package br.com.controle;

import br.com.modelo.Pessoa;
import javax.swing.JOptionPane;

/**
 * CONTROLLER - classe mãe dos controles de Aluno e Instrutor.
 * O método validarPessoa recebe uma Pessoa, então funciona tanto
 * para Aluno quanto para Instrutor (POLIMORFISMO).
 */
public class PessoaControle {

    protected boolean validarPessoa(Pessoa p) {
        // p.getTipo() devolve "Aluno" ou "Instrutor", dependendo do objeto recebido
        if (p.getNome() == null || p.getNome().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o nome do " + p.getTipo() + "!");
            return false;
        }
        if (p.getCpf() == null || p.getCpf().trim().length() < 11) {
            JOptionPane.showMessageDialog(null, "Informe um CPF válido para o " + p.getTipo() + "!");
            return false;
        }
        return true;
    }
}
