package com.nwic.gt.decoder.file.tracking.system.constants;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Author: Vikas Predhva
 * Date: 01-12-2025
 * Description: Api Constant fields, which will be static all time
 */
@Component
public class ApiConstants {

    //@Value("${day.restriction}")
    //private Integer dayRestriction;

    public static final Integer DAY_RESTRICTION = 7;
    public static final String BASEDIR = "D:"; // local
    //public static final String BASEDIR = ""; // staging:- make it blank

    // Amravati Agency
//    public static final String FTP_DATA_Amravati_GW = "D:/ftp_data/AmravatiGw";
    public static final String FTP_DATA_Amravati_SW = BASEDIR +"/ftp_data/AmravatiSw/AmravatiSw/";

    // Andhra Pradesh Agency
    public static final String FTP_DATA_AP_GW = BASEDIR +"/ftp_data/ap_gw/ap_gw/";
    public static final String FTP_DATA_AP_SW = BASEDIR +"/ftp_data/ap_sw/ap_sw/";

    // Arunachal Pradesh Agency
    //public static final String FTP_DATA_Arunachal_GW = BaseDir+"/ftp_data/ArunanchalGW/ArunanchalGW/";  // no file get
    public static final String FTP_DATA_ARUNACHAL_SW = BASEDIR +"/ftp_data/arunachal_sw/arunachal_sw/";

    // Assam Agency
    //public static final String FTP_DATA_ASSAM_GW = BaseDir+"/ftp_data/assam_sw/assam_sw/";   // no file get
    public static final String FTP_DATA_ASSAM_SW = BASEDIR +"/ftp_data/assam_sw/assam_sw/";

    // Bihar Agency
    public static final String FTP_DATA_BIHAR_GW = BASEDIR +"/ftp_data/bihar_gw/bihar_gw/";
    public static final String FTP_DATA_BIHAR_SW = BASEDIR +"/ftp_data/bihar_sw/bihar_sw/";

    // Chhattisgarh Agency
    public static final String FTP_DATA_CHHATTISGARH_GW = BASEDIR +"/ftp_data/chhattisgarh_gw/chhattisgarh_gw/";
    public static final String FTP_DATA_CHHATTISGARH_SW = BASEDIR +"/ftp_data/chhattisgarh_sw/chhattisgarh_sw/";

    // Delhi Agency
    public static final String FTP_DATA_DELHI_SW_GW = BASEDIR +"/ftp_data/Delhi_SW_GW/Delhi_SW_GW/";

    // Himachal Pradesh Agency
    public static final String FTP_DATA_HIMACHAL_GW = BASEDIR +"/ftp_data/hp_gw/hp_gw/";
    public static final String FTP_DATA_HIMACHAL_SW = BASEDIR +"/ftp_data/hp_sw/hp_sw/";

//    -- Received files on: 09-02-2025

    // Rajasthan Agency
    public static final String FTP_DATA_RAJASTHAN_GW = BASEDIR +"/ftp_data/rajasthan_gw/rajasthan_gw";
    public static final String FTP_DATA_RAJASTHAN_ATAL = BASEDIR +"/ftp_data/rajasthan_atal/rajasthan_atal";


    // Uttar Pradesh Agency
    public static final String FTP_DATA_UTTAR_PRADESH_GW_ATAL = BASEDIR +"/ftp_data/upgw_atal/upgw_atal";
    public static final String FTP_DATA_UTTAR_PRADESH_SW = BASEDIR +"/ftp_data/up_sw/up_sw";

    // Gujarat Agency
    public static final String FTP_DATA_GUJARAT_GWD = BASEDIR +"/ftp_data/gujrat_gwd/gujrat_gwd";
    public static final String FTP_DATA_GUJARAT_SW_PHASE2 = BASEDIR +"/ftp_data/gujarat_sw_phase2/gujarat_sw_phase2";

    // Maharashtra Agency
    public static final String FTP_DATA_MAHARASHTRA_GW_ATAL = BASEDIR +"/ftp_data/MaharastraGw_atal/MaharastraGw_atal";
    public static final String FTP_DATA_MAHARASHTRA_SW = BASEDIR +"/ftp_data/MaharastraSw/MaharastraSw";

    // Central Ground Water Board
    public static final String FTP_DATA_CGWB_GPRS = BASEDIR +"/ftp_data/CGWB_GPRS/CGWB_GPRS";
    public static final String FTP_DATA_CGWB_HYD = BASEDIR +"/ftp_data/CGWB_Hyd/CGWB_Hyd";

    // Telangana Ground Water
    public static final String FTP_DATA_TELANGANA_GW = BASEDIR +"/ftp_data/telanagana_gwd/telanagana_gwd";

///////////////////////////
//@PostConstruct
//public void init() {
//    DAY_RESTRICTION = this.dayRestriction;
//}
}
