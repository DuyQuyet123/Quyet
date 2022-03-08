package quiet.com.ShopQA.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import quiet.com.ShopQA.DTO.SizeDTO;
import quiet.com.ShopQA.Service.SizeService;

@RestController
@RequestMapping("/api")
public class SizeApiControler {
	@Autowired
	private SizeService sizeService;
	
	@PostMapping("/size/add")
	public SizeDTO addSizePost(@RequestBody SizeDTO sizeDTO) {
		sizeService.insert(sizeDTO);
		return sizeDTO;
	}
	@GetMapping("/size/search")
	public List<SizeDTO> searchSize(){
		List<SizeDTO> sizeDTOs = sizeService.search();
		return sizeDTOs;
	}
	@PutMapping("/size/update")
	public SizeDTO updateSize(@RequestBody SizeDTO sizeDTO) {
		sizeService.update(sizeDTO);
		return sizeDTO;
	}
	@DeleteMapping(value = "/size/delete")
	public void updateSize(@RequestParam(name = "id") Long id) {
		sizeService.delete(id);
	}
}
