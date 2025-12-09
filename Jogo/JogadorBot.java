package Jogo;

import Labirinto.Divisao;
import Labirinto.Labirinto;
import Structures.ArrayUnorderedList;
import java.util.Iterator;

/**
 * Implementa um Jogador controlado automaticamente (Bot/IA).
 * Esta classe simula um jogador automático que usa o algoritmo de Dijkstra
 * para encontrar o caminho mais curto até o tesouro, mas ainda respeita
 * desafios, eventos aleatórios e impedimentos do jogo.
 *
 * O bot possui estratégia inteligente:
 * - Usa Dijkstra (caminho mais curto) quando possível
 * - Adapta-se a eventos aleatórios e impedimentos
 * - Ainda precisa resolver enigmas e alavancas como jogadores humanos
 *
 * @author Grupo ED
 * @version 1.0
 */
public class JogadorBot extends Jogador {

    private boolean modoAleatorio; // Se true, joga aleatoriamente (modo simples)

    /**
     * Construtor do JogadorBot com estratégia inteligente (Dijkstra).
     * Inicializa o bot com um nome e posição inicial.
     * Por padrão, usa estratégia inteligente (Dijkstra).
     *
     * @param nome O nome do bot
     * @param pontoInicial A divisão inicial onde o bot começa
     */
    public JogadorBot(String nome, Divisao pontoInicial) {
        super(nome, pontoInicial);
        this.modoAleatorio = false; // Modo inteligente por padrão
    }

    /**
     * Construtor do JogadorBot com escolha de modo.
     *
     * @param nome O nome do bot
     * @param pontoInicial A divisão inicial onde o bot começa
     * @param modoAleatorio Se true, joga aleatoriamente; se false, usa Dijkstra
     */
    public JogadorBot(String nome, Divisao pontoInicial, boolean modoAleatorio) {
        super(nome, pontoInicial);
        this.modoAleatorio = modoAleatorio;
    }

    /**
     * Escolhe automaticamente o próximo movimento.
     *
     * ESTRATÉGIA INTELIGENTE (Dijkstra):
     * - Calcula o caminho mais curto até o tesouro
     * - Escolhe o próximo passo nesse caminho
     * - Se não conseguir calcular, escolhe aleatoriamente
     *
     * ESTRATÉGIA ALEATÓRIA:
     * - Escolhe um vizinho aleatório
     *
     * @param labirinto O labirinto onde o bot se encontra
     * @return A Divisao escolhida, ou null se não houver vizinhos disponíveis
     */
    @Override
    public Divisao escolherMovimento(Labirinto labirinto) {
        System.out.println("------------------------------------");
        System.out.println("🤖 Turno de " + getNome() + " (Bot). Posição atual: " + getPosicaoAtual().getNome());

        ArrayUnorderedList<Divisao> vizinhos = labirinto.getVizinhos(getPosicaoAtual());

        if (vizinhos.isEmpty()) {
            System.out.println(getNome() + " está encurralado.");
            return null;
        }

        Divisao escolhida = null;

        // Escolher estratégia baseada no modo
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
     * Escolhe um vizinho aleatoriamente (estratégia simples).
     *
     * @param vizinhos Lista de divisões vizinhas disponíveis
     * @return Divisão escolhida aleatoriamente
     */
    private Divisao escolherAleatorio(ArrayUnorderedList<Divisao> vizinhos) {
        int indexAleatorio = (int) (Math.random() * vizinhos.size());

        int contador = 0;
        Iterator<Divisao> it = vizinhos.iterator();
        while (it.hasNext()) {
            Divisao divisao = it.next();
            if (contador == indexAleatorio) {
                System.out.println("   💭 Estratégia: Aleatória");
                return divisao;
            }
            contador++;
        }
        return vizinhos.first();
    }

    /**
     * Escolhe o próximo movimento usando algoritmo de Dijkstra.
     * Calcula o caminho mais curto até o tesouro e retorna o próximo passo.
     * Se não conseguir calcular (erro ou sem caminho), escolhe aleatoriamente.
     *
     * @param labirinto O labirinto completo (Network)
     * @param vizinhos Lista de divisões vizinhas disponíveis
     * @return Próxima divisão no caminho mais curto, ou aleatória se falhar
     */
    private Divisao escolherComDijkstra(Labirinto labirinto, ArrayUnorderedList<Divisao> vizinhos) {
        try {
            Divisao tesouro = labirinto.getPontoCentral();

            if (tesouro == null) {
                System.out.println("   ⚠️ Tesouro não encontrado! Usando estratégia aleatória.");
                return escolherAleatorio(vizinhos);
            }

            // Se já está no tesouro, fica parado (não deveria acontecer)
            if (getPosicaoAtual().equals(tesouro)) {
                return getPosicaoAtual();
            }

            // Obter o caminho mais curto usando Dijkstra (da estrutura Network)
            Iterator<Divisao> caminhoIterator = labirinto.getCaminhoMaisCurto(getPosicaoAtual(), tesouro);

            if (caminhoIterator == null || !caminhoIterator.hasNext()) {
                System.out.println("   ⚠️ Sem caminho até o tesouro! Usando estratégia aleatória.");
                return escolherAleatorio(vizinhos);
            }

            // O iterator retorna o caminho completo: [posição atual, próximo, ..., tesouro]
            // Precisamos pegar o SEGUNDO elemento (próximo passo)

            Divisao primeiraDiv = caminhoIterator.next(); // Posição atual

            if (!caminhoIterator.hasNext()) {
                // Só tem uma divisão no caminho (já está no destino)
                return getPosicaoAtual();
            }

            Divisao proximoPasso = caminhoIterator.next(); // PRÓXIMO passo no caminho

            // Verificar se o próximo passo está nos vizinhos disponíveis
            boolean vizinhoValido = false;
            Iterator<Divisao> itVizinhos = vizinhos.iterator();
            while (itVizinhos.hasNext()) {
                if (itVizinhos.next().equals(proximoPasso)) {
                    vizinhoValido = true;
                    break;
                }
            }

            if (vizinhoValido) {
                System.out.println("   🧠 Estratégia: Dijkstra (caminho mais curto)");
                System.out.println("   🎯 Objetivo: " + tesouro.getNome());
                return proximoPasso;
            } else {
                System.out.println("   ⚠️ Próximo passo não é vizinho direto! Usando aleatório.");
                return escolherAleatorio(vizinhos);
            }

        } catch (Exception e) {
            // Se houver qualquer erro ao calcular Dijkstra, usa estratégia aleatória
            System.out.println("   ⚠️ Erro ao calcular caminho: " + e.getMessage());
            System.out.println("   💭 Usando estratégia aleatória como fallback.");
            return escolherAleatorio(vizinhos);
        }
    }

    /**
     * Define o modo de jogo do bot.
     *
     * @param modoAleatorio true para jogar aleatoriamente, false para usar Dijkstra
     */
    public void setModoAleatorio(boolean modoAleatorio) {
        this.modoAleatorio = modoAleatorio;
    }

    /**
     * Retorna se o bot está em modo aleatório.
     *
     * @return true se joga aleatoriamente, false se usa estratégia inteligente
     */
    public boolean isModoAleatorio() {
        return modoAleatorio;
    }
}