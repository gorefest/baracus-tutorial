package net.mantucon.baracus.application;

import android.content.Context;
import net.mantucon.baracus.dao.BaracusOpenHelper;
import net.mantucon.baracus.migr8.ModelVersion100;

/**
 * Created with IntelliJ IDEA.
 * User: marcus
 */
public class OpenHelper extends BaracusOpenHelper {

    private static final String DATABASE_NAME="tutorial-app.db";
    private static final int TARGET_VERSION=100;

    static {
        addMigrationStep(new ModelVersion100());
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
