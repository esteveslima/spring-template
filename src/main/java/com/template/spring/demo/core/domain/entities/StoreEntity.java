package com.template.spring.demo.core.domain.entities;

import com.template.spring.demo.core.domain.exceptions.store.StoreFieldFailedValidationException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@Entity @Table(name = "stores")  // TODO: Find a way to separate DB annotations from domain entities or ensure a data mapper is stable and performant(had a separated model class, changed ORM annotations to entity to avoid having mappings on repositories)
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "name")
    private String name;

    //

    public StoreEntity(UserEntity user, String name) {
        StoreEntity.validateName(name);

        this.id = Integer.MIN_VALUE;
        this.user = user;
        this.name = name;
    }

    public void setName(String name) throws StoreFieldFailedValidationException{
        StoreEntity.validateName(name);
        this.name = name;
    }

    //

    public static void validateName(String name) throws StoreFieldFailedValidationException {
        if(name == null){
            String errorMessage = "Name cannot be null";
            Map<String, Object> validationFailuresMap = new HashMap<String, Object>();
            validationFailuresMap.put("name", name);
            throw new StoreFieldFailedValidationException(errorMessage, validationFailuresMap);
        }

        int nameMinLength = 3;
        int nameMaxLength = 200;
        boolean isAdequateLength = name.length() >= nameMinLength && name.length() <= nameMaxLength;
        if(!isAdequateLength){
            String errorMessage = String.format("Name length must be between %d and %d", nameMinLength, nameMaxLength);
            Map<String, Object> validationFailuresMap = new HashMap<String, Object>();
            validationFailuresMap.put("name", name);
            throw new StoreFieldFailedValidationException(errorMessage, validationFailuresMap);
        }

        return;
    }
}
