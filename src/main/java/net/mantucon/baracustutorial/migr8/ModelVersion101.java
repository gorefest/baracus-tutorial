package net.mantucon.baracustutorial.migr8;

import android.database.sqlite.SQLiteDatabase;
import net.mantucon.baracus.migr8.MigrationStep;
import net.mantucon.baracustutorial.model.Customer;
import net.mantucon.baracus.util.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: marcus
 * To change this template use File | Settings | File Templates.
 */
public class ModelVersion101 implements MigrationStep {


    private static final Logger logger = new Logger(ModelVersion101.class);
    @Override
    public void applyVersion(SQLiteDatabase db) {

        String stmt  = "CREATE TABLE " + Customer.TABLE_CUSTOMER
                + "( "+ Customer.idCol.fieldName+" INTEGER PRIMARY KEY"
                + ", "+ Customer.lastNameCol.fieldName+ " TEXT"
                + ", "+ Customer.firstNameCol.fieldName+ " TEXT"+
                  ")";
        logger.info(stmt);
        db.execSQL(stmt);

    }

    @Override
    public int getModelVersionNumber() {
        return 101;
    }
}
