package com.example.citygame.Models;

import java.io.Serializable;
import java.util.List;

public class Scenario implements Serializable {

    private String scenarioName;
    private String scenarioId;
    private List<Objective> objectives;

    public void deleteObjective(Scenario.Objective objective){
        objectives.remove(objective);
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    @Override
    public String toString() {
        return   scenarioName + '\'' +
                ", objectives=" + objectives;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

    public class Coordinates implements Serializable {
        private double lat;
        private double lon;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }
    }

    public class Objective implements Serializable{
        private String placeName;
        private Coordinates coordinates;
        private List<Task> tasks;

        @Override
        public String toString() {
            return "'\'' placeName='" + placeName;
        }

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }

        public Coordinates getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
        }

        public List<Task> getTasks() {
            return tasks;
        }

        public void setTasks(List<Task> tasks) {
            this.tasks = tasks;
        }
    }

    public class Task implements Serializable  {
        private String question;
        private List<PossibleAnswers> possibleAnswers;

        @Override
        public String toString() {
            return "Task{" +
                    "question='" + question + '\'';
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public List<PossibleAnswers> getPossibleAnswers() {
            return possibleAnswers;
        }

        public void setPossibleAnswers(List<PossibleAnswers> possibleAnswers) {
            this.possibleAnswers = possibleAnswers;
        }
    }

    public class PossibleAnswers implements Serializable  {
        private String answer;
        private Boolean isCorrect ;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public Boolean getCorrect() {
            return isCorrect;
        }

        public void setCorrect(Boolean correct) {
            isCorrect = correct;
        }
    }

}

