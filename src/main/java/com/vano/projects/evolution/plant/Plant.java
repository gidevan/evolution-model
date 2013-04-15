package com.vano.projects.evolution.plant;

import com.vano.projects.evolution.common.Position;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ivan_Pukhau
 * Date: 11/24/11
 * Time: 3:05 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Plant {

    long getPoison();

    long getAntipoison();

    long getAge();

    long getPower();

    void lifeCicle();

    String getPlantName();

    List<Seed> getChilds();

}
