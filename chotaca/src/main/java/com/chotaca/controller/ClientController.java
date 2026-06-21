package com.chotaca.controller;

import com.chotaca.dto.ClientDTO;
import com.chotaca.entity.Client;
import com.chotaca.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public Client saveClient(@Valid @RequestBody Client client) {
        return clientService.saveClient(client);
    }
    @GetMapping
    public List<ClientDTO> getAllClients() {
        return clientService.getAllClientDTOs();
    }

    @GetMapping("/search")
    public List<Client> searchClients(
            @RequestParam String name) {

        return clientService.searchClients(name);
    }

    @GetMapping("/{id}")
    public ClientDTO getClientById(@PathVariable Long id) {

        Client client = clientService.getClientById(id);

        return clientService.convertToDTO(client);
    }

    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return "Client Deleted Successfully";
    }

    @GetMapping("/test")
    public String test() {
        return "Client API Working";
    }

    @PutMapping("/{id}")
    public Client updateClient(
            @PathVariable Long id,
            @Valid @RequestBody Client client) {

        return clientService.updateClient(id, client);
    }


    @GetMapping("/page")
    public Page<Client> getClientsPaginated(
            @RequestParam int page,
            @RequestParam int size
    ) {

        return clientService.getClientsPaginated(
                page,
                size
        );
    }


    @GetMapping("/sort")
    public Page<Client> getClientsSorted(

            @RequestParam int page,

            @RequestParam int size,

            @RequestParam String sortBy
    ) {

        return clientService.getClientsSorted(
                page,
                size,
                sortBy
        );
    }

    @GetMapping("/unpaid")
    public List<Client> getUnpaidClients() {
        return clientService.getUnpaidClients();
    }





    @GetMapping("/export/csv")
    public ResponseEntity<String> exportClientsCsv() {

        String csvData = clientService.generateClientCsv();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=clients.csv")
                .body(csvData);
    }
}