package Main;

/**
 * Classe principal do programa Labirinto da Glória.
 * Ponto de entrada da aplicação que delega a configuração
 * e inicialização ao {@link MenuPrincipal}.
 *
 * @author Grupo ED
 * @version 2.0
 */
public class Main {

    /**
     * Método principal que inicia a aplicação.
     * Apresenta o menu principal ao utilizador.
     *
     * @param args Argumentos da linha de comandos (não utilizados)
     */
    public static void main(String[] args) {
        MenuPrincipal menu = new MenuPrincipal();
        menu.mostrar();
    }
}

