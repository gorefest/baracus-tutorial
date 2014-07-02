package net.mantucon.baracustutorial.service;

import net.mantucon.baracus.annotations.Bean;
import net.mantucon.baracus.dao.ConfigurationDao;
import net.mantucon.baracus.lifecycle.Destroyable;
import net.mantucon.baracus.lifecycle.Initializeable;
import net.mantucon.baracus.util.Logger;

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
