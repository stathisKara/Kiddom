package kiddom.authentication;

import org.springframework.security.core.Authentication;

/**
 * Created by Arianna on 8/6/2017.
 */
public interface IAuthenticationFacade {
    Authentication getAuthentication();
}
