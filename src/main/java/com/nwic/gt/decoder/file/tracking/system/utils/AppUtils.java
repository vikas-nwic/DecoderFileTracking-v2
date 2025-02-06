package com.nwic.gt.decoder.file.tracking.system.utils;

import com.nwic.gt.decoder.file.tracking.system.models.DecoderFileTracker;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AppUtils {

    // Method to generate a unique filename with a timestamp and UUID
    public static String generateUniqueFilename(String baseName, String extension) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return baseName + "_" + timestamp + "_" + uuid + "." + extension;
    }

    // Method to create a title style
    public static CellStyle createTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    public static void addLogoToHeader(Workbook workbook, Sheet sheet, String logoPath) throws IOException {
        // Load the logo image as an InputStream from the classpath
        InputStream logoStream = new ClassPathResource(logoPath).getInputStream();

        // Read all bytes from the InputStream
        byte[] logoBytes = IOUtils.toByteArray(logoStream);
        logoStream.close(); // Close the stream after reading

        // Add the image to the workbook
        int pictureIdx = workbook.addPicture(logoBytes, Workbook.PICTURE_TYPE_PNG);

        // Create a drawing patriarch and client anchor
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = workbook.getCreationHelper().createClientAnchor();

        // Define the position and size of the logo in the sheet
        anchor.setCol1(0); // Column where the picture will start
        anchor.setRow1(0); // Row where the picture will start
        anchor.setCol2(2); // Column where the picture will end
        anchor.setRow2(2); // Row where the picture will end

        // Create the picture and resize it
        Picture picture = drawing.createPicture(anchor, pictureIdx);
        picture.resize(1.5); // Resize the image to the desired size (1.5 times the original size)
    }

    // Method to create a header style with borders and background
    public static CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);

        // Add borders
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        return style;
    }



    // Method to create alternating row styles
    public static CellStyle createRowStyle(Workbook workbook, IndexedColors bgColor) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(bgColor.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setWrapText(true);

        // Add borders
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    // Method to create a cell with style
    public static void createStyledCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    public static long convertToLong(Object obj) {
        try {
            if (obj instanceof BigInteger) {
                return ((BigInteger) obj).longValue();
            } else if (obj instanceof Number) {
                return ((Number) obj).longValue();
            } else {
                throw new IllegalArgumentException("Cannot convert the object to a long: " + obj);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return ((Number) obj).longValue();
    }


    public static byte[] readAllBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int nRead;
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }





}
