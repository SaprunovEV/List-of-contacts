package by.sapra.listofcontacts.model.mappers;

import by.sapra.listofcontacts.model.ContactEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactRowMapper implements RowMapper<ContactEntity> {
    @Override
    public ContactEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        return ContactEntity.builder()
                .id(rs.getLong("id"))
                .email(rs.getString("email"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .phone(rs.getString("phone"))
                .build();
    }
}
