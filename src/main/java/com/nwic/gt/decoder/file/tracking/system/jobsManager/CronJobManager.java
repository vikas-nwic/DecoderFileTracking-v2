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

    public final RajasthanGroundWaterAgency rajasthanGroundWaterAgency;
    public final RajasthanAtalAgency rajasthanAtalAgency;

    public final UttarPradeshAtalGroundWaterAgency uttarPradeshAtalGroundWaterAgency;
    public final UttarPradeshSurfaceWaterAgency uttarPradeshPradeshSurfaceWaterAgency;

    public final GujaratGroundWaterDAgency gujaratGroundWaterAgency;
    public final GujaratSurfaceWaterAgencyPhase2 gujaratSurfaceWaterAgencyPhase2;

    public final MaharashtraGroundWaterAtalAgency maharashtraGroundWaterAtalAgency;
    public final MaharashtraSurfaceWaterAgency maharashtraSurfaceWaterAgency;

    public final CGWBGPRSAgency cGWBGPRSAgency;
    public final CGWBHydAgency cGWBHydAgency;

    public final TelanganaGroundWaterDAgency telanganaGroundWaterAgency;

    public final BBMBWaterAgency bBMBWaterAgency;

    public final CGWBBangaloreWaterAgency cGWBBangaloreWaterAgency;
    public final CGWBChennaiWaterAgency cGWBChennaiWaterAgency;

    public final GoaSurfaceWaterAgency goaSurfaceWaterAgency;
    public final GujaratSurfaceWaterAgency gujaratSurfaceWaterAgency;

    public final MaharashtraAmravatiSurfaceWaterAgency maharashtraAmravatiSurfaceWaterAgency;
    public final MaharashtraAurangabadSurfaceWaterAgency maharashtraAurangabadSurfaceWaterAgency;

    public final PunjabSurfaceWater1Agency punjabSurfaceWater1Agency;




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
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void amravatiSurfaceWaterAgencyJob() throws IOException {
        logger.info("=========================== Amravati SurfaceWater Agency job start ===========================");
        amravatiSurfaceWaterAgency.readAllDirectoryFiles();
        logger.info("Execution completed at time: {}", DecoderUtils.timeConversion());
        logger.info("=========================== Amravati SurfaceWater Agency job end===========================\n");
    }


    //Andhra Pradesh Ground Water Agency (AndhraPradeshGroundWaterAgency.java)
    //@Scheduled(cron= "0/30 * * ? * *")
   // @Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void andhraPradeshGroundWaterAgencyJob() throws IOException {
        logger.info("=========================== AndhraPradeshGroundWaterAgency job start ===========================");
        andhraPradeshGroundWaterAgency.readAllDirectoryFiles();
        logger.info("Execution completed at time: {}", System.currentTimeMillis());
        logger.info("=========================== APGroundWaterAgency job end ===========================\n");
    }

//    //Andhra Pradesh Surface Water Agency (AndhraPradeshGroundWaterAgency.java)
//    //@Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void andhraPradeshSurfaceWaterAgency() throws IOException {
        logger.info("=========================== AndhraPradeshSurfaceWaterAgency job start ===========================");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                andhraPradeshSurfaceWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("Execution completed at time: {}", System.currentTimeMillis());
        logger.info("=========================== AndhraPradeshSurfaceWaterAgency job end ===========================\n");
    }
//
//    // Arunachal Pradesh Surface Water Agency (ArunachalPradeshSurfaceWaterAgency.java)
//     //@Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void arunachalPradeshSurfaceWaterAgencyJob() throws IOException {
        logger.info("=========================== Arunachal Pradesh Surface Water Agency job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                arunachalPradeshSurfaceWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("Execution completed at time: {}",DecoderUtils.timeConversion());
        logger.info("=========================== Arunachal Pradesh Surface Water Agency job end =========================== \n");
    }
//
//    // Assam Surface Water Agency (AssamSurfaceWaterAgency.java)
//    //@Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void assamSurfaceWaterAgencyJob() throws IOException {
        logger.info("=========================== Assam Surface Water Agency job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                assamSurfaceWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("Execution completed at time: {}",DecoderUtils.timeConversion());
        logger.info("=========================== Assam Surface Water Agency job end =========================== \n");
    }
//
//    // Bihar Ground Water Agency (BiharGroundWaterAgency.java)
    //@Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void biharGroundWaterAgencyJob() throws IOException {
        logger.info("=========================== Bihar Ground Water Agency job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                biharGroundWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("Execution completed at time: {}",DecoderUtils.timeConversion());
        logger.info("=========================== Bihar Ground Water Agency job end =========================== \n");
    }
//
//    // Bihar Surface Water Agency (BiharSurfaceWaterAgency.java)
//    //@Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void biharSurfaceWaterAgencyJob() throws IOException {
        logger.info("=========================== Bihar Surface Water Agency job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                biharSurfaceWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("Execution completed at time: {}",DecoderUtils.timeConversion());
        logger.info("=========================== Bihar Surface Water Agency job end =========================== \n");
    }
//
//    //  Chhattisgarh Ground Water Agency (BiharSurfaceWaterAgency.java)
//    //@Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void chhattisgarhGroundWaterAgencyJob() throws IOException {
        logger.info("=========================== Chhattisgarh Ground Water Agency job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                chhattisgarhGroundWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("Execution completed at time: {}",DecoderUtils.timeConversion());
        logger.info("=========================== Chhattisgarh Ground Water Agency job end =========================== \n");
    }
//
//    // Chhattisgarh Surface Water Agency (BiharSurfaceWaterAgency.java)
//   // @Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void chhattisgarhSurfaceWaterAgencyJob() throws IOException {
        logger.info("=========================== Chhattisgarh Surface Water Agency job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                chhattisgarhSurfaceWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("Execution completed at time: {}",DecoderUtils.timeConversion());
        logger.info("=========================== Chhattisgarh Surface Water Agency job end =========================== \n");
    }
//
//    // Delhi Ground And Surface Water Agency (DelhiGroundAndSurfaceWaterAgency.java)
//    //@Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void delhiGroundSurfaceWaterAgencyJob() throws IOException {
        logger.info("=========================== Delhi Ground And Surface Water Agency job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                delhiGroundAndSurfaceWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("Execution completed at time: {}",DecoderUtils.timeConversion());
        logger.info("=========================== Delhi Ground And Surface Water Agency job end =========================== \n");
    }

//
//    // Delhi Ground Water Agency (HimachalPradeshGroundWaterAgency.java)
//    @Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void himachalPradeshGroundWaterAgencyJob() throws IOException {
        logger.info("=========================== Himachal Pradesh Ground Water Agency job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                himachalPradeshGroundWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("Execution completed at time: {}",DecoderUtils.timeConversion());
        logger.info("=========================== Himachal Ground Water Agency job end =========================== \n");
    }
//
//    //  Himachal Surface Water Agency (HimachalPradeshSurfaceWaterAgency.java)
//    //@Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void himachalPradeshSurfaceWaterAgencyJob() throws IOException {
        logger.info("=========================== Himachal Surface Water Agency Job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                himachalPradeshSurfaceWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("=========================== Himachal Pradesh Surface Water Agency Job end =========================== \n");
    }



     //  Rajasthan Ground Water Agency (RajasthanGroundWaterAgency.java)
    //@Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void rajasthanGroundWaterAgency() throws IOException {
        logger.info("=========================== Rajasthan Ground Water Agency Job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                rajasthanGroundWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("=========================== Rajasthan Ground Water Agency Job end =========================== \n");
    }

    //  Rajasthan Atal Agency (RajasthanAtalAgency.java)
    //@Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void rajasthanAtalAgency() throws IOException {
        logger.info("=========================== Rajasthan Atal Agency Job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                rajasthanAtalAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("=========================== Rajasthan Atal Agency Job end =========================== \n");
    }

     //  Uttar Pradesh Atal Ground Water Agency (UttarPradeshAtalGroundWaterAgency.java)
    //@Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void uttarPradeshAtalGroundWaterAgency() throws IOException {
        logger.info("=========================== Uttar Pradesh Atal Ground Water Agency Job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                uttarPradeshAtalGroundWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("=========================== Uttar Pradesh Atal Ground Water Agency Job end =========================== \n");
    }

    //  Uttar Pradesh Surface Water Agency (UttarPradeshSurfaceWaterAgency.java)
    //@Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void uttarPradeshPradeshSurfaceWaterAgency() throws IOException {
        logger.info("=========================== Uttar Pradesh Surface Water Agency Job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                uttarPradeshPradeshSurfaceWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("=========================== Uttar Pradesh Surface Agency Job end =========================== \n");
    }

     //  Gujarat Ground Water Agency (GujaratGroundWaterAgency.java)
    //@Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void gujaratGroundWaterAgency() throws IOException {
        logger.info("=========================== Gujarat Ground Water Agency Job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                gujaratGroundWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("=========================== Gujarat Ground Water Agency Job end =========================== \n");
    }

    //  Gujarat Surface Water Agency Phase2 (GujaratSurfaceWaterAgencyPhase2.java)
    //@Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void gujaratSurfaceWaterAgencyPhase2() throws IOException {
        logger.info("=========================== Gujarat Surface Water Agency Phase2 Job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                gujaratSurfaceWaterAgencyPhase2.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("=========================== Gujarat Surface Water Agency Phase2 Job end =========================== \n");
    }

    //  Maharashtra Ground Water Agency (MaharashtraGroundWaterAtalAgency.java)
    //@Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void maharashtraGroundWaterAtalAgency() throws IOException {
        logger.info("=========================== Maharashtra Ground Water Agency Job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                maharashtraGroundWaterAtalAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("=========================== Maharashtra Ground Water Agency Job end =========================== \n");
    }

    //  Maharashtra Surface Water Atal Agency (MaharashtraSurfaceWaterAgency.java)
    //@Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void maharashtraSurfaceWaterAgency() throws IOException {
        logger.info("=========================== Maharashtra Surface Water Agency Job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                maharashtraSurfaceWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("=========================== Maharashtra Surface Water Agency Job end =========================== \n");
    }

    //  CGWB GPRS Agency (CGWBGPRSAgency.java)
    //@Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void cGWBGPRSAgency() throws IOException {
        logger.info("=========================== CGWB GPRS Agency Job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                cGWBGPRSAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("=========================== CGWB GPRS Agency Job end =========================== \n");
    }

    //  CGWB Hyd Agency (CGWBHydAgency.java)
    //@Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void cGWBHydAgency() throws IOException {
        logger.info("=========================== CGWB Hyd Agency Job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                cGWBHydAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("=========================== CGWB Hyd Agency Job end =========================== \n");
    }



    //  Telangana Ground Water Agency (TelanganaGroundWaterAgency.java)
    //@Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void telanganaGroundWaterAgency() throws IOException {
        logger.info("=========================== Telangana Ground Water Agency Job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                telanganaGroundWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("=========================== Telangana Ground Water Agency Job end =========================== \n");
    }

    //  BBMB Water Agency (BBMBWaterAgency.java)
    //@Scheduled(cron= "0/30 * * ? * *")
    //@Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void bBMBWaterAgency() throws IOException {
        logger.info("=========================== BBMB Water Agency Job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                bBMBWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("===========================BBMB Water Agency Job end =========================== \n");
    }

    //  CGWB Bangalore Water Agency  (CGWBBangaloreWaterAgency.java)
    //@Scheduled(cron= "0/30 * * ? * *")
    @Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void cGWBBangaloreWaterAgency() throws IOException {
        logger.info("=========================== BBMB Water Agency Job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                cGWBBangaloreWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("===========================BBMB Water Agency Job end =========================== \n");
    }

    //  CGWB Chennai Water Agency  (CGWBChennaiWaterAgency.java)
    // @Scheduled(cron= "0/30 * * ? * *")
    // @Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void cGWBChennaiWaterAgency() throws IOException {
        logger.info("=========================== CGWB Chennai Water Agency Job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                cGWBChennaiWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("===========================CGWB Chennai Water Agency Job end =========================== \n");
    }

    // Goa Surface Water Agency  (GoaSurfaceWaterAgency.java)
    // @Scheduled(cron= "0/30 * * ? * *")
    // @Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void goaSurfaceWaterAgency() throws IOException {
        logger.info("=========================== Goa Surface Water Agency Job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                goaSurfaceWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("=========================== Goa Surface Water Agency Job end =========================== \n");
    }

    // Gujarat Surface Water Agency  (GujaratSurfaceWaterAgency.java)
    // @Scheduled(cron= "0/30 * * ? * *")
    // @Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void gujaratSurfaceWaterAgency() throws IOException {
        logger.info("=========================== Gujarat Surface Water Agency Job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                gujaratSurfaceWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("=========================== Gujarat Surface Water Agency Job end =========================== \n");
    }

    // Maharashtra Amravati Surface Water Agency  (MaharashtraAmravatiSurfaceWaterAgency.java)
    // @Scheduled(cron= "0/30 * * ? * *")
    // @Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void maharashtraAmravatiSurfaceWaterAgency() throws IOException {
        logger.info("=========================== Maharashtra Amravati Surface Water Agency Job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                maharashtraAmravatiSurfaceWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("=========================== Maharashtra Amravati Surface Water Agency Job end =========================== \n");
    }

    // Maharashtra Aurangabad Surface Water Agency  (MaharashtraAurangabadSurfaceWaterAgency.java)
    // @Scheduled(cron= "0/30 * * ? * *")
    // @Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void maharashtraAurangabadSurfaceWaterAgency() throws IOException {
        logger.info("=========================== Maharashtra Aurangabad Surface Water Agency Job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                maharashtraAurangabadSurfaceWaterAgency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("=========================== Maharashtra Aurangabad Surface Water Agency Job end =========================== \n");
    }

    // Punjab Surface Water Agency  (PunjabSurfaceWater1Agency.java)
    // @Scheduled(cron= "0/30 * * ? * *")
    // @Scheduled(fixedRate = 5000, initialDelay = 10000) // Starts after 10s, then runs every 5s
    public void punjabSurfaceWater1Agency() throws IOException {
        logger.info("=========================== Punjab Surface Water Agency Job start =========================== ");
        DecoderUtils.measureExecutionTime(() -> {
            try {
                punjabSurfaceWater1Agency.readAllDirectoryFiles();
            } catch (IOException e) {
                logger.error("Error while reading directory files", e);
            }
        });
        logger.info("=========================== Punjab Surface Water Agency Job end =========================== \n");
    }


}
