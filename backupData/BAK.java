//    public void readAllDirectoryFiles() throws IOException {
//        logger.info("readAllDirectoryFiles start...");
//        String folderPath = ApiConstants.GPRS_INSAT_AP_GW;
//        Path folder = Paths.get(folderPath);
//
////        if (!Files.isDirectory(folder) ) {throw new IOException("Directory not found"+folderPath);}
//        if (!Files.isDirectory(folder)) {
//            throw new IOException(String.format("Directory not found: %s", folderPath));
//        }
//
//        Set<String> processedFiles = loadProcessedFiles(ApiConstants.AP_GW_PROCESSED_FILES_PATH);
//        LocalDate currentDate = LocalDate.now();
//        LocalDate lastDate = currentDate.minusDays(Long.parseLong(ApiConstants.DAY_RESTRICTION));
//
//        try (var files = Files.walk(folder)) {
//            List<Path> fileList = files.filter(path -> path.toString().endsWith(".csv")).collect(Collectors.toList());
//            boolean recordFound = false;
//            boolean fileFound = false;
//
//            for (Path file : fileList) {
//                try {
//                    String fileName = file.getFileName().toString();
////                    if (processedFiles.contains(fileName)) {
////                        continue;
////                    }
//                    processedFiles.add(fileName);
//                    boolean fileHasRecords = false;
//                   // saveProcessedFiles(processedFiles, ApiConstants.AP_GW_PROCESSED_FILES_PATH);  // temporary comment out for testing purpose
//                    //logger.info("check file extention "+file.toString().contains(".csv"));
//                    // Call readSingleFile() only once
//                    fileHasRecords = readSingleFile(file, currentDate, lastDate);
//
//                    //logger.info("check file extention "+file.toString().contains(".csv"));
//                    if (fileHasRecords) {
//                        recordFound = true;
//                    }
//                    if (file.toString().contains(".csv")) {
//                        fileFound = true;
//                    }
//                    if (!fileFound) {
//                        throw new ResourceNotFoundException("No file found");
//                    } else
//                    if (!recordFound) {
//                        throw new ResourceNotFoundException("No record found in the file");
//                    }
//                } catch (FileNotFoundException e) {
//                    logger.error("File not found: " + e.getMessage());
//                } catch (Exception e) {
//                    logger.error("Error processing file: {}", file.toString(), e);
//                }
//            }
//
//        } catch (IOException e) {
//            logger.error("Error reading directory: {}", folderPath, e);
//            throw e;
//        }
//
//        logger.info("readAllDirectoryFiles() end...");
//    }