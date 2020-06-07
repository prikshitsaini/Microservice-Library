package com.assignment.microservices.bookservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name="user-service" )
public interface UserServiceProxy {

	   @GetMapping("/user/check/{uid}")
		public boolean userExist(@PathVariable(value = "uid") int uid);

}
