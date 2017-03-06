package com.videorentalservice.loaders;

import com.videorentalservice.models.*;
import com.videorentalservice.repositories.DiscRepository;
import com.videorentalservice.services.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Rave on 18.02.2017.
 */

@Component
public class JPALoader implements ApplicationListener<ContextRefreshedEvent> {
    private DiscRepository discRepository;
    private UserService userService;
    private RoleService roleService;
    private GenreService genreService;
    private DiscService discService;
    private BookingService bookingService;

    private Logger log = Logger.getLogger(JPALoader.class);

    @Autowired
    public void setDiscRepository(DiscRepository discRepository) {
        this.discRepository = discRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    @Autowired
    public void setDiscService(DiscService discService) {
        this.discService = discService;
    }

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    private void loadGenres() {
        Genre animatedGenre = new Genre();
        animatedGenre.setGenre("Animovaný");
        genreService.saveOrUpdate(animatedGenre);
        log.info("Saved genre" + animatedGenre.getGenre());

        Genre actionGenre = new Genre();
        actionGenre.setGenre("Akčný");
        genreService.saveOrUpdate(actionGenre);
        log.info("Saved genre" + actionGenre.getGenre());

        Genre comedyGenre = new Genre();
        comedyGenre.setGenre("Komédia");
        genreService.saveOrUpdate(comedyGenre);
        log.info("Saved genre" + comedyGenre.getGenre());

        Genre familyGenre = new Genre();
        familyGenre.setGenre("Rodinný");
        genreService.saveOrUpdate(familyGenre);
        log.info("Saved genre" + familyGenre.getGenre());
    }

    private void loadDiscs() {
        Disc lionKingDisc = new Disc();
        lionKingDisc.setTitle("Leví kráľ");
        lionKingDisc.setOriginalTitle("The Lion King");
        lionKingDisc.setDescription("Rozľahlou africkou savanou sa ťahá nekonečný sprievod. Slony, žirafy, zebry a ďalšie zvieratá prichádzajú k Levej skale, aby oslávili narodenie Simbu, syna kráľa Mufasu. Je tu však niekto, kto sa spolu s ostatnými neraduje. Mufasov brat Scar sa nechce zmieriť s tým, že nie je následníkom trónu, a premýšľa, ako by sa Simbu zbavil.");
        lionKingDisc.setType("DVD");
        lionKingDisc.setImageUrl("https://s-media-cache-ak0.pinimg.com/736x/cb/32/50/cb32509c8101ad041572f2b88dac5636.jpg");
        lionKingDisc.setYear(1994);
        lionKingDisc.setPrice(new BigDecimal("0.50"));
        discRepository.save(lionKingDisc);

        log.info("Saved disc - id: " + lionKingDisc.getId() + " - name: " + lionKingDisc.getTitle());

        Disc johnWickDisc = new Disc();
        johnWickDisc.setTitle("John Wick");
        johnWickDisc.setOriginalTitle("John Wick");
        johnWickDisc.setDescription("Vlúpať se mu do domu, ukradnúť auto a zabiť milovaného psa, to sa skrátka nemalo stať. Strední zlodejíčkovia to nevedeli, ale ich bossovia v podsvetí áno. A musia konať. A tak,. keď sa John Wick vydáva na svoju drsnú vendetu, stáva sa zároveň terčom svojich bývalých kolegov - nájomných zabijakov.");
        johnWickDisc.setType("Blu-Ray");
        johnWickDisc.setImageUrl("http://i.ebayimg.com/00/s/MTA1MVg3NTA=/z/3koAAOSw4GVYQPeq/$_12.JPG?set_id=880000500F");
        johnWickDisc.setYear(2014);
        johnWickDisc.setPrice(new BigDecimal("0.70"));
        discRepository.save(johnWickDisc);

        log.info("Saved Disc - id: " + johnWickDisc.getId() + " - name: " + johnWickDisc.getTitle());
    }

    private void assignGenres() {
        List<Genre> genres = (List<Genre>) genreService.listAll();
        List<Disc> discs = (List<Disc>) discService.listAll();

        genres.forEach(genre -> {
            switch (genre.getGenre()) {
                case "Akčný":
                    discs.forEach(disc -> {
                        if (disc.getOriginalTitle().equals("John Wick")) {
                            disc.addGenre(genre);
                            discService.saveOrUpdate(disc);
                            disc.removeGenre(genre);
                        }
                    });
                    break;
                default:
                    discs.forEach(disc -> {
                        if (disc.getOriginalTitle().equals("The Lion King")) {
                            disc.addGenre(genre);
                            discService.saveOrUpdate(disc);
                            disc.removeGenre(genre);
                        }
                    });
                    break;
            }
        });
        log.info("Genres assigned.");
    }

    private void loadRoles() {
        Role superAdminRole = new Role();
        superAdminRole.setRoleName("SUPER_ADMIN");
        roleService.saveOrUpdate(superAdminRole);
        log.info("Saved role " + superAdminRole.getRoleName());

        Role adminRole = new Role();
        adminRole.setRoleName("ADMIN");
        roleService.saveOrUpdate(adminRole);
        log.info("Saved role " + adminRole.getRoleName());

        Role userRole = new Role();
        userRole.setRoleName("USER");
        roleService.saveOrUpdate(userRole);
        log.info("Saved role " + userRole.getRoleName());
    }

    private void loadUsers() {
        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword("admin");
        userService.saveOrUpdate(user1);

        User user2 = new User();
        user2.setUsername("PiotrH");
        user2.setPassword("poklop11");
        userService.saveOrUpdate(user2);

        User user3 = new User();
        user3.setUsername("deges22");
        user3.setPassword("password");
        userService.saveOrUpdate(user3);

        User user4 = new User();
        user4.setUsername("user");
        user4.setPassword("user");
        userService.saveOrUpdate(user4);
    }

    private void loadAndAssignBooking() {

        List<Disc> discs = (List<Disc>) discService.listAll();
        List<User> users = (List<User>) userService.listAll();

        Disc jw = discService.getById(2);
        Disc lk = discService.getById(1);

        User user = userService.getById(4);
        User deges = userService.getById(3);

        Booking firstBooking = new Booking();
        firstBooking.setCompanyName("Video-požičovňa Saturn");
        firstBooking.setDays(7);
        firstBooking.addUser(user);
        firstBooking.addDisc(jw);
        firstBooking.setTotalCost(jw.getPrice().multiply(new BigDecimal(7)));
        bookingService.saveOrUpdate(firstBooking);

        jw.setBooking(firstBooking);
        discService.saveOrUpdate(jw);

        user.setBooking(firstBooking);
        userService.saveOrUpdate(user);

        log.info("Saved booking " + firstBooking.getCompanyName() + " " + firstBooking.getDays());


        Booking secondBooking = new Booking();
        secondBooking.setCompanyName("Video-požičovňa Saturn");
        secondBooking.setDays(2);
        secondBooking.addUser(deges);
        secondBooking.addDisc(lk);
        secondBooking.setTotalCost(lk.getPrice().multiply(new BigDecimal(7)));
        bookingService.saveOrUpdate(secondBooking);

        lk.setBooking(secondBooking);
        discService.saveOrUpdate(lk);

        deges.setBooking(secondBooking);
        userService.saveOrUpdate(deges);

        log.info("Saved booking " + secondBooking.getCompanyName() + " " + secondBooking.getDays());

    }

    private void assignRoles() {
        List<Role> roles = (List<Role>) roleService.listAll();
        List<User> users = (List<User>) userService.listAll();

        roles.forEach(role -> {
            switch (role.getRoleName()) {
                case "SUPER_ADMIN":
                    users.forEach(user -> {
                        if (user.getUsername().equals("admin")) {
                            user.addRole(role);
                            userService.saveOrUpdate(user);
                            user.removeRole(role);
                        }
                    });
                    break;
                case "ADMIN":
                    users.forEach(user -> {
                        if (user.getUsername().equals("PiotrH")) {
                            user.addRole(role);
                            userService.saveOrUpdate(user);
                            user.removeRole(role);
                        }
                    });
                    break;
                default:
                    users.forEach(user -> {
                        if (!user.getUsername().equals("PiotrH") &&
                                !user.getUsername().equals("admin")) {
                            user.addRole(role);
                            userService.saveOrUpdate(user);
                            user.removeRole(role);
                        }
                    });
                    break;
            }
        });
        log.info("Roles assigned.");
    }





    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadGenres();
        loadDiscs();
        assignGenres();
        loadRoles();
        loadUsers();
        assignRoles();
        loadAndAssignBooking();
    }
}
