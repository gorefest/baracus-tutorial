package net.mantucon.baracus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
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

import java.util.ArrayList;
import java.util.List;

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

    ExpandableListView expandableListView;



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

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        fillTable();

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

        // Demonstrate Customer 1:N Lazy Loading
        List<Customer> allCustomers = customerDao.loadAll();

        for (Customer customer : allCustomers) {
            List<BankAccount> customerAccounts = customer.getAccounts();
            for (BankAccount account : customerAccounts) {
                logger.info("Customer $1 $2 --- account --> $3/$4", customer.getFirstName(),customer.getLastName(), account.getBankName(), account.getIban());
            }
        }

        // Demontrate BankAccount N:1 Lazy Loading
        List<BankAccount> allBankAccounts = bankAccountDao.loadAll();
        for (BankAccount account : allBankAccounts){
            logger.info("Bank Account $1 / $2 --- customer ---> $1 $2", account.getBankName(), account.getIban(), account.getCustomer().getFirstName(), account.getCustomer().getLastName());
        }


        // refill the data table
        fillTable();

    }

    /**
     * fill the data trable
     */
    private void fillTable() {

        final List<Customer> customers = customerDao.loadAll();
        AccountExpandListAdapter adapter = new AccountExpandListAdapter(this, new ArrayList<Customer>(customers));

        expandableListView.setAdapter(adapter);

        expandableListView.setLongClickable(true);

        expandableListView.setClickable(true);
        // Handle the long click; hold the customer long to open the CustomerEditor Activity
        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HelloAndroidActivity.this, CustomerEditorActivity.class);
                ArrayList<Customer> chList = new ArrayList<Customer>(customers);
                Customer c = chList.get(position);

                intent.putExtra("customerId", c.getId()); // pass the customer ID as parameter to the activity
                HelloAndroidActivity.this.startActivity(intent);
                return false;
            }
        });



    }


    public void onButtonNewClicked(View view) {
        Intent intent = new Intent(HelloAndroidActivity.this, CustomerEditorActivity.class);
        HelloAndroidActivity.this.startActivity(intent);
    }
}

