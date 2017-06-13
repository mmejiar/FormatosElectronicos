package digipro.beteam.formatoselectronicos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import digipro.beteam.model.ConfigFile;
import digipro.beteam.utils.Utils;

public class UrlPortalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_portal);

        final ConfigFile config = new ConfigFile();
        Utils.writeToStorage(config);

        final Button boton1 = (Button) findViewById(R.id.buttonGuardar);
        final TextView txtUrl = (TextView) findViewById(R.id.textUrl);
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtUrl.getText().toString() != null){
                    config.setUrl(txtUrl.getText().toString());
                    Utils.validateConfig(config);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
