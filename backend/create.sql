

create sequence hibernate_sequence start 1 increment 1;

create table certificate (
                             id int8 not null,
                             digital_signature bytea,
                             id_of_certificate_owner int8,
                             id_of_certificate_publisher int8,
                             is_revoked boolean,
                             public_key bytea,
                             time_of_publishing date,
                             valid_until date,
                             primary key (id)
);

create table certificate_authority (
                                       id int8 not null,
                                       certificate_authority_parent_id int8,
                                       is_end_entity_certificate boolean,
                                       owner_id int8,
                                       private_key bytea,
                                       public_key bytea,
                                       certificate_id int8,
                                       primary key (id)
);

create table long_holder (
                             id int8 not null,
                             holden_id int8,
                             certificate_authority_id int8,
                             primary key (id)
);

create table user_role (
                           user_id int8 not null,
                           role int4
);

create table users (
                       id int8 not null,
                       enabled boolean,
                       first_name varchar(255),
                       forgot_password_verification_code varchar(255),
                       last_name varchar(255),
                       password varchar(255),
                       passwordless_login_verification_code varchar(255),
                       passwordless_login_verification_code_issued timestamp,
                       username varchar(255),
                       verification_code varchar(255),
                       primary key (id)
);

alter table certificate_authority
    add constraint FKj8r17bf661067aok55e2gh9ej
        foreign key (certificate_id)
            references certificate;

alter table long_holder
    add constraint FKk4ju27166t8mecxjb2q8quu3m
        foreign key (certificate_authority_id)
            references certificate_authority;

alter table user_role
    add constraint FKj345gk1bovqvfame88rcx7yyx
        foreign key (user_id)
            references users;
