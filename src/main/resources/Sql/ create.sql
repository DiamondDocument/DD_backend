create table users(
    user_id varchar(20) primary key not null,
    nickname varchar(20) not null,
    password varchar(20) not null,
    gender char(5) not null,
    intro varchar(255) not null,
    email varchar(50) not null
);
create table teams(
    team_id char(5) primary key not null,
    name varchar(20) not null,
    intro varchar(255) not null,
    captain_id varchar(20) not null,
    foreign key (captain_id) references users(user_id)
);
create table user_team(
    team_id char(5) not null,
    user_id varchar(20) not null,
    user_rank char(2) not null,
    foreign key (user_id) references users(user_id),
    foreign key (team_id) references teams(team_id),
);
create table user_last(
    doc_id char(7) not null,
    user_id varchar(20) not null,
    browse_time datetime not null,
    foreign key (user_id) references users(user_id),
    foreign key (doc_id) references documents(doc_id),
);
create table team_apply(
    team_id char(5) not null,
    user_id varchar(20) not null,
    apply_time datetime not null,
    apply_stu varchar(20) not null,
    foreign key (user_id) references users(user_id),
    foreign key (team_id) references teams(team_id),
);
create table team_invite(
    team_id char(5) not null,
    user_id varchar(20) not null,
    invite_time datetime not null,
    invite_stu varchar(20) not null,
    foreign key (user_id) references users(user_id),
    foreign key (team_id) references teams(team_id),
);
