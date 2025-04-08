package bodies_caravan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bodies_caravan.model.SearchHistory;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

}
