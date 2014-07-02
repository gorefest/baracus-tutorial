package net.mantucon.baracustutorial;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import net.mantucon.baracustutorial.annotations.Bean;
import net.mantucon.baracustutorial.context.ManagedActivity;
import net.mantucon.baracustutorial.dao.CustomerDao;
import net.mantucon.baracustutorial.model.Customer;

/**
 * Created by marcus on 03.02.14.
 */
public class CustomerEditorActivity extends ManagedActivity {

    TextView lastName; // sic.
    TextView firstName;
    Customer customer;

    @Bean
    CustomerDao customerDao;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.customer_editor);
        enableFocusChangeBasedValidation();

        lastName = (TextView) findViewById(R.id.txtCustomerLastName);
        firstName = (TextView) findViewById(R.id.txtCustomerFirstName);


        // If we edit an existing customer, load the customer data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Long customerId = extras.getLong("customerId");
            customer = customerDao.getById(customerId);
            lastName.setText(customer.getLastName());
            firstName.setText(customer.getFirstName());
        } else {
            customer = new Customer();
        }

    }

    public void btnSaveClicked(View v) {
        if (validate()) {
            customer.setFirstName(firstName.getText().toString());
            customer.setLastName(lastName.getText().toString());
            customerDao.save(customer);
            dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK)); // go back to caller
            finish();
        } else {
            Toast.makeText(this, R.string.insufficientData, Toast.LENGTH_LONG).show();
        }
    }
}