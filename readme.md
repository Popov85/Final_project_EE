# Restaurant automation application

This is a web-based application intended to assist all restaurant workers: managers, waiters and chefs in their daily routine.
It is written in Java and powered by the amazing Java's Spring frameworks: Spring MVC, Hibernate, etc.

## Getting Started

### Prerequisites

Application requires Postgres DBMS (all the scripts to build a DB can be found
<a href="https://github.com/Popov85/Final_project_EE/tree/master/src/main/resources/sql">here</a>).

### Installing

Once a copy of sources is downloaded and all the dependencies are installed and resolved, run the tomcat server.
```
	git clone https://github.com/Popov85/Final_project_EE
	cd restaurant
	./mvnw tomcat7:run
```

## Built With

* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html/) - Main programming language
* [Gradle](https://gradle.org/) - Dependency Management
* [Tomcat](http://tomcat.apache.org/) - Servlet container
* [Hibernate ORM](https://gradle.org/) - Object-relational mapping tool
* [Spring](https://spring.io/) - Main application framework
* [Spring MVC](https://spring.io/) - Web-framework
* [Spring Security](https://spring.io/) - Security-framework
* [Spring WebSocket](https://spring.io/) - WebSocket framework
* [Thymeleaf](http://www.thymeleaf.org/) - Server template engine
* [jQuery](https://jquery.com/) - Main javascript framework
* [DataTables](https://datatables.net/) - jQuery's tables plugin
* [Bootstrap](http://getbootstrap.com/) - CSS framework


## Demo
You can test a fully working live demo at https://Popov85.github.io/restaurant/

## Features

* **Server-side table processing**
* **Security supported**
* **STOMP messaging between waiters->chef and chef->waiter**

## App

### Manager admin panel

 Managers can edit staff information, see all prepared dishes and orders (analyse effectiveness of chefs), see the stock state
(ingredients running out), analyse the most and last popular dishes.

<img width="600" src="https://drive.google.com/uc?export=view&id=0B32rkhYCEyjueFVkT2dBQmZnNVk"/>

### Waiter view

Waiter's responsibility is to take orders, do check-outs, close/cancel orders and edit them if needed.
Also waiter informs a client if a dish cannot be prepared because of shortage in ingredients.

<img width="600" src="https://drive.google.com/uc?export=view&id=0B32rkhYCEyjuWnMzTTBsZ1dXZzg"/>

<img width="600" src="https://drive.google.com/uc?export=view&id=0B32rkhYCEyjuellPTF9IVHpxeG8"/>

### Chef view

Chef manages orders and signals when a dish is cooked or cannot be cooked for whatever reason.

<img width="600" alt="chef" src="https://drive.google.com/uc?export=view&id=0B32rkhYCEyjucUpIOWxleC1nT3c">

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* D. Shkurupiy
* D. Lenchuk
* D. Prokopuik



