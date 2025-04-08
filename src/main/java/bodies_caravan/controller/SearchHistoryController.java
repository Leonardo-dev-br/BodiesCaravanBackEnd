package bodies_caravan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import bodies_caravan.model.SearchHistory;
import bodies_caravan.repository.SearchHistoryRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/history")
@Slf4j
public class SearchHistoryController {
    @Autowired
    private SearchHistoryRepository searchHistory;

    @GetMapping
    public List<SearchHistory> index() {
        return searchHistory.findAll();
    }

    @GetMapping("{id}")
    public SearchHistory getUniqueSearchHistory(@PathVariable Long id) {
        log.info("Buscando Histórico " + id);
        return getSearchHistory(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SearchHistory create(@RequestBody @Valid SearchHistory history) {
        log.info("Histórico registrado " + history);
        return searchHistory.save(history);
    }

    @PutMapping("{id}")
    public SearchHistory updateHistory(@PathVariable Long id, @RequestBody SearchHistory history) {
        log.info("Atualizando histórico conta de: " + id + " " + history);

        getSearchHistory(id);
        history.setIdSearchHistory(id);

        return searchHistory.save(history);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("Processando o Deletar do histórico de ID: " + id);
        searchHistory.delete(getSearchHistory(id));
    }

    private SearchHistory getSearchHistory(Long id) {
        return searchHistory
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Histórico de Busca do Usuário  " + id + "  não encontrados"));
    }

}
