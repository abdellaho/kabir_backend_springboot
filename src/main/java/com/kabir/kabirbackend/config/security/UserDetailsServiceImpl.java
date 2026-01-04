package com.kabir.kabirbackend.config.security;

import com.kabir.kabirbackend.entities.Personnel;
import com.kabir.kabirbackend.repository.PersonnelRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PersonnelRepository personnelRepository;

    public UserDetailsServiceImpl(PersonnelRepository personnelRepository) {
        this.personnelRepository = personnelRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Personnel personnel = personnelRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(personnel.getEmail())
                .password(personnel.getPassword())
                .roles(getRoles(personnel).toArray(new String[0]))
                .build();
    }

    public List<String> getRoles(Personnel personnel) {
        List<String> list = new ArrayList<>();
        if(personnel.getTypePersonnel() == 0) {
            list.add("ROLE_ADMIN");
        } else {
            if(personnel.isAjouterRepertoire()) list.add("AJOUTER_REPERTOIRE");
            if(personnel.isConsulterRepertoire()) list.add("CONSULTER_REPERTOIRE");
            if(personnel.isSupprimerRepertoire()) list.add("SUPPRIMER_REPERTOIRE");
            if(personnel.isModifierRepertoire()) list.add("MODIFIER_REPERTOIRE");
            if(personnel.isAjouterStock()) list.add("AJOUTER_STOCK");
            if(personnel.isConsulterStock()) list.add("CONSULTER_STOCK");
            if(personnel.isSupprimerStock()) list.add("SUPPRIMER_STOCK");
            if(personnel.isModifierStock()) list.add("MODIFIER_STOCK");
        }

        return list;
    }
}

