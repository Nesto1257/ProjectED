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
 * Classe responsável pelo carregamento de dados a partir de ficheiros JSON.
 * Utiliza a biblioteca Gson para desserializar os ficheiros e converter
 * para as estruturas de dados do jogo.
 * Ficheiros suportados:
 * <ul>
 *   <li>Labirinto.json - Estrutura do labirinto (divisões e corredores)</li>
 *   <li>enigmas.json - Questões de enigmas para os desafios</li>
 * </ul>
 *
 * @author Grupo ED
 * @version 1.0
 */
public class CarregadorDados {

    /**
     * Carrega e configura o labirinto a partir de um ficheiro JSON.
     * Processa as divisões e os corredores definidos no ficheiro.
     *
     * @param nomeFicheiroLabirinto O caminho para o ficheiro JSON do labirinto
     * @return O labirinto configurado com divisões e ligações
     */
    public static Labirinto carregarLabirinto(String nomeFicheiroLabirinto) {
        Gson gson = new Gson();
        Labirinto labirinto = new Labirinto();

        try (Reader reader = new FileReader(nomeFicheiroLabirinto)) {
            // Desserializar o ficheiro JSON
            LabirintoLayout layout = gson.fromJson(reader, LabirintoLayout.class);

            // Criar e adicionar as divisões
            if (layout.divisoes != null) {
                for (DivisaoJson dJson : layout.divisoes) {
                    Divisao divisao = new Divisao(dJson.id, dJson.nome, TipoDivisao.fromString(dJson.tipo));
                    labirinto.adicionarDivisao(divisao);
                }
            }

            // Estabelecer as ligações entre divisões (corredores)
            if (layout.corredores != null) {
                for (CorredorJson cJson : layout.corredores) {
                    Divisao origem = labirinto.getDivisaoByID(cJson.origem);
                    Divisao destino = labirinto.getDivisaoByID(cJson.destino);

                    if (origem != null && destino != null) {
                        labirinto.ligarDivisoes(origem, destino, cJson.peso);
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("ERRO: Falha ao carregar o Labirinto do JSON: " + e.getMessage());
        }

        return labirinto;
    }

    /**
     * Carrega as questões de enigmas a partir de um ficheiro JSON.
     *
     * @param nomeFicheiroEnigmas O caminho para o ficheiro JSON dos enigmas
     * @return Uma lista com as questões carregadas
     */
    public static ArrayUnorderedList<QuestaoEnigma> carregarEnigmas(String nomeFicheiroEnigmas) {
        Gson gson = new Gson();
        ArrayUnorderedList<QuestaoEnigma> questoesList = new ArrayUnorderedList<>();

        try (Reader reader = new FileReader(nomeFicheiroEnigmas)) {
            // Desserializar para um array de questões
            Type tipoArray = new TypeToken<QuestaoEnigma[]>(){}.getType();
            QuestaoEnigma[] questoesArray = gson.fromJson(reader, tipoArray);

            // Transferir para a estrutura ArrayUnorderedList
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
}
