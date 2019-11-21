INSERT INTO User (Id, Name, Email) VALUES (1,'user1','user1@gmail.com');
INSERT INTO User (Id, Name, Email) VALUES (2,'user2','user2@gmail.com');
INSERT INTO User (Id, Name, Email) VALUES (3,'user3','user3@gmail.com');

INSERT INTO Account (UserId,Balance,CurrencyCode) VALUES (2,300.0000,'USD');
INSERT INTO Account (UserId,Balance,CurrencyCode) VALUES (1,300.0000,'USD');
INSERT INTO Account (UserId,Balance,CurrencyCode) VALUES (1,300.0000,'USD');
INSERT INTO Account (UserId,Balance,CurrencyCode) VALUES (2,300.0000,'USD');
INSERT INTO Account (UserId,Balance,CurrencyCode) VALUES (1,300.0000,'USD');
INSERT INTO Account (UserId,Balance,CurrencyCode) VALUES (2,300.0000,'USD');


INSERT INTO Transaction (FromUserId,ToUserId, Amount, Status) VALUES (1,2,100.000, 'Pending');