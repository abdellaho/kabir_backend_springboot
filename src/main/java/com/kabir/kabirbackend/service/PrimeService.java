package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.dto.PrimeDTO;
import com.kabir.kabirbackend.entities.Prime;
import com.kabir.kabirbackend.mapper.PrimeMapper;
import com.kabir.kabirbackend.repository.PrimeRepository;
import com.kabir.kabirbackend.service.interfaces.IPrimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrimeService implements IPrimeService {

    private final Logger logger = LoggerFactory.getLogger(PrimeService.class);
    private final PrimeRepository primeRepository;
    private final PrimeMapper primeMapper;

    public PrimeService(PrimeRepository primeRepository, PrimeMapper primeMapper) {
        this.primeRepository = primeRepository;
        this.primeMapper = primeMapper;
    }

    @Override
    public PrimeDTO save(PrimeDTO primeDTO) {
        logger.info("Saving Prime: {}", primeDTO);
        try {
            Prime prime = primeMapper.toPrime(primeDTO);
            prime = primeRepository.save(prime);
            return primeMapper.toPrimeDTO(prime);
        } catch (Exception e) {
            logger.error("Error saving Prime", e);
            throw new RuntimeException("Error saving Prime", e);
        }
    }

    @Override
    public List<PrimeDTO> findAll() {
        logger.info("Finding all Primes");
        try {
            List<Prime> primes = primeRepository.findAll();
            return primes.stream().map(primeMapper::toPrimeDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all Primes", e);
            throw new RuntimeException("Error finding all Primes", e);
        }
    }

    @Override
    public PrimeDTO findById(Long id) {
        logger.info("Finding Prime by id: {}", id);
        try {
            Prime prime = primeRepository.findById(id).orElseThrow(() -> new RuntimeException("Prime not found"));
            return primeMapper.toPrimeDTO(prime);
        } catch (Exception e) {
            logger.error("Error finding Prime by id", e);
            throw new RuntimeException("Error finding Prime by id", e);
        }
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting Prime: {}", id);
        try {
            primeRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting Prime", e);
            throw new RuntimeException("Error deleting Prime", e);
        }
    }

    @Override
    public List<PrimeDTO> search(PrimeDTO primeDTO) {
        return List.of();
    }
}
