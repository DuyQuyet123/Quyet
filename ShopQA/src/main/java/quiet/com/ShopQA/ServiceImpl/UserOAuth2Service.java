package quiet.com.ShopQA.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quiet.com.ShopQA.Entity.UserEntity;
import quiet.com.ShopQA.Repostory.UserRepository;
import quiet.com.ShopQA.enums.ProviderType;

@Service
public class UserOAuth2Service {
    @Autowired
    private UserRepository repo;

    public void processOAuthPostLoginFaceBook(String email, String username) {//làm tương tự vơis
        UserEntity existUser = repo.searchUser(email, username);

        if (existUser == null) {
            UserEntity newUser = new UserEntity();
            newUser.setUsername(username);
            newUser.setProviderType(ProviderType.FACEBOOK);
            newUser.setRole("ROLE_MEMBER");
            newUser.setEnabled(true);
            newUser.setEmail(email);
            repo.save(newUser);
        }

    }

    public void processOAuthPostLoginGoogle(String email, String username) {//làm tương tự vơis
        UserEntity existUser = repo.searchUser(email, username);

        if (existUser == null) {
            UserEntity newUser = new UserEntity();
            newUser.setUsername(username);
            newUser.setProviderType(ProviderType.GOOGLE);
            newUser.setRole("ROLE_MEMBER");
            newUser.setEnabled(true);
            newUser.setEmail(email);
            repo.save(newUser);
        }

    }
}
