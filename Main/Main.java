package Main;

import Labirinto.*;
import Jogo.*;
import Eventos.*;
import Structures.ArrayUnorderedList;

/**
 * Ponto de entrada do programa. Configura e inicia o simulador Labirinto da Glória.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Labirinto da Glória ===\n");

        // 1. CARREGAR O LABIRINTO DO JSON
        System.out.println("📂 A carregar labirinto...");
        Labirinto labirinto = CarregadorDados.carregarLabirinto("src/main/resources/labirinto.json");

        if (labirinto == null || labirinto.getTamanho() == 0) {
            System.out.println("❌ ERRO: Ficheiro labirinto.json não encontrado ou vazio!");
            System.out.println("⚠️ Certifique-se que o ficheiro existe em: src/main/resources/labirinto.json");
            return;
        }

        System.out.println("✅ Labirinto carregado com sucesso!");
        System.out.println("   → " + labirinto.getTamanho() + " divisões carregadas\n");

        // 2. CARREGAR QUESTÕES DE ENIGMAS
        System.out.println("📚 A carregar enigmas...");
        ArrayUnorderedList<QuestaoEnigma> listaQuestoes = CarregadorDados.carregarEnigmas("src/main/resources/enigmas.json");

        if (listaQuestoes.isEmpty()) {
            System.out.println("⚠️ Aviso: Ficheiro enigmas.json não encontrado. Usando questão de exemplo.");
            listaQuestoes.addToRear(new QuestaoEnigma(
                    "O que tem cidades, mas não tem casas; tem montanhas, mas não tem árvores; e tem água, mas não tem peixes?",
                    new String[]{"Um mapa", "Um livro", "Um computador"},
                    "Um mapa"
            ));
        } else {
            System.out.println("✅ Carregadas " + listaQuestoes.size() + " questões de enigmas!\n");
        }

        // 3. ATRIBUIR DESAFIOS ÀS DIVISÕES
        System.out.println("🎯 A configurar desafios...");

        // Usar DesafioFactory para criar desafios (Padrão Factory)
        DesafioFactory desafioFactory = new DesafioFactory(listaQuestoes);

        // Percorrer todas as divisões e atribuir desafios
        for (int i = 0; i < labirinto.getTamanho(); i++) {
            Divisao div = labirinto.getDivisaoPorIndice(i);
            if (div != null) {
                // Sala da Alavanca
                if (div.getTipo().equals(Divisao.TIPO_ALAVANCA)) {
                    div.setDesafio(desafioFactory.criarDesafioAlavanca());
                    System.out.println("   → Desafio de Alavanca atribuído a: " + div.getNome());
                }

                // Salas de Enigma
                if (div.getTipo().equals(Divisao.TIPO_ENIGMA)) {
                    div.setDesafio(desafioFactory.criarDesafioEnigma());
                    System.out.println("   → Desafio de Enigma atribuído a: " + div.getNome());
                }
            }
        }
        System.out.println("✅ Desafios configurados!\n");

        // === IMPRIMIR ESTRUTURA DO LABIRINTO (PARA TESTES) ===
        System.out.println(labirinto);

        // 4. CONFIGURAR OS JOGADORES
        System.out.println("👥 A configurar jogadores...");
        ArrayUnorderedList<Jogador> jogadores = new ArrayUnorderedList<>();

        // Obter lista de pontos de entrada disponíveis
        ArrayUnorderedList<Divisao> entradasDisponiveis = labirinto.getPontosEntrada();

        if (entradasDisponiveis.isEmpty()) {
            System.out.println("❌ ERRO: Nenhum ponto de entrada encontrado no labirinto!");
            return;
        }

        // Mostrar entradas disponíveis e pedir escolha ao jogador
        System.out.println("\n🚪 ESCOLHA O SEU PONTO DE ENTRADA:");
        System.out.println("═════════════════════════════════════");

        int opcao = 1;
        java.util.Iterator<Divisao> it = entradasDisponiveis.iterator();
        while (it.hasNext()) {
            Divisao entrada = it.next();
            System.out.println(opcao + ". " + entrada.getNome() + " (ID: " + entrada.getId() + ")");
            opcao++;
        }

        // Usar InputValidator para validar escolha (Padrão Utility Class)
        int escolha = InputValidator.lerInteiro(
            "\nDigite o número da sua escolha (1-" + (opcao - 1) + "): ",
            1,
            opcao - 1
        );

        // Obter a entrada escolhida
        Divisao entradaEscolhida = null;
        int contador = 1;
        java.util.Iterator<Divisao> itEscolha = entradasDisponiveis.iterator();
        while (itEscolha.hasNext()) {
            Divisao entrada = itEscolha.next();
            if (contador == escolha) {
                entradaEscolhida = entrada;
                break;
            }
            contador++;
        }

        if (entradaEscolhida == null) {
            System.out.println("❌ ERRO: Não foi possível selecionar a entrada!");
            return;
        }

        // ⚔️ CRIAR OS 4 JOGADORES DE FANTASIA ⚔️
        System.out.println("\n⚔️ A CRIAR OS HERÓIS DA AVENTURA... ⚔️");
        System.out.println("════════════════════════════════════════");

        // ✨ ESCOLHA DO MODO DE JOGO ✨
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║        🎮 MODO DE JOGO 🎮             ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
        System.out.println("1. 🕹️  MODO MANUAL");
        System.out.println("   → Você controla TODOS os 4 heróis");
        System.out.println("   → Toma decisões estratégicas em cada turno");
        System.out.println();
        System.out.println("2. 🤖 MODO AUTOMÁTICO");
        System.out.println("   → Todos os heróis são controlados por IA (Dijkstra)");
        System.out.println("   → Assista a aventura desenrolar-se automaticamente");
        System.out.println();

        // Usar InputValidator para validar escolha do modo
        int modoJogo = InputValidator.lerInteiro("Escolha o modo (1 = Manual, 2 = Automático): ", 1, 2);

        boolean modoManual = (modoJogo == 1);

        System.out.println();
        if (modoManual) {
            System.out.println("✅ MODO MANUAL selecionado! Você controla os 4 heróis.");
        } else {
            System.out.println("✅ MODO AUTOMÁTICO selecionado! Os bots vão jogar sozinhos.");
        }
        System.out.println();

        // 1. CAVALEIRO
        System.out.println("✅ Entrada selecionada: " + entradaEscolhida.getNome());
        Jogador cavaleiro;
        if (modoManual) {
            cavaleiro = new JogadorHumano("🛡️ Cavaleiro", entradaEscolhida);
            System.out.println("🛡️ Cavaleiro se junta à aventura! (Controlado por você)");
        } else {
            cavaleiro = new JogadorBot("🛡️ Cavaleiro", entradaEscolhida, false); // Bot inteligente
            System.out.println("🛡️ Cavaleiro se junta à aventura! (Bot Inteligente - IA)");
        }
        jogadores.addToRear(cavaleiro);
        System.out.println("   Posição inicial: " + entradaEscolhida.getNome());

        // 2. MAGO
        Divisao entradaMago = obterEntradaDiferente(entradasDisponiveis, entradaEscolhida);
        Jogador mago;
        if (modoManual) {
            mago = new JogadorHumano("🔮 Mago", entradaMago);
            System.out.println("\n🔮 Mago se junta à aventura! (Controlado por você)");
        } else {
            mago = new JogadorBot("🔮 Mago", entradaMago, false); // Bot inteligente
            System.out.println("\n🔮 Mago se junta à aventura! (Bot Inteligente - IA)");
        }
        jogadores.addToRear(mago);
        System.out.println("   Posição inicial: " + entradaMago.getNome());

        // 3. BÁRBARO
        Divisao entradaBarbaro = obterEntradaDiferente(entradasDisponiveis, entradaEscolhida);
        Jogador barbaro;
        if (modoManual) {
            barbaro = new JogadorHumano("⚔️ Bárbaro", entradaBarbaro);
            System.out.println("\n⚔️ Bárbaro se junta à aventura! (Controlado por você)");
        } else {
            barbaro = new JogadorBot("⚔️ Bárbaro", entradaBarbaro, false); // Bot inteligente
            System.out.println("\n⚔️ Bárbaro se junta à aventura! (Bot Inteligente - IA)");
        }
        jogadores.addToRear(barbaro);
        System.out.println("   Posição inicial: " + entradaBarbaro.getNome());

        // 4. ARQUEIRO
        Divisao entradaArqueiro = obterEntradaDiferente(entradasDisponiveis, entradaEscolhida);
        Jogador arqueiro;
        if (modoManual) {
            arqueiro = new JogadorHumano("🏹 Arqueiro", entradaArqueiro);
            System.out.println("\n🏹 Arqueiro junta-se à aventura!");
        } else {
            arqueiro = new JogadorBot("🏹 Arqueiro", entradaArqueiro, false); // Bot inteligente
            System.out.println("\n🏹 Arqueiro junta-se à aventura!");
        }
        jogadores.addToRear(arqueiro);
        System.out.println("   Posição inicial: " + entradaArqueiro.getNome());

        System.out.println("\n════════════════════════════════════════");
        System.out.println("✅ 4 heróis prontos para a aventura!");
        if (modoManual) {
            System.out.println("🎮 Modo MANUAL: Você controla TODOS os 4 heróis!");
        } else {
            System.out.println("🤖 Modo AUTOMÁTICO: Todos jogam com IA (Dijkstra)!");
        }
        System.out.println();

        // 5. INICIAR O MOTOR DE JOGO
        GameEngine motor = new GameEngine(labirinto, jogadores);

        // Adicionar observer para logging (Padrão Observer)
        // Modo verbose: true para depuração, false para jogo normal
        motor.addObserver(new ConsoleGameObserver(false));

        motor.iniciarJogo();
    }

    /**
     * Método auxiliar para obter uma entrada diferente da já selecionada.
     * Se houver apenas uma entrada, retorna essa mesma.
     * @param entradasDisponiveis Lista de todas as entradas disponíveis
     * @param entradaEvitar Entrada a evitar (já escolhida)
     * @return Uma entrada diferente, ou aleatória se houver apenas uma
     */
    private static Divisao obterEntradaDiferente(ArrayUnorderedList<Divisao> entradasDisponiveis, Divisao entradaEvitar) {
        // Se só há uma entrada, todos começam no mesmo lugar
        if (entradasDisponiveis.size() == 1) {
            return entradasDisponiveis.first();
        }

        // Criar lista de entradas diferentes da evitada
        ArrayUnorderedList<Divisao> outrasEntradas = new ArrayUnorderedList<>();
        java.util.Iterator<Divisao> it = entradasDisponiveis.iterator();
        while (it.hasNext()) {
            Divisao entrada = it.next();
            if (!entrada.equals(entradaEvitar)) {
                outrasEntradas.addToRear(entrada);
            }
        }

        // Se não há outras entradas, retorna a original
        if (outrasEntradas.isEmpty()) {
            return entradaEvitar;
        }

        // Escolher aleatoriamente entre as outras entradas
        int indexAleatorio = (int) (Math.random() * outrasEntradas.size());
        int contador = 0;
        java.util.Iterator<Divisao> itAleatorio = outrasEntradas.iterator();
        while (itAleatorio.hasNext()) {
            Divisao entrada = itAleatorio.next();
            if (contador == indexAleatorio) {
                return entrada;
            }
            contador++;
        }

        // Fallback (não deveria chegar aqui)
        return entradasDisponiveis.first();
    }
}

