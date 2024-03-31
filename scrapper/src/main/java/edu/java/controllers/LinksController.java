package edu.java.controllers;

import edu.java.Services.jdbc.JdbcLinkService;
import edu.java.dto.models.Link;
import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import edu.java.exceptions.BadRequestException;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LinksController {

    private final static String INVALID_REQUEST_MESSAGE = "Request's parameters are invalid";
    private final static String INVALID_ID_DESCRIPTION = "Id is invalid";
    private JdbcLinkService linkService;

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getLinks(@RequestHeader("Tg-Chat-Id") Long id) {
        checkId(id);
        List<LinkResponse> linkResponses = new ArrayList<>();
        List<Link> links = linkService.listAll(id);
        for (Link link : links) {
            linkResponses.add(new LinkResponse(link.getId().intValue(), link.getLink().toString()));
        }
        ListLinksResponse listLinksResponse = new ListLinksResponse(linkResponses, linkResponses.size());
        return ResponseEntity
            .ok()
            .body(listLinksResponse);
    }

    @PostMapping("/links")
    public ResponseEntity<LinkResponse> addLink(
        @RequestHeader("Tg-Chat-Id") Long id,
        @Valid @RequestBody AddLinkRequest request
    ) {
        checkId(id);
        Link link = linkService.add(id, URI.create(request.link()));
        return ResponseEntity
            .ok()
            .body(new LinkResponse(link.getId().intValue(), link.getLink().toString()));
    }

    @DeleteMapping("/links")
    public ResponseEntity<LinkResponse> deleteLink(
        @RequestHeader("Tg-Chat-Id") Long id,
        @Valid @RequestBody AddLinkRequest request
    ) {
        checkId(id);
        Link link = linkService.remove(id, URI.create(request.link()));
        return ResponseEntity
            .ok()
            .body(new LinkResponse(link.getId().intValue(), link.getLink().toString()));
    }

    private void checkId(Long id) {
        if (id < 1L) {
            throw new BadRequestException(INVALID_REQUEST_MESSAGE, INVALID_ID_DESCRIPTION);
        }
    }

}
