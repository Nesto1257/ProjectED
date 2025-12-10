package Main;

import Jogo.GameSetup;
import Jogo.InputValidator;
import Labirinto.CriadorMapa;
import java.io.File;

/**
 * Classe responsável pela apresentação do menu principal do jogo.
 * Permite ao utilizador escolher entre iniciar um jogo ou criar um novo mapa.
 *
 * @author Grupo ED
 * @version 1.0
 */
public class MenuPrincipal {

    /** Diretório onde os mapas são armazenados */
    private static final String DIRETORIO_MAPAS = "src/main/resources/";

    /** Prefixo para ficheiros de mapas de labirinto */
    private static final String PREFIXO_LABIRINTO = "labirinto";

    /**
     * Apresenta o menu principal e processa as escolhas do utilizador.
     * O menu é apresentado em loop até que o utilizador escolha sair.
     */
    public void mostrar() {
        boolean sair = false;

        while (!sair) {
            imprimirCabecalho();
            imprimirOpcoes();

            int opcao = InputValidator.lerInteiro("Escolha uma opção: ", 1, 3);

            switch (opcao) {
                case 1:
                    iniciarJogo();
                    break;
                case 2:
                    criarMapa();
                    break;
                case 3:
                    sair = true;
                    mensagem();
                    break;
            }
        }
    }

    /**
     * Imprime o cabeçalho decorativo do menu.
     */
    private void imprimirCabecalho() {
        System.out.println();
        System.out.println("╔═══════════════════════════════════════════════════════╗");
        System.out.println("║          🏰 LABIRINTO DA GLÓRIA 🏰                    ║");
        System.out.println("║               Menu Principal                          ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
        System.out.println();
    }

    /**
     * Imprime as opções disponíveis no menu.
     */
    private void imprimirOpcoes() {
        System.out.println("  1. 🎮 Iniciar Jogo");
        System.out.println("  2. 🗺️  Criar Novo Mapa");
        System.out.println("  3. 🚪 Sair");
        System.out.println();
    }

    /**
     * Inicia o jogo após o utilizador escolher um mapa.
     */
    private void iniciarJogo() {
        String mapaEscolhido = escolherMapa();

        if (mapaEscolhido != null) {
            GameSetup setup = new GameSetup(mapaEscolhido);
            setup.iniciar();
        }
    }

    /**
     * Apresenta a lista de mapas disponíveis e permite escolher um.
     *
     * @return O caminho completo do mapa escolhido, ou null se cancelado
     */
    private String escolherMapa() {
        System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║            📜 MAPAS DISPONÍVEIS 📜                    ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝\n");

        File diretorio = new File(DIRETORIO_MAPAS);
        File[] ficheiros = diretorio.listFiles((dir, nome) ->
                nome.startsWith(PREFIXO_LABIRINTO) && nome.endsWith(".json"));

        if (ficheiros == null || ficheiros.length == 0) {
            System.out.println("❌ Nenhum mapa encontrado no diretório!");
            System.out.println("   Crie um novo mapa primeiro.\n");
            return null;
        }

        // Listar mapas disponíveis
        System.out.println("Mapas encontrados:\n");
        for (int i = 0; i < ficheiros.length; i++) {
            String nomeMapa = ficheiros[i].getName().replace(".json", "");
            System.out.println("  " + (i + 1) + ". 🗺️  " + nomeMapa);
        }
        System.out.println("  " + (ficheiros.length + 1) + ". ↩️  Voltar ao menu principal");
        System.out.println();

        int escolha = InputValidator.lerInteiro("Escolha um mapa: ", 1, ficheiros.length + 1);

        if (escolha == ficheiros.length + 1) {
            return null; // Voltar ao menu
        }

        String caminhoMapa = ficheiros[escolha - 1].getPath();
        System.out.println("\n✅ Mapa selecionado: " + ficheiros[escolha - 1].getName() + "\n");
        return caminhoMapa;
    }

    /**
     * Inicia o processo de criação de um novo mapa.
     */
    private void criarMapa() {
        CriadorMapa criador = new CriadorMapa();
        criador.iniciar();
    }

    /**
     * Apresenta mensagem de despedida ao sair do jogo.
     */
    private void mensagem() {
        System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║    👋 Obrigado por jogar Labirinto da Glória! 👋     ║");
        System.out.println("║              Até à próxima aventura!                  ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝\n");
    }
}

