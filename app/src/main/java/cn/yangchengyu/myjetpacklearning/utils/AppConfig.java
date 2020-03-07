package cn.yangchengyu.myjetpacklearning.utils;

import android.content.res.AssetManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import cn.yangchengyu.myjetpacklearning.model.BottomBar;
import cn.yangchengyu.myjetpacklearning.model.Destination;

public class AppConfig {

    private static HashMap<String, Destination> destConfig;
    private static BottomBar bottomBar;

    public static BottomBar getBottomBarConfig() {
        if (bottomBar == null) {
            String content = parseFile("main_tabs_config.json");
            bottomBar = JSON.parseObject(content, BottomBar.class);
        }
        return bottomBar;
    }

    public static HashMap<String, Destination> getDestConfig() {
        if (destConfig == null) {
            String content = parseFile("destination.json");
            destConfig = JSON.parseObject(content, new TypeReference<HashMap<String, Destination>>() {
            });
        }
        return destConfig;
    }

    private static String parseFile(String fileName) {
        AssetManager assets = AppGlobals.getApplication().getAssets();
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder builder = new StringBuilder();
        try {
            inputStream = assets.open(fileName);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return builder.toString();
    }
}
