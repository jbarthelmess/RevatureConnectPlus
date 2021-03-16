create table user_(
    user_id int primary key generated always as identity,
    username varchar(256) not null,
    password varchar(256) not null,
    display_name varchar(256)
);

create table post(
    post_id int primary key generated always as identity,
    user_id int not null,
    date_posted int not null,
    content varchar(5000) not null
);

create table like_(
    user_id int not null,
    post_id int not null
)

create table comment(
    user_id int not null,
    post_id int not null,
    comment varchar(3000) not null
)

alter table post add foreign key (user_id) references user_(user_id);
alter table like_ add foreign key (user_id) references user_(user_id);
alter table comment add foreign key (user_id) references user_(user_id);
alter table like_ add foreign key (post_id) references post(post_id);
alter table comment add foreign key (post_id) references post(post_id);
alter table username add constraint username unique;