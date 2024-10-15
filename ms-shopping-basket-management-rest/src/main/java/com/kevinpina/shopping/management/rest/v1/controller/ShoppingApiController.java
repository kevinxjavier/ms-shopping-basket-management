package com.kevinpina.shopping.management.rest.v1.controller;

import com.kevinpina.shopping.management.domain.utils.Constants;
import com.kevinpina.shopping.management.rest.v1.client.api.ShoppingApi;
import com.kevinpina.shopping.management.rest.v1.client.model.ItemDTO;
import com.kevinpina.shopping.management.rest.v1.client.model.ItemObjectDTO;
import com.kevinpina.shopping.management.rest.v1.client.model.ProductResumeDTO;
import com.kevinpina.shopping.management.rest.v1.utils.CSVHelper;
import joptsimple.internal.Strings;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

/**
 * The default implementation for {@link ShoppingApi}.
 */
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopping_management")
public class ShoppingApiController implements ShoppingApi {

	private static final Logger log = LoggerFactory.getLogger(ShoppingApiController.class);

	@Value("${path.csv.local}")
	private String pathCsvLocal;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<ProductResumeDTO> getItems(String authorization, ItemObjectDTO itemObjectDTO) {
		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity.status(HttpStatus.OK).body(getProductResume(itemObjectDTO.getItems()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<ProductResumeDTO> loadItems(String authorization, List<MultipartFile> file) {
		if (file != null && !file.isEmpty()) {
			String destinationFile = CSVHelper.saveFile(file.get(0), pathCsvLocal);
			log.info("----- Uploaded the file successfully: {} to destination {}", file.get(0).getOriginalFilename(), destinationFile);
			if (!Strings.isNullOrEmpty(destinationFile) && CSVHelper.hasCSVFormat(file.get(0))) {
				try {
					return ResponseEntity.status(HttpStatus.OK).body(getProductResume(CSVHelper.csvToItems(destinationFile)));
				} catch (Exception e) {
					log.error(e.getMessage());
					if (CSVHelper.renameFile(destinationFile, pathCsvLocal)) {
						log.error("----- Renaming file as Not OK (nok_) in: {}/nok_{}", destinationFile.substring(0, destinationFile.lastIndexOf("/")),
								destinationFile.substring(destinationFile.lastIndexOf("/") + 1));
					} else {
						log.error("----- Could not rename the file: {}!", file.get(0).getOriginalFilename());
					}
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
		}

		log.info("----- Please upload a csv file!");
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	private ProductResumeDTO getProductResume(List<ItemDTO> items) {
		final Double totalValueProducts = items.stream().mapToDouble(item -> {
			if (item.getTax() == null) {
				item.setTax(ItemDTO.TaxEnum.GENERAL);
			}
			return item.getValue().add(item.getValue().multiply(BigDecimal.valueOf(item.getTax().getValue() / Constants.NUMBER_DIVISOR_PERCENTAGE))).doubleValue();
		}).sum();
		final DecimalFormat totalValueProductsDecimals = new DecimalFormat("#.00");
		totalValueProductsDecimals.setRoundingMode(RoundingMode.UP);

		ProductResumeDTO productResumeDTO = new ProductResumeDTO();
		productResumeDTO.setItems(items);
		productResumeDTO.setTotalProducts(items.size());
		productResumeDTO.setTotalValueProducts(new BigDecimal(totalValueProductsDecimals.format(totalValueProducts)));
		return productResumeDTO;
	}

}
