package Jogo;

import Labirinto.*;
import Structures.ArrayUnorderedList;

/**
 * Classe base abstrata para todos os participantes do jogo.
 * Modela o estado e as ações fundamentais do jogador.
 */
public abstract class Jogador {
    private String nome;
    private Divisao posicaoAtual;
    private int jogadasExtra; // Ganhos/Perdas de jogadas
    private int turnosImpedido; // Ficar impedido de jogar
    private Divisao ultimaPosicaoEstavel; // Para gerir o recuo (recoil)
    private Divisao limiteRecuo; // Limite de recuo após troca de posição

    // Rastreamento de progresso
    private ArrayUnorderedList<Divisao> divisoesVisitadas; // Histórico de divisões visitadas
    private ArrayUnorderedList<String> obstaculosUltrapassados; // Enigmas e alavancas resolvidos
    private ArrayUnorderedList<String> efeitosAplicados; // Efeitos de eventos aleatórios recebidos
    private int totalMovimentos; // Contador de movimentos realizados

    /**
     * Construtor do Jogador.
     * Inicializa um novo jogador com nome e posição inicial.
     *
     * @param nome O nome do jogador
     * @param pontoInicial A divisão onde o jogador inicia o jogo
     */
    public Jogador(String nome, Divisao pontoInicial) {
        this.nome = nome;
        this.posicaoAtual = pontoInicial;
        this.jogadasExtra = 0;
        this.turnosImpedido = 0;
        this.ultimaPosicaoEstavel = pontoInicial;
        this.limiteRecuo = null; // Sem limite inicialmente

        // Inicializar rastreamento de progresso
        this.divisoesVisitadas = new ArrayUnorderedList<>();
        this.obstaculosUltrapassados = new ArrayUnorderedList<>();
        this.efeitosAplicados = new ArrayUnorderedList<>();
        this.totalMovimentos = 0;

        // Adicionar posição inicial ao histórico
        this.divisoesVisitadas.addToRear(pontoInicial);
    }

    /**
     * Define o processo de decisão do movimento, que varia consoante seja Humano ou Bot.
     * @param labirinto O labirinto atual.
     * @return A Divisao que o jogador escolheu para se mover.
     */
    public abstract Divisao escolherMovimento(Labirinto labirinto);

    /**
     * Atualiza a posição do jogador.
     */
    public void moverPara(Divisao novaPosicao) {
        this.posicaoAtual = novaPosicao;
        // A posição estável é atualizada após um movimento bem-sucedido
        this.ultimaPosicaoEstavel = novaPosicao;

        // Adicionar ao histórico de divisões visitadas
        this.divisoesVisitadas.addToRear(novaPosicao);
        this.totalMovimentos++;
    }

    /**
     * Registra que um obstáculo foi ultrapassado.
     * @param descricaoObstaculo Descrição do obstáculo (ex: "Enigma na Sala Norte")
     */
    public void registrarObstaculoUltrapassado(String descricaoObstaculo) {
        this.obstaculosUltrapassados.addToRear(descricaoObstaculo);
    }

    /**
     * Registra um efeito aplicado ao jogador.
     * @param descricaoEfeito Descrição do efeito (ex: "Ganhou jogada extra")
     */
    public void registrarEfeito(String descricaoEfeito) {
        this.efeitosAplicados.addToRear(descricaoEfeito);
    }

    // --- Getters e Setters ---

    public String getNome() {
        return nome;
    }

    public Divisao getPosicaoAtual() {
        return posicaoAtual;
    }

    public int getTurnosImpedido() {
        return turnosImpedido;
    }

    public void setTurnosImpedido(int turnos) {
        this.turnosImpedido = turnos;
    }

    public int getJogadasExtra() {
        return jogadasExtra;
    }

    public void adicionarJogadasExtra(int num) {
        this.jogadasExtra += num;
    }

    public Divisao getUltimaPosicaoEstavel() {
        return ultimaPosicaoEstavel;
    }

    public void setUltimaPosicaoEstavel(Divisao ultimaPosicaoEstavel) {
        this.ultimaPosicaoEstavel = ultimaPosicaoEstavel;
    }

    public Divisao getLimiteRecuo() {
        return limiteRecuo;
    }

    public void setLimiteRecuo(Divisao limiteRecuo) {
        this.limiteRecuo = limiteRecuo;
    }

    public ArrayUnorderedList<Divisao> getDivisoesVisitadas() {
        return divisoesVisitadas;
    }

    public ArrayUnorderedList<String> getObstaculosUltrapassados() {
        return obstaculosUltrapassados;
    }

    public ArrayUnorderedList<String> getEfeitosAplicados() {
        return efeitosAplicados;
    }

    public int getTotalMovimentos() {
        return totalMovimentos;
    }

    /**
     * Retorna um resumo do estado atual do jogador.
     * @return String com informações do jogador
     */
    public String getResumoEstado() {
        StringBuilder sb = new StringBuilder();
        sb.append("👤 ").append(nome).append("\n");
        sb.append("   📍 Posição: ").append(posicaoAtual.getNome()).append("\n");
        sb.append("   🚶 Movimentos: ").append(totalMovimentos).append("\n");
        sb.append("   ✅ Obstáculos ultrapassados: ").append(obstaculosUltrapassados.size()).append("\n");

        if (turnosImpedido > 0) {
            sb.append("   ⏸️  IMPEDIDO (").append(turnosImpedido).append(" turnos restantes)\n");
        }

        if (jogadasExtra > 0) {
            sb.append("   ⭐ Jogadas extra: ").append(jogadasExtra).append("\n");
        }

        if (!efeitosAplicados.isEmpty()) {
            sb.append("   🎲 Último efeito: ");
            // Pegar o último efeito
            java.util.Iterator<String> it = efeitosAplicados.iterator();
            String ultimoEfeito = null;
            while (it.hasNext()) {
                ultimoEfeito = it.next();
            }
            sb.append(ultimoEfeito).append("\n");
        }

        return sb.toString();
    }
}