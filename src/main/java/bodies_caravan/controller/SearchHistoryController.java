package bodies_caravan.controller;

import bodies_caravan.model.SearchHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@RestController
public class SearchHistoryController {
    private Logger log = LoggerFactory.getLogger(getClass());
    private  List<SearchHistory> searchHistory = new ArrayList<>();

    @GetMapping("/history")
    public List<SearchHistory> index(){
        return searchHistory;
    }

    @GetMapping("/history/{id}")
    public SearchHistory getUniqueSearchHistory(@PathVariable Long id){
        log.info("Buscando Histórico " + id);
        return getSearchHistory(id);
    }

    @PostMapping("/history")
    public ResponseEntity create(@RequestBody SearchHistory history){
        searchHistory.add(history);
        System.out.println("Histórico registrado " + history);
        return ResponseEntity.status(201).body(history);
    }

    @PutMapping("{id}")
    public SearchHistory updateHistory(@PathVariable Long id, @RequestBody SearchHistory history) {
        log.info("Atualizando histórico conta de: " + id + " " + history);

        searchHistory.remove(getSearchHistory(id));
        history.setIdSearchHistory(id);
        searchHistory.add(history);

        return history;
    }

    @DeleteMapping("history/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        log.info("Processando o Deletar do histórico de ID: " + id);
        searchHistory.remove(id);
    }

    private SearchHistory getSearchHistory(Long id) {
        return searchHistory.stream()
                .filter(searchHis -> searchHis.getIdSearchHistory().equals(id))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico de Busca do Usuário  " + id + "  não encontrados")
                );
    }

}
