package by.sapra.listofcontacts.services.impl;

import by.sapra.listofcontacts.model.ContactEntity;
import by.sapra.listofcontacts.repositories.ContactRepository;
import by.sapra.listofcontacts.services.ContactService;
import by.sapra.listofcontacts.services.model.ContactList;
import by.sapra.listofcontacts.services.model.ContactModel;
import by.sapra.listofcontacts.services.model.ContactPayload;
import by.sapra.listofcontacts.services.model.RemovedId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
class JdbcContactService implements ContactService {
    private final ContactRepository contactRepository;

    @Override
    public ContactList getAll() {
        return ContactList.builder().contacts(
                    contactRepository.findAll().stream()
                            .map(this::mapToModel)
                            .toList()
                )
                .build();
    }

    @Override
    public ContactModel getContactById(String id) {
        Optional<ContactEntity> optional = contactRepository.findById(Long.parseLong(id));
        if (optional.isPresent()) {
            ContactEntity entity = optional.get();
            return mapToModel(entity);
        }
        return null;
    }

    @Override
    public ContactModel editContact(ContactPayload payload) {
        ContactEntity contact = contactRepository.save(mapToEntity(payload));
        return mapToModel(contact);
    }

    @Override
    public ContactModel createContact(ContactPayload payload) {
        ContactEntity save = contactRepository.save(mapToEntity(payload));
        return mapToModel(save);
    }

    @Override
    public RemovedId deleteContactById(String id) {
        contactRepository.deleteById(Long.valueOf(id));
        return RemovedId.builder().data(id).build();
    }

    private ContactEntity mapToEntity(ContactPayload payload) {
        return ContactEntity.builder()
                .id((payload.getId() != null) && (!payload.getId().isEmpty()) ? Long.valueOf(payload.getId()) : null)
                .phone(payload.getPhone())
                .lastName(payload.getLastName())
                .firstName(payload.getFirstName())
                .email(payload.getEmail())
                .build();
    }

    private ContactModel mapToModel(ContactEntity entity) {
        return ContactModel.builder()
                .id(String.valueOf(entity.getId()))
                .email(entity.getEmail())
                .lastName(entity.getLastName())
                .firstName(entity.getFirstName())
                .phone(entity.getPhone())
                .build();
    }
}
