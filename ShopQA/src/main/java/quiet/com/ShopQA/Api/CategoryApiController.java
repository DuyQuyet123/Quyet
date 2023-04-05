package quiet.com.ShopQA.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import quiet.com.ShopQA.DTO.CategoryDTO;
import quiet.com.ShopQA.DTO.DataGetInfo;
import quiet.com.ShopQA.service.CategoryService;
import quiet.com.ShopQA.service.impl.factory.GetDataInfoFactory;
import quiet.com.ShopQA.service.impl.factory.ResponseFactory;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorys")
@Api(tags = "Category V1", description = "Cấu hình Category")
public class CategoryApiController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ResponseFactory factory;

    @Autowired
    private GetDataInfoFactory dataInfoFactory;

    @ApiOperation(value = "Create new Category")
    @PostMapping
    public CategoryDTO categoryAdd(@RequestBody CategoryDTO categoryDTO) {
        categoryService.insert(categoryDTO);
        return categoryDTO;
    }

    @ApiOperation(value = "Update Category")
    @PutMapping
    public void categoryUpdate(@RequestBody CategoryDTO categoryDTO) {
        categoryService.update(categoryDTO);

    }
    @ApiOperation(value = "Delete Category")
    @DeleteMapping("{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        categoryService.delete(id);
    }

    @ApiOperation(value = "Get Info Category")
    @GetMapping(value = "{id}")
    public DataGetInfo<CategoryDTO> get(@PathVariable(name = "id") Long id) {

        return dataInfoFactory.mapDataResponse(categoryService.get(id));
    }

    @ApiOperation(value = "Get All Category")
    @GetMapping
    public List<CategoryDTO> searchCategory() {
        List<CategoryDTO> categoryDTOs = categoryService.search();
        return categoryDTOs;
    }

}
