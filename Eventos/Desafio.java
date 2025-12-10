package Eventos;

/**
 * Interface que define o contrato para todos os desafios do jogo.
 * Os desafios são obstáculos que bloqueiam certas divisões do labirinto
 * e que os jogadores devem resolver para prosseguir.
 *
 * Implementações:
 * <ul>
 *   <li>{@link DesafioEnigma} - Desafio baseado em perguntas</li>
 *   <li>{@link DesafioAlavanca} - Desafio de escolha de alavanca</li>
 * </ul>
 *
 * @author Grupo ED
 * @version 1.0
 */
public interface Desafio {

    /**
     * Tenta resolver o desafio com a resposta fornecida.
     *
     * @param input A resposta do jogador (escolha de alavanca ou resposta ao enigma)
     * @return true se a resposta está correcta e o desafio foi resolvido, false caso contrário
     */
    boolean tentarResolucao(String input);

    /**
     * Verifica se o desafio já foi completado.
     *
     * @return true se o desafio foi resolvido e a passagem está livre, false caso contrário
     */
    boolean estaCompleto();

    /**
     * Obtém a descrição do desafio a apresentar ao jogador.
     *
     * @return A descrição ou pergunta do desafio
     */
    String getDescricao();
}