package edu.java.repository.jpa;

import edu.java.dto.entity.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.net.URI;
import java.util.Optional;

@Repository
public interface JpaLinkRepository extends JpaRepository<LinkEntity, Long> {
    Optional<LinkEntity> findByUrl(URI url);
}
