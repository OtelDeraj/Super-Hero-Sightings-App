use SuperHeroSightings;

insert into powers(`name`)
values
('Speed'),
('Strength'),
('Telekenisis'),
('Invisiblity'),
('Flight');

select * from powers;

insert into supers(`name`, `description`)
values
('StrongMan', 'Hero, From Kentucky'),
('Inviso-Bill', 'Wanted for Grand Larsony'),
('SuperMan', 'American Born, Bred for Justice'),
('Sinestro', 'Alien bent of using fear to subjagate humans');

select * from supers;

insert into super_powers(powerId, superId)
values
(2, 1),
(4, 2),
(2, 3),
(5, 3),
(1, 3),
(3, 4),
(5, 4);

select * from super_powers where superId = 3;

insert into organizations(`name`, `description`, address, phone)
values
('Justice League', 'Space based group of Heroes.', 'Earths Orbit', '333-555-8888'),
('League of Evil', 'Home of Villains.', 'Swamp of Evil', '777-666-3333'),
('United Nations', 'Collection of countries keeping order across earth.', 'New York, NY', '123-555-9999');

select * from organizations;

insert into affiliations(orgId, superId)
values
(1, 1),
(2, 2),
(1, 3),
(3, 3),
(2, 4);

select * from affiliations where orgId = 3;


insert into locations(`name`, `description`, address, latitude, longitude)
values
('Metropolis', 'Big ol city', '1296 metro ave', 80, -112),
('Gotham', 'Seedy', '1234 Wayne Dr', 23, -150),
('fake town', 'fake', '4545 Made Up Blvd', -60, 100),
('New York', 'Big Apple', '1530 1st ave', 50, -110);

select * from locations;

insert into sightings(sightDate, superId, locId)
values
('2020-11-08', 1, 3),
('2020-07-29', 3, 4),
('2020-09-12', 4, 2),
('2019-12-24', 3, 1);

select * from sightings;


