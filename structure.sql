CREATE TABLE board_commission  ( 
	oid                	int(11) AUTO_INCREMENT NOT NULL,
	fk_board_period_oid	int(11) NOT NULL,
	lu_commission      	varchar(64) NOT NULL,
	primary key (oid)
	) engine=myisam;
CREATE TABLE board_expert  ( 
	oid                	int(11) AUTO_INCREMENT NOT NULL,
	fk_board_period_oid	int(11) UNSIGNED NOT NULL,
	fk_user_oid        	int(11) UNSIGNED NOT NULL,
	start_date         	date DEFAULT NULL,
	end_date           	date DEFAULT NULL ,
	primary key (oid)
	) engine=myisam;
CREATE TABLE board_observer  ( 
	oid                     	int(11) AUTO_INCREMENT NOT NULL,
	fk_board_period_oid     	int(11) NOT NULL,
	fk_observer_oid         	int(11) NOT NULL,
	lu_membership_type      	varchar(16) DEFAULT NULL,
	voting_date             	date DEFAULT NULL,
	statute_letter_no       	varchar(64) DEFAULT NULL,
	statute_letter_date     	date DEFAULT NULL,
	communique_issuance_date	date DEFAULT NULL,
	observation_start_date  	date DEFAULT NULL,
	observation_end_date    	date DEFAULT NULL,
	lu_how_to_elect         	varchar(64) DEFAULT NULL ,
	primary key (oid)
	) engine=myisam;
CREATE TABLE board_period  ( 
	oid         	int(11) AUTO_INCREMENT NOT NULL,
	fk_board_oid	int(11) NOT NULL,
	lu_period_no	int(11) NOT NULL ,
	primary key (oid)
	) engine=myisam;
CREATE TABLE board  ( 
	oid              	int(20) AUTO_INCREMENT NOT NULL,
	title            	varchar(256) DEFAULT NULL,
	lu_board_type    	varchar(64) DEFAULT NULL,
	fk_chairman_oid  	int(11) DEFAULT NULL,
	fk_secretary_oid 	int(11) DEFAULT NULL,
	observation_count	int(11) DEFAULT NULL,
	lu_session_period	varchar(64) DEFAULT NULL,
	related_law      	text DEFAULT NULL,
	phone            	varchar(128) DEFAULT NULL,
	fax              	varchar(128) DEFAULT NULL,
	email            	varchar(256) DEFAULT NULL,
	primary key (oid)
	) engine=myisam;
CREATE TABLE contact  ( 
	oid            	int(20) AUTO_INCREMENT NOT NULL,
	lu_contact_type	varchar(64) DEFAULT NULL,
	lu_title       	varchar(64) DEFAULT NULL,
	lu_gender      	varchar(8) DEFAULT NULL,
	name           	varchar(256) DEFAULT NULL,
	family         	varchar(256) DEFAULT NULL,
	father_name    	varchar(256) DEFAULT NULL,
	birth_date     	date DEFAULT NULL,
	national_code  	varchar(32) DEFAULT NULL,
	phone          	varchar(32) DEFAULT NULL,
	cell           	varchar(32) DEFAULT NULL,
	email          	varchar(256) DEFAULT NULL,
	fax            	varchar(32) DEFAULT NULL,
	url            	varchar(512) DEFAULT NULL,
	address        	varchar(512) DEFAULT NULL,
	fk_contact_oid 	int(20) DEFAULT NULL,
	filename       	varchar(256) DEFAULT NULL,
	description    	text DEFAULT NULL ,
	primary key (oid)
	) engine=myisam;
CREATE TABLE lookup  ( 
	oid      	int(20) AUTO_INCREMENT NOT NULL,
	group_key	varchar(256) DEFAULT NULL,
	`key`      	varchar(256) DEFAULT NULL,
	value    	text DEFAULT NULL,
	extra    	varchar(256) DEFAULT NULL,
	orderby  	int(3) UNSIGNED DEFAULT 0 ,
	primary key (oid)
	) engine=myisam;
CREATE TABLE misc_report  ( 
	oid                	int(11) AUTO_INCREMENT NOT NULL,
	fk_board_period_oid	int(11) NOT NULL,
	title              	varchar(1024) NOT NULL,
	letter_no          	varchar(128) NOT NULL,
	letter_date        	date NOT NULL,
	description        	text DEFAULT NULL,
	filename           	varchar(256) DEFAULT NULL,
	lu_type            	varchar(32) NOT NULL ,
	primary key (oid)
	) engine=myisam;
CREATE TABLE parliament_member  ( 
	oid          	int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
	lu_title     	varchar(32) DEFAULT NULL,
	name         	varchar(256) COMMENT 'Name' DEFAULT NULL,
	family       	varchar(256) DEFAULT NULL,
	lu_gender    	varchar(8) DEFAULT NULL,
	father_name  	varchar(256) DEFAULT NULL,
	birth_year   	smallint(5) UNSIGNED NULL DEFAULT '0',
	lu_birth_city	varchar(128) DEFAULT NULL,
	filename     	varchar(256) DEFAULT NULL,
	description  	mediumtext DEFAULT NULL ,
	primary key (oid)
	) engine=myisam;
CREATE TABLE parliament_member_stat  ( 
	fk_parliament_member_oid	int(10) UNSIGNED NOT NULL,
	stat_period             	varchar(255) DEFAULT NULL,
	stat_province           	varchar(255) DEFAULT NULL,
	stat_city               	varchar(255) DEFAULT NULL,
	stat_period_no          	int(10) UNSIGNED NULL DEFAULT '0',
	stat_province_key       	varchar(255) DEFAULT NULL 
	);
CREATE TABLE pm_period_city  ( 
	fk_pm_period_oid	int(10) UNSIGNED NOT NULL,
	lu_city         	varchar(128) NOT NULL 
	);
CREATE TABLE pm_period_commission  ( 
	oid                     	int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
	fk_parliament_member_oid	int(10) UNSIGNED NOT NULL,
	lu_commission           	varchar(64) NOT NULL,
	lu_commission_role      	varchar(64) NOT NULL,
	lu_period_no            	tinyint(4) NOT NULL,
	lu_year_no              	tinyint(3) UNSIGNED NOT NULL ,
	primary key (oid)
	) engine=myisam;
CREATE TABLE pm_period_lang  ( 
	fk_pm_period_oid	int(10) UNSIGNED NOT NULL,
	lu_lang         	varchar(16) NOT NULL 
	);
CREATE TABLE pm_period  ( 
	oid                     	int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
	fk_parliament_member_oid	int(10) UNSIGNED NOT NULL,
	lu_inter_period         	varchar(16) DEFAULT NULL,
	lu_period_no            	tinyint(3) UNSIGNED NOT NULL,
	lu_province             	varchar(128) DEFAULT NULL,
	vote_count              	bigint(20) UNSIGNED NOT NULL DEFAULT '0',
	vote_percent            	decimal(4,2) UNSIGNED NOT NULL DEFAULT '0.00',
	description             	text DEFAULT NULL ,
	primary key (oid)
	) engine=myisam;
CREATE TABLE security_role  ( 
	oid  	int(11) NOT NULL,
	title	varchar(255) DEFAULT NULL ,
	primary key (oid)
	) engine=myisam;
CREATE TABLE security_user_role  ( 
	fk_user_oid	int(11) NOT NULL,
	fk_role_oid	int(11) NOT NULL 
	);
CREATE TABLE security_user  ( 
	oid           	int(11) AUTO_INCREMENT NOT NULL,
	fk_contact_oid	int(11) DEFAULT NULL,
	username      	varchar(32) DEFAULT NULL,
	password      	varchar(64) DEFAULT NULL,
	lu_status     	varchar(16) NOT NULL DEFAULT 'enable',
	primary key (oid)
	) engine=myisam;
CREATE TABLE sess_absent  ( 
	fk_session_oid    	int(11) NOT NULL,
	fk_observer_oid   	int(11) NOT NULL,
	lu_abs_pres_status	varchar(64) DEFAULT NULL 
	);
CREATE TABLE sess_agenda  ( 
	oid           	int(11) AUTO_INCREMENT NOT NULL,
	fk_session_oid	int(11) NOT NULL,
	orderby       	smallint(6) DEFAULT NULL,
	description   	text DEFAULT NULL ,
	primary key (oid)
	) engine=myisam;
CREATE TABLE sess_board_justification_agenda  ( 
	fk_session_oid	int(11) NOT NULL,
	agenda_no     	int(11) NOT NULL 
	);
CREATE TABLE sess_board_justification  ( 
	fk_session_oid	int(11) NOT NULL,
	location      	varchar(512) DEFAULT NULL,
	document      	text DEFAULT NULL,
	agenda_history	text DEFAULT NULL,
	description   	text DEFAULT NULL,
	filename      	varchar(256) DEFAULT NULL,
	letter_no     	varchar(64) DEFAULT NULL,
	letter_date   	date DEFAULT NULL
	) engine=myisam;
CREATE TABLE sess_board_report  ( 
	fk_session_oid	int(11) NOT NULL,
	letter_no     	varchar(64) DEFAULT NULL,
	letter_date   	date DEFAULT NULL,
	lu_hold_status	varchar(64) DEFAULT NULL,
	approval      	text DEFAULT NULL,
	filename      	varchar(256) DEFAULT NULL 
	);
CREATE TABLE sess_commission_report  ( 
	oid                    	int(11) AUTO_INCREMENT NOT NULL,
	fk_session_oid         	int(11) NOT NULL,
	fk_board_observer_oid  	int(11) NOT NULL,
	fk_board_commission_oid	int(11) NOT NULL,
	to_cmsn_letter_no      	varchar(64) DEFAULT NULL,
	to_cmsn_letter_date    	date DEFAULT NULL,
	to_dpt_letter_no       	varchar(64) DEFAULT NULL,
	to_dpt_letter_date     	date DEFAULT NULL,
	evaluation             	text DEFAULT NULL,
	filename               	varchar(256) DEFAULT NULL,
	lu_grade_status        	varchar(64) DEFAULT NULL,
	fk_observer_report_oid 	int(11) NOT NULL ,
	primary key (oid)
	) engine=myisam;
CREATE TABLE sess_deputy_analytical  ( 
	oid                     	int(11) AUTO_INCREMENT NOT NULL,
	fk_session_oid          	int(11) NOT NULL,
	fk_commission_report_oid	int(11) NOT NULL,
	beneficiary_letter_no   	varchar(64) DEFAULT NULL,
	beneficiary_letter_date 	date DEFAULT NULL,
	evaluation_letter_no    	varchar(64) DEFAULT NULL,
	evaluation_letter_date  	date DEFAULT NULL,
	lu_grade_status         	varchar(64) DEFAULT NULL,
	analytical              	text DEFAULT NULL,
	filename                	varchar(256) DEFAULT NULL ,
	primary key (oid)
	) engine=myisam;
CREATE TABLE sess_deputy_justification  ( 
	fk_session_oid  	int(11) NOT NULL,
	document        	text DEFAULT NULL,
	expert_opinion  	text DEFAULT NULL,
	description     	text DEFAULT NULL,
	filename        	varchar(256) DEFAULT NULL,
	send_letter_no  	varchar(64) DEFAULT NULL,
	send_letter_date	date DEFAULT NULL
	) engine=myisam;
CREATE TABLE sess_invitation  ( 
	fk_session_oid      	int(11) NOT NULL,
	session_start_time  	time DEFAULT NULL,
	letter_received_date	date DEFAULT NULL,
	letter_no           	varchar(32) DEFAULT NULL
	) engine=myisam;
CREATE TABLE sess_observer_report  ( 
	oid                      	int(11) AUTO_INCREMENT NOT NULL,
	fk_session_oid           	int(11) NOT NULL,
	fk_board_observer_oid    	int(11) DEFAULT NULL,
	letter_no                	varchar(64) DEFAULT NULL,
	letter_date              	date DEFAULT NULL,
	lu_abs_pres_status       	varchar(64) DEFAULT NULL,
	lu_invitation_status     	varchar(4) DEFAULT NULL,
	lu_coordination_status   	varchar(64) DEFAULT NULL,
	lu_hold_status           	varchar(64) DEFAULT NULL,
	lu_member_abs_pres_status	varchar(64) DEFAULT NULL,
	topic                    	text DEFAULT NULL,
	suggestion               	text DEFAULT NULL,
	contrary                 	text DEFAULT NULL,
	description              	text DEFAULT NULL,
	filename                 	varchar(256) DEFAULT NULL ,
	primary key (oid)
	) engine=myisam;
CREATE TABLE sess_session  ( 
	oid                	int(11) AUTO_INCREMENT NOT NULL,
	fk_board_period_oid	int(11) DEFAULT NULL,
	no                 	int(11) DEFAULT NULL,
	date               	date DEFAULT NULL,
	primary key (oid)
	) engine=myisam;
