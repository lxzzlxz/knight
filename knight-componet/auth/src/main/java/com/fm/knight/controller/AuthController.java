package com.fm.knight.controller;

import com.fm.knight.service.IAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/v1")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public Mono<Map<String, Object>> check(@RequestParam(value = "domain") String domain, @RequestParam(value = "sessionId") String sessionId) {

        return authService.check(domain, sessionId);
    }

	/*@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Map<String, Object> login(@RequestParam String domain, @RequestParam String sessionId, @RequestParam String userId,
			@RequestBody String body, @RequestParam int second) {

		return authService.login(domain, sessionId, userId, body, second);
	}

	@RequestMapping(value = "/appLogin", method = RequestMethod.POST)
	public Map<String, Object> appLogin(@RequestParam String domain, @RequestParam String sessionId, @RequestParam String userId,
			@RequestBody String body, @RequestParam int second) {

		return authService.appLogin(domain, sessionId, userId, body, second);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public Map<String, Object> loginOut(@RequestParam String domain, @RequestParam String sessionId) {

		return authService.logOut(domain, sessionId);
	}

	@RequestMapping(value = "/setdata", method = RequestMethod.POST)
	public Map<String, Object> setData(@RequestBody String data, @RequestParam("domain") String domain, @RequestParam("sessionId") String sessionId) {
		return authService.setData(domain, sessionId, data);
	}

	@RequestMapping(value = "/getdata", method = RequestMethod.GET)
	public String getData(@RequestParam String domain, @RequestParam String sessionId) {

		return authService.getData(domain, sessionId);
	}

	@RequestMapping(value = "/setServiceKey", method = RequestMethod.GET)
	public Map<String, Object> setServiceKey(@RequestParam("serviceName") String serviceName,
			@RequestParam("sessionId") String sessionId) {

		return authService.setServiceKey(serviceName, sessionId);
	}

	@RequestMapping(value = "/delServiceKey", method = RequestMethod.GET)
	public Map<String, Object> delServiceKey(@RequestParam("serviceName") String serviceName) {

		return authService.delServiceKey(serviceName);
	}

	@RequestMapping(value = "/getService", method = RequestMethod.GET)
	public String getService(@RequestParam("sessionId") String sessionId) {

		return authService.getService(sessionId);
	}*/
}
