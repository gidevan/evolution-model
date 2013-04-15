package com.vano.projects.evolution.terrain;

import com.vano.projects.evolution.common.Constants;
import com.vano.projects.evolution.common.Position;
import com.vano.projects.evolution.common.PositionContent;
import com.vano.projects.evolution.plant.Antiaris;
import com.vano.projects.evolution.plant.Cactus;
import com.vano.projects.evolution.plant.Plant;
import com.vano.projects.evolution.plant.Seed;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ivan_Pukhau
 * Date: 11/24/11
 * Time: 3:15 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class Territory {
    private static final int K1 = 3;
    private static final int K2 = 2;
    private static final int ZERO = 0;
    private PositionContent[][] terra = new PositionContent[Constants.TERRITORY_LENGTH][Constants.TERRITORY_WEIGHT];

    private long territoryAge;

    /**
     * Set of not empty positions.
     */
    private HashSet<Position> notEmptyPositions;

    public PositionContent[][] getTerra() {
        return terra;
    }

    public Territory() {
        initTerritory();
        Position cactusDefaultPosition = new Position(Constants.TERRITORY_LENGTH / K1,
                Constants.TERRITORY_WEIGHT / K2);
        Position antiaresDefaultPosition = new Position(Constants.TERRITORY_LENGTH / K2,
                Constants.TERRITORY_WEIGHT / K2);
        createPlantColony(true, cactusDefaultPosition);
        createPlantColony(false, antiaresDefaultPosition);
    }

    public Territory(List<PositionContent> positions) {
        initTerritory();
        for(PositionContent positionContent : positions) {
            Position position = positionContent.getPosition();
            int x = position.getX();
            int y = position.getY();
            if (x >= 0 && x <= Constants.TERRITORY_LENGTH && y >= 0 && y <= Constants.TERRITORY_WEIGHT) {
                if (positionContent.getPlant() != null || !CollectionUtils.isEmpty(positionContent.getAntiarisSeeds())
                        || !CollectionUtils.isEmpty(positionContent.getCactusSeeds())) {
                    notEmptyPositions.add(position);
                    terra[x][y] = positionContent;
                }
            }
        }
    }

    private void initTerritory() {
        territoryAge = 0;
        notEmptyPositions = new HashSet<Position>();
    }

    public void iterate(){
        HashSet<Position> notEmpty = new HashSet<Position>();
        notEmpty.addAll(notEmptyPositions);
        for(Position position : notEmpty) {
            PositionContent content = terra[position.getX()][position.getY()];
            processPlant(content);
            content.germinateSeed(position);
            processPosition(position);
        }
        territoryAge++;
    }

    public long getTerritoryAge() {
        return territoryAge;
    }

    /**
     * Return statistic of territory(number of plants, seeds etc.)
     * @return
     */
    public Map<String, Integer> getTerritoryStatistic() {
        HashMap<String, Integer> statistic = new HashMap<String, Integer>();
        for(Position position : notEmptyPositions) {
            PositionContent content = getPositionContent(position);
            Plant plant = content.getPlant();
            if (plant != null) {
                String plantName = plant.getPlantName();
                calculatePositionStatistic(statistic, plantName, 1);
            }
            if (!content.getAntiarisSeeds().isEmpty()) {
                calculateSeedsStatistic(statistic, content.getAntiarisSeeds());
            }
            if (!content.getCactusSeeds().isEmpty()) {
                calculateSeedsStatistic(statistic, content.getCactusSeeds());
            }
        }
        return statistic;
    }

    private void calculateSeedsStatistic(HashMap<String, Integer> statistic, List<Seed> seeds) {
        String key = seeds.get(ZERO).getSeedName();
        calculatePositionStatistic(statistic, key, seeds.size());
    }
    private void calculatePositionStatistic(HashMap<String, Integer> statistic, String key, int inc) {
        Integer value = statistic.get(key);
        if (value == null) {
            value = new Integer(ZERO);
        }
        value = value + inc;
        statistic.put(key, value);
    }

    /**
     * Creates default cactus colony.
     */
    private void createPlantColony(boolean isCactus, Position defaultPosition) {
        int plantNumber = isCactus ? Constants.DEFAULT_CACTUS_NUMBER : Constants.DEFAULT_ANTIARIS_NUMBER;
        for(int i = 0; i < plantNumber; i++) {
            defaultPosition = createPlant(defaultPosition, isCactus);
        }
    }

    private Position createPlant(Position defaultPosition, boolean isCactus) {
        Position position = generateRandomPosition(defaultPosition);
        Plant plant = isCactus ? new Cactus() : new Antiaris();
        PositionContent content = getPositionContent(position);
        content.setPlant(plant);
        notEmptyPositions.add(position);
        return position;
    }

    private PositionContent getPositionContent(Position position) {
        PositionContent content = terra[position.getX()][position.getY()];
        if (content == null) {
            content = new PositionContent(position);
            terra[position.getX()][position.getY()] = content;
        }
        return content;
    }

    private Position generateRandomPosition(Position defaultPosition) {
        if (terra[defaultPosition.getX()][defaultPosition.getY()] != null) {
            Random randomGenerator = new Random();
            boolean dx = randomGenerator.nextBoolean();
            boolean dy = randomGenerator.nextBoolean();
            int x = dx ? defaultPosition.getX() + Constants.OFFSET : defaultPosition.getX() - Constants.OFFSET;
            if (x < 0 || x > Constants.TERRITORY_LENGTH) {
                x = defaultPosition.getX();
            }
            int y = dy ? defaultPosition.getY() + Constants.OFFSET : defaultPosition.getY() - Constants.OFFSET;
            if (y < 0 || y > Constants.TERRITORY_WEIGHT) {
                y = defaultPosition.getY();
            }
            Position position = new Position(x, y);
            defaultPosition = generateRandomPosition(position);
        }
        return defaultPosition;
    }

    /**
     * Get list of seed positions by parent plant position.
     * @param defaultPosition parent Plant position
     * @return
     */
    private List<Position> getSeedPositions(Position defaultPosition) {
        List<Position> positions = new ArrayList<Position>();
        for(int i = defaultPosition.getX() - 1; i <= defaultPosition.getX() + 1; i++) {
            for(int j = defaultPosition.getY() - 1; j <= defaultPosition.getY() + 1; j++) {
                if (i >= 0 && i < Constants.TERRITORY_LENGTH
                        && j >= 0 && j < Constants.TERRITORY_WEIGHT
                        && !(i == defaultPosition.getX()
                                && j == defaultPosition.getY())) {
                    positions.add(new Position(i, j));
                }
            }
        }
        return positions;
    }

    private void processPlant(PositionContent content) {
        Plant plant = content.getPlant();
        if (plant != null) {
            plant.lifeCicle();
            processSeeds(content);
        }
    }

    /**
     * Sets seed on territory.
     * @param plant
     */
    private void processSeeds(PositionContent positionContent) {
        Plant plant = positionContent.getPlant();
        List<Seed> seeds = plant.getChilds();
        if (seeds != null && !seeds.isEmpty()) {
            List<Position> seedPositions = getSeedPositions(positionContent.getPosition());
            Position seedPosition = null;
            for(Seed seed : seeds) {
                if (!seedPositions.isEmpty()) {
                    seedPosition = seedPositions.get(ZERO);
                    seedPositions.remove(ZERO);
                }
                PositionContent content = getPositionContent(seedPosition);
                if (content.getPlant() == null) {
                    content.addSead(seed);
                    notEmptyPositions.add(seedPosition);
                }
            }
            positionContent.setPlant(null);
        }
    }

    private void processPosition(Position position) {
        PositionContent content = getPositionContent(position);
        if (content == null || (content.getPlant() == null && content.getCactusSeeds().isEmpty()
                && content.getAntiarisSeeds().isEmpty())) {
            notEmptyPositions.remove(position);
        }
    }
}
