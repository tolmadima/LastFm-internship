package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lastfm.ArtistInfo.ArtistDataDto;
import com.example.lastfm.ArtistInfo.ArtistInfoDto;
import com.example.lastfm.ArtistInfo.BioDto;
import com.example.lastfm.ArtistInfo.ImageDto;
import com.example.lastfm.ArtistInfo.Stats;
import com.squareup.picasso.Picasso;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;



public class ArtistInfoActivity extends AppCompatActivity {
    Context context;
    private TextView tvNameView;
    private TextView tvPlayCount;
    private ImageView artistImage;
    private TextView tvArtistBio;
    //Вызывается массив картинок размер которых варьируется от 0-4
    //0 - самое маленькое разрешение
    //4 - самое большое разрешение
    private static final int PICTURE_SIZE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_screen);
        tvNameView = findViewById(R.id.artist_info_name);
        tvPlayCount = findViewById(R.id.artist_info_playcount);
        artistImage = findViewById(R.id.big_artist_image);
        tvArtistBio = findViewById(R.id.artist_info_bio);
        Intent intent = getIntent();
        String name = intent.getStringExtra("artistName");
        LastFMClient client = ServiceGenerator.getInstance().getLastFMClient();
        client.getArtistInfo(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArtistInfoDto>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(ArtistInfoDto info) {
                showInfo(info);
            }

            @Override
            public void onError(Throwable e) {
                String text = getString(R.string.request_error_message);
                Toast.makeText(ArtistInfoActivity.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showInfo(ArtistInfoDto info){
        ArtistDataDto data = info.getArtist();
        String artistName = data.getName();
        BioDto artistBio = data.getBio();
        Stats artistStat = data.getStats();
        String playcount = artistStat.getPlaycount();
        String bio = artistBio.getContent();
        ImageDto url = data.getImage().get(PICTURE_SIZE);
        String imageUrl = url.getText();
        tvNameView.setText(artistName);
        tvArtistBio.setText(bio);
        tvPlayCount.setText(playcount);
        Picasso.get().load(imageUrl).into(artistImage);
        artistImage.setVisibility(imageUrl != null ? View.VISIBLE : View.GONE);
    }
}