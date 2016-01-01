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
public class ModelVersion100 implements MigrationStep {


    private static final Logger logger = new Logger(ModelVersion100.class);
    @Override
    public void applyVersion(SQLiteDatabase db) {

        String stmt  = "CREATE TABLE " + BankAccount.TABLE_BANK_ACCOUNT
                + "( "+ BankAccount.idCol.fieldName+" INTEGER PRIMARY KEY"
                + ", "+ BankAccount.bankNameCol.fieldName+ " TEXT"
                + ", "+ BankAccount.ibanCol.fieldName+ " TEXT"+
                  ")";
        logger.info(stmt);
        db.execSQL(stmt);

    }

    @Override
    public int getModelVersionNumber() {
        return 100;
    }
}
