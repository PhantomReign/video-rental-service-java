

delete from role_permission;
delete from user_role;
delete from permissions;
delete from roles;
delete from users;
delete from orders;
delete from genres;
delete from discs;
delete from disc_genre;
delete from categories;

INSERT INTO permissions (id, name, date_Created, last_Updated) VALUES
(1, 'MANAGE_CATEGORIES', now(), now()),
(2, 'MANAGE_DISCS', now(), now()),
(3, 'MANAGE_ORDERS', now(), now()),
(4, 'MANAGE_GENRES', now(), now()),
(5, 'MANAGE_USERS', now(), now()),
(6, 'MANAGE_ROLES', now(), now()),
(7, 'MANAGE_PERMISSIONS', now(), now());

INSERT INTO roles (id, name, date_Created, last_Updated) VALUES
(1, 'ROLE_SUPER_ADMIN', now(), now()),
(2, 'ROLE_ADMIN', now(), now()),
(3, 'ROLE_USER', now(), now());

INSERT INTO users (id, email, password, user_Name, first_Name, last_Name, phone, address, date_Created, last_Updated) VALUES

(1, 'superadmin@gmail.com', '$2a$06$ucnRvG91hj54lMYDhFS.Y.fay.Raee.aJMVkEsV4cLsRnwITYgI/S', 'Super Admin', 'Derek', 'Jones', '+421 955 489 777', 'Jehodova 6, 563 34 Brno', now(), now()),
(2, 'admin@gmail.com', '$2a$06$76k9I2FSGwpwM5JHV8Q9Vevn9K./3Ke9NUb.ji/Vf/l1jZkPWRRau', 'Admin', 'Eric', 'Ulbricht', '+421 955 489 222', 'Sarvbova 55, 733 34 Praha', now(), now()),
(3, 'user@gmail.com', '$2a$06$aJLUgPwSZof/i/ylEipaleuIUBS7ZT6.SwYiWuSOdrxpmc2Ddy9iS', 'User', 'Piotr', 'Jones', '+421 333 444 777', 'Uhova 12, 563 34 Brno', now(), now());

insert into role_permission(role_id, permission_id) values
(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),
(2,1),(2,2),(2,3),(2,4);

insert into user_role(user_id, role_id) values
(1,1),
(2,2),
(3,3);

insert into categories(id, name, date_Created, last_Updated) values
(1, 'DVD', now(), now()),
(2, 'Blu-Ray', now(), now());

insert into genres(id, name, date_Created, last_Updated) values
(1, 'Animovaný', now(), now()),
(2, 'Akčný', now(), now()),
(3, 'Komédia', now(), now()),
(4, 'Rodinný', now(), now());


insert into discs(id, title, sub_Title, original_Title, original_Sub_Title, description, image_Url,
imageBGUrl, video_Url, year, price, available, category_Id, date_Created, last_Updated) values
(1, 'Leví kráľ', '', 'The Lion King', '',
'Rozľahlou africkou savanou sa ťahá nekonečný sprievod. Slony, žirafy, zebry a ďalšie zvieratá prichádzajú k Levej skale, aby oslávili narodenie Simbu, syna kráľa Mufasu. Je tu však niekto, kto sa spolu s ostatnými neraduje. Mufasov brat Scar sa nechce zmieriť s tým, že nie je následníkom trónu, a premýšľa, ako by sa Simbu zbavil.',
'https://s-media-cache-ak0.pinimg.com/736x/cb/32/50/cb32509c8101ad041572f2b88dac5636.jpg',
'http://eskipaper.com/images/lion-king-wallpaper-3.jpg',
'www.youtube.com/embed/jOIu472cCq0',
1994, 0.50, TRUE, 1, now(), now()),
(2, 'Leví kráľ 2', 'Simbová pýcha', 'The Lion King 2', 'Simba''''s'' ''Pride',
'V Serengeti pokojne vládne mierumilovný Mufasov syn Simba. Avšak za hranicami jeho kráľovstva žije vo vyhnanstve levica Zara, družka zlého Skara a pripravuje sa na svoj krvavý návrat do Simbovej ríše. Nástrojom jej pomsty a zároveň Simbovým vrahom sa má stať jej syn Kovu. Simbova krásna dcéra Kiara, budúca dedička trónu, je veľmi tvrdohlavá. Rozhodne sa tajne ujsť z domova, aby na vlastnej koži spoznala a preskúmala prísne zakázanú Vonkajšiu krajinu - zem za hranicami Simbovej ríše. Na svojej výprave sa stretne s rozmarným Kovu, ktorý ju zachráni z plameňov a zamiluje sa do nej. Kiara a Kovu poznávajú, že len oni môžu urovnať svár medzi dvoma znepriatelenými kráľovstvami a zároveň obhájiť aj svoju lásku.',
'http://pisces.bbystatic.com//image2/BestBuy_US/images/products/4798/4798922_sa.jpg',
'http://cdn.playbuzz.com/cdn/ed40ac19-0485-4084-8f48-0de2a794add0/dc3120c2-73b0-44b4-a8ba-686868a8f419.jpg',
'www.youtube.com/embed/H82ppf2x5y8',
1998, 0.50, TRUE, 1, now(), now()),
(3, 'X-Men', 'Apokalypsa', 'X-Men', 'Apocalypse',
'Od počiatku civilizácie bol uctievaný ako boh. Apocalypse, prvý a najsilnejší z mutantov marvelovského sveta X-Menov načerpal silu ostatných mutantov a stal sa tak nesmrteľným a neporaziteľným. Prebudil sa po tisíc rokoch a je rozčarovaný zo sveta, ktorý nachádza. Regrutuje tím mutantov vrátane sklamaného Magneta (Michael Fassbender) s cieľom očistiť ľudstvo a nastoliť nový svetový poriadok, ktorému chce vládnuť. Osud Krajina tak visí na vlásku. Raven (Jennifer Lawrencová) s pomocou profesora X (James McAvoy) stane v čele tíme mladých X-Menov, aby spoločne zastavili svojho najväčšieho nepriateľa a zachránili ľudstvo pred úplným zánikom.',
'https://images-na.ssl-images-amazon.com/images/I/61oPYII0sAL.jpg',
'http://www.wallpaperscharlie.com/wp-content/uploads/2016/06/X-Men-Apocalypse-Wallpapers-10.jpg',
'www.youtube.com/embed/PfBVIHgQbYk',
2016, 0.50, TRUE, 1, now(), now()),
(4, 'Legenda o Mulan', '', 'Mulan', '',
'Kočovníci zo severu napadli ľudí z centrálnych plání a cisár vydáva rozkaz, aby muži nastúpili do zbrane. Mladé dievča, Mulan, osedlá koňa a odchádza za úsvitu z domu, aby bojovala v armáde Namiesto svojho chorého otca. Prestrojená za muža bojuje dlhých dvanásť rokov a spoznáva, čo je to priatelstvo, smrť na bojisku i láska. Spracovanie tradičnej činskej legendy o dievčati Mulan, ktoré sa stalo slávnym generálom.',
'https://s-media-cache-ak0.pinimg.com/736x/a0/48/cc/a048cc8963e6a5548e75a4c3026581e8.jpg',
'http://s1.picswalls.com/wallpapers/2014/08/11/mulan-high-definition-wallpaper_090337199_184.jpg',
'www.youtube.com/embed/N9FbeVTWrKM',
1998, 0.50, TRUE, 1, now(), now()),
(5, 'Kill Bill', '', 'Kill Bill', 'Vol. 1',
'Bývalá členka špičkového zabijáckeho komanda sa rozhodne navždy skončiť s minulosťou a chce sa vydať. Jej svadobný deň sa však zmení na krvavé jatky v okamihu, keď na ňu zaútočí jej bývalý šéf Bill a pokúsi sa ju zabiť. Mladá žena však nezomrie, i keď si to všetci myslia: šťastnou náhodou vražedný útok prežije, ale upadne do kómy. Po piatich mučivo dlhých rokoch sa vracia z temného prahu smrti s jedinou myšlienkou: pomstiť sa všetkým, ktorí jej ublížili, bez ohľadu na to, čo ich k tomu viedlo. Ku všetkému odhodlaná hrdinka, ktorá si hovorí “Nevesta”, však najprv musí zistiť kto je proti nej – a tiež to, či neexistuje niekto, kto by mohol stáť na jej strane.',
'https://s-media-cache-ak0.pinimg.com/736x/e7/04/5a/e7045a78ea2fab2bfe42aa7406247904.jpg',
'https://images3.alphacoders.com/216/216485.jpg',
'www.youtube.com/embed/7kSuas6mRpk',
2003, 0.70, TRUE, 2, now(), now()),
(6, 'John Wick', '', 'John Wick', '',
'Vlúpať se mu do domu, ukradnúť auto a zabiť milovaného psa, to sa skrátka nemalo stať. Strední zlodejíčkovia to nevedeli, ale ich bossovia v podsvetí áno. A musia konať. A tak,. keď sa John Wick vydáva na svoju drsnú vendetu, stáva sa zároveň terčom svojich bývalých kolegov - nájomných zabijakov.',
'http://i.ebayimg.com/00/s/MTA1MVg3NTA=/z/3koAAOSw4GVYQPeq/$_12.JPG?set_id=880000500F',
'http://wallpapersdsc.net/wp-content/uploads/2016/01/John-Wick-Computer-Wallpaper.jpg',
'www.youtube.com/embed/RllJtOw0USI',
2014, 0.70, TRUE, 2, now(), now()),
(7, 'V ako Vendetta', '', 'V for Vendetta', '',
'Príbeh sa odohráva v postkatastrofickej Británii drvenej totalitným režimom. Tajomný pomstiteľ vedie tvrdý odhodlaný boj proti totalitnej spoločnosti. Keď zachráni mladú ženu zo spárov tajnej polície, nachádza v nej nového spojenca.',
'https://displate.com/displates/2014-09-16/c40b36775d960f6468fae704958a6015.jpg?w=357&h=500&v=3',
'https://images6.alphacoders.com/325/thumb-1920-325108.jpg',
'www.youtube.com/embed/lSA7mAHolAw',
2005, 0.70, TRUE, 2, now(), now()),
(8, 'R.I.P.D', 'Útvar rozhodne neživých agentov', 'R.I.P.D', 'Rest in peace department',
'Detektíva Nicka smrteľne zasiahnu pri jednej akcii. No na druhom svete ho už čaká policajná dôstojníčka, ktorá mu ponúkne prácu v Útvare rozhodne neživých agentov. Bude sa môcť vrátiť na Zem a likvidovať zločincov, ktorí už dávno majú byť na onom svete. Nezvyčajný tím policajtov sa ocitá pred najväčšou bezpečnostnou krízou.',
'http://images.desimartini.com/media/main/movie_poster_detail/68875146-2309-45b3-8699-514c848dddcc.jpg',
'http://i.lv3.hbo.com/assets/images/movies/r-i-p-d-/r-i-p-d-1920.jpg',
'www.youtube.com/embed/ZCvJtozx0AA',
2013, 0.50, TRUE, 1, now(), now()),
(9, 'Logan', 'Wolverine', 'Logan', '',
'Wolverine sa vracia. Tentokrát však nie je v dokonalej kondícii. Jeho schopnosť uzdravovania pomaly vyprcháva a elánom tiež neprekypuje. Naviac je na všetko skoro sám. Z X-Menov prežíva ešte Charles Xavier, ktorý je ale vážne chorý. Stráca pamäť a trpí nebezpečnými záchvatmi ohrozujúcimi jeho okolie. Spoločne sa ukrývajú v opustenej továrni na americko-mexickej hranici. Ich utajenie skončí, keď sa objaví Laura, dievča, ktoré vyniká rovnakými schopnosťami ako Wolverine a v pätách má naviac neľútostných prenasledovateľov, ktorí se jej snažia zmocniť. A tak prichádza čas, aby Wolverine v sebe našiel silu nielen na boj, ale na to, aby Laure odovzdal svoje skúsenosti.',
'https://images-na.ssl-images-amazon.com/images/I/91vTDigUkRL._SY500_.jpg',
'https://images3.alphacoders.com/807/thumb-1920-807544.jpg',
'www.youtube.com/embed/RH3OxVFvTeg',
2017, 0.70, TRUE, 2, now(), now());

insert into disc_genre(disc_id, genre_id) values
(1,1),(1,3),(1,4),
(2,1),(2,3),(2,4),
(3,2),
(4,1),(4,3),(4,4),
(5,2),
(6,2),
(7,2),
(8,2),(8,3),
(9,1),(9,3),(9,4);

