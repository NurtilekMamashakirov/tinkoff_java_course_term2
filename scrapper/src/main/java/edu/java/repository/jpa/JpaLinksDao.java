package edu.java.repository.jpa;

import edu.java.dto.models.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLinksDao extends JpaRepository<Link, Long> {

}
