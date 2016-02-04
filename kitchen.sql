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
\.


--
-- Name: dishes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('dishes_id_seq', 1833, true);


--
-- Data for Name: dishes_ingredients; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY dishes_ingredients (id, dish_id, ingredient_id, ingredient_amount) FROM stdin;
\.


--
-- Name: dishes_ingredients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('dishes_ingredients_id_seq', 540, true);


--
-- Data for Name: ingredients; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY ingredients (id, name, unit, desired_on_hand, shelf_life_days) FROM stdin;
\.


--
-- Name: ingredients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('ingredients_id_seq', 1177, true);


--
-- Data for Name: inventories; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY inventories (id, ingredient_id, current_on_hand, delivery_date, expiration_date) FROM stdin;
\.


--
-- Name: inventories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('inventories_id_seq', 814, true);


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY orders (id, dish_id, table_num, seat_num, comments, is_paid, patron_id, creation_date, completion_date, creation_time, completion_time, is_up) FROM stdin;
\.


--
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('orders_id_seq', 1336, true);


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

