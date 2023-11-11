package by.sapra.listofcontacts.testUtils;

import by.sapra.listofcontacts.model.ContactEntity;
import lombok.AllArgsConstructor;
import lombok.With;

@With
@AllArgsConstructor
public class ContactEntityTestDataBuilder implements TestDataBuilder<ContactEntity> {
    private String firstName = "test_first";
    private String lastName = "test_last";
    private String email = "email@email.test";
    private String phone = "test_phone";

    private ContactEntityTestDataBuilder() {}

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
}
