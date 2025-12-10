package Jogo;

import Labirinto.Divisao;
import Structures.ArrayUnorderedList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe responsável por gerar o relatório final da partida em formato JSON.
 * Regista informações detalhadas sobre o jogo, incluindo:
 * <ul>
 *   <li>Data e hora de início e fim</li>
 *   <li>Jogador vencedor</li>
 *   <li>Percurso de cada jogador</li>
 *   <li>Obstáculos ultrapassados</li>
 *   <li>Efeitos de eventos aplicados</li>
 * </ul>
 *
 * @author Grupo ED
 * @version 1.0
 */
public class RelatorioPartida {

    /** Data e hora de início da partida */
    private String dataHoraInicio;

    /** Data e hora de fim da partida */
    private String dataHoraFim;

    /** Nome do jogador vencedor */
    private String nomeVencedor;

    /** Lista de relatórios individuais de cada jogador */
    private ArrayUnorderedList<RelatorioJogador> jogadores;

    /** Número total de turnos jogados */
    private int totalTurnos;

    /**
     * Construtor do relatório de partida.
     * Inicializa a data de início e prepara a lista de jogadores.
     */
    public RelatorioPartida() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dataHoraInicio = LocalDateTime.now().format(formatter);
        this.jogadores = new ArrayUnorderedList<>();
        this.totalTurnos = 0;
    }

    /**
     * Regista a data e hora de início da partida.
     */
    public void registrarInicio() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dataHoraInicio = LocalDateTime.now().format(formatter);
    }

    /**
     * Regista o fim da partida com as informações finais.
     *
     * @param vencedor O jogador que venceu a partida
     * @param turnos O número total de turnos jogados
     */
    public void registrarFim(Jogador vencedor, int turnos) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dataHoraFim = LocalDateTime.now().format(formatter);
        this.nomeVencedor = vencedor != null ? vencedor.getNome() : "Nenhum";
        this.totalTurnos = turnos;
    }

    /**
     * Adiciona os dados de um jogador ao relatório.
     * Recolhe todas as informações sobre o percurso, obstáculos
     * e efeitos aplicados ao jogador.
     *
     * @param jogador O jogador cujos dados serão adicionados
     */
    public void adicionarJogador(Jogador jogador) {
        RelatorioJogador relJogador = new RelatorioJogador();
        relJogador.nome = jogador.getNome();
        relJogador.posicaoFinal = jogador.getPosicaoAtual().getNome();
        relJogador.totalMovimentos = jogador.getTotalMovimentos();

        // Converter a lista de divisões visitadas
        relJogador.divisoesVisitadas = new String[jogador.getDivisoesVisitadas().size()];
        java.util.Iterator<Divisao> itDiv = jogador.getDivisoesVisitadas().iterator();
        int idx = 0;
        while (itDiv.hasNext()) {
            relJogador.divisoesVisitadas[idx++] = itDiv.next().getNome();
        }

        // Converter a lista de obstáculos ultrapassados
        relJogador.obstaculosUltrapassados = new String[jogador.getObstaculosUltrapassados().size()];
        java.util.Iterator<String> itObs = jogador.getObstaculosUltrapassados().iterator();
        idx = 0;
        while (itObs.hasNext()) {
            relJogador.obstaculosUltrapassados[idx++] = itObs.next();
        }

        // Converter a lista de efeitos aplicados
        relJogador.efeitosAplicados = new String[jogador.getEfeitosAplicados().size()];
        java.util.Iterator<String> itEf = jogador.getEfeitosAplicados().iterator();
        idx = 0;
        while (itEf.hasNext()) {
            relJogador.efeitosAplicados[idx++] = itEf.next();
        }

        jogadores.addToRear(relJogador);
    }

    /**
     * Gera o ficheiro JSON com o relatório completo da partida.
     * O ficheiro é criado com formatação legível (pretty print).
     *
     * @param nomeFicheiro O nome do ficheiro a criar (sem extensão)
     * @return true se o ficheiro foi criado com sucesso, false caso contrário
     */
    public boolean gerarArquivoJSON(String nomeFicheiro) {
        try {
            // Converter para a estrutura serializável
            RelatorioParaJSON relatorio = new RelatorioParaJSON();
            relatorio.dataHoraInicio = this.dataHoraInicio;
            relatorio.dataHoraFim = this.dataHoraFim;
            relatorio.vencedor = this.nomeVencedor;
            relatorio.totalTurnos = this.totalTurnos;

            // Converter a lista de jogadores
            relatorio.jogadores = new RelatorioJogador[jogadores.size()];
            java.util.Iterator<RelatorioJogador> it = jogadores.iterator();
            int idx = 0;
            while (it.hasNext()) {
                relatorio.jogadores[idx++] = it.next();
            }

            // Gerar o JSON com formatação
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(relatorio);

            // Guardar o ficheiro
            String caminhoCompleto = nomeFicheiro.endsWith(".json") ? nomeFicheiro : nomeFicheiro + ".json";
            FileWriter writer = new FileWriter(caminhoCompleto);
            writer.write(json);
            writer.close();

            System.out.println("\n✅ Relatório da partida gerado com sucesso!");
            System.out.println("📄 Ficheiro: " + caminhoCompleto);

            return true;

        } catch (IOException e) {
            System.err.println("\n❌ Erro ao gerar relatório JSON: " + e.getMessage());
            return false;
        }
    }

    /**
     * Classe interna que representa os dados de um jogador no relatório.
     */
    private static class RelatorioJogador {
        String nome;
        String posicaoFinal;
        int totalMovimentos;
        String[] divisoesVisitadas;
        String[] obstaculosUltrapassados;
        String[] efeitosAplicados;
    }

    /**
     * Classe interna que define a estrutura JSON final do relatório.
     */
    private static class RelatorioParaJSON {
        String dataHoraInicio;
        String dataHoraFim;
        String vencedor;
        int totalTurnos;
        RelatorioJogador[] jogadores;
    }
}

