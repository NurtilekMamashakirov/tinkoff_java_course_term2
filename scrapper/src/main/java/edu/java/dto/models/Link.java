package edu.java.dto.models;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    private Long id;
    private String link;
    private OffsetDateTime updated_at;
    private OffsetDateTime checked_at;
    private List<Chat> chats;
}
