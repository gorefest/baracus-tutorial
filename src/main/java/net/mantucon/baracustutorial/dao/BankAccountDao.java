package net.mantucon.baracustutorial.dao;

import android.content.ContentValues;
import android.database.Cursor;
import net.mantucon.baracustutorial.annotations.Bean;
import net.mantucon.baracustutorial.model.BankAccount;
import net.mantucon.baracustutorial.model.Customer;
import net.mantucon.baracustutorial.orm.Field;
import net.mantucon.baracustutorial.orm.FieldList;
import net.mantucon.baracustutorial.orm.LazyReference;
import net.mantucon.baracustutorial.orm.ReferenceLoader;

import java.util.LinkedList;
import java.util.List;

import static net.mantucon.baracustutorial.model.BankAccount.*;
import static net.mantucon.baracustutorial.orm.ModelBase.idCol;

/**
 * Created with IntelliJ IDEA.
 * User: marcus
 * To change this template use File | Settings | File Templates.
 */
public class BankAccountDao extends BaseDao<BankAccount> {

    @Bean
    CustomerDao customerDao;

    /**
     * Lock the DAO of
     */
    public BankAccountDao() {
        super(BankAccount.class);
    }

    public List<BankAccount> getByCustomerId(Long id) {
            Cursor c = null;
            List<BankAccount> result = new LinkedList<BankAccount>();
            try {
                c = this.getDb().query(true, rowMapper.getAffectedTable(), rowMapper.getFieldList().getFieldNames(), BankAccount.customerIdCol.fieldName + "=" + id.toString(), null, null, null, null, null);
                result = iterateCursor(c);
            } finally {
                if (c != null && !c.isClosed()) {
                    c.close();
                }
            }

            return result;

    }


    private final RowMapper<BankAccount> rowMapper = new RowMapper<BankAccount>() {
        @Override
        public BankAccount from(Cursor c) {
            BankAccount result = new BankAccount();

            result.setId(c.getLong(idCol.fieldIndex));
            result.setIban(c.getString(ibanCol.fieldIndex));
            result.setBankName(c.getString(bankNameCol.fieldIndex));
            result.setComment(c.getString(commentCol.fieldIndex));

            Long customerId = c.getLong(customerIdCol.fieldIndex);
            result.setCustomerReference(new LazyReference<Customer>(new ReferenceLoader<Customer>(customerDao, customerId)));

            result.setTransient(false);
            return result;        }

        @Override
        public String getAffectedTable() {
            return BankAccount.TABLE_BANK_ACCOUNT;
        }

        @Override
        public FieldList getFieldList() {
            return BankAccount.fieldList;
        }

        @Override
        public Field getNameField() {
            return bankNameCol;
        }

        @Override
        public ContentValues getContentValues(BankAccount account) {
            ContentValues result = new ContentValues();
            if (account.getId() != null) { result.put(idCol.fieldName, account.getId()); }
            if (account.getIban() != null) { result.put(BankAccount.ibanCol.fieldName, account.getIban()); }
            if (account.getBankName() != null) { result.put(bankNameCol.fieldName, account.getBankName()); }
            if (account.getCustomerReference() != null && account.getCustomerReference().getObjectRefId() != null) {
                result.put(customerIdCol.fieldName, account.getCustomerReference().getObjectRefId());
            }

           if (account.getComment() != null) {
                result.put(BankAccount.commentCol.fieldName, account.getComment());
            }

            return result;
        }
    };

    @Override
    public RowMapper<BankAccount> getRowMapper() {
        return rowMapper;
    }
}
