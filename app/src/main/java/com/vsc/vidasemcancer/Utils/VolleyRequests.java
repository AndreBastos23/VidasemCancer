package com.vsc.vidasemcancer.Utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.widget.ImageView;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.vsc.vidasemcancer.VidaSemCancer;

import java.io.UnsupportedEncodingException;

public class VolleyRequests implements Html.ImageGetter {


    private Bitmap bp;

    public Bitmap getImage(String url) {
        try {
            url = new String(url.getBytes("ISO-8859-1"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Cache cache = VidaSemCancer.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);

        if (entry != null) {
            bp = BitmapFactory.decodeByteArray(entry.data, 0, entry.data.length);
        } else {
            ImageRequest request = new ImageRequest(url,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            bp = bitmap;
                        }
                    },
                    0,
                    0, ImageView.ScaleType.FIT_CENTER,
                    null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
// Access the RequestQueue through your singleton class.
            VidaSemCancer.getInstance().addToRequestQueue(request);
        }


        return bp;
        // Cached response doesn't exists. Make network call here
    }


    @Override
    public Drawable getDrawable(String source) {
        //TODO: Find another way of getting the right image
        source = source.replace("-300x94", "-1024x322");
        Drawable drawFromPath = new BitmapDrawable(VidaSemCancer.getInstance().getResources(), getImage(source));
        int width = drawFromPath.getIntrinsicWidth();
        int height = drawFromPath.getIntrinsicHeight();
        drawFromPath.setBounds(0, 0, width > 0 ? width : 0, height > 0 ? height : 0);

        return drawFromPath;

    }

    public Bitmap getImageFromSpanned(Spanned spanned) {
        ImageSpan[] imageSpan = spanned.getSpans(0, 10, ImageSpan.class);
        if (imageSpan.length == 0) {
            return null;
        }

        String url = imageSpan[0].getSource().replace("-300x94", "-1024x322");
        return getImage(url);

    }
}

