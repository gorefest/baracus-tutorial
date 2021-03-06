package org.baracustutorial.service;

import org.baracus.annotations.Bean;
import org.baracus.util.Logger;
import org.baracustutorial.dao.BankAccountDao;
import org.baracustutorial.model.BankAccount;

import java.util.List;

/**
 * Created by marcus on 30.07.14.
 */
public class BankAccountLoadServiceImpl2 implements BankAccountLoadService {

    @Bean
    BankAccountDao bankAccountDao;

    final Logger logger = new Logger(this.getClass());

    @Override
    public List<BankAccount> loadAllAccountsByCustomerId(Long id) {
        // In this demo, this is the alternative implementation
        logger.info("Alternative implementation called!");
        return bankAccountDao.getByCustomerId(id);
    }
}
