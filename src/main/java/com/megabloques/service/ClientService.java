package com.megabloques.service;

import com.megabloques.document.Client;
import com.megabloques.exception.ResourceNotFoundException;
import com.megabloques.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(String id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
    }

    public Client create(Client client) {
        client.setId(null);
        return clientRepository.save(client);
    }

    public Client update(String id, Client client) {
        Client existing = findById(id);
        existing.setName(client.getName());
        existing.setDocumentType(client.getDocumentType());
        existing.setDocumentNumber(client.getDocumentNumber());
        existing.setPhone(client.getPhone());
        existing.setEmail(client.getEmail());
        existing.setAddress(client.getAddress());
        existing.setCity(client.getCity());
        existing.setNotes(client.getNotes());
        existing.setActive(client.isActive());
        return clientRepository.save(existing);
    }

    public void delete(String id) {
        Client client = findById(id);
        clientRepository.delete(client);
    }
}
