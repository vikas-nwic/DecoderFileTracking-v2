package com.nwic.gt.decoder.file.tracking.system.jobsManager;

import com.nwic.gt.decoder.file.tracking.system.agencies.*;
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
    public final AssamSurfaceWaterAgency assamSurfaceWaterAgency;
    public final BiharGroundWaterAgency biharGroundWaterAgency;
    public final BiharSurfaceWaterAgency biharSurfaceWaterAgency;
    public final ChhattisgarhGroundWaterAgency chhattisgarhGroundWaterAgency;
    private final ChhattisgarhSurfaceWaterAgency chhattisgarhSurfaceWaterAgency;
    public final DelhiGroundAndSurfaceWaterAgency delhiGroundAndSurfaceWaterAgency;
    public final HimachalPradeshGroundWaterAgency himachalPradeshGroundWaterAgency;
    public final HimachalPradeshSurfaceWaterAgency himachalPradeshSurfaceWaterAgency;

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
     //@Scheduled(cron= "0/30 * * ? * *")
//    @Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
//    public void amravatiSurfaceWaterAgencyJob() throws IOException {
//        logger.info("=========================== Amravati SurfaceWater Agency job start ===========================");
//        amravatiSurfaceWaterAgency.readAllDirectoryFiles();
//        logger.info("Execution completed at time: {}", DecoderUtils.timeConversion());
//        logger.info("=========================== Amravati SurfaceWater Agency job end===========================\n");
//    }


    //Andhra Pradesh Ground Water Agency (AndhraPradeshGroundWaterAgency.java)
    //@Scheduled(cron= "0/30 * * ? * *")
    @Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void andhraPradeshGroundWaterAgencyJob() throws IOException {
        logger.info("=========================== AndhraPradeshGroundWaterAgency job start ===========================");
        andhraPradeshGroundWaterAgency.readAllDirectoryFiles();
        logger.info("Execution completed at time: {}", System.currentTimeMillis());
        logger.info("=========================== APGroundWaterAgency job end ===========================\n");
    }

//    //Andhra Pradesh Surface Water Agency (AndhraPradeshGroundWaterAgency.java)
//    //@Scheduled(cron= "0/30 * * ? * *")
//    @Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
//    public void andhraPradeshSurfaceWaterAgency() throws IOException {
//        logger.info("=========================== AndhraPradeshSurfaceWaterAgency job start ===========================");
//        DecoderUtils.measureExecutionTime(() -> {
//            try {
//                andhraPradeshSurfaceWaterAgency.readAllDirectoryFiles();
//            } catch (IOException e) {
//                logger.error("Error while reading directory files", e);
//            }
//        });
//        logger.info("Execution completed at time: {}", System.currentTimeMillis());
//        logger.info("=========================== AndhraPradeshSurfaceWaterAgency job end ===========================\n");
//    }
//
//    // Arunachal Pradesh Surface Water Agency (ArunachalPradeshSurfaceWaterAgency.java)
//     //@Scheduled(cron= "0/30 * * ? * *")
//    @Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
//    public void arunachalPradeshSurfaceWaterAgencyJob() throws IOException {
//        logger.info("=========================== Arunachal Pradesh Surface Water Agency job start =========================== ");
//        DecoderUtils.measureExecutionTime(() -> {
//            try {
//                arunachalPradeshSurfaceWaterAgency.readAllDirectoryFiles();
//            } catch (IOException e) {
//                logger.error("Error while reading directory files", e);
//            }
//        });
//        logger.info("Execution completed at time: {}",DecoderUtils.timeConversion());
//        logger.info("=========================== Arunachal Pradesh Surface Water Agency job end =========================== \n");
//    }
//
//    // Assam Surface Water Agency (AssamSurfaceWaterAgency.java)
//    //@Scheduled(cron= "0/30 * * ? * *")
//    @Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
//    public void assamSurfaceWaterAgencyJob() throws IOException {
//        logger.info("=========================== Assam Surface Water Agency job start =========================== ");
//        DecoderUtils.measureExecutionTime(() -> {
//            try {
//                assamSurfaceWaterAgency.readAllDirectoryFiles();
//            } catch (IOException e) {
//                logger.error("Error while reading directory files", e);
//            }
//        });
//        logger.info("Execution completed at time: {}",DecoderUtils.timeConversion());
//        logger.info("=========================== Assam Surface Water Agency job end =========================== \n");
//    }
//
//    // Bihar Ground Water Agency (BiharGroundWaterAgency.java)
//    //@Scheduled(cron= "0/30 * * ? * *")
//    @Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
//    public void biharGroundWaterAgencyJob() throws IOException {
//        logger.info("=========================== Bihar Ground Water Agency job start =========================== ");
//        DecoderUtils.measureExecutionTime(() -> {
//            try {
//                biharGroundWaterAgency.readAllDirectoryFiles();
//            } catch (IOException e) {
//                logger.error("Error while reading directory files", e);
//            }
//        });
//        logger.info("Execution completed at time: {}",DecoderUtils.timeConversion());
//        logger.info("=========================== Bihar Ground Water Agency job end =========================== \n");
//    }
//
//    // Bihar Surface Water Agency (BiharSurfaceWaterAgency.java)
//    //@Scheduled(cron= "0/30 * * ? * *")
//    @Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
//    public void biharSurfaceWaterAgencyJob() throws IOException {
//        logger.info("=========================== Bihar Surface Water Agency job start =========================== ");
//        DecoderUtils.measureExecutionTime(() -> {
//            try {
//                biharSurfaceWaterAgency.readAllDirectoryFiles();
//            } catch (IOException e) {
//                logger.error("Error while reading directory files", e);
//            }
//        });
//        logger.info("Execution completed at time: {}",DecoderUtils.timeConversion());
//        logger.info("=========================== Bihar Surface Water Agency job end =========================== \n");
//    }
//
//    //  Chhattisgarh Ground Water Agency (BiharSurfaceWaterAgency.java)
//    //@Scheduled(cron= "0/30 * * ? * *")
//    @Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
//    public void chhattisgarhGroundWaterAgencyJob() throws IOException {
//        logger.info("=========================== Chhattisgarh Ground Water Agency job start =========================== ");
//        DecoderUtils.measureExecutionTime(() -> {
//            try {
//                chhattisgarhGroundWaterAgency.readAllDirectoryFiles();
//            } catch (IOException e) {
//                logger.error("Error while reading directory files", e);
//            }
//        });
//        logger.info("Execution completed at time: {}",DecoderUtils.timeConversion());
//        logger.info("=========================== Chhattisgarh Ground Water Agency job end =========================== \n");
//    }
//
//    // Chhattisgarh Surface Water Agency (BiharSurfaceWaterAgency.java)
//   // @Scheduled(cron= "0/30 * * ? * *")
//    @Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
//    public void chhattisgarhSurfaceWaterAgencyJob() throws IOException {
//        logger.info("=========================== Chhattisgarh Surface Water Agency job start =========================== ");
//        DecoderUtils.measureExecutionTime(() -> {
//            try {
//                chhattisgarhSurfaceWaterAgency.readAllDirectoryFiles();
//            } catch (IOException e) {
//                logger.error("Error while reading directory files", e);
//            }
//        });
//        logger.info("Execution completed at time: {}",DecoderUtils.timeConversion());
//        logger.info("=========================== Chhattisgarh Surface Water Agency job end =========================== \n");
//    }
//
//    // Delhi Ground And Surface Water Agency (DelhiGroundAndSurfaceWaterAgency.java)
//    //@Scheduled(cron= "0/30 * * ? * *")
//    @Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
//    public void DelhiGroundSurfaceWaterAgencyJob() throws IOException {
//        logger.info("=========================== Delhi Ground And Surface Water Agency job start =========================== ");
//        DecoderUtils.measureExecutionTime(() -> {
//            try {
//                delhiGroundAndSurfaceWaterAgency.readAllDirectoryFiles();
//            } catch (IOException e) {
//                logger.error("Error while reading directory files", e);
//            }
//        });
//        logger.info("Execution completed at time: {}",DecoderUtils.timeConversion());
//        logger.info("=========================== Delhi Ground And Surface Water Agency job end =========================== \n");
//    }
//
//    // Delhi Ground Water Agency (HimachalPradeshGroundWaterAgency.java)
//    //@Scheduled(cron= "0/30 * * ? * *")
//    @Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
//    public void HimachalPradeshGroundWaterAgencyJob() throws IOException {
//        logger.info("=========================== Himachal Pradesh Ground Water Agency job start =========================== ");
//        DecoderUtils.measureExecutionTime(() -> {
//            try {
//                himachalPradeshGroundWaterAgency.readAllDirectoryFiles();
//            } catch (IOException e) {
//                logger.error("Error while reading directory files", e);
//            }
//        });
//        logger.info("Execution completed at time: {}",DecoderUtils.timeConversion());
//        logger.info("=========================== Himachal Ground Water Agency job end =========================== \n");
//    }
//
//    //  Himachal Surface Water Agency (HimachalPradeshSurfaceWaterAgency.java)
//    //@Scheduled(cron= "0/30 * * ? * *")
//    @Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
//    public void HimachalPradeshSurfaceWaterAgencyJob() throws IOException {
//        logger.info("=========================== Himachal Surface Water Agency Job start =========================== ");
//        DecoderUtils.measureExecutionTime(() -> {
//            try {
//                himachalPradeshSurfaceWaterAgency.readAllDirectoryFiles();
//            } catch (IOException e) {
//                logger.error("Error while reading directory files", e);
//            }
//        });
//        logger.info("=========================== Himachal Pradesh Surface Water Agency Job end =========================== \n");
//    }

}
