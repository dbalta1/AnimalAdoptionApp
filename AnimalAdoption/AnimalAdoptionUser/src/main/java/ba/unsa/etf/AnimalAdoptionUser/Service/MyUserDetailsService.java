package ba.unsa.etf.AnimalAdoptionUser.Service;
import ba.unsa.etf.AnimalAdoptionUser.Entity.Korisnik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import ba.unsa.etf.AnimalAdoptionUser.Repository.KorisnikRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Korisnik korisnik = korisnikRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Korisnik nije pronađen"));

        return User.withUsername(korisnik.getUsername())
                .password(korisnik.getPassword())
                .roles(korisnik.getUloga().name())
                .build();
    }
}
