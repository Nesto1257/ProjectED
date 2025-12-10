package Labirinto;

import Eventos.*;

/**
 * Representa uma divisão (vértice) no labirinto.
 * Cada divisão é um espaço no labirinto que pode conter um desafio
 * (enigma ou alavanca) e possui um tipo específico.
 *
 * A divisão implementa {@link Comparable} para permitir ordenação
 * e utilização em estruturas de dados ordenadas.
 *
 * @author Grupo ED
 * @version 1.0
 */
public class Divisao implements Comparable<Divisao> {

    /** Identificador único da divisão */
    private int id;

    /** Nome descritivo da divisão */
    private String nome;

    /** Tipo da divisão (entrada, centro, enigma, alavanca, simples) */
    private TipoDivisao tipo;

    /** Desafio associado à divisão (pode ser null) */
    private Desafio desafio;


    /**
     * Construtor da divisão utilizando o enum TipoDivisao.
     *
     * @param id O identificador único da divisão
     * @param nome O nome descritivo da divisão
     * @param tipo O tipo da divisão como enum
     */
    public Divisao(int id, String nome, TipoDivisao tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.desafio = null;
    }


    /**
     * Obtém o identificador da divisão.
     *
     * @return O ID da divisão
     */
    public int getId() {
        return id;
    }

    /**
     * Obtém o nome da divisão.
     *
     * @return O nome da divisão
     */
    public String getNome() {
        return nome;
    }

    /**
     * Obtém o tipo da divisão como enum.
     *
     * @return O tipo da divisão como TipoDivisao
     */
    public TipoDivisao getTipoEnum() {
        return tipo;
    }

    /**
     * Obtém o tipo da divisão como String.
     *
     * @return O nome do tipo da divisão
     */
    public String getTipo() {
        return tipo.getNome();
    }

    /**
     * Define o desafio associado a esta divisão.
     *
     * @param desafio O desafio a associar
     */
    public void setDesafio(Desafio desafio) {
        this.desafio = desafio;
    }

    /**
     * Obtém o desafio associado à divisão.
     *
     * @return O desafio, ou null se não existir
     */
    public Desafio getDesafio() {
        return desafio;
    }

    /**
     * Verifica se a divisão tem um desafio pendente por resolver.
     *
     * @return true se existir um desafio não completado, false caso contrário
     */
    public boolean temDesafioPendente() {
        return this.desafio != null && !this.desafio.estaCompleto();
    }

    /**
     * Compara duas divisões pela igualdade.
     * Duas divisões são iguais se tiverem o mesmo ID.
     *
     * @param obj O objeto a comparar
     * @return true se forem iguais, false caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Divisao divisao = (Divisao) obj;
        return id == divisao.id;
    }

    /**
     * Compara esta divisão com outra para ordenação.
     *
     * @param outra A outra divisão a comparar
     * @return Valor negativo, zero ou positivo conforme a ordem
     */
    @Override
    public int compareTo(Divisao outra) {
        return Integer.compare(this.id, outra.id);
    }

    /**
     * Devolve uma representação textual da divisão.
     *
     * @return O nome e tipo da divisão
     */
    @Override
    public String toString() {
        return nome + " (" + tipo + ")";
    }
}