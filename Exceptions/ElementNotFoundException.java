package Exceptions;

/**
 * Exceção lançada quando um elemento procurado não é encontrado numa coleção.
 * Estende {@code RuntimeException} para permitir que seja uma exceção não verificada (unchecked).
 * * @author Ernesto Guilherme
 * @version 1.0
 */
public class ElementNotFoundException extends RuntimeException
{
    /**
     * Cria uma nova instância de ElementNotFoundException.
     * * @param collection Uma 'string' que representa o tipo de coleção onde a pesquisa falhou (ex: "Array", "Lista Ligada").
     */
    public ElementNotFoundException (String collection)
    {
        super ("The target element is not in this " + collection);
    }
}