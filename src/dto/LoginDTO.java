package dto;

import java.io.Serializable;

public class LoginDTO  implements Serializable{

    private String clubName;
    private String password;
    private boolean status;

    public LoginDTO(String clubName, String password, boolean status) {
        this.clubName = clubName;
        this.password = password;
        this.status = status;
    }
    public LoginDTO(String clubName, String password) {
        this.clubName = clubName;
        this.password = password;
    }
    public LoginDTO() {
    }
    public String getClubName() {
        return clubName;
    }
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
