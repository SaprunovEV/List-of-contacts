package by.sapra.listofcontacts.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@Builder
@FieldNameConstants
public class ContactEntity {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
