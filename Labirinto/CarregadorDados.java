package Labirinto;

import Eventos.QuestaoEnigma;
import Structures.ArrayUnorderedList;
import Labirinto.LabirintoLayout.DivisaoJson;
import Labirinto.LabirintoLayout.CorredorJson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Classe responsável por carregar os dados JSON para as estruturas do Labirinto
 * usando a biblioteca Gson.
 */
public class CarregadorDados {

    /**
     * Carrega e configura todo o objeto Labirinto a partir do ficheiro JSON.
     * @param nomeFicheiroLabirinto O caminho para o ficheiro JSON do labirinto.
     * @return O Labirinto configurado com Divisoes e Corredores.
     */
    public static Labirinto carregarLabirinto(String nomeFicheiroLabirinto) {
        Gson gson = new Gson();
        Labirinto labirinto = new Labirinto();

        try (Reader reader = new FileReader(nomeFicheiroLabirinto)) {
            // Desserializa o ficheiro JSON para a estrutura de mapeamento
            LabirintoLayout layout = gson.fromJson(reader, LabirintoLayout.class);

            // 1. Criar e Adicionar as Divisões
            if (layout.divisoes != null) {
                System.out.println("🔍 Debug: Ficheiro JSON contém " + layout.divisoes.length + " divisões");
                int divisoesAdicionadas = 0;

                for (DivisaoJson dJson : layout.divisoes) {
                    Divisao divisao = new Divisao(dJson.id, dJson.nome, dJson.tipo);
                    labirinto.adicionarDivisao(divisao);
                    divisoesAdicionadas++;
                    System.out.println("   → Adicionada: [" + dJson.id + "] " + dJson.nome + " (" + dJson.tipo + ")");
                }

                System.out.println("✅ Total de divisões adicionadas: " + divisoesAdicionadas);
            } else {
                System.out.println("❌ AVISO: Nenhuma divisão encontrada no JSON!");
            }

            // 2. Ligar os Corredores (Arestas)
            if (layout.corredores != null) {
                System.out.println("\n🔗 Debug: Ficheiro JSON contém " + layout.corredores.length + " corredores");
                int corredoresAdicionados = 0;

                for (CorredorJson cJson : layout.corredores) {
                    // Para ligar, precisamos das referências aos objetos Divisao
                    Divisao origem = labirinto.getDivisaoByID(cJson.origem);
                    Divisao destino = labirinto.getDivisaoByID(cJson.destino);

                    if (origem != null && destino != null) {
                        labirinto.ligarDivisoes(origem, destino, cJson.peso);
                        corredoresAdicionados++;
                        System.out.println("   → Ligado: " + origem.getNome() + " ←→ " + destino.getNome() + " (peso: " + cJson.peso + ")");
                    } else {
                        System.out.println("   ⚠️ ERRO: Corredor inválido - origem:" + cJson.origem + " destino:" + cJson.destino);
                        if (origem == null) System.out.println("      → Origem (ID " + cJson.origem + ") não encontrada!");
                        if (destino == null) System.out.println("      → Destino (ID " + cJson.destino + ") não encontrado!");
                    }
                }

                System.out.println("✅ Total de corredores adicionados: " + corredoresAdicionados);
            } else {
                System.out.println("❌ AVISO: Nenhum corredor encontrado no JSON!");
            }

        } catch (IOException e) {
            System.err.println("ERRO: Falha ao carregar o Labirinto do JSON: " + e.getMessage());
        }

        return labirinto;
    }

    /**
     * Carrega a lista de Questões de Enigma do ficheiro JSON.
     * @param nomeFicheiroEnigmas O caminho para o JSON dos enigmas.
     * @return Uma ArrayUnorderedList de QuestaoEnigma.
     */
    public static ArrayUnorderedList<QuestaoEnigma> carregarEnigmas(String nomeFicheiroEnigmas) {
        Gson gson = new Gson();
        ArrayUnorderedList<QuestaoEnigma> questoesList = new ArrayUnorderedList<>();

        try (Reader reader = new FileReader(nomeFicheiroEnigmas)) {
            // Desserializa uma lista de QuestaoEnigma.
            // Primeiro carregamos como array, depois transferimos para ArrayUnorderedList
            Type tipoArray = new TypeToken<QuestaoEnigma[]>(){}.getType();
            QuestaoEnigma[] questoesArray = gson.fromJson(reader, tipoArray);

            // Transfere os elementos do array para a ArrayUnorderedList
            if (questoesArray != null) {
                for (QuestaoEnigma q : questoesArray) {
                    questoesList.addToRear(q);
                }
            }

        } catch (IOException e) {
            System.err.println("ERRO: Falha ao carregar Enigmas do JSON: " + e.getMessage());
        }

        return questoesList;
    }

    /**
     * Guarda o labirinto num ficheiro JSON.
     * @param labirinto O labirinto a ser guardado
     * @param nomeFicheiroLabirinto O caminho para o ficheiro JSON de destino
     * @return true se guardado com sucesso, false caso contrário
     */
    public static boolean guardarLabirinto(Labirinto labirinto, String nomeFicheiroLabirinto) {
        Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();

        try (java.io.FileWriter writer = new java.io.FileWriter(nomeFicheiroLabirinto)) {
            // Criar estrutura para serialização
            LabirintoLayout layout = new LabirintoLayout();

            // 1. Extrair todas as divisões
            int numDivisoes = labirinto.getTamanho();
            layout.divisoes = new LabirintoLayout.DivisaoJson[numDivisoes];

            for (int i = 0; i < numDivisoes; i++) {
                Divisao div = labirinto.getDivisaoPorIndice(i);
                if (div != null) {
                    LabirintoLayout.DivisaoJson divJson = new LabirintoLayout.DivisaoJson();
                    divJson.id = div.getId();
                    divJson.nome = div.getNome();
                    divJson.tipo = div.getTipo();
                    layout.divisoes[i] = divJson;
                }
            }

            // 2. Extrair todos os corredores (arestas)
            // Primeiro contar quantas conexões existem
            int numCorredores = 0;
            for (int i = 0; i < numDivisoes; i++) {
                for (int j = i + 1; j < numDivisoes; j++) {
                    if (labirinto.existeConexao(i, j)) {
                        numCorredores++;
                    }
                }
            }

            layout.corredores = new LabirintoLayout.CorredorJson[numCorredores];
            int corredorIndex = 0;

            for (int i = 0; i < numDivisoes; i++) {
                for (int j = i + 1; j < numDivisoes; j++) {
                    if (labirinto.existeConexao(i, j)) {
                        LabirintoLayout.CorredorJson corrJson = new LabirintoLayout.CorredorJson();
                        corrJson.origem = labirinto.getDivisaoPorIndice(i).getId();
                        corrJson.destino = labirinto.getDivisaoPorIndice(j).getId();
                        corrJson.peso = labirinto.getPesoCorredor(i, j);
                        layout.corredores[corredorIndex++] = corrJson;
                    }
                }
            }

            // 3. Serializar para JSON e escrever no ficheiro
            gson.toJson(layout, writer);

            System.out.println("✅ Labirinto guardado com sucesso em: " + nomeFicheiroLabirinto);
            return true;

        } catch (java.io.IOException e) {
            System.err.println("ERRO: Falha ao guardar o Labirinto no JSON: " + e.getMessage());
            return false;
        }
    }
}
