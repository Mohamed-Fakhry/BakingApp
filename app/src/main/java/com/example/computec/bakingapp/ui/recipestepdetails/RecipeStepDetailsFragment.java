package com.example.computec.bakingapp.ui.recipestepdetails;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.computec.bakingapp.R;
import com.example.computec.bakingapp.model.Step;
import com.example.computec.bakingapp.ui.base.BaseFragment;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetailsFragment extends BaseFragment {

    static final String STEP_KEY = "step";
    private static final String SELECTED_POSITION = "seekto";

    @BindView(R.id.player_view)
    SimpleExoPlayerView simpleExoPlayerView;
    @Nullable
    @BindView(R.id.stepDescriptionTV)
    TextView stepDescriptionTV;
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.app_bar)
    AppBarLayout appBar;


    long position;
    private Step step;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private BandwidthMeter bandwidthMeter;

    SimpleExoPlayer player;
    private Uri playerUri;
    private boolean isPlayerPlaying;

    public RecipeStepDetailsFragment() {
    }

    public static RecipeStepDetailsFragment newInstance(Step step) {
        RecipeStepDetailsFragment fragment = new RecipeStepDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(STEP_KEY, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(SELECTED_POSITION, position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            step = getArguments().getParcelable(STEP_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step_details, container, false);
        position = C.TIME_UNSET;
        if (savedInstanceState != null) {
            position = savedInstanceState.getLong(SELECTED_POSITION, C.TIME_UNSET);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        setUnBinder(ButterKnife.bind(this, view));
        if (stepDescriptionTV != null) {
            stepDescriptionTV.setText(step.getDescription());
            toolbar.setTitle(step.getShortDescription());

            getBaseActivity().setSupportActionBar(toolbar);
            getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getBaseActivity().getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Log.d("text", step.toString());
        playerUri = Uri.parse(step.getVideoURL());

        //
        TrackSelection.Factory
                videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

        isPlayerPlaying = true;
        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getActivity(), "bakingapp"),
                (TransferListener<? super DataSource>) bandwidthMeter);

        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource source = new ExtractorMediaSource(playerUri,
                mediaDataSourceFactory, extractorsFactory, null, null);

        if (position != C.TIME_UNSET) {
            player.seekTo(position);
        }
        player.prepare(source);

        player.clearVideoSurface();
        player.setVideoSurfaceView(
                (SurfaceView) simpleExoPlayerView.getVideoSurfaceView());
        simpleExoPlayerView.setPlayer(player);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (player != null) {
            player.setPlayWhenReady(isPlayerPlaying);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player != null) {
            player.setPlayWhenReady(isPlayerPlaying);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            position = player.getCurrentPosition();
            isPlayerPlaying = player.getPlayWhenReady();
            player.setPlayWhenReady(false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            player.setPlayWhenReady(isPlayerPlaying);
        }
    }
}