package com.example.loginpage;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class ViewHolder extends RecyclerView.ViewHolder{
    PlayerView playerView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setExoPlayer(Application application, String name, String url){
        TextView textView = itemView.findViewById(R.id.tv_item);
        playerView = itemView.findViewById(R.id.exoplayer_item);
//        System.out.println(name);
//        System.out.println(url);
        textView.setText(name);
        try {
//             bandwidthmeter is used for getting default bandwidth
//            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter.Builder(application).build();
//            // track selector is used to navigate between video using a default seeker.
//            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
//
//            // we are adding our track selector to exoplayer.
//            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            ExoPlayer player = new SimpleExoPlayer.Builder(application.getApplicationContext()).build();

            // we are parsing a video url and
            // parsing its video uri.
            Uri videouri = Uri.parse(url);

            // we are creating a variable for data source
            // factory and setting its user agent as 'exoplayer_view'
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");

            // we are creating a variable for extractor
            // factory and setting it to default extractor factory.
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            // we are creating a media source with above variables
            // and passing our event handler as null,
            MediaSource mediaSource = new ExtractorMediaSource(videouri, dataSourceFactory, extractorsFactory, null, null);

            // inside our exoplayer view
            // we are setting our player
            playerView.setPlayer(player);

            // we are preparing our exoplayer
            // with media source.
            player.prepare(mediaSource);

            // we are setting our exoplayer
            // when it is ready.
            player.setPlayWhenReady(false);


        } catch (Exception e) {
            // below line is used for handling our errors.
            Log.e("TAG", "Error : " + e.toString());
        }
    }
}
