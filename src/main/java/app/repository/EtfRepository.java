package app.repository;

import app.entities.ETF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EtfRepository extends JpaRepository<ETF, Long> {

    List<ETF> findByExchange(String exchange);
}