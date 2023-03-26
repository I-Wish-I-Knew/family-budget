CREATE TABLE IF NOT EXISTS FAMILIES (
    family_id    BIGINT GENERATED BY DEFAULT AS IDENTITY,
    family_name  VARCHAR(200) NOT NULL,
    CONSTRAINT pk_families PRIMARY KEY (family_id)
);

CREATE TABLE IF NOT EXISTS USERS (
    user_id     BIGINT GENERATED BY DEFAULT AS IDENTITY,
    user_name   VARCHAR(150) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    CONSTRAINT pk_users PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS FAMILY_MEMBERS (
     user_id    BIGINT NOT NULL,
     family_id  BIGINT NOT NULL,
     CONSTRAINT pk_family_members PRIMARY KEY (user_id, family_id),
     CONSTRAINT fk_family_members_users FOREIGN KEY (user_id)
         REFERENCES USERS (user_id),
     CONSTRAINT fk_family_members_families FOREIGN KEY (family_id)
        REFERENCES FAMILIES (family_id)
);

CREATE TABLE IF NOT EXISTS MONEY_SOURCES (
    money_source_id   INT GENERATED BY DEFAULT AS IDENTITY,
    money_source_name VARCHAR(200) NOT NULL UNIQUE,
    CONSTRAINT pk_money_sources PRIMARY KEY (money_source_id)
);

CREATE TABLE IF NOT EXISTS INCOME (
    income_id        INT GENERATED BY DEFAULT AS IDENTITY,
    money_source_id  INT,
    user_id          BIGINT NOT NULL,
    income_sum       INT NOT NULL,
    paymentDate      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_income PRIMARY KEY (income_id),
    CONSTRAINT fk_income_money_sources FOREIGN KEY (money_source_id)
        REFERENCES MONEY_SOURCES (money_source_id) ON DELETE SET NULL,
    CONSTRAINT fk_income_users FOREIGN KEY (user_id) REFERENCES USERS (user_id)
    );

CREATE TABLE IF NOT EXISTS EXPENSE_GROUPS (
    expense_group_id    INT GENERATED BY DEFAULT AS IDENTITY,
    expense_group_name  VARCHAR(150) UNIQUE NOT NULL,
    CONSTRAINT pk_expense_groups PRIMARY KEY (expense_group_id)
);

CREATE TABLE IF NOT EXISTS RECIPIENTS (
    recipient_id      BIGINT GENERATED BY DEFAULT AS IDENTITY,
    recipient_name    VARCHAR(200) UNIQUE,
    recipient_account VARCHAR(150) UNIQUE,
    CONSTRAINT pk_recipients PRIMARY KEY (recipient_id)
);

CREATE TABLE IF NOT EXISTS EXPENSES (
    expense_id     BIGINT GENERATED BY DEFAULT AS IDENTITY,
    user_id        BIGINT NOT NULL,
    recipient_id   BIGINT NOT NULL,
    expense_sum    INT NOT NULL,
    category       VARCHAR NOT NULL,
    group_id       INT,
    paymentDate    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_expenses PRIMARY KEY (expense_id),
    CONSTRAINT fk_expenses_recipients FOREIGN KEY (recipient_id)
         REFERENCES RECIPIENTS (recipient_id) ON DELETE CASCADE,
    CONSTRAINT fk_expenses_users FOREIGN KEY (user_id)
         REFERENCES USERS (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_expenses_expense_groups FOREIGN KEY (group_id)
         REFERENCES EXPENSE_GROUPS (expense_group_id) ON DELETE SET NULL
);







