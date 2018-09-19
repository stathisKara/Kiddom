package kiddom.service;

import kiddom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import kiddom.model.*;

import java.security.Provider;
import java.util.*;
//import com.example.model.Role;

//import com.example.repository.RoleRepository;


@Service("userService")
public class UserServiceImpl implements UserService {

    @Qualifier("userRepository")
    @Autowired
	private UserRepository userRepository;

    @Qualifier("parentRepository")
	@Autowired
    private ParentRepository parentRepository;

    @Qualifier("providerRepository")
    @Autowired
    private ProviderRepository providerRepository;

    @Qualifier("cookieRepository")
    @Autowired
    private CookieRepository cookieRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserEntity findByUsername(String username) {
		return userRepository.findByUsername(username);
		//return null;
	}

	@Override
    public  ParentEntity findParent(ParentPK parentPk){
	    ParentEntity parent = parentRepository.findOne(parentPk);
	    return parent;
    }

    @Override
    public ProviderEntity findProvider(ProviderPK providerPk){
        ProviderEntity provider = providerRepository.findOne(providerPk);
        return provider;
    }

	@Override
    public UserEntity findByUsernamePassword(String username,String password){
        UserEntity userExists = findByUsername(username);
        if (userExists != null)
        {
            if (bCryptPasswordEncoder.matches((password),userExists.getPassword()))//
            {
                System.out.println("Same password");
                return userExists;
            }
            else
            {
                System.out.print("Username "+userExists.getUsername()+" pass in base "+userExists.getPassword()+" pass given "+password);
                return null;
            }
        }
        else
        {
            System.out.println("There is no user registered with this uname");
            return null;
        }
    }

	@Override
    public void saveUser(UserEntity user, ParentEntity parent,String photopath) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		System.out.println("Creating user..." + user.getPassword());
		user.setType(1);
		//if (request.getParameter("image") != null) {
		//	parent.setPhoto(request.getParameter("image"));
		//}
        System.out.println("exw parei san area " + parent.getArea());
        userRepository.save(user);
        //CookiesEntity cookie = new CookiesEntity();
        System.out.println("Creating parent user...");
        System.out.println("Trying to add " + parent.getName() + " " + parent.getSurname() + "  " + parent.getEmail());
        //parent.setUsername(user.getUsername());
        parent.setUser(user);
        parent.setPhoto(photopath);
        //cookie.setCategory("none");
        //cookie.setParentByParentId(parent);
        //cookie.setUsername(user.getUsername());

		String postcode=parent.getArea()+","+parent.getTown();
		try
        {
            String pos[] =LatLng.getLatLongPositions(postcode);
            System.out.print(pos[0]+ pos[1]);
            parent.setLatitude(Float.parseFloat(pos[0]));
            parent.setLongitude(Float.parseFloat(pos[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        parentRepository.save(parent);
        //cookieRepository.save(cookie);
        System.out.println("Done.");
    }

    @Override
    public List<UserEntity> findUsers(){
        return userRepository.findAll();
    }

    @Override
    public void saveUserProvider(UserEntity user, ProviderEntity provider) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        System.out.println("Creating user...");
        user.setType(2);
       /*if (request.getParameter("image") != null) {
			parent.setPhoto(request.getParameter("image"));
		}*/
		//user.setProviderByUserId(provider);
        userRepository.save(user);
        System.out.println("Creating provider user...");
        System.out.println("Trying to add " + provider.getName() + " " + provider.getSurname() + " " + provider.getEmail());
        provider.setUser(user);
        providerRepository.save(provider);
        System.out.println("Done.");
    }


    @Override
    public void updateUserParent(ParentEntity parenton,ParentEntity parent,UserEntity useron,UserEntity user){
        if(!parent.getEmail().replaceAll(" ","").equals("")){
            parenton.setEmail(parent.getEmail());
        }
        if(!parent.getName().replaceAll(" ","").equals(""))
        {
            parenton.setName(parent.getName());
        }
        if(!parent.getSurname().replaceAll(" ","").equals(""))
        {
            parenton.setSurname(parent.getSurname());
        }
        if(!parent.getArea().replaceAll(" ","").equals(""))
        {
            parenton.setArea(parent.getArea());
        }
        if(!parent.getTown().replaceAll(" ","").equals(""))
        {
            parenton.setTown(parent.getTown());
        }

        if(!parent.getTelephone().replaceAll(" ","").equals(""))
        {
            parenton.setTelephone(parent.getTelephone());
        }
        if(!user.getPassword().replaceAll(" ","").equals(""))
        {
            useron.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userRepository.save(useron);
        parentRepository.save(parenton);
    }

    public List<ParentEntity> getParents(){
        return parentRepository.findAll();
    }

    public List<ProviderEntity> getProviders(){
        return (List<ProviderEntity>) providerRepository.findAll();
    }

 /****** Update Provider's Profile ******/
    @Override
    public void updateUserProvider(ProviderEntity provideron,ProviderEntity provider,UserEntity useron,UserEntity user){
        if(!provider.getEmail().replaceAll(" ","").equals("")) {
            provideron.setEmail(provider.getEmail());
        }
        if(!provider.getName().replaceAll(" ","").equals(""))
        {
            provideron.setName(provider.getName());
        }
        if(!provider.getSurname().replaceAll(" ","").equals(""))
        {
            provideron.setSurname(provider.getSurname());
        }
        if(!provider.getTown().replaceAll(" ","").equals(""))
        {
            provideron.setTown(provider.getTown());
        }
        if(!provider.getArea().replaceAll(" ","").equals(""))
        {
            provideron.setArea(provider.getArea());
        }
        if(!provider.getTelephone().replaceAll(" ","").equals(""))
        {
            provideron.setTelephone(provider.getTelephone());
        }
        if(!provider.getTr().replaceAll(" ","").equals(""))
        {
            provideron.setTr(provider.getTr());
        }
        if(!user.getPassword().replaceAll(" ","").equals(""))
        {
            useron.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userRepository.save(useron);
        providerRepository.save(provideron);
    }

    /****** Update Parent's Points ******/

    public void updateUserPoints(ParentEntity parenton,ParentEntity parent,UserEntity useron,UserEntity user) {
        int temp=parent.getTotalPoints();
        int newtotal=temp + parenton.getTotalPoints();
        parenton.setTotalPoints(newtotal);
        int newavail=temp + parenton.getAvailPoints();
        System.out.println("Tha valw twra "+newavail+" lefta");
        parenton.setAvailPoints(newavail);
        parentRepository.save(parenton);
    }

    public void uploadPhoto(ParentEntity parenton,ParentEntity parent,UserEntity useron,UserEntity user,String photopath) {
        parenton.setPhoto(photopath);
        parentRepository.save(parenton);
    }

    @Override
    public void approveProvider(ProviderEntity providerEntity)
    {
        providerEntity.setApproved(1);
        providerRepository.save(providerEntity);
    }
}
