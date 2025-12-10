package Labirinto;

/**
 * Classe auxiliar para mapeamento do ficheiro JSON do labirinto.
 * Serve como estrutura intermédia entre o ficheiro JSON e as estruturas
 * de dados do projeto.
 *
 * A biblioteca Gson desserializa o ficheiro JSON para esta estrutura,
 * que depois é convertida para objetos Divisao e ligações no Network.
 *
 * Utiliza arrays nativos porque a biblioteca Gson não consegue
 * desserializar diretamente para estruturas de dados.
 *
 * @author Grupo ED
 * @version 1.0
 */
public class LabirintoLayout {

    /**
     * Representa uma divisão no formato JSON.
     * Esta classe mapeia os campos de uma divisão do ficheiro.
     */
    public static class DivisaoJson {

        /** Identificador único da divisão */
        public int id;

        /** Nome descritivo da divisão */
        public String nome;

        /** Tipo da divisão (Entrada, Centro, Enigma, Alavanca, Simples) */
        public String tipo;
    }

    /**
     * Representa um corredor (ligação entre divisões) no formato JSON.
     * Esta classe mapeia os campos de um corredor do ficheiro.
     */
    public static class CorredorJson {

        /** ID da divisão de origem */
        public int origem;

        /** ID da divisão de destino */
        public int destino;

        /** Peso do corredor (utilizado para cálculo de caminhos) */
        public double peso;
    }

    /** Array de divisões carregadas do ficheiro JSON */
    public DivisaoJson[] divisoes;

    /** Array de corredores carregados do ficheiro JSON */
    public CorredorJson[] corredores;
}