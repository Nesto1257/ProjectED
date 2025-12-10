package Eventos;

import Structures.ArrayUnorderedList;
import java.util.Iterator;
import java.util.Random;

/**
 * Implementação de um desafio de enigma.
 * O jogador deve responder corretamente a uma pergunta para desbloquear a passagem.
 * As questões são selecionadas aleatoriamente e só repetem quando todas
 * as questões disponíveis já tiverem sido utilizadas.
 *
 * @author Grupo ED
 * @version 1.0
 */
public class DesafioEnigma implements Desafio {

    /** Indica se o desafio foi completado */
    private boolean completo;

    /** Lista com todas as questões disponíveis */
    private final ArrayUnorderedList<QuestaoEnigma> todasQuestoes;

    /** Lista de questões ainda não utilizadas */
    private ArrayUnorderedList<QuestaoEnigma> questoesDisponiveis;

    /** A questão atualmente apresentada */
    private QuestaoEnigma questaoAtual;

    /** Gerador de números aleatórios */
    private final Random randomGenerator;

    /**
     * Construtor do desafio de enigma.
     *
     * @param questoes A lista de todas as questões disponíveis
     */
    public DesafioEnigma(ArrayUnorderedList<QuestaoEnigma> questoes) {
        this.completo = false;
        this.todasQuestoes = questoes;
        this.randomGenerator = new Random();

        // Inicializar a lista de questões disponíveis
        this.questoesDisponiveis = copiarQuestoes(todasQuestoes);

        if (!todasQuestoes.isEmpty()) {
            selecionarNovaQuestao();
        } else {
            this.questaoAtual = null;
        }
    }

    /**
     * Cria uma cópia da lista de questões.
     *
     * @param original A lista original
     * @return Uma nova lista com as mesmas questões
     */
    private ArrayUnorderedList<QuestaoEnigma> copiarQuestoes(ArrayUnorderedList<QuestaoEnigma> original) {
        ArrayUnorderedList<QuestaoEnigma> copia = new ArrayUnorderedList<>();
        Iterator<QuestaoEnigma> it = original.iterator();
        while (it.hasNext()) {
            copia.addToRear(it.next());
        }
        return copia;
    }

    /**
     * Seleciona uma nova questão aleatoriamente.
     * Quando todas as questões foram utilizadas, recicla a lista completa.
     */
    private void selecionarNovaQuestao() {
        // Reciclar se não houver questões disponíveis
        if (questoesDisponiveis.isEmpty()) {
            questoesDisponiveis = copiarQuestoes(todasQuestoes);
        }

        if (!questoesDisponiveis.isEmpty()) {
            // Selecionar índice aleatório
            int indiceAleatorio = randomGenerator.nextInt(questoesDisponiveis.size());

            // Obter a questão
            this.questaoAtual = questoesDisponiveis.get(indiceAleatorio);

            // Remover da lista de disponíveis
            try {
                questoesDisponiveis.remove(questaoAtual);
            } catch (Exception e) {
                // Questão já foi removida ou não existe - continuar normalmente
                System.err.println("Aviso: Não foi possível remover questão da lista: " + e.getMessage());
            }
        } else {
            this.questaoAtual = null;
        }
    }

    /**
     * Tenta resolver o enigma com a resposta fornecida.
     *
     * @param input A resposta do jogador
     * @return true se a resposta está correta, false caso contrário
     */
    @Override
    public boolean tentarResolucao(String input) {
        if (completo) return true;

        if (questaoAtual != null && questaoAtual.verificarResposta(input)) {
            this.completo = true;
            return true;
        } else {
            // Resposta errada: selecionar nova questão
            selecionarNovaQuestao();
            return false;
        }
    }

    /**
     * Verifica se o desafio foi completado.
     *
     * @return true se o enigma foi resolvido
     */
    @Override
    public boolean estaCompleto() {
        return completo;
    }

    /**
     * Obtém a descrição do desafio com a questão atual.
     *
     * @return A descrição formatada do enigma
     */
    @Override
    public String getDescricao() {
        if (completo) {
            return "O Enigma foi resolvido. A passagem está livre.";
        } else if (questaoAtual != null) {
            return "Desafio de Enigma. Responda corretamente para continuar:\n" + questaoAtual;
        } else {
            return "Desafio de Enigma sem questões disponíveis.";
        }
    }
}