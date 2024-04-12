package edu.java.dto.entity;

import edu.java.dto.entity.converter.UriConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;
import lombok.Data;

@Data
@Entity
@Table(name = "link")
public class LinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "link")
    @Convert(converter = UriConverter.class)
    private URI url;
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
    @Column(name = "checked_at")
    private OffsetDateTime checkedAt;
    @ManyToMany(mappedBy = "links", fetch = FetchType.EAGER)
    private Set<ChatEntity> chats;

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LinkEntity link = (LinkEntity) o;
        return Objects.equals(id, link.id) && Objects.equals(url, link.url)
            && Objects.equals(updatedAt, link.updatedAt) && Objects.equals(checkedAt, link.checkedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, updatedAt, checkedAt);
    }
}
