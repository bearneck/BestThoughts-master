package com.grcen.bestthoughts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.System.load;

public class detail extends Activity {
    public static final String IMAGE_URL = "image_Url";
    public static final String ICON_URL = "icon_Url";
    public static final String ZAN_URL = "zan_Url";
    public static final String DOWN_URL = "down_Url";
    public static final String SHARE_URL = "share_Url";
    public static final String CONTEXT_URL = "context_Url";
    public static final String CONTENT_id = "content_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        Bundle b = getIntent().getExtras();

        String iconurl = b.getString(ICON_URL);
        final String content = b.getString(CONTEXT_URL);
        final String image = b.getString(IMAGE_URL);
        final int zanurl = b.getInt(ZAN_URL);
        final int downurl = b.getInt(DOWN_URL);
        int share = b.getInt(SHARE_URL);

        CircleImageView iconbutton = (CircleImageView) findViewById(R.id.icon);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        TextView contenttext = (TextView) findViewById(R.id.content);
        final TextView zantext = (TextView) findViewById(R.id.zan);
        final TextView downtext = (TextView) findViewById(R.id.down);
        TextView sharetext = (TextView) findViewById(R.id.share);
        final ImageView zanimage = (ImageView)findViewById(R.id.zanimage);
        final ImageView zannoimage = (ImageView)findViewById(R.id.downimage);
        LinearLayout up = (LinearLayout) findViewById(R.id.upview);
        LinearLayout down =(LinearLayout) findViewById(R.id.downview);

        Glide.with(getApplicationContext()).load(iconurl).error(R.mipmap.oherro).into(iconbutton);
        loadIntoUseFitWidth(getApplicationContext(), image, R.mipmap.load, imageView);

        contenttext.setText(content);
        zantext.setText(zanurl + "");
        downtext.setText(downurl + "");
        sharetext.setText(share + "");

        ImageButton back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), activity_image.class);
                intent.putExtra(activity_image.IMAGE_URL, image);
                getApplicationContext().startActivity(intent);
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                int zannew = zanurl+1;
                zantext.setText(zannew+ " ");
                zantext.setTextColor(R.color.zancolor);
                zanimage.setImageResource(R.mipmap.up);
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                int zannonew = downurl+1;
                downtext.setText(zannonew+ " ");
                downtext.setTextColor(R.color.zancolor);
                zannoimage.setImageResource(R.mipmap.down);
            }
        });

    }

    /**
     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
     */
    public static void loadIntoUseFitWidth(Context context, final String imageUrl, int errorImageId, final ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        return false;
                    }
                })
                .placeholder(errorImageId)
                .error(errorImageId)
                .into(imageView);
    }
}
