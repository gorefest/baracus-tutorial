package org.baracustutorial.migr8;

import android.database.sqlite.SQLiteDatabase;
import org.baracus.migr8.MigrationStep;
import org.baracustutorial.model.BankAccount;
import org.baracus.util.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: marcus
 * To change this template use File | Settings | File Templates.
 */
public class ModelVersion102 implements MigrationStep {


    private static final Logger logger = new Logger(ModelVersion102.class);
    @Override
    public void applyVersion(SQLiteDatabase db) {

        String stmt  = "ALTER TABLE " + BankAccount.TABLE_BANK_ACCOUNT
                + " ADD COLUMN "+BankAccount.customerIdCol.fieldName + " INTEGER";
        logger.info(stmt);
        db.execSQL(stmt);

    }

    @Override
    public int getModelVersionNumber() {
        return 102;
    }
}
