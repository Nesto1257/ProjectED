package Labirinto;

/**
 * Enumeração que define os tipos possíveis de divisões no labirinto.
 * Cada tipo representa uma função específica dentro do jogo.
 * Tipos disponíveis:
 * <ul>
 *   <li>ENTRADA - Pontos de início dos jogadores</li>
 *   <li>CENTRO - Localização do tesouro (objetivo)</li>
 *   <li>ALAVANCA - Divisão com desafio de alavanca</li>
 *   <li>ENIGMA - Divisão com desafio de enigma</li>
 *   <li>SIMPLES - Divisão sem obstáculos</li>
 * </ul>
 *
 * @author Grupo ED
 * @version 1.0
 */
public enum TipoDivisao {

    /** Ponto de entrada para os jogadores */
    ENTRADA("Entrada"),

    /** Centro do labirinto onde está o tesouro */
    CENTRO("Centro"),

    /** Divisão com desafio de alavanca */
    ALAVANCA("Alavanca"),

    /** Divisão com desafio de enigma */
    ENIGMA("Enigma"),

    /** Divisão simples sem obstáculos */
    SIMPLES("Simples");

    /** Nome legível do tipo */
    private final String nome;

    /**
     * Construtor do enum.
     *
     * @param nome O nome legível do tipo
     */
    TipoDivisao(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o nome legível do tipo.
     *
     * @return O nome do tipo
     */
    public String getNome() {
        return nome;
    }

    /**
     * Converte uma String para o enum correspondente.
     * Útil para manter compatibilidade com o carregamento de ficheiros JSON.
     *
     * @param tipo A String representando o tipo
     * @return O TipoDivisao correspondente, ou SIMPLES se não encontrado
     */
    public static TipoDivisao fromString(String tipo) {
        if (tipo == null) return SIMPLES;

        switch (tipo.toLowerCase()) {
            case "entrada":
                return ENTRADA;
            case "centro":
                return CENTRO;
            case "alavanca":
                return ALAVANCA;
            case "enigma":
                return ENIGMA;
            default:
                return SIMPLES;
        }
    }

    /**
     * Devolve a representação textual do tipo.
     *
     * @return O nome do tipo
     */
    @Override
    public String toString() {
        return nome;
    }
}
