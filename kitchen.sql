--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: dishes; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE dishes (
    id integer NOT NULL,
    name character varying,
    category integer
);


ALTER TABLE dishes OWNER TO "Guest";

--
-- Name: dishes_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE dishes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dishes_id_seq OWNER TO "Guest";

--
-- Name: dishes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE dishes_id_seq OWNED BY dishes.id;


--
-- Name: dishes_ingredients; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE dishes_ingredients (
    id integer NOT NULL,
    dish_id integer,
    ingredient_id integer,
    ingredient_amount numeric(6,2)
);


ALTER TABLE dishes_ingredients OWNER TO "Guest";

--
-- Name: dishes_ingredients_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE dishes_ingredients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dishes_ingredients_id_seq OWNER TO "Guest";

--
-- Name: dishes_ingredients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE dishes_ingredients_id_seq OWNED BY dishes_ingredients.id;


--
-- Name: ingredients; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE ingredients (
    id integer NOT NULL,
    name character varying,
    unit character varying,
    desired_on_hand integer,
    shelf_life_days integer
);


ALTER TABLE ingredients OWNER TO "Guest";

--
-- Name: ingredients_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE ingredients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ingredients_id_seq OWNER TO "Guest";

--
-- Name: ingredients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE ingredients_id_seq OWNED BY ingredients.id;


--
-- Name: inventories; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE inventories (
    id integer NOT NULL,
    ingredient_id integer,
    current_on_hand numeric(6,2),
    delivery_date date,
    expiration_date date
);


ALTER TABLE inventories OWNER TO "Guest";

--
-- Name: inventories_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE inventories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE inventories_id_seq OWNER TO "Guest";

--
-- Name: inventories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE inventories_id_seq OWNED BY inventories.id;


--
-- Name: orders; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE orders (
    id integer NOT NULL,
    dish_id integer,
    table_num integer,
    seat_num integer,
    comments character varying,
    is_paid boolean,
    patron_id integer,
    creation_date date,
    completion_date date,
    creation_time character varying,
    completion_time character varying,
    is_up boolean
);


ALTER TABLE orders OWNER TO "Guest";

--
-- Name: orders_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE orders_id_seq OWNER TO "Guest";

--
-- Name: orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE orders_id_seq OWNED BY orders.id;


--
-- Name: patrons; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE patrons (
    id integer NOT NULL,
    first_name character varying,
    last_name character varying,
    phone character varying(12),
    is_active boolean
);


ALTER TABLE patrons OWNER TO "Guest";

--
-- Name: patrons_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE patrons_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patrons_id_seq OWNER TO "Guest";

--
-- Name: patrons_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE patrons_id_seq OWNED BY patrons.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY dishes ALTER COLUMN id SET DEFAULT nextval('dishes_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY dishes_ingredients ALTER COLUMN id SET DEFAULT nextval('dishes_ingredients_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY ingredients ALTER COLUMN id SET DEFAULT nextval('ingredients_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY inventories ALTER COLUMN id SET DEFAULT nextval('inventories_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY orders ALTER COLUMN id SET DEFAULT nextval('orders_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY patrons ALTER COLUMN id SET DEFAULT nextval('patrons_id_seq'::regclass);


--
-- Data for Name: dishes; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY dishes (id, name, category) FROM stdin;
1834	Hamburger	2
1835	Fries	3
1836	Ice Cream	4
1837	Beer	5
1838	Clam Chowder	1
\.


--
-- Name: dishes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('dishes_id_seq', 1838, true);


--
-- Data for Name: dishes_ingredients; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY dishes_ingredients (id, dish_id, ingredient_id, ingredient_amount) FROM stdin;
541	1834	1178	8.00
542	1835	1179	9.00
543	1836	1180	8.00
545	1837	1181	1.00
546	1834	1182	1.00
547	1838	1183	12.00
\.


--
-- Name: dishes_ingredients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('dishes_ingredients_id_seq', 547, true);


--
-- Data for Name: ingredients; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY ingredients (id, name, unit, desired_on_hand, shelf_life_days) FROM stdin;
1179	Potatoes	Ounces	1000	200
1180	Ice Cream	Ounces	1000	20
1178	Ground Beef	Ounces	500	7
1181	Beer	Pints	100	100
1182	Buns	Pairs	1000	20
1183	Clams	Ounces	100	2
\.


--
-- Name: ingredients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('ingredients_id_seq', 1183, true);


--
-- Data for Name: inventories; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY inventories (id, ingredient_id, current_on_hand, delivery_date, expiration_date) FROM stdin;
815	1178	1520.00	2016-02-04	2016-02-11
819	1182	1198.00	2016-02-05	2016-02-25
816	1179	64.00	2016-02-04	2016-08-22
818	1181	99.00	2016-02-04	2016-05-14
817	1180	960.00	2016-02-04	2016-02-24
\.


--
-- Name: inventories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('inventories_id_seq', 819, true);


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY orders (id, dish_id, table_num, seat_num, comments, is_paid, patron_id, creation_date, completion_date, creation_time, completion_time, is_up) FROM stdin;
1362	1834	1	2		\N	0	2016-02-05	\N	08:15:24.473	\N	\N
1363	1835	1	2	extra crispy	\N	0	2016-02-05	\N	08:15:24.612	\N	\N
1364	1837	1	2	foamy	\N	0	2016-02-05	\N	08:15:24.770	\N	\N
1361	1834	1	1	well done	\N	0	2016-02-05	\N	08:15:00.584	\N	t
1365	1836	2	1	with syrup	t	0	2016-02-05	\N	08:15:48.959	\N	t
1344	1834	1	1	\N	t	0	2016-02-04	2016-02-04	15:18:25.251	16:23:39.088	t
1355	1834	16	1	well done	\N	0	2016-02-04	2016-02-04	16:43:50.633	16:45:19.046	\N
1357	1834	\N	\N	\N	t	\N	2016-02-01	2016-02-05	\N	08:09:49.142	\N
1358	1834	\N	\N	\N	t	\N	2016-02-01	2016-02-05	\N	08:09:51.870	\N
1359	1834	\N	\N	\N	t	\N	2016-02-01	2016-02-05	\N	08:09:54.790	\N
1360	1834	\N	\N	\N	t	\N	2016-02-01	2016-02-05	\N	08:09:57.414	\N
1354	1834	1	18	\N	t	0	2016-02-04	2016-02-05	16:38:23.363	08:14:24.174	\N
1345	1834	2	1	\N	t	0	2016-02-04	2016-02-05	15:24:41.454	08:14:26.422	t
1348	1834	9	9	well done	t	0	2016-02-04	2016-02-05	15:28:48.012	08:14:28.302	\N
1346	1835	2	3	\N	t	0	2016-02-04	2016-02-05	15:25:02.098	08:14:30.070	\N
1349	1835	9	9	extra crispy	t	0	2016-02-04	2016-02-05	15:28:48.142	08:14:32.134	t
1350	1836	9	9	with cream	t	0	2016-02-04	2016-02-05	15:28:48.281	08:14:34.110	t
1347	1836	10	1	with syrup	t	0	2016-02-04	2016-02-05	15:27:06.528	08:14:36.126	\N
1356	1834	16	1	\N	t	0	2016-02-04	2016-02-05	16:45:19.052	08:14:37.870	\N
\.


--
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('orders_id_seq', 1365, true);


--
-- Data for Name: patrons; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY patrons (id, first_name, last_name, phone, is_active) FROM stdin;
\.


--
-- Name: patrons_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('patrons_id_seq', 522, true);


--
-- Name: dishes_ingredients_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY dishes_ingredients
    ADD CONSTRAINT dishes_ingredients_pkey PRIMARY KEY (id);


--
-- Name: dishes_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY dishes
    ADD CONSTRAINT dishes_pkey PRIMARY KEY (id);


--
-- Name: ingredients_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY ingredients
    ADD CONSTRAINT ingredients_pkey PRIMARY KEY (id);


--
-- Name: inventories_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY inventories
    ADD CONSTRAINT inventories_pkey PRIMARY KEY (id);


--
-- Name: orders_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- Name: patrons_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY patrons
    ADD CONSTRAINT patrons_pkey PRIMARY KEY (id);


--
-- Name: dishes_ingredients_dish_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY dishes_ingredients
    ADD CONSTRAINT dishes_ingredients_dish_id_fkey FOREIGN KEY (dish_id) REFERENCES dishes(id);


--
-- Name: dishes_ingredients_ingredient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY dishes_ingredients
    ADD CONSTRAINT dishes_ingredients_ingredient_id_fkey FOREIGN KEY (ingredient_id) REFERENCES ingredients(id);


--
-- Name: inventories_ingredient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY inventories
    ADD CONSTRAINT inventories_ingredient_id_fkey FOREIGN KEY (ingredient_id) REFERENCES ingredients(id);


--
-- Name: orders_dish_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_dish_id_fkey FOREIGN KEY (dish_id) REFERENCES dishes(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

