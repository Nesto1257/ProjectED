package Jogo;

import Labirinto.*;
import Structures.ArrayUnorderedList;

/**
 * Classe abstrata que representa um participante no jogo Labirinto da Glória.
 * Define o estado e comportamentos fundamentais que são comuns a todos os tipos
 * de jogadores, sejam humanos ou controlados por computador.
 *
 * Esta classe mantém informação sobre:
 * <ul>
 *   <li>Posição atual e histórico de movimentos</li>
 *   <li>Jogadas extra e impedimentos</li>
 *   <li>Obstáculos ultrapassados e efeitos aplicados</li>
 * </ul>
 *
 * @author Grupo ED
 * @version 1.0
 */
public abstract class Jogador {

    /** Nome do jogador */
    private String nome;

    /** Divisão onde o jogador se encontra atualmente */
    private Divisao posicaoAtual;

    /** Número de jogadas extra disponíveis */
    private int jogadasExtra;

    /** Número de turnos que o jogador está impedido de jogar */
    private int turnosImpedido;


    /** Histórico de divisões visitadas pelo jogador */
    private ArrayUnorderedList<Divisao> divisoesVisitadas;

    /** Lista de obstáculos ultrapassados (enigmas e alavancas) */
    private ArrayUnorderedList<String> obstaculosUltrapassados;

    /** Lista de efeitos de eventos aleatórios aplicados */
    private ArrayUnorderedList<String> efeitosAplicados;

    /** Contador total de movimentos realizados */
    private int totalMovimentos;

    /**
     * Construtor do jogador.
     * Inicializa um novo jogador com o nome e posição inicial especificados.
     *
     * @param nome O nome do jogador
     * @param pontoInicial A divisão onde o jogador começa o jogo
     */
    public Jogador(String nome, Divisao pontoInicial) {
        this.nome = nome;
        this.posicaoAtual = pontoInicial;
        this.jogadasExtra = 0;
        this.turnosImpedido = 0;

        // Inicializar as listas de rastreamento
        this.divisoesVisitadas = new ArrayUnorderedList<>();
        this.obstaculosUltrapassados = new ArrayUnorderedList<>();
        this.efeitosAplicados = new ArrayUnorderedList<>();
        this.totalMovimentos = 0;

        // Adicionar a posição inicial ao histórico
        this.divisoesVisitadas.addToRear(pontoInicial);
    }

    /**
     * Método abstrato que define o processo de decisão do movimento.
     * A implementação varia consoante o tipo de jogador (humano ou bot).
     *
     * @param labirinto O labirinto onde o jogo decorre
     * @return A divisão escolhida para o próximo movimento
     */
    public abstract Divisao escolherMovimento(Labirinto labirinto);

    /**
     * Move o jogador para uma nova divisão.
     * Atualiza a posição atual e o histórico de movimentos.
     *
     * @param novaPosicao A divisão de destino
     */
    public void moverPara(Divisao novaPosicao) {
        this.posicaoAtual = novaPosicao;
        this.divisoesVisitadas.addToRear(novaPosicao);
        this.totalMovimentos++;
    }

    /**
     * Regista que um obstáculo foi ultrapassado com sucesso.
     *
     * @param descricaoObstaculo Descrição do obstáculo (ex: "Enigma na Sala Norte")
     */
    public void registrarObstaculoUltrapassado(String descricaoObstaculo) {
        this.obstaculosUltrapassados.addToRear(descricaoObstaculo);
    }

    /**
     * Regista um efeito aplicado ao jogador através de um evento aleatório.
     *
     * @param descricaoEfeito Descrição do efeito (ex: "Ganhou jogada extra")
     */
    public void registrarEfeito(String descricaoEfeito) {
        this.efeitosAplicados.addToRear(descricaoEfeito);
    }

    /**
     * Obtém o nome do jogador.
     *
     * @return O nome do jogador
     */
    public String getNome() {
        return nome;
    }

    /**
     * Obtém a posição atual do jogador.
     *
     * @return A divisão onde o jogador se encontra
     */
    public Divisao getPosicaoAtual() {
        return posicaoAtual;
    }

    /**
     * Obtém o número de turnos que o jogador está impedido.
     *
     * @return Número de turnos de impedimento restantes
     */
    public int getTurnosImpedido() {
        return turnosImpedido;
    }

    /**
     * Define o número de turnos de impedimento.
     *
     * @param turnos O número de turnos de impedimento
     */
    public void setTurnosImpedido(int turnos) {
        this.turnosImpedido = turnos;
    }

    /**
     * Obtém o número de jogadas extra.
     *
     * @return Número de jogadas extra
     */
    public int getJogadasExtra() {
        return jogadasExtra;
    }

    /**
     * Adiciona ou remove jogadas extra.
     *
     * @param num Número de jogadas a adicionar (positivo) ou remover (negativo)
     */
    public void adicionarJogadasExtra(int num) {
        this.jogadasExtra += num;
    }


    /**
     * Obtém o histórico de divisões visitadas.
     *
     * @return Lista com todas as divisões visitadas
     */
    public ArrayUnorderedList<Divisao> getDivisoesVisitadas() {
        return divisoesVisitadas;
    }

    /**
     * Obtém a lista de obstáculos ultrapassados.
     *
     * @return Lista com descrições dos obstáculos ultrapassados
     */
    public ArrayUnorderedList<String> getObstaculosUltrapassados() {
        return obstaculosUltrapassados;
    }

    /**
     * Obtém a lista de efeitos aplicados.
     *
     * @return Lista com descrições dos efeitos aplicados
     */
    public ArrayUnorderedList<String> getEfeitosAplicados() {
        return efeitosAplicados;
    }

    /**
     * Obtém o número total de movimentos realizados.
     *
     * @return O total de movimentos
     */
    public int getTotalMovimentos() {
        return totalMovimentos;
    }
}