// Pacote: Labirinto

package Labirinto;

import Eventos.*;

/**
 * Representa um vértice (nó) no Labirinto, que é um espaço
 * que pode conter um Desafio e tem um tipo específico.
 */
public class Divisao implements Comparable<Divisao> {
    private int id; // Usado para referenciar a Divisao na adjMatrix do Network
    private String nome;
    private String tipo; // Ex: "ENTRADA", "CENTRO", "ALAVANCA", "ENIGMA"
    private Desafio desafio; // Pode ser nulo se não houver desafio

    // Constantes para os tipos de divisão
    public static final String TIPO_ENTRADA = "Entrada";
    public static final String TIPO_CENTRO = "Centro";
    public static final String TIPO_ALAVANCA = "Alavanca";
    public static final String TIPO_ENIGMA = "Enigma";
    public static final String TIPO_SIMPLES = "Simples";

    /**
     * Construtor de Divisao.
     */
    public Divisao(int id, String nome, String tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.desafio = null;
    }

    // --- Getters e Setters ---

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setDesafio(Desafio desafio) {
        this.desafio = desafio;
    }

    public Desafio getDesafio() {
        return desafio;
    }

    /**
     * Retorna se a divisao tem um desafio pendente (e.g., alavanca não ativada).
     */
    public boolean temDesafioPendente() {
        // Implementação depende da classe Desafio
        return this.desafio != null && !this.desafio.estaCompleto();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Divisao divisao = (Divisao) obj;
        return id == divisao.id; // Assume que o ID é único
    }

    @Override
    public int compareTo(Divisao outra) {
        return Integer.compare(this.id, outra.id);
    }

    @Override
    public String toString() {
        return nome + " (" + tipo + ")";
    }
}