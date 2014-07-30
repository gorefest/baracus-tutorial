package net.mantucon.baracustutorial.application;

import android.content.Context;
import net.mantucon.baracus.dao.BaracusOpenHelper;
import net.mantucon.baracustutorial.migr8.ModelVersion100;
import net.mantucon.baracustutorial.migr8.ModelVersion101;
import net.mantucon.baracustutorial.migr8.ModelVersion102;
import net.mantucon.baracustutorial.migr8.ModelVersion103;

/**
 * Created with IntelliJ IDEA.
 * User: marcus
 */
public class OpenHelper extends BaracusOpenHelper {

    private static final String DATABASE_NAME="tutorial-app.db";

    private static final int TARGET_VERSION=103;

    static {
        addMigrationStep(new ModelVersion100());
        addMigrationStep(new ModelVersion101());
        addMigrationStep(new ModelVersion102());
        addMigrationStep(new ModelVersion103());
    }

    /**
     * Open Helper for the android database
     *
     * @param mContext              - the android context
     */
    public OpenHelper(Context mContext) {
        super(mContext, DATABASE_NAME, TARGET_VERSION);
    }
}
