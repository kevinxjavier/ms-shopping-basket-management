package com.kevinpina.shopping.management.rest.v1.controller;

import com.kevinpina.shopping.management.domain.exception.FailParsingCSVFileException;
import com.kevinpina.shopping.management.domain.model.Item;
import com.kevinpina.shopping.management.domain.usecase.item.CsvUseCase;
import com.kevinpina.shopping.management.domain.usecase.item.SaveItemUseCase;
import com.kevinpina.shopping.management.domain.utils.Constants;
import com.kevinpina.shopping.management.rest.v1.client.api.ShoppingApi;
import com.kevinpina.shopping.management.rest.v1.client.model.ItemDTO;
import com.kevinpina.shopping.management.rest.v1.client.model.ItemObjectDTO;
import com.kevinpina.shopping.management.rest.v1.client.model.ProductResumeDTO;
import com.kevinpina.shopping.management.rest.v1.config.ConnectionProperties;
import com.kevinpina.shopping.management.rest.v1.mapper.impl.ConnectionPropertiesMapper;
import com.kevinpina.shopping.management.rest.v1.utils.CSVHelper;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

/**
 * The default implementation for {@link ShoppingApi}.
 */
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopping_management")
public class ShoppingApiController implements ShoppingApi {

	private final SaveItemUseCase saveItemUseCase;
	private final CsvUseCase csvUseCase;
	private final ConnectionProperties connectionProperties;
	private final ConnectionPropertiesMapper connectionPropertiesMapper;

	@Value("${csv.local.path}")
	private String csvLocalPath;

	private static final Logger log = LoggerFactory.getLogger(ShoppingApiController.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<ProductResumeDTO> getItems(String authorization, ItemObjectDTO itemObjectDTO) {
		String userName = getUserName();
		ProductResumeDTO productResume = getProductResume(itemObjectDTO.getItems());

		String tempDestination = CSVHelper.saveFileLocally(csvLocalPath, userName, productResume.getItems());
		saveFileRemotely(tempDestination, userName);

		Item item = Item.builder().uuid(UUID.randomUUID()).userName(userName)
				.items(productResume.getItems().toString()).build();
		saveItemUseCase.saveItem(item);

		return ResponseEntity.status(HttpStatus.OK).body(productResume);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<ProductResumeDTO> loadItems(String authorization, MultipartFile file) {
		if (file != null && !file.isEmpty()) {
			String destinationFile = CSVHelper.saveFileLocally(file, csvLocalPath);
			log.info("----- Uploaded the file successfully: {} to destination {}", file.getOriginalFilename(), destinationFile);
			if (!Strings.isNullOrEmpty(destinationFile) && CSVHelper.hasCSVFormat(file)) {
				try {
					ProductResumeDTO productResume = getProductResume(CSVHelper.csvToItems(destinationFile));
					saveFileRemotely(destinationFile, getUserName());
					return ResponseEntity.status(HttpStatus.OK).body(productResume);
				} catch (Exception e) {
					log.error(e.getMessage());
					if (CSVHelper.renameFile(destinationFile, csvLocalPath)) {
						log.error("----- Renaming file as Not OK ({}) in: {}{}{}", Constants.NOK, Constants.NOK, destinationFile.substring(0, destinationFile.lastIndexOf("/")),
								destinationFile.substring(destinationFile.lastIndexOf("/") + 1));
					} else {
						log.error("----- Could not rename the file: {}!", file.getOriginalFilename());
					}
					throw new FailParsingCSVFileException("Problems reading the csv file: " + e.getMessage());
				}
			}
		}

		log.info("----- Please upload a csv file!");
		throw new FailParsingCSVFileException("Please upload a csv file!");
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

	private void saveFileRemotely(String tempDestination, String userName) {
		if (Boolean.parseBoolean(connectionProperties.getProperties().get(Constants.SAVE_REMOTELY_FILE)) &&
				!csvUseCase.saveFileRemotely(connectionPropertiesMapper.convertToDto(connectionProperties, tempDestination, userName))) {
			log.error(Constants.THE_FILE_WAS_NOT_SAVED_REMOTELY);
		}
	}

	private String getUserName() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes != null ? requestAttributes.getRequest() : null;
		return request != null && request.getAttribute(Constants.USERNAME) != null ?
				request.getAttribute(Constants.USERNAME).toString(): "anonymous";
	}

}
