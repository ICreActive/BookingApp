create table hibernate_sequence
(
    next_val bigint
) engine = InnoDB;
insert into hibernate_sequence
values (1);

create table hotel
(
    id              bigint               not null,
    date_finish     date,
    date_start      date,
    description     varchar(255),
    klass_apartment integer,
    number_of_seats integer default 0,
    price           integer default 0,
    title           varchar(255),
    is_available    bit     default true NOT NULL,
    filename        varchar(255),
    primary key (id)
) engine = InnoDB;

create table hotel_invoices
(
    hotel_id    bigint not null,
    invoices_id bigint not null,
    primary key (hotel_id, invoices_id)
) engine = InnoDB;

create table invoice
(
    id            bigint not null,
    paid          bit,
    hotel_id      bigint,
    seller_id     bigint,
    user_id       bigint,
    creating_date varchar(255),
    updating_date varchar(255),
    primary key (id)
) engine = InnoDB;

create table order_user
(
    id                 bigint  not null,
    klass_of_apartment integer,
    local_date_finish  date    not null,
    local_date_start   date    not null,
    number_of_seats    integer not null,
    user_id            bigint,
    creating_date      varchar(255),
    updating_date      varchar(255),
    primary key (id)
) engine = InnoDB;
create table role
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
) engine = InnoDB;

create table seller
(
    id            bigint           not null,
    bank_account  varchar(255),
    name          varchar(255),
    address       varchar(255),
    is_active     bit default true NOT NULL,
    creating_date varchar(255),
    updating_date varchar(255),
    primary key (id)
) engine = InnoDB;

create table user
(
    id                   bigint     AUTO_INCREMENT      not null,
    email                varchar(255),
    password             varchar(255),
    user_firstname       varchar(15),
    user_lastname        varchar(255),
    username             varchar(15),
    address              varchar(255),
    is_active            bit default true NOT NULL,
    activation_code      varchar(255),
    reset_password_token varchar(255),
    creating_date        varchar(255),
    updating_date        varchar(255),
    primary key (id)
) engine = InnoDB;

# create table user_orders
# (
#     user_id   bigint not null,
#     orders_id bigint not null,
#     primary key (user_id, orders_id)
# ) engine=InnoDB;

create table user_roles
(
    user_id  bigint not null,
    roles_id bigint not null,
    primary key (user_id, roles_id)
) engine = InnoDB;

alter table hotel_invoices
    add constraint UK_3cl6hpmpd998p9klx9qxwhgsp unique (invoices_id);
# alter table user_orders
#     add constraint UK_oqipapb9ox7fhui5n2ttp9ab4 unique (orders_id);
alter table hotel_invoices
    add constraint FKavk1kcupjj0i9yxpfw2oyffeo foreign key (invoices_id) references invoice (id);
alter table hotel_invoices
    add constraint FK9dpswu6h482aircwdk4heo05o foreign key (hotel_id) references hotel (id);
alter table invoice
    add constraint FKmntgg69vdjo4eka876uhj6q44 foreign key (hotel_id) references hotel (id);
alter table invoice
    add constraint FKrmlq2bn36fbbaolx9xxp1j7v5 foreign key (seller_id) references seller (id);
alter table invoice
    add constraint FKjunvl5maki3unqdvljk31kns3 foreign key (user_id) references user (id);
alter table order_user
    add constraint FK7u87v2jn4jiwxwlnw9krj36u7 foreign key (user_id) references user (id);
# alter table user_orders
#     add constraint FKeq4c6qsvyn2hifn56rh0kye8j foreign key (orders_id) references order_user (id);
# alter table user_orders
#     add constraint FKkuspr37yv513ga1okogyxrb7m foreign key (user_id) references user (id);
alter table user_roles
    add constraint FKj9553ass9uctjrmh0gkqsmv0d foreign key (roles_id) references role (id);
alter table user_roles
    add constraint FK55itppkw3i07do3h7qoclqd4k foreign key (user_id) references user (id);
