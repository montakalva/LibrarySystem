
CREATE DATABASE IF NOT EXISTS JAVA_27_28_LibraryManagementSystem;

USE JAVA_27_28_LibraryManagementSystem;

CREATE TABLE IF NOT EXISTS books (
    id int not null auto_increment,
    bookName varchar (200) not null,
    author varchar (200) not null,
    yearPublished varchar (20),
    genre varchar (100),
    description text,
    status varchar (100) not null,
    bookAmount int not null,
    specialMarks text,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
    id int not null auto_increment,
    userName varchar (200) not null,
    password varchar (100) not null,
    age int not null,
    email varchar (200) UNIQUE not null,
    phoneNumber varchar (100),
    specialMarks text,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS bookManagementSystem (
    id int not null auto_increment,
    borrowedAt timestamp not null,
    returnedAt timestamp,
    bookId int,
    userId int,
    PRIMARY KEY (id),
    FOREIGN KEY (bookId) REFERENCES books (id),
    FOREIGN KEY (userId) REFERENCES users (id)
);