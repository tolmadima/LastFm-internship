
package com.example.lastfm;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ArtistInfo {

    @SerializedName("artist")
    @Expose
    private ArtistData artist;

    public ArtistData getArtist() {
        return artist;
    }

    public void setArtist(ArtistData artist) {
        this.artist = artist;
    }

}


