create table RADIUS_STUDENT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    FIRST_NAME varchar(255) not null,
    LAST_NAME varchar(255) not null,
    PATRONYMIC varchar(255) not null,
    BIRTH_DATE date not null,
    GROUP_ID varchar(36) not null,
    --
    primary key (ID)
);