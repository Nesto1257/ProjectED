package Labirinto;

import Structures.*;
import Exceptions.*;

/**
 * Representa o labirinto do jogo como um grafo ponderado de divisões.
 * Utiliza a estrutura de dados Network (grafo ponderado não dirigido)
 * para modelar as ligações entre divisões.
 *
 * Estruturas de dados utilizadas:
 * <ul>
 *   <li>Network - Grafo ponderado para representar as ligações</li>
 *   <li>LinkedBinarySearchTree - Índice para busca eficiente por ID</li>
 *   <li>ArrayUnorderedList - Lista de adjacências para vizinhos</li>
 * </ul>
 *
 * @author Grupo ED
 * @version 1.0
 */
public class Labirinto {

    /** Estrutura Network que armazena as divisões e ligações */
    private Network<Divisao> estrutura;

    /** A divisão central onde está o tesouro */
    private Divisao pontoCentral;

    /** Lista de pontos de entrada do labirinto */
    private ArrayUnorderedList<Divisao> pontosEntrada;

    /** Índice BST para busca eficiente de divisões por ID */
    private LinkedBinarySearchTree<DivisaoIndexada> indiceDivisoes;

    /** Lista de adjacências indexada por ID da divisão */
    private ArrayUnorderedList<Divisao>[] listaAdjacencias;

    /** Capacidade inicial do array de adjacências */
    private static final int CAPACIDADE_INICIAL = 100;

    /**
     * Classe interna para indexação eficiente de divisões.
     * Permite busca O(log n) utilizando a Binary Search Tree.
     */
    private static class DivisaoIndexada implements Comparable<DivisaoIndexada> {
        int id;
        Divisao divisao;

        DivisaoIndexada(int id, Divisao div) {
            this.id = id;
            this.divisao = div;
        }

        @Override
        public int compareTo(DivisaoIndexada outra) {
            return Integer.compare(this.id, outra.id);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            DivisaoIndexada other = (DivisaoIndexada) obj;
            return id == other.id;
        }
    }

    /**
     * Construtor do labirinto.
     * Inicializa todas as estruturas de dados necessárias.
     */
    @SuppressWarnings("unchecked")
    public Labirinto() {
        this.estrutura = new Network<Divisao>();
        this.pontosEntrada = new ArrayUnorderedList<>();
        this.indiceDivisoes = new LinkedBinarySearchTree<>();

        // Inicializar a lista de adjacências
        this.listaAdjacencias = (ArrayUnorderedList<Divisao>[]) new ArrayUnorderedList[CAPACIDADE_INICIAL];
        for (int i = 0; i < CAPACIDADE_INICIAL; i++) {
            this.listaAdjacencias[i] = new ArrayUnorderedList<>();
        }
    }

    /**
     * Procura uma divisão pelo seu identificador.
     * Utiliza o índice BST para busca eficiente O(log n).
     *
     * @param id O identificador da divisão
     * @return A divisão correspondente, ou null se não existir
     */
    public Divisao getDivisaoByID(int id) {
        try {
            DivisaoIndexada chave = new DivisaoIndexada(id, null);
            DivisaoIndexada resultado = indiceDivisoes.find(chave);
            return resultado != null ? resultado.divisao : null;
        } catch (ElementNotFoundException e) {
            return null;
        } catch (Exception e) {
            // Fallback para busca linear
            for (int i = 0; i < estrutura.size(); i++) {
                try {
                    Divisao d = estrutura.getVertex(i);
                    if (d.getId() == id) {
                        return d;
                    }
                } catch (Exception ex) {
                    // Ignorar
                }
            }
        }
        return null;
    }

    /**
     * Adiciona uma nova divisão ao labirinto.
     * A divisão é adicionada ao Network e ao índice BST.
     * Se for uma divisão do tipo Centro, é definida como ponto central.
     * Se for uma divisão do tipo Entrada, é adicionada à lista de entradas.
     *
     * @param divisao A divisão a adicionar
     */
    public void adicionarDivisao(Divisao divisao) {
        estrutura.addVertex(divisao);

        // Adicionar ao índice BST para busca eficiente
        indiceDivisoes.addElement(new DivisaoIndexada(divisao.getId(), divisao));

        TipoDivisao tipo = divisao.getTipoEnum();
        if (tipo == TipoDivisao.CENTRO) {
            pontoCentral = divisao;
        } else if (tipo == TipoDivisao.ENTRADA) {
            pontosEntrada.addToRear(divisao);
        }
    }

    /**
     * Liga duas divisões através de um corredor.
     * Atualiza tanto o Network quanto a lista de adjacências.
     *
     * @param div1 A primeira divisão
     * @param div2 A segunda divisão
     * @param peso O peso da ligação (distância/dificuldade)
     */
    public void ligarDivisoes(Divisao div1, Divisao div2, double peso) {
        estrutura.addEdge(div1, div2, peso);

        // Atualizar lista de adjacências (bidirecional)
        int id1 = div1.getId();
        int id2 = div2.getId();

        expandirListaSeNecessario(Math.max(id1, id2));

        listaAdjacencias[id1].addToRear(div2);
        listaAdjacencias[id2].addToRear(div1);
    }

    /**
     * Expande o array de listas de adjacências se necessário.
     *
     * @param idNecessario O ID que precisa de ser acomodado
     */
    @SuppressWarnings("unchecked")
    private void expandirListaSeNecessario(int idNecessario) {
        if (idNecessario >= listaAdjacencias.length) {
            int novaCapacidade = Math.max(listaAdjacencias.length * 2, idNecessario + 10);
            ArrayUnorderedList<Divisao>[] novaLista = (ArrayUnorderedList<Divisao>[]) new ArrayUnorderedList[novaCapacidade];

            System.arraycopy(listaAdjacencias, 0, novaLista, 0, listaAdjacencias.length);

            for (int i = listaAdjacencias.length; i < novaCapacidade; i++) {
                novaLista[i] = new ArrayUnorderedList<>();
            }

            listaAdjacencias = novaLista;
        }
    }

    /**
     * Obtém a lista de divisões vizinhas de uma divisão.
     * Utiliza a lista de adjacências para busca eficiente O(1).
     *
     * @param divisao A divisão cujos vizinhos se pretende obter
     * @return A lista de divisões adjacentes
     */
    public ArrayUnorderedList<Divisao> getVizinhos(Divisao divisao) {
        int id = divisao.getId();

        if (id >= 0 && id < listaAdjacencias.length) {
            return listaAdjacencias[id];
        }

        return new ArrayUnorderedList<>();
    }

    /**
     * Obtém a divisão central (tesouro) do labirinto.
     *
     * @return A divisão central, ou null se não definida
     */
    public Divisao getPontoCentral() {
        return pontoCentral;
    }

    /**
     * Obtém a lista de pontos de entrada do labirinto.
     *
     * @return A lista de divisões de entrada
     */
    public ArrayUnorderedList<Divisao> getPontosEntrada() {
        return pontosEntrada;
    }

    /**
     * Devolve uma representação textual com as estatísticas do labirinto.
     *
     * @return As estatísticas do labirinto formatadas
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔════════════════════════════════════════════════════════════╗\n");
        sb.append("║              LABIRINTO DA GLÓRIA                           ║\n");
        sb.append("╚════════════════════════════════════════════════════════════╝\n\n");

        // Contar corredores
        int numConexoes = 0;
        for (int i = 0; i < estrutura.size(); i++) {
            try {
                for (int j = i + 1; j < estrutura.size(); j++) {
                    if (estrutura.isEdge(i, j)) {
                        numConexoes++;
                    }
                }
            } catch (Exception e) {
                // Ignorar
            }
        }

        // Mostrar objetivo
        sb.append("🏆 OBJETIVO: ");
        if (pontoCentral != null) {
            sb.append(pontoCentral.getNome()).append("\n");
        } else {
            sb.append("(não definido)\n");
        }

        // Estatísticas
        sb.append("\n📊 ESTATÍSTICAS:\n");
        sb.append("─────────────────────────────────────────────────────────────\n");
        sb.append(String.format("  Total de Divisões: %d\n", estrutura.size()));
        sb.append(String.format("  Total de Corredores: %d\n", numConexoes));
        sb.append(String.format("  Pontos de Entrada: %d\n", pontosEntrada.size()));

        sb.append("\n═════════════════════════════════════════════════════════════\n");

        return sb.toString();
    }

    /**
     * Obtém o número total de divisões no labirinto.
     *
     * @return O número de divisões
     */
    public int getTamanho() {
        return estrutura.size();
    }

    /**
     * Obtém uma divisão pelo seu índice no Network.
     *
     * @param indice O índice da divisão
     * @return A divisão correspondente, ou null se o índice for inválido
     */
    public Divisao getDivisaoPorIndice(int indice) {
        try {
            return estrutura.getVertex(indice);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Calcula o caminho mais curto entre duas divisões utilizando Dijkstra.
     * Este método é utilizado pelos bots inteligentes para encontrar
     * a rota mais rápida até ao tesouro.
     *
     * @param origem A divisão de origem
     * @param destino A divisão de destino
     * @return Iterador com o caminho completo, ou null se não existir caminho
     */
    public java.util.Iterator<Divisao> getCaminhoMaisCurto(Divisao origem, Divisao destino) {
        try {
            return estrutura.iteratorShortestPath(origem, destino);
        } catch (Exception e) {
            System.out.println("⚠️ Erro ao calcular caminho mais curto: " + e.getMessage());
            return null;
        }
    }
}