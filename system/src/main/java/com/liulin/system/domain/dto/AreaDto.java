package com.liulin.system.domain.dto;

import java.util.List;

public class AreaDto {

    private String name;

    private List<LevelDto> levels;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LevelDto> getLevels() {
        return levels;
    }

    public void setLevels(List<LevelDto> levels) {
        this.levels = levels;
    }
}
