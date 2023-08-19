package com.pavel.socialweb.Entity;

import com.pavel.socialweb.Enum.FriendStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "friend")
public class FriendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    @ManyToOne
    @JoinColumn(name = "first_user")
    private UserEntity firstUser;

    @ManyToOne
    @JoinColumn(name = "second_user")
    private UserEntity secondUser;

    @CreationTimestamp
    private LocalDateTime createAt;
}
