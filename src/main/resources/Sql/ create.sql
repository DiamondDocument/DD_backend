create table spaces
(
    space_id    int         not null primary key auto_increment,
    auth        int         not null default 2 check ( auth between 1 and 4),
    name        varchar(20) null,
    create_time datetime default now()
);
create table users
(
    user_id  varchar(20) primary key not null,
    nickname varchar(20)             not null,
    password varchar(20)             not null,
    gender   char(5),
    intro    varchar(255),
    email    varchar(50) unique      not null,
    space_id int                     not null,
    foreign key (space_id) references spaces (space_id) on delete cascade
);
create table teams
(
    team_id    char(5) primary key not null,
    name       varchar(20)         not null,
    intro      varchar(255),
    captain_id varchar(20)         not null,
    space_id   int                 not null,
    foreign key (space_id) references spaces (space_id) on delete cascade ,
    foreign key (captain_id) references users (user_id) on delete cascade
);

create table team_member
(
    team_id   char(5)     not null,
    member_id varchar(20) not null,
    foreign key (member_id) references users (user_id) on delete cascade,
    foreign key (team_id) references teams (team_id) on delete cascade,
    primary key (team_id, member_id)
);

create table folders
(
    folder_id   char(7)     not null primary key,
    name        varchar(20) not null,
    creator_id  varchar(20) not null,
    create_time datetime    not null default now(),
    parent_id   char(7)     null,
    space_id    int         not null,
    self_auth   int         not null default 2 check ( self_auth between 1 and 4),
    now_auth    int         not null default 2 check ( now_auth between 1 and 4),
    is_delete   bool        not null default false,
    deleter_id  varchar(20) null,
    delete_time datetime    null,
    foreign key (deleter_id) references users (user_id) on delete set null ,
    foreign key (creator_id) references users (user_id) on delete cascade,
    foreign key (parent_id) references folders (folder_id) on delete cascade,
    foreign key (space_id) references spaces (space_id) on delete cascade

);
create table documents
(
    doc_id      char(7) primary key,
    name        varchar(20) not null,
    creator_id  varchar(20) not null,
    create_time datetime    not null default now(),
    modifier_id varchar(20),
    modify_time datetime,
    self_auth   int         not null default 2 check ( self_auth between 1 and 4),
    now_auth    int         not null default 2 check ( now_auth between 1 and 4),
    is_editing  bool        not null default false,
    parent_id   char(7),
    space_id    int         not null,
    is_delete   bool        not null default false,
    deleter_id  varchar(20),
    delete_time datetime,
    foreign key (deleter_id) references users (user_id) on delete set null ,
    foreign key (creator_id) references users (user_id) on delete cascade,
    foreign key (modifier_id) references users (user_id) on delete set null ,
    foreign key (parent_id) references folders (folder_id) on delete cascade,
    foreign key (space_id) references spaces (space_id) on delete cascade
);

create table user_recent
(
    doc_id      char(7)     not null,
    user_id     varchar(20) not null,
    browse_time datetime    not null default now(),
    foreign key (user_id) references users (user_id) on delete cascade,
    foreign key (doc_id) references documents (doc_id) on delete cascade,
    primary key (doc_id, user_id)
);
create table team_deal
(
    deal_id     int primary key auto_increment,
    team_id     char(5)     not null,
    user_id     varchar(20) not null,
    deal_type   int         not null check ( deal_type between 1 and 2),
    create_time datetime    not null default now(),
    deal_status int         not null default 0 check ( deal_status between 0 and 2),
    foreign key (user_id) references users (user_id) on delete cascade,
    foreign key (team_id) references teams (team_id) on delete cascade
);
# create table team_invite
# (
#     invite_id          int primary key auto_increment,
#     team_id     char(5)     not null,
#     invitee_id     varchar(20) not null,
#     invite_time datetime    not null default now(),
#     invite_status  int not null,
#     foreign key (invitee_id) references users (user_id),
#     foreign key (team_id) references teams (team_id)
# );

create table messages
(
    msg_id      char(7)      not null primary key,
    sender_id   varchar(20)  not null,
    receiver_id varchar(20)  not null,
    send_time   datetime     not null default now(),
    msg_type    int          not null check ( msg_type between 1 and 7),
    msg_content varchar(255) null,
    msg_status  int          not null,
    msg_doc_id  char(7)      null,
    msg_deal_id int          null,
    constraint fk_message_sender foreign key (sender_id) references users (user_id) on delete cascade ,
    constraint fk_message_receiver foreign key (receiver_id) references users (user_id) on delete cascade ,
    constraint fk_message_doc foreign key (msg_doc_id) references documents (doc_id) on delete cascade ,
    foreign key (msg_deal_id) references team_deal (deal_id) on delete cascade
);


create table templates
(
    temp_id     char(7) primary key,
    name        varchar(20) not null,
    creator_id  varchar(20) not null,
    create_time datetime default now(),
    intro       varchar(255),
    foreign key (creator_id) references users (user_id) on delete cascade
);
create table document_collector
(
    doc_id       char(7)     not null,
    collector_id varchar(20) not null,
    foreign key (collector_id) references users (user_id) on delete cascade ,
    foreign key (doc_id) references documents (doc_id) on delete cascade ,
    primary key (doc_id, collector_id)
);
create table template_collector
(
    temp_id      char(7)     not null,
    collector_id varchar(20) not null,
    foreign key (collector_id) references users (user_id)on delete cascade ,
    foreign key (temp_id) references templates (temp_id) on delete cascade ,
    primary key (temp_id, collector_id)
);
create table comments
(
    comment_id  int             auto_increment primary key ,
    content     varchar(255)    not null ,
    doc_id      char(7)         not null ,
    creator_id  varchar(20)     not null ,
    create_time datetime        default now(),
    foreign key (creator_id) references users (user_id) on delete cascade ,
    foreign key (doc_id) references documents (doc_id) on delete cascade
);

