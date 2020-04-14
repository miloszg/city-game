package com.example.citygame;

public class NameAndIdScenario {
    private String scenarioName;
    private String id;

    @Override
    public String toString() {
        return  scenarioName;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
