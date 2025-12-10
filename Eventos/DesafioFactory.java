package Eventos;

import Structures.ArrayUnorderedList;
import java.util.Random;

/**
 * Fábrica para criação de desafios do jogo.
 * Implementa o padrão Factory Method para encapsular a lógica de criação
 * dos diferentes tipos de desafios.
 * Tipos de desafios suportados:
 * <ul>
 *   <li>Desafio de Enigma - Perguntas com múltiplas opções</li>
 *   <li>Desafio de Alavanca - Escolha entre duas opções</li>
 * </ul>
 *
 * @author Grupo ED
 * @version 1.0
 */
public class DesafioFactory {

    /** Gerador de números aleatórios */
    private static final Random random = new Random();

    /** Lista de questões disponíveis para os enigmas */
    private final ArrayUnorderedList<QuestaoEnigma> questoesDisponiveis;

    /**
     * Construtor da fábrica de desafios.
     *
     * @param questoes A lista de questões de enigma disponíveis
     */
    public DesafioFactory(ArrayUnorderedList<QuestaoEnigma> questoes) {
        this.questoesDisponiveis = questoes;
    }

    /**
     * Cria um desafio de enigma.
     * Utiliza as questões disponíveis carregadas do ficheiro JSON.
     * Se não existirem questões, cria uma questão padrão.
     *
     * @return Um novo desafio de enigma
     */
    public Desafio criarDesafioEnigma() {
        if (questoesDisponiveis == null || questoesDisponiveis.isEmpty()) {
            ArrayUnorderedList<QuestaoEnigma> questoesPadrao = new ArrayUnorderedList<>();
            questoesPadrao.addToRear(criarQuestaoPadrao());
            return new DesafioEnigma(questoesPadrao);
        }
        return new DesafioEnigma(questoesDisponiveis);
    }

    /**
     * Cria um desafio de alavanca.
     * A resposta correta é gerada aleatoriamente (1 ou 2).
     *
     * @return Um novo desafio de alavanca
     */
    public Desafio criarDesafioAlavanca() {
        int respostaCorreta = random.nextInt(2) + 1;
        String descricao = "🔧 Duas alavancas misteriosas: 1 (Esquerda) ou 2 (Direita).\n" +
                          "   Apenas uma abre o caminho. Escolha com sabedoria!";
        return new DesafioAlavanca(respostaCorreta, descricao);
    }

    /**
     * Cria uma questão de enigma padrão.
     * Utilizada como fallback quando não existem questões carregadas.
     *
     * @return Uma questão padrão
     */
    private QuestaoEnigma criarQuestaoPadrao() {
        String[] opcoes = {"Sim", "Não"};
        return new QuestaoEnigma("Deseja continuar?", opcoes, "Sim");
    }
}

