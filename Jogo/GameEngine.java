// Pacote: Jogo

package Jogo;

import Labirinto.*;
import Structures.ArrayUnorderedList;

/**
 * Motor central do jogo, responsável por coordenar o fluxo principal.
 * Esta classe coordena todo o jogo do Labirinto da Glória através de gestores especializados:
 * <ul>
 * <li>TurnManager: Gestão de turnos e fila de jogadores</li>
 * <li>EventManager: Aplicação de eventos aleatórios</li>
 * <li>ChallengeManager: Resolução de desafios (enigmas e alavancas)</li>
 * </ul>
 *
 * Princípios aplicados:
 * - Single Responsibility Principle (SRP)
 * - Separation of Concerns
 * - Delegation Pattern
 *
 * @author Grupo ED
 * @version 2.0 - Refatorado para melhor coesão
 */
public class GameEngine {
    private Labirinto labirinto;
    private TurnManager turnManager;
    private EventManager eventManager;
    private ChallengeManager challengeManager;
    private boolean jogoTerminado;
    private Jogador vencedor;
    private RelatorioPartida relatorio;
    private EstadoJogo estadoAtual;
    private ArrayUnorderedList<GameObserver> observers;

    /**
     * Construtor do GameEngine.
     * Inicializa o motor com o labirinto e lista de jogadores.
     * Cria os gestores especializados para turnos, eventos e desafios.
     *
     * @param labirinto O labirinto onde o jogo decorre
     * @param jogadores Lista de jogadores que vão participar no jogo
     */
    public GameEngine(Labirinto labirinto, ArrayUnorderedList<Jogador> jogadores) {
        this.labirinto = labirinto;
        this.jogoTerminado = false;
        this.vencedor = null;
        this.relatorio = new RelatorioPartida();
        this.estadoAtual = EstadoJogo.NAO_INICIADO;
        this.observers = new ArrayUnorderedList<>();

        // Inicializar gestores especializados
        this.turnManager = new TurnManager(jogadores);
        this.eventManager = new EventManager(jogadores);
        this.challengeManager = new ChallengeManager();

        // Registrar início da partida
        relatorio.registrarInicio();
    }

    // ===== PADRÃO OBSERVER =====

    /**
     * Adiciona um observer para receber notificações de eventos do jogo.
     *
     * @param observer O observer a adicionar
     */
    public void addObserver(GameObserver observer) {
        if (observer != null) {
            observers.addToRear(observer);
        }
    }

    /**
     * Remove um observer da lista.
     *
     * @param observer O observer a remover
     */
    public void removeObserver(GameObserver observer) {
        try {
            observers.remove(observer);
        } catch (Exception e) {
            // Observer não encontrado, ignorar
        }
    }

    /**
     * Notifica todos os observers sobre o início de um turno.
     */
    private void notifyTurnoIniciado(Jogador jogador) {
        java.util.Iterator<GameObserver> it = observers.iterator();
        while (it.hasNext()) {
            it.next().onTurnoIniciado(jogador, turnManager.getContadorTurnos());
        }
    }

    /**
     * Notifica todos os observers sobre um movimento.
     */
    private void notifyMovimento(Jogador jogador, Divisao origem, Divisao destino) {
        java.util.Iterator<GameObserver> it = observers.iterator();
        while (it.hasNext()) {
            it.next().onMovimento(jogador, origem, destino);
        }
    }

    /**
     * Notifica todos os observers sobre o fim do jogo.
     */
    private void notifyJogoTerminado() {
        java.util.Iterator<GameObserver> it = observers.iterator();
        while (it.hasNext()) {
            it.next().onJogoTerminado(vencedor, turnManager.getContadorTurnos());
        }
    }

    // ===== ESTADO DO JOGO =====

    /**
     * Obtém o estado atual do jogo.
     *
     * @return EstadoJogo atual
     */
    public EstadoJogo getEstadoAtual() {
        return estadoAtual;
    }

    /**
     * Inicia o loop principal do jogo.
     */
    public void iniciarJogo() {
        estadoAtual = EstadoJogo.EM_CURSO;
        System.out.println("--- Jogo Iniciado: Labirinto da Glória ---");

        while (!jogoTerminado) {
            // Obter próximo jogador
            Jogador jogadorAtual = turnManager.obterProximoJogador();

            if (jogadorAtual == null) {
                System.out.println("⚠️ Erro: Não foi possível obter o próximo jogador.");
                break;
            }

            processarTurno(jogadorAtual);
        }

        exibirResultadoFinal();
    }

    /**
     * Exibe o resultado final e gera relatório.
     */
    private void exibirResultadoFinal() {
        if (vencedor != null) {
            System.out.println("\n╔══════════════════════════════════════════════════════╗");
            System.out.println("║                🏆 VITÓRIA! 🏆                       ║");
            System.out.println("╚══════════════════════════════════════════════════════╝");
            System.out.println("\n🎉 " + vencedor.getNome() + " alcançou o centro e conquistou o tesouro!");
            System.out.println("🏆 Parabéns pela vitória épica!");
            System.out.println("📊 Total de turnos jogados: " + turnManager.getContadorTurnos());

            // Gerar relatório final em JSON
            gerarRelatorioFinal();
        }
    }

    /**
     * Processa o turno de um jogador.
     * Delega responsabilidades aos gestores especializados.
     *
     * @param jogador O jogador do turno atual
     */
    private void processarTurno(Jogador jogador) {
        // Notificar observers do início do turno
        notifyTurnoIniciado(jogador);

        // Exibir cabeçalho do turno
        turnManager.exibirCabecalhoTurno(jogador);

        // 1. Verificar impedimentos
        if (turnManager.processarImpedimento(jogador)) {
            return; // Jogador impedido, pula o turno
        }

        Divisao divisaoAtual = jogador.getPosicaoAtual();

        // 2. Processar desafios pendentes
        if (!challengeManager.processarDesafio(jogador, divisaoAtual)) {
            return; // Desafio falhou, termina o turno
        }

        // 3. Escolha do movimento
        Divisao divisaoDestino = jogador.escolherMovimento(labirinto);

        if (divisaoDestino == null) {
            System.out.println(jogador.getNome() + " não conseguiu fazer uma escolha válida e permanece em " + divisaoAtual.getNome());
            return;
        }

        // 4. Aplicar eventos aleatórios
        boolean movimentoCancelado = eventManager.aplicarEventoAleatorio(jogador, divisaoAtual, divisaoDestino);

        if (movimentoCancelado) {
            System.out.println("⚠️  O movimento planejado foi cancelado pelo evento!");
        }

        // 5. Mover o jogador (apenas se não foi cancelado)
        if (!movimentoCancelado) {
            jogador.moverPara(divisaoDestino);
            System.out.println("➡️  " + jogador.getNome() + " avança para: " + divisaoDestino.getNome());

            // Notificar observers do movimento
            notifyMovimento(jogador, divisaoAtual, divisaoDestino);
        }

        // 6. Verificar condição de vitória
        if (jogador.getPosicaoAtual().getTipo().equals(Divisao.TIPO_CENTRO)) {
            jogoTerminado = true;
            vencedor = jogador;
            estadoAtual = EstadoJogo.TERMINADO;

            // Notificar observers do fim do jogo
            notifyJogoTerminado();
        }

        // 7. Processar jogadas extra
        turnManager.adicionarJogadaExtra(jogador);
    }




    /**
     * Gera o relatório final da partida em formato JSON.
     * Inclui o percurso completo de cada jogador, obstáculos enfrentados,
     * enigmas resolvidos e efeitos aplicados.
     */
    private void gerarRelatorioFinal() {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║           📝 GERANDO RELATÓRIO DA PARTIDA            ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        // Registrar fim da partida
        relatorio.registrarFim(vencedor, turnManager.getContadorTurnos());

        // Adicionar dados de cada jogador
        java.util.Iterator<Jogador> it = turnManager.getTodosJogadores().iterator();
        while (it.hasNext()) {
            relatorio.adicionarJogador(it.next());
        }

        // Gerar nome do arquivo com timestamp
        java.time.LocalDateTime agora = java.time.LocalDateTime.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String nomeArquivo = "relatorio_partida_" + agora.format(formatter);

        // Gerar arquivo JSON
        boolean sucesso = relatorio.gerarArquivoJSON(nomeArquivo);

        if (sucesso) {
            System.out.println("\n📊 RESUMO DO RELATÓRIO:");
            System.out.println("   - Vencedor: " + vencedor.getNome());
            System.out.println("   - Total de turnos: " + turnManager.getContadorTurnos());
            System.out.println("   - Jogadores registrados: " + turnManager.getTodosJogadores().size());
            System.out.println("\n💾 O relatório detalhado foi salvo em formato JSON!");
        }
    }
}
