package Jogo;

import Labirinto.*;
import Structures.ArrayUnorderedList;

/**
 * Motor central do jogo Labirinto da Glória.
 * Esta classe é responsável por coordenar todo o fluxo do jogo, delegando
 * responsabilidades específicas a gestores especializados.
 *
 * Gestores utilizados:
 * <ul>
 *   <li>{@link TurnManager} - Gestão de turnos e fila circular de jogadores</li>
 *   <li>{@link EventManager} - Aplicação de eventos aleatórios nos corredores</li>
 *   <li>{@link ChallengeManager} - Resolução de desafios (enigmas e alavancas)</li>
 * </ul>
 *
 * Padrões de desenho aplicados:
 * <ul>
 *   <li>Single Responsibility Principle (SRP) - cada gestor tem uma responsabilidade</li>
 *   <li>Delegation Pattern - delega tarefas aos gestores especializados</li>
 * </ul>
 *
 * @author Grupo ED
 * @version 2.0
 */
public class GameEngine {

    /** O labirinto onde o jogo decorre */
    private Labirinto labirinto;

    /** Gestor responsável pelos turnos dos jogadores */
    private TurnManager turnManager;

    /** Gestor responsável pelos eventos aleatórios */
    private EventManager eventManager;

    /** Gestor responsável pelos desafios */
    private ChallengeManager challengeManager;

    /** Indica se o jogo terminou */
    private boolean jogoTerminado;

    /** O jogador vencedor, ou null se ainda não houver */
    private Jogador vencedor;

    /** Relatório da partida para exportação */
    private RelatorioPartida relatorio;

    /** Janela de estatísticas do jogo */
    private EstatisticasJogo estatisticas;

    /** Lista de todos os jogadores */
    private ArrayUnorderedList<Jogador> jogadores;

    /** Indica se o jogo está em modo automático (bots) */
    private boolean modoAutomatico;

    /** Delay entre turnos em modo automático (milissegundos) */
    private static final int DELAY_MODO_AUTOMATICO = 1500;

    /**
     * Construtor do motor de jogo.
     * Inicializa todos os componentes necessários para o funcionamento do jogo,
     * incluindo os gestores especializados e o relatório da partida.
     *
     * @param labirinto O labirinto onde o jogo irá decorrer
     * @param jogadores A lista de jogadores participantes
     */
    public GameEngine(Labirinto labirinto, ArrayUnorderedList<Jogador> jogadores) {
        this.labirinto = labirinto;
        this.jogadores = jogadores;
        this.jogoTerminado = false;
        this.vencedor = null;
        this.relatorio = new RelatorioPartida();

        // Detetar se está em modo automático (primeiro jogador é bot)
        this.modoAutomatico = jogadores.first() instanceof JogadorBot;

        // Inicializar os gestores especializados
        this.turnManager = new TurnManager(jogadores);
        this.eventManager = new EventManager(jogadores);
        this.challengeManager = new ChallengeManager();

        // Criar janela de estatísticas
        this.estatisticas = new EstatisticasJogo(jogadores);

        // Registar o início da partida no relatório
        relatorio.registrarInicio();
    }

    /**
     * Inicia o ciclo principal do jogo.
     * Este método executa continuamente até que um jogador atinja
     * o centro do labirinto e conquiste o tesouro.
     */
    public void iniciarJogo() {
        System.out.println("--- Jogo Iniciado: Labirinto da Glória ---");

        while (!jogoTerminado) {
            // Obter o próximo jogador da fila de turnos
            Jogador jogadorAtual = turnManager.obterProximoJogador();

            if (jogadorAtual == null) {
                System.out.println("⚠️ Erro: Não foi possível obter o próximo jogador.");
                break;
            }

            processarTurno(jogadorAtual);

            // Adicionar delay em modo automático para visualização
            if (modoAutomatico && !jogoTerminado) {
                try {
                    Thread.sleep(DELAY_MODO_AUTOMATICO);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        exibirResultadoFinal();
    }

    /**
     * Apresenta o resultado final do jogo e gera o relatório.
     * Mostra o vencedor e as estatísticas da partida.
     */
    private void exibirResultadoFinal() {
        if (vencedor != null) {
            System.out.println("\n╔══════════════════════════════════════════════════════╗");
            System.out.println("║                  🏆 VITÓRIA! 🏆                      ║");
            System.out.println("╠══════════════════════════════════════════════════════╣");
            System.out.println("║  🎉 " + String.format("%-49s", vencedor.getNome() + " conquistou o tesouro!") + "║");
            System.out.println("║  📊 " + String.format("%-49s", "Total de turnos: " + turnManager.getContadorTurnos()) + "║");
            System.out.println("╚══════════════════════════════════════════════════════╝");

            // Gerar o relatório final em formato JSON
            gerarRelatorioFinal();

            // Fechar a janela de estatísticas
            estatisticas.fechar();
        }
    }

    /**
     * Processa o turno de um jogador.
     * Executa a seguinte sequência:
     * <ol>
     *   <li>Verifica se o jogador está impedido de jogar</li>
     *   <li>Processa desafios pendentes na divisão atual</li>
     *   <li>Permite ao jogador escolher o seu movimento</li>
     *   <li>Aplica eventos aleatórios no corredor</li>
     *   <li>Move o jogador para a nova divisão</li>
     *   <li>Verifica a condição de vitória</li>
     *   <li>Processa jogadas extra, se existirem</li>
     * </ol>
     *
     * @param jogador O jogador cujo turno está a ser processado
     */
    private void processarTurno(Jogador jogador) {
        // Mostrar o cabeçalho do turno
        turnManager.exibirCabecalhoTurno(jogador);

        // Atualizar estatísticas e destacar jogador atual
        estatisticas.destacarJogadorAtual(jogador, jogadores);
        estatisticas.atualizarEstatisticas(jogadores, turnManager.getContadorTurnos());

        // Verificar se o jogador está impedido de jogar
        if (turnManager.processarImpedimento(jogador)) {
            return;
        }

        Divisao divisaoAtual = jogador.getPosicaoAtual();

        // Processar desafios pendentes na divisão atual
        if (!challengeManager.processarDesafio(jogador, divisaoAtual)) {
            return;
        }

        // Obter a escolha de movimento do jogador
        Divisao divisaoDestino = jogador.escolherMovimento(labirinto);

        if (divisaoDestino == null) {
            System.out.println(jogador.getNome() + " não conseguiu fazer uma escolha válida e permanece em " + divisaoAtual.getNome());
            return;
        }

        // Aplicar eventos aleatórios durante a travessia do corredor
        boolean movimentoCancelado = eventManager.aplicarEventoAleatorio(jogador, divisaoAtual, divisaoDestino);

        // Mover o jogador se o movimento não foi cancelado
        if (!movimentoCancelado) {
            jogador.moverPara(divisaoDestino);
            System.out.println("➡️  " + jogador.getNome() + " avança para: " + divisaoDestino.getNome());
        }

        // Atualizar estatísticas após movimento
        estatisticas.atualizarEstatisticas(jogadores, turnManager.getContadorTurnos());

        // Verificar se o jogador atingiu o centro (condição de vitória)
        if (jogador.getPosicaoAtual().getTipoEnum() == TipoDivisao.CENTRO) {
            jogoTerminado = true;
            vencedor = jogador;
            estatisticas.mostrarVitoria(vencedor);
            return;
        }

        // Processar jogadas extra - dar um novo turno completo ao jogador
        if (jogador.getJogadasExtra() > 0 && !jogoTerminado) {
            jogador.adicionarJogadasExtra(-1);
            System.out.println("\n🎯 " + jogador.getNome() + " usa a jogada extra!");
            processarTurno(jogador);
        }
    }

    /**
     * Gera o relatório final da partida em formato JSON.
     * O relatório inclui informações detalhadas sobre cada jogador,
     * incluindo o percurso realizado, obstáculos ultrapassados e efeitos aplicados.
     */
    private void gerarRelatorioFinal() {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║           📝 A GERAR RELATÓRIO DA PARTIDA            ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        // Registar o fim da partida
        relatorio.registrarFim(vencedor, turnManager.getContadorTurnos());

        // Adicionar os dados de cada jogador ao relatório
        java.util.Iterator<Jogador> it = turnManager.getTodosJogadores().iterator();
        while (it.hasNext()) {
            relatorio.adicionarJogador(it.next());
        }

        // Gerar o nome do ficheiro com a data e hora atuais
        java.time.LocalDateTime agora = java.time.LocalDateTime.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String nomeFicheiro = "relatório_partida_" + agora.format(formatter);

        // Gerar o ficheiro JSON
        boolean sucesso = relatorio.gerarArquivoJSON(nomeFicheiro);

        if (sucesso) {
            System.out.println("\n📊 RESUMO DO RELATÓRIO:");
            System.out.println("   - Vencedor: " + vencedor.getNome());
            System.out.println("   - Total de turnos: " + turnManager.getContadorTurnos());
            System.out.println("   - Jogadores registados: " + turnManager.getTodosJogadores().size());
            System.out.println("\n💾 O relatório detalhado foi guardado em formato JSON!");
        }
    }
}
