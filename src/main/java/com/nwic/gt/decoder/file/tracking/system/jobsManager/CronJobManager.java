package com.nwic.gt.decoder.file.tracking.system.jobsManager;

import com.nwic.gt.decoder.file.tracking.system.agencies.APGroundWaterAgency;
import com.nwic.gt.decoder.file.tracking.system.services.impl.MailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class CronJobManager {

    public final MailSenderService mailJob;
    public final APGroundWaterAgency apGroundWaterAgency;

    //@Scheduled(cron = "0 0 9 * * ?")
    public void mailJob() throws IOException {
        mailJob.sendEmailWithAttachment();
    }

    @Scheduled(cron = "0 9 * * * ?")
    public void apGroundWaterAgency() throws IOException {
        apGroundWaterAgency.readAllDirectoryFiles();
    }
}
