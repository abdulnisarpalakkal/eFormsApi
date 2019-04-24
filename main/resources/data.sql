insert into user_role(id,type,role_desc) values(1,'ADMIN','Administrator');
insert into user_role(id,type,role_desc) values(2,'USER','end users');
insert into form_rule_type(id,rule_type_name,rule_type_desc) values(1,'SHOW','show component');
insert into form_rule_type(id,rule_type_name,rule_type_desc) values(2,'HIDE','hide component');
insert into form_rule_type_parameter(id,parameter_label,parameter_name,form_rule_type_id) values(1,'Condition','condition','1');
insert into form_rule_type_parameter(id,parameter_label,parameter_name,form_rule_type_id) values(2,'Hide condition','hide_condition','2');
