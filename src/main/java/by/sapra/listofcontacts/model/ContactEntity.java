package by.sapra.listofcontacts.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactEntity {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
