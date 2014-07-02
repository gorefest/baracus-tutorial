package net.mantucon.baracustutorial.dao;

import android.content.ContentValues;
import android.database.Cursor;
import net.mantucon.baracus.annotations.Bean;
import net.mantucon.baracus.dao.BaseDao;
import net.mantucon.baracus.orm.Field;
import net.mantucon.baracus.orm.FieldList;
import net.mantucon.baracus.orm.LazyCollection;
import net.mantucon.baracustutorial.model.BankAccount;
import net.mantucon.baracustutorial.model.Customer;

import java.util.List;

import static net.mantucon.baracus.orm.ModelBase.idCol;
import static net.mantucon.baracustutorial.model.Customer.firstNameCol;
import static net.mantucon.baracustutorial.model.Customer.lastNameCol;

/**
 * Created with IntelliJ IDEA.
 * User: marcus
 * To change this template use File | Settings | File Templates.
 */
public class CustomerDao extends BaseDao<Customer> {

    @Bean
    BankAccountDao bankAccountDao;

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
            result.setLastName(c.getString(lastNameCol.fieldIndex));
            result.setFirstName(c.getString(firstNameCol.fieldIndex));
            result.setTransient(false);

            result.setAccounts(new LazyCollection<BankAccount>(new LazyCollection.LazyLoader<BankAccount>() {
                @Override
                public List<BankAccount> loadReference() {
                    return bankAccountDao.getByCustomerId(id);
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
            if (customer.getLastName() != null) { result.put(lastNameCol.fieldName, customer.getLastName()); }
            if (customer.getFirstName() != null) { result.put(firstNameCol.fieldName, customer.getFirstName()); }
            return result;
        }
    };

    @Override
    public RowMapper<Customer> getRowMapper() {
        return rowMapper;
    }
}
