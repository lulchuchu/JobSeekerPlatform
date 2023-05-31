create table if not exists user
(
    id                int auto_increment
    primary key,
    address           varchar(255) null,
    bio               longtext     null,
    email             varchar(255) null,
    name              varchar(255) null,
    password          varchar(255) null,
    profile_picture   varchar(255) null,
    role              varchar(255) null,
    short_description varchar(255) null,
    username          varchar(255) null,
    cv                varchar(255) null
    );

create table if not exists company
(
    id                int auto_increment
    primary key,
    bio               longtext     null,
    company_size      varchar(255) null,
    industry          varchar(255) null,
    location          varchar(255) null,
    name              varchar(255) null,
    profile_picture   varchar(255) null,
    short_description varchar(255) null,
    website           varchar(255) null,
    admin_id          int          null,
    constraint FKsjgavlbmr5qjf2tqqodpmrnye
    foreign key (admin_id) references user (id)
    );

create table if not exists application
(
    id          int auto_increment
    primary key,
    address     varchar(255) null,
    description longtext     null,
    end_date    date         null,
    experience  varchar(255) null,
    on_site     varchar(255) null,
    start_date  date         null,
    title       varchar(255) null,
    type        varchar(255) null,
    company_id  int          null,
    constraint FKjm4svmen8mv59grpbgjp5v1ml
    foreign key (company_id) references company (id)
    );

create table if not exists company_followers
(
    company_id int not null,
    user_id    int not null,
    constraint UKk52qy4gy6lqdpee10fcf21cli
    unique (company_id, user_id),
    constraint FK5xb9koylo6qjdpk098af1j3fa
    foreign key (company_id) references company (id),
    constraint FKf2hfux937o3e24ha60cymty35
    foreign key (user_id) references user (id)
    );

create table if not exists followers_following
(
    follower_id  int not null,
    following_id int not null,
    constraint UK3c4r1e5dr2p3w9djm8qeh4ri8
    unique (follower_id, following_id),
    constraint FK9rfuwyqsyq3o1yfwy62k4uofr
    foreign key (follower_id) references user (id),
    constraint FKp8j0lnihae8t8ifttfvxkhb8y
    foreign key (following_id) references user (id)
    );

create table if not exists interview
(
    id             int auto_increment
    primary key,
    accepted       bit         null,
    time           datetime(6) null,
    application_id int         null,
    user_id        int         null,
    constraint FKdyrt85lva791fmh1qnhf0ee0f
    foreign key (application_id) references application (id),
    constraint FKr1uf9tvt2l7jx0x7riec5hfxe
    foreign key (user_id) references user (id)
    );

create table if not exists job
(
    id          int auto_increment
    primary key,
    address     varchar(255) null,
    description longtext     null,
    leave_date  date         null,
    start_date  date         null,
    title       varchar(255) null,
    type        varchar(255) null,
    company_id  int          null,
    user_id     int          null,
    constraint FK5q04favsasq8y70bsei7wv8fc
    foreign key (company_id) references company (id),
    constraint FKihd6m3auwpenduntl3e1opcoq
    foreign key (user_id) references user (id)
    );

create table if not exists messagee
(
    id          int auto_increment
    primary key,
    contents    varchar(255) null,
    time        datetime(6)  null,
    receiver_id int          null,
    sender_id   int          null,
    constraint FK3twm7a9n7cup4vsw5x8bu0ium
    foreign key (receiver_id) references user (id),
    constraint FKn897jx3f7y55k13dvt9ck6c9b
    foreign key (sender_id) references user (id)
    );

create table if not exists post
(
    id          int auto_increment
    primary key,
    content     longtext     null,
    images      varchar(255) null,
    posted_date date         null,
    company_id  int          null,
    user_id     int          null,
    constraint FK72mt33dhhs48hf9gcqrq4fxte
    foreign key (user_id) references user (id),
    constraint FKskdgb1bsxsiyl4yn7vgbiqvny
    foreign key (company_id) references company (id)
    );

create table if not exists comment
(
    id           int auto_increment
    primary key,
    comment_date date         null,
    contents     varchar(255) null,
    post_id      int          null,
    user_id      int          null,
    constraint FK8kcum44fvpupyw6f5baccx25c
    foreign key (user_id) references user (id),
    constraint FKs1slvnkuemjsq2kj4h3vhx7i1
    foreign key (post_id) references post (id)
    );

create table if not exists notification
(
    id        int auto_increment
    primary key,
    message   varchar(255) null,
    post_id   int          null,
    sender_id int          null,
    constraint FKn1l10g2mvj4r1qs93k952fshe
    foreign key (post_id) references post (id),
    constraint FKnbt1hengkgjqru2q44q8rlc2c
    foreign key (sender_id) references user (id)
    );

create table if not exists notification_user
(
    notification_id int not null,
    user_id         int not null,
    constraint UK4d043r1800xriuol256r42jx8
    unique (notification_id, user_id),
    constraint FK1rvq5l5k8ymnhtvn7nac8xbdh
    foreign key (notification_id) references notification (id),
    constraint FKtr2wo5lp3thmlmw5kykf26tn9
    foreign key (user_id) references user (id)
    );

create table if not exists post_react
(
    post_id int not null,
    user_id int not null,
    constraint UKtasxel1jd057icnxxjfkhimqu
    unique (post_id, user_id),
    constraint FK9i75uannuwayy7jd2uu9dq6t7
    foreign key (post_id) references post (id),
    constraint FKdq6btdbfpxi7y0ufpxwgbaurj
    foreign key (user_id) references user (id)
    );

create table if not exists user_application
(
    application_id int not null,
    user_id        int not null,
    constraint UK52q7tcd28hgmam9fsb595rt2q
    unique (application_id, user_id),
    constraint FK483kmnovbg9ccip3mj0keku9m
    foreign key (user_id) references user (id),
    constraint FK8n17vi0mc676sranq3umd4oal
    foreign key (application_id) references application (id)
    );

