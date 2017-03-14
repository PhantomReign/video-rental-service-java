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
        lionKingDisc.setSubTitle("");
        lionKingDisc.setOriginalTitle("The Lion King");
        lionKingDisc.setOriginalSubTitle("");
        lionKingDisc.setDescription("Rozľahlou africkou savanou sa ťahá nekonečný sprievod. Slony, žirafy, zebry a ďalšie zvieratá prichádzajú k Levej skale, aby oslávili narodenie Simbu, syna kráľa Mufasu. Je tu však niekto, kto sa spolu s ostatnými neraduje. Mufasov brat Scar sa nechce zmieriť s tým, že nie je následníkom trónu, a premýšľa, ako by sa Simbu zbavil.");
        lionKingDisc.setType("DVD");
        lionKingDisc.setImageUrl("https://s-media-cache-ak0.pinimg.com/736x/cb/32/50/cb32509c8101ad041572f2b88dac5636.jpg");
        lionKingDisc.setImageBGUrl("http://eskipaper.com/images/lion-king-wallpaper-3.jpg");
        lionKingDisc.setVideoUrl("//www.youtube.com/embed/jOIu472cCq0");
        lionKingDisc.setYear(1994);
        lionKingDisc.setPrice(new BigDecimal("0.50"));
        discRepository.save(lionKingDisc);

        log.info("Saved disc - id: " + lionKingDisc.getId() + " - name: " + lionKingDisc.getTitle());

        Disc lionKing2Disc = new Disc();
        lionKing2Disc.setTitle("Leví kráľ 2");
        lionKing2Disc.setSubTitle("Simbová pýcha");
        lionKing2Disc.setOriginalTitle("The Lion King 2");
        lionKing2Disc.setOriginalSubTitle("Simba's Pride");
        lionKing2Disc.setDescription("V Serengeti pokojne vládne mierumilovný Mufasov syn Simba. Avšak za hranicami jeho kráľovstva žije vo vyhnanstve levica Zara, družka zlého Skara a pripravuje sa na svoj krvavý návrat do Simbovej ríše. Nástrojom jej pomsty a zároveň Simbovým vrahom sa má stať jej syn Kovu. Simbova krásna dcéra Kiara, budúca dedička trónu, je veľmi tvrdohlavá. Rozhodne sa tajne ujsť z domova, aby na vlastnej koži spoznala a preskúmala prísne zakázanú Vonkajšiu krajinu - zem za hranicami Simbovej ríše. Na svojej výprave sa stretne s rozmarným Kovu, ktorý ju zachráni z plameňov a zamiluje sa do nej. Kiara a Kovu poznávajú, že len oni môžu urovnať svár medzi dvoma znepriatelenými kráľovstvami a zároveň obhájiť aj svoju lásku.");
        lionKing2Disc.setType("DVD");
        lionKing2Disc.setImageUrl("http://pisces.bbystatic.com//image2/BestBuy_US/images/products/4798/4798922_sa.jpg");
        lionKing2Disc.setImageBGUrl("http://cdn.playbuzz.com/cdn/ed40ac19-0485-4084-8f48-0de2a794add0/dc3120c2-73b0-44b4-a8ba-686868a8f419.jpg");
        lionKing2Disc.setVideoUrl("//www.youtube.com/embed/H82ppf2x5y8");
        lionKing2Disc.setYear(1998);
        lionKing2Disc.setPrice(new BigDecimal("0.50"));
        discRepository.save(lionKing2Disc);

        log.info("Saved disc - id: " + lionKing2Disc.getId() + " - name: " + lionKing2Disc.getTitle());

        Disc xmen = new Disc();
        xmen.setTitle("X-Men");
        xmen.setSubTitle("Apokalypsa");
        xmen.setOriginalTitle("X-Men");
        xmen.setOriginalTitle("Apocalypse");
        xmen.setDescription("Od počiatku civilizácie bol uctievaný ako boh. Apocalypse, prvý a najsilnejší z mutantov marvelovského sveta X-Menov načerpal silu ostatných mutantov a stal sa tak nesmrteľným a neporaziteľným. Prebudil sa po tisíc rokoch a je rozčarovaný zo sveta, ktorý nachádza. Regrutuje tím mutantov vrátane sklamaného Magneta (Michael Fassbender) s cieľom očistiť ľudstvo a nastoliť nový svetový poriadok, ktorému chce vládnuť. Osud Krajina tak visí na vlásku. Raven (Jennifer Lawrencová) s pomocou profesora X (James McAvoy) stane v čele tíme mladých X-Menov, aby spoločne zastavili svojho najväčšieho nepriateľa a zachránili ľudstvo pred úplným zánikom.");
        xmen.setType("DVD");
        xmen.setImageUrl("https://images-na.ssl-images-amazon.com/images/I/61oPYII0sAL.jpg");
        xmen.setImageBGUrl("http://www.wallpaperscharlie.com/wp-content/uploads/2016/06/X-Men-Apocalypse-Wallpapers-10.jpg");
        xmen.setVideoUrl("//www.youtube.com/embed/PfBVIHgQbYk");
        xmen.setYear(2016);
        xmen.setPrice(new BigDecimal("0.50"));
        discRepository.save(xmen);

        log.info("Saved disc - id: " + xmen.getId() + " - name: " + xmen.getTitle());

        Disc mulan = new Disc();
        mulan.setTitle("Legenda o Mulan");
        mulan.setSubTitle("");
        mulan.setOriginalTitle("Mulan");
        mulan.setOriginalSubTitle("");
        mulan.setDescription("Kočovníci zo severu napadli ľudí z centrálnych plání a cisár vydáva rozkaz, aby muži nastúpili do zbrane. Mladé dievča, Mulan, osedlá koňa a odchádza za úsvitu z domu, aby bojovala v armáde Namiesto svojho chorého otca. Prestrojená za muža bojuje dlhých dvanásť rokov a spoznáva, čo je to priatelstvo, smrť na bojisku i láska. Nové filmové spracovanie tradičnej činskej legendy o dievčati Mulan, ktoré sa stalo slávnym generálom.");
        mulan.setType("DVD");
        mulan.setImageUrl("https://s-media-cache-ak0.pinimg.com/736x/a0/48/cc/a048cc8963e6a5548e75a4c3026581e8.jpg");
        mulan.setImageBGUrl("http://s1.picswalls.com/wallpapers/2014/08/11/mulan-high-definition-wallpaper_090337199_184.jpg");
        mulan.setVideoUrl("//www.youtube.com/embed/N9FbeVTWrKM");
        mulan.setYear(1998);
        mulan.setPrice(new BigDecimal("0.50"));
        discRepository.save(mulan);

        log.info("Saved disc - id: " + mulan.getId() + " - name: " + mulan.getTitle());

        Disc killbill = new Disc();
        killbill.setTitle("Kill Bill");
        killbill.setSubTitle("");
        killbill.setOriginalTitle("Kill Bill");
        killbill.setOriginalSubTitle("Vol. 1");
        killbill.setDescription("Bývalá členka špičkového zabijáckeho komanda sa rozhodne navždy skončiť s minulosťou a chce sa vydať. Jej svadobný deň sa však zmení na krvavé jatky v okamihu, keď na ňu zaútočí jej bývalý šéf Bill a pokúsi sa ju zabiť. Mladá žena však nezomrie, i keď si to všetci myslia: šťastnou náhodou vražedný útok prežije, ale upadne do kómy. Po piatich mučivo dlhých rokoch sa vracia z temného prahu smrti s jedinou myšlienkou: pomstiť sa všetkým, ktorí jej ublížili, bez ohľadu na to, čo ich k tomu viedlo. Ku všetkému odhodlaná hrdinka, ktorá si hovorí “Nevesta”, však najprv musí zistiť kto je proti nej – a tiež to, či neexistuje niekto, kto by mohol stáť na jej strane.");
        killbill.setType("DVD");
        killbill.setImageUrl("https://s-media-cache-ak0.pinimg.com/736x/e7/04/5a/e7045a78ea2fab2bfe42aa7406247904.jpg");
        killbill.setImageBGUrl("https://images3.alphacoders.com/216/216485.jpg");
        killbill.setVideoUrl("//www.youtube.com/embed/7kSuas6mRpk");
        killbill.setYear(2003);
        killbill.setPrice(new BigDecimal("0.50"));
        discRepository.save(killbill);

        log.info("Saved disc - id: " + mulan.getId() + " - name: " + mulan.getTitle());

        Disc johnWickDisc = new Disc();
        johnWickDisc.setTitle("John Wick");
        johnWickDisc.setSubTitle("");
        johnWickDisc.setOriginalTitle("John Wick");
        johnWickDisc.setOriginalSubTitle("");
        johnWickDisc.setDescription("Vlúpať se mu do domu, ukradnúť auto a zabiť milovaného psa, to sa skrátka nemalo stať. Strední zlodejíčkovia to nevedeli, ale ich bossovia v podsvetí áno. A musia konať. A tak,. keď sa John Wick vydáva na svoju drsnú vendetu, stáva sa zároveň terčom svojich bývalých kolegov - nájomných zabijakov.");
        johnWickDisc.setType("Blu-Ray");
        johnWickDisc.setImageUrl("http://i.ebayimg.com/00/s/MTA1MVg3NTA=/z/3koAAOSw4GVYQPeq/$_12.JPG?set_id=880000500F");
        johnWickDisc.setImageBGUrl("http://wallpapersdsc.net/wp-content/uploads/2016/01/John-Wick-Computer-Wallpaper.jpg");
        johnWickDisc.setVideoUrl("//www.youtube.com/embed/RllJtOw0USI");
        johnWickDisc.setYear(2014);
        johnWickDisc.setPrice(new BigDecimal("0.70"));
        discRepository.save(johnWickDisc);

        log.info("Saved Disc - id: " + johnWickDisc.getId() + " - name: " + johnWickDisc.getTitle());

        Disc v = new Disc();
        v.setTitle("V ako Vendetta");
        v.setSubTitle("");
        v.setOriginalTitle("V for Vendetta");
        v.setOriginalSubTitle("");
        v.setDescription("Príbeh sa odohráva v postkatastrofickej Británii drvenej totalitným režimom. Tajomný pomstiteľ vedie tvrdý odhodlaný boj proti totalitnej spoločnosti. Keď zachráni mladú ženu zo spárov tajnej polície, nachádza v nej nového spojenca.");
        v.setType("Blu-Ray");
        v.setImageUrl("https://displate.com/displates/2014-09-16/c40b36775d960f6468fae704958a6015.jpg?w=357&h=500&v=3");
        v.setImageBGUrl("https://images6.alphacoders.com/325/thumb-1920-325108.jpg");
        v.setVideoUrl("//www.youtube.com/embed/lSA7mAHolAw");
        v.setYear(2005);
        v.setPrice(new BigDecimal("0.70"));
        discRepository.save(v);

        log.info("Saved Disc - id: " + v.getId() + " - name: " + v.getTitle());

        Disc ripd = new Disc();
        ripd.setTitle("R.I.P.D");
        ripd.setSubTitle("Útvar rozhodne neživých agentov");
        ripd.setOriginalTitle("R.I.P.D");
        ripd.setOriginalSubTitle("Rest in peace department");
        ripd.setDescription("Detektíva Nicka smrteľne zasiahnu pri jednej akcii. No na druhom svete ho už čaká policajná dôstojníčka, ktorá mu ponúkne prácu v Útvare rozhodne neživých agentov. Bude sa môcť vrátiť na Zem a likvidovať zločincov, ktorí už dávno majú byť na onom svete. Nezvyčajný tím policajtov sa ocitá pred najväčšou bezpečnostnou krízou.");
        ripd.setType("Blu-Ray");
        ripd.setImageUrl("http://images.desimartini.com/media/main/movie_poster_detail/68875146-2309-45b3-8699-514c848dddcc.jpg");
        ripd.setImageBGUrl("http://i.lv3.hbo.com/assets/images/movies/r-i-p-d-/r-i-p-d-1920.jpg");
        ripd.setVideoUrl("//www.youtube.com/embed/ZCvJtozx0AA");
        ripd.setYear(2013);
        ripd.setPrice(new BigDecimal("0.70"));
        discRepository.save(ripd);

        log.info("Saved Disc - id: " + ripd.getId() + " - name: " + ripd.getTitle());

        Disc logan = new Disc();
        logan.setTitle("Logan");
        logan.setSubTitle("Wolverine");
        logan.setOriginalTitle("Logan");
        logan.setOriginalSubTitle("");
        logan.setDescription("Wolverine sa vracia. Tentokrát však nie je v dokonalej kondícii. Jeho schopnosť uzdravovania pomaly vyprcháva a elánom tiež neprekypuje. Naviac je na všetko skoro sám. Z X-Menov prežíva ešte Charles Xavier, ktorý je ale vážne chorý. Stráca pamäť a trpí nebezpečnými záchvatmi ohrozujúcimi jeho okolie. Spoločne sa ukrývajú v opustenej továrni na americko-mexickej hranici. Ich utajenie skončí, keď sa objaví Laura, dievča, ktoré vyniká rovnakými schopnosťami ako Wolverine a v pätách má naviac neľútostných prenasledovateľov, ktorí se jej snažia zmocniť. A tak prichádza čas, aby Wolverine v sebe našiel silu nielen na boj, ale na to, aby Laure odovzdal svoje skúsenosti.");
        logan.setType("DVD");
        logan.setImageUrl("https://images-na.ssl-images-amazon.com/images/I/91vTDigUkRL._SY500_.jpg");
        logan.setImageBGUrl("https://images3.alphacoders.com/807/thumb-1920-807544.jpg");
        logan.setVideoUrl("//www.youtube.com/embed/RH3OxVFvTeg");
        logan.setYear(2017);
        logan.setPrice(new BigDecimal("0.70"));
        discRepository.save(logan);

        log.info("Saved Disc - id: " + logan.getId() + " - name: " + logan.getTitle());
    }

    private void assignGenres() {
        List<Genre> genres = (List<Genre>) genreService.listAll();
        List<Disc> discs = (List<Disc>) discService.listAll();

        genres.forEach(genre -> {
            switch (genre.getGenre()) {
                case "Akčný":
                    discs.forEach(disc -> {
                        if (disc.getOriginalTitle().equals("John Wick") ||
                                disc.getOriginalTitle().equals("X-Men") ||
                                disc.getOriginalTitle().equals("Kill Bill") ||
                                disc.getOriginalTitle().equals("Logan") ||
                                disc.getOriginalTitle().equals("V for Vendetta") ||
                                disc.getOriginalTitle().equals("R.I.P.D")) {
                            disc.addGenre(genre);
                            discService.saveOrUpdate(disc);
                            disc.removeGenre(genre);
                        }
                    });
                    break;
                default:
                    discs.forEach(disc -> {
                        if (disc.getOriginalTitle().equals("The Lion King") ||
                                disc.getOriginalTitle().equals("The Lion King 2") ||
                                disc.getOriginalTitle().equals("Mulan")) {
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

        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.setUsername("user " + i);
            user.setPassword("user " + i);
            userService.saveOrUpdate(user);
        }

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
