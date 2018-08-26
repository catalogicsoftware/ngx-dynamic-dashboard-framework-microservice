# ngx-dynamic-dashboard-framework-microservice
This is a backend service for the ngx dynamic dashboard framework:

[https://github.com/catalogicsoftware/ngx-dynamic-dashboard-framework](https://github.com/catalogicsoftware/ngx-dynamic-dashboard-framework)

It is a maven based project so you will need to do the following: 
* Install and configure Maven. 
* Copy the dist directory produced from the Angular based dynamic dashboard [project's](https://github.com/catalogicsoftware/ngx-dynamic-dashboard-framework) build into this  
project's `static` folder. 
* Build this project using `<directory path to maven bin folder>/mvn install`  from within the project's root directory. 
* Launch the microservice over the default port: `http://localhost:8080` using `java -jar <path to the this project's root folder>/target/ngxdd-x.y.z.jar`


