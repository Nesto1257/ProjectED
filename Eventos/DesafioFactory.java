package Eventos;

import Labirinto.TipoDivisao;
import Structures.ArrayUnorderedList;
import java.util.Random;

/**
 * Factory para criação de desafios.
 *
 * Padrão Factory Method:
 * - Encapsula a lógica de criação de objetos
 * - Facilita extensão com novos tipos de desafios
 * - Centraliza a criação para manutenção mais fácil
 * - Permite configuração flexível (ex: dificuldade)
 *
 * @author Grupo ED
 * @version 1.0
 */
public class DesafioFactory {

    private static final Random random = new Random();
    private final ArrayUnorderedList<QuestaoEnigma> questoesDisponiveis;

    /**
     * Construtor da factory de desafios.
     *
     * @param questoes Lista de questões de enigma disponíveis
     */
    public DesafioFactory(ArrayUnorderedList<QuestaoEnigma> questoes) {
        this.questoesDisponiveis = questoes;
    }

    /**
     * Cria um desafio baseado no tipo de divisão.
     *
     * @param tipoDivisao O tipo da divisão que requer o desafio
     * @return O desafio apropriado ou null se a divisão não requer desafio
     */
    public Desafio criarDesafio(TipoDivisao tipoDivisao) {
        if (tipoDivisao == null || !tipoDivisao.temDesafio()) {
            return null;
        }

        switch (tipoDivisao) {
            case ENIGMA:
                return criarDesafioEnigma();
            case ALAVANCA:
                return criarDesafioAlavanca();
            default:
                return null;
        }
    }

    /**
     * Cria um desafio baseado na string do tipo (compatibilidade).
     *
     * @param tipoString String do tipo de divisão
     * @return O desafio apropriado ou null
     */
    public Desafio criarDesafio(String tipoString) {
        return criarDesafio(TipoDivisao.fromString(tipoString));
    }

    /**
     * Cria um desafio de enigma com questões aleatórias.
     *
     * @return Novo DesafioEnigma
     */
    public Desafio criarDesafioEnigma() {
        if (questoesDisponiveis == null || questoesDisponiveis.isEmpty()) {
            // Criar questão padrão se não houver questões
            ArrayUnorderedList<QuestaoEnigma> questoesPadrao = new ArrayUnorderedList<>();
            questoesPadrao.addToRear(criarQuestaoPadrao());
            return new DesafioEnigma(questoesPadrao);
        }
        return new DesafioEnigma(questoesDisponiveis);
    }

    /**
     * Cria um desafio de alavanca com resposta aleatória.
     *
     * @return Novo DesafioAlavanca
     */
    public Desafio criarDesafioAlavanca() {
        int respostaCorreta = random.nextInt(2) + 1; // 1 ou 2
        String descricao = "🔧 Duas alavancas misteriosas: 1 (Esquerda) ou 2 (Direita).\n" +
                          "   Apenas uma abre o caminho. Escolha com sabedoria!";
        return new DesafioAlavanca(respostaCorreta, descricao);
    }

    /**
     * Cria uma questão padrão de fallback.
     *
     * @return Questão padrão
     */
    private QuestaoEnigma criarQuestaoPadrao() {
        String[] opcoes = {"Sim", "Não"};
        return new QuestaoEnigma("Deseja continuar?", opcoes, "Sim");
    }
}

