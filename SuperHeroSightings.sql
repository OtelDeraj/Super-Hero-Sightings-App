drop database if exists SuperHeroSightings;

create database SuperHeroSightings;

use SuperHeroSightings;

create table Powers(
powerId int primary key auto_increment,
`name` varchar(30) not null unique
);

insert into Powers(`name`)
values
('Super Testing Abilities'),
('Super Intellect'),
('Telekenesis');

create table Supers(
superId int primary key auto_increment,
`name` varchar(30) not null unique,
`description` varchar(255) not null
);

insert into supers(`name`, `description`)
values
('Captain Testing', 'This is a test description of a totally real not fake super');

create table Super_Powers(
powerId int not null,
superId int not null,
foreign key fk_sp_power(powerId)
references Powers(powerId),
foreign key fk_sp_super(superId)
references Supers(superId)
);

insert into super_powers(powerId, superId)
values
(1, 1),
(2, 1),
(3, 1);

create table Orgs(
orgId int primary key auto_increment,
`name` varchar(50) not null unique,
`description` varchar(255) not null,
address varchar(60) not null,
phone varchar(15) not null
);

insert into Orgs(`name`, `description`, address, phone)
values
('Test Org', 'Test Org Desc', '1600 Test Dr.', '612-206-4554');

create table Affiliations(
orgId int not null,
superId int not null,
foreign key fk_aff_org(orgId)
references Organizations(orgId),
foreign key fk_aff_sup(superId)
references Supers(superId)
);

insert into affiliations(orgId, superId)
values
(1, 1);

create table Locations(
locId int primary key auto_increment,
`name` varchar(50) not null unique,
`description` varchar(255) not null,
address varchar(60) not null,
lat decimal(7, 5) not null,
lon decimal(8, 5) not null
);

insert into locations(`name`, `description`, address, lat, lon)
values
('Test Location', 'Test Loc Desc', 'Test Loc Address', 90.00000, 180.00000);

create table Sightings(
sightId int primary key auto_increment,
sightDate date not null,
superId int not null,
locId int not null,
foreign key fk_sight_super(superId)
references Supers(superId),
foreign key fk_sight_loc(locId)
references Locations(locId)
);

insert into sightings(sightDate, superId, locId)
values
('2020-12-08', 1, 1);

select * from powers pw left outer join super_powers sp on pw.powerId = sp.powerId where sp.superId = 1;

select * from Orgs og inner join affiliations af on og.orgId = af.orgId where af.superId = 1;



