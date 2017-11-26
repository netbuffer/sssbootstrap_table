# sssbootstrap_table demo

## 技术栈/technology stack
* spring+springmvc+spring data jpa
* bootstrap-table1.9
* `bootstrap-datetimepicker`
* druid
* fastjson
* log4jdbc-log4j2(show sql)
* hibernate-validator
* lombok
* zxing
* itextpdf
* jedis([spring-data-redis](http://docs.spring.io/spring-data/redis/docs/1.7.6.RELEASE/reference/html/))
* JsonPath:[https://github.com/json-path/JsonPath](https://github.com/json-path/JsonPath)
* spring-session 

> ## github:[https://github.com/netbuffer/sssbootstrap_table](https://github.com/netbuffer/sssbootstrap_table)
> ## git@osc:[http://git.oschina.net/netbuffer/sssbootstrap_table](http://git.oschina.net/netbuffer/sssbootstrap_table)

## How to run
The project constructed by `maven`, please execute under the `src/main/java` `SQL script` to your `MySQL` database operation, and then modify the database configuration information `jdbc.properties` under the path `src/main/resource` configuration file in the project directory, enter, execute `mvn tomcat7:run` to run

## 运行
项目采用`maven`构建，运行前请先执行`src/main/java`下的`sql`脚本到你的`mysql`数据库中，然后修改`src/main/resource`下的`jdbc.properties`配置文件中的数据库配置信息，进入项目目录，执行`mvn tomcat7:run`来运行

git->clone;eclipse->File->Import->Existing Maven projects，导入到eclipse后，等maven依赖下载完，右键项目，run as->maven build->tomcat7:run
---
* `develop`开发分支  
* `jwt` json web token测试分支
* `beetl` beetl模板渲染测试分支
* `thymeleaf` thymeleaf模板渲染测试分支
* `freemarker` freemarker模板渲染测试分支
* `angular1` angular1.6.4测试分支
* `gradle` 使用gradle构建项目

# other projects
> `ssmbootstrap_table(spring+springmvc+mybatis)` github:[https://github.com/netbuffer/ssmbootstrap_table](https://github.com/netbuffer/ssmbootstrap_table)`/`git@osc:[https://git.oschina.net/gradle/ssmbootstrap_table](https://git.oschina.net/gradle/ssmbootstrap_table)

> `ssmbt(ssmbootstrap_table maven module )` github:[https://github.com/netbuffer/ssmbt](https://github.com/netbuffer/ssmbt)`/`git@osc:[https://git.oschina.net/netbuffer/ssmbt](https://git.oschina.net/netbuffer/ssmbt)    

> `sshbootstrap_table(spring+struts2+hibernate)` github:[https://github.com/netbuffer/sshbootstrap-table](https://github.com/netbuffer/sshbootstrap-table)`/`git@osc:[https://git.oschina.net/netbuffer/sshbootstrap-table](https://git.oschina.net/netbuffer/sshbootstrap-table)    

> `spring-boot-bootstrap_table(springboot)` github:[https://github.com/netbuffer/spring-boot-bootstrap_table](https://github.com/netbuffer/spring-boot-bootstrap_table)`/`git@osc:[https://git.oschina.net/netbuffer/spring-boot-bootstrap_table](https://git.oschina.net/netbuffer/spring-boot-bootstrap_table)   

> `jfinal-bootstrap-table(jfinal)`github:[https://github.com/netbuffer/jfinal-bootstrap-table](https://github.com/netbuffer/jfinal-bootstrap-table)`/`git@osc:[http://git.oschina.net/gradle/jfinal-bootstrap-table](http://git.oschina.net/gradle/jfinal-bootstrap-table)  

> `medoo_bootstrap_table(php5)`github:[https://github.com/netbuffer/medoo_bootstrap_table](https://github.com/netbuffer/medoo_bootstrap_table)`/`git@osc:[http://git.oschina.net/gradle/medoo_bootstrap_table](http://git.oschina.net/gradle/medoo_bootstrap_table)
