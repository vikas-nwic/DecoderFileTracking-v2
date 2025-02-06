package com.nwic.gt.decoder.file.tracking.system.jobsManager;

import com.nwic.gt.decoder.file.tracking.system.agencies.AndhraPradeshGroundWaterAgency;
import com.nwic.gt.decoder.file.tracking.system.agencies.AmravatiSurfaceWaterAgency;
import com.nwic.gt.decoder.file.tracking.system.agencies.AndhraPradeshSurfaceWaterAgency;
import com.nwic.gt.decoder.file.tracking.system.agencies.ArunachalPradeshSurfaceWaterAgency;
import com.nwic.gt.decoder.file.tracking.system.services.impl.MailSenderService;
import com.nwic.gt.decoder.file.tracking.system.utils.DecoderUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Author: Vikas Predhva
 * Organization: Grant Thornton
 * Date: 05-02-2025
 * Description: This is a single point to manage all Cron Jobs
 */

@Component
@AllArgsConstructor
public class CronJobManager {

    private static final Logger logger = LoggerFactory.getLogger(CronJobManager.class);

    public final MailSenderService mailSenderService;
    public final AndhraPradeshGroundWaterAgency andhraPradeshGroundWaterAgency;
    public final AndhraPradeshSurfaceWaterAgency andhraPradeshSurfaceWaterAgency;
    public final AmravatiSurfaceWaterAgency amravatiSurfaceWaterAgency;
    public final ArunachalPradeshSurfaceWaterAgency arunachalPradeshSurfaceWaterAgency;

    //Mail Sender Service (MailSenderService.java)
    //@Scheduled(cron = "0 0 9 * * ?")
    public void mailJob() throws IOException {
        logger.info("=========================== Mail send to monitoring dashboard job start ===========================");
        mailSenderService.sendEmailWithAttachment();
        logger.info("Execution completed at time: {}", System.currentTimeMillis());
        logger.info("=========================== Mail send to monitoring dashboard job end ===========================\n");
    }


    //Amravati Surface Water Agency (AmravatiSurfaceWaterAgency.java)
    //@Scheduled(cron = "0 9 * * *
//     @Scheduled(cron= "0/30 * * ? * *")
    public void amravatiSurfaceWaterAgencyJob() throws IOException {
        logger.info("=========================== Amravati SurfaceWater Agency job start ===========================");
        amravatiSurfaceWaterAgency.readAllDirectoryFiles();
        logger.info("Execution completed at time: {}", DecoderUtils.timeConversion());
        logger.info("=========================== Amravati SurfaceWater Agency job end===========================\n");
    }


    //Andhra Pradesh Ground Water Agency (AndhraPradeshGroundWaterAgency.java)
    @Scheduled(cron= "0/30 * * ? * *")
    public void andhraPradeshGroundWaterAgencyJob() throws IOException {
        logger.info("=========================== AndhraPradeshGroundWaterAgency job start ===========================");
        andhraPradeshGroundWaterAgency.readAllDirectoryFiles();
        logger.info("Execution completed at time: {}", System.currentTimeMillis());
        logger.info("=========================== APGroundWaterAgency job end ===========================\n");
    }


    //Andhra Pradesh Surface Water Agency (AndhraPradeshGroundWaterAgency.java)
    @Scheduled(cron= "0/30 * * ? * *")
    public void andhraPradeshSurfaceWaterAgency() throws IOException {
        logger.info("=========================== AndhraPradeshSurfaceWaterAgency job start ===========================");
        andhraPradeshSurfaceWaterAgency.readAllDirectoryFiles();
        logger.info("Execution completed at time: {}", System.currentTimeMillis());
        logger.info("=========================== AndhraPradeshSurfaceWaterAgency job end ===========================\n");
    }


    @Scheduled(cron= "0/30 * * ? * *")
    public void arunachalPradeshSurfaceWaterAgencyJob() throws IOException {
        logger.info("=========================== Arunachal Pradesh Surface Water Agency job start =========================== ");
        arunachalPradeshSurfaceWaterAgency.readAllDirectoryFiles();
        logger.info("Arunachal Pradesh Surface Water Agency Job Execution completed at time: {}",DecoderUtils.timeConversion());
        logger.info("===========================  Arunachal Pradesh Surface Water AgencyJob job end =========================== \n");
    }
}
