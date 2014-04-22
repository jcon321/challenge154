/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge154;

import constants.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author jconner
 */
public class Cave {

    //The caves dimensions n x n
    private Integer n;
    private Integer numberOfRooms;
    private List<Room> room = new ArrayList<>();

    //Room Generation Variables
    private final int wumpusPercent;
    private final int trapPercent;
    private final int goldPercent;
    private final int wepPercent;
    private final int emptyPercent;

    public Cave(Integer n) {
        this.n = n;
        this.numberOfRooms = n * n;

        wumpusPercent = (int) Math.round(0.15 * numberOfRooms);
        trapPercent = (int) Math.round(0.05 * numberOfRooms);
        goldPercent = (int) Math.round(0.15 * numberOfRooms);
        wepPercent = (int) Math.round(0.15 * numberOfRooms);
        emptyPercent = numberOfRooms - wumpusPercent - trapPercent - goldPercent - wepPercent;

        for (int x = 0; x < wumpusPercent; x++) {
            room.add(new Room("Wumpus", Constants.getMessage("UNEXPLORED_ROOM"),
                    Constants.getMessage("DESC_Wumpus")));
        }

        for (int x = 0; x < trapPercent; x++) {
            room.add(new Room("Pit Trap", Constants.getMessage("UNEXPLORED_ROOM"),
                    Constants.getMessage("DESC_Pit")));
        }

        for (int x = 0; x < goldPercent; x++) {
            room.add(new Room("Gold", Constants.getMessage("UNEXPLORED_ROOM"),
                    Constants.getMessage("DESC_Gold")));
        }

        for (int x = 0; x < wepPercent; x++) {
            room.add(new Room("Weapon", Constants.getMessage("UNEXPLORED_ROOM"), 
                    Constants.getMessage("DESC_Weapon")));
        }

        for (int x = 0; x < emptyPercent; x++) {
            if (x == 0) {
                room.add(new Room("Entrance", Constants.getMessage("ENTRANCE"),
                        Constants.getMessage("DESC_Entrance")));
            } else {
                room.add(new Room("Empty", Constants.getMessage("UNEXPLORED_ROOM"), 
                        Constants.getMessage("DESC_Empty")));
            }

        }
        Collections.shuffle(room);
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public List<Room> getRooms() {
        return room;
    }

    public void setRooms(List<Room> room) {
        this.room = room;
    }

    public void printCave() {
        String cave = "";
        for (int x = 0; x < this.numberOfRooms; x++) {
            if (!room.get(x).isIsPlayerHere()) {
                cave += room.get(x).getRoomValue();
            } else {
                cave += Constants.getMessage("PLAYER");
            }
        }
        cave = cave.replaceAll("(.{" + n + "})", "$1\n");
        System.out.println();
        System.out.println(cave);
    }
}
