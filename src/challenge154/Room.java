/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package challenge154;

/**
 *
 * @author jconner
 */
public class Room {
    
    //Location - Top Left starts at 0, Bottom Right would be Location (n * n) - 1
    private String roomType;
    private String roomValue;
    private String roomDescription;
    private boolean isPlayerHere = false;
    private boolean visited = false;
    
    public Room(String roomType, String roomValue, String roomDescription) {
        this.roomType = roomType;
        this.roomValue = roomValue;
        this.roomDescription = roomDescription;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomValue() {
        return roomValue;
    }

    public void setRoomValue(String roomValue) {
        this.roomValue = roomValue;
    }

    public boolean isIsPlayerHere() {
        return isPlayerHere;
    }

    public void setIsPlayerHere(boolean isPlayerHere) {
        this.isPlayerHere = isPlayerHere;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
}
