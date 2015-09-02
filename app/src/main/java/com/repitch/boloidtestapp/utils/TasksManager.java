package com.repitch.boloidtestapp.utils;

import com.repitch.boloidtestapp.models.Task;

import java.util.ArrayList;

/**
 * Created by repitch on 02.09.15.
 * SINGLETON
 */
public class TasksManager {

    private static TasksManager sInstance;

    private ArrayList<Task> mTasks;

    public ArrayList<Task> getTasks() {
        return mTasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        mTasks = tasks;
    }

    public static synchronized TasksManager getInstance() {
        if (sInstance == null) {
            sInstance = new TasksManager();
        }
        return sInstance;
    }
}
