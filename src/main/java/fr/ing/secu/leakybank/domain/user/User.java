package fr.ing.secu.leakybank.domain.user;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@DDD.ValueObject
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NonNull
    private String login;
    @NonNull
    private String password;

}
