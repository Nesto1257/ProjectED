package Jogo;

import Labirinto.*;
import Eventos.*;
import Structures.ArrayUnorderedList;

/**
 * Classe responsável pela configuração e inicialização do jogo.
 * Separa a lógica de setup do ponto de entrada principal, seguindo
 * o princípio Single Responsibility (SRP).
 *
 * Responsabilidades:
 * <ul>
 *   <li>Carregar o labirinto do ficheiro JSON</li>
 *   <li>Carregar as questões de enigmas</li>
 *   <li>Configurar os desafios nas divisões</li>
 *   <li>Criar e configurar os jogadores</li>
 *   <li>Iniciar o motor de jogo</li>
 * </ul>
 *
 * @author Grupo ED
 * @version 1.0
 */
public class GameSetup {

    /** Caminho para o ficheiro do labirinto */
    private String ficheiroLabirinto;

    /** Caminho para o ficheiro de enigmas */
    private static final String FICHEIRO_ENIGMAS = "src/main/resources/enigmas.json";

    /** Número de jogadores no jogo */
    private static final int NUM_JOGADORES = 4;

    /** O labirinto carregado */
    private Labirinto labirinto;

    /** Lista de questões de enigmas */
    private ArrayUnorderedList<QuestaoEnigma> questoes;

    /** Lista de jogadores configurados */
    private ArrayUnorderedList<Jogador> jogadores;

    /**
     * Construtor que recebe o caminho do ficheiro de labirinto.
     *
     * @param ficheiroLabirinto O caminho para o ficheiro JSON do labirinto
     */
    public GameSetup(String ficheiroLabirinto) {
        this.ficheiroLabirinto = ficheiroLabirinto;
    }

    /**
     * Construtor padrão que usa o labirinto por defeito.
     */
    public GameSetup() {
        this.ficheiroLabirinto = "src/main/resources/labirinto.json";
    }

    /**
     * Inicia o processo completo de configuração e execução do jogo.
     *
     * @return true se o jogo foi iniciado com sucesso, false se houve erro
     */
    public boolean iniciar() {
        System.out.println("=== Labirinto da Glória ===\n");

        // Carregar dados
        if (!carregarLabirinto()) {
            return false;
        }

        carregarEnigmas();
        configurarDesafios();

        // Mostrar estatísticas
        System.out.println(labirinto);

        // Configurar jogadores
        if (!configurarJogadores()) {
            return false;
        }

        // Iniciar o jogo
        iniciarJogo();

        return true;
    }

    /**
     * Carrega o labirinto a partir do ficheiro JSON.
     *
     * @return true se carregado com sucesso, false caso contrário
     */
    private boolean carregarLabirinto() {
        labirinto = CarregadorDados.carregarLabirinto(ficheiroLabirinto);

        if (labirinto == null || labirinto.getTamanho() == 0) {
            System.out.println("❌ ERRO: Ficheiro labirinto não encontrado ou vazio!");
            return false;
        }

        return true;
    }

    /**
     * Carrega as questões de enigmas do ficheiro JSON.
     * Se não existirem, cria uma questão de exemplo.
     */
    private void carregarEnigmas() {
        questoes = CarregadorDados.carregarEnigmas(FICHEIRO_ENIGMAS);

        if (questoes.isEmpty()) {
            questoes.addToRear(new QuestaoEnigma(
                "O que tem cidades, mas não tem casas; tem montanhas, mas não tem árvores; e tem água, mas não tem peixes?",
                new String[]{"Um mapa", "Um livro", "Um computador"},
                "Um mapa"
            ));
        }
    }

    /**
     * Configura os desafios nas divisões apropriadas do labirinto.
     */
    private void configurarDesafios() {
        DesafioFactory factory = new DesafioFactory(questoes);

        for (int i = 0; i < labirinto.getTamanho(); i++) {
            Divisao div = labirinto.getDivisaoPorIndice(i);
            if (div != null) {
                TipoDivisao tipo = div.getTipoEnum();
                if (tipo == TipoDivisao.ALAVANCA) {
                    div.setDesafio(factory.criarDesafioAlavanca());
                }
                if (tipo == TipoDivisao.ENIGMA) {
                    div.setDesafio(factory.criarDesafioEnigma());
                }
            }
        }
    }

    /**
     * Configura os jogadores, incluindo a escolha do modo de jogo
     * e a ordem aleatória de turnos.
     *
     * @return true se configurado com sucesso, false caso contrário
     */
    private boolean configurarJogadores() {
        ArrayUnorderedList<Divisao> entradas = labirinto.getPontosEntrada();

        if (entradas.isEmpty()) {
            System.out.println("❌ ERRO: Nenhum ponto de entrada encontrado no labirinto!");
            return false;
        }

        // Escolher modo de jogo
        boolean modoManual = escolherModoJogo();

        // Criar jogadores
        jogadores = criarJogadores(entradas, modoManual);

        // Baralhar ordem
        baralharOrdem();

        return true;
    }

    /**
     * Apresenta o menu de escolha do modo de jogo.
     *
     * @return true para modo manual, false para modo automático
     */
    private boolean escolherModoJogo() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║        🎮 MODO DE JOGO 🎮             ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
        System.out.println("1. 🕹️  MODO MANUAL");
        System.out.println("   → Controla TODOS os 4 heróis");
        System.out.println();
        System.out.println("2. 🤖 MODO AUTOMÁTICO");
        System.out.println("   → Todos os heróis são controlados por IA");
        System.out.println();

        int escolha = InputValidator.lerInteiro("Escolha o modo (1 = Manual, 2 = Automático): ", 1, 2);
        boolean modoManual = (escolha == 1);

        System.out.println();
        if (modoManual) {
            System.out.println("✅ MODO MANUAL selecionado!");
        } else {
            System.out.println("✅ MODO AUTOMÁTICO selecionado!");
        }

        return modoManual;
    }

    /**
     * Cria os 4 jogadores com entradas únicas (uma entrada diferente para cada).
     * As entradas são baralhadas aleatoriamente usando Fisher-Yates.
     *
     * @param entradas Lista de entradas disponíveis
     * @param modoManual Se true, cria jogadores humanos; se false, cria bots
     * @return Lista de jogadores criados
     */
    private ArrayUnorderedList<Jogador> criarJogadores(ArrayUnorderedList<Divisao> entradas, boolean modoManual) {
        System.out.println("\n🎲 A LANÇAR DADOS PARA ORDEM DE JOGO... 🎲\n");

        // Converter entradas para array
        Divisao[] entradasArray = converterParaArray(entradas);

        // Baralhar as entradas usando Fisher-Yates para garantir aleatoriedade
        for (int i = entradasArray.length - 1; i > 0; i--) {
            int j = (int)(Math.random() * (i + 1));
            Divisao temp = entradasArray[i];
            entradasArray[i] = entradasArray[j];
            entradasArray[j] = temp;
        }

        // Nomes e emojis dos heróis
        String[] nomes = {"🛡️ Cavaleiro", "🔮 Mago", "⚔️ Bárbaro", "🏹 Arqueiro"};

        ArrayUnorderedList<Jogador> lista = new ArrayUnorderedList<>();

        // Atribuir uma entrada única a cada jogador
        for (int i = 0; i < nomes.length; i++) {
            // Usar módulo para caso haja menos entradas que jogadores
            Divisao entrada = entradasArray[i % entradasArray.length];
            Jogador jogador;

            if (modoManual) {
                jogador = new JogadorHumano(nomes[i], entrada);
            } else {
                jogador = new JogadorBot(nomes[i], entrada, false);
            }

            lista.addToRear(jogador);
        }

        return lista;
    }

    /**
     * Converte uma lista de divisões para array.
     *
     * @param lista A lista a converter
     * @return Array com as divisões
     */
    private Divisao[] converterParaArray(ArrayUnorderedList<Divisao> lista) {
        Divisao[] array = new Divisao[lista.size()];
        int idx = 0;
        java.util.Iterator<Divisao> it = lista.iterator();
        while (it.hasNext()) {
            array[idx++] = it.next();
        }
        return array;
    }

    /**
     * Baralha a ordem dos jogadores usando Fisher-Yates.
     */
    private void baralharOrdem() {
        // Converter para array
        Jogador[] array = new Jogador[jogadores.size()];
        int idx = 0;
        java.util.Iterator<Jogador> it = jogadores.iterator();
        while (it.hasNext()) {
            array[idx++] = it.next();
        }

        // Fisher-Yates shuffle
        for (int i = array.length - 1; i > 0; i--) {
            int j = (int)(Math.random() * (i + 1));
            Jogador temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }

        // Recriar lista na ordem baralhada
        jogadores = new ArrayUnorderedList<>();

        System.out.println("📜 ORDEM DE JOGO:");
        System.out.println("─────────────────────────────────────");
        for (int i = 0; i < array.length; i++) {
            jogadores.addToRear(array[i]);
            System.out.println((i + 1) + "º " + array[i].getNome() + " → " + array[i].getPosicaoAtual().getNome());
        }

        System.out.println("\n✅ " + NUM_JOGADORES + " heróis prontos para a aventura!\n");
    }

    /**
     * Inicia o motor de jogo com a configuração atual.
     */
    private void iniciarJogo() {
        GameEngine motor = new GameEngine(labirinto, jogadores);
        motor.iniciarJogo();
    }
}

