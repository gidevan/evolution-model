package com.vano.projects.evolution.client;

import com.vano.projects.evolution.common.Constants;
import com.vano.projects.evolution.common.PositionContent;
import com.vano.projects.evolution.plant.Cactus;
import com.vano.projects.evolution.terrain.Territory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: Ivan_Pukhau
 * Date: 11/24/11
 * Time: 4:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestClient {
    private static final String CACTUS = "C";
    private static final String ANTIARIS = "A";
    private static final String CACTUS_SEED = "c";
    private static final String ANTIARIS_SEED = "a";
    private static final String DELIMETER_FIELD = "|";
    private static final String EMPTY = " ";
    private static final String FILE_LOG = "target/log.txt";

    public static void main(String[] args) {
        Territory territory = new Territory();
        try {
            FileWriter outFile = new FileWriter(FILE_LOG);
            PrintWriter out = new PrintWriter(outFile);
            for(int i=0; i<= Constants.ITERATION_NUMBER; i++) {
                territory.iterate();
                printTerra(out, territory.getTerra(), i);
            }
            out.close();
        } catch (IOException e) {

        }
    }

    private static void printTerra(PrintWriter out, PositionContent[][] terra, int iterationNumber) {
        out.println("iteration: " + iterationNumber);
        int cactusNumber = 0;
        int antiarisNumber = 0;
        for(int i = 0; i < Constants.TERRITORY_LENGTH; i++) {
            for(int j = 0; j < Constants.TERRITORY_WEIGHT; j++) {
                PositionContent content = terra[i][j];
                StringBuffer builder = new StringBuffer();
                if (content != null && content.getPlant() != null) {
                    if (content.getPlant() instanceof Cactus) {
                        builder.append(CACTUS);
                        cactusNumber++;
                    } else {
                        builder.append(ANTIARIS);
                        antiarisNumber++;
                    }
                    builder.append(content.getPlant().getAge());
                }
                if (content != null && content.getCactusSeeds().size() > 0) {
                    builder.append(CACTUS_SEED).append(content.getCactusSeeds().size());
                }
                if (content == null ||  (content.getCactusSeeds().isEmpty() && content.getPlant() == null)) {
                    builder.append(EMPTY).append(EMPTY);
                }
                builder.append(DELIMETER_FIELD);
                out.print(builder);
            }
            out.println();
        }
        out.println("Cactuses: " + cactusNumber);
        out.println("Antiarises: " + antiarisNumber);
        out.println();
    }
}
