package com.vano.projects.evolution.plant;

import com.vano.projects.evolution.common.Constants;
import com.vano.projects.evolution.common.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ivan_Pukhau
 * Date: 11/24/11
 * Time: 3:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class Cactus implements Plant {

    private long poison;

    private long antipoison;

    private long age;

    private long power;

    private List<Seed> childs;

    private String plantName = Constants.CACTUS_PLANT_NAME;

    public void lifeCicle() {
        if (age >= Constants.CACTUS_AGE) {
            reproduce();
        }
        age++;
    }

    private void reproduce() {
        childs = createSeeds();
    }

    public long getPoison() {
        return poison;
    }

    public void setPoison(long poison) {
        this.poison = poison;
    }

    public long getAntipoison() {
        return antipoison;
    }

    public void setAntipoison(long antipoison) {
        this.antipoison = antipoison;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public long getPower() {
        return power;
    }

    public void setPower(long power) {
        this.power = power;
    }

    public List<Seed> getChilds() {
        return childs;
    }

    public void setChilds(List<Seed> childs) {
        this.childs = childs;
    }

    public String getPlantName() {
        return plantName;
    }

    /**
     *
     * @param defaultPosition
     * @return
     */
    private List<Seed> createSeeds() {
        List<Seed> seeds = new ArrayList<Seed>();
        for(int i = 0; i < Constants.CACTUS_SEED_NUNBER; i++) {
            seeds.add(new CactusSeed());
        }
        return seeds;
    }

}
