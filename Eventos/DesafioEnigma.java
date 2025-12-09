// Pacote: Eventos

package Eventos;

import Structures.ArrayUnorderedList;
import java.util.Iterator;
import java.util.Random;

/**
 * Implementa o Desafio de Enigma, usando questões selecionadas de forma aleatória.
 *
 * Estrutura de dados otimizada: A ArrayUnorderedList original é usada para
 * acesso aleatório O(1) (implícito, se o ArrayUnorderedList for baseado em array).
 *
 * @author Grupo ED
 * @version 2.1 (Com seleção aleatória)
 */
public class DesafioEnigma implements Desafio {
    private boolean completo;
    private final ArrayUnorderedList<QuestaoEnigma> todasQuestoes; // Armazena todas as questões
    private QuestaoEnigma questaoAtual;
    private final Random randomGenerator; // Gerador de números aleatórios

    /**
     * Construtor do DesafioEnigma.
     *
     * @param questoes A lista inicial de todas as questões carregadas (e.g., do JSON).
     */
    public DesafioEnigma(ArrayUnorderedList<QuestaoEnigma> questoes) {
        this.completo = false;
        this.todasQuestoes = questoes; // Guardar a lista completa
        this.randomGenerator = new Random();

        // Se a lista estiver vazia, não há questão para selecionar, mas a lógica
        // de seleção de nova questão será executada se for necessário.
        if (todasQuestoes.size() > 0) {
            selecionarNovaQuestao();
        } else {
            this.questaoAtual = null;
        }
    }

    /**
     * Seleciona a próxima questão de forma **aleatória** da lista de todas as questões.
     * Operação O(1) (assumindo ArrayUnorderedList usa array ou ArrayList por baixo).
     */
    private void selecionarNovaQuestao() {
        if (todasQuestoes.size() > 0) {
            // Gerar um índice aleatório entre 0 (inclusive) e o tamanho da lista (exclusive)
            int indiceAleatorio = randomGenerator.nextInt(todasQuestoes.size());

            // Acesso à questão pelo índice. Se a ArrayUnorderedList for como um ArrayList,
            // esta é a forma mais eficiente de acesso aleatório.
            this.questaoAtual = todasQuestoes.get(indiceAleatorio);
        } else {
            this.questaoAtual = null;
        }
    }

    @Override
    public boolean tentarResolucao(String input) {
        if (completo) return true;

        if (questaoAtual != null && questaoAtual.verificarResposta(input)) {
            this.completo = true; // Desafio resolvido
            return true;
        } else {
            // Resposta errada: **selecionar uma nova questão aleatória** para a próxima tentativa.
            // Se o jogador falhar, ele deve enfrentar um enigma diferente (aleatório).
            selecionarNovaQuestao();
            return false;
        }
    }

    @Override
    public boolean estaCompleto() {
        return completo;
    }

    @Override
    public String getDescricao() {
        if (completo) {
            return "O Enigma foi resolvido. A passagem está livre.";
        } else if (questaoAtual != null) {
            return "Desafio de Enigma. Responda corretamente para continuar:\n" + questaoAtual.toString();
        } else {
            return "Desafio de Enigma sem questões disponíveis.";
        }
    }

    // NOTA: É necessário garantir que a sua implementação de ArrayUnorderedList
    // tenha um método `get(int index)` ou similar para aceder ao elemento pelo índice.
}