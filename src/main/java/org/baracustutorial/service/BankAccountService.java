package org.baracustutorial.service;

import org.baracus.annotations.Bean;
import org.baracustutorial.dao.BankAccountDao;
import org.baracustutorial.model.BankAccount;
import org.baracus.util.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: marcus
 */
@Bean
public class BankAccountService {

    private static final Logger logger = new Logger(BankAccountService.class);

    @Bean
    BankAccountDao bankAccountDao;

    public void createAndOrDumpAccount() {
        BankAccount account = bankAccountDao.getByName("FOOBANK");
        if (account == null) {
            logger.info("No account for FOOBANK was found. I am going to create one.");
            account = new BankAccount();
            account.setBankName("FOOBANK");
            account.setIban("MARMELADEFOO666");
            bankAccountDao.save(account);
        } else {
            logger.info("ACCOUNT FOUND. Id is $1",account.getId());
            logger.info("String value $1",account.toString());
        }
    }
}
