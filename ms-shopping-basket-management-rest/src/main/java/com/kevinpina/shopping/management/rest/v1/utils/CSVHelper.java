package com.kevinpina.shopping.management.rest.v1.utils;

import com.kevinpina.shopping.management.domain.exception.FailParsingCSVFileException;
import com.kevinpina.shopping.management.domain.utils.Constants;
import com.kevinpina.shopping.management.rest.v1.client.model.ItemDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CSVHelper {

    private static final Logger log = LoggerFactory.getLogger(CSVHelper.class);

    public static boolean hasCSVFormat(MultipartFile file) {
        return Constants.TYPE.equals(file.getContentType());
    }

    public static List<ItemDTO> csvToItems(String destinationFile) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(destinationFile))));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<ItemDTO> items = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                boolean isTypeProduct = isItemTax(csvRecord);
                ItemDTO item = new ItemDTO();
                item.setId(Long.parseLong(csvRecord.get(Constants.HEDAER_ID)));
                item.setName(csvRecord.get(Constants.HEADER_NAME));
                item.setValue(new BigDecimal(csvRecord.get(Constants.HEADER_VALUE)));
                item.setTax(isTypeProduct ? ItemDTO.TaxEnum.fromValue(Float.parseFloat(csvRecord.get(Constants.HEADER_TAX))) : ItemDTO.TaxEnum.GENERAL);
                items.add(item);
            }

            return items;
        } catch (IOException e) {
            throw new FailParsingCSVFileException("fail to parse CSV file: " + e.getMessage());
        }
    }

    private static boolean isItemTax(CSVRecord csvRecord) {
        try {
            return csvRecord.get(Constants.HEADER_TAX) != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static String saveFileLocally(MultipartFile fileCsv, String csvLocalPath) {
        String destination = csvLocalPath  + new SimpleDateFormat(Constants.FORMAT_DATE).format(new Date()) + "_" + fileCsv.getOriginalFilename();
        File file = new File(destination);
        try {
            fileCsv.transferTo(file);
            return destination;
        } catch (IOException e) {
            return null;
        }
    }

    public static String saveFileLocally(String destination, String userName, List<ItemDTO> itemsDTO) {
        destination += userName + "_" + new SimpleDateFormat(Constants.FORMAT_DATE).format(new Date()) + Constants.CSV;

        String[] header = {Constants.HEDAER_ID, Constants.HEADER_NAME, Constants.HEADER_VALUE};

        String[][] data = new String[itemsDTO.size()][3];
        AtomicInteger atomicInteger = new AtomicInteger(0);
        itemsDTO.forEach(item -> {
            int index = atomicInteger.getAndIncrement();
            data[index][0] = item.getId().toString();
            data[index][1] = item.getName();
            data[index][2] = item.getValue().toString();
        });

        try (CSVPrinter printer = new CSVPrinter(new FileWriter(destination), CSVFormat.DEFAULT.withHeader(header))) {
            for (String[] row : data) {
                printer.printRecord((Object[]) row);
            }
            log.info("CSV file created successfully: {}", destination);
        } catch (IOException e) {
            log.error("Error happened when saving csv file in path: {}, error: {}", destination, e.getMessage());
        }

        return destination;
    }

    public static boolean renameFile(String destination, String csvLocalPath) {
        File originalFile = new File(destination);
        File renameFile = new File(csvLocalPath + Constants.NOK_PREFIX_FILE_NAME + originalFile.getName());
        return originalFile.renameTo(renameFile);
    }

}
