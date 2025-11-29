package com.Project.UPIRecon.ingest.service;

import com.Project.UPIRecon.ingest.model.RawTransaction;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.Project.UPIRecon.config.LoggingConfig;
import org.slf4j.Logger;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelParserService {
	private static final Logger log = LoggingConfig.getLogger(ExcelParserService.class);

	private String getCellString(Row row, int index) {
		Cell cell = row.getCell(index);
		if (cell == null)
			return null;
		cell.setCellType(CellType.STRING);
		return cell.getStringCellValue().trim();
	}

	public List<RawTransaction> parseExcel(MultipartFile file) throws Exception {
		log.info("Starting Excel parsing for file: {}", file.getOriginalFilename());
		List<RawTransaction> transactions = new ArrayList<>();

		try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheetAt(0);
			log.info("Sheet '{}' loaded. Total rows found: {}", sheet.getSheetName(), sheet.getLastRowNum());

			for (Row row : sheet) {
				if (row.getRowNum() == 0) {
					log.debug("Skipping header row");
					continue; 
				}
				log.debug("Parsing row {}", row.getRowNum());
				RawTransaction txn = new RawTransaction();

				txn.setTransactionId(getCellString(row, 0));
				txn.setSenderUpi(getCellString(row, 1));
				txn.setReceiverUpi(getCellString(row, 2));
				txn.setAmount(BigDecimal.valueOf(row.getCell(3).getNumericCellValue()));

				Cell timestampCell = row.getCell(4);
				if (timestampCell != null) {
					if (timestampCell.getCellType() == CellType.NUMERIC
							|| timestampCell.getCellType() == CellType.FORMULA) {
						if (DateUtil.isCellDateFormatted(timestampCell)) {
							txn.setTimeStamp(timestampCell.getLocalDateTimeCellValue());
						} else {
							// fallback or throw
							throw new IllegalArgumentException("Expected date formatted cell at timestamp column");
						}
					} else if (timestampCell.getCellType() == CellType.STRING) {
						txn.setTimeStamp(LocalDateTime.parse(timestampCell.getStringCellValue().trim()));
					} else {
						log.warn("Timestamp cell in row {} is invalid. Setting timestamp = null", row.getRowNum());
						txn.setTimeStamp(null);
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
		log.info("Excel parsing completed. Total parsed transactions: {}", transactions.size());
		return transactions;
	}
}
