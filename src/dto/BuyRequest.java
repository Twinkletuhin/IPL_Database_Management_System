package dto;

import java.io.Serializable;

import menu.Player;

public class BuyRequest implements Serializable {
  private String buyerClub;
  private Player player;

  public BuyRequest() {
    this.player = null;
    this.buyerClub = null;
  }

  public void setBuyerClub(String buyerClub) {
    this.buyerClub = buyerClub;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public String getBuyerClub() {
    return buyerClub;
  }

  public Player getPlayer() {
    return player;
  }

  public BuyRequest(String buyer, Player player) {
    this.buyerClub = buyer;
    this.player = player;
  }

  public void setBuyer(String clubName) {
    buyerClub = clubName;

  }

  public String getBuyer() {
    return buyerClub;
  }

}
