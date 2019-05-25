package app.repository;

import app.entities.ETF;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EtfRepository extends JpaRepository<ETF, Long> {
    List<ETF> findByDescriptionContainsIgnoreCaseOrExchangeContainsIgnoreCase(String keyword, String k2);
    List<ETF> findAllByOrderByTotalExpenseRatioAsc();
}