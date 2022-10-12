package fr.ing.secu.leakybank.domain.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jqassistant.contrib.plugin.ddd.annotation.DDD;

@DDD.Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Customer {

    @EqualsAndHashCode.Include
    private String login;
    @EqualsAndHashCode.Exclude
    private String firstName;
    @EqualsAndHashCode.Exclude
    private String lastName;
    @EqualsAndHashCode.Exclude
    private boolean isAdmin;
}
