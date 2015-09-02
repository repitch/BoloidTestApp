package com.repitch.boloidtestapp.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.repitch.boloidtestapp.R;
import com.repitch.boloidtestapp.models.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by repitch on 02.09.15.
 */
public class JSONTasksManager {
    public JSONTasksManager(){
    }

    public void parseTasks(OnParseCompleted listener){
        new ParseTasks(listener).execute();
    }

    public interface OnParseCompleted {
        void onParseCompleted();
    }

    public class ParseTasks extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        private OnParseCompleted listener;

        public ParseTasks(OnParseCompleted listener){
            this.listener = listener;
        }

        @Override
        protected String doInBackground(Void... params) {
            Log.i("JSON", "Beginning of parsing tasks");
            try {
                URL url = new URL(Consts.JSON_TASKS_URL);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        protected void onPostExecute(String strJSON) {
            super.onPostExecute(strJSON);
            Log.i("JSON", strJSON);
            ArrayList<Task> tasks = deserializeJSON(strJSON);
            // кидаем все таски в менеджер (синглтон)
            TasksManager.getInstance().setTasks(tasks);

            listener.onParseCompleted();
        }
    }

    private ArrayList<Task> deserializeJSON(String strJSON) {
        try {
            JSONObject jsonObject = new JSONObject(strJSON);
            JSONArray jsonTasks = jsonObject.getJSONArray("tasks");
            ArrayList<Task> tasks = new ArrayList<>();
            for (int i=0; i<jsonTasks.length(); i++) {
                JSONObject jsonTask = jsonTasks.getJSONObject(i);
                Task task = new Task(jsonTask);
                Log.i("JSON", task.toString());
                tasks.add(task);
            }
            return tasks;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
