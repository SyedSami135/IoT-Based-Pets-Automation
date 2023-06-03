package com.example.pets;



import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

public class CameraFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_camera, container, false);

        VideoView videoView = v. findViewById(R.id.vView);
//        Uri videoUri = Uri.parse("/");
//        videoView.setVideoURI(videoUri);
        videoView.setVideoPath("android.resource://" + "com.example.pets" + "/" + R.raw.vedio);
        MediaController mediaController = new MediaController(getContext());
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();


        return v;
    }
}