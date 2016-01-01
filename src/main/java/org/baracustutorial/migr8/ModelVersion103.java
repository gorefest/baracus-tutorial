package org.baracustutorial.migr8;

import android.database.sqlite.SQLiteDatabase;
import org.baracus.migr8.MigrationStep;
import org.baracus.util.Logger;
import org.baracustutorial.model.BankAccount;

/**
 * Created with IntelliJ IDEA.
 * User: marcus
 * To change this template use File | Settings | File Templates.
 */
public class ModelVersion103 implements MigrationStep {


    private static final Logger logger = new Logger(ModelVersion103.class);
    @Override
    public void applyVersion(SQLiteDatabase db) {

        String stmt  = "ALTER TABLE " + BankAccount.TABLE_BANK_ACCOUNT
               + " ADD COLUMN "+BankAccount.commentCol.fieldName + " TEXT";
        logger.info(stmt);
        db.execSQL(stmt);

    }

    @Override
    public int getModelVersionNumber() {
        return 103;
    }
}
