package edu.java.bot.services;

import edu.java.bot.dto.request.LinkUpdate;

public interface BotService {
    void handleUpdates(LinkUpdate linkUpdate);
}
