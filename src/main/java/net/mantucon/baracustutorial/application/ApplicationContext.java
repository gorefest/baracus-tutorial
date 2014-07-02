package net.mantucon.baracustutorial.application;

import net.mantucon.baracus.context.BaracusApplicationContext;
import net.mantucon.baracustutorial.dao.BankAccountDao;
import net.mantucon.baracustutorial.dao.CustomerDao;
import net.mantucon.baracustutorial.service.BankAccountService;
import net.mantucon.baracustutorial.service.ConfigurationService;
import net.mantucon.baracustutorial.service.CustomerService;

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
    }

}
