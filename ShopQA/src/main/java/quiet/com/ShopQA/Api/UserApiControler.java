package quiet.com.ShopQA.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import quiet.com.ShopQA.DTO.UserDTO;
import quiet.com.ShopQA.Service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserApiControler {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserDTO userAdd(@RequestBody UserDTO userDTO) {
        userService.insert(userDTO);
        return userDTO;
    }

    @PutMapping
    public void userUpdate(@RequestBody UserDTO userDTO) {
        userService.update(userDTO);
    }

    @DeleteMapping(value = "{id}")
    public void delete(@RequestParam(name = "id") Long id) {
        userService.delete(id);
    }

    @GetMapping
    public List<UserDTO> searchCategory() {
        List<UserDTO> userDTOs = userService.search1();
        return userDTOs;
    }
}
