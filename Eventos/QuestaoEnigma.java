// Pacote: Eventos

package Eventos;

/**
 * Representa uma única pergunta de Enigma carregada do ficheiro JSON.
 */
public class QuestaoEnigma {
    private String pergunta;
    private String[] opcoes; // Possíveis respostas
    private String respostaCorreta;

    public QuestaoEnigma(String pergunta, String[] opcoes, String respostaCorreta) {
        this.pergunta = pergunta;
        this.opcoes = opcoes;
        this.respostaCorreta = respostaCorreta;
    }

    // --- Getters ---

    public String getPergunta() {
        return pergunta;
    }

    public String[] getOpcoes() {
        return opcoes;
    }

    /**
     * Verifica se a resposta do jogador está correta.
     * Aceita tanto o número da opção (1, 2, 3...) quanto o texto da resposta.
     */
    public boolean verificarResposta(String respostaJogador) {
        String respostaTrim = respostaJogador.trim();

        // Verificar se é o texto da resposta correta (ignora maiúsculas/minúsculas)
        if (respostaTrim.equalsIgnoreCase(respostaCorreta.trim())) {
            return true;
        }

        // Verificar se é o número da opção
        try {
            int numeroEscolhido = Integer.parseInt(respostaTrim);

            // Verificar se o número é válido (1 a opcoes.length)
            if (numeroEscolhido >= 1 && numeroEscolhido <= opcoes.length) {
                // Comparar a opção escolhida com a resposta correta
                String opcaoEscolhida = opcoes[numeroEscolhido - 1]; // -1 porque array começa em 0
                return opcaoEscolhida.trim().equalsIgnoreCase(respostaCorreta.trim());
            }
        } catch (NumberFormatException e) {
            // Não é um número, continuar para retornar false
        }

        return false;
    }

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