package by.sapra.listofcontacts.services.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactModel {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
