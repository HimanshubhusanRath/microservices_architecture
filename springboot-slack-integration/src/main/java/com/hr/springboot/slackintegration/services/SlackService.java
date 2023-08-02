package com.hr.springboot.slackintegration.services;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.model.Attachment;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;

@Service
public class SlackService {
    private static final String WEBHOOK_URL = "https://hooks.slack.com/services/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";


    private Logger LOG = LoggerFactory.getLogger(SlackService.class.getName());

    public void notifySlack(String message) {
        // Prepare the message to be sent to slack channel
        final Payload payload = Payload.builder()
                .attachments(Collections.singletonList(Attachment.builder().channelName("#testHR").build()))
                .text(message)
                .build();

        // Send the message to slack channel using webhook
        try
        {
            final WebhookResponse webhookResponse = Slack.getInstance().send(WEBHOOK_URL, payload);
            if (webhookResponse.getCode() == 200)
            {
                LOG.info("Success send slack !");
            }
            LOG.info(webhookResponse.getMessage());
        } catch (IOException e) {
            LOG.error("Unexpected Error! WebHook: " + e);
        }
    }
}
