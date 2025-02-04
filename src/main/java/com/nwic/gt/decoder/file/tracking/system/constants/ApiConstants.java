package com.nwic.gt.decoder.file.tracking.system.constants;


import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class ApiConstants {

    public static final Integer DAY_RESTRICTION = 7;  // do not hard code it, maintain it in a txt file.
   //public static final String DAY_RESTRICTION = "7";  // do not hard code it, maintain it in a txt file.


    // AP Agency
    public static final String  GPRS_INSAT_AP_GW = "D:/ftp_data/gprs_insat/gprs_insat/ap_gw";
    public static final String  GPRS_INSAT_AP_SW = "D:/ftp_data/gprs_insat/gprs_insat/ap_sw";

    // Amravati Agency
    public static final String  GPRS_INSAT_Amravati_GW = "D:/ftp_data/gprs_insat/gprs_insat/AmravatiSw";
    public static final String  GPRS_INSAT_Amravati_SW = "D:/ftp_data/gprs_insat/gprs_insat/AmravatiGw";

    // Assam Agency
    public static final String  GPRS_INSAT_Assam_GW = "D:/ftp_data/gprs_insat/gprs_insat/AssamGW";   // no file yet
    public static final String  GPRS_INSAT_Assam_SW = "D:/ftp_data/gprs_insat/gprs_insat/AssamSW";

    // Arunanchal Agency
    public static final String  GPRS_INSAT_Arunanchal_GW = "D:/ftp_data/gprs_insat/gprs_insat/ArunanchalSW";  // no file yet
    public static final String  GPRS_INSAT_Arunanchal_SW = "D:/ftp_data/gprs_insat/gprs_insat/ArunanchalSW";


    //---------------------------------------------  PROCESSED_FILES ---------------------------------------------

    // AP Agency PROCESSED FILES PATH
    public static final String AP_GW_PROCESSED_FILES_PATH = "D:\\ftp_data\\processed-files\\ap_gw_processed_files.txt";
    public static final String AP_SW_PROCESSED_FILES_PATH = "D:\\ftp_data\\processed-files\\ap_sw_processed_files.txt";

    //  // Amravati Agency PROCESSED FILES PATH
    public static final String Amravati_GW_PROCESSED_FILES_PATH = "D:\\ftp_data\\processed-files\\Amravati_GW_processed_files.txt";
    public static final String Amravati_SW_PROCESSED_FILES_PATH = "D:\\ftp_data\\processed-files\\Amravati_GW_processed_files.txt";

    // Assam Agency PROCESSED FILES PATH
    public static final String Assam_GW_PROCESSED_FILES_PATH = "D:\\ftp_data\\processed-files\\Assam_GW_processed_files.txt";
    public static final String Assam_SW_PROCESSED_FILES_PATH = "D:\\ftp_data\\processed-files\\Assam_SW_processed_files.txt";

    // Arunanchal Agency PROCESSED FILES PATH
    public static final String Arunanchal_GW_PROCESSED_FILES_PATH = "D:\\ftp_data\\processed-files\\Arunanchal_GW_processed_files.txt";
    public static final String Arunanchal_SW_PROCESSED_FILES_PATH = "D:\\ftp_data\\processed-files\\Arunanchal_SW_processed_files.txt";

}
