package com.repitch.boloidtestapp.models;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.View;

import com.repitch.boloidtestapp.R;
import com.repitch.boloidtestapp.activities.BaseActivity;
import com.repitch.boloidtestapp.ui.fragments.TaskDetailFragment;
import com.repitch.boloidtestapp.utils.TasksManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.overlay.balloon.OnBalloonListener;
import ru.yandex.yandexmapkit.utils.GeoPoint;

/**
 * Created by repitch on 02.09.15.
 */
public class Task implements Serializable {

    private static final String ID_NAME = "ID";
    private static final String TITLE_NAME = "title";
    private static final String DATE_NAME = "date";
    private static final String TEXT_NAME = "text";
    private static final String LONGTEXT_NAME = "longText";
    private static final String DURATIONLIMIT_NAME = "durationLimitText";
    private static final String PRICE_NAME = "price";
    private static final String LOCATIONTEXT_NAME = "locationText";
    private static final String LOCATION_NAME = "location";
    private static final String ZOOMLEVEL_NAME = "zoomLevel";
    private static final String PRICES_NAME = "prices";
    private static final String TRANSLATION_NAME = "translation";

    private int mID;

    public String getTitle() {
        return mTitle;
    }

    private String mTitle;
    private int mDate;

    public String getText() {
        return mText;
    }

    public String getLongText() {
        return mLongText;
    }

    private String mText;
    private String mLongText;
    private String mDurationLimitText;
    private int mPrice;
    private String mLocationText;

    public GeoPoint getLocation() {
        return mLocation;
    }

    private GeoPoint mLocation;
    private int mZoomLevel;
    private Price[] mPrices;
    private boolean mTranslation;

    @Override
    public String toString() {
        String str = "Task ("+mID+"): "+mTitle;
        return str;
    }

    public Task(JSONObject jsonTask) throws JSONException {
        mID = jsonTask.getInt(ID_NAME);
        mTitle = jsonTask.getString(TITLE_NAME);
        mDate = jsonTask.getInt(DATE_NAME);
        mText = jsonTask.getString(TEXT_NAME);
        mLongText = jsonTask.getString(LONGTEXT_NAME);
        mDurationLimitText = jsonTask.getString(DURATIONLIMIT_NAME);
        mPrice = jsonTask.getInt(PRICE_NAME);
        mLocationText = jsonTask.getString(LOCATIONTEXT_NAME);

        JSONObject locationObj = jsonTask.getJSONObject(LOCATION_NAME);
        mLocation = new GeoPoint(locationObj.getDouble("lat"), locationObj.getDouble("lon"));

        mZoomLevel = jsonTask.getInt(ZOOMLEVEL_NAME);
        mZoomLevel = jsonTask.getInt(ZOOMLEVEL_NAME);

        JSONArray jsonPrices = jsonTask.getJSONArray(PRICES_NAME);
        mPrices = new Price[jsonPrices.length()];
        for (int i=0; i<jsonPrices.length(); i++){
            JSONObject jsonPrice = jsonPrices.getJSONObject(i);
            Price price = new Price(jsonPrice);
            mPrices[i] = price;
        }
        mTranslation = jsonTask.getBoolean(TRANSLATION_NAME);
    }

    public void setOnMap(final Context context, MapView mapView){
        // Получаем объект MapController
        MapController mapController = mapView.getMapController();

        // получаем объект overlaymanager
        OverlayManager testOverlayManager = mapController.getOverlayManager();
        // новый слой
        Overlay testOverlay = new Overlay(mapController);
        OverlayItem testOverlayItem = new OverlayItem(mLocation, context.getResources().getDrawable(R.drawable.geopoint));
        testOverlay.addOverlayItem(testOverlayItem);
        testOverlayManager.addOverlay(testOverlay);

        // тестовый балун
        BalloonItem testBalloon = new BalloonItem(context, mLocation);
        testBalloon.setText(mTitle);
        testOverlayItem.setBalloonItem(testBalloon);

        testBalloon.setOnBalloonListener(new OnBalloonListener() {
            @Override
            public void onBalloonViewClick(BalloonItem balloonItem, View view) {
                // открыть детальное описание задания
                GeoPoint gp = balloonItem.getGeoPoint();
                Task curTask = null;
                for (Task task: TasksManager.getInstance().getTasks()){
                    if (task.getLocation().equals(gp)){
                        Log.i("TASK", "Clicked on balloon. " + task.toString());
                        curTask = task;
                        break;
                    }
                }
                ((BaseActivity) context).launchFragment(
                        TaskDetailFragment.newInstance(curTask),
                        TaskDetailFragment.getFragmentTag()
                );
            }

            @Override
            public void onBalloonShow(BalloonItem balloonItem) {

            }

            @Override
            public void onBalloonHide(BalloonItem balloonItem) {

            }

            @Override
            public void onBalloonAnimationStart(BalloonItem balloonItem) {

            }

            @Override
            public void onBalloonAnimationEnd(BalloonItem balloonItem) {

            }
        });

    }
}
