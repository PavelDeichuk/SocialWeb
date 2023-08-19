package com.pavel.socialweb.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "description is null!")
    private String description;

    @ManyToOne
    @JoinColumn(name = "first_user")
    private UserEntity firstUser;

    @ManyToOne
    @JoinColumn(name = "second_user")
    private UserEntity secondUser;
}
