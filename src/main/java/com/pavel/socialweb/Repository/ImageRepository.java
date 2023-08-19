package com.pavel.socialweb.Repository;

import com.pavel.socialweb.Entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageEntity,Long> {

}
