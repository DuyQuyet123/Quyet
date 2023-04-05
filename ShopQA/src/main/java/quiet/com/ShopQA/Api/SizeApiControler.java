package quiet.com.ShopQA.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import quiet.com.ShopQA.DTO.SizeDTO;
import quiet.com.ShopQA.service.SizeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sizes")
public class SizeApiControler {
    @Autowired
    private SizeService sizeService;

    @PostMapping
    public SizeDTO addSizePost(@RequestBody SizeDTO sizeDTO) {
        sizeService.insert(sizeDTO);
        return sizeDTO;
    }

    @GetMapping
    public List<SizeDTO> searchSize() {
        List<SizeDTO> sizeDTOs = sizeService.search();
        return sizeDTOs;
    }

    @PutMapping
    public SizeDTO updateSize(@RequestBody SizeDTO sizeDTO) {
        sizeService.update(sizeDTO);
        return sizeDTO;
    }

    @DeleteMapping("{id}")
    public void updateSize(@PathVariable(name = "id") Long id) {
        sizeService.delete(id);
    }
}
