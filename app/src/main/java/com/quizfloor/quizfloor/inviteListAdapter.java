package com.quizfloor.quizfloor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.internal.ImageDownloader;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;

/**
 * Created by keyul on 7/28/2015.
 */
public class inviteListAdapter extends ArrayAdapter<JSONObject> {


    private final Context context;
    private final List<JSONObject> invitableFriends;
    private ImageView profilePicView;

    public inviteListAdapter(Context context, List<JSONObject> invitableFriends) {
        super(context, R.layout.invite_adapter, invitableFriends);
        this.context = context;
        this.invitableFriends = invitableFriends;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View listItemView = inflater.inflate(R.layout.invite_adapter, parent, false);

        profilePicView = (ImageView) listItemView.findViewById(R.id.inviteListItemProfilePic);
        TextView nameView = (TextView) listItemView.findViewById(R.id.inviteListItemName);
       // CheckBox checkList = (CheckBox)listItemView.findViewById(R.id.FriendcheckBox);
        final ImageView checkBox = (ImageView) listItemView.findViewById(R.id.inviteListItemCheckbox);

        JSONObject currentUser = invitableFriends.get(position);
        JSONObject pictureJson = currentUser.optJSONObject("picture").optJSONObject("data");

        new ImageDownloader(profilePicView).execute(pictureJson.optString("url"));

        nameView.setText(currentUser.optString("first_name"));

        checkBox.setOnTouchListener(new View.OnTouchListener() {
            boolean checked = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // toggle image
                if (checked) {
                    checked = false;
                    checkBox.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
                } else {
                    checked = true;
                    checkBox.setImageResource(R.drawable.ic_check_box_black_24dp);
                }
                return false;
            }
        });

        return listItemView;
    }

    class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public ImageDownloader(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }

           // mIcon = getRoundedShape(mIcon);
            return mIcon;
        }
        public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
            int targetWidth = 50;
            int targetHeight = 50;
            Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                    targetHeight,Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(targetBitmap);
            Path path = new Path();
            path.addCircle(((float) targetWidth - 1) / 2,
                    ((float) targetHeight - 1) / 2,
                    (Math.min(((float) targetWidth),
                            ((float) targetHeight)) / 2),
                    Path.Direction.CCW);

            canvas.clipPath(path);
            Bitmap sourceBitmap = scaleBitmapImage;
            canvas.drawBitmap(sourceBitmap,
                    new Rect(0, 0, sourceBitmap.getWidth(),
                            sourceBitmap.getHeight()),
                    new Rect(0, 0, targetWidth, targetHeight), null);
            return targetBitmap;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    }