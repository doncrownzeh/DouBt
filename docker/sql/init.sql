CREATE DATABASE postgres_1;
\c postgres_1;

CREATE TABLE TABLE_1 (
  id serial NOT NULL,
  PRIMARY KEY (id),
  column_0 character varying NOT NULL,
  column_1 character varying NOT NULL
);

CREATE TABLE TABLE_2 (
  id uuid NOT NULL,
  column_1 character varying NOT NULL,
  column_2 smallint NOT NULL,
  ref_id integer NOT NULL
);

ALTER TABLE TABLE_2
ADD CONSTRAINT table_2_id PRIMARY KEY (id);
ALTER TABLE TABLE_2
ADD FOREIGN KEY (ref_id) REFERENCES TABLE_1 (id);

CREATE TABLE TABLE_3 (
  id serial NOT NULL,
  PRIMARY KEY (id),
  name character varying NOT NULL
);

CREATE TABLE TABLE_4 (
  table_2_id uuid NOT NULL,
  table_3_id integer NOT NULL
);

ALTER TABLE TABLE_4
ADD CONSTRAINT constraint_link_table PRIMARY KEY (table_2_id, table_3_id);
ALTER TABLE TABLE_4
ADD FOREIGN KEY (table_2_id) REFERENCES TABLE_2 (id);
ALTER TABLE TABLE_4
ADD FOREIGN KEY (table_3_id) REFERENCES TABLE_3 (id);


INSERT INTO TABLE_3 (name) VALUES ('val1');
INSERT INTO TABLE_3 (name) VALUES ('val2');
INSERT INTO TABLE_3 (name) VALUES ('val3');
INSERT INTO TABLE_3 (name) VALUES ('val4');
INSERT INTO TABLE_3 (name) VALUES ('val5');
INSERT INTO TABLE_3 (name) VALUES ('val6');
COMMIT;

INSERT INTO TABLE_1 (column_0, column_1)
VALUES ('t_1_val_1', 't_1_val_2');
COMMIT;

INSERT INTO TABLE_2 (id, column_1, column_2, ref_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 'val1', 2, 1);
COMMIT;

INSERT INTO TABLE_4 (table_2_id, table_3_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 1);
INSERT INTO TABLE_4 (table_2_id, table_3_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 2);
INSERT INTO TABLE_4 (table_2_id, table_3_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 3);
INSERT INTO TABLE_4 (table_2_id, table_3_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 4);
INSERT INTO TABLE_4 (table_2_id, table_3_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 5);
INSERT INTO TABLE_4 (table_2_id, table_3_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 6);
COMMIT;

CREATE DATABASE postgres_2;
\c postgres_2;

CREATE TABLE TABLE_1 (
  id serial NOT NULL,
  PRIMARY KEY (id),
  column_0 character varying NOT NULL,
  column_1 character varying NOT NULL
);

CREATE TABLE TABLE_2 (
  id uuid NOT NULL,
  column_1 character varying NOT NULL,
  column_2 smallint NOT NULL,
  ref_id integer NOT NULL
);

ALTER TABLE TABLE_2
ADD CONSTRAINT table_2_id PRIMARY KEY (id);
ALTER TABLE TABLE_2
ADD FOREIGN KEY (ref_id) REFERENCES TABLE_1 (id);

CREATE TABLE TABLE_3 (
  id serial NOT NULL,
  PRIMARY KEY (id),
  name character varying NOT NULL
);

CREATE TABLE TABLE_4 (
  table_2_id uuid NOT NULL,
  table_3_id integer NOT NULL
);

ALTER TABLE TABLE_4
ADD CONSTRAINT constraint_link_table PRIMARY KEY (table_2_id, table_3_id);
ALTER TABLE TABLE_4
ADD FOREIGN KEY (table_2_id) REFERENCES TABLE_2 (id);
ALTER TABLE TABLE_4
ADD FOREIGN KEY (table_3_id) REFERENCES TABLE_3 (id);


INSERT INTO TABLE_3 (name) VALUES ('val1');
INSERT INTO TABLE_3 (name) VALUES ('val2');
INSERT INTO TABLE_3 (name) VALUES ('val3');
INSERT INTO TABLE_3 (name) VALUES ('val4');
INSERT INTO TABLE_3 (name) VALUES ('val5');
INSERT INTO TABLE_3 (name) VALUES ('val6');
COMMIT;

INSERT INTO TABLE_1 (column_0, column_1)
VALUES ('t_1_val_1', 't_1_val_2');
COMMIT;

INSERT INTO TABLE_2 (id, column_1, column_2, ref_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 'val1', 2, 1);
COMMIT;

INSERT INTO TABLE_4 (table_2_id, table_3_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 1);
INSERT INTO TABLE_4 (table_2_id, table_3_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 2);
INSERT INTO TABLE_4 (table_2_id, table_3_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 3);
INSERT INTO TABLE_4 (table_2_id, table_3_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 4);
INSERT INTO TABLE_4 (table_2_id, table_3_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 5);
INSERT INTO TABLE_4 (table_2_id, table_3_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 6);
COMMIT;

CREATE DATABASE postgres_3;
\c postgres_3;

CREATE TABLE TABLE_1 (
  id serial NOT NULL,
  PRIMARY KEY (id),
  column_0 character varying NOT NULL,
  column_1 character varying NOT NULL
);

CREATE TABLE TABLE_2 (
  id uuid NOT NULL,
  column_1 character varying NOT NULL,
  column_2 smallint NOT NULL,
  ref_id integer NOT NULL
);

ALTER TABLE TABLE_2
ADD CONSTRAINT table_2_id PRIMARY KEY (id);
ALTER TABLE TABLE_2
ADD FOREIGN KEY (ref_id) REFERENCES TABLE_1 (id);

CREATE TABLE TABLE_3 (
  id serial NOT NULL,
  PRIMARY KEY (id),
  name character varying NOT NULL
);

CREATE TABLE TABLE_4 (
  table_2_id uuid NOT NULL,
  table_3_id integer NOT NULL
);

ALTER TABLE TABLE_4
ADD CONSTRAINT constraint_link_table PRIMARY KEY (table_2_id, table_3_id);
ALTER TABLE TABLE_4
ADD FOREIGN KEY (table_2_id) REFERENCES TABLE_2 (id);
ALTER TABLE TABLE_4
ADD FOREIGN KEY (table_3_id) REFERENCES TABLE_3 (id);


INSERT INTO TABLE_3 (name) VALUES ('val1');
INSERT INTO TABLE_3 (name) VALUES ('val2');
INSERT INTO TABLE_3 (name) VALUES ('val3');
INSERT INTO TABLE_3 (name) VALUES ('val4');
INSERT INTO TABLE_3 (name) VALUES ('val5');
INSERT INTO TABLE_3 (name) VALUES ('val6');
COMMIT;

INSERT INTO TABLE_1 (column_0, column_1)
VALUES ('t_1_val_1', 't_1_val_2');
COMMIT;

INSERT INTO TABLE_2 (id, column_1, column_2, ref_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 'val1', 2, 1);
COMMIT;

INSERT INTO TABLE_4 (table_2_id, table_3_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 1);
INSERT INTO TABLE_4 (table_2_id, table_3_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 2);
INSERT INTO TABLE_4 (table_2_id, table_3_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 3);
INSERT INTO TABLE_4 (table_2_id, table_3_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 4);
INSERT INTO TABLE_4 (table_2_id, table_3_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 5);
INSERT INTO TABLE_4 (table_2_id, table_3_id)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608', 6);
COMMIT;