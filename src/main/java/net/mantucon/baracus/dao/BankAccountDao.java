package net.mantucon.baracus.dao;

import android.content.ContentValues;
import android.database.Cursor;
import net.mantucon.baracus.model.BankAccount;
import net.mantucon.baracus.orm.Field;
import net.mantucon.baracus.orm.FieldList;
import net.mantucon.baracus.orm.LazyCollection;

import java.util.List;

import static net.mantucon.baracus.model.BankAccount.bankNameCol;
import static net.mantucon.baracus.model.BankAccount.ibanCol;
import static net.mantucon.baracus.orm.AbstractModelBase.idCol;

/**
 * Created with IntelliJ IDEA.
 * User: marcus
 * To change this template use File | Settings | File Templates.
 */
public class BankAccountDao extends BaseDao<BankAccount> {

    /**
     * Lock the DAO of
     */
    public BankAccountDao() {
        super(BankAccount.class);
    }

    private static final RowMapper<BankAccount> rowMapper = new RowMapper<BankAccount>() {
        @Override
        public BankAccount from(Cursor c) {
            BankAccount result = new BankAccount();
            result.setId(c.getLong(idCol.fieldIndex));
            result.setIban(c.getString(ibanCol.fieldIndex));
            result.setBankName(c.getString(bankNameCol.fieldIndex));
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
            return result;
        }
    };

    @Override
    public RowMapper<BankAccount> getRowMapper() {
        return rowMapper;
    }
}
