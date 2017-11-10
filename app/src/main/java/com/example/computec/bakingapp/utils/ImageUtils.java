package com.example.computec.bakingapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * Created by Mohamed Fakhry on 10/11/2017.
 */

public class ImageUtils {

    public static Bitmap retriveVideoFrameFromVideo(String videoPath)
            throws Throwable {

        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;

        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());

            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
                            + e.getMessage());
        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

    public static void getImageWithProgress(final ImageView imageView, Bitmap bitmap) {
        Context context = imageView.getContext();
        final ViewGroup parent = (ViewGroup) imageView.getParent();
        final ProgressBar progressBar = new ProgressBar(context);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        parent.removeAllViews();
        parent.addView(progressBar);
        Glide.with(context)
                .load(stream.toByteArray())
                .centerCrop()
                .listener(new RequestListener<byte[], GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, byte[] model, Target<GlideDrawable> target, boolean isFirstResource) {
                        parent.removeAllViews();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, byte[] model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        parent.removeAllViews();
                        parent.addView(imageView);
                        return false;
                    }
                })
                .into(imageView);
    }

}
