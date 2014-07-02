package net.mantucon.baracustutorial.service;

import net.mantucon.baracustutorial.annotations.Bean;
import net.mantucon.baracustutorial.dao.ConfigurationDao;
import net.mantucon.baracustutorial.lifecycle.Destroyable;
import net.mantucon.baracustutorial.lifecycle.Initializeable;
import net.mantucon.baracustutorial.model.ConfigurationParameter;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: marcus
 */

@Bean
public class ConfigurationService implements Initializeable, Destroyable{

    @Bean
    ConfigurationDao configurationDao;

    Date lastStarted;

    private static final String KEY_APP_INIT_DONE="APPL_INIT_DONE";
    private static final String KEY_APP_LAST_START="APPL_LAST_START";

    private static enum YesNo {
        YES,
        NO
    }

    public boolean isApplicationInitializationDone() {
        ConfigurationParameter parameter= configurationDao.getByName(KEY_APP_INIT_DONE);
        if (parameter != null) {
            return YesNo.YES.toString().equals(parameter.getConfigParameterValue());
        }
        return false;
    }

    public void setApplicationInitializationDone(boolean isEnabled) {
        ConfigurationParameter parameter= configurationDao.getByName(KEY_APP_INIT_DONE);

        if (parameter == null) {
            parameter = new ConfigurationParameter();
            parameter.setConfigParameter(KEY_APP_INIT_DONE);
        }

        parameter.setConfigParameterValue(isEnabled ? YesNo.YES.toString()
                                                    : YesNo.NO.toString());

        configurationDao.save(parameter);

    }


    @Override
    public void onDestroy() {

        try {

            ConfigurationParameter parameter= configurationDao.getByName(KEY_APP_LAST_START);

            if (parameter == null) {
                parameter = new ConfigurationParameter();
                parameter.setConfigParameter(KEY_APP_LAST_START);
            }

            parameter.setConfigParameterValue(String.valueOf(lastStarted.getTime()));

            configurationDao.save(parameter);
        } catch (Exception e) {
            // do nothing, this is nothing important
        }

    }

    @Override
    public void postConstruct() {
        lastStarted = new Date();
    }

}
