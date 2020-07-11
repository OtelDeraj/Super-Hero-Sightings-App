drop database if exists SuperHeroSightings;

create database SuperHeroSightings;

use SuperHeroSightings;

create table Powers(
powerId int primary key auto_increment,
`name` varchar(30) not null unique
);

create table Supers(
superId int primary key auto_increment,
`name` varchar(30) not null unique,
`description` varchar(255) not null
);

create table Super_Powers(
powerId int not null,
superId int not null,
foreign key fk_sp_power(powerId)
references Powers(powerId),
foreign key fk_sp_super(superId)
references Supers(superId)
);

create table Organizations(
orgId int primary key auto_increment,
`name` varchar(50) not null unique,
`description` varchar(255) not null,
address varchar(60) not null,
phone varchar(15) not null
);

create table Affiliations(
orgId int not null,
superId int not null,
foreign key fk_aff_org(orgId)
references Organizations(orgId),
foreign key fk_aff_sup(superId)
references Supers(superId)
);

create table Locations(
locId int primary key auto_increment,
`name` varchar(50) not null unique,
`description` varchar(255) not null,
address varchar(60) not null,
coord point not null
);

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







