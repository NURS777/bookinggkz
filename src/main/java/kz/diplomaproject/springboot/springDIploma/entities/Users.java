package kz.diplomaproject.springboot.springDIploma.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "phonenumber")
    private String phonenumber;

    @Column(name = "password")
    private String password;

    @Column(name = "city")
    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    private Topics topics;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Roles> roles;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organizations organization;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
        return true;
    }
}
