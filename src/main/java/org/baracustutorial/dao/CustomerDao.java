package org.baracustutorial.dao;

import android.content.ContentValues;
import android.database.Cursor;
import org.baracus.annotations.Bean;
import org.baracus.dao.BaseDao;
import org.baracus.orm.Field;
import org.baracus.orm.FieldList;
import org.baracus.orm.LazyCollection;
import org.baracustutorial.model.BankAccount;
import org.baracustutorial.model.Customer;
import org.baracustutorial.service.BankAccountLoadService;

import java.util.List;

import static org.baracus.orm.ModelBase.idCol;


/**
 * Created with IntelliJ IDEA.
 * User: marcus
 * To change this template use File | Settings | File Templates.
 */
public class CustomerDao extends BaseDao<Customer> {

    @Bean
    BankAccountLoadService bankAccountLoadService;

    /**
     * Lock the DAO of
     */
    public CustomerDao() {
        super(Customer.class);
    }

    private final RowMapper<Customer> rowMapper = new RowMapper<Customer>() {
        @Override
        public Customer from(Cursor c) {
            Customer result = new Customer();
            final Long id = c.getLong(idCol.fieldIndex);
            result.setId(id);
            result.setLastName(c.getString(Customer.lastNameCol.fieldIndex));
            result.setFirstName(c.getString(Customer.firstNameCol.fieldIndex));
            result.setTransient(false);

            result.setAccounts(new LazyCollection<BankAccount>(new LazyCollection.LazyLoader<BankAccount>() {
                @Override
                public List<BankAccount> loadReference() {
                    return bankAccountLoadService.loadAllAccountsByCustomerId(id);
                }
            }));


            return result;
        }

        @Override
        public String getAffectedTable() {
            return Customer.TABLE_CUSTOMER;
        }

        @Override
        public FieldList getFieldList() {
            return Customer.fieldList;
        }

        @Override
        public Field getNameField() {
            return Customer.lastNameCol;
        }

        @Override
        public ContentValues getContentValues(Customer customer) {
            ContentValues result = new ContentValues();
            if (customer.getId() != null) { result.put(idCol.fieldName, customer.getId()); }
            if (customer.getLastName() != null) { result.put(Customer.lastNameCol.fieldName, customer.getLastName()); }
            if (customer.getFirstName() != null) { result.put(Customer.firstNameCol.fieldName, customer.getFirstName()); }
            return result;
        }
    };

    @Override
    public RowMapper<Customer> getRowMapper() {
        return rowMapper;
    }
}
