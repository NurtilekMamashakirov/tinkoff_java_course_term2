package edu.java.dto.models;

import java.net.URI;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    private Long id;
    private URI url;
    private OffsetDateTime updatedAt;
    private OffsetDateTime checkedAt;
}
