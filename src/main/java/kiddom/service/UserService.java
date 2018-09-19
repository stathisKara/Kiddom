package kiddom.service;

import kiddom.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service("userService")
public interface UserService {
	public UserEntity findByUsername(String username);
    public void saveUser(UserEntity user, ParentEntity parent,String photopath);
	public void saveUserProvider(UserEntity user, ProviderEntity provider);
	public UserEntity findByUsernamePassword(String username, String Password);
	public List<UserEntity> findUsers();
	public List<ParentEntity> getParents();
    public List<ProviderEntity> getProviders();
	public  ParentEntity findParent(ParentPK parentPk);
	public ProviderEntity findProvider(ProviderPK providerPk);
	public void updateUserParent(ParentEntity parenton,ParentEntity parent,UserEntity useron,UserEntity user);
	public void updateUserPoints(ParentEntity parenton,ParentEntity parent,UserEntity useron,UserEntity user);
	public void updateUserProvider(ProviderEntity provideron,ProviderEntity provider,UserEntity useron,UserEntity user);
	public void approveProvider(ProviderEntity providerEntity);
	public void uploadPhoto(ParentEntity parenton,ParentEntity parent,UserEntity useron,UserEntity user,String photopath);
}
