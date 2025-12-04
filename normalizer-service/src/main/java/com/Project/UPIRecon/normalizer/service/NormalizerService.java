package com.Project.UPIRecon.normalizer.service;

import com.Project.UPIRecon.dto.RawTransactionEvent;
import com.Project.UPIRecon.dto.NormalizedTransactionEvent;
import com.Project.UPIRecon.normalizer.kafka.TransactionProducer;
import com.Project.UPIRecon.normalizer.entity.NormalizedTransaction;
import com.Project.UPIRecon.normalizer.repository.NormalizedTransactionRepository;
import com.Project.UPIRecon.config.LoggingConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NormalizerService {

	private static final Logger log = LoggingConfig.getLogger(NormalizerService.class);

	@Autowired
	private NormalizedTransactionRepository repo;

	@Autowired
	private TransactionProducer producer;

	public void normalize(RawTransactionEvent raw) {

		log.info("Normalizing transaction. TransactionId: {}", raw.getTransactionId());

		String normalizedKey = raw.getSenderUpi() + "|" + raw.getReceiverUpi() + "|" + raw.getAmount() + "|"
				+ raw.getTimestamp();

		log.debug("Normalized key generated. : normalizedKey {}", normalizedKey);

		NormalizedTransactionEvent event = new NormalizedTransactionEvent();
		event.setTransactionId(raw.getTransactionId());
		event.setAmount(raw.getAmount());
		event.setSenderUpi(raw.getSenderUpi());
		event.setReceiverUpi(raw.getReceiverUpi());
		event.setTimestamp(raw.getTimestamp());
		event.setNormalizedKey(normalizedKey);
		event.setSource(raw.getSource());

		try {
			NormalizedTransaction entity = new NormalizedTransaction(event);
			repo.save(entity);
			log.info("Saved normalized transaction to DB. TransactionId: {}", raw.getTransactionId());

			producer.send(event);
			log.info("Published normalized transaction to Kafka. TransactionId: {}", raw.getTransactionId());

		} catch (Exception e) {
			log.error("Error processing normalized transaction. TransactionId: {} | Error: {}",
                    raw.getTransactionId(), e.getMessage(), e);
            throw e; 
		}
	}
}
