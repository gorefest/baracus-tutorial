package net.mantucon.baracus.model;

import net.mantucon.baracus.orm.*;

/**
 * Created with IntelliJ IDEA.
 * User: marcus
 */
public class BankAccount extends AbstractModelBase {

    public static final String TABLE_BANK_ACCOUNT = "bank_account";

    private static int columnIndex= AbstractModelBase.fieldList.size();

    private String bankName;
    private String iban;
    private Reference<Customer> customerReference;
    private String comment;

    public static final FieldList fieldList = new FieldList(BankAccount.class.getSimpleName());
    public static final Field bankNameCol = new Field("bank_name", columnIndex++);
    public static final Field ibanCol = new Field("iban", columnIndex++);
    public static final Field customerIdCol = new Field("customerId", columnIndex++);
    public static final Field commentCol= new Field("comment", columnIndex++);


    static {
        fieldList.add(AbstractModelBase.fieldList);
        fieldList.add(bankNameCol);
        fieldList.add(ibanCol);
        fieldList.add(customerIdCol);
        fieldList.add(commentCol);
    }

    public BankAccount() {
        super(TABLE_BANK_ACCOUNT);
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Reference<Customer> getCustomerReference() {
        return customerReference;
    }

    public void setCustomerReference(Reference<Customer> customerReference) {
        this.customerReference = customerReference;
    }

    public Customer getCustomer() {
        return customerReference.getObject();
    }

    public void setCustomer(Customer customer) {
        this.customerReference = new ObjectReference<Customer>(customer);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
