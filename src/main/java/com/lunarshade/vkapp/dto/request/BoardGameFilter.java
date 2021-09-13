package com.lunarshade.vkapp.dto.request;


import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.convert.converter.Converter;

@NoArgsConstructor
@Setter
public class BoardGameFilter {
    private int[] players;
    private int[] time;
    private int[] mode;
    private ModeName modeName;
    private Integer minPlayers;
    private Integer maxPlayers;
    private Integer minTime;
    private Integer maxTime;

    public Integer getMinPlayer() throws IncompatibleFilterParameters {
        if (players == null && mode == null) return null;
        else if (players == null) return mode[0];
        else return calcMinPlayer(players[0], mode[0]);
    }

    public Integer getMaxPlayer() throws IncompatibleFilterParameters {
        if ((players == null && mode == null) || players.length == 1) return null;
        else if (mode.length == 1) return players [1];
        return calcMaxPlayer(players[1], mode[1]);
    }

    public Integer getMinTime() {
        return  time[0];
    }

    public Integer getMaxTime() {
        return  time[1];
    }

    public ModeName getModeName() {
        return modeName;
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

    public static class StringToEnumConverter implements Converter<String, ModeName> {
        @Override
        public ModeName convert(String source) {
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
