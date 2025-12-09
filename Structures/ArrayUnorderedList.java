package Structures;


import Exceptions.ElementNotFoundException;
import Interface.UnorderedListADT;

/**
 * Representa uma implementação de uma lista linear não ordenada (Unordered List)
 * usando um array redimensionável.
 * Estende {@code ArrayList} e implementa a interface {@code UnorderedListADT},
 * providenciando métodos para adição de elementos em posições específicas.
 *
 * @param <T> O tipo de elementos armazenados na lista.
 * @author Ernesto
 * @author Guilherme
 *
 * @version 1.0
 */
public class ArrayUnorderedList<T> extends ArrayList<T>
        implements UnorderedListADT<T>
{

    /**
     * Cria uma lista não ordenada vazia usando a capacidade padrão (100).
     */
    public ArrayUnorderedList()
    {
        super();
    }

    /**
     * Cria uma lista não ordenada vazia usando a capacidade inicial especificada.
     * * @param initialCapacity A capacidade inicial do array subjacente.
     */
    public ArrayUnorderedList (int initialCapacity)
    {
        super(initialCapacity);
    }

    /**
     * Adiciona o elemento especificado à frente (início) desta lista.
     * Se necessário, a capacidade do array é expandida e os elementos são deslocados.
     *
     * @param element O elemento a ser adicionado à lista.
     */
    @Override
    public void addToFront (T element)
    {
        if (size() == list.length)
            expandCapacity();

        // Desloca os elementos para abrir espaço na posição 0
        for (int scan=rear; scan > 0; scan--)
            list[scan] = list[scan-1];

        list[0] = element;
        rear++;
    }

    /**
     * Adiciona o elemento especificado à parte de trás (fim) desta lista.
     * Se necessário, a capacidade do array é expandida.
     *
     * @param element O elemento a ser adicionado à lista.
     */
    @Override
    public void addToRear (T element)
    {
        if (size() == list.length)
            expandCapacity();

        list[rear] = element;
        rear++;
    }

    /**
     * Adiciona o elemento especificado imediatamente após a primeira ocorrência do
     * elemento alvo especificado.
     *
     * @param element O elemento a ser adicionado à lista.
     * @param target O elemento após o qual o novo elemento será inserido.
     * @throws ElementNotFoundException Se o elemento alvo não for encontrado.
     */
    @Override
    public void addAfter (T element, T target)
    {
        if (size() == list.length)
            expandCapacity();

        // 1. Encontrar o elemento alvo
        int scan = 0;
        while (scan < rear && !target.equals(list[scan]))
            scan++;

        if (scan == rear)
            throw new ElementNotFoundException("lista");

        // scan aponta para o elemento alvo (target).
        // Queremos inserir APÓS scan, ou seja, na posição scan + 1.
        scan++;

        // 2. Deslocar elementos para abrir espaço na posição scan
        for (int scan2=rear; scan2 > scan; scan2--)
            list[scan2] = list[scan2-1];

        // 3. Inserir o elemento
        list[scan] = element;
        rear++;
    }

    public T get(int index)
    {
        if (index < 0 || index >= rear) {
            // Se você não tiver uma classe IndexOutOfBoundsException customizada,
            // use a nativa do Java: java.lang.IndexOutOfBoundsException
            throw new java.lang.IndexOutOfBoundsException("Índice fora dos limites: " + index);
        }

        // 'list' e 'rear' são campos protected da ArrayList
        return list[index];
    }
}