package fr.ing.secu.leakybank.infrastructure.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomerEntity {

    private String login;
    private String firstName;
    private String lastName;
    private boolean isAdmin;

}
