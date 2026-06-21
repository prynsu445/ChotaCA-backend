package com.chotaca.service;

import com.chotaca.dto.ClientDTO;
import com.chotaca.entity.Client;
import com.chotaca.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import com.chotaca.dto.ClientDTO;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    public Client updateClient(Long id, Client updatedClient) {

        Client client = clientRepository.findById(id).orElse(null);

        if (client != null) {
            client.setClientName(updatedClient.getClientName());
            client.setMobile(updatedClient.getMobile());
            client.setEmail(updatedClient.getEmail());
            client.setPanNumber(updatedClient.getPanNumber());
            client.setGstNumber(updatedClient.getGstNumber());
            client.setAddress(updatedClient.getAddress());
            client.setStatus(updatedClient.getStatus());

            return clientRepository.save(client);
        }

        return null;
    }

    public List<Client> searchClients(String name) {

        return clientRepository
                .findByClientNameContainingIgnoreCase(name);
    }

    public Page<Client> getClientsPaginated(
            int page,
            int size
    ) {
        Pageable pageable =
                PageRequest.of(page, size);

        return clientRepository.findAll(pageable);
    }

    public Page<Client> getClientsSorted(
            int page,
            int size,
            String sortBy
    ) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy)
                );

        return clientRepository.findAll(pageable);
    }


    public ClientDTO convertToDTO(Client client) {

        ClientDTO dto = new ClientDTO();

        dto.setId(client.getId());
        dto.setClientName(client.getClientName());
        dto.setMobile(client.getMobile());
        dto.setEmail(client.getEmail());
        dto.setPanNumber(client.getPanNumber());
        dto.setGstNumber(client.getGstNumber());
        dto.setAddress(client.getAddress());
        dto.setStatus(client.getStatus());

        return dto;
    }
    public List<ClientDTO> getAllClientDTOs() {
        return clientRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<Client> getUnpaidClients() {
        return clientRepository.findClientsWithUnpaidInvoices();
    }



    public String generateClientCsv() {

        StringBuilder csv = new StringBuilder();

        csv.append("Id,Name,Email,Mobile\n");

        List<Client> clients = clientRepository.findAll();

        for(Client client : clients) {

            csv.append(client.getId()).append(",");
            csv.append(client.getClientName()).append(",");
            csv.append(client.getEmail()).append(",");
            csv.append(client.getMobile()).append("\n");
        }

        return csv.toString();
    }


}