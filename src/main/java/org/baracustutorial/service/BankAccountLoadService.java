package org.baracustutorial.service;

import org.baracustutorial.model.BankAccount;

import java.util.List;

/**
 * This is a simple interface to demonstrate the registration of an interface
 *
 * Created by marcus on 30.07.14.
 */
public interface BankAccountLoadService {

    /**
     * load all customers thru a service
     * @param id
     * @return
     */
    List<BankAccount> loadAllAccountsByCustomerId(Long id);

}
