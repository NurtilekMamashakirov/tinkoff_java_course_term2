package edu.java.scheduler;

import edu.java.Services.LinkUpdater;
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

    @Scheduled(fixedDelayString = "#{@schedulerInterval}")
    public void update() {
        linkUpdater.update();
    }

}
