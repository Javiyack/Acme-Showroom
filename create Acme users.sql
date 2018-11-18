start transaction;
USE `Acme-Showroom`;

create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';


GRANT SELECT, INSERT, UPDATE, DELETE
  ON `Acme-Showroom`.* TO 'acme-user'@'%';

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER,
    CREATE TEMPORARY TABLES, LOCK TABLES, CREATE VIEW, CREATE ROUTINE,
    ALTER ROUTINE, EXECUTE, TRIGGER, SHOW VIEW
  ON `Acme-Showroom`.* TO 'acme-manager'@'%';

commit;