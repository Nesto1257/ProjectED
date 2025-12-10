package Eventos;

/**
 * Representa uma questão de enigma do jogo.
 * Cada questão contém uma pergunta, várias opções de resposta
 * e a resposta correta.
 * As questões são carregadas a partir do ficheiro enigmas.json.
 *
 * @author Grupo ED
 * @version 1.0
 */
public class QuestaoEnigma {

    /** O texto da pergunta */
    private String pergunta;

    /** As opções de resposta disponíveis */
    private String[] opcoes;

    /** A resposta correta */
    private String respostaCorreta;

    /**
     * Construtor da questão de enigma.
     *
     * @param pergunta O texto da pergunta
     * @param opcoes As opções de resposta disponíveis
     * @param respostaCorreta A resposta correta
     */
    public QuestaoEnigma(String pergunta, String[] opcoes, String respostaCorreta) {
        this.pergunta = pergunta;
        this.opcoes = opcoes;
        this.respostaCorreta = respostaCorreta;
    }

    /**
     * Verifica se a resposta do jogador está correta.
     * Aceita tanto o número da opção (1, 2, 3...) quanto o texto da resposta.
     * A comparação ignora diferenças entre maiúsculas e minúsculas.
     *
     * @param respostaJogador A resposta fornecida pelo jogador
     * @return true se a resposta está correta, false caso contrário
     */
    public boolean verificarResposta(String respostaJogador) {
        String respostaTrim = respostaJogador.trim();

        // Verificar se é o texto da resposta correta
        if (respostaTrim.equalsIgnoreCase(respostaCorreta.trim())) {
            return true;
        }

        // Verificar se é o número da opção
        try {
            int numeroEscolhido = Integer.parseInt(respostaTrim);

            // Verificar se o número é válido
            if (numeroEscolhido >= 1 && numeroEscolhido <= opcoes.length) {
                String opcaoEscolhida = opcoes[numeroEscolhido - 1];
                return opcaoEscolhida.trim().equalsIgnoreCase(respostaCorreta.trim());
            }
        } catch (NumberFormatException e) {
            // Não é um número
        }

        return false;
    }

    /**
     * Devolve uma representação textual da questão.
     * Inclui a pergunta e todas as opções numeradas.
     *
     * @return A questão formatada para apresentação
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Enigma: ").append(pergunta).append("\n");
        for (int i = 0; i < opcoes.length; i++) {
            sb.append("  ").append(i + 1).append(") ").append(opcoes[i]).append("\n");
        }
        return sb.toString();
    }
}