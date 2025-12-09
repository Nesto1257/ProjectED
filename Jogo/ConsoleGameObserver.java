package Jogo;

import Labirinto.Divisao;

/**
 * Implementação padrão do GameObserver para logging no console.
 *
 * Demonstra o uso do padrão Observer para separar a lógica de
 * apresentação da lógica do jogo.
 *
 * @author Grupo ED
 * @version 1.0
 */
public class ConsoleGameObserver implements GameObserver {

    private boolean verbose;

    /**
     * Construtor do observer de console.
     *
     * @param verbose Se true, mostra todas as mensagens; se false, apenas as importantes
     */
    public ConsoleGameObserver(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * Construtor padrão (modo não-verbose).
     */
    public ConsoleGameObserver() {
        this(false);
    }

    @Override
    public void onTurnoIniciado(Jogador jogador, int numeroTurno) {
        if (verbose) {
            System.out.println("[LOG] Turno " + numeroTurno + " iniciado para " + jogador.getNome());
        }
    }

    @Override
    public void onMovimento(Jogador jogador, Divisao origem, Divisao destino) {
        if (verbose) {
            System.out.println("[LOG] " + jogador.getNome() + ": " +
                origem.getNome() + " → " + destino.getNome());
        }
    }

    @Override
    public void onDesafioResolvido(Jogador jogador, String tipoDesafio, boolean sucesso) {
        String resultado = sucesso ? "✅ SUCESSO" : "❌ FALHOU";
        if (verbose) {
            System.out.println("[LOG] " + jogador.getNome() + " - " + tipoDesafio + ": " + resultado);
        }
    }

    @Override
    public void onEventoAleatorio(Jogador jogador, String descricaoEvento) {
        if (verbose) {
            System.out.println("[LOG] Evento para " + jogador.getNome() + ": " + descricaoEvento);
        }
    }

    @Override
    public void onJogoTerminado(Jogador vencedor, int totalTurnos) {
        System.out.println("\n[LOG] ══════════════════════════════════════");
        System.out.println("[LOG] JOGO TERMINADO");
        if (vencedor != null) {
            System.out.println("[LOG] Vencedor: " + vencedor.getNome());
        }
        System.out.println("[LOG] Total de turnos: " + totalTurnos);
        System.out.println("[LOG] ══════════════════════════════════════");
    }
}

