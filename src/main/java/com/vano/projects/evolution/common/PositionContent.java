package com.vano.projects.evolution.common;

import com.vano.projects.evolution.plant.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ivan_Pukhau
 * Date: 11/25/11
 * Time: 7:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class PositionContent {

    private Plant plant;

    private List<Seed> cactusSeeds;

    private List<Seed> antiarisSeeds;

    private Position position;

    public PositionContent(Position position) {
        this.position = position;
        cactusSeeds = new LinkedList<Seed>();
        antiarisSeeds = new LinkedList<Seed>();
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public List<Seed> getCactusSeeds() {
        return cactusSeeds;
    }

    public void setCactusSeeds(List<Seed> cactusSeeds) {
        this.cactusSeeds = cactusSeeds;
    }

    public List<Seed> getAntiarisSeeds() {
        return antiarisSeeds;
    }

    public void setAntiarisSeeds(List<Seed> antiarisSeeds) {
        this.antiarisSeeds = antiarisSeeds;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void addSead(Seed seed) {
        if (seed instanceof CactusSeed) {
            cactusSeeds.add(seed);
        } else {
            antiarisSeeds.add(seed);
        }
    }

    public void germinateSeed(Position position) {
        if (!cactusSeeds.isEmpty() || !antiarisSeeds.isEmpty()) {
            if (cactusSeeds.size() > antiarisSeeds.size()) {
                createPlant(true, Constants.CACTUS_SEED_LIMIT);
            } else if (cactusSeeds.size() < antiarisSeeds.size()) {
                createPlant(false, Constants.ANTIARIS_SEED_LIMIT);
            }
        }
    }

    private void createPlant(boolean isCactus, int seedLimit) {
        List<Seed> seeds = isCactus ? cactusSeeds : antiarisSeeds;
        if (seeds.size() >= seedLimit && plant == null) {
            plant = isCactus ? new Cactus() : new Antiaris();
            cactusSeeds.clear();
            antiarisSeeds.clear();
        }

    }



}
