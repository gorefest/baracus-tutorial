package net.mantucon.baracus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import net.mantucon.baracus.annotations.Bean;
import net.mantucon.baracus.application.ApplicationContext;
import net.mantucon.baracus.dao.BankAccountDao;
import net.mantucon.baracus.dao.CustomerDao;
import net.mantucon.baracus.model.BankAccount;
import net.mantucon.baracus.model.Customer;
import net.mantucon.baracus.orm.ObjectReference;
import net.mantucon.baracus.service.BankAccountService;
import net.mantucon.baracus.service.ConfigurationService;
import net.mantucon.baracus.service.CustomerService;
import net.mantucon.baracus.util.Logger;

public class HelloAndroidActivity extends Activity {

    static final Logger logger = new Logger(HelloAndroidActivity.class);

    static {
        Logger.setTag("TUTORIAL_APP");
    }

    @Bean
    CustomerService customerService;

    @Bean
    BankAccountService bankAccountService;

    @Bean
    ConfigurationService configurationService;

    @Bean
    CustomerDao customerDao;

    @Bean
    BankAccountDao bankAccountDao;




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

        if (!configurationService.isApplicationInitializationDone()) {
            initData();
            configurationService.setApplicationInitializationDone(true);
        }
    }

    private void initData() {
        Customer johnDoe = new Customer();
        johnDoe.setFirstName("John");
        johnDoe.setLastName("Doe");
        customerDao.save(johnDoe);

        BankAccount b1 = new BankAccount();
        b1.setCustomer(johnDoe);
        b1.setIban("1234DOE777");
        b1.setBankName("Foo Bank Inc.");

        BankAccount b2 = new BankAccount();
        b2.setCustomer(johnDoe);
        b2.setIban("DOEDEEDOE");
        b2.setBankName("Bar Investments Inc.");

        bankAccountDao.save(b1);
        bankAccountDao.save(b2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(net.mantucon.baracus.R.menu.main, menu);
	return true;
    }

    public void onButtonTestClicked(View v) {
        customerService.testService();
        bankAccountService.createAndOrDumpAccount();
        ApplicationContext.destroy(true);
        ApplicationContext.initApplicationContext();
    }

}

