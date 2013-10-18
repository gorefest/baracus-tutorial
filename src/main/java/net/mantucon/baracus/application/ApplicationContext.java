package net.mantucon.baracus.application;

import net.mantucon.baracus.context.BaracusApplicationContext;
import net.mantucon.baracus.dao.BankAccountDao;
import net.mantucon.baracus.dao.BaracusOpenHelper;
import net.mantucon.baracus.service.BankAccountService;
import net.mantucon.baracus.service.CustomerService;

/**
 * Created with IntelliJ IDEA.
 * User: marcus
 */
public class ApplicationContext extends BaracusApplicationContext {

    static {
        registerBeanClass(OpenHelper.class);

        registerBeanClass(BankAccountDao.class);

        registerBeanClass(CustomerService.class);
        registerBeanClass(BankAccountService.class);
    }

}
