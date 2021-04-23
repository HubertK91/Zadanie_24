DROP TABLE IF EXISTS transaction;
CREATE TABLE transaction (
  id int NOT NULL AUTO_INCREMENT,
  `type` enum('EXPENSE','INCOME') NOT NULL,
  `description` varchar(100) NOT NULL,
  amount double NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (id)
);
INSERT INTO transaction VALUES (1,'INCOME','przelew z firmy',8000,'2020-09-03'),(2,'EXPENSE','komputer',3000,'2021-03-02');
