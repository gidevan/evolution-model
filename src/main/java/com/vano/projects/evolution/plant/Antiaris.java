package com.vano.projects.evolution.plant;

import com.vano.projects.evolution.common.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ivan_Pukhau
 * Date: 11/28/11
 * Time: 8:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class Antiaris implements Plant {

    private long poison;

    private long antipoison;

    private long age;

    private List<Seed> childs;

    private long power;

    private String plantName = Constants.ANTIARIS_PLANT_NAME;

    public void lifeCicle() {
        if (age >= Constants.ANTIARIS_AGE) {
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

    public List<Seed> getChilds() {
        return childs;
    }

    public void setChilds(List<Seed> childs) {
        this.childs = childs;
    }

    public long getPower() {
        return power;
    }

    public void setPower(long power) {
        this.power = power;
    }

    public String getPlantName() {
        return plantName;
    }

    private List<Seed> createSeeds() {
        List<Seed> seeds = new ArrayList<Seed>();
        for(int i = 0; i < Constants.ANTIARIS_SEED_NUNBER; i++) {
            seeds.add(new AntiarisSeed());
        }
        return seeds;
    }

}
