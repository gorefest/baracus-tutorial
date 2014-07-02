package net.mantucon.baracustutorial.dao;

import android.content.ContentValues;
import android.database.Cursor;
import net.mantucon.baracus.dao.BaseDao;
import net.mantucon.baracustutorial.model.BankAccount;
import net.mantucon.baracus.orm.Field;
import net.mantucon.baracus.orm.FieldList;

import static net.mantucon.baracus.orm.ModelBase.idCol;

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
            result.setIban(c.getString(BankAccount.ibanCol.fieldIndex));
            result.setBankName(c.getString(BankAccount.bankNameCol.fieldIndex));
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
            return BankAccount.bankNameCol;
        }

        @Override
        public ContentValues getContentValues(BankAccount account) {
            ContentValues result = new ContentValues();
            if (account.getId() != null) { result.put(idCol.fieldName, account.getId()); }
            if (account.getIban() != null) { result.put(BankAccount.ibanCol.fieldName, account.getIban()); }
            if (account.getBankName() != null) { result.put(BankAccount.bankNameCol.fieldName, account.getBankName()); }
            return result;
        }
    };

    @Override
    public RowMapper<BankAccount> getRowMapper() {
        return rowMapper;
    }
}
