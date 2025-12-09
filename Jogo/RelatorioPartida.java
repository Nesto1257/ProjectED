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
 * Registra o percurso completo de cada jogador, obstáculos enfrentados,
 * enigmas resolvidos e efeitos aplicados durante o jogo.
 *
 * @author Grupo ED
 * @version 1.0
 */
public class RelatorioPartida {

    private String dataHoraInicio;
    private String dataHoraFim;
    private String nomeVencedor;
    private ArrayUnorderedList<RelatorioJogador> jogadores;
    private int totalTurnos;

    /**
     * Construtor do relatório de partida.
     */
    public RelatorioPartida() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dataHoraInicio = LocalDateTime.now().format(formatter);
        this.jogadores = new ArrayUnorderedList<>();
        this.totalTurnos = 0;
    }

    /**
     * Registra o início da partida.
     */
    public void registrarInicio() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dataHoraInicio = LocalDateTime.now().format(formatter);
    }

    /**
     * Registra o fim da partida.
     * @param vencedor O jogador vencedor
     * @param turnos Total de turnos jogados
     */
    public void registrarFim(Jogador vencedor, int turnos) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dataHoraFim = LocalDateTime.now().format(formatter);
        this.nomeVencedor = vencedor != null ? vencedor.getNome() : "Nenhum";
        this.totalTurnos = turnos;
    }

    /**
     * Adiciona o relatório de um jogador.
     * @param jogador O jogador a ser registrado
     */
    public void adicionarJogador(Jogador jogador) {
        RelatorioJogador relJogador = new RelatorioJogador();
        relJogador.nome = jogador.getNome();
        relJogador.posicaoFinal = jogador.getPosicaoAtual().getNome();
        relJogador.totalMovimentos = jogador.getTotalMovimentos();

        // Converter divisões visitadas
        relJogador.divisoesVisitadas = new String[jogador.getDivisoesVisitadas().size()];
        java.util.Iterator<Divisao> itDiv = jogador.getDivisoesVisitadas().iterator();
        int idx = 0;
        while (itDiv.hasNext()) {
            relJogador.divisoesVisitadas[idx++] = itDiv.next().getNome();
        }

        // Converter obstáculos ultrapassados
        relJogador.obstaculosUltrapassados = new String[jogador.getObstaculosUltrapassados().size()];
        java.util.Iterator<String> itObs = jogador.getObstaculosUltrapassados().iterator();
        idx = 0;
        while (itObs.hasNext()) {
            relJogador.obstaculosUltrapassados[idx++] = itObs.next();
        }

        // Converter efeitos aplicados
        relJogador.efeitosAplicados = new String[jogador.getEfeitosAplicados().size()];
        java.util.Iterator<String> itEf = jogador.getEfeitosAplicados().iterator();
        idx = 0;
        while (itEf.hasNext()) {
            relJogador.efeitosAplicados[idx++] = itEf.next();
        }

        jogadores.addToRear(relJogador);
    }

    /**
     * Gera o arquivo JSON com o relatório completo da partida.
     * @param nomeArquivo Nome do arquivo a ser gerado (sem extensão)
     * @return true se o arquivo foi gerado com sucesso, false caso contrário
     */
    public boolean gerarArquivoJSON(String nomeArquivo) {
        try {
            // Converter para estrutura serializável
            RelatorioParaJSON relatorio = new RelatorioParaJSON();
            relatorio.dataHoraInicio = this.dataHoraInicio;
            relatorio.dataHoraFim = this.dataHoraFim;
            relatorio.vencedor = this.nomeVencedor;
            relatorio.totalTurnos = this.totalTurnos;

            // Converter jogadores
            relatorio.jogadores = new RelatorioJogador[jogadores.size()];
            java.util.Iterator<RelatorioJogador> it = jogadores.iterator();
            int idx = 0;
            while (it.hasNext()) {
                relatorio.jogadores[idx++] = it.next();
            }

            // Gerar JSON com formatação bonita
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(relatorio);

            // Salvar arquivo
            String caminhoCompleto = nomeArquivo.endsWith(".json") ? nomeArquivo : nomeArquivo + ".json";
            FileWriter writer = new FileWriter(caminhoCompleto);
            writer.write(json);
            writer.close();

            System.out.println("\n✅ Relatório da partida gerado com sucesso!");
            System.out.println("📄 Arquivo: " + caminhoCompleto);

            return true;

        } catch (IOException e) {
            System.err.println("\n❌ Erro ao gerar relatório JSON: " + e.getMessage());
            return false;
        }
    }

    /**
     * Classe interna para representar os dados de um jogador no relatório.
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
     * Classe interna para estrutura JSON final.
     */
    private static class RelatorioParaJSON {
        String dataHoraInicio;
        String dataHoraFim;
        String vencedor;
        int totalTurnos;
        RelatorioJogador[] jogadores;
    }
}

