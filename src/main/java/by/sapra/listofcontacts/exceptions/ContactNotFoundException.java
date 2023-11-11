package by.sapra.listofcontacts.exceptions;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(String s) {
        super(s);
    }
}
