package com.victot.desafio_dev_jr_apse.service;

import com.victot.desafio_dev_jr_apse.model.User;
import com.victot.desafio_dev_jr_apse.model.exception.NotFoundException;
import com.victot.desafio_dev_jr_apse.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {//todo verificar os dados e salvar depois
        return userRepository.save(user);
    }

    public User update(User user) {

        if (userRepository.existsById(user.getId())) {
            User old = userRepository.findById(user.getId()).orElse(null);
            old.setEmail(user.getEmail());//ignorando esta advertência devido a já chegar se o user não vai ser null
            old.setPassword(user.getPassword());
            old.setUsername(user.getUsername());
            return userRepository.save(old);
        }else {
            throw new NotFoundException("Este id " + user.getId() + " não existe");
        }
    }

    public User findById(Long id) {
        User userReturn = userRepository.findById(id).orElse(null);
        if (userReturn == null) {
            throw new NotFoundException("O id " + id + " não existe.");
        }else{
            return userReturn;
        }
    }

    public List<String> findAll() {
        ArrayList<User> users = new ArrayList<>(userRepository.findAll());
        if (users.isEmpty()) {
            throw new NotFoundException("Nenhum usuário listado");
        }else{
            return users.stream().map(User::getUsername).toList();
        }
    }

    public void deleteById(Long id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }else{
            throw new NotFoundException("O id: " + id + " não existe, nada foi deletado");
        }
    }
}
