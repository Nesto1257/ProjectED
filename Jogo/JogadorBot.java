package Jogo;

import Labirinto.Divisao;
import Labirinto.Labirinto;
import Structures.ArrayUnorderedList;
import java.util.Iterator;

/**
 * Representa um jogador controlado automaticamente por inteligência artificial.
 * Esta classe implementa o modo automático do jogo, onde o bot toma decisões
 * de movimento de forma autónoma.
 *
 * Estratégias disponíveis:
 * <ul>
 *   <li>Inteligente (Dijkstra) - Calcula o caminho mais curto até ao tesouro</li>
 *   <li>Aleatória - Escolhe um vizinho de forma aleatória</li>
 * </ul>
 *
 * O bot ainda precisa de resolver enigmas e alavancas, tal como
 * um jogador humano, e está sujeito a eventos aleatórios.
 *
 * @author Grupo ED
 * @version 1.0
 */
public class JogadorBot extends Jogador {

    /** Define se o bot utiliza estratégia aleatória ou inteligente */
    private boolean modoAleatorio;

    /**
     * Construtor do jogador bot.
     * Cria um jogador controlado por IA com a estratégia especificada.
     *
     * @param nome O nome do bot
     * @param pontoInicial A divisão onde o bot inicia o jogo
     * @param modoAleatorio Se true, usa estratégia aleatória; se false, usa Dijkstra
     */
    public JogadorBot(String nome, Divisao pontoInicial, boolean modoAleatorio) {
        super(nome, pontoInicial);
        this.modoAleatorio = modoAleatorio;
    }

    /**
     * Escolhe automaticamente o próximo movimento.
     * Utiliza a estratégia configurada para determinar a melhor divisão
     * para onde se mover.
     *
     * @param labirinto O labirinto onde o jogo decorre
     * @return A divisão escolhida, ou null se não houver saídas disponíveis
     */
    @Override
    public Divisao escolherMovimento(Labirinto labirinto) {
        // Obter as divisões vizinhas
        ArrayUnorderedList<Divisao> todosVizinhos = labirinto.getVizinhos(getPosicaoAtual());

        // Filtrar a posição atual da lista de vizinhos
        ArrayUnorderedList<Divisao> vizinhos = new ArrayUnorderedList<>();
        Divisao posicaoAtual = getPosicaoAtual();
        Iterator<Divisao> itFiltro = todosVizinhos.iterator();
        while (itFiltro.hasNext()) {
            Divisao d = itFiltro.next();
            if (!d.equals(posicaoAtual)) {
                vizinhos.addToRear(d);
            }
        }

        if (vizinhos.isEmpty()) {
            System.out.println(getNome() + " está encurralado.");
            return null;
        }

        Divisao escolhida;

        // Selecionar a estratégia de movimento
        if (modoAleatorio) {
            escolhida = escolherAleatorio(vizinhos);
        } else {
            escolhida = escolherComDijkstra(labirinto, vizinhos);
        }

        if (escolhida != null) {
            System.out.println("🤖 " + getNome() + " escolheu mover para: " + escolhida.getNome());
        }
        return escolhida;
    }

    /**
     * Escolhe um vizinho de forma aleatória.
     * Esta é a estratégia simples que não considera a distância ao objetivo.
     *
     * @param vizinhos Lista de divisões vizinhas disponíveis
     * @return A divisão escolhida aleatoriamente
     */
    private Divisao escolherAleatorio(ArrayUnorderedList<Divisao> vizinhos) {
        int indexAleatorio = (int) (Math.random() * vizinhos.size());

        int contador = 0;
        Iterator<Divisao> it = vizinhos.iterator();
        while (it.hasNext()) {
            Divisao divisao = it.next();
            if (contador == indexAleatorio) {
                return divisao;
            }
            contador++;
        }
        return vizinhos.first();
    }

    /**
     * Escolhe o próximo movimento utilizando o algoritmo de Dijkstra.
     * Calcula o caminho mais curto até ao tesouro e devolve o próximo
     * passo nesse caminho.
     * Se não for possível calcular o caminho (erro ou sem caminho válido),
     * recorre à estratégia aleatória.
     *
     * @param labirinto O labirinto completo
     * @param vizinhos Lista de divisões vizinhas disponíveis
     * @return O próximo passo no caminho mais curto, ou escolha aleatória se falhar
     */
    private Divisao escolherComDijkstra(Labirinto labirinto, ArrayUnorderedList<Divisao> vizinhos) {
        try {
            Divisao tesouro = labirinto.getPontoCentral();

            if (tesouro == null) {
                System.out.println("   ⚠️ Tesouro não encontrado! A usar estratégia aleatória.");
                return escolherAleatorio(vizinhos);
            }

            // Verificar se já está no tesouro
            if (getPosicaoAtual().equals(tesouro)) {
                return getPosicaoAtual();
            }

            // Obter o caminho mais curto utilizando Dijkstra
            Iterator<Divisao> caminhoIterator = labirinto.getCaminhoMaisCurto(getPosicaoAtual(), tesouro);

            if (caminhoIterator == null || !caminhoIterator.hasNext()) {
                System.out.println("   ⚠️ Sem caminho até ao tesouro! A usar estratégia aleatória.");
                return escolherAleatorio(vizinhos);
            }

            // O iterador devolve o caminho completo: [posição atual, próximo, ..., tesouro]
            // Precisamos do segundo elemento (próximo passo)
            caminhoIterator.next();

            if (!caminhoIterator.hasNext()) {
                // Só existe uma divisão no caminho (já está no destino)
                return getPosicaoAtual();
            }

            Divisao proximoPasso = caminhoIterator.next();

            // Verificar se o próximo passo está disponível nos vizinhos
            boolean vizinhoValido = false;
            Iterator<Divisao> itVizinhos = vizinhos.iterator();
            while (itVizinhos.hasNext()) {
                if (itVizinhos.next().equals(proximoPasso)) {
                    vizinhoValido = true;
                    break;
                }
            }

            if (vizinhoValido) {
                return proximoPasso;
            } else {
                System.out.println("   ⚠️ Próximo passo não é válido!");
                return escolherAleatorio(vizinhos);
            }

        } catch (Exception e) {
            // Em caso de erro, utilizar estratégia aleatória
            System.out.println("   ⚠️ Erro ao calcular caminho: " + e.getMessage());
            return escolherAleatorio(vizinhos);
        }
    }
}