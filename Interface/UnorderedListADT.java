package Interface;

/**
 * Define a interface para uma Lista Não Ordenada (Unordered List).
 * Este é um Contrato de Tipo de Dados Abstrato (ADT) que estende a {@code ListADT}
 * e providencia métodos para adicionar elementos em posições específicas (frente, fim, ou após um alvo).
 * * @author Ernesto
 * @author Guilherme
 * @version 1.0
 */
public interface UnorderedListADT<T> extends ListADT<T>
{
    /**
     * Adiciona o elemento especificado à frente (início) desta lista.
     * * @param element O elemento a ser adicionado.
     */
    public void addToFront (T element);

    /**
     * Adiciona o elemento especificado à parte de trás (fim) desta lista.
     * * @param element O elemento a ser adicionado.
     */
    public void addToRear (T element);

    /**
     * Adiciona o elemento especificado imediatamente após a primeira ocorrência do
     * elemento alvo especificado.
     * Lança uma {@code ElementNotFoundException} se o alvo não for encontrado.
     * * @param element O elemento a ser adicionado.
     * @param target O elemento após o qual o novo elemento será inserido.
     */
    public void addAfter (T element, T target);
}