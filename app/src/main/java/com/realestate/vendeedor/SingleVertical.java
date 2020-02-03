package com.realestate.vendeedor;

public class SingleVertical {

    private String name, prize,sqft,ago,sr;
    private String image;

    public SingleVertical( ) {

    }

    public SingleVertical(String name, String prize,String sqft,String ago,String sr,String image) {
        this.name = name;
        this.prize = prize;
        this.image = image;
        this.sqft=sqft;
        this.ago=ago;
        this.sr=sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getSr() {
        return sr;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public void setSqft(String sqft) {
        this.sqft = sqft;
    }

    public void setAgo(String ago) {
        this.ago = ago;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getPrize() {
        return prize;
    }

    public String getSqft() {
        return sqft;
    }

    public String getAgo() {
        return ago;
    }

    public String  getImage() {
        return image;
    }
}