package uni.fmi.masters.myfootballapp.models;

public class Countries {

    private int id;
    private String name;
    private String capital;
    private String region;

    public Countries(int id, String name, String capital, String region) {
        this.id = id;
        this.name = name;
        this.capital = capital;
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
