package net.mantucon.baracus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import net.mantucon.baracus.annotations.Bean;
import net.mantucon.baracus.service.CustomerService;
import net.mantucon.baracus.util.Logger;

public class HelloAndroidActivity extends Activity {

    static final Logger logger = new Logger(HelloAndroidActivity.class);

    static {
        Logger.setTag("TUTORIAL_APP");
    }

    @Bean
    CustomerService customerService;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(net.mantucon.baracus.R.menu.main, menu);
	return true;
    }

    public void onButtonTestClicked(View v) {
        customerService.testService();
    }

}

