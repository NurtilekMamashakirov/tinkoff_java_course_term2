package edu.java.dto.entity;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import java.util.List;

@Data
public class ChatEntity {
    @Id
    private Long id;
    @ManyToMany
    private List<LinkEntity> linkEntities;
}
