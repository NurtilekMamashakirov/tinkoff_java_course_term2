package edu.java.scheduler;

import edu.java.services.LinkUpdater;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@EnableScheduling
@Component
@AllArgsConstructor
public class LinkUpdateScheduler {

    private LinkUpdater linkUpdater;
    private final static String UPDATES_INFO = "Было обновлено %s ссылок";

    @Scheduled(fixedDelayString = "#{@schedulerInterval}")
    public void update() {
        log.info(String.format(UPDATES_INFO, linkUpdater.update()));
    }

}
