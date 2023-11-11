package by.sapra.listofcontacts.testUtils;

import by.sapra.listofcontacts.model.ContactEntity;

public class ContactEntityTestDataBuilder implements TestDataBuilder<ContactEntity> {
    private String firstName = "test_first";
    private String lastName = "test_last";
    private String email = "email@email.test";
    private String phone = "test_phone";

    private ContactEntityTestDataBuilder() {}

    public ContactEntityTestDataBuilder(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public static ContactEntityTestDataBuilder aContact() {
        return new ContactEntityTestDataBuilder();
    }

    @Override
    public ContactEntity build() {
        return ContactEntity.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .build();
    }

    public ContactEntityTestDataBuilder withFirstName(String firstName) {
        return this.firstName == firstName ? this : new ContactEntityTestDataBuilder(firstName, this.lastName, this.email, this.phone);
    }

    public ContactEntityTestDataBuilder withLastName(String lastName) {
        return this.lastName == lastName ? this : new ContactEntityTestDataBuilder(this.firstName, lastName, this.email, this.phone);
    }

    public ContactEntityTestDataBuilder withEmail(String email) {
        return this.email == email ? this : new ContactEntityTestDataBuilder(this.firstName, this.lastName, email, this.phone);
    }

    public ContactEntityTestDataBuilder withPhone(String phone) {
        return this.phone == phone ? this : new ContactEntityTestDataBuilder(this.firstName, this.lastName, this.email, phone);
    }
}
