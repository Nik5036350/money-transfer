DROP SCHEMA IF EXISTS test;

DROP TABLE IF EXISTS User;

CREATE TABLE User
(
    Id    LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
    Name  VARCHAR(30) NOT NULL,
    Email VARCHAR(30) NOT NULL
);

-- CREATE UNIQUE INDEX idx_ue on User(UserName,EmailAddress);


DROP TABLE IF EXISTS Account;

CREATE TABLE Account
(
    Id           LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
    UserId       LONG NOT NULL,
    Balance      DECIMAL(19, 4),
    CurrencyCode VARCHAR(30),
    foreign key (UserId) references User (Id)
);

-- CREATE UNIQUE INDEX idx_acc on Account(UserId, CurrencyCode);
DROP TABLE IF EXISTS Transaction;

CREATE TABLE Transaction
(
    Id         LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
    FromUserId LONG NOT NULL,
    ToUserId   LONG NOT NULL,
    Amount     DECIMAL(19, 4),
    Status     VARCHAR(30) DEFAULT 'Pending',
    foreign key (FromUserId) references User (Id),
    foreign key (ToUserId) references User (Id)
);

-- CREATE UNIQUE INDEX idx_ue on User(UserName,EmailAddress);

