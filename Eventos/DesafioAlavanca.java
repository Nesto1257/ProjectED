// Pacote: Eventos

package Eventos;

/**
 * Implementa o Desafio da Alavanca.
 * Uma escolha correta desbloqueia permanentemente a Divisao.
 */
public class DesafioAlavanca implements Desafio {
    private boolean completo;
    private int respostaCorreta; // Ex: 1 ou 2 (alavanca A ou B)
    private String descricao;

    public DesafioAlavanca(int respostaCorreta, String descricao) {
        this.completo = false;
        this.respostaCorreta = respostaCorreta;
        this.descricao = descricao;
    }

    @Override
    public boolean tentarResolucao(String input) {
        try {
            int tentativa = Integer.parseInt(input.trim());
            if (tentativa == respostaCorreta) {
                this.completo = true;
                return true;
            } else {
                return false; // Escolha errada: mantém o bloqueio
            }
        } catch (NumberFormatException e) {
            return false; // Input inválido
        }
    }

    @Override
    public boolean estaCompleto() {
        return completo;
    }

    @Override
    public String getDescricao() {
        return "Desafio de Alavanca: " + descricao + "\nEscolha (Ex: 1 ou 2) para desbloquear a passagem.";
    }
}