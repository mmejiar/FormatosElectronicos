package digipro.beteam.formatoselectronicos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import digipro.beteam.model.ConfigFile;
import digipro.beteam.utils.Utils;
import self.philbrown.droidQuery.$;
import self.philbrown.droidQuery.AjaxOptions;
import self.philbrown.droidQuery.Function;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        goToConfig();

        try {
            $.with(this);
            $.ajax(new AjaxOptions().url("http://demosbeteam.azurewebsites.net/default.aspx/ObtieneConfigConexion")
                    .type("POST")
                    .dataType("json")
                    .contentType("application/json; charset=utf-8")
                    .context(this)
                    .success(new Function() {
                        @Override
                        public void invoke($ droidQuery, Object... params) {
                            try {
                                AjaxResponse response = new Gson().fromJson(((JSONObject) params[0]).getString("d"), AjaxResponse.class);
                                LinkedTreeMap<String, Object> services = (LinkedTreeMap<String, Object>) response.ReturnedObject;

                                //Se escribe en el archivo los datos de configuración
                                ConfigFile config;
                                String var = Utils.readFromSD();
                                config = Utils.deserializeJson(var);
                                if(config.getUrl().toString().trim().length() > 0){
                                    config.setWsusuario(services.get("wsusuario").toString());
                                    config.setWsimagenes( services.get("wsimagenes").toString());
                                    config.setWcffiletransfer(services.get("wcffiletransfer").toString());
                                    Utils.writeToStorage(config);
                                }
                            } catch (Exception ex) {
                                String error = ex.getMessage();
                                Utils.SendMessage(MainActivity.this, error);
                            }
                        }
                    }).error(new Function() {
                        @Override
                        public void invoke($ droidQuery, Object... params) {
                            int statusCode = (Integer) params[1];
                            String error = (String) params[2];
                            Log.e("Ajax", statusCode + " " + error);
                        }
                    }));

        } catch (Exception ex) {
            String error = ex.getMessage();
            Utils.SendMessage(MainActivity.this, error);
        }
    }

    private void goToConfig(){
        final ImageButton btnConfig = (ImageButton) findViewById(R.id.btnConfig);
        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UrlPortalActivity.class);
                startActivity(intent);
            }
        });
    }
}
