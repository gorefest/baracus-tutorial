package net.mantucon.baracustutorial.service;

import net.mantucon.baracus.annotations.Bean;
import net.mantucon.baracus.util.Logger;
import net.mantucon.baracustutorial.dao.BankAccountDao;
import net.mantucon.baracustutorial.model.BankAccount;

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
