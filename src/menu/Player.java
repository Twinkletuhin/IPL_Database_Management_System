package menu;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Serializable {
    private String name;
    private String country;
    private String position;
    private String club;
    private int age;
    private Integer number;
    private int weeklySalary;
    private double height;
    private String imagePath=new String(getClass().getResource("/playerimage/default.png").toExternalForm());
    private String countryPath;//=new String(getClass().getResource("/flags/bd.png").toExternalForm());

    public Player(String name, String country, int age, double height, String club, String position, Integer number,
            int weeklySalary) {
        this.name = name;
        this.country = country;
        this.position = position;
        this.club = club;
        this.age = age;
        this.number = number;
        this.weeklySalary = weeklySalary;
        this.height = height;
    }

    public Player() {

    }
    public Player(Player p){
        this.name=p.name;
        this.country=p.country;
        this.position=p.position;
        this.club=p.club;
        this.age=p.age;
        this.number=p.number;
        this.weeklySalary=p.weeklySalary;
        this.height=p.height;
        
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setWeeklySalary(int weeklySalary) {
        this.weeklySalary = weeklySalary;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getPosition() {
        return position;
    }

    public String getClub() {
        return club;
    }

    public int getAge() {
        return age;
    }

    public Integer getNumber() {
        return number;
    }

    public int getWeeklySalary() {
        return weeklySalary;
    }

    public double getHeight() {
        return height;
    }
    
    public String getImagePath() {
        return imagePath;
    }

    public String getCountryFlagPath() {
        return countryPath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setCountryFlagPath(String countryPath) {
        this.countryPath = countryPath;
    }

    @Override

    public String toString() {
        return String.format("%-20s %-15s %-5d %-6.2f %-30s %-15s %-10s %-15d",
                name, country, age, height, club, position,
                (number != null ? number : ""), weeklySalary);
    }

    public static String getHeader() {
        return String.format("%-20s %-15s %-5s %-6s %-30s %-15s %-10s %-15s",
                "Name", "Country", "Age", "Height", "Club", "Position", "Number", "Weekly Salary");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Player player = (Player) obj;
        return Objects.equals(name, player.name); // Replace `name` with the unique identifier
    }

    @Override
    public int hashCode() {
        return Objects.hash(name); // Replace `name` with the unique identifier
    }

}
