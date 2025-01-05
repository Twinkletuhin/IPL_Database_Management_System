package dto;

import java.io.Serializable;

import menu.Player;

public class SellRequest implements Serializable {
    private String clubName;
    private Player player;
    private boolean isStatus;
    public SellRequest(){
        this.clubName=null;
        this.player=null;
    }
    public SellRequest(String clubName,Player player){
        this.clubName=clubName;
        this.player=player;
    }
    public void setSellStatus(boolean status){
        isStatus=status;
    }
    public boolean getSellStatus(boolean status){
        return isStatus;
    }
    public String getClubName() {
        return clubName;
    }
    public Player getPlayer() {
        return player;
    }
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    

}
