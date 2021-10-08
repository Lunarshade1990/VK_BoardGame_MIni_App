package com.lunarshade.vkapp.dao.request;

import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;

@NoArgsConstructor
public class BoardGameFilter {
    private int[] players;
    private int[] time;
    private int[] mode;
    private String search;
    private String sort;
    private String order;
    private ModeName modeName;

    public Integer getMinPlayer() throws IncompatibleFilterParameters {
        if (players == null && mode == null) return null;
        else if (players == null) return mode[0];
        else return calcMinPlayer(players[0], mode[0]);
    }

    public Integer getMaxPlayer() throws IncompatibleFilterParameters {
        if ((players == null && mode == null) || players.length == 1) return null;
        else if (mode.length == 1) return mode [0];
        return calcMaxPlayer(players[1], mode[1]);
    }

    public Integer getMinTime() {
        if (time == null) return null;
        return  time[0];
    }

    public Integer getMaxTime() {
        if (time == null) return null;
        return  time[1];
    }

    public ModeName getModeName() {
        return modeName;
    }

    public String getSearch() {
        return search;
    }

    public String getSort() {
        return sort;
    }

    public String getOrder() {
        return order;
    }

    private Integer calcMaxPlayer(int maxFilter, int maxMode) throws IncompatibleFilterParameters {
        switch (modeName) {
            case SOLO, DUEL_PLUS -> {
                checkMaxCondition(maxFilter, maxMode);
                return null;
            }
            case DUEL -> {
                checkMaxCondition(maxFilter, maxMode);
                return 2;
            }
            case COMPANY -> {
                checkMaxCondition(maxFilter, maxMode);
                return maxMode;
            }
            default -> {return null;}
        }
    }

    private Integer calcMinPlayer(int minFilter, int minMode) throws IncompatibleFilterParameters {
        if (minFilter > minMode) {
            throw new IncompatibleFilterParameters();
        } else return minMode;
    }

    private void checkMaxCondition(int maxFilter, int maxMode) throws IncompatibleFilterParameters {
        if (maxMode > maxFilter) {
            throw new IncompatibleFilterParameters();
        }
    }

    public enum ModeName {
        SOLO, DUEL, DUEL_PLUS, COMPANY
    }

    public void setPlayers(int[] players) {
        this.players = players;
    }

    public void setTime(int[] time) {
        this.time = time;
    }

    public void setMode(int[] mode) {
        this.mode = mode;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setModeName(ModeName modeName) {
        this.modeName = modeName;
    }

    public static class StringToEnumConverter implements Converter<String, ModeName> {
        @Override
        public ModeName convert(String source) {
            if (source == "" || source == null) return null;
            try {
                if (source.equals("duel+")) return ModeName.DUEL_PLUS;
                return ModeName.valueOf(source.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }



    public static class IncompatibleFilterParameters extends Exception {
        public IncompatibleFilterParameters() {
        }
    }
}