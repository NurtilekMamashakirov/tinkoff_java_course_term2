package edu.java.controllers;

import edu.java.Services.ScrapperService;
import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import edu.java.exceptions.BadRequestException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LinksController {

    private final static String INVALID_REQUEST_MESSAGE = "Request's parameters are invalid";
    private final static String INVALID_ID_DESCRIPTION = "Id is invalid";
    private ScrapperService scrapperService;

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getLinks(@RequestHeader("Tg-Chat-Id") Long id) {
        checkId(id);
        return ResponseEntity
            .ok()
            .body(scrapperService.getLinks(id));
    }

    @PostMapping("/links")
    public ResponseEntity<LinkResponse> addLink(
        @RequestHeader("Tg-Chat-Id") Long id,
        @Valid @RequestBody AddLinkRequest request
    ) {
        checkId(id);
        return ResponseEntity
            .ok()
            .body(scrapperService.addLink(id, request.link()));
    }

    @DeleteMapping("/links")
    public ResponseEntity<LinkResponse> deleteLink(
        @RequestHeader("Tg-Chat-Id") Long id,
        @Valid @RequestBody AddLinkRequest request
    ) {
        checkId(id);
        return ResponseEntity
            .ok()
            .body(scrapperService.deleteLink(id, request.link()));
    }

    private void checkId(Long id) {
        if (id < 1L) {
            throw new BadRequestException(INVALID_REQUEST_MESSAGE, INVALID_ID_DESCRIPTION);
        }
    }

}
