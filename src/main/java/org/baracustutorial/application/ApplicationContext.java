package org.baracustutorial.application;

import org.baracus.context.BaracusApplicationContext;
import org.baracustutorial.dao.BankAccountDao;
import org.baracustutorial.dao.CustomerDao;
import org.baracustutorial.service.BankAccountService;
import org.baracustutorial.service.ConfigurationService;
import org.baracustutorial.service.CustomerService;

/**
 * Created with IntelliJ IDEA.
 * User: marcus
 */
public class ApplicationContext extends BaracusApplicationContext {

    static {
        registerBeanClass(OpenHelper.class);

        registerBeanClass(BankAccountDao.class);
        registerBeanClass(CustomerDao.class);

        registerBeanClass(CustomerService.class);
        registerBeanClass(BankAccountService.class);

        registerBeanClass(ConfigurationService.class);

        setApplicationContextInitializer(new AfterContextInitialized());
    }

}
