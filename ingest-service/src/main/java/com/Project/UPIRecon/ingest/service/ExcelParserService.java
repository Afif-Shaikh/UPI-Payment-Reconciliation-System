package com.Project.UPIRecon.ingest.service;

import com.Project.UPIRecon.ingest.model.RawTransaction;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelParserService {
	
	private String getCellString(Row row, int index) {
	    Cell cell = row.getCell(index);
	    if (cell == null) return null;
	    cell.setCellType(CellType.STRING);
	    return cell.getStringCellValue().trim();
	}


    public List<RawTransaction> parseExcel(MultipartFile file) throws Exception {
        List<RawTransaction> transactions = new ArrayList<>();

        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // skip header row

                RawTransaction txn = new RawTransaction();
                
                txn.setTransactionId(getCellString(row, 0));
                txn.setSenderUpi(getCellString(row, 1));
                txn.setReceiverUpi(getCellString(row, 2));
                txn.setAmount(BigDecimal.valueOf(row.getCell(3).getNumericCellValue()));

                Cell timestampCell = row.getCell(4);
                if (timestampCell != null) {
                    if (timestampCell.getCellType() == CellType.NUMERIC || timestampCell.getCellType() == CellType.FORMULA) {
                        if (DateUtil.isCellDateFormatted(timestampCell)) {
                            txn.setTimeStamp(timestampCell.getLocalDateTimeCellValue());
                        } else {
                            // fallback or throw
                            throw new IllegalArgumentException("Expected date formatted cell at timestamp column");
                        }
                    } else if (timestampCell.getCellType() == CellType.STRING) {
                        txn.setTimeStamp(LocalDateTime.parse(timestampCell.getStringCellValue().trim()));
                    } else {
                        // Handle other cell types or null
                        txn.setTimeStamp(null); // or throw
                    }
                }

                txn.setStatus(row.getCell(5).getStringCellValue());
                txn.setSource(row.getCell(6).getStringCellValue());
                
                // Optional: rawData
                Cell remarkCell = row.getCell(7);
                if (remarkCell != null) {
                    txn.setRemarks(remarkCell.toString());
                }

                transactions.add(txn);
            }
        }

        return transactions;
    }
}
