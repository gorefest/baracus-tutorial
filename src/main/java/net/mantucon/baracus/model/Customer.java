package net.mantucon.baracus.model;

import net.mantucon.baracus.orm.AbstractModelBase;
import net.mantucon.baracus.orm.Field;
import net.mantucon.baracus.orm.FieldList;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: marcus
 */
public class Customer extends AbstractModelBase {

    public static final String TABLE_CUSTOMER = "customer";

    private static int columnIndex= AbstractModelBase.fieldList.size();

    private String lastName;
    private String firstName;
    private List<BankAccount> accounts;

    public static final FieldList fieldList = new FieldList(Customer.class.getSimpleName());
    public static final Field lastNameCol = new Field("last_name", columnIndex++);
    public static final Field firstNameCol = new Field("first_name", columnIndex++);


    static {
        fieldList.add(AbstractModelBase.fieldList);
        fieldList.add(lastNameCol);
        fieldList.add(firstNameCol);
    }

    public Customer() {
        super(TABLE_CUSTOMER);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BankAccount> accounts) {
        this.accounts = accounts;
    }
}
