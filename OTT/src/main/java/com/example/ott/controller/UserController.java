package com.example.ott.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ott.model.User;
import com.example.ott.model.UserRepo;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {
	
	@Autowired
	UserRepo urepo;

	@RequestMapping("indexheader")
	public String IndexHeader() {
		return "index_header";
	}
	@RequestMapping("watch")
	public String Watch() {
		return "watch";
	}
	@RequestMapping("search")
	public String Search() {
		return "search";
	}

	@RequestMapping("indexfooter")
	public String IndexFooter() {
		return "index_footer";
	}
	@RequestMapping("catagory")
	public String catagory()
	{
		return "catagory";
	}
	@RequestMapping("index")
	public String Index() {
		return "index";
	}

	@RequestMapping("signup")
	public String signup() {
		return "signup";
	}

	@RequestMapping("about")
	public String about() {
		return "about";
	}

	@RequestMapping("categories")
	public String categories() {
		return "categories";
	}
	@RequestMapping("favourites")
	public String favourites() {
		return "fav";
	}

	@RequestMapping("admlogin")
	public String adminlogin() {
		return "adminlogin";
	}
	

	@RequestMapping("fav")
	public String fav() {
		return "fav";
	}

	@RequestMapping("admheader")
	public String admheader() {
		return "admheader";
	}
	
	@RequestMapping("playpage")
	public String playpage() {
		return "playpage";
	}

	@RequestMapping("logoutproc")
	public String logoutproc(HttpServletRequest request, HttpServletResponse response){
	    
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals("MovIot")) {
	                cookie.setMaxAge(0); // Setting the max age to 0 deletes the cookie
	                response.addCookie(cookie);
	                break;
	            }
	        }
	        
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals("MovIotUser")) {
	                cookie.setMaxAge(0); // Setting the max age to 0 deletes the cookie
	                response.addCookie(cookie);
	                break;
	            }
	        }
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals("MovIotEmail")) {
	                cookie.setMaxAge(0); // Setting the max age to 0 deletes the cookie
	                response.addCookie(cookie);
	                break;
	            }
	        }
	    }
	    
	    
	    return "redirect:/wa";
	}

	@RequestMapping("admloginproc")
	public String loginprocess(@RequestParam("uemail") String email, @RequestParam("upass") String password, ModelMap model) {
	    // Check if the provided credentials match the admin's credentials
	    if (email.equals("admin@example.com") && password.equals("adminpassword")) {
	        return "admin_dashboard"; // If credentials match, redirect to admin dashboard
	    }  else {
	            model.put("msg", "Email or password incorrect");
	            return "adminlogin"; // If credentials don't match any user, display error and redirect to login page
	        }
	    }
	

	@RequestMapping("loginproc")
	public String loginprocess(@RequestParam("uemail") String email, @RequestParam("upass") String password, ModelMap model, HttpServletResponse response) {

	        User user = urepo.findByEmail(email);
	        if (user.getPassword().equals(password)) {
	            String id = Integer.toString(user.getId());
	            System.out.println(user.getEmail());
	            System.out.println(user.getPassword());
	            String nm = user.getName();
	            String mail = user.getEmail();
	            Cookie cr = new Cookie("MovIot", id );
	            Cookie cn = new Cookie("MovIotUser", nm );
	            Cookie ce = new Cookie("MovIotEmail", mail );
	            
	            // Setting cookie expiration time (optional)
	            cr.setMaxAge(4 * 60 * 60); // Cookie expires in 4 hours
	            cn.setMaxAge(4 * 60 * 60);
	            ce.setMaxAge(4 * 60 * 60);
	            
	            // Adding the cookie to the response
	            response.addCookie(cr);
	            response.addCookie(cn);
	            response.addCookie(ce);
	            
	            return "redirect:/wa"; // If credentials match a regular user, redirect to user dashboard
	        } else {
	            model.put("msg", "Email or password incorrect");
	            return "login"; // If credentials don't match any user, display error and redirect to login page
	        }
	    }



	@RequestMapping("wa")
	public String wa() {
	    // Add a 1-second delay before redirecting
	    try {
	        TimeUnit.SECONDS.sleep(1);
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	    }
	    
	    // Redirect to the "index" page after the delay
	    return "redirect:/index"; // Assuming "index" is the endpoint you want to redirect to after the delay
	}

	@RequestMapping("regprocess")
	public String register(@RequestParam("uname") String name, @RequestParam("uemail") String email,
			@RequestParam("uphno") String phno, @RequestParam("upass") String pass,
			@RequestParam("upass") String password, @RequestParam("uans") String answer) {
		User u = new User();
		u.setName(name);
		u.setEmail(email);
		u.setPhno(phno);
		u.setPassword(password);
		u.setAnswer(answer);
		urepo.save(u);
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage() {
	    return "login";
	}
	
}