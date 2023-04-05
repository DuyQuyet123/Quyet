package quiet.com.ShopQA.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import quiet.com.ShopQA.DTO.TrademarkDTO;
import quiet.com.ShopQA.service.TrademarkService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trademarks")
public class TrademarkApiControler {
    @Autowired
    private TrademarkService trademarkService;

    @PostMapping
    public TrademarkDTO trademarkAddPostManager(@RequestBody TrademarkDTO trademarkDTO) {
        trademarkService.insert(trademarkDTO);
        return trademarkDTO;
    }

    @PutMapping
    public void updateTrademarkPost(@RequestBody TrademarkDTO trademarkDTO) {
        trademarkService.update(trademarkDTO);

    }

    @DeleteMapping
    public void deleteTrademarkPost(@PathVariable(name = "id") Long id) {
        trademarkService.delete(id);

    }

    @GetMapping
    public List<TrademarkDTO> searchTrademark() {
        List<TrademarkDTO> trademarkDTOs = trademarkService.search();
        return trademarkDTOs;
    }

    @GetMapping(value = "{id}")
    public TrademarkDTO getId(@PathVariable(name = "id") Long id) {
        return trademarkService.get(id);
    }
}
