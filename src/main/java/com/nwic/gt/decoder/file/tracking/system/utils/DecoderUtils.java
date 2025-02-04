package com.nwic.gt.decoder.file.tracking.system.utils;

import com.nwic.gt.decoder.file.tracking.system.constants.ApiConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Component
@Service
public class DecoderUtils {

    private static final Logger logger = LoggerFactory.getLogger(DecoderUtils.class);

    public static boolean  fileDateValidation(String fileName1) {

        int ABSOLUTE_YEAR = Calendar.getInstance().get(Calendar.YEAR);
        int PARTIAL_YEAR = ABSOLUTE_YEAR % 100;
        int MINUS_DAYS = ApiConstants.DAY_RESTRICTION;
        boolean isValidFile = false;

        logger.info("ABSOLUTE_YEAR: " + ABSOLUTE_YEAR + " PARTIAL_YEAR: " + PARTIAL_YEAR);
        String[] fileNames = {fileName1};

        // Regex patterns to match various date formats with time index
        String[] patterns = {
                "\\d{4}/\\d{2}/\\d{2}_\\d{4}", // YYYY/MM/DD_HHMM
                "\\d{2}/\\d{2}/\\d{4}_\\d{4}", // DD/MM/YYYY_HHMM
                "\\d{2}/\\d{2}/\\d{2}_\\d{4}", // DD/MM/YY_HHMM
                "\\d{4}-\\d{2}-\\d{2}_\\d{4}", // YYYY-MM-DD_HHMM
                "\\d{2}-\\d{2}-\\d{4}_\\d{4}", // DD-MM-YYYY_HHMM
                "\\d{2}-\\d{2}-\\d{2}_\\d{4}", // DD-MM-YY_HHMM
                "\\d{6}_\\d{4}" // DDMMYY_HHMM & YYMMDD_HHMM
        };

        // Date format patterns corresponding to the regex patterns
        String[] dateFormats = {
                "yyyy/MM/dd_HHmm",
                "dd/MM/yyyy_HHmm",
                "dd/MM/yy_HHmm",
                "yyyy-MM-dd_HHmm",
                "dd-MM-yyyy_HHmm",
                "dd-MM-yy_HHmm",
                "ddMMyy_HHmm"
        };

        for (String fileName : fileNames) {
            // logger.info("\nfileName loop-1...");
            boolean matchFound = false; // To track if a match is found

            for (int i = 0; i < patterns.length; i++) {
                // logger.info("\npatterns check loop-2...");
                if (matchFound)
                    break; // Exit loop if a match is already found

                Pattern regex = Pattern.compile(patterns[i]);
                Matcher matcher = regex.matcher(fileName);

                while (matcher.find()) {
                    // logger.info("\nmatcher check while loop-3...");

                    matchFound = true; // Set match flag
                    String dateAndIndex = matcher.group();
                    logger.info("\nFile: " + fileName);
                    logger.info("Extracted: " + dateAndIndex);

                    // Parse the extracted date and compare with the current date
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat(dateFormats[i]);
                        Date extractedDate = sdf.parse(dateAndIndex);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(extractedDate);

                        String datePartTemp = dateAndIndex.substring(0, 6);
                        // logger.info("datePartTemp: " + datePartTemp);
                        // logger.info("substring : " + datePartTemp.substring(4, 6));

                        if (!dateAndIndex.equals(ABSOLUTE_YEAR) // "2025"
                                && datePartTemp.startsWith(String.valueOf(PARTIAL_YEAR)) && // "25"
                                !datePartTemp.substring(4, 6)
                                        .equals(String.valueOf(PARTIAL_YEAR))) {
                            // logger.info("codition 1 satisfied");
                            // this condition will handle this 250115_1505
                            if (patterns[i].equals("\\d{6}_\\d{4}")) {
                                // Special handling for DDMMYY_HHMM
                                String datePart = dateAndIndex.substring(0, 6);
                                int day = Integer.parseInt(datePart.substring(4, 6));
                                int month = Integer.parseInt(datePart.substring(2, 4)) - 1; // Month is zero-based in
                                // Calendar
                                int year = Integer.parseInt(datePart.substring(0, 2));
                                // Adjust year: if year is 25 or below, treat it as 2025
                                if (year <= 31) { // Treat `25` as `2025`
                                    year += 2000;
                                }
                                calendar.set(year, month, day);
                                extractedDate = calendar.getTime();
                            }
                            // logger.info("codition 1 end");
                        }
                        if (!dateAndIndex.equals(ABSOLUTE_YEAR) // "2025"
                                && datePartTemp.endsWith(String.valueOf(PARTIAL_YEAR)) // "25"
                                && !datePartTemp.substring(4, 6).equals(String.valueOf(PARTIAL_YEAR))) {
                            // logger.info("codition 2 satisfied");
                        } else {
                            if (patterns[i].equals("\\d{6}_\\d{4}") ||
                                    patterns[i].equals("\\d{2}/\\d{2}/\\d{2}_\\d{4}") ||
                                    patterns[i].equals("\\d{2}-\\d{2}-\\d{2}_\\d{4}")) {
                                String datePart = dateAndIndex.substring(0, 6);
                                if (datePart.startsWith("25")) {
                                    calendar.set(Calendar.YEAR, ABSOLUTE_YEAR); // 2025
                                    extractedDate = calendar.getTime();
                                }
                                if (datePart.endsWith(String.valueOf(PARTIAL_YEAR))) { // "25"
                                    calendar.set(Calendar.YEAR, ABSOLUTE_YEAR); // 2025
                                    extractedDate = calendar.getTime();
                                }
                            }
                        } // else end

                        // Get the current date
                        Date currentDate = new Date();
                        calendar.setTime(currentDate);
                        calendar.add(Calendar.DAY_OF_YEAR, -MINUS_DAYS);
                        Date date7DaysAgo = calendar.getTime();

                        logger.info("Extracted date: " + extractedDate);
                        if (extractedDate.before(date7DaysAgo)) {
                            logger.info("The extracted date is older than " + MINUS_DAYS + " days.");
                            isValidFile = false;
                        } else if (extractedDate.after(currentDate)) {
                            logger.info("The extracted date is in the future.");
                            isValidFile = false;
                        } else {
                            logger.info("The extracted date is within the last " + MINUS_DAYS + " days.");
                            isValidFile = true;

                        }
                    } catch (ParseException e) {
                        // logger.info("Error parsing date: " + dateAndIndex);
                        logger.info("Error parsing date: " + dateAndIndex, e);
                    }
                    // logger.info("\nwhile loop-2 end before break");
                    break; // Exit while loop after the first match

                } // while end
                // logger.info("\nwhile loop-2 end");
            } // loop-2 end
            // logger.info("\nloop-1 end...");
        } // loop-1 end
        return isValidFile;
    }


    public static boolean  contentDateValidation(String contentDate) {
        logger.info("\ncontentDateValidation() start...");
        int ABSOLUTE_YEAR = Calendar.getInstance().get(Calendar.YEAR);
        int PARTIAL_YEAR = ABSOLUTE_YEAR % 100;
        int MINUS_DAYS = 7;
        boolean isValidFile = false;

        logger.info("ABSOLUTE_YEAR: " + ABSOLUTE_YEAR + " PARTIAL_YEAR: " + PARTIAL_YEAR);
        String[] fileNames = {contentDate};

        // Regex patterns to match various date formats with time index
        String[] patterns = {
                "\\d{2}/\\d{2}/\\d{2} \\d{2}:\\d{2}", // DD/MM/YY HH:MM
        };

        // Date format patterns corresponding to the regex patterns
        String[] dateFormats = {
                "dd/MM/yy HH:mm"
        };

        for (String fileName : fileNames) {
             logger.info(" content Date loop-1...");
            boolean matchFound = false; // To track if a match is found

            for (int i = 0; i < patterns.length; i++) {
                logger.info(" content Date patterns check loop-2...");
                if (matchFound)
                    break; // Exit loop if a match is already found

                Pattern regex = Pattern.compile(patterns[i]);
                Matcher matcher = regex.matcher(fileName);

                while (matcher.find()) {
                    // logger.info("\nmatcher check while loop-3...");

                    matchFound = true; // Set match flag
                    String dateAndIndex = matcher.group();
                    logger.info(" while content Date : " + fileName);
                    logger.info("while contentDate Extracted: " + dateAndIndex);

                    // Parse the extracted date and compare with the current date
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat(dateFormats[i]);
                        Date extractedDate = sdf.parse(dateAndIndex);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(extractedDate);

                        String datePartTemp = dateAndIndex.substring(0, 6);
                        // logger.info("datePartTemp: " + datePartTemp);
                        // logger.info("substring : " + datePartTemp.substring(4, 6));

                        if (!dateAndIndex.equals(ABSOLUTE_YEAR) // "2025"
                                && datePartTemp.startsWith(String.valueOf(PARTIAL_YEAR)) && // "25"
                                !datePartTemp.substring(4, 6)
                                        .equals(String.valueOf(PARTIAL_YEAR))) {
                            // logger.info("codition 1 satisfied");
                            // this condition will handle this 250115_1505
                            if (patterns[i].equals("\\d{6}_\\d{4}")) {
                                // Special handling for DDMMYY_HHMM
                                String datePart = dateAndIndex.substring(0, 6);
                                int day = Integer.parseInt(datePart.substring(4, 6));
                                int month = Integer.parseInt(datePart.substring(2, 4)) - 1; // Month is zero-based in
                                // Calendar
                                int year = Integer.parseInt(datePart.substring(0, 2));
                                // Adjust year: if year is 25 or below, treat it as 2025
                                if (year <= 31) { // Treat `25` as `2025`
                                    year += 2000;
                                }
                                calendar.set(year, month, day);
                                extractedDate = calendar.getTime();
                            }
                            // logger.info("codition 1 end");
                        }
                        if (!dateAndIndex.equals(ABSOLUTE_YEAR) // "2025"
                                && datePartTemp.endsWith(String.valueOf(PARTIAL_YEAR)) // "25"
                                && !datePartTemp.substring(4, 6).equals(String.valueOf(PARTIAL_YEAR))) {
                            // logger.info("codition 2 satisfied");
                        } else {
                            if (patterns[i].equals("\\d{6}_\\d{4}") ||
                                    patterns[i].equals("\\d{2}/\\d{2}/\\d{2}_\\d{4}") ||
                                    patterns[i].equals("\\d{2}-\\d{2}-\\d{2}_\\d{4}")) {
                                String datePart = dateAndIndex.substring(0, 6);
                                if (datePart.startsWith("25")) {
                                    calendar.set(Calendar.YEAR, ABSOLUTE_YEAR); // 2025
                                    extractedDate = calendar.getTime();
                                }
                                if (datePart.endsWith(String.valueOf(PARTIAL_YEAR))) { // "25"
                                    calendar.set(Calendar.YEAR, ABSOLUTE_YEAR); // 2025
                                    extractedDate = calendar.getTime();
                                }
                            }
                        } // else end

                        // Get the current date
                        Date currentDate = new Date();
                        calendar.setTime(currentDate);
                        calendar.add(Calendar.DAY_OF_YEAR, -MINUS_DAYS);
                        Date date7DaysAgo = calendar.getTime();

                        logger.info("content Date Extracted date: " + extractedDate);
                        if (extractedDate.before(date7DaysAgo)) {
                            logger.info("The extracted date is older than " + MINUS_DAYS + " days.");
                            isValidFile = false;
                        } else if (extractedDate.after(currentDate)) {
                            logger.info("The extracted date is in the future.");
                            isValidFile = false;
                        } else {
                            logger.info("The extracted date is within the last " + MINUS_DAYS + " days.");
                            isValidFile = true;
                        }
                    } catch (ParseException e) {
                        // logger.info("Error parsing date: " + dateAndIndex);
                        logger.info("Error parsing date: " + dateAndIndex, e);
                    }
                     logger.info(" content Date while loop-2 end before break");
                    break; // Exit while loop after the first match

                } // while end
                // logger.info("\nwhile loop-2 end");
            } // loop-2 end
            // logger.info("\nloop-1 end...");
        } // loop-1 end
        logger.info("contentDateValidation() start...");
        return isValidFile;
    }

    ////////////////////////////////////


    public static List<String> readCsv(Path file, String createdDate) throws IOException {
        List<String> allLines = Files.readAllLines(file);
        for (String line : allLines) {
            System.out.println(line);
        }
        return allLines;
    }

    public static List<String> readCsvFile(Path file, String createdDate) throws IOException {

        boolean isHeader = true;
        List<String> matchingRows = new ArrayList<String>();
        LocalDate currentDate = LocalDate.now();
        List<String> resultList = new ArrayList<String>();

        try {
            // Read all lines from the CSV file
            List<String> lines = Files.readAllLines(file);
            for (String line : lines) {
                // Skip empty lines
                if (line.trim().isEmpty()) { continue; }
                // Skip the header line if present
                if (isHeader) { isHeader = false;continue; }
                // Split the line into cells by comma
                String[] cells = line.split(",", -1);
                // Check if the row has at least two columns and the first column starts with '&'
                if (cells.length >= 2 && cells[0].startsWith("&")) {
                    String firstTwoCells = cells[0] + "," + cells[1];
                    //logger.info("cell1: "+cells[0]+ " cell2: "+cells[1]);
                    String loggerId = cells[0];
                    String fileCellDate = cells[1];
                    String today_Date = fileCellDate.toString().split(" ")[0];
                    logger.info(" loggerId: " + loggerId + " fileCellDate: " + fileCellDate + " currentDate: " + currentDate + " cell date: " + fileCellDate);
                    //  Add the values to the list
                    resultList.add("loggerId: " + loggerId);
                    resultList.add("fileCellDate: " + fileCellDate);
                    resultList.add("currentDate: " + currentDate);
                    logger.info(String.join(" ", resultList));
//                    Arrays.asList(1,2,3,4).forEach(System.out::println);
                }
            }
        } catch (IOException e) {
            logger.error("Error reading the CSV file: " + e.getMessage());
            throw e;
        }
        for (String dataItr : resultList
        ) {
            logger.info("dataitr: ", dataItr);

        }
        return resultList;
    }
    // end

    // Give file creation Date
    public static String getFileCreatedDate(Path file) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
        FileTime creationTime = attrs.creationTime();
        LocalDate localDate = creationTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String formattedDate = localDate.format(formatter);
        return formattedDate;
    }

    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }


    // Not used yet
    public static String timestampToString(Timestamp timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");//yyyy-MM-dd HH:mm:ss
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        return localDateTime.format(formatter);
    }

    private String toDate(long timestamp) {
        LocalDate date = Instant.ofEpochMilli(timestamp * 1000).atZone(ZoneId.systemDefault()).toLocalDate();
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    // this method will get date base on regular expression and matcher
    public static LocalDate extractDate(String fileName) {
        LocalDate parsedDate = null;
        DateTimeFormatter formatter = null;
        // Extract the date part (ddMMyy)
        String day = fileName.substring(0, 2); // "02"
        String month = fileName.substring(2, 4); // "12"
        String year = "20" + fileName.substring(4, 6); // "20" + "25" -> "2025"
        String formattedDate = day + "/" + month + "/" + year;
        System.out.println("Formatted date: " + formattedDate);

        try {
            String regex = "_([0-9]{7})_";
            // Compile the regular expression
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(fileName);
            logger.info("this is final date: " + matcher);   // dd/mm/yyy date format
            // Check if the pattern matches and extract the date
            if (matcher.find()) {
                String date = matcher.group(1);  // Group 1 contains the date part
                logger.info("Extracted date: " + date);
            } else {
                logger.warn("Date not found.");
            }
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            parsedDate = LocalDate.parse(formattedDate, formatter);
            System.out.println("Parsed date: " + parsedDate); // LocalDate format (yyyy-MM-dd)
        } catch (DateTimeParseException e) {
            logger.error("Invalid date format.");
        }
        return parsedDate;
    }


    // these Methods to extract date from filename
    public static Optional<LocalDate> extractDateFromFileName(String fileName) {
        logger.info("util extractDateFromFileName ============== " + fileName);
        String potentialDate = extractDateFromFileName1(fileName);
        // return parseDateBasedOnLength(potentialDate);
        return
                //parseAndCompareDateWithCurrentYear
                parseDateAndCompareWithCurrentYear(potentialDate);
    }

    // Method to extract the date substring from the filename
    private static String extractDateFromFileName1(String fileName) {
        String[] parts = fileName.split("_");
        if (parts.length > 4) {
            logger.info("parts-4 " + parts[4]);
            return parts[4]; // "241203" from filename like "APGW102_5RUC3098_241203_1555-041"
        }
        return "";
    }

    // Method to parse the date based on its length
    private static Optional<LocalDate> parseDateBasedOnLength(String potentialDate) {

        if (potentialDate.length() == 6) {
            logger.info("inside length 6");
            // Try both ddmmyy and yymmdd formats
            Optional<LocalDate> date = tryParseDate(potentialDate, "ddMMyy");
            if (date.isPresent()) {
                logger.info("true");
                return date; // ddMMyy format
            } else {
                logger.warn("false");
                return tryParseDate(potentialDate, "yyMMdd"); // yymmdd format
            }
        }
//        else if (potentialDate.length() == 8) {
//            logger.info("inside length 8");
//            logger.info("true");
//            Optional<LocalDate> date = tryParseDate(potentialDate, "yyyyMMdd");
//            if (date.isPresent()) {
//                return date; // yyyyMMdd format
//            } else {
//                logger.warn("false");
//                return tryParseDate(potentialDate, "ddMMyyyy");
//            }
//        }
        else if (potentialDate.length() == 8) {
            logger.info("potentialDate:" + potentialDate);
            logger.info("Inside length 8");

            // Try both yyyyMMdd and ddMMyyyy formats
            Optional<LocalDate> date = tryParseDate(potentialDate, "yyyyMMdd");
            if (date.isPresent()) {
                logger.info("Parsed successfully in yyyyMMdd format");
                return date; // yyyyMMdd format
            } else {
                logger.warn("Parsing failed for yyyyMMdd, trying ddMMyyyy format");
                return tryParseDate(potentialDate, "ddMMyyyy"); // ddMMyyyy format
            }
        }

        return Optional.empty(); // Unable to parse if length is neither 6 nor 8
    }


    private static Optional<LocalDate> tryParseDate(String dateStr, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            return Optional.of(LocalDate.parse(dateStr, formatter));
        } catch (DateTimeParseException e) {
            return Optional.empty(); // Return empty if parsing fails
        }
    }

    private static Optional<LocalDate> parseDateAndCompareWithCurrentYear(String potentialDate) {
        if (potentialDate.length() == 6 || potentialDate.length() == 8) {
            logger.info("Processing date of length " + potentialDate.length());
            logger.info("Potential date: " + potentialDate);
            // Try parsing the date formats based on the length of the input
            Optional<LocalDate> date1 = tryParseDate(potentialDate, potentialDate.length() == 6 ? "ddMMyy" : "ddMMyyyy");
            Optional<LocalDate> date2 = tryParseDate(potentialDate, potentialDate.length() == 8 ? "yyyyMMdd" : "yyMMdd");
            LocalDate currentDate = LocalDate.now();
            int currentYear = currentDate.getYear();
            // Compare parsed dates to the current year
            if (date1.isPresent() && date1.get().getYear() == currentYear) {
                logger.info("Date1 matches current year: " + currentYear);
                return date1;
            }
            if (date2.isPresent() && date2.get().getYear() == currentYear) {
                logger.info("Date2 matches current year: " + currentYear);
                return date2;
            }
            // If neither matches the current year, choose the closest year
            return selectClosestDate(date1, date2, currentYear);
        }
        // Return empty if date can't be parsed
        logger.warn("Unable to parse the date, returning empty.");
        return Optional.empty();
    }


    private static Optional<LocalDate> selectClosestDate(Optional<LocalDate> date1, Optional<LocalDate> date2, int currentYear) {
        // If both dates are empty, return empty
        if (!date1.isPresent() && !date2.isPresent()) {
            return Optional.empty();
        }
        // If only one date is present, return it
        if (date1.isPresent() && !date2.isPresent()) {
            return date1;
        }
        if (date2.isPresent() && !date1.isPresent()) {
            return date2;
        }
        // If both dates are present, compare their years and return the one closest to the current year
        int year1 = date1.get().getYear();
        int year2 = date2.get().getYear();
        if (Math.abs(year1 - currentYear) <= Math.abs(year2 - currentYear)) {
            return date1;
        } else {
            return date2;
        }
    }

    ///////////////////// end



    public static String extractLoggerAndTime(String fileName) {

        String loggerId = "";
        String date = "";
        String time = "";

        if (fileName.endsWith(".csv")) { fileName = fileName.substring(0, fileName.length() - 4);}
        String[] parts = fileName.split("_");
        if (parts.length >= 4) {
            loggerId = parts[1];
            date = parts[2];
            time = parts[3];
            logger.info("loggerId: " + loggerId);
            logger.info("Date: " + date);
            logger.info("Time: " + time);
        } else {
            logger.info("Filename format is incorrect.");
        }
        return loggerId + "+" + date + "+" + time;
    }

    // this method is currently not used anywhere
    public static LocalDateTime extractDateTimeFromFilename(String fileName) {

        String loggerId = "";
        String date = "";
        String time = "";
        logger.info("util extract Date Time From File name================================================ " + fileName);

        try {
            if (fileName.endsWith(".csv")) {fileName = fileName.substring(0, fileName.length() - 4);}
            String[] parts = fileName.split("_");
            if (parts.length >= 4) {
                loggerId = parts[1];
                date = parts[2];
                time = parts[3];
                System.out.println("util Logger: " + logger);
                System.out.println("util Date: " + date);
                System.out.println("util Time: " + time);
            } else {
                System.out.println("util  Filename format is incorrect.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error @ loadProcessedFiles{} loading processed files: " + e.getMessage(), e);
            return null;
        }
        return null; // Return null if there was an error parsing the date/time
    }

    // These method related to text file data read & write
    public static Set<String> loadProcessedFiles(String processedFilesPath) {
        try {
            File file = new File(processedFilesPath);
            if (!file.exists()) {
                logger.info("This file name exist in text file");
                return new HashSet<>();
            }
            return new HashSet<>(Files.readAllLines(Paths.get(processedFilesPath)));
        } catch (IOException e) {
            logger.error("Error @ loadProcessedFiles{} loading processed files: " + e.getMessage(), e);
            return new HashSet<>();
        }
    }

    // This method will save file name in a txt file
    public static void saveProcessedFiles(Set<String> processedFiles, String processedFilesPath) {

        try {
            Files.write(Paths.get(processedFilesPath), processedFiles);
        } catch (IOException e) {
            logger.error("Error @ saveProcessedFiles{} saving processed files: " + e.getMessage(), e);
        }
    }

    // This method will minus days from a file name datetime, applicable with file Name till now handle this APGW3_5RUC3166_241218_1034.csv and  return2024-12-11T10:34
    public static String minusDay(String fileName) {
        logger.info("minusDay method fileName: "+fileName);
        LocalDateTime dateAndTime = null;
        try {
            if (fileName.endsWith(".csv")) { fileName = fileName.substring(0, fileName.length() - 4); }
            String[] parts = fileName.split("_");
            String datePart = parts[2];
            String timePart = parts[3];
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyMMdd HHmm");
            String dateTimeString = datePart + " " + timePart;
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, inputFormatter);
            dateAndTime = dateTime.minusDays(ApiConstants.DAY_RESTRICTION);
            logger.info("minusDay method return: "+dateAndTime);
        } catch (Exception e) {
            logger.error("Error @ minusDay{} "+e);
        }
        return String.valueOf(dateAndTime);
    }


    // Getting this datetime format from current yyyy-MM-dd'T'HH:mm
    public static String gettingCurrentDateTime(){
        String formattedDateTime = null;
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            formattedDateTime = currentDateTime.format(formatter);
        } catch (Exception e) {
            logger.error("Error @ gettingCurrentDateTime{} "+e);
        }
        return formattedDateTime;
    }

    // This method will Parse strings into LocalDateTime, compare with each if it is day restriction older then ,than it will proceed
    public static boolean  fileDateComparison( String restrictedDateTime) {

        String currentDateTime = DecoderUtils.gettingCurrentDateTime();
        logger.info("Service fileDateComparison: "+currentDateTime);

        logger.info("Enter in fileDateComparison method " ," minus date: "+restrictedDateTime+ "currentDateTime: "+currentDateTime);
        boolean isValid = false;

        try {
            LocalDateTime fileDT = LocalDateTime.parse(restrictedDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME); // get this date in return 2024-12-11T10:34
            LocalDateTime currentDT = LocalDateTime.parse(currentDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            logger.info("FileDT: "+fileDT+ " currentDT: "+currentDT);
            // Check if currentDT is greater than or equal to actualFileDT
            if (currentDT.isEqual(fileDT)) {
                isValid = true;
                logger.info("Both dates are the same.");
            }
           else if (currentDT.isAfter(fileDT)) {
                logger.info("currentDT > fileDT");
                isValid = true;
           } else {
                isValid = false;
                logger.info("false");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }

/////////////////

    public long getFileCount() throws IOException {
        long count =  Files.list(Paths.get(ApiConstants.GPRS_INSAT_AP_GW))
                //  .filter(Files::isRegularFile) // Only count regular files
                .filter(path -> path.toString().endsWith(".csv")) // Filter for .csv files
                .count();
        if (count == 0) {
            logger.warn("No CSV files found in the directory: {}", ApiConstants.GPRS_INSAT_AP_GW);
        }
        return count;
    }
    // Use streams to filter out non-letter characters
    public static String getAgencyName(String fileName) {
        return fileName.chars()
                .filter(Character::isLetter)
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());

    }
    //    public static void blankFileValidation(int receivedFileCount,
//                                           int previousReceivedFileCount,
//                                           String receivedFileDate,
//                                           String previousReceivedFileDate,
//                                           String sensorHubCode,
//                                           int agencyId) {
//
//        // Create a list to hold the values to check
//        List<Object> valuesToCheck = Arrays.asList(
//                receivedFileCount,
//                previousReceivedFileCount,
//                receivedFileDate,
//                previousReceivedFileDate,
//                sensorHubCode,
//                agencyId
//        );
//
//        if (valuesToCheck.stream().allMatch(APGWDecoderUtils::isDefaultValue)) {
//            System.out.println("Inserting data: receivedFileCount = 0, previousReceivedFileCount = 0, receivedFileDate = null, previousReceivedFileDate = null, sensorHubCode = null, agencyId = 0");
//        } else {
//            System.out.println("Conditions don't match the expected values.");
//        }
//    }

//    public static List<Object> blankFileValidation(int receivedFileCount,int previousReceivedFileCount, String receivedFileDate,String previousReceivedFileDate, String sensorHubCode) {
//        List<Object> valuesToCheck = Arrays.asList(receivedFileCount, previousReceivedFileCount, receivedFileDate, previousReceivedFileDate, sensorHubCode);
//        return valuesToCheck;
//    }

    // blank file data calidation,method to check if a value is default (0 for integers, null for strings)
//    public static boolean isDefaultValue(Object value) {
//        if (value instanceof Integer) {
//            return (Integer) value == 0;
//        } else if (value instanceof String) {
//            return value == null || ((String) value).isEmpty();
//        }
//        return false;
//    }

//    public static String checkCSVFile() {
//        String data= null;
//        File file = new File(ApiConstants.GPRS_INSAT_AP_GW);
//
//        // Check if the file exists
//        if (!file.exists()) {
//            System.out.println("The file does not exist.");
//            return null;
//        }
//
//        // Check if the file is empty
//        if (file.length() == 0) {
//            System.out.println("The file is empty.");
//            return null;
//        }
//
//        try {
//            // Using Files.lines to read the file as a Stream
//            Optional<String> header = Files.lines(Paths.get(ApiConstants.GPRS_INSAT_AP_GW)).findFirst();  // Read the first line as the header
//
//            // Check if the header is present
//            if (header.get().trim().isEmpty()) {
//                System.out.println("The file has no header.");
//                return null;
//            }
//
//            // Check for data rows after the header
//            boolean dataExists = Files.lines(Paths.get(ApiConstants.GPRS_INSAT_AP_GW))
//                    .skip(1)  // Skip the header line
//                    .anyMatch(line -> !line.trim().isEmpty());  // Check if there's any non-empty data row
//
//            if (!dataExists) {
//                data = "no data";
//                System.out.println("The file has a header but no data.");
//            } else {
//                System.out.println("The file has a header and data.");
//            }
//        } catch (IOException e) {
//            System.out.println("An error occurred while reading the file: " + e.getMessage());
//        }
//        return data;
//    }



}
