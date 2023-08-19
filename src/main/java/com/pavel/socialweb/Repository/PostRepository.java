package com.pavel.socialweb.Repository;

import com.pavel.socialweb.Entity.PostEntity;
import com.pavel.socialweb.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity,Long> {
    Page<PostEntity> findByUser(UserEntity userEntity, Pageable pageable);

    Optional<PostEntity> findByTitle(String title);
}
