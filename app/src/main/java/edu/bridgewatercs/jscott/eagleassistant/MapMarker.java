package edu.bridgewatercs.jscott.eagleassistant;

public class MapMarker {

    //determines what is in a map marker
    private int id;
    private Double lat;
    private Double lng;
    private String title;

    //required empty method
    public MapMarker() {}

    //required actual method
    public MapMarker(int id, Double lat, Double lng, String title) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.title = title;
    }

    //the setters
    public void setID(int id) {
        this.id = id;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }
    public void setLng(Double lng) {
        this.lng = lng;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    //the getters
    public int getID() {
        return id;
    }
    public Double getLat() {
        return lat;
    }
    public Double getLng() {
        return lng;
    }
    public String getTitle() {
        return title;
    }
}
