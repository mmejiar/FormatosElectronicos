package digipro.beteam.utils;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

import digipro.beteam.formatoselectronicos.MainActivity;
import digipro.beteam.model.ConfigFile;

public class Utils {

    public static void validateConfig(ConfigFile config){
        ConfigFile cfg;
        String var;
        var = readFromSD();
        if(var.trim().length() > 0){
            cfg = deserializeJson(var);
            cfg.setUrl(config.getUrl().toString());
            writeToStorage(cfg);
        } else{
            writeToStorage(config);
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
        String texto;
        if(file.exists()){
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
             texto = text.toString();
        } else{
            texto = "";
        }
        return texto;
    }

    public static ConfigFile deserializeJson(String jsonString){
        Gson gson = new Gson();
        ConfigFile config;
        config = gson.fromJson(jsonString, ConfigFile.class);
        return config;
    }

    public static void SendMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
