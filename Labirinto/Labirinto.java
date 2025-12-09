// Pacote: Labirinto

package Labirinto;

import Structures.*;
import Exceptions.*;

/**
 * Representa o Labirinto da Glória como um grafo (Network) de Divisões.
 * Esta classe modela a estrutura completa do labirinto, incluindo:
 * <ul>
 * <li>Divisões (vértices): salas, corredores, entradas e o tesouro</li>
 * <li>Corredores (arestas): conexões entre divisões com pesos</li>
 * <li>Pontos de entrada: locais onde os jogadores podem começar</li>
 * <li>Ponto central: sala do tesouro (objetivo do jogo)</li>
 * </ul>
 *
 * O labirinto usa a estrutura de dados Network (grafo ponderado não-dirigido)
 * para representar as conexões entre divisões e permitir navegação.
 *
 * @author Grupo ED
 * @version 1.0
 */
public class Labirinto {
    private Network<Divisao> estrutura;
    private Divisao pontoCentral;
    private ArrayUnorderedList<Divisao> pontosEntrada; // Ou a sua ArrayUnorderedList
    private LinkedBinarySearchTree<DivisaoIndexada> indiceDivisoes; // ✅ Índice BST para busca O(log n)
    private ArrayUnorderedList<Divisao>[] listaAdjacencias; // ✅ Lista de adjacências para busca O(1) de vizinhos
    private static final int CAPACIDADE_INICIAL = 100; // Capacidade inicial do array de adjacências

    /**
     * Classe wrapper interna para indexação eficiente de divisões por ID.
     * Permite busca O(log n) em vez de O(n) usando Binary Search Tree.
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

    @SuppressWarnings("unchecked")
    public Labirinto() {
        // A rede deve ser inicializada com as Divisoes como vértices
        this.estrutura = new Network<Divisao>();
        this.pontosEntrada = new ArrayUnorderedList<>();
        this.indiceDivisoes = new LinkedBinarySearchTree<>(); // ✅ Inicializar índice BST

        // ✅ Inicializar lista de adjacências para busca rápida de vizinhos
        this.listaAdjacencias = (ArrayUnorderedList<Divisao>[]) new ArrayUnorderedList[CAPACIDADE_INICIAL];
        for (int i = 0; i < CAPACIDADE_INICIAL; i++) {
            this.listaAdjacencias[i] = new ArrayUnorderedList<>();
        }
    }

    /**
     * Busca uma divisão pelo seu ID usando índice BST.
     * Complexidade: O(log n) em vez de O(n).
     *
     * @param id O ID da divisão a ser encontrada
     * @return A divisão correspondente ou null se não encontrada
     */
    public Divisao getDivisaoByID(int id) {
        // ✅ Busca O(log n) usando BST como índice
        try {
            DivisaoIndexada chave = new DivisaoIndexada(id, null);
            DivisaoIndexada resultado = indiceDivisoes.find(chave);
            return resultado != null ? resultado.divisao : null;
        } catch (ElementNotFoundException e) {
            // Elemento não encontrado no índice
            return null;
        } catch (Exception e) {
            // Em caso de outro erro, fallback para busca linear
            System.err.println("Erro ao buscar no índice BST, usando fallback: " + e.getMessage());

            // Fallback: busca linear (apenas se índice falhar)
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
     * A divisão é adicionada ao Network e ao índice BST para busca eficiente.
     */
    public void adicionarDivisao(Divisao divisao) {
        estrutura.addVertex(divisao);

        // ✅ Adicionar ao índice BST - O(log n)
        indiceDivisoes.addElement(new DivisaoIndexada(divisao.getId(), divisao));

        if (divisao.getTipo().equals(Divisao.TIPO_CENTRO)) {
            pontoCentral = divisao;
        } else if (divisao.getTipo().equals(Divisao.TIPO_ENTRADA)) {
            pontosEntrada.addToRear(divisao);
        }
    }

    /**
     * Liga duas divisões através de um corredor com um peso específico.
     * O peso será usado para determinar a chance de EventosAleatorios.
     * Atualiza tanto o Network quanto a lista de adjacências para busca eficiente.
     */
    public void ligarDivisoes(Divisao div1, Divisao div2, double peso) {
        // O Network já trata as ligações simétricas na addEdge
        estrutura.addEdge(div1, div2, peso);

        // ✅ Atualizar lista de adjacências (grafo não-dirigido, portanto bidirecional)
        int id1 = div1.getId();
        int id2 = div2.getId();

        // Expandir array se necessário
        expandirListaSeNecessario(Math.max(id1, id2));

        // Adicionar às listas de adjacências (bidirecional)
        listaAdjacencias[id1].addToRear(div2);
        listaAdjacencias[id2].addToRear(div1);
    }

    /**
     * Expande o array de listas de adjacências se o ID for maior que a capacidade atual.
     * @param idNecessario O ID que precisa ser acomodado
     */
    @SuppressWarnings("unchecked")
    private void expandirListaSeNecessario(int idNecessario) {
        if (idNecessario >= listaAdjacencias.length) {
            int novaCapacidade = Math.max(listaAdjacencias.length * 2, idNecessario + 10);
            ArrayUnorderedList<Divisao>[] novaLista = (ArrayUnorderedList<Divisao>[]) new ArrayUnorderedList[novaCapacidade];

            // Copiar listas existentes
            System.arraycopy(listaAdjacencias, 0, novaLista, 0, listaAdjacencias.length);

            // Inicializar novas posições
            for (int i = listaAdjacencias.length; i < novaCapacidade; i++) {
                novaLista[i] = new ArrayUnorderedList<>();
            }

            listaAdjacencias = novaLista;
        }
    }

    /**
     * Retorna uma lista com os vizinhos (Divisoes adjacentes) de uma Divisao.
     * Complexidade: O(1) para acessar a lista + O(grau) para iterar vizinhos.
     * Muito mais eficiente que O(n) anterior.
     *
     * @param divisao A divisão cujos vizinhos devem ser retornados
     * @return Lista de divisões adjacentes
     */
    public ArrayUnorderedList<Divisao> getVizinhos(Divisao divisao) {
        // ✅ Busca O(1) usando lista de adjacências indexada por ID
        int id = divisao.getId();

        // Verificar se o ID está dentro dos limites
        if (id >= 0 && id < listaAdjacencias.length) {
            return listaAdjacencias[id];
        }

        // Fallback: se por algum motivo o ID estiver fora dos limites, retornar lista vazia
        System.err.println("Aviso: ID " + id + " fora dos limites da lista de adjacências. Usando fallback.");
        return new ArrayUnorderedList<>();
    }

    // --- Getters importantes para o MotorJogo ---

    public Divisao getPontoCentral() {
        return pontoCentral;
    }

    public Divisao getRandomPontoEntrada() {
        if (pontosEntrada.isEmpty()) return null;
        // Escolher aleatoriamente um ponto de entrada
        int index = (int) (Math.random() * pontosEntrada.size());

        // Percorrer a lista até ao índice desejado usando iterator
        int contador = 0;
        java.util.Iterator<Divisao> it = pontosEntrada.iterator();
        while (it.hasNext()) {
            Divisao divisao = it.next();
            if (contador == index) {
                return divisao;
            }
            contador++;
        }
        return null;
    }

    /**
     * Retorna a lista de todos os pontos de entrada disponíveis.
     * @return ArrayUnorderedList com todas as entradas do labirinto
     */
    public ArrayUnorderedList<Divisao> getPontosEntrada() {
        return pontosEntrada;
    }

    /**
     * Retorna uma representação visual do labirinto mostrando todas as divisões
     * e suas conexões (corredores).
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔════════════════════════════════════════════════════════════╗\n");
        sb.append("║              ESTRUTURA DO LABIRINTO DA GLÓRIA              ║\n");
        sb.append("╚════════════════════════════════════════════════════════════╝\n\n");

        // Listar todas as divisões
        sb.append("📍 DIVISÕES NO LABIRINTO:\n");
        sb.append("─────────────────────────────────────────────────────────────\n");

        for (int i = 0; i < estrutura.size(); i++) {
            try {
                Divisao div = estrutura.getVertex(i);
                if (div != null) {
                    String icone = getIcone(div.getTipo());
                    sb.append(String.format("  [%d] %s %-30s (%s)\n",
                            i, icone, div.getNome(), div.getTipo()));
                }
            } catch (Exception e) {
                // Ignorar
            }
        }

        // Mostrar ponto central
        sb.append("\n🏆 PONTO CENTRAL (Objetivo): ");
        if (pontoCentral != null) {
            sb.append(pontoCentral.getNome()).append("\n");
        } else {
            sb.append("(não definido)\n");
        }

        // Mostrar pontos de entrada
        sb.append("🚪 PONTOS DE ENTRADA: ");
        if (pontosEntrada.isEmpty()) {
            sb.append("(nenhum)\n");
        } else {
            sb.append("\n");
            java.util.Iterator<Divisao> it = pontosEntrada.iterator();
            while (it.hasNext()) {
                Divisao entrada = it.next();
                sb.append("   - ").append(entrada.getNome()).append("\n");
            }
        }

        // Listar todas as conexões (arestas)
        sb.append("\n🔗 CORREDORES (Conexões):\n");
        sb.append("─────────────────────────────────────────────────────────────\n");

        int numConexoes = 0;
        for (int i = 0; i < estrutura.size(); i++) {
            try {
                Divisao origem = estrutura.getVertex(i);
                if (origem != null) {
                    for (int j = i + 1; j < estrutura.size(); j++) {
                        if (estrutura.isEdge(i, j)) {
                            Divisao destino = estrutura.getVertex(j);
                            if (destino != null) {
                                sb.append(String.format("  %s ←→ %s\n",
                                        origem.getNome(), destino.getNome()));
                                numConexoes++;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                // Ignorar
            }
        }

        if (numConexoes == 0) {
            sb.append("  (nenhuma conexão estabelecida)\n");
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
     * Método auxiliar para obter ícone baseado no tipo de divisão
     */
    private String getIcone(String tipo) {
        switch (tipo) {
            case Divisao.TIPO_ENTRADA: return "🚪";
            case Divisao.TIPO_CENTRO: return "🏆";
            case Divisao.TIPO_ENIGMA: return "❓";
            case Divisao.TIPO_ALAVANCA: return "🔧";
            default: return "📦";
        }
    }

    // --- Métodos auxiliares para exportação JSON ---

    /**
     * Retorna o tamanho do labirinto (número de divisões)
     */
    public int getTamanho() {
        return estrutura.size();
    }

    /**
     * Retorna uma divisão pelo índice no Network
     */
    public Divisao getDivisaoPorIndice(int indice) {
        try {
            return estrutura.getVertex(indice);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Verifica se existe conexão entre duas divisões pelos índices
     */
    public boolean existeConexao(int indice1, int indice2) {
        return estrutura.isEdge(indice1, indice2);
    }

    /**
     * Retorna o peso do corredor entre duas divisões
     */
    public double getPesoCorredor(int indice1, int indice2) {
        return estrutura.getWeight(indice1, indice2);
    }

    /**
     * Calcula o caminho mais curto entre duas divisões usando algoritmo de Dijkstra.
     * Este método é usado pelos bots inteligentes para encontrar a rota mais rápida
     * até o tesouro.
     *
     * @param origem A divisão de origem
     * @param destino A divisão de destino (geralmente o tesouro)
     * @return Iterator com o caminho completo (incluindo origem e destino), ou null se não houver caminho
     */
    public java.util.Iterator<Divisao> getCaminhoMaisCurto(Divisao origem, Divisao destino) {
        try {
            // Usar o método iteratorShortestPath do Network (algoritmo de Dijkstra)
            return estrutura.iteratorShortestPath(origem, destino);
        } catch (Exception e) {
            System.out.println("⚠️ Erro ao calcular caminho mais curto: " + e.getMessage());
            return null;
        }
    }

    /**
     * Calcula o peso (distância) do caminho mais curto entre duas divisões.
     * Útil para comparar rotas alternativas.
     *
     * @param origem A divisão de origem
     * @param destino A divisão de destino
     * @return O peso total do caminho mais curto, ou Double.MAX_VALUE se não houver caminho
     */
    public double getPesoCaminhoMaisCurto(Divisao origem, Divisao destino) {
        try {
            return estrutura.shortestPathWeight(origem, destino);
        } catch (Exception e) {
            return Double.MAX_VALUE; // Sem caminho
        }
    }

    /**
     * Retorna a estrutura Network interna (para casos avançados).
     * @return O Network que representa o labirinto
     */
    public Network<Divisao> getEstrutura() {
        return estrutura;
    }
}