CREATE DATABASE trocencheres;

CREATE USER 'dbowner'@'localhost' IDENTIFIED BY 'Pa$$w0rd';
GRANT ALL PRIVILEGES ON trocencheres.* TO 'dbowner'@'localhost';


USE trocencheres;

CREATE TABLE CATEGORIES (
    no_categorie   INTEGER NOT NULL AUTO_INCREMENT,
    libelle        VARCHAR(30) NOT NULL,
    CONSTRAINT categorie_pk PRIMARY KEY (no_categorie)
);

CREATE TABLE RETRAITS (
	no_retrait       INTEGER NOT NULL AUTO_INCREMENT,
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(15) NOT NULL,
    ville            VARCHAR(30) NOT NULL,
    CONSTRAINT retrait_pk PRIMARY KEY (no_retrait)
);

CREATE TABLE UTILISATEURS (
    no_utilisateur   INTEGER NOT NULL AUTO_INCREMENT,
    pseudo           VARCHAR(30) NOT NULL,
    nom              VARCHAR(30) NOT NULL,
    prenom           VARCHAR(30) NOT NULL,
    email            VARCHAR(20) NOT NULL,
    telephone        VARCHAR(15),
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(10) NOT NULL,
    ville            VARCHAR(30) NOT NULL,
    mot_de_passe     VARCHAR(30) NOT NULL,
    credit           INTEGER NOT NULL,
    administrateur   bit NOT NULL,
    CONSTRAINT utilisateur_pk PRIMARY KEY (no_utilisateur)
);

CREATE TABLE ARTICLES_VENDUS (
    no_article                    INTEGER NOT NULL AUTO_INCREMENT,
    nom_article                   VARCHAR(30) NOT NULL,
    description                   VARCHAR(300) NOT NULL,
	date_debut_encheres           DATE NOT NULL,
    date_fin_encheres             DATE NOT NULL,
    prix_initial                  INTEGER,
    prix_vente                    INTEGER,
	etat_vente					  CHAR(2),
    no_utilisateur_acheteur       INTEGER,
	no_utilisateur_vendeur		  INTEGER NOT NULL,
    no_categorie                  INTEGER NOT NULL,
	no_retrait                    INTEGER NOT NULL,
    CONSTRAINT articles_vendus_pk PRIMARY KEY (no_article)
);

CREATE TABLE ENCHERES (
    no_utilisateur   INTEGER NOT NULL,
    no_article       INTEGER NOT NULL,
    date_enchere     datetime NOT NULL,
	montant_enchere  INTEGER NOT NULL,
	CONSTRAINT enchere_pk PRIMARY KEY (no_utilisateur, no_article)
);

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT encheres_utilisateur_acheteur_fk FOREIGN KEY ( no_utilisateur_acheteur ) REFERENCES UTILISATEURS ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action 
;
ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT encheres_utilisateur_vendeur_fk FOREIGN KEY ( no_utilisateur_vendeur ) REFERENCES UTILISATEURS ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action 
;
ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT articles_vendus_categories_fk FOREIGN KEY ( no_categorie )
        REFERENCES categories ( no_categorie )
ON DELETE NO ACTION 
    ON UPDATE no action 
;
ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT retraits_articles_vendus_fk FOREIGN KEY ( no_retrait )
        REFERENCES RETRAITS ( no_retrait )
ON DELETE NO ACTION 
    ON UPDATE no action 
;



ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_articles_vendus_fk FOREIGN KEY ( no_article )
        REFERENCES ARTICLES_VENDUS ( no_article )
ON DELETE NO ACTION 
    ON UPDATE no action 
;
ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_utilisateurs_fk FOREIGN KEY ( no_utilisateur )
        REFERENCES UTILISATEURS ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action 
;
