// Pacote: Labirinto

package Labirinto;

/**
 * Classe auxiliar para mapear a estrutura do ficheiro JSON do Labirinto.
 * Esta classe serve como intermediária entre o ficheiro JSON e as estruturas
 * de dados próprias do projeto. Gson desserializa o JSON para esta estrutura,
 * que depois é convertida para objetos Divisao e ligações no Network.
 *
 * Usa arrays nativos porque a biblioteca Gson não consegue desserializar
 * diretamente para estruturas de dados customizadas (como ArrayUnorderedList).
 *
 * @author Grupo ED
 * @version 1.0
 */
public class LabirintoLayout {

    /**
     * Representa uma Divisão no formato JSON.
     * Esta classe interna mapeia os campos de uma divisão do ficheiro JSON.
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
     * Representa um Corredor (conexão entre divisões) no formato JSON.
     * Esta classe interna mapeia os campos de um corredor do ficheiro JSON.
     */
    public static class CorredorJson {
        /** ID da divisão de origem */
        public int origem;

        /** ID da divisão de destino */
        public int destino;

        /** Peso do corredor (influencia probabilidade de eventos aleatórios) */
        public double peso;
    }

    /** Array de divisões carregadas do JSON */
    public DivisaoJson[] divisoes;

    /** Array de corredores carregados do JSON */
    public CorredorJson[] corredores;
}