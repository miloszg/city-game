package com.example.citygame.Models;

import java.util.ArrayList;

public class GameResponse {
    public String id;
    public String title;
    public Integer score;
    public ArrayList<String> users_ids;
    public ArrayList<String> completed_markers;
    public ScenarioResponse scenario;
}
