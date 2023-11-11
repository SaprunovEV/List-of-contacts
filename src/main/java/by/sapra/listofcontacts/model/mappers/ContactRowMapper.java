package by.sapra.listofcontacts.model.mappers;

import by.sapra.listofcontacts.model.ContactEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactRowMapper implements RowMapper<ContactEntity> {
    @Override
    public ContactEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        return ContactEntity.builder()
                .id(rs.getLong(ContactEntity.Fields.id))
                .email(rs.getString(ContactEntity.Fields.email))
                .firstName(rs.getString(ContactEntity.Fields.lastName))
                .lastName(rs.getString(ContactEntity.Fields.lastName))
                .phone(rs.getString(ContactEntity.Fields.phone))
                .build();
    }
}
