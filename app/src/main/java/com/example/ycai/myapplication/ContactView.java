package com.example.ycai.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.model.IContact;
import com.example.ycai.myapplication.helper.DownloadImageTask;

/**
 * A custom {@link GridLayout} representing one contact.
 */
public class ContactView extends GridLayout {
    /**
     * Photo specifications
     */
    public final int SQUARE_PHOTO_SIZE_IN_DP = dpToPixel(80);
    public final Spec PHOTO_ROW_SPEC = spec(0, 2);
    public static final Spec PHOTO_COLUMN_SPEC = spec(0);
    public static final String PHOTO_URL_ROOT = "http://54.72.181.8/yolo/";

    /**
     * Name specifications
     */
    public final int NAME_WIDTH = dpToPixel(200);
    public final int NAME_HEIGHT = LayoutParams.WRAP_CONTENT;
    public static final Spec NAME_ROW_SPEC = spec(0);
    public static final Spec NAME_COLUMN_SPEC = spec(1);
    public static final int NAME_TEXT_SIZE = 20;

    /**
     * Status specifications
     */
    public final int STATUS_WIDTH = dpToPixel(200);
    public final int STATUS_HEIGHT = LayoutParams.WRAP_CONTENT;
    public static final Spec STATUS_ROW_SPEC = spec(1);
    public static final Spec STATUS_COLUMN_SPEC = spec(1);
    public static final int STATUS_TEXT_SIZE = 10;

    /**
     * Favorite button specifications
     */
    public final int FAVORITE_BUTTON_WIDTH = dpToPixel(50);
    public final int FAVORITE_BUTTON_HEIGHT = dpToPixel(50);
    public static final Spec FAVORITE_BUTTON_ROW_SPEC = spec(0, 2);
    public static final Spec FAVORITE_BUTTON_COLUMN_SPEC = spec(2);
    public static final int MARKED_FAVORITE_IMAGE_BUTTON = R.drawable.abc_btn_rating_star_on_mtrl_alpha;
    public static final int NOT_MARKED_FAVORITE_IMAGE_BUTTON = R.drawable.abc_btn_rating_star_off_mtrl_alpha;

    /**
     * injected contact
     */
    private final IContact contact;

    public ContactView(IContact contact, Context context) {
        this(contact, context, null);
    }

    public ContactView(IContact contact, Context context, AttributeSet attrs) {
        this(contact, context, attrs, 0);
    }

    public ContactView(IContact contact, Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.contact = contact;

        initGridLayout();
        addViews();
    }

    /**
     * setting up the grid layout
     */
    private void initGridLayout() {
        setColumnCount(3);
        setRowCount(2);
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        setLayoutParams(layoutParams);
    }

    /**
     * add all the view to the grid layout
     */
    private void addViews() {
        addView(getPhotoView());
        addView(getContactNameView());
        addView(getFavoriteButton());
        addView(getContactStatusView());
    }

    /**
     *
     * @return contact's photo view
     */
    public ImageView getPhotoView() {
        LayoutParams layoutParams = createLayoutParams(SQUARE_PHOTO_SIZE_IN_DP, SQUARE_PHOTO_SIZE_IN_DP, PHOTO_ROW_SPEC, PHOTO_COLUMN_SPEC);
        ImageView photoImageView = new ImageView(getContext());
        photoImageView.setLayoutParams(layoutParams);
        new DownloadImageTask(photoImageView).execute(PHOTO_URL_ROOT + contact.getPhotoId());
        return photoImageView;
    }

    /**
     *
     * @return contact's name text view
     */
    public TextView getContactNameView() {
        LayoutParams layoutParams = createLayoutParams(NAME_WIDTH, NAME_HEIGHT, NAME_ROW_SPEC, NAME_COLUMN_SPEC);
        layoutParams.setGravity(Gravity.LEFT | Gravity.RIGHT);
        TextView contactNameView = new TextView(getContext());
        contactNameView.setLayoutParams(layoutParams);
        contactNameView.setText(contact.getFirstName() + " " + contact.getLastName());
        contactNameView.setTextSize(NAME_TEXT_SIZE);
        return contactNameView;
    }

    /**
     *
     * @return contact's status text view
     */
    public TextView getContactStatusView() {
        LayoutParams layoutParams = createLayoutParams(STATUS_WIDTH, STATUS_HEIGHT, STATUS_ROW_SPEC, STATUS_COLUMN_SPEC);
        layoutParams.setGravity(Gravity.LEFT | Gravity.RIGHT | Gravity.TOP | Gravity.BOTTOM);
        TextView contactStatusView = new TextView(getContext());
        contactStatusView.setLayoutParams(layoutParams);
        contactStatusView.setText(contact.getStatus());
        contactStatusView.setTextSize(STATUS_TEXT_SIZE);
        return contactStatusView;
    }

    /**
     *
     * @return favorite button view
     */
    public ImageButton getFavoriteButton() {
        LayoutParams layoutParams = createLayoutParams(FAVORITE_BUTTON_WIDTH, FAVORITE_BUTTON_HEIGHT, FAVORITE_BUTTON_ROW_SPEC, FAVORITE_BUTTON_COLUMN_SPEC);
        layoutParams.setGravity(Gravity.CENTER_VERTICAL);
        ImageButton favoriteButton = new ImageButton(getContext());
        if (contact.isFavorite()) {
            favoriteButton.setImageResource(R.drawable.abc_btn_rating_star_on_mtrl_alpha);
        }else{
            favoriteButton.setImageResource(R.drawable.abc_btn_rating_star_off_mtrl_alpha);
        }
        favoriteButton.setOnClickListener(new FavoriteButtonOnClickListener(contact));
        favoriteButton.setLayoutParams(layoutParams);
        return favoriteButton;
    }

    /**
     *
     * @param width layoutwidth
     * @param height layoutheight
     * @param rowSpec rowspec
     * @param columnSpec columnspec
     * @return
     */
    @NonNull
    private LayoutParams createLayoutParams(int width, int height, Spec rowSpec, Spec columnSpec) {
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        layoutParams.rowSpec = rowSpec;
        layoutParams.columnSpec = columnSpec;
        return layoutParams;
    }

    /**
     *
     * @param dp dp value to convert in pixel
     * @return converted pixel
     */
    public int dpToPixel(float dp){
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     *
     * @return the {@link IContact}
     */
    public IContact getContact() {
        return contact;
    }


    /**
     * Favorite button on click listener
     */
    private class FavoriteButtonOnClickListener implements OnClickListener {
        private IContact contact;

        public FavoriteButtonOnClickListener(IContact contact) {
            this.contact = contact;
        }

        /**
         * Should mark as favorite if it is not (and oppositely)
         * @param v the view
         */
        @Override
        public void onClick(View v) {
            if (!contact.isFavorite()) {
                SplashScreen.application.markAsFavorite(contact);
                ((ImageButton)v).setImageResource(MARKED_FAVORITE_IMAGE_BUTTON);
            }else{
                SplashScreen.application.unmarkAsFavorite(contact);
                ((ImageButton)v).setImageResource(NOT_MARKED_FAVORITE_IMAGE_BUTTON);
            }
        }
    }
}
