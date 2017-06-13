package digipro.beteam.utils;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

import digipro.beteam.model.ConfigFile;

public class Utils {

    public static void validateConfig(ConfigFile config){
        ConfigFile cfg;
        if(config.getUrl() == null) {
            writeToStorage(config);
        }
        else{
            String var = readFromSD();
            cfg = deserializeJson(var);
            if(cfg.getUrl() == null){
                writeToStorage(config);
            }
        }
    }

    public static void writeToStorage(ConfigFile config) {

            Gson gson = new Gson();
            String json = gson.toJson(config);

            String TAG = "MyFile";
            File root = null;
            try {
                // check for SDcard
                root = Environment.getExternalStorageDirectory();
                Log.i(TAG, "path.." + root.getAbsolutePath());

                //check sdcard permission
                if (root.canWrite()) {
                    File fileDir = new File(root.getAbsolutePath());
                    fileDir.mkdirs();

                    File file = new File(fileDir, "cfgfile.txt");
                    FileWriter filewriter = new FileWriter(file);
                    BufferedWriter out = new BufferedWriter(filewriter);
                    out.write(json);
                    out.close();
                }
            } catch (IOException e) {
                Log.e("ERROR:---", "Could not write file to SDCard" + e.getMessage());
            }

    }

    public static String readFromSD(){
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard,"cfgfile.txt");
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        }
        catch (IOException e) {
        }
        return text.toString();
    }

    public static ConfigFile deserializeJson(String jsonString){
        Gson gson = new Gson();
        ConfigFile config;
        config = gson.fromJson(jsonString, ConfigFile.class);
        return config;
    }
}
