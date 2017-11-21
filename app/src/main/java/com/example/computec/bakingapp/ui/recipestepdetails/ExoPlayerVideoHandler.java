package com.example.computec.bakingapp.ui.recipestepdetails;

import android.content.Context;
import android.net.Uri;
import android.view.SurfaceView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

class ExoPlayerVideoHandler {

    private static ExoPlayerVideoHandler instance;

    static ExoPlayerVideoHandler getInstance() {
        if (instance == null) {
            instance = new ExoPlayerVideoHandler();
        }
        return instance;
    }

    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private BandwidthMeter bandwidthMeter;

    private SimpleExoPlayer player;
    private Uri playerUri;
    private boolean isPlayerPlaying;

    private ExoPlayerVideoHandler() {}

    void prepareExoPlayerForUri(Context context, Uri uri,
                                       SimpleExoPlayerView exoPlayerView) {
        if (context != null && uri != null && exoPlayerView != null) {
            if (!uri.equals(playerUri) || player == null) {
                playerUri = uri;

                TrackSelection.Factory
                        videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

                trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

                player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

                isPlayerPlaying = true;
                bandwidthMeter = new DefaultBandwidthMeter();
                mediaDataSourceFactory = new DefaultDataSourceFactory(context,
                        Util.getUserAgent(context, "bakingapp"),
                        (TransferListener<? super DataSource>) bandwidthMeter);

                DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

                MediaSource source = new ExtractorMediaSource(playerUri,
                        mediaDataSourceFactory, extractorsFactory, null, null);

                player.prepare(source);
            }
            player.clearVideoSurface();
            player.setVideoSurfaceView(
                    (SurfaceView) exoPlayerView.getVideoSurfaceView());
            player.seekTo(player.getCurrentPosition() + 1);
            exoPlayerView.setPlayer(player);
        }
    }

    void releaseVideoPlayer() {
        if (player != null){
            player.release();
            trackSelector = null;
        }
        player = null;
    }

    void goToBackground() {
        if (player != null) {
            isPlayerPlaying = player.getPlayWhenReady();
            player.setPlayWhenReady(false);
        }
    }

    void goToForeground() {
        if (player != null) {
            player.setPlayWhenReady(isPlayerPlaying);
        }
    }
}
