package com.example.computec.bakingapp.ui.recipestepdetails;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.computec.bakingapp.R;
import com.example.computec.bakingapp.model.Step;
import com.example.computec.bakingapp.ui.base.BaseFragment;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetailsFragment extends BaseFragment {

    static final String STEP_KEY = "step";

    @BindView(R.id.player_view)
    SimpleExoPlayerView simpleExoPlayerView;
    @Nullable
    @BindView(R.id.stepDescriptionTV)
    TextView stepDescriptionTV;

    ExoPlayerVideoHandler exoPlayerVideoHandler;

    private Step step;

    public RecipeStepDetailsFragment() {}

    public static RecipeStepDetailsFragment newInstance(Step step) {
        RecipeStepDetailsFragment fragment = new RecipeStepDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(STEP_KEY, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            step = getArguments().getParcelable(STEP_KEY);
        }
        if (getActivity().findViewById(R.id.stepTV) == null)
            setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_step_details, container, false);
    }

    @Override
    protected void setUp(View view) {
        setUnBinder(ButterKnife.bind(this, view));
        if (stepDescriptionTV != null)
            stepDescriptionTV.setText(step.getDescription());
        Log.d("text", step.toString());
        exoPlayerVideoHandler = ExoPlayerVideoHandler.getInstance();
        exoPlayerVideoHandler.prepareExoPlayerForUri(getActivity(), Uri.parse(step.getVideoURL()), simpleExoPlayerView);
    }

    @Override
    public void onStart() {
        super.onStart();
        exoPlayerVideoHandler.goToForeground();
    }

    @Override
    public void onResume() {
        super.onResume();
        exoPlayerVideoHandler.goToForeground();
    }

    @Override
    public void onPause() {
        super.onPause();
        exoPlayerVideoHandler.goToBackground();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        exoPlayerVideoHandler.releaseVideoPlayer();
    }
}