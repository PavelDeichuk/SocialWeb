package com.pavel.socialweb.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "username is null!")
    @Size(min = 3, max = 32, message = "min 3 max 32 size username!")
    private String username;

    @NotNull(message = "password is null!")
    @Size(min = 3, max = 64, message = "min 3 max 64 size password!")
    private String password;

    @Email(message = "Email error validation!")
    private String email;

    private String role;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "user")
    private List<PostEntity> post;
}
