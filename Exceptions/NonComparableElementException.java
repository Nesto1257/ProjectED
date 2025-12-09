package Exceptions;

/**
 * Exceção lançada quando uma coleção ou estrutura de dados que exige que os seus
 * elementos sejam comparáveis (implementando a interface {@code Comparable}) é
 * usada com elementos que não a implementam.
 * Estende {@code RuntimeException} para ser uma exceção não verificada (unchecked).
 * * @author Ernesto Guilherme
 * @version 1.0
 */
public class NonComparableElementException extends RuntimeException
{
    /**
     * Cria uma instância de {@code NonComparableElementException} com uma mensagem
     * de detalhe que indica o tipo de coleção que requer elementos comparáveis.
     * * @param collection Uma 'string' que representa o nome da coleção ou estrutura
     * de dados que exige a comparabilidade (ex: "Heap", "Árvore Binária de Pesquisa").
     */
    public NonComparableElementException (String collection)
    {
        super ("The " + collection + " requires comparable elements.");
    }
}