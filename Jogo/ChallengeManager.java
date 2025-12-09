package Jogo;

import Labirinto.Divisao;

/**
 * Gestor de desafios (enigmas e alavancas).
 * Responsável por validar e resolver desafios em divisões bloqueadas.
 *
 * @author Grupo ED
 * @version 2.0
 */
public class ChallengeManager {

    /**
     * Verifica se há um desafio pendente na divisão atual e tenta resolvê-lo.
     *
     * @param jogador O jogador que vai tentar resolver o desafio
     * @param divisaoAtual A divisão onde o jogador está
     * @return true se o desafio foi resolvido ou não existe, false se falhou
     */
    public boolean processarDesafio(Jogador jogador, Divisao divisaoAtual) {
        // Se não há desafio pendente, pode continuar
        if (!divisaoAtual.temDesafioPendente()) {
            return true;
        }

        // Exibir desafio
        System.out.println("\n⚠️  OBSTÁCULO BLOQUEADO! ⚠️");
        System.out.println(divisaoAtual.getDesafio().getDescricao());

        // Obter resposta do jogador
        String resposta = obterResposta(jogador);

        // Tentar resolver o desafio
        boolean sucesso = divisaoAtual.getDesafio().tentarResolucao(resposta);

        if (sucesso) {
            System.out.println("✅ CORRETO! O desafio foi resolvido!");

            // Registrar obstáculo ultrapassado
            String tipoObstaculo = determinarTipoObstaculo(divisaoAtual);
            jogador.registrarObstaculoUltrapassado(tipoObstaculo + " em " + divisaoAtual.getNome());

            return true;
        } else {
            System.out.println("❌ ERRADO! O bloqueio permanece. Tente novamente no próximo turno.");
            return false;
        }
    }

    /**
     * Obtém a resposta do jogador (humano ou bot).
     *
     * @param jogador O jogador que vai responder
     * @return A resposta fornecida
     */
    private String obterResposta(Jogador jogador) {
        if (jogador instanceof JogadorHumano) {
            System.out.print("\nSua resposta: ");
            return InputValidator.lerLinha();
        } else {
            // Bot tenta resposta aleatória (simplificado)
            String resposta = String.valueOf((int)(Math.random() * 2) + 1);
            System.out.println("Bot tentou: " + resposta);
            return resposta;
        }
    }

    /**
     * Determina o tipo de obstáculo baseado no tipo da divisão.
     *
     * @param divisao A divisão a verificar
     * @return String com o tipo de obstáculo
     */
    private String determinarTipoObstaculo(Divisao divisao) {
        if (divisao.getTipo().equals(Divisao.TIPO_ENIGMA)) {
            return "Enigma";
        } else if (divisao.getTipo().equals(Divisao.TIPO_ALAVANCA)) {
            return "Alavanca";
        } else {
            return "Obstáculo";
        }
    }
}

