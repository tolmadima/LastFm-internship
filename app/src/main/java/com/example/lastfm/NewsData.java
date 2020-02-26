package com.example.lastfm;

public class NewsData {
   private String Header;
   private String Image;
   private String Description;


    NewsData(String Header, String Image, String Text){
        this.Header = Header;
        this.Image = Image;
        this.Description = Text;
    }

    public String getHeader(){return Header;}
    public String getImage(){return Image;}
    public String getDescription(){return Description;}
}
