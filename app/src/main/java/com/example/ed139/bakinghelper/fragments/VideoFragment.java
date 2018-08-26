package com.example.ed139.bakinghelper.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ed139.bakinghelper.R;
import com.example.ed139.bakinghelper.models.StepDeets;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;

public class VideoFragment extends Fragment {

    // save state with help from https://stackoverflow.com/questions/51075420/android-exoplayer-save-state

    private static final String VIDEO_CURRENT_POSITION = "video_current_position";
    private String url;
    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private String videoPath;

    private boolean playWhenReady;
    private int currentWindow;
    private long playerPosition;

    // Mandatory empty constructor
    public VideoFragment() {
    }

    // get data either from intent or bundle depending on two pane or single pane
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        // Load the saved state of the video if there is one
        if(savedInstanceState == null) {
            playWhenReady = true;
            currentWindow = 0;
            playerPosition = 0;
        } else {
            playWhenReady = savedInstanceState.getBoolean("playWhenReady");
            currentWindow = savedInstanceState.getInt("currentWindow");
            playerPosition = savedInstanceState.getLong(VIDEO_CURRENT_POSITION);
        }

        Bundle bundle = this.getArguments();
        Gson gson = new Gson();
        Intent mIntent = getActivity().getIntent();

        if (bundle != null) {
            videoPath = bundle.getString("video_path");
        } else if (mIntent != null){
            String currentStringStep = mIntent.getStringExtra("key");
            StepDeets stepDeets = gson.fromJson(currentStringStep, StepDeets.class);
            videoPath = stepDeets.getUrl();
        }
    }

    // Inflates the video layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.video_fragment, container, false);

        mPlayerView = rootView.findViewById(R.id.playerView);

        if (!videoPath.isEmpty()) {
            initializePlayer(Uri.parse(videoPath));
        } else {
            //do nothing
        }

        // Return the root view
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outstate) {
        super.onSaveInstanceState(outstate);
        if(mExoPlayer != null) {
            playerPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            playWhenReady = mExoPlayer.getPlayWhenReady();

            outstate.putBoolean("playWhenReady", playWhenReady);
            outstate.putInt("currentWindow", currentWindow);
            outstate.putLong(VIDEO_CURRENT_POSITION, playerPosition);
        }
    }

    private void initializePlayer(Uri videoUrl) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "BakingAssistant");
            MediaSource mediaSource = new ExtractorMediaSource(videoUrl, new DefaultDataSourceFactory(getContext(), userAgent),
                    new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(playWhenReady);
            mExoPlayer.seekTo(playerPosition);
        }
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mExoPlayer == null) {
            // do nothing
        } else {
            playerPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            playerPosition = mExoPlayer.getCurrentPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mExoPlayer != null && playerPosition != 0 && !TextUtils.isEmpty(videoPath)) {
            initializePlayer(Uri.parse(videoPath));
            mExoPlayer.seekTo(playerPosition);
            mExoPlayer.setPlayWhenReady(playWhenReady);
        }
    }
}
