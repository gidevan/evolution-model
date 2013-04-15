package com.vano.projects.evolution.terrain;

import com.vano.projects.evolution.common.Constants;
import com.vano.projects.evolution.common.PositionContent;
import com.vano.projects.evolution.plant.Cactus;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.PrintWriter;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: Ivan_Pukhau
 * Date: 11/29/11
 * Time: 9:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class TerritoryTest {

    private static final String CACTUS = "C";
    private static final String ANTIARIS = "A";
    private static final String CACTUS_SEED = "*";
    private static final String ANTIARIS_SEED = "^";
    private static final String DELIMETER_FIELD = "|";
    private static final String EMPTY = " ";
    private static final int ITERATION_NUMBER = 150;

    private Logger log = Logger.getLogger(TerritoryTest.class);

    @Test
    public void getTerraTest() {
        Territory territory = new Territory();
        for(int i = 0; i < ITERATION_NUMBER; i++) {
            territory.iterate();
            PositionContent[][] terra = territory.getTerra();
            Assert.assertNotNull(terra);
            printTerra(terra, i);
            Map<String, Integer> statistic = territory.getTerritoryStatistic();
            for(Map.Entry<String, Integer> entry : statistic.entrySet()) {
                log.debug(entry.getKey() + " : " + entry.getValue());
            }
        }
    }

    private void printTerra(PositionContent[][] terra, int iterationNumber) {
        log.debug("iteration: " + iterationNumber);
        StringBuffer builder = new StringBuffer("\n");
        for(int i = 0; i < Constants.TERRITORY_LENGTH; i++) {
            for(int j = 0; j < Constants.TERRITORY_WEIGHT; j++) {
                PositionContent content = terra[i][j];
                if(content != null) {
                    if (content.getPlant() != null) {
                        if (Constants.CACTUS_PLANT_NAME.equals(content.getPlant().getPlantName())) {
                            builder.append(CACTUS);
                        } else {
                            builder.append(ANTIARIS);
                        }
                        builder.append(content.getPlant().getAge());
                    }
                    int antiarisSeedNumber = content.getAntiarisSeeds().size();
                    int cactusSeedNumber = content.getCactusSeeds().size();
                    if (antiarisSeedNumber > 0 && cactusSeedNumber > 0) {
                        if (cactusSeedNumber > antiarisSeedNumber) {
                            builder.append(CACTUS_SEED).append(ANTIARIS_SEED);
                        } else {
                            builder.append(ANTIARIS_SEED).append(CACTUS_SEED);
                        }
                    } else if (cactusSeedNumber > 0) {
                        builder.append(CACTUS_SEED).append(cactusSeedNumber);
                    } else if (antiarisSeedNumber > 0) {
                        builder.append(ANTIARIS_SEED).append(antiarisSeedNumber);
                    }
                }
                if (content == null ||  (content.getCactusSeeds().isEmpty() && content.getAntiarisSeeds().isEmpty()
                        && content.getPlant() == null)) {
                    builder.append(EMPTY).append(EMPTY);
                }
                builder.append(DELIMETER_FIELD);
            }
            builder.append("\n");
        }
        log.debug(builder);
    }

}
