create table spaces
(
    space_id   char(7)     not null primary key,
    space_name varchar(20) not null,
    create_time    datetime  default now()
);
create table users
(
    user_id  varchar(20) primary key not null,
    nickname varchar(20)             not null,
    password varchar(20)             not null,
    gender   char(5)                 ,
    intro    varchar(255)            ,
    email    varchar(50)   unique not null,
    space_id char(7)            not null ,
    foreign key(space_id) references spaces(space_id)
);
create table teams
(
    team_id    char(5) primary key not null,
    name       varchar(20)         not null,
    intro      varchar(255)        ,
    captain_id varchar(20)         not null,
    space_id    char(7)         not null,
    foreign key(space_id) references spaces(space_id),
    foreign key (captain_id) references users (user_id)
);

create table team_member
(
    team_id   char(5)     not null,
    member_id   varchar(20) not null,
    foreign key (member_id) references users (user_id),
    foreign key (team_id) references teams (team_id),
    primary key (team_id, member_id)
);

create table folders
(
    folder_id   char(7)     not null primary key,
    folder_name varchar(20) not null,
    creator_id  varchar(20) not null,
    create_time datetime    not null default now(),
    parent_id   char(7)     null,
    space_id    char(7)     not null,
    self_auth   int         not null,
    now_auth    int         not null,
    is_delete   bool        not null,
    deleter_id  varchar(20) null,
    delete_time datetime    null,
    foreign key (deleter_id) references users (user_id),
    foreign key (creator_id) references users (user_id),
    foreign key (parent_id) references folders (folder_id),
    foreign key (space_id) references spaces (space_id)

);
create table documents
(
    doc_id      char(7) primary key,
    name        varchar(20) not null,
    creator_id  varchar(20) not null,
    create_time datetime    not null default now(),
    modifier_id varchar(20),
    modify_time datetime,
    self_auth   int         not null,
    now_auth    int         not null,
    is_editing  bool         not null default false,
    parent_id   char(7)     ,
    space_id    char(7)     not null,
    is_delete   bool      not null,
    deleter_id  varchar(20)     ,
    delete_time datetime    ,
    foreign key (deleter_id) references users (user_id),
    foreign key (creator_id) references users (user_id),
    foreign key (modifier_id) references users (user_id),
    foreign key (parent_id) references folders (folder_id),
    foreign key (space_id) references spaces (space_id)
);

create table user_recent
(
    doc_id      char(7)     not null,
    user_id     varchar(20) not null,
    browse_time datetime    not null,
    foreign key (user_id) references users (user_id),
    foreign key (doc_id) references documents (doc_id),
    primary key (doc_id, user_id)
);
create table team_apply
(
    apply_id         int primary key auto_increment,
    team_id    char(5)     not null,
    applicant_id    varchar(20) not null,
    apply_time datetime    not null default now(),
    apply_status  int not null,
    foreign key (applicant_id) references users (user_id),
    foreign key (team_id) references teams (team_id)
);
create table team_invite
(
    invite_id          int primary key auto_increment,
    team_id     char(5)     not null,
    invitee_id     varchar(20) not null,
    invite_time datetime    not null default now(),
    invite_status  int not null,
    foreign key (invitee_id) references users (user_id),
    foreign key (team_id) references teams (team_id)
);

create table messages
(
    msg_id       char(7)      not null primary key,
    sender_id    varchar(20)      not null,
    receiver_id  varchar(20)      not null,
    send_time    datetime     not null default now(),
    msg_type     int          not null,
    msg_content  varchar(255) null,
    msg_status   int          not null,
    msg_doc_id   char(7)      null,
    msg_apply_id     int      null,
    msg_invite_id  int          null,
    constraint fk_message_sender foreign key (sender_id) references users (user_id),
    constraint fk_message_receiver foreign key (receiver_id) references users (user_id),
    constraint fk_message_doc foreign key (msg_doc_id) references documents (doc_id),
    foreign key (msg_apply_id) references team_apply(apply_id),
    foreign key (msg_invite_id) references team_invite (invite_id)
);


create table templates
(
    temp_id     char(7) primary key,
    name        varchar(20) not null,
    creator_id  varchar(20) not null,
    create_time datetime default now(),
    intro       varchar(255),
    foreign key (creator_id) references users (user_id)
);
create table document_collector
(
    doc_id       char(7) not null,
    collector_id varchar(20) not null,
    foreign key (collector_id) references users (user_id),
    foreign key (doc_id) references documents (doc_id),
    primary key (doc_id, collector_id)
);
create table template_collector
(
    temp_id      char(7) not null,
    collector_id varchar(20) not null,
    foreign key (collector_id) references users (user_id),
    foreign key (temp_id) references templates (temp_id),
    primary key (temp_id, collector_id)
);

