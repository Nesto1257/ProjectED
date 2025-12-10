package Jogo;

import Labirinto.Divisao;
import Structures.ArrayUnorderedList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Iterator;

/**
 * Janela Swing que apresenta as estatísticas do jogo em tempo real.
 * Mostra a posição de cada jogador, divisões visitadas, obstáculos
 * ultrapassados e efeitos em vigor.
 *
 * @author Grupo ED
 * @version 1.0
 */
public class EstatisticasJogo extends JFrame {

    /** Painel principal com scroll */
    private JPanel painelPrincipal;

    /** Painéis individuais para cada jogador */
    private JPanel[] paineisJogadores;

    /** Labels para mostrar informações de cada jogador */
    private JLabel[] labelsNome;
    private JLabel[] labelsPosicao;
    private JTextArea[] areasVisitadas;
    private JTextArea[] areasObstaculos;
    private JTextArea[] areasEfeitos;

    /** Label para mostrar o turno atual */
    private JLabel labelTurno;

    /** Número de jogadores */
    private int numJogadores;

    /**
     * Construtor da janela de estatísticas.
     *
     * @param jogadores Lista de jogadores do jogo
     */
    public EstatisticasJogo(ArrayUnorderedList<Jogador> jogadores) {
        super("📊 Estatísticas do Jogo - Labirinto da Glória");

        this.numJogadores = jogadores.size();

        // Configurar janela
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(true);

        // Inicializar arrays
        paineisJogadores = new JPanel[numJogadores];
        labelsNome = new JLabel[numJogadores];
        labelsPosicao = new JLabel[numJogadores];
        areasVisitadas = new JTextArea[numJogadores];
        areasObstaculos = new JTextArea[numJogadores];
        areasEfeitos = new JTextArea[numJogadores];

        // Criar interface
        criarInterface(jogadores);

        // Mostrar janela
        setVisible(true);
    }

    /**
     * Cria a interface gráfica da janela.
     *
     * @param jogadores Lista de jogadores
     */
    private void criarInterface(ArrayUnorderedList<Jogador> jogadores) {
        // Painel principal com BorderLayout
        JPanel containerPrincipal = new JPanel(new BorderLayout(10, 10));
        containerPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        containerPrincipal.setBackground(new Color(40, 44, 52));

        // Cabeçalho com turno
        JPanel painelCabecalho = criarPainelCabecalho();
        containerPrincipal.add(painelCabecalho, BorderLayout.NORTH);

        // Painel central com jogadores (2x2 grid)
        painelPrincipal = new JPanel(new GridLayout(2, 2, 10, 10));
        painelPrincipal.setBackground(new Color(40, 44, 52));

        // Criar painel para cada jogador
        int index = 0;
        Iterator<Jogador> it = jogadores.iterator();
        while (it.hasNext()) {
            Jogador jogador = it.next();
            JPanel painelJogador = criarPainelJogador(jogador, index);
            paineisJogadores[index] = painelJogador;
            painelPrincipal.add(painelJogador);
            index++;
        }

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(painelPrincipal);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(40, 44, 52));
        containerPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Legenda
        JPanel painelLegenda = criarPainelLegenda();
        containerPrincipal.add(painelLegenda, BorderLayout.SOUTH);

        setContentPane(containerPrincipal);
    }

    /**
     * Cria o painel do cabeçalho com informação do turno.
     *
     * @return Painel do cabeçalho
     */
    private JPanel criarPainelCabecalho() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painel.setBackground(new Color(30, 33, 39));
        painel.setBorder(new EmptyBorder(10, 10, 10, 10));

        labelTurno = new JLabel("🎮 TURNO: 0");
        labelTurno.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
        labelTurno.setForeground(new Color(255, 215, 0));

        painel.add(labelTurno);
        return painel;
    }

    /**
     * Cria o painel de um jogador individual.
     *
     * @param jogador O jogador
     * @param index Índice do jogador
     * @return Painel do jogador
     */
    private JPanel criarPainelJogador(Jogador jogador, int index) {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(new Color(50, 54, 62));
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(getCorJogador(index), 2),
            new EmptyBorder(10, 10, 10, 10)
        ));

        // Nome do jogador
        labelsNome[index] = new JLabel(jogador.getNome());
        labelsNome[index].setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        labelsNome[index].setForeground(getCorJogador(index));
        labelsNome[index].setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.add(labelsNome[index]);

        painel.add(Box.createVerticalStrut(5));

        // Posição atual
        labelsPosicao[index] = new JLabel("📍 Posição: " + jogador.getPosicaoAtual().getNome());
        labelsPosicao[index].setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        labelsPosicao[index].setForeground(Color.WHITE);
        labelsPosicao[index].setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.add(labelsPosicao[index]);

        painel.add(Box.createVerticalStrut(10));

        // Divisões visitadas
        areasVisitadas[index] = criarTextArea();
        painel.add(criarPainelComTitulo("Visitadas", areasVisitadas[index]));

        painel.add(Box.createVerticalStrut(5));

        // Obstáculos ultrapassados
        areasObstaculos[index] = criarTextArea();
        painel.add(criarPainelComTitulo("Obstáculos", areasObstaculos[index]));

        painel.add(Box.createVerticalStrut(5));

        // Efeitos aplicados
        areasEfeitos[index] = criarTextArea();
        painel.add(criarPainelComTitulo("Efeitos", areasEfeitos[index]));

        return painel;
    }

    /**
     * Cria uma área de texto estilizada.
     *
     * @return TextArea configurada
     */
    private JTextArea criarTextArea() {
        JTextArea area = new JTextArea(3, 20);
        area.setEditable(false);
        area.setFont(new Font("Consolas", Font.PLAIN, 11));
        area.setBackground(new Color(35, 38, 45));
        area.setForeground(new Color(200, 200, 200));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    /**
     * Cria um painel com título para envolver uma área de texto.
     *
     * @param titulo Título do painel
     * @param area Área de texto
     * @return Painel com scroll
     */
    private JPanel criarPainelComTitulo(String titulo, JTextArea area) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(50, 54, 62));
        painel.setAlignmentX(Component.LEFT_ALIGNMENT);

        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(80, 84, 92)),
            titulo
        );
        border.setTitleColor(new Color(150, 150, 150));
        border.setTitleFont(new Font("Segoe UI", Font.PLAIN, 10));
        painel.setBorder(border);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(null);
        scroll.setPreferredSize(new Dimension(150, 60));
        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    /**
     * Cria o painel de legenda.
     *
     * @return Painel de legenda
     */
    private JPanel criarPainelLegenda() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        painel.setBackground(new Color(30, 33, 39));
        painel.setBorder(new EmptyBorder(5, 5, 5, 5));

        String[] legendas = {"🏆 Centro = Vitória", "❓ Enigma", "🔧 Alavanca", "🚪 Entrada"};
        for (String legenda : legendas) {
            JLabel label = new JLabel(legenda);
            label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 11));
            label.setForeground(new Color(150, 150, 150));
            painel.add(label);
        }

        return painel;
    }

    /**
     * Devolve a cor associada a um jogador.
     *
     * @param index Índice do jogador
     * @return Cor do jogador
     */
    private Color getCorJogador(int index) {
        Color[] cores = {
            new Color(231, 76, 60),   // Vermelho
            new Color(52, 152, 219),  // Azul
            new Color(46, 204, 113),  // Verde
            new Color(241, 196, 15)   // Amarelo
        };
        return cores[index % cores.length];
    }

    /**
     * Atualiza as estatísticas de todos os jogadores.
     *
     * @param jogadores Lista de jogadores
     * @param turnoAtual Número do turno atual
     */
    public void atualizarEstatisticas(ArrayUnorderedList<Jogador> jogadores, int turnoAtual) {
        SwingUtilities.invokeLater(() -> {
            // Atualizar turno
            labelTurno.setText("🎮 TURNO: " + turnoAtual);

            // Atualizar cada jogador
            int index = 0;
            Iterator<Jogador> it = jogadores.iterator();
            while (it.hasNext()) {
                Jogador jogador = it.next();
                atualizarPainelJogador(jogador, index);
                index++;
            }

            // Forçar repaint
            repaint();
        });
    }

    /**
     * Atualiza o painel de um jogador específico.
     *
     * @param jogador O jogador
     * @param index Índice do jogador
     */
    private void atualizarPainelJogador(Jogador jogador, int index) {
        // Posição atual
        labelsPosicao[index].setText("📍 Posição: " + jogador.getPosicaoAtual().getNome());

        // Divisões visitadas
        StringBuilder sbVisitadas = new StringBuilder();
        Iterator<Divisao> itDiv = jogador.getDivisoesVisitadas().iterator();
        while (itDiv.hasNext()) {
            sbVisitadas.append("• ").append(itDiv.next().getNome()).append("\n");
        }
        areasVisitadas[index].setText(sbVisitadas.toString());

        // Obstáculos ultrapassados
        StringBuilder sbObstaculos = new StringBuilder();
        Iterator<String> itObs = jogador.getObstaculosUltrapassados().iterator();
        while (itObs.hasNext()) {
            sbObstaculos.append("✓ ").append(itObs.next()).append("\n");
        }
        if (sbObstaculos.length() == 0) {
            sbObstaculos.append("Nenhum ainda");
        }
        areasObstaculos[index].setText(sbObstaculos.toString());

        // Efeitos aplicados
        StringBuilder sbEfeitos = new StringBuilder();
        Iterator<String> itEf = jogador.getEfeitosAplicados().iterator();
        while (itEf.hasNext()) {
            sbEfeitos.append("⚡ ").append(itEf.next()).append("\n");
        }
        if (jogador.getTurnosImpedido() > 0) {
            sbEfeitos.append("🚫 Impedido: ").append(jogador.getTurnosImpedido()).append(" turnos\n");
        }
        if (jogador.getJogadasExtra() > 0) {
            sbEfeitos.append("🎯 Jogadas extra: ").append(jogador.getJogadasExtra()).append("\n");
        }
        if (sbEfeitos.length() == 0) {
            sbEfeitos.append("Nenhum");
        }
        areasEfeitos[index].setText(sbEfeitos.toString());

        // Destacar jogador atual (borda mais grossa)
        paineisJogadores[index].repaint();
    }

    /**
     * Destaca o jogador que está a jogar.
     *
     * @param jogadorAtual O jogador atual
     * @param jogadores Lista de todos os jogadores
     */
    public void destacarJogadorAtual(Jogador jogadorAtual, ArrayUnorderedList<Jogador> jogadores) {
        SwingUtilities.invokeLater(() -> {
            int index = 0;
            Iterator<Jogador> it = jogadores.iterator();
            while (it.hasNext()) {
                Jogador j = it.next();
                if (j == jogadorAtual) {
                    // Destacar com borda mais grossa e brilhante
                    paineisJogadores[index].setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(getCorJogador(index), 4),
                        new EmptyBorder(10, 10, 10, 10)
                    ));
                    labelsNome[index].setText("▶ " + j.getNome() + " (A JOGAR)");
                } else {
                    // Borda normal
                    paineisJogadores[index].setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(getCorJogador(index), 2),
                        new EmptyBorder(10, 10, 10, 10)
                    ));
                    labelsNome[index].setText(j.getNome());
                }
                index++;
            }
        });
    }

    /**
     * Mostra mensagem de vitória.
     *
     * @param vencedor O jogador vencedor
     */
    public void mostrarVitoria(Jogador vencedor) {
        SwingUtilities.invokeLater(() -> {
            labelTurno.setText("🏆 VENCEDOR: " + vencedor.getNome() + " 🏆");
            labelTurno.setForeground(new Color(46, 204, 113));

            JOptionPane.showMessageDialog(
                this,
                "🎉 " + vencedor.getNome() + " conquistou o tesouro!\n\n" +
                "Total de movimentos: " + vencedor.getTotalMovimentos() + "\n" +
                "Obstáculos ultrapassados: " + vencedor.getObstaculosUltrapassados().size(),
                "🏆 Vitória!",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
    }

    /**
     * Fecha a janela de estatísticas.
     */
    public void fechar() {
        SwingUtilities.invokeLater(() -> {
            setVisible(false);
            dispose();
        });
    }
}

