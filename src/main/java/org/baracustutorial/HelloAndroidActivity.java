package org.baracustutorial;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import org.baracus.annotations.Bean;
import org.baracus.util.DBBackup;
import org.baracus.util.Logger;
import org.baracustutorial.dao.BankAccountDao;
import org.baracustutorial.dao.CustomerDao;
import org.baracustutorial.model.BankAccount;
import org.baracustutorial.model.Customer;
import org.baracustutorial.service.BankAccountService;
import org.baracustutorial.service.ConfigurationService;
import org.baracustutorial.service.CustomerService;

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

    static String backupName;

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
	getMenuInflater().inflate(org.baracustutorial.R.menu.main, menu);
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

    public void onButtonBackupClicked(View view) {
        DBBackup.BackupResult result = DBBackup.performDatabaseBackup();
        if (result.isSuccessful()) {
            showPopup(this, "Successfully backed up "+result.getSize()+" to file "+result.getBackupDbName());
            backupName = result.getBackupDbName();
        } else {
            showPopup(this, "Backup failed :( Reason :"+result.getReason());
        }
    }


    public static void showPopup(Context context, String message) {
        AlertDialog.Builder popupBuilder = new AlertDialog.Builder(context);
        TextView myMsg = new TextView(context);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        myMsg.setText(message);
        popupBuilder.setView(myMsg);
        popupBuilder.show();
    }

    public void onButtonModifyClicked(View view) {
        Customer janeDoe = new Customer();
        janeDoe.setFirstName("Jane");
        janeDoe.setLastName("Doe");
        customerDao.save(janeDoe);
        showPopup(this, "Added Jane Doe");
        fillTable();
    }

    public void onBtnRestoreClicked(View view) {
        DBBackup.BackupResult result = DBBackup.restore(backupName);
        if (result.isSuccessful()) {
            showPopup(this, "Restore completed successfully.");
        } else {
            showPopup(this, "Restore failed due to reason :" + result.getReason());
        }
    }
}

