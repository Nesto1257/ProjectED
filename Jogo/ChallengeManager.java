package Jogo;

import Labirinto.Divisao;
import Labirinto.TipoDivisao;

/**
 * Gestor de desafios do jogo Labirinto da Glória.
 * Responsável por processar e validar as resoluções de enigmas e alavancas
 * que bloqueiam certas divisões do labirinto.
 *
 * Esta classe gere a interação entre o jogador e os obstáculos,
 * verificando se as respostas estão corretas e registando os
 * obstáculos ultrapassados.
 *
 * @author Grupo ED
 * @version 1.0
 */
public class ChallengeManager {

    /**
     * Processa um desafio pendente na divisão atual.
     * Se existir um desafio por resolver, apresenta-o ao jogador
     * e verifica se a resposta está correta.
     *
     * @param jogador O jogador que vai tentar resolver o desafio
     * @param divisaoAtual A divisão onde o jogador se encontra
     * @return true se o desafio foi resolvido ou não existe, false se falhou
     */
    public boolean processarDesafio(Jogador jogador, Divisao divisaoAtual) {
        // Verificar se existe desafio pendente
        if (!divisaoAtual.temDesafioPendente()) {
            return true;
        }

        // Apresentar o desafio ao jogador
        System.out.println("\n⚠️  OBSTÁCULO BLOQUEADO! ⚠️");
        System.out.println(divisaoAtual.getDesafio().getDescricao());

        // Obter a resposta do jogador
        String resposta = obterResposta(jogador, divisaoAtual);

        // Validar a resposta
        boolean sucesso = divisaoAtual.getDesafio().tentarResolucao(resposta);

        if (sucesso) {
            System.out.println("✅ CORRETO! O desafio foi resolvido!");

            // Registar o obstáculo ultrapassado
            String tipoObstaculo = determinarTipoObstaculo(divisaoAtual);
            jogador.registrarObstaculoUltrapassado(tipoObstaculo + " em " + divisaoAtual.getNome());

            return true;
        } else {
            System.out.println("❌ ERRADO! O bloqueio permanece. Tente novamente no próximo turno.");
            return false;
        }
    }

    /**
     * Obtém a resposta do jogador para o desafio.
     * Jogadores humanos inserem a resposta via consola.
     * Bots geram uma resposta aleatória baseada no tipo de desafio.
     *
     * @param jogador O jogador que vai responder
     * @param divisao A divisão com o desafio (para determinar o tipo)
     * @return A resposta fornecida
     */
    private String obterResposta(Jogador jogador, Divisao divisao) {
        if (jogador instanceof JogadorHumano) {
            System.out.print("\nA sua resposta: ");
            return InputValidator.lerLinha();
        } else {
            // Determinar número de opções baseado no tipo de desafio
            int maxOpcoes;
            if (divisao.getTipoEnum() == TipoDivisao.ALAVANCA) {
                maxOpcoes = 2; // Alavanca: 1 ou 2
            } else {
                maxOpcoes = 3; // Enigma: 1, 2 ou 3
            }

            String resposta = String.valueOf((int)(Math.random() * maxOpcoes) + 1);
            System.out.println("🤖 Bot tentou: " + resposta);
            return resposta;
        }
    }

    /**
     * Determina o tipo de obstáculo com base no tipo da divisão.
     *
     * @param divisao A divisão a analisar
     * @return Uma string descritiva do tipo de obstáculo
     */
    private String determinarTipoObstaculo(Divisao divisao) {
        TipoDivisao tipo = divisao.getTipoEnum();

        if (tipo == TipoDivisao.ENIGMA) {
            return "Enigma";
        } else if (tipo == TipoDivisao.ALAVANCA) {
            return "Alavanca";
        } else {
            return "Obstáculo";
        }
    }
}

