package pl.kargolek.cryptopriceservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kargolek.cryptopriceservice.model.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
}
