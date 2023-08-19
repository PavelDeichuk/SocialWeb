package com.pavel.socialweb.Entity;

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
@Table(name = "subscription")
public class SubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sub_user_id")
    private UserEntity subUser;

    @ManyToOne
    @JoinColumn(name = "pub_user_id")
    private UserEntity pubUser;

    @CreationTimestamp
    private LocalDateTime createAt;
}
