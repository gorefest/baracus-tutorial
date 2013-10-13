package net.mantucon.baracus.context;

import android.R;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import net.mantucon.baracus.dao.BaracusOpenHelper;
import net.mantucon.baracus.dao.ConfigurationDao;
import net.mantucon.baracus.errorhandling.CustomErrorHandler;
import net.mantucon.baracus.errorhandling.ErrorHandlingFactory;
import net.mantucon.baracus.errorhandling.ErrorSeverity;
import net.mantucon.baracus.errorhandling.StandardErrorHandler;
import net.mantucon.baracus.orm.AbstractModelBase;
import net.mantucon.baracus.signalling.*;
import net.mantucon.baracus.util.Logger;
import net.mantucon.baracus.validation.ValidationFactory;
import net.mantucon.baracus.validation.Validator;
import sun.misc.MessageUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static net.mantucon.baracus.util.StringUtil.join;

/**
 * Created with IntelliJ IDEA.  <br>
 * User: marcus                 <br>
 * Date: 10.07.12               <br>
 * Time: 06:05                  <br>
 * <hr>
 *
 * Base Application Context class. In order to use BARACUS you must inherit this class.
 * use the registerBeanClass() function to add all Your bean classes. Implement Initializable and
 * Destroyable interface in order to have creation / destruction lifecycle management support.
 *
 * <hr>
 * Example Context Implementation :
<pre>
 {@code

 public class ApplicationContext extends BaracusApplicationContext{

 static {
    registerBeanClass(BankDao.class);
    ...
 }


 private static final Logger logger = new Logger(ApplicationContext.class);

 private ApplicationContext() {
 // protection constructor
 }


 }

 To make use of Your class as an app container, You must register it in the
 AndroidManifest.xml's application tag :

 {@code

 <application android:icon="@drawable/icon"
 android:label="@string/app_name"
 android:debuggable="true"
 android:theme="@android:style/Theme.DeviceDefault"
 android:name=".wonderapp.application.ApplicationContext">


 }

 </pre>

 *
 */
public abstract class BaracusApplicationContext extends BaracusApplicationContextV32 {


    private static ActivityLifecycleCallbacks callbacks;


    public static synchronized void make() {
        if (!semaphore) {
            semaphore = true;
            callbacks = new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    logger.debug("onActivityCreated called for $1",activity.getClass().getName());
                    BeanContainer.addExistingActivity(activity);
//                    beanContainer.holdBean(activity.getClass(), activity);
                    if (!init) {
                        logger.debug("build application context");
                        initApplicationContext();
                    }
                    beanContainer.performInjection(activity);

                }

                @Override
                public void onActivityStarted(Activity activity) {
                    logger.debug("onActivityStarted called for $1",activity.getClass().getName());
                    BeanContainer.addActiveActivity(activity);
                }

                @Override
                public void onActivityResumed(Activity activity) {
                    logger.debug("onActivityResumed called for $1",activity.getClass().getName());
                    BeanContainer.removePausedActivity(activity);
                }

                @Override
                public void onActivityPaused(Activity activity) {
                    logger.debug("onActivityPaused called for $1",activity.getClass().getName());
                    BeanContainer.addPausedActivity(activity);
                }

                @Override
                public void onActivityStopped(Activity activity) {
                    logger.debug("onActivityStopped called for $1",activity.getClass().getName());
                    BeanContainer.removeActiveActivity(activity);
                    beanContainer.performOutjection(activity);
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                    logger.debug("onActivitySaveInstanceState called for $1",activity.getClass().getName());

//                    beanContainer.performOutjection(activity.getClass());
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    logger.debug("onActivityDestroyed called for $1",activity.getClass().getName());
                    BeanContainer.removeExistingActivity(activity);
                }
            };

            __instance.registerActivityLifecycleCallbacks(callbacks);



        }

        semaphore = false;

        refCount++;
    }



    /**
     * destroys the application context and shreds all beans. this function allows you
     * to shut down the entire bean context in your application without restarting it
     *
     * @param force - set to true, and all references are ignored
     */
    public static synchronized void destroy(boolean force) {
        refCount--;
        if (refCount == 0 || force) {

            __instance.unregisterActivityLifecycleCallbacks(callbacks);
            BaracusApplicationContextV32.destroy(force);
        }
    }

}
