package com.nwic.gt.decoder.file.tracking.system.constants;


import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Author: Vikas Predhva
 * Date: 01-12-2025
 * Description:
 */
public class ApiConstants {

    public static final Integer DAY_RESTRICTION = 7;  // do not hard code it, maintain it in a txt file.

    // Andhra Pradesh Agency
    public static final String GPRS_INSAT_AP_GW = "D:/ftp_data/gprs_insat/gprs_insat/ap_gw/ap_gw";
    public static final String GPRS_INSAT_AP_SW = "D:/ftp_data/gprs_insat/gprs_insat/ap_sw/ap_sw";

    // Arunachal Pradesh Agency
    //public static final String GPRS_INSAT_Arunachal_GW = "D:/ftp_data/gprs_insat/gprs_insat/ArunanchalGW";  // no file yet
    public static final String GPRS_INSAT_Arunachal_SW = "D:/ftp_data/gprs_insat/gprs_insat/arunachal_sw/arunachal_sw";

    // Assam Agency
    //public static final String GPRS_INSAT_Assam_GW = "D:/ftp_data/gprs_insat/gprs_insat/assam_sw";   // no file yet
    public static final String GPRS_INSAT_Assam_SW = "D:/ftp_data/gprs_insat/gprs_insat/assam_sw/assam_sw";

    // Bihar Agency
    public static final String GPRS_INSAT_Bihar_GW = "D:/ftp_data/gprs_insat/gprs_insat/bihar_gw/bihar_gw";
    public static final String GPRS_INSAT_Bihar_SW = "D:/ftp_data/gprs_insat/gprs_insat/bihar_sw/bihar_sw";

    // Chhattisgarh Agency
    public static final String GPRS_INSAT_chhattisgarh_GW = "D:/ftp_data/gprs_insat/gprs_insat/chhattisgarh_gw/chhattisgarh_gw";
    public static final String GPRS_INSAT_chhattisgarh_SW = "D:/ftp_data/gprs_insat/gprs_insat/chhattisgarh_sw/chhattisgarh_sw";

    // Delhi Agency
    public static final String GPRS_INSAT_Delhi_SW_GW = "D:/ftp_data/gprs_insat/gprs_insat/Delhi_SW_GW/Delhi_SW_GW";

    // Himachal Pradesh Agency
    public static final String GPRS_INSAT_Himachal_GW = "D:/ftp_data/gprs_insat/gprs_insat/hp_gw/hp_gw";
    public static final String GPRS_INSAT_Himachal_SW = "D:/ftp_data/gprs_insat/gprs_insat/hp_sw/hp_sw";


    // Amravati Agency
//    public static final String GPRS_INSAT_Amravati_GW = "D:/ftp_data/gprs_insat/gprs_insat/AmravatiGw";
    public static final String GPRS_INSAT_Amravati_SW = "D:/ftp_data/gprs_insat/gprs_insat/AmravatiSw";
















    //---------------------------------------------  PROCESSED_FILES ---------------------------------------------

    // AP Agency PROCESSED FILES PATH
    public static final String AP_GW_PROCESSED_FILES_PATH = "D:\\ftp_data\\processed-files\\ap_gw_processed_files.txt";
    public static final String AP_SW_PROCESSED_FILES_PATH = "D:\\ftp_data\\processed-files\\ap_sw_processed_files.txt";

    //  // Amravati Agency PROCESSED FILES PATH
//    public static final String Amravati_GW_PROCESSED_FILES_PATH = "D:\\ftp_data\\processed-files\\AmravatiGw_processed_files.txt";
    public static final String Amravati_SW_PROCESSED_FILES_PATH = "D:\\ftp_data\\processed-files\\AmravatiSw_processed_files.txt";


    // Assam Agency PROCESSED FILES PATH
//    public static final String Assam_GW_PROCESSED_FILES_PATH = "D:\\ftp_data\\processed-files\\Assam_GW_processed_files.txt";
//    public static final String Assam_SW_PROCESSED_FILES_PATH = "D:\\ftp_data\\processed-files\\Assam_SW_processed_files.txt";

//    // Arunachal pradesh cm Agency PROCESSED FILES PATH
//    public static final String Arunanchal_GW_PROCESSED_FILES_PATH = "D:\\ftp_data\\processed-files\\Arunanchal_GW_processed_files.txt";
    public static final String Arunanchal_SW_PROCESSED_FILES_PATH = "D:\\ftp_data\\processed-files\\Arunanchal_SW_processed_files.txt";

}
