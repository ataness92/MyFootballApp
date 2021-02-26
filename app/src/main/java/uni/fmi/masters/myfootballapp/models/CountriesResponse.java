package uni.fmi.masters.myfootballapp.models;

import java.util.List;

public class CountriesResponse {

    private List<Countries> data;

    public CountriesResponse(List<Countries> data) {
        this.data = data;
    }

    public List<Countries> getData() {
        return data;
    }

    public void setData(List<Countries> data) {
        this.data = data;
    }
}
