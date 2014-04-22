package challenge154;

import constants.Constants;
import java.util.Scanner;

/**
 *
 * @author jconner
 */
public class Challenge154 {

    public static Cave cave;
    public static Player player;

    public static final int POINTS_EMPTY = 1;
    public static final int POINTS_WEAPON = 5;
    public static final int POINTS_WUMPUS = 10;
    public static final int POINTS_GOLD = 5;

    public static void main(String[] args) {
        String userInput = "";
        Integer caveDimensions = 0;
        String command = "";
        Scanner reader = new Scanner(System.in);

        while (true) {
            System.out.print("Enter an integer n > 1 for cave dimensions; n * n: ");
            userInput = reader.next();
            System.out.println();
            try {
                caveDimensions = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {

            }
            if (caveDimensions > 1) {
                break;
            }
        }

        cave = new Cave(caveDimensions);

        //Set initial player position to cave entrance
        for (int x = 0; x < cave.getRooms().size(); x++) {
            if (cave.getRooms().get(x).getRoomValue().equals(Constants.getMessage("ENTRANCE"))) {
                player = new Player(x);
                cave.getRooms().get(x).setIsPlayerHere(true);
                player.setPosition(x);
            }
        }

        //Begin Game
        while (!command.equals("X")) {
            cave.printCave();
            exploreRoom();
            printStatistics();
            System.out.print("Enter in a command to continue; ? for HELP: ");
            command = reader.next().toUpperCase();

            switch (command) {
                case "?":
                    System.out.println(Constants.getMessage("HELP"));
                    break;

                case "N":
                case "S":
                case "E":
                case "W":
                    movement(command);
                    break;

                case "R":
                    runOutOfCave();
                    break;

                case "L":
                    loot();
                    break;
            }
        }
    }

    public static void movement(String command) {
        System.out.println(command);

        switch (command) {
            case "N": {
                //Make sure no wall to north
                if (player.getPosition() > cave.getN() - 1) {
                    cave.getRooms().get(player.getPosition()).setIsPlayerHere(false);
                    player.setPosition(player.getPosition() - cave.getN());
                    cave.getRooms().get(player.getPosition()).setIsPlayerHere(true);
                } else {
                    System.out.println(Constants.getMessage("WALLTHERE"));
                }
                break;
            }
            case "S": {
                //Make sure no wall to south
                if (player.getPosition() + cave.getN() < cave.getRooms().size()) {
                    cave.getRooms().get(player.getPosition()).setIsPlayerHere(false);
                    player.setPosition(player.getPosition() + cave.getN());
                    cave.getRooms().get(player.getPosition()).setIsPlayerHere(true);
                } else {
                    System.out.println(Constants.getMessage("WALLTHERE"));
                }
                break;
            }
            case "E": {
                //Make sure no wall to east
                if ((player.getPosition() + 1) % cave.getN() != 0) {
                    cave.getRooms().get(player.getPosition()).setIsPlayerHere(false);
                    player.setPosition(player.getPosition() + 1);
                    cave.getRooms().get(player.getPosition()).setIsPlayerHere(true);
                } else {
                    System.out.println(Constants.getMessage("WALLTHERE"));
                }
                break;
            }
            case "W": {
                //Make sure no wall to west
                if (player.getPosition() % cave.getN() != 0) {
                    cave.getRooms().get(player.getPosition()).setIsPlayerHere(false);
                    player.setPosition(player.getPosition() - 1);
                    cave.getRooms().get(player.getPosition()).setIsPlayerHere(true);
                } else {
                    System.out.println(Constants.getMessage("WALLTHERE"));
                }
                break;
            }
        }
    }

    public static void runOutOfCave() {
        if (cave.getRooms().get(player.getPosition()).getRoomValue().equals(Constants.getMessage("ENTRANCE"))) {
            System.out.println(Constants.getMessage("RUN_AWAY"));
            gameOver();
        } else {
            System.out.println(Constants.getMessage("CANT_RUN"));
        }
    }

    public static void loot() {
        if (cave.getRooms().get(player.getPosition()).getRoomType().equals("Gold")) {
            System.out.println(Constants.getMessage("LOOT_GOLD"));
            player.setPoints(player.getPoints() + POINTS_GOLD);
            cave.getRooms().get(player.getPosition()).setRoomType("Empty");
            cave.getRooms().get(player.getPosition()).setRoomDescription(Constants.getMessage("DESC_Looted"));
        } else if (cave.getRooms().get(player.getPosition()).getRoomType().equals("Weapon")) {
            if (player.isHasWeapon() == false) {
                System.out.println(Constants.getMessage("LOOT_WEAPON"));
                player.setPoints(player.getPoints() + POINTS_WEAPON);
                player.setHasWeapon(true);
                cave.getRooms().get(player.getPosition()).setRoomType("Empty");
                cave.getRooms().get(player.getPosition()).setRoomDescription(Constants.getMessage("DESC_Looted"));
            } else {
                System.out.println(Constants.getMessage("LOOT_ALREADY"));
            }
        } else {
            System.out.println(Constants.getMessage("LOOT_NOTHING"));
        }
    }

    public static void exploreRoom() {
        //1 Point for entering an unexplored room
        if (cave.getRooms().get(player.getPosition()).isVisited() == false) {
            player.setPoints(player.getPoints() + POINTS_EMPTY);
            cave.getRooms().get(player.getPosition()).setVisited(true);
        }

        if (cave.getRooms().get(player.getPosition()).getRoomValue().equals(Constants.getMessage("UNEXPLORED_ROOM"))) {
            cave.getRooms().get(player.getPosition()).setRoomValue(revealRoomType());
        }

        if (cave.getRooms().get(player.getPosition()).getRoomValue().equals(Constants.getMessage("PIT_TRAP"))) {
            System.out.println(cave.getRooms().get(player.getPosition()).getRoomDescription());
            gameOver();
        }

        if (cave.getRooms().get(player.getPosition()).getRoomValue().equals(Constants.getMessage("WUMPUS"))) {
            if (player.isHasWeapon() == false) {
                if (!cave.getRooms().get(player.getPosition()).getRoomDescription().equals(Constants.getMessage("DEAD_WUMPUS"))) {
                    System.out.println(cave.getRooms().get(player.getPosition()).getRoomDescription());
                    gameOver();
                }
            } else {
                if (!cave.getRooms().get(player.getPosition()).getRoomDescription().equals(Constants.getMessage("DEAD_WUMPUS"))) {
                    System.out.println(Constants.getMessage("SLAY_WUMPUS"));
                    player.setPoints(player.getPoints() + POINTS_WUMPUS);
                    player.setHasWeapon(false);
                    cave.getRooms().get(player.getPosition()).setRoomDescription(Constants.getMessage("DEAD_WUMPUS"));
                }
            }
        }
    }

    public static String revealRoomType() {
        String result = "";

        switch (cave.getRooms().get(player.getPosition()).getRoomType()) {
            case "Empty": {
                result = Constants.getMessage("EMPTY_ROOM");
                break;
            }
            case "Gold": {
                result = Constants.getMessage("GOLD");
                break;
            }
            case "Weapon": {
                result = Constants.getMessage("WEAPON");
                break;
            }
            case "Wumpus": {
                result = Constants.getMessage("WUMPUS");
                break;
            }
            case "Pit Trap": {
                //Game Over
                result = Constants.getMessage("PIT_TRAP");
                break;
            }
        }

        return (result);
    }

    public static String revealClues() {
        String result = "";
        int posNorth = player.getPosition() - cave.getN();
        int posSouth = player.getPosition() + cave.getN();
        int posEast = player.getPosition() + 1;
        int posWest = player.getPosition() - 1;

        //if north of position is a wumpus or pit
        if (posNorth > -1) {
            if (cave.getRooms().get(posNorth).getRoomType()
                    .equals("Wumpus")) {
                result += Constants.getMessage("CLUE_WUMPUS");
            }
            if (cave.getRooms().get(posNorth).getRoomType()
                    .equals("Pit Trap")) {
                result += Constants.getMessage("CLUE_PIT");
            }
        }

        //if south of position is a wumpus or pit
        if (posSouth < cave.getRooms().size()) {
            if (cave.getRooms().get(posSouth).getRoomType()
                    .equals("Wumpus")) {
                result += Constants.getMessage("CLUE_WUMPUS");
            }
            if (cave.getRooms().get(posSouth).getRoomType()
                    .equals("Pit Trap")) {
                result += Constants.getMessage("CLUE_PIT");
            }
        }

        //if east of position is a wumpus or pit
        if (posEast < cave.getRooms().size() && (player.getPosition() + 1) % cave.getN() != 0) {
            if (cave.getRooms().get(posEast).getRoomType()
                    .equals("Wumpus")) {
                result += Constants.getMessage("CLUE_WUMPUS");
            }
            if (cave.getRooms().get(posEast).getRoomType()
                    .equals("Pit Trap")) {
                result += Constants.getMessage("CLUE_PIT");
            }
        }

        //if west of position is a wumpus or pit
        if (posWest > -1 && player.getPosition() % cave.getN() != 0) {
            if (cave.getRooms().get(posWest).getRoomType()
                    .equals("Wumpus")) {
                result += Constants.getMessage("CLUE_WUMPUS");
            }
            if (cave.getRooms().get(posWest).getRoomType()
                    .equals("Pit Trap")) {
                result += Constants.getMessage("CLUE_PIT");
            }
        }

        return (result);
    }

    public static void printStatistics() {
        System.out.println(cave.getRooms().get(player.getPosition()).getRoomDescription());
        System.out.println("Clues: " + revealClues());
        if (player.isHasWeapon()) {
            System.out.println("You are armed, and ready.");
        } else {
            System.out.println("You are unarmed!");
        }
        System.out.println("[Points: " + player.getPoints() + "]");
    }

    public static void gameOver() {
        System.out.println("***Game Over***");
        System.out.println("[Total Points: " + player.getPoints() + "]");
        System.exit(0);
    }
}
