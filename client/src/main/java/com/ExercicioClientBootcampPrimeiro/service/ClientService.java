package com.ExercicioClientBootcampPrimeiro.service;

import com.ExercicioClientBootcampPrimeiro.dto.ClientDTO;
import com.ExercicioClientBootcampPrimeiro.entities.Client;
import com.ExercicioClientBootcampPrimeiro.repositories.ClientRepository;
import com.ExercicioClientBootcampPrimeiro.service.exception.ControllerExceptionNotExist;
import com.ExercicioClientBootcampPrimeiro.service.exception.DataBaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(PageRequest pageRequest){
        Page<Client> cliente = repository.findAll(pageRequest);
        return cliente.map(ClientDTO::new);
    }
    @Transactional(readOnly = true)
    public ClientDTO findId(Long id){
        Optional<Client> cliente = repository.findById(id);
        Client cli = cliente.orElseThrow(()-> new ControllerExceptionNotExist("Entity not found"));
        return new ClientDTO(cli);
    }
    @Transactional
    public ClientDTO insert(ClientDTO dto){
        Client cli = new Client();
        copyDtoToEntity(dto,cli);
        cli =  repository.save(cli);
        return  new ClientDTO(cli);
    }
    @Transactional
    public ClientDTO update(Long id, ClientDTO dto){
        try {
            Client cli = repository.getOne(id);
            copyDtoToEntity(dto, cli);
            repository.save(cli);
            return new ClientDTO(cli);
        }
        catch (EntityNotFoundException e){
            throw new ControllerExceptionNotExist("Entity not found");
        }
    }
    public void delete(Long id){
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ControllerExceptionNotExist("Id not found" + id);
        }
        catch (DataIntegrityViolationException e){
            throw new DataBaseException("Integrety violation");
        }
    }

    private void copyDtoToEntity(ClientDTO dto, Client client){
        client.setName(dto.getName());
        client.setCpf(dto.getCpf());
        client.setIncome(dto.getIncome());
        client.setBirthDate(dto.getBirthDate());
        client.setChildren(dto.getChildren());
    }
}
