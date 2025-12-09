package Jogo;

import Labirinto.Divisao;
import Structures.ArrayUnorderedList;
import Eventos.EventoAleatorio;
import Eventos.TipoEvento;

/**
 * Gestor de eventos aleatórios no labirinto.
 * Responsável por aplicar eventos aleatórios durante movimentação dos jogadores.
 *
 * @author Grupo ED
 * @version 2.0
 */
public class EventManager {
    private static final double CHANCE_EVENTO = 0.2; // 20% de chance
    private ArrayUnorderedList<Jogador> todosJogadores;

    /**
     * Construtor do gestor de eventos.
     *
     * @param jogadores Lista de todos os jogadores no jogo
     */
    public EventManager(ArrayUnorderedList<Jogador> jogadores) {
        this.todosJogadores = jogadores;
    }

    /**
     * Aplica um evento aleatório ao jogador durante a travessia do corredor.
     *
     * @param jogador O jogador que está atravessando o corredor
     * @param origem A divisão de origem
     * @param destino A divisão de destino planejada
     * @return true se o movimento foi cancelado pelo evento, false caso contrário
     */
    public boolean aplicarEventoAleatorio(Jogador jogador, Divisao origem, Divisao destino) {
        // Verifica se ocorre evento aleatório
        if (Math.random() >= CHANCE_EVENTO) {
            return false; // Nenhum evento ocorreu
        }

        EventoAleatorio evento = EventoAleatorio.getEventoAleatorio();
        System.out.println("\n🎲 !!! EVENTO NO CORREDOR !!! " + evento.getDescricao());

        // Usar TipoEvento enum para switch (melhor OOP)
        switch (evento.getTipoEvento()) {
            case EXTRA_TURN:
                return aplicarJogadaExtra(jogador);

            case TROCA_POSICAO:
                return aplicarTrocaPosicao(jogador);

            case RECUAR:
                return aplicarRecuo(jogador);

            case IMPEDIR_TURNO:
                return aplicarImpedimento(jogador);

            case TROCAR_TODOS:
                return aplicarCaosTotal();

            default:
                return false;
        }
    }

    /**
     * Aplica evento de jogada extra.
     *
     * @param jogador O jogador beneficiado
     * @return false (não cancela movimento)
     */
    private boolean aplicarJogadaExtra(Jogador jogador) {
        jogador.adicionarJogadasExtra(1);
        jogador.registrarEfeito("Ganhou 1 jogada extra");
        System.out.println("→ " + jogador.getNome() + " ganha 1 jogada extra!");
        return false; // Não cancela o movimento
    }

    /**
     * Aplica evento de troca de posição com outro jogador.
     *
     * @param jogador O jogador que vai trocar de posição
     * @return true (cancela movimento planejado)
     */
    private boolean aplicarTrocaPosicao(Jogador jogador) {
        trocarPosicaoComOutroJogador(jogador);
        jogador.registrarEfeito("Trocou de posição com outro jogador");
        return true; // Cancela o movimento - já foi movido pela troca
    }

    /**
     * Aplica evento de recuo.
     *
     * @param jogador O jogador que vai recuar
     * @return true (cancela movimento planejado)
     */
    private boolean aplicarRecuo(Jogador jogador) {
        recuarJogador(jogador);
        jogador.registrarEfeito("Recuou para posição anterior");
        return true; // Cancela o movimento - já recuou
    }

    /**
     * Aplica evento de impedimento de turnos.
     *
     * @param jogador O jogador que será impedido
     * @return false (não cancela movimento atual)
     */
    private boolean aplicarImpedimento(Jogador jogador) {
        jogador.setTurnosImpedido(2);
        jogador.registrarEfeito("Impedido por 2 turnos");
        System.out.println("→ " + jogador.getNome() + " fica impedido de jogar por 2 turnos!");
        return false; // Não cancela o movimento atual
    }

    /**
     * Aplica evento de caos total (todos trocam de posição).
     *
     * @return true (cancela movimento planejado)
     */
    private boolean aplicarCaosTotal() {
        trocarTodosJogadoresDePosicao();
        return true; // Cancela o movimento - todos foram movidos
    }

    /**
     * Troca a posição do jogador com outro jogador à escolha.
     *
     * @param jogadorAtual O jogador que vai trocar de posição
     */
    private void trocarPosicaoComOutroJogador(Jogador jogadorAtual) {
        // Criar lista de jogadores disponíveis (exceto o atual)
        ArrayUnorderedList<Jogador> outrosJogadores = new ArrayUnorderedList<>();
        java.util.Iterator<Jogador> it = todosJogadores.iterator();
        while (it.hasNext()) {
            Jogador j = it.next();
            if (j != jogadorAtual) {
                outrosJogadores.addToRear(j);
            }
        }

        if (outrosJogadores.isEmpty()) {
            System.out.println("→ Não há outros jogadores para trocar de posição!");
            return;
        }

        Jogador jogadorEscolhido = escolherJogadorParaTroca(jogadorAtual, outrosJogadores);

        if (jogadorEscolhido != null) {
            executarTrocaDePosicao(jogadorAtual, jogadorEscolhido);
        }
    }

    /**
     * Permite ao jogador escolher com quem trocar de posição.
     *
     * @param jogadorAtual O jogador que vai trocar
     * @param outrosJogadores Lista de jogadores disponíveis
     * @return O jogador escolhido para troca
     */
    private Jogador escolherJogadorParaTroca(Jogador jogadorAtual, ArrayUnorderedList<Jogador> outrosJogadores) {
        if (jogadorAtual instanceof JogadorHumano) {
            return escolherJogadorManual(outrosJogadores);
        } else {
            return escolherJogadorAleatorio(outrosJogadores);
        }
    }

    /**
     * Escolha manual de jogador para troca (jogador humano).
     *
     * @param outrosJogadores Lista de jogadores disponíveis
     * @return O jogador escolhido
     */
    private Jogador escolherJogadorManual(ArrayUnorderedList<Jogador> outrosJogadores) {
        System.out.println("\n👥 Escolha com que jogador deseja trocar de posição:");

        int opcao = 1;
        java.util.Iterator<Jogador> itOpcoes = outrosJogadores.iterator();
        while (itOpcoes.hasNext()) {
            Jogador j = itOpcoes.next();
            System.out.println(opcao + ". " + j.getNome() + " (em " + j.getPosicaoAtual().getNome() + ")");
            opcao++;
        }

        // Usar InputValidator para obter escolha válida
        int escolha = InputValidator.lerInteiro("Escolha (1-" + (opcao-1) + "): ", 1, opcao-1);

        // Obter jogador escolhido
        int contador = 1;
        java.util.Iterator<Jogador> itEscolha = outrosJogadores.iterator();
        while (itEscolha.hasNext()) {
            Jogador j = itEscolha.next();
            if (contador == escolha) {
                return j;
            }
            contador++;
        }
        return null;
    }

    /**
     * Escolha aleatória de jogador para troca (bot).
     *
     * @param outrosJogadores Lista de jogadores disponíveis
     * @return O jogador escolhido aleatoriamente
     */
    private Jogador escolherJogadorAleatorio(ArrayUnorderedList<Jogador> outrosJogadores) {
        int indexAleatorio = (int)(Math.random() * outrosJogadores.size());
        int contador = 0;
        java.util.Iterator<Jogador> itAleatorio = outrosJogadores.iterator();
        while (itAleatorio.hasNext()) {
            Jogador j = itAleatorio.next();
            if (contador == indexAleatorio) {
                return j;
            }
            contador++;
        }
        return null;
    }

    /**
     * Executa a troca de posição entre dois jogadores.
     *
     * @param jogador1 Primeiro jogador
     * @param jogador2 Segundo jogador
     */
    private void executarTrocaDePosicao(Jogador jogador1, Jogador jogador2) {
        // Guardar posições originais
        Divisao posicaoOriginal1 = jogador1.getPosicaoAtual();
        Divisao posicaoOriginal2 = jogador2.getPosicaoAtual();

        // Trocar posições
        jogador1.moverPara(posicaoOriginal2);
        jogador2.moverPara(posicaoOriginal1);

        // Definir limite de recuo
        jogador1.setLimiteRecuo(posicaoOriginal2);
        jogador2.setLimiteRecuo(posicaoOriginal1);

        System.out.println("🔄 " + jogador1.getNome() + " trocou de posição com " + jogador2.getNome() + "!");
        System.out.println("→ " + jogador1.getNome() + " agora está em " + jogador1.getPosicaoAtual().getNome());
        System.out.println("→ " + jogador2.getNome() + " agora está em " + jogador2.getPosicaoAtual().getNome());
    }

    /**
     * Recua o jogador para a última posição estável.
     *
     * @param jogador O jogador a recuar
     */
    private void recuarJogador(Jogador jogador) {
        Divisao posicaoAtual = jogador.getPosicaoAtual();
        Divisao destino = jogador.getUltimaPosicaoEstavel();

        // Se já está na última posição estável, não há para onde recuar
        if (posicaoAtual.equals(destino)) {
            System.out.println("→ " + jogador.getNome() + " já está na posição inicial e não pode recuar!");
            return;
        }

        // Verificar limite de recuo
        if (jogador.getLimiteRecuo() != null) {
            if (posicaoAtual.equals(jogador.getLimiteRecuo())) {
                System.out.println("→ " + jogador.getNome() + " está no limite de recuo e não pode recuar mais!");
                return;
            }
            destino = jogador.getLimiteRecuo();
        }

        jogador.moverPara(destino);
        System.out.println("⬅️  " + jogador.getNome() + " recuou para " + destino.getNome());
    }

    /**
     * Embaralha as posições de todos os jogadores (CAOS!).
     */
    private void trocarTodosJogadoresDePosicao() {
        System.out.println("\n💥 CAOS TOTAL! Todos os jogadores trocam de posição!");

        // Criar array com todas as posições atuais
        int numJogadores = todosJogadores.size();
        Divisao[] posicoesOriginais = new Divisao[numJogadores];
        Jogador[] jogadoresArray = new Jogador[numJogadores];

        int index = 0;
        java.util.Iterator<Jogador> it = todosJogadores.iterator();
        while (it.hasNext()) {
            Jogador j = it.next();
            jogadoresArray[index] = j;
            posicoesOriginais[index] = j.getPosicaoAtual();
            index++;
        }

        // Embaralhar posições (algoritmo Fisher-Yates)
        for (int i = numJogadores - 1; i > 0; i--) {
            int j = (int)(Math.random() * (i + 1));
            // Trocar posições[i] com posições[j]
            Divisao temp = posicoesOriginais[i];
            posicoesOriginais[i] = posicoesOriginais[j];
            posicoesOriginais[j] = temp;
        }

        // Atribuir novas posições embaralhadas
        for (int i = 0; i < numJogadores; i++) {
            jogadoresArray[i].moverPara(posicoesOriginais[i]);
            // Definir limite de recuo para a nova posição
            jogadoresArray[i].setLimiteRecuo(posicoesOriginais[i]);
            System.out.println("→ " + jogadoresArray[i].getNome() + " agora está em " + posicoesOriginais[i].getNome());
        }
    }
}

