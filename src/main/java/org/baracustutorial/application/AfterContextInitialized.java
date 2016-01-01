package org.baracustutorial.application;

import org.baracus.lifecycle.ApplicationContextInitializer;
import org.baracustutorial.service.BankAccountLoadService;
import org.baracustutorial.service.BankAccountLoadServiceImpl1;
import org.baracustutorial.service.BankAccountLoadServiceImpl2;
import org.baracustutorial.validation.NameValidator;

import java.util.Date;

/**
 * Created by marcus on 30.07.14.
 */
public class AfterContextInitialized implements ApplicationContextInitializer {

    static boolean reinit = true;

    @Override
    public void afterContextIsBuilt() {
        if (reinit) {
            if ((new Date().getTime() % 2 ) == 0) {
                ApplicationContext.registerBeanClass(BankAccountLoadService.class, BankAccountLoadServiceImpl1.class);
            } else {
                ApplicationContext.registerBeanClass(BankAccountLoadService.class, BankAccountLoadServiceImpl2.class);
            }

            reinit = false;
            ApplicationContext.reinitializeContext();
            ApplicationContext.registerValidator(new NameValidator());

        }
    }
}
