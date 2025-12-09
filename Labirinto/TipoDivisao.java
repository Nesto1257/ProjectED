package Labirinto;

/**
 * Enum que representa os tipos possíveis de divisão no labirinto.
 *
 * Benefícios OOP:
 * - Type safety: impossível usar valores inválidos
 * - Encapsulação: comportamentos específicos por tipo
 * - Extensibilidade: fácil adicionar novos tipos
 *
 * @author Grupo ED
 * @version 1.0
 */
public enum TipoDivisao {
    ENTRADA("Entrada", "🚪", false),
    CENTRO("Centro", "🏆", false),
    ALAVANCA("Alavanca", "🔧", true),
    ENIGMA("Enigma", "❓", true),
    SIMPLES("Simples", "🚶", false);

    private final String nome;
    private final String icone;
    private final boolean temDesafio;

    TipoDivisao(String nome, String icone, boolean temDesafio) {
        this.nome = nome;
        this.icone = icone;
        this.temDesafio = temDesafio;
    }

    public String getNome() {
        return nome;
    }

    public String getIcone() {
        return icone;
    }

    public boolean temDesafio() {
        return temDesafio;
    }

    /**
     * Converte uma string para o enum correspondente.
     * Mantém compatibilidade com código existente que usa strings.
     *
     * @param tipo String representando o tipo
     * @return TipoDivisao correspondente ou SIMPLES se não encontrado
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

    @Override
    public String toString() {
        return nome;
    }
}
