package com.bsep.proj.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "users")
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
//    @ManyToMany(fetch = FetchType.EAGER)
    @ElementCollection(fetch = FetchType.EAGER)
    // napravi tabelu za rolu, ne znam kako da enum cuvam
    private List<UserRole> role = new ArrayList<>();
    private String firstName;
    private String lastName;
    private String verificationCode = "";
    private String forgotPasswordVerificationCode = "";
    private String passwordlessLoginVerificationCode = "";
    private Date passwordlessLoginVerificationCodeIssued;
    private Boolean enabled = true;

    public User(String username, String password, List<UserRole> role, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        this.role.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.name())));
//        this.role.forEach(role -> System.out.println(role.name()));
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
