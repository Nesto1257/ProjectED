package Labirinto;

import Jogo.InputValidator;
import Structures.ArrayUnorderedList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Classe responsável pela criação interativa de novos mapas de labirinto.
 * Permite ao utilizador criar divisões, estabelecer conexões entre elas
 * e guardar o mapa num ficheiro JSON reutilizável.
 *
 * Validações implementadas:
 * <ul>
 *   <li>Apenas uma divisão do tipo Centro (obrigatória)</li>
 *   <li>Pelo menos uma divisão do tipo Entrada (obrigatória)</li>
 *   <li>Conexões apenas entre divisões existentes</li>
 *   <li>Sem conexões duplicadas</li>
 *   <li>Validação do nome do ficheiro</li>
 * </ul>
 *
 * @author Grupo ED
 * @version 1.0
 */
public class CriadorMapa {

    /** Diretório onde os mapas serão guardados */
    private static final String DIRETORIO_MAPAS = "src/main/resources/";

    /** Lista de divisões criadas */
    private ArrayUnorderedList<LabirintoLayout.DivisaoJson> divisoes;

    /** Lista de conexões criadas */
    private ArrayUnorderedList<LabirintoLayout.CorredorJson> corredores;

    /** Contador de IDs para as divisões */
    private int proximoId;

    /**
     * Construtor que inicializa as estruturas de dados.
     */
    public CriadorMapa() {
        this.divisoes = new ArrayUnorderedList<>();
        this.corredores = new ArrayUnorderedList<>();
        this.proximoId = 0;
    }

    /**
     * Inicia o processo interativo de criação de mapa.
     */
    public void iniciar() {
        imprimirCabecalho();

        boolean terminar = false;

        while (!terminar) {
            imprimirEstadoAtual();
            imprimirMenuOpcoes();

            int opcao = InputValidator.lerInteiro("Escolha uma opção: ", 1, 5);

            switch (opcao) {
                case 1:
                    criarDivisao();
                    break;
                case 2:
                    criarConexao();
                    break;
                case 3:
                    removerDivisao();
                    break;
                case 4:
                    if (validarMapa()) {
                        guardarMapa();
                        terminar = true;
                    }
                    break;
                case 5:
                    if (confirmarSaida()) {
                        terminar = true;
                    }
                    break;
            }
        }
    }

    /**
     * Imprime o cabeçalho do criador de mapas.
     */
    private void imprimirCabecalho() {
        System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║            🗺️  CRIADOR DE MAPAS 🗺️                    ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝\n");
        System.out.println("Crie o seu próprio labirinto!");
        System.out.println("⚠️  Requisitos:");
        System.out.println("   • Exatamente 1 divisão do tipo 'Centro' (tesouro)");
        System.out.println("   • Pelo menos 1 divisão do tipo 'Entrada'");
        System.out.println("   • Todas as divisões devem estar conectadas\n");
    }

    /**
     * Imprime o estado atual do mapa em construção.
     */
    private void imprimirEstadoAtual() {
        System.out.println("\n─────────────────────────────────────────────────────────");
        System.out.println("📊 ESTADO ATUAL: " + divisoes.size() + " divisões | " + corredores.size() + " conexões");

        if (!divisoes.isEmpty()) {
            System.out.println("\n📍 Divisões:");
            java.util.Iterator<LabirintoLayout.DivisaoJson> itDiv = divisoes.iterator();
            while (itDiv.hasNext()) {
                LabirintoLayout.DivisaoJson div = itDiv.next();
                System.out.println("   [" + div.id + "] " + div.nome + " (" + div.tipo + ")");
            }
        }

        if (!corredores.isEmpty()) {
            System.out.println("\n🔗 Conexões:");
            java.util.Iterator<LabirintoLayout.CorredorJson> itCor = corredores.iterator();
            while (itCor.hasNext()) {
                LabirintoLayout.CorredorJson cor = itCor.next();
                String nomeOrigem = obterNomeDivisao(cor.origem);
                String nomeDestino = obterNomeDivisao(cor.destino);
                System.out.println("   " + nomeOrigem + " ↔ " + nomeDestino);
            }
        }
        System.out.println("─────────────────────────────────────────────────────────");
    }

    /**
     * Imprime as opções do menu de criação.
     */
    private void imprimirMenuOpcoes() {
        System.out.println("\n  1. ➕ Criar Divisão");
        System.out.println("  2. 🔗 Criar Conexão");
        System.out.println("  3. ➖ Remover Divisão");
        System.out.println("  4. 💾 Guardar Mapa");
        System.out.println("  5. ↩️  Voltar (Menu Principal)");
        System.out.println();
    }

    /**
     * Cria uma divisão com input do utilizador.
     */
    private void criarDivisao() {
        System.out.println("\n═══ CRIAR DIVISÃO ═══\n");

        System.out.print("Nome da divisão: ");
        String nome = InputValidator.lerLinha();

        System.out.println("\nTipos disponíveis:");
        System.out.println("  1. Entrada  - Ponto de início dos jogadores");
        System.out.println("  2. Centro   - Localização do tesouro (objetivo)");
        System.out.println("  3. Enigma   - Divisão com desafio de enigma");
        System.out.println("  4. Alavanca - Divisão com desafio de alavanca");
        System.out.println("  5. Simples  - Corredor/divisão sem obstáculos");

        boolean temCentro = verificarSeTem(TipoDivisao.CENTRO);

        if (temCentro) {
            System.out.println("\n⚠️  Já existe uma divisão Centro. Não é possível criar outra.");
        }

        int tipoEscolhido = InputValidator.lerInteiro("\nEscolha o tipo (1-5): ", 1, 5);

        if (tipoEscolhido == 2 && temCentro) {
            System.out.println("❌ Já existe uma divisão Centro!");
            return;
        }

        String tipo = converterTipo(tipoEscolhido);

        LabirintoLayout.DivisaoJson novaDivisao = new LabirintoLayout.DivisaoJson();
        novaDivisao.id = proximoId++;
        novaDivisao.nome = nome;
        novaDivisao.tipo = tipo;

        divisoes.addToRear(novaDivisao);
        System.out.println("\n✅ Divisão '" + nome + "' criada com sucesso! (ID: " + novaDivisao.id + ")");
    }

    /**
     * Cria uma conexão entre duas divisões.
     */
    private void criarConexao() {
        if (divisoes.size() < 2) {
            System.out.println("\n❌ Precisa de pelo menos 2 divisões para criar uma conexão!");
            return;
        }

        System.out.println("\n═══ CRIAR CONEXÃO ═══\n");

        System.out.println("Divisões disponíveis:");
        java.util.Iterator<LabirintoLayout.DivisaoJson> it = divisoes.iterator();
        while (it.hasNext()) {
            LabirintoLayout.DivisaoJson div = it.next();
            System.out.println("  [" + div.id + "] " + div.nome);
        }

        System.out.println();
        int idOrigem = InputValidator.lerInteiro("ID da divisão de ORIGEM: ", 0, proximoId - 1);
        int idDestino = InputValidator.lerInteiro("ID da divisão de DESTINO: ", 0, proximoId - 1);

        if (!divisaoExiste(idOrigem) || !divisaoExiste(idDestino)) {
            System.out.println("❌ ID inválido!");
            return;
        }

        if (idOrigem == idDestino) {
            System.out.println("❌ Não pode conectar uma divisão a ela mesma!");
            return;
        }

        if (conexaoExiste(idOrigem, idDestino)) {
            System.out.println("❌ Esta conexão já existe!");
            return;
        }

        LabirintoLayout.CorredorJson novaConexao = new LabirintoLayout.CorredorJson();
        novaConexao.origem = idOrigem;
        novaConexao.destino = idDestino;

        // 1. Gerar número entre 1.0 e 2.5
        double aleatorio = 1.0 + (Math.random() * 1.5);

        // 2. Arredondar para 1 casa decimal
        // Multiplica por 10, arredonda ao inteiro mais próximo e divide por 10,0
        double pesoUmDecimal = Math.round(aleatorio * 10.0) / 10.0;

        novaConexao.peso = pesoUmDecimal;

        corredores.addToRear(novaConexao);

        String nomeOrigem = obterNomeDivisao(idOrigem);
        String nomeDestino = obterNomeDivisao(idDestino);
        System.out.println("\n✅ Conexão criada: " + nomeOrigem + " ↔ " + nomeDestino);
    }

    /**
     * Remove uma divisão existente.
     */
    private void removerDivisao() {
        if (divisoes.isEmpty()) {
            System.out.println("\n❌ Não há divisões para remover!");
            return;
        }

        System.out.println("\n═══ REMOVER DIVISÃO ═══\n");

        System.out.println("Divisões existentes:");
        java.util.Iterator<LabirintoLayout.DivisaoJson> it = divisoes.iterator();
        while (it.hasNext()) {
            LabirintoLayout.DivisaoJson div = it.next();
            System.out.println("  [" + div.id + "] " + div.nome + " (" + div.tipo + ")");
        }

        System.out.println();
        int idRemover = InputValidator.lerInteiro("ID da divisão a remover (-1 para cancelar): ", -1, proximoId - 1);

        if (idRemover == -1) {
            return;
        }

        LabirintoLayout.DivisaoJson divisaoRemover = null;
        it = divisoes.iterator();
        while (it.hasNext()) {
            LabirintoLayout.DivisaoJson div = it.next();
            if (div.id == idRemover) {
                divisaoRemover = div;
                break;
            }
        }

        if (divisaoRemover == null) {
            System.out.println("❌ Divisão não encontrada!");
            return;
        }

        divisoes.remove(divisaoRemover);
        removerConexoesDaDivisao(idRemover);

        System.out.println("✅ Divisão '" + divisaoRemover.nome + "' e suas conexões foram removidas!");
    }

    /**
     * Remove todas as conexões relacionadas a uma divisão.
     *
     * @param idDivisao O ID da divisão
     */
    private void removerConexoesDaDivisao(int idDivisao) {
        ArrayUnorderedList<LabirintoLayout.CorredorJson> aRemover = new ArrayUnorderedList<>();

        java.util.Iterator<LabirintoLayout.CorredorJson> it = corredores.iterator();
        while (it.hasNext()) {
            LabirintoLayout.CorredorJson cor = it.next();
            if (cor.origem == idDivisao || cor.destino == idDivisao) {
                aRemover.addToRear(cor);
            }
        }

        it = aRemover.iterator();
        while (it.hasNext()) {
            corredores.remove(it.next());
        }
    }

    /**
     * Valida se o mapa cumpre os requisitos mínimos.
     *
     * @return true se válido, false caso contrário
     */
    private boolean validarMapa() {
        System.out.println("\n🔍 A validar mapa...\n");

        boolean valido = true;

        if (!verificarSeTem(TipoDivisao.CENTRO)) {
            System.out.println("❌ Falta uma divisão do tipo 'Centro' (tesouro)!");
            valido = false;
        }

        if (!verificarSeTem(TipoDivisao.ENTRADA)) {
            System.out.println("❌ Falta pelo menos uma divisão do tipo 'Entrada'!");
            valido = false;
        }

        if (divisoes.isEmpty()) {
            System.out.println("❌ O mapa não tem nenhuma divisão!");
            valido = false;
        }

        if (corredores.isEmpty() && divisoes.size() > 1) {
            System.out.println("❌ O mapa não tem nenhuma conexão entre divisões!");
            valido = false;
        }

        if (valido) {
            System.out.println("✅ Mapa válido!");
        }

        return valido;
    }

    /**
     * Guarda o mapa num ficheiro JSON.
     */
    private void guardarMapa() {
        System.out.println("\n═══ GUARDAR MAPA ═══\n");

        System.out.print("Nome do ficheiro (sem extensão): ");
        String nomeFicheiro = InputValidator.lerLinha();

        nomeFicheiro = nomeFicheiro.trim().replaceAll("[^a-zA-Z0-9_-]", "_");

        if (nomeFicheiro.isEmpty()) {
            nomeFicheiro = "labirinto_novo";
        }

        if (!nomeFicheiro.startsWith("labirinto")) {
            nomeFicheiro = "labirinto_" + nomeFicheiro;
        }

        String caminhoCompleto = DIRETORIO_MAPAS + nomeFicheiro + ".json";

        LabirintoLayout layout = new LabirintoLayout();
        layout.divisoes = converterDivisoesParaArray();
        layout.corredores = converterCorredoresParaArray();

        try (Writer writer = new FileWriter(caminhoCompleto)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(layout, writer);
            System.out.println("\n✅ Mapa guardado com sucesso!");
            System.out.println("   Ficheiro: " + caminhoCompleto);
        } catch (IOException e) {
            System.out.println("❌ Erro ao guardar o mapa: " + e.getMessage());
        }
    }

    /**
     * Confirma se o utilizador deseja sair sem guardar.
     *
     * @return true se confirma saída, false caso contrário
     */
    private boolean confirmarSaida() {
        if (divisoes.isEmpty()) {
            return true;
        }

        System.out.println("\n⚠️  Tem alterações não guardadas!");
        System.out.println("  1. Sim, descartar e voltar");
        System.out.println("  2. Não, continuar a editar");

        int opcao = InputValidator.lerInteiro("Confirma saída? ", 1, 2);
        return opcao == 1;
    }

    /**
     * Verifica se existe uma divisão de determinado tipo.
     *
     * @param tipo O tipo a verificar
     * @return true se existe, false caso contrário
     */
    private boolean verificarSeTem(TipoDivisao tipo) {
        java.util.Iterator<LabirintoLayout.DivisaoJson> it = divisoes.iterator();
        while (it.hasNext()) {
            LabirintoLayout.DivisaoJson div = it.next();
            if (div.tipo.equalsIgnoreCase(tipo.getNome())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se uma divisão com o ID especificado existe.
     *
     * @param id O ID a verificar
     * @return true se existe, false caso contrário
     */
    private boolean divisaoExiste(int id) {
        java.util.Iterator<LabirintoLayout.DivisaoJson> it = divisoes.iterator();
        while (it.hasNext()) {
            if (it.next().id == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se já existe uma conexão entre duas divisões.
     *
     * @param origem ID da origem
     * @param destino ID do destino
     * @return true se existe, false caso contrário
     */
    private boolean conexaoExiste(int origem, int destino) {
        java.util.Iterator<LabirintoLayout.CorredorJson> it = corredores.iterator();
        while (it.hasNext()) {
            LabirintoLayout.CorredorJson cor = it.next();
            if ((cor.origem == origem && cor.destino == destino) ||
                (cor.origem == destino && cor.destino == origem)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtém o nome de uma divisão pelo ID.
     *
     * @param id O ID da divisão
     * @return O nome da divisão, ou "?" se não encontrada
     */
    private String obterNomeDivisao(int id) {
        java.util.Iterator<LabirintoLayout.DivisaoJson> it = divisoes.iterator();
        while (it.hasNext()) {
            LabirintoLayout.DivisaoJson div = it.next();
            if (div.id == id) {
                return div.nome;
            }
        }
        return "?";
    }

    /**
     * Converte a escolha numérica do tipo para String.
     *
     * @param escolha O número da escolha (1-5)
     * @return O nome do tipo
     */
    private String converterTipo(int escolha) {
        switch (escolha) {
            case 1: return "Entrada";
            case 2: return "Centro";
            case 3: return "Enigma";
            case 4: return "Alavanca";
            default: return "Simples";
        }
    }

    /**
     * Converte a lista de divisões para array.
     *
     * @return Array de DivisaoJson
     */
    private LabirintoLayout.DivisaoJson[] converterDivisoesParaArray() {
        LabirintoLayout.DivisaoJson[] array = new LabirintoLayout.DivisaoJson[divisoes.size()];
        int idx = 0;
        java.util.Iterator<LabirintoLayout.DivisaoJson> it = divisoes.iterator();
        while (it.hasNext()) {
            array[idx++] = it.next();
        }
        return array;
    }

    /**
     * Converte a lista de corredores para array.
     *
     * @return Array de CorredorJson
     */
    private LabirintoLayout.CorredorJson[] converterCorredoresParaArray() {
        LabirintoLayout.CorredorJson[] array = new LabirintoLayout.CorredorJson[corredores.size()];
        int idx = 0;
        java.util.Iterator<LabirintoLayout.CorredorJson> it = corredores.iterator();
        while (it.hasNext()) {
            array[idx++] = it.next();
        }
        return array;
    }
}

