package com.repitch.boloidtestapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.repitch.boloidtestapp.R;
import com.repitch.boloidtestapp.activities.BaseActivity;
import com.repitch.boloidtestapp.models.Task;
import com.repitch.boloidtestapp.utils.JSONTasksManager;
import com.repitch.boloidtestapp.utils.TasksManager;

import java.util.ArrayList;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.utils.GeoPoint;

/**
 * Created by repitch on 01.09.15.
 */
public class MapFragment extends BaseFragment {
    private LinearLayout mDataLoadingWrap;
    private MapView mMapView;

    private GeoPoint testGeoPoint = new GeoPoint(12.02, 14.98);

    @Override
    public String getFragmentTitle() {
        return "Map";
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        // init UI
        mMapView = (MapView) rootView.findViewById(R.id.map);
        mDataLoadingWrap = (LinearLayout) rootView.findViewById(R.id.dataLoadingWrap);

        // проверим, есть ли задания в менеджере
        ArrayList<Task> tasks = TasksManager.getInstance().getTasks();
        if (tasks==null || tasks.size()==0){
            JSONTasksManager jtMngr = new JSONTasksManager();
            jtMngr.parseTasks(new JSONTasksManager.OnParseCompleted() {
                @Override
                public void onParseCompleted() {
                    showPoints();
                }
            });
        } else {
            // сфокусируемся на первом задании
            showPoints();
        }

        return rootView;
    }

    private void showPoints() {
        // прячем хеадер загрузки
        mDataLoadingWrap.setVisibility(View.INVISIBLE);
        Toast.makeText(mActivity, "Tasks uploaded from server!", Toast.LENGTH_SHORT).show();

        // отметим все новые точки на карте
        setTaskPoints();

        // сфокусируемся на первом задании
        showFirstTask();
    }

    // из списка заданий фокусируется на первом
    private void showFirstTask() {
        ArrayList<Task> tasks = TasksManager.getInstance().getTasks();
        if (tasks.size()==0){
            Toast.makeText(mActivity, "There is no task:(", Toast.LENGTH_LONG).show();
        } else {
            Task task0 = tasks.get(0);
            GeoPoint taskPoint = task0.getLocation();

            MapController mapController = mMapView.getMapController();
            mapController.setPositionAnimationTo(taskPoint);
        }
    }

    // Расставляет только обновленные задания по карте
    private void setTaskPoints() {
        ArrayList<Task> tasks = TasksManager.getInstance().getTasks();
        for (Task task: tasks){
            task.setOnMap(mActivity, mMapView);
        }
    }



    private void testBalloons(View rootView) {
        // Получаем объект MapController
        MapController mapController = mMapView.getMapController();

        // перемещаемся на тестовую точку
        mapController.setPositionAnimationTo(testGeoPoint);
        // получаем объект overlaymanager
        OverlayManager testOverlayManager = mapController.getOverlayManager();
        // новый слой
        Overlay testOverlay = new Overlay(mapController);
        OverlayItem testOverlayItem = new OverlayItem(testGeoPoint, mActivity.getResources().getDrawable(R.drawable.geopoint));
        testOverlay.addOverlayItem(testOverlayItem);
        testOverlayManager.addOverlay(testOverlay);

        // тестовый балун
        BalloonItem testBalloon = new BalloonItem(mActivity, testGeoPoint);
        testBalloon.setText("Here is test balloon");
        testOverlayItem.setBalloonItem(testBalloon);
    }
}
