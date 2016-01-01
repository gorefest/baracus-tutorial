package org.baracustutorial.service;

import org.baracus.annotations.Bean;
import org.baracus.dao.ConfigurationDao;
import org.baracus.lifecycle.Destroyable;
import org.baracus.lifecycle.Initializeable;
import org.baracus.util.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: marcus
 * Date: 13.10.13
 * Time: 15:06
 * To change this template use File | Settings | File Templates.
 */
@Bean
public class CustomerService implements Initializeable, Destroyable {

    private static final Logger logger = new Logger(CustomerService.class);

    @Bean
    ConfigurationDao configurationDao;

    public void testService() {
        logger.info("Hooray! I have been called!");
    }

    @Override
    public void onDestroy() {
        logger.info("Bean destruction initiated!");
    }

    @Override
    public void postConstruct() {
        logger.info("Bean is created. Is the ConfigurationDao correctly injected : $1", (configurationDao != null));
    }
}
