package Eventos;

/**
 * Implementação de um desafio de alavanca.
 * O jogador deve escolher a alavanca correta para desbloquear a passagem.
 * Uma escolha correta desbloqueia permanentemente a divisão.
 *
 * @author Grupo ED
 * @version 1.0
 */
public class DesafioAlavanca implements Desafio {

    /** Indica se o desafio foi completado */
    private boolean completo;

    /** A resposta correta (1 ou 2) */
    private int respostaCorreta;

    /** Descrição do desafio */
    private String descricao;

    /**
     * Construtor do desafio de alavanca.
     *
     * @param respostaCorreta A alavanca correta (1 ou 2)
     * @param descricao A descrição do desafio
     */
    public DesafioAlavanca(int respostaCorreta, String descricao) {
        this.completo = false;
        this.respostaCorreta = respostaCorreta;
        this.descricao = descricao;
    }

    /**
     * Tenta resolver o desafio com a alavanca escolhida.
     *
     * @param input A escolha do jogador (1 ou 2)
     * @return true se a alavanca está correta, false caso contrário
     */
    @Override
    public boolean tentarResolucao(String input) {
        try {
            int tentativa = Integer.parseInt(input.trim());
            if (tentativa == respostaCorreta) {
                this.completo = true;
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Verifica se o desafio foi completado.
     *
     * @return true se a alavanca correta foi acionada
     */
    @Override
    public boolean estaCompleto() {
        return completo;
    }

    /**
     * Obtém a descrição do desafio.
     *
     * @return A descrição formatada do desafio
     */
    @Override
    public String getDescricao() {
        return "Desafio de Alavanca: " + descricao + "\nEscolha (Ex: 1 ou 2) para desbloquear a passagem.";
    }
}