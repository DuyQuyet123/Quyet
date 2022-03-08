package quiet.com.ShopQA.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import quiet.com.ShopQA.DTO.TrademarkDTO;
import quiet.com.ShopQA.Service.TrademarkService;
@RestController
@RequestMapping("/api")
public class TrademarkApiControler {
	@Autowired
	private TrademarkService trademarkService;
	
	@PostMapping("/trademark/add")
	public TrademarkDTO trademarkAddPostManager(@RequestBody TrademarkDTO trademarkDTO) {
		trademarkService.insert(trademarkDTO);
		return trademarkDTO;
	}
	
	@PutMapping("/trademark/update")
	public void updateTrademarkPost(@RequestBody TrademarkDTO trademarkDTO) {
		trademarkService.update(trademarkDTO);
		
	}
	@DeleteMapping("/trademark/update")
	public void deleteTrademarkPost(@RequestParam(name = "id")Long id) {
		trademarkService.delete(id);
		
	}
	@GetMapping("/trademark/search")
	public List<TrademarkDTO> searchTrademark(){
		List<TrademarkDTO> trademarkDTOs = trademarkService.search();
		return trademarkDTOs;
	}
	
	@GetMapping(value = "/trademark/{id}")
	public TrademarkDTO getId(@PathVariable(name = "id") Long id) {
		return trademarkService.get(id);
	}
}
