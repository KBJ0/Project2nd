
CREATE TABLE `user_master` (
                               `user_seq`					BIGINT			PRIMARY KEY,
                               `user_email`				VARCHAR(50)		NOT NULL UNIQUE,
                               `user_pw`					VARCHAR(100)	NOT NULL,
                               `user_name`					VARCHAR(20)		NOT NULL,
                               `user_addr`					VARCHAR(100)	NOT NULL,
                               `user_nickname` 			VARCHAR(20)		NOT NULL,
                               `user_fav`					VARCHAR(50),
                               `user_birth`				DATE				NOT NULL,
                               `user_gender`				SMALLINT			NOT NULL,
                               `user_phone`				VARCHAR(20)		NOT NULL,
                               `user_intro`				VARCHAR(1000),
                               `user_gb`					SMALLINT			NOT NULL DEFAULT 1,
                               `user_pic`					VARCHAR(100)	NOT NULL,
                               `input_dt`					DATETIME			NOT NULL	DEFAULT CURRENT_TIMESTAMP,
                               `update_dt`					DATETIME			ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE `email_token` (
                               `token_email` 				VARCHAR(50)  	PRIMARY KEY,
                               `token_data` 				VARCHAR(100) 	NOT NULL,
                               `input_dt` 					DATETIME     	NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               `end_dt` 					DATETIME     	NOT NULL
);


CREATE TABLE `party_master` (
                                `party_seq` 				BIGINT 			PRIMARY KEY,
                                `party_name` 				VARCHAR(100) 	NOT NULL UNIQUE,
                                `party_genre` 				INT 				NOT NULL,
                                `party_location` 			INT 				NOT NULL,
                                `party_min_age` 			YEAR 				NOT NULL,
                                `party_max_age` 			YEAR 				NOT NULL,
                                `party_gender` 			SMALLINT 		NOT NULL,
                                `party_maximum` 			INT 				NOT NULL,
                                `party_join_gb` 			SMALLINT 		NOT NULL,
                                `party_intro` 				VARCHAR(1000) 	NOT NULL,
                                `party_join_form` 		VARCHAR(2000) 	NOT NULL,
                                `party_auth_gb` 			SMALLINT 		NOT NULL,
                                `party_pic`					VARCHAR(100)	NOT NULL,
                                `input_dt` 					DATETIME 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                `update_dt` 				DATETIME 		ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `party_wish` (
                              `wish_seq` 		 			BIGINT 			PRIMARY KEY,
                              `wish_user_seq` 			BIGINT 			NOT NULL,
                              `wish_party_seq` 			BIGINT 			NOT NULL,
                              `input_dt` 					DATETIME 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              UNIQUE(`wish_user_seq`, `wish_party_seq`),
                              FOREIGN KEY (`wish_user_seq`)	 REFERENCES user_master(`user_seq`),
                              FOREIGN KEY (`wish_party_seq`) REFERENCES party_master(`party_seq`)
);


CREATE TABLE `party_join` (
                              `join_seq` 					BIGINT 			PRIMARY KEY,
                              `join_party_seq` 			BIGINT 			NOT NULL,
                              `join_user_seq` 			BIGINT 			NOT NULL,
                              `join_msg` 					VARCHAR(2000) 	NOT NULL,
                              `join_gb` 					SMALLINT 		NOT NULL DEFAULT 1,
                              `input_dt` 					DATETIME 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              `update_dt` 				DATETIME 		ON UPDATE CURRENT_TIMESTAMP,
                              UNIQUE(`join_party_seq`, `join_user_seq`),
                              FOREIGN KEY (`join_party_seq`) REFERENCES party_master(`party_seq`),
                              FOREIGN KEY (`join_user_seq`)  REFERENCES user_master(`user_seq`)
);


CREATE TABLE `party_member` (
                                `member_seq` 				BIGINT 			PRIMARY KEY,
                                `member_user_seq` 		BIGINT 			NOT NULL,
                                `member_party_seq` 		BIGINT 			NOT NULL,
                                `member_role` 				VARCHAR(10),
                                `member_gb` 				SMALLINT 		NOT NULL DEFAULT 1,
                                `input_dt` 					DATETIME 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                `update_dt` 				DATETIME 		ON UPDATE CURRENT_TIMESTAMP,
                                UNIQUE(`member_user_seq`, `member_party_seq`),
                                FOREIGN KEY (`member_user_seq`)  REFERENCES user_master(`user_seq`),
                                FOREIGN KEY (`member_party_seq`) REFERENCES party_master(`party_seq`)
);


CREATE TABLE `party_budget` (
                                `budget_seq` 				BIGINT 			PRIMARY KEY,
                                `budget_party_seq` 		BIGINT 			NOT NULL,
                                `budget_member_seq` 		BIGINT 			NOT NULL,
                                `budget_gb` 				SMALLINT 		NOT NULL,
                                `budget_amount` 			BIGINT 			NOT NULL,
                                `budget_dt` 				DATE 				NOT NULL,
                                `budget_text` 				VARCHAR(10),
                                `budget_pic` 				VARCHAR(100),
                                `budget_posted_member` 	BIGINT 			NOT NULL,
                                `input_dt` 					DATETIME 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                `update_dt` 				DATETIME 		ON UPDATE CURRENT_TIMESTAMP,
                                FOREIGN KEY (`budget_party_seq`)  REFERENCES party_master(`party_seq`),
                                FOREIGN KEY (`budget_member_seq`) REFERENCES party_member(`member_seq`)
);


CREATE TABLE `party_ranking` (
                                 `ranking_seq` 				BIGINT 			PRIMARY KEY,
                                 `ranking_party_seq` 		BIGINT 			NOT NULL,
                                 `ranking_name` 			VARCHAR(10) 	NOT NULL,
                                 `ranking_grade` 			VARCHAR(3) 		NOT NULL,
                                 `input_dt` 					DATETIME 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 `update_dt` 				DATETIME 		ON UPDATE CURRENT_TIMESTAMP,
                                 FOREIGN KEY (`ranking_party_seq`) REFERENCES party_master(`party_seq`)
);


CREATE TABLE `plan_master` (
                               `plan_seq` 					BIGINT 			PRIMARY KEY,
                               `plan_party_seq` 			BIGINT 			NOT NULL,
                               `plan_start_dt` 			DATE 				NOT NULL,
                               `plan_start_time` 		TIME 				NOT NULL,
                               `plan_title` 				VARCHAR(50) 	NOT NULL,
                               `plan_contents` 			VARCHAR(2000) 	NOT NULL,
                               `input_dt` 					DATETIME 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               `update_dt` 				DATETIME 		ON UPDATE CURRENT_TIMESTAMP,
                               FOREIGN KEY (`plan_party_seq`) REFERENCES party_master(`party_seq`)
);


CREATE TABLE `plan_member` (
                               `plmember_seq` 			BIGINT 			PRIMARY KEY,
                               `plmember_plan_seq` 		BIGINT 			NOT NULL,
                               `plmember_member_seq` 	BIGINT 			NOT NULL,
                               `input_dt` 					DATETIME 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               `update_dt` 				DATETIME 		ON UPDATE CURRENT_TIMESTAMP,
                               UNIQUE(`plmember_plan_seq`, `plmember_member_seq`),
                               FOREIGN KEY (`plmember_plan_seq`)   REFERENCES plan_master(`plan_seq`),
                               FOREIGN KEY (`plmember_member_seq`) REFERENCES party_member(`member_seq`)
);


CREATE TABLE `party_board_master` (
                                      `board_seq` 				BIGINT 			PRIMARY KEY,
                                      `board_party_seq` 		BIGINT 			NOT NULL,
                                      `board_member_seq` 		BIGINT 			NOT NULL,
                                      `board_title` 				VARCHAR(50) 	NOT NULL,
                                      `board_contents` 			VARCHAR(2000) 	NOT NULL,
                                      `board_hit` 				BIGINT 			NOT NULL DEFAULT 0,
                                      `input_dt` 					DATETIME 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      `update_dt` 				DATETIME 		ON UPDATE CURRENT_TIMESTAMP,
                                      FOREIGN KEY (`board_party_seq`)  REFERENCES party_master(`party_seq`),
                                      FOREIGN KEY (`board_member_seq`) REFERENCES party_member(`member_seq`)
);


CREATE TABLE `party_board_comment` (
                                       `comment_seq` 				BIGINT 			PRIMARY KEY,
                                       `comment_board_seq` 		BIGINT 			NOT NULL,
                                       `comment_member_seq` 	BIGINT 			NOT NULL,
                                       `comment_contents` 		VARCHAR(200) 	NOT NULL,
                                       `input_dt` 					DATETIME 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       `update_dt` 				DATETIME 		ON UPDATE CURRENT_TIMESTAMP,
                                       FOREIGN KEY (`comment_board_seq`)  REFERENCES party_board_master(`board_seq`),
                                       FOREIGN KEY (`comment_member_seq`) REFERENCES party_member(`member_seq`)
);

CREATE TABLE `party_board_pic` (
                                   `board_pic_seq` 		 	BIGINT 			PRIMARY KEY,
                                   `board_pic_board_seq` 	BIGINT 			NOT NULL,
                                   `board_pic_file` 		 	VARCHAR(100) 	NOT NULL,
                                   `inpuproject2ndt_dt`  	DATETIME 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   FOREIGN KEY (`board_pic_board_seq`) REFERENCES party_board_master(`board_seq`)
);


CREATE TABLE `review_master` (
                                 `review_seq` 				BIGINT 			PRIMARY KEY,
                                 `review_plan_seq` 		BIGINT 			NOT NULL,
                                 `review_plmember_seq` 	BIGINT 			NOT NULL,
                                 `review_title` 			VARCHAR(50) 	NOT NULL,
                                 `review_contents` 		VARCHAR(2000) 	NOT NULL,
                                 `review_rating` 			DECIMAL(2,1) 	NOT NULL,
                                 `input_dt` 					DATETIME 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 `update_dt` 				DATETIME 		ON UPDATE CURRENT_TIMESTAMP,
                                 UNIQUE(`review_plan_seq`, `review_plmember_seq`),
                                 FOREIGN KEY (`review_plan_seq`) 	   REFERENCES plan_master(`plan_seq`),
                                 FOREIGN KEY (`review_plmember_seq`) REFERENCES plan_member(`plmember_seq`)
);


CREATE TABLE `review_pic` (
                              `review_pic_seq` 			BIGINT 			PRIMARY KEY,
                              `review_pic_review_seq` BIGINT 			NOT NULL,
                              `review_pic_file` 		VARCHAR(100) 	NOT NULL,
                              `input_dt` 					DATETIME 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (`review_pic_review_seq`) REFERENCES review_master(`review_seq`)
);


CREATE TABLE `review_fav` (
                              `review_fav_seq` 			BIGINT 			PRIMARY KEY,
                              `review_fav_review_seq` BIGINT 			NOT NULL,
                              `review_fav_user_seq` 	BIGINT 			NOT NULL,
                              `input_dt` 					DATETIME 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              UNIQUE(`review_fav_review_seq`, `review_fav_user_seq`),
                              FOREIGN KEY (`review_fav_review_seq`) REFERENCES review_master(`review_seq`),
                              FOREIGN KEY (`review_fav_user_seq`)   REFERENCES user_master(`user_seq`)
);
