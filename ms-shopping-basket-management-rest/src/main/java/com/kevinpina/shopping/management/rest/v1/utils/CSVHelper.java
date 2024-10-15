package com.kevinpina.shopping.management.rest.v1.utils;

import com.kevinpina.shopping.management.domain.exception.FailParsingCSVFileException;
import com.kevinpina.shopping.management.domain.utils.Constants;
import com.kevinpina.shopping.management.rest.v1.client.model.ItemDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CSVHelper {

    private static final String HEDAER_ID = "id";
    public static final String HEADER_NAME = "name";
    public static final String HEADER_VALUE = "value";
    public static final String HEADER_TAX = "tax";
    public static final String FORMAT_DATE = "yyMMddHHmmss";
    public static final String NOK_PREFIX_FILE_NAME = "nok_";

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
                item.setId(Long.parseLong(csvRecord.get(HEDAER_ID)));
                item.setName(csvRecord.get(HEADER_NAME));
                item.setValue(new BigDecimal(csvRecord.get(HEADER_VALUE)));
                item.setTax(isTypeProduct ? ItemDTO.TaxEnum.fromValue(Float.parseFloat(csvRecord.get(HEADER_TAX))) : ItemDTO.TaxEnum.GENERAL);
                items.add(item);
            }

            return items;
        } catch (IOException e) {
            throw new FailParsingCSVFileException("fail to parse CSV file: " + e.getMessage());
        }
    }

    private static boolean isItemTax(CSVRecord csvRecord) {
        try {
            return csvRecord.get(HEADER_TAX) != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static String saveFile(MultipartFile fileCsv, String pathCsvLocal) {
        String destination = pathCsvLocal  + new SimpleDateFormat(FORMAT_DATE).format(new Date()) + "_" + fileCsv.getOriginalFilename();
        File file = new File(destination);
        try {
            fileCsv.transferTo(file);
            return destination;
        } catch (IOException e) {
            return null;
        }
    }

    public static boolean renameFile(String destination, String pathCsvLocal) {
        File originalFile = new File(destination);
        File renameFile = new File(pathCsvLocal + NOK_PREFIX_FILE_NAME + originalFile.getName());
        return originalFile.renameTo(renameFile);
    }

}
