package ru.kata.spring.boot_security.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter @Setter @NoArgsConstructor
public class Role implements GrantedAuthority{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "rolename")
    private String rolename;

    public Role(String role) {
        this.rolename = role;
    }

    public Role(int id, String role) {
        this.id = id;
        this.rolename = role;
    }

    @Override
    public String getAuthority() {
        return rolename;
    }
    @Override
    public String toString() {
        return rolename;
    }
}