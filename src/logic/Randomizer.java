package src.logic;

import src.gameobjects.Player;

public class Randomizer {
    public static Tile randomizedLocation(Grid ref, Player player) {
        Tile randTile = null;
        boolean occupied;
        do {
            // Generate a random location
            Double randDouble = Math.random() * ref.getColumns() * ref.getColumns();
            int randInt = randDouble.intValue();
            randTile = (ref.getTiles())[randInt];
            // Check if random location is already occupied by player
            occupied = false;
            if ((player.getLocation().getTileName()).equals(randTile.getTileName())) {
                occupied = true;
            }
            for (Tile tile : player.getAppendices()) {
                if ((tile.getTileName()).equals(randTile.getTileName())) {
                    occupied = true;
                }
            }
        } while (occupied);
        return randTile;
    }
}
